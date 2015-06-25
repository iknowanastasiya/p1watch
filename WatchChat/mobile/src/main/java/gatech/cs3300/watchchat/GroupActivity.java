package gatech.cs3300.watchchat;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.RemoteInput;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
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
    // Key for the string that's delivered in the action's intent
    private static final String EXTRA_VOICE_REPLY = "extra_voice_reply";

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
            g = getIntent().getParcelableExtra("Group");
        }

        if(g != null) {
            setTitle(g.getGroupName());
        }

        Message m = new Message("hi", "tom", new Date(), g);
        m.received = true;
        mMessages.add(m);
        m = new Message("whats up", "tom", new Date(), g);
        m.received = false;
        mMessages.add(m);
        m = new Message("nm", "tom", new Date(), g);
        m.received = true;
        mMessages.add(m);
        m = new Message("k", "tom", new Date(), g);
        m.received = false;
        mMessages.add(m);
        mMessagesAdapter = new GroupMessagesAdapter(getBaseContext(), mMessages);

        mMessagesList.setAdapter(mMessagesAdapter);
        mMessagesList.setDivider(null);

        mPostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                post();
            }
        });

        if(RemoteInput.getResultsFromIntent(getIntent()) != null){
            post(RemoteInput.getResultsFromIntent(getIntent()).getCharSequence(EXTRA_VOICE_REPLY).toString());
        }
    }

    private void post(String text){
        Message m = new Message(text, "me", new Date(), g);
        m.received = false;
        mMessagesAdapter.addMessage(m);
    }

    private void post() {
        String text = mComposeView.getText().toString();
        Message m = new Message(text, "me", new Date(), g);
        mComposeView.setText("");
        m.received = false;
        mMessagesAdapter.addMessage(m);
        makeTestNotification(text);
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
            Intent intent = new Intent(this, GroupPreferencesActivity.class);
            intent.putExtra("Group", g);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void makeTestNotification(String text){
        // Creates an explicit intent for an Activity in your app
        Intent resultIntent = new Intent(this, GroupActivity.class).putExtra("Group", g);

        // The stack builder object will contain an artificial back stack for the
        // started Activity.
        // This ensures that navigating backward from the Activity leads out of
        // your application to the Home screen.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        // Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(GroupActivity.class);
        // Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        RemoteInput remoteInput = new RemoteInput.Builder(EXTRA_VOICE_REPLY)
                .setLabel("Reply...")
                .setChoices(getResources().getStringArray(R.array.reply_choices))
                .build();
        NotificationCompat.Action action  =
                new NotificationCompat.Action.Builder(R.drawable.ic_reply_white_24dp, "Reply", resultPendingIntent)
                .addRemoteInput(remoteInput)
                .build();

        NotificationCompat.Builder mBuilder =
                (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_user_white)
                        .setContentTitle(g.getGroupName())
                        .setContentText(text)
                        .setAutoCancel(true)
                        .extend(new NotificationCompat.WearableExtender().addAction(action));

        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        // mId allows you to update the notification later on.
        int mId = 0;
        mNotificationManager.notify(mId, mBuilder.build());
    }
}
