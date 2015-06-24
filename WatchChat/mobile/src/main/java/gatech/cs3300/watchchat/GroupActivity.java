package gatech.cs3300.watchchat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Date;

public class GroupActivity extends AppCompatActivity {

    private ListView mMessagesList;
    private GroupMessagesAdapter mMessagesAdapter;
    private EditText mComposeView;
    private Button mPostButton;

    private ArrayList<Message> mMessages = new ArrayList<>();

    private Group g;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);

        mMessages = new ArrayList<>();
        mMessagesList = (ListView) findViewById(R.id.messages_list);
        mPostButton = (Button) findViewById(R.id.post_button);
        mComposeView = (EditText) findViewById(R.id.message_text_field);

        if(getIntent().hasExtra("Group")) {
            g = (Group) (getIntent().getParcelableExtra("Group"));
        }

        if(g != null) {
            setTitle(g.getGroupName());
        }

        mMessagesAdapter = new GroupMessagesAdapter(getBaseContext(), mMessages);
        Message m = new Message("hello", "tom", new Date(), g);
        m.received = true;
        mMessages.add(m);
        mMessagesList.setAdapter(mMessagesAdapter);

        mPostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                post();
            }
        });

    }

    private void post() {
        String text = mComposeView.getText().toString();
        Message m = new Message(text, "me", new Date(), g);
        m.received = false;
        mMessagesAdapter.addMessage(m);
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
