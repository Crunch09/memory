package de.thm.ateam.memory.network;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import de.thm.ateam.memory.R;

/**
 * 
 * AlertDialog which can be shown if there occured an error
 *
 */
public class MyAlertDialog extends DialogFragment {

  private int messageId;

  public interface MyAlertDialogListener {
    void onFinishAlertDialog();
  }

  public MyAlertDialog(int messageId) {
    this.messageId = messageId;
  }

  @Override
  public Dialog onCreateDialog(Bundle savedInstanceState) {

    return new AlertDialog.Builder(getActivity())
    .setIcon(android.R.drawable.ic_dialog_alert)
    .setTitle(R.string.sorry)
    .setMessage(messageId)
    .setCancelable(false)
    .setPositiveButton(R.string.ok,
        new DialogInterface.OnClickListener() {
      public void onClick(DialogInterface dialog, int whichButton) {
        dialog.dismiss();
        MyAlertDialogListener activity = (MyAlertDialogListener) getActivity();
        activity.onFinishAlertDialog();
      }
    }
        )
        .create();
  }
}
