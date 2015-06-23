package gatech.cs3300.watchchat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONObject;

import java.net.URL;

public class GroupPreferencesActivity extends ActionBarActivity implements AddGroupMemberDialog.AddGroupMemberDialogListener, ConfirmLeaveGroupDialog.ConfirmLeaveGroupDialogListener {

    // Views
    private Button mGroupAddMemberButton;
    private ListView mGroupMembersListView;
    private Button mLeaveGroupButton;
    private ProgressBar mGroupActivityIndicator;

    // Adapter
    private String[] mGroupMemberNames;
    private GroupMembersAdapter mGroupMemberAdapter;

   private String[] testGroupNames() {
       return new String[]{"Billy", "Susan", "Dirk"};
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_preferences);

        mGroupAddMemberButton = (Button) findViewById(R.id.group_add_member_button);
        mGroupMembersListView = (ListView) findViewById(R.id.group_members_list_view);
        mGroupActivityIndicator = (ProgressBar) findViewById(R.id.group_activity_indicator);

        mGroupAddMemberButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addMemberDialog();
            }
        });

        mGroupMemberAdapter = new GroupMembersAdapter(getApplicationContext(), testGroupNames() );
        mGroupMembersListView.setAdapter(mGroupMemberAdapter);

        mGroupActivityIndicator.setVisibility(View.GONE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_group_preferences, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.LeaveGroupButton:
                leaveGroupDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void refreshGroupMembers() {
        mGroupMemberAdapter.setMemberNames(mGroupMemberNames);
    }

    private void addMemberDialog() {
        AddGroupMemberDialog dialog = new AddGroupMemberDialog();
        dialog.setListener(this);
        dialog.show(getFragmentManager(), "add_group_member_dialog");
    }

    private void leaveGroupDialog() {
        ConfirmLeaveGroupDialog dialog = new ConfirmLeaveGroupDialog();
        dialog.setListener(this);
        dialog.show(getFragmentManager(), "confirm_leave_group_dialog");
    }

    // Listeners

    public void addGroupMemberWithName(String name) {
        mGroupActivityIndicator.setVisibility(View.VISIBLE);
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("groupId", 124387410);
        params.put("userId", 3548);
        try{
            URL url = new URL("http://cs3300.elasticbeanstalk.com" + "/groups/add");
            client.post(url.toString(), params, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    try {
                        JSONObject response = new JSONObject(new String(responseBody));
                        System.out.println(response);
                        if(response.getString("groupName").equals("-1")){
                            Toast.makeText(getApplicationContext(), "Error adding user to the group!",
                                    Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Log.d("groupName", response.getString("groupName"));
                            startGroupActivity(response.getString("groupName"), response.getString("groupId"));
                        }
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), "Parsing Error!",
                                Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody,
                                      Throwable error) {
                    Log.e("Adding user", error.getLocalizedMessage());
                    error.printStackTrace(System.out);
                    Toast.makeText(getApplicationContext(), "Network Error!",
                            Toast.LENGTH_SHORT).show();
                }
            });
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    public void leaveGroup() {
        mGroupActivityIndicator.setVisibility(View.VISIBLE);
    }

    public void startGroupActivity(String username, String userId){
        Intent intent = new Intent(this, GroupActivity.class);
        intent.putExtra("UserId", userId)
                .putExtra("Username", username);
        startActivity(intent);
    }
}
