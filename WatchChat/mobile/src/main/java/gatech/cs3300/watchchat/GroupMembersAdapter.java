package gatech.cs3300.watchchat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class GroupMembersAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<String> mValues;

    public GroupMembersAdapter(Context context, ArrayList<String> values) {
        mContext = context;
        mValues = values;
    }

    public void addMember(String member) {
        mValues.add(member);
        notifyDataSetChanged();
    }

    public void setMemberNames(ArrayList<String> members) {
        mValues = new ArrayList<String>(members);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mValues.size();
    }

    @Override
    public Object getItem(int position) {
        return mValues.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.group_member_row, parent, false);
        }
        TextView textView = (TextView) convertView.findViewById(R.id.group_member_name);
        textView.setText(mValues.get(position));

        return convertView;
    }

}
