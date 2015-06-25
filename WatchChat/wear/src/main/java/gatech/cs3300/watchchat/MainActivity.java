package gatech.cs3300.watchchat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;

public class MainActivity extends Activity {

    static final int SELECT_REPLY_REQUEST = 0;
    static final int SELECT_GROUP_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                // TODO: Get active group name
                TextView groupText = (TextView) findViewById(R.id.groupText);
                groupText.setText("Test");

                // TODO: Get latest message from group
                TextView messageText = (TextView) findViewById(R.id.messageText);
                messageText.setText("Message");

                Button replyButton = (Button) findViewById(R.id.replyButton);
                replyButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent replyScreen = new Intent(MainActivity.this, ReplyActivity.class);
                        startActivityForResult(replyScreen, SELECT_REPLY_REQUEST);
                    }
                });

                Button groupButton = (Button) findViewById(R.id.groupButton);
                groupButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent groupScreen = new Intent(MainActivity.this, GroupListActivity.class);
                        startActivityForResult(groupScreen, SELECT_GROUP_REQUEST);
                    }
                });
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SELECT_REPLY_REQUEST) {
            if (resultCode == RESULT_OK) {
                // TODO: Call API to send message
                //int replyNumber = data.getIntExtra("reply_number", -1);
                String replyText = data.getStringExtra("reply_text");

                TextView messageText = (TextView) findViewById(R.id.messageText);
                messageText.setText(replyText);
            }
        }

        if (requestCode == SELECT_GROUP_REQUEST) {
            if (resultCode == RESULT_OK) {
                String groupName = data.getStringExtra("group_name");

                // Set group name to selected group
                TextView groupText = (TextView) findViewById(R.id.groupText);
                groupText.setText(groupName);

                // Get the latest message from that group
                TextView messageText = (TextView) findViewById(R.id.messageText);
                String message;

                // TODO: Call API to get latest message
                switch (groupName) {
                    case "Group 1": message = "That's awesome!";
                        break;

                    case "Group 2": message = "Hey, what's up?";
                        break;

                    case "Group 3": message = "Wanna grab lunch?";
                        break;

                    case "Group 4": message = "Have you finished the project yet?";
                        break;

                    case "Group 5": message = "iOS has the superior mobile development ecosystem.";
                        break;

                    default: message = "I hate Android Wear.";
                        break;
                }

                messageText.setText(message);
            }
        }
    }
}
