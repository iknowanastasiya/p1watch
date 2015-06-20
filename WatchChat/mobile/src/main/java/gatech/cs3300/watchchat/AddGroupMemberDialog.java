package gatech.cs3300.watchchat;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

public class AddGroupMemberDialog extends DialogFragment {

    public interface AddGroupMemberDialogListener {
        public void addGroupMemberWithName(String name);
    }

    private AddGroupMemberDialogListener mListener;

    public void setListener(AddGroupMemberDialogListener listener) {
        mListener = listener;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.dialog_add_group_member, null);
        final EditText groupMemberNameEditText = (EditText) view.findViewById(R.id.group_member_name_text_field);

        builder.setView(view)
                // Add action buttons
                .setPositiveButton(R.string.add_group_member, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        String name = groupMemberNameEditText.getText().toString();
                        if (mListener != null) {
                            mListener.addGroupMemberWithName(name);
                        }
                    }
                })
                .setNegativeButton(R.string.cancel_add_group_member, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });

        return builder.create();
    }

}
