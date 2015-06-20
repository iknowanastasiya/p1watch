package gatech.cs3300.watchchat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class GroupMembersAdapter extends ArrayAdapter<String> {

    private String[] values;

    public GroupMembersAdapter(Context context, String[] values) {
        super(context, -1, values);
        this.values = values;
    }

    public void setMemberNames(String[] names) {
        this.values = values;
        notifyDataSetChanged();
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.group_member_row, parent, false);
        }
        TextView textView = (TextView) convertView.findViewById(R.id.group_member_name);
        textView.setText(values[position]);

        return convertView;
    }

}
