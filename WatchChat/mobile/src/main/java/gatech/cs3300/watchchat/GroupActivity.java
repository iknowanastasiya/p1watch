package gatech.cs3300.watchchat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;

public class GroupActivity extends ActionBarActivity {

    private ListView mMessagesList;
    private GroupMessagesAdapter mMessagesAdapter;

    private ArrayList<GroupMessage> mMessages = new ArrayList<GroupMessage>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);

        mMessages = new ArrayList<GroupMessage>();
        mMessages.add(GroupMessage.received("Hello, World"));
        mMessages.add(GroupMessage.sent("Hello, World"));

        mMessagesAdapter = new GroupMessagesAdapter(getBaseContext(), mMessages);

        mMessagesList = (ListView) findViewById(R.id.messages_list);
        mMessagesList.setAdapter(mMessagesAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_group, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.GroupPreferencesButton) {
            startActivity(new Intent(this, GroupPreferencesActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
