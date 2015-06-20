package gatech.cs3300.watchchat;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

public class ConfirmLeaveGroupDialog extends DialogFragment {

    public interface ConfirmLeaveGroupDialogListener {
        public void leaveGroup();
    }

    private ConfirmLeaveGroupDialogListener mListener;

    public void setListener(ConfirmLeaveGroupDialogListener listener) {
        mListener = listener;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.confirm_leave_group_message)
                // Add action buttons
                .setPositiveButton(R.string.add_group_member, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        if (mListener != null) {
                            mListener.leaveGroup();
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
