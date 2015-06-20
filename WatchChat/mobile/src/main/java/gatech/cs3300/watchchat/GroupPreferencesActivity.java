package gatech.cs3300.watchchat;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;

public class GroupPreferencesActivity extends ActionBarActivity implements AddGroupMemberDialog.AddGroupMemberDialogListener, ConfirmLeaveGroupDialog.ConfirmLeaveGroupDialogListener {

    // Views
    private Button mGroupAddMemberButton;
    private ListView mGroupMembersListView;
    private Button mLeaveGroupButton;
    private ProgressBar mLeaveGroupActivityIndicator;

    // Adapter
    private String[] mGroupMemberNames;
    private GroupMembersAdapter mGroupMemberAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_preferences);

        mGroupAddMemberButton = (Button) findViewById(R.id.group_add_member_button);
        mGroupMembersListView = (ListView) findViewById(R.id.group_members_list_view);
        mLeaveGroupButton = (Button) findViewById(R.id.leave_group_button);
        mLeaveGroupActivityIndicator = (ProgressBar) findViewById(R.id.leave_group_activity_indicator);

        mGroupAddMemberButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addMemberDialog();
            }
        });

        mGroupMemberAdapter = new GroupMembersAdapter(getApplicationContext(), null);
        mGroupMembersListView.setAdapter(mGroupMemberAdapter);

        mLeaveGroupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                leaveGroupDialog();
            }
        });

        mLeaveGroupActivityIndicator.setVisibility(View.GONE);
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
        refreshGroupMembers();
    }

    public void leaveGroup() {

    }

}
