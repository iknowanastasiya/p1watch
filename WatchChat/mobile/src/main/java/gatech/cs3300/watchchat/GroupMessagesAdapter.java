package gatech.cs3300.watchchat;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

public class GroupMessagesAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<Message> values;

    public GroupMessagesAdapter(Context context, ArrayList<Message> values) {
        super();
        mContext = context;
        this.values = values;
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

        view.setMessage(values.get(position));

        return view;
    }
}
