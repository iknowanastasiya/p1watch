package gatech.cs3300.watchchat;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.support.wearable.view.WearableListView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import java.util.ArrayList;

public class ReplyActivity extends Activity implements WearableListView.ClickListener {

    private WearableListView mListView;
    private ArrayList<String> listItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reply);

        listItems = new ArrayList();
        listItems.add("Yes");
        listItems.add("No");
        listItems.add("Maybe");
        listItems.add("Hey!");
        listItems.add("Just a second.");
        listItems.add("Sorry, I'm driving.");
        listItems.add("What?");

        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                mListView = (WearableListView) stub.findViewById(R.id.listView);
                mListView.setAdapter(new ListAdapter(ReplyActivity.this));
                mListView.setClickListener(ReplyActivity.this);

                Button cancelButton = (Button) findViewById(R.id.cancelButton);
                cancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setResult(Activity.RESULT_CANCELED);
                        finish();
                    }
                });
            }
        });
    }

    @Override
    public void onClick(WearableListView.ViewHolder viewHolder) {
        int selectedIndex = viewHolder.getLayoutPosition();
        Intent resultIntent = new Intent();
        resultIntent.putExtra("reply_text", listItems.get(selectedIndex));
        //resultIntent.putExtra("reply_number", selectedIndex);
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }

    @Override
    public void onTopEmptyRegionClick() {
    }

    private class ListAdapter extends WearableListView.Adapter {
        private final LayoutInflater mInflater;

        private ListAdapter(Context context) {
            mInflater = LayoutInflater.from(context);
        }

        @Override
        public WearableListView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new WearableListView.ViewHolder(mInflater.inflate(R.layout.list_item, null));
        }

        @Override
        public void onBindViewHolder(WearableListView.ViewHolder holder, int position) {
            TextView view = (TextView) holder.itemView.findViewById(R.id.textView);
            view.setText(listItems.get(position).toString());
            holder.itemView.setTag(position);
        }

        @Override
        public int getItemCount() {
            return listItems.size();
        }
    }
}
