package gatech.cs3300.watchchat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.util.Log;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONObject;

import java.net.URL;

public class CreateGroupActivity extends ActionBarActivity {

    // Views
    private EditText mGroupNameTextField;
    private Button mCreateGroupButton;
    private ProgressBar mCreateGroupActivityIndicator;
    APIController apiController;

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
        //apiController = new APIController();
        //apiController.createGroup(groupName);

        //startActivity(new Intent(this, GroupsActivity.class));
        EditText mGroupNameTextField = (EditText) findViewById(R.id.group_name_text_field);
        if (TextUtils.isEmpty(mGroupNameTextField.getText())) {
            Toast.makeText(getApplicationContext(), "Empty Username!", Toast.LENGTH_SHORT).show();
        } else {
            final Intent intent = new Intent(this, GroupsActivity.class);
            final String groupname = mGroupNameTextField.getText().toString();
            System.out.println(groupname);
            AsyncHttpClient client = new AsyncHttpClient();
            RequestParams params = new RequestParams();
            params.put("groupname", groupname);
            try {
                URL url = new URL(new String("http://cs3300.elasticbeanstalk.com" + "/groups/create"));
                client.post(url.toString(), params, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        try {
                            JSONObject response = new JSONObject(new String(responseBody));
                            System.out.println(response);
                            Log.d("groupId", response.getString("groupId"));
                          //  int createdGroupId = response.getInt("groupId");
                           // System.out.println(createdGroupId);
                            startGroupActivity(response.getString("groupName"), response.getString("groupId"));
                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), "Parsing Error!",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody,
                                          Throwable error) {
                        Log.e("Create Group", error.getLocalizedMessage());
                        //error.printStackTrace(System.out);
                        Toast.makeText(getApplicationContext(), "Network Error!",
                                Toast.LENGTH_SHORT).show();
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private Boolean isGroupNameValid(String groupName) {
        return !TextUtils.isEmpty(groupName);
    }

    public void startGroupActivity(String groupname, String groupId) {
        Intent intent = new Intent(this, GroupActivity.class);
        intent.putExtra("groupId", groupId)
                .putExtra("groupName", groupname);
        startActivity(intent);
    }
}

