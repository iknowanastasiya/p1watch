package gatech.cs3300.watchchat;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

public class GroupMessagesAdapter extends BaseAdapter {

    public interface GroupMessagesListener {
        public void openURL(String url);
    }

    private Context mContext;
    private ArrayList<Message> values;
    private GroupMessagesListener mListener;

    public GroupMessagesAdapter(Context context, ArrayList<Message> values) {
        super();
        mContext = context;
        this.values = new ArrayList<Message>(values);
    }

    public void addMessage(Message m) {
        values.add(m);
        notifyDataSetChanged();
    }

    public void setListener(GroupMessagesListener listener) {
        mListener = listener;
    }

    @Override
    public int getCount() {
        return values.size();
    }

    @Override
    public Object getItem(int position) {
        return values.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        MessageView view = (MessageView) convertView;
        if (view == null) {
            view = new MessageView(mContext);
        }

        final Message message = values.get(position);
        view.setMessage(message);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (message.documentURL != null && mListener != null) {
                    mListener.openURL(message.documentURL);
                }
            }
        });

        return view;
    }
}
