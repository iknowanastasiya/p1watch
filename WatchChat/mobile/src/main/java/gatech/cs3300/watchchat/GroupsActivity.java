package gatech.cs3300.watchchat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;

public class GroupsActivity extends AppCompatActivity {

    private User currentUser;
    private String username, userid;
    private ArrayList<Group> groups;
    private SharedPreferences mPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groups);

        if(getIntent().hasExtra("user")){
            currentUser = getIntent().getParcelableExtra("user");
            userid = currentUser.userId;
            username = currentUser.userName;
        } else {
            mPrefs = getSharedPreferences("WATCHCHAT", MODE_PRIVATE);
            if (mPrefs != null && !mPrefs.getString("UserName", "").equals("")) {
                username = mPrefs.getString("UserName", "");
                userid = mPrefs.getString("UserId", "");
            }
        }

        groups = new ArrayList<>(100);

        ListView listView = (ListView) findViewById(R.id.GroupList);
        listView.setAdapter(new SimpleArrayAdapter());
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                groupClicked(groups.get(position));
            }
        });
    }

    @Override
    protected void onStart(){
        super.onStart();
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
        startActivity(new Intent(this, MainActivity.class).putExtra("Logout",true));
    }

    public void createGroup(){
        startActivity(new Intent(this, CreateGroupActivity.class).putExtra("User", currentUser));
    }

    public void fetchGroupsFromAPI(){
        AsyncHttpClient client = new AsyncHttpClient();
        try{
            URL url = new URL(new String("http://cs3300.elasticbeanstalk.com/users/"+userid+"/groups" ));
            client.get(url.toString(), new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    try {
                        parseGroups(new JSONArray(new String(responseBody)));
                    } catch (Exception e) {
                        e.printStackTrace();
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
        ListView listView = (ListView) findViewById(R.id.GroupList);
        ((ArrayAdapter) listView.getAdapter()).notifyDataSetChanged();
    }

    private void groupClicked(Group g){
        g.viewedUnreadMessages();
        Intent intent = new Intent(this, GroupActivity.class);
        intent.putExtra("Group", g);
        startActivity(intent);
    }

    private class SimpleArrayAdapter extends ArrayAdapter<Group>{

        public SimpleArrayAdapter(){
            super(getApplicationContext(), -1, groups);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) getApplicationContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.group_list_item, parent, false);
            TextView groupNameView = (TextView) rowView.findViewById(R.id.GroupNameText);
            TextView dateView = (TextView) rowView.findViewById(R.id.DateText);

            Group g = groups.get(position);

            if(g.getGroupName() != null){
                groupNameView.setText(g.getGroupName());
            }

            if(g.getMostRecentMessage() != null && g.getMostRecentMessage().date != null) {
                Date d = g.getMostRecentMessage().date;

                String dateString = new SimpleDateFormat("MM-dd-yyyy").format(d);
                dateView.setText(dateString);
            }
            return rowView;
        }
    }
}
