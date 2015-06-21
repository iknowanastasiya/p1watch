package gatech.cs3300.watchchat;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;

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
    }

    public void leaveGroup() {
        mGroupActivityIndicator.setVisibility(View.VISIBLE);
    }

}
