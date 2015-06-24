package gatech.cs3300.watchchat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                TextView groupText = (TextView) findViewById(R.id.groupText);
                groupText.setText("Test");

                TextView messageText = (TextView) findViewById(R.id.messageText);
                messageText.setText("Message");

                Button replyButton = (Button) findViewById(R.id.replyButton);
                replyButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent replyScreen = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(replyScreen);
                    }
                });

                Button groupButton = (Button) findViewById(R.id.groupButton);
                replyButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
            }
        });


    }
}
