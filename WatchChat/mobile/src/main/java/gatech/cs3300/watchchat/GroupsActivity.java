package gatech.cs3300.watchchat;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class GroupsActivity extends AppCompatActivity {

    private String username, userid;
    private ArrayList<Group> groups;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groups);

        if(getIntent().hasExtra("username"))
            username = getIntent().getStringExtra("Username");
        if(getIntent().hasExtra("userId"))
            userid = getIntent().getStringExtra("UserId");

        groups = new ArrayList<>(100);
        fetchGroupsFromAPI();
    }

    @Override
    protected void onResume(){
        super.onResume();
        fetchGroupsFromAPI();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_groups, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.LogoutButton:
                logout();
                return true;
            case R.id.CreateGroupButton:
                createGroup();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void logout(){
        startActivity(new Intent(this, MainActivity.class));
    }

    public void createGroup(){
        startActivity(new Intent(this, CreateGroupActivity.class));
    }

    public void fetchGroupsFromAPI(){
        AsyncHttpClient client = new AsyncHttpClient();
        try{
            URL url = new URL(new String("http://cs3300.elasticbeanstalk.com" + "/users/" + userid + "/groups"));
            client.get(url.toString(), new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    try {
                        parseGroups(new JSONArray(new String(responseBody)));
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), "Parsing Error!",
                                Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody,
                                      Throwable error) {
                    Log.e("Get groups", error.getLocalizedMessage());
                    Toast.makeText(getApplicationContext(), "Network Error!",
                            Toast.LENGTH_SHORT).show();
                }
            });
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    private void parseGroups(JSONArray response){
        for(int i = 0; i < response.length(); i++) {
            try {
                Group group = new Group(response.getJSONObject(i));
                if (!groups.contains(group))
                    groups.add(0, group);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        Collections.sort(groups);
    }
}
