package gatech.cs3300.watchchat;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

public class CreateGroupActivity extends ActionBarActivity {

    // Views
    private EditText mGroupNameTextField;
    private Button mCreateGroupButton;
    private ProgressBar mCreateGroupActivityIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);

        mGroupNameTextField = (EditText) findViewById(R.id.group_name_text_field);
        mCreateGroupButton = (Button) findViewById(R.id.create_group_button);
        mCreateGroupActivityIndicator = (ProgressBar) findViewById(R.id.create_group_activity_indicator);

        mCreateGroupActivityIndicator.setVisibility(View.GONE);
        mCreateGroupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptCreateGroup();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create_group, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void attemptCreateGroup() {
        mGroupNameTextField.setError(null);
        String groupName = mGroupNameTextField.getText().toString();

        if (TextUtils.isEmpty(groupName)) {
            mGroupNameTextField.setError(getString(R.string.error_group_name_required));
            mGroupNameTextField.requestFocus();
        } else if (!isGroupNameValid(groupName)) {
            mGroupNameTextField.setError(getString(R.string.error_group_name_invalid));
            mGroupNameTextField.requestFocus();
        } else {
            performCreateGroup(groupName);
        }
    }

    private void performCreateGroup(String groupName) {
        mCreateGroupActivityIndicator.setVisibility(View.VISIBLE);
    }

    private Boolean isGroupNameValid(String groupName) {
        return !TextUtils.isEmpty(groupName);
    }
}
