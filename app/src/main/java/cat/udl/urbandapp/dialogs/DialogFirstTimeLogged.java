package cat.udl.urbandapp.dialogs;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import cat.udl.urbandapp.DefaultActivity;
import cat.udl.urbandapp.R;
import cat.udl.urbandapp.UserProfileActivity;

public class DialogFirstTimeLogged extends DialogFragment {

    public View rootView;
    private Activity activity;

    public static DialogFirstTimeLogged newInstance(DefaultActivity activity) {
        DialogFirstTimeLogged dialog = new DialogFirstTimeLogged();
        dialog.activity = activity;
        return dialog;
    }

    @NonNull @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        initView();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setPositiveButton(" Configura tu perfil por primera vez!", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                DialogSetProfileStep1 dialogstep1 = new DialogSetProfileStep1().newInstance(getActivity());
                dialogstep1.show(getActivity().getSupportFragmentManager(),"STEP 1");
            }
        });

        // Set other dialog properties

        AlertDialog alertDialog = builder.setView(rootView)
                .setCancelable(true)
                .create();


        alertDialog.setCanceledOnTouchOutside(false);
        return alertDialog;
    }

    //inicialtizar la vista del dialog
    private void initView() {
        rootView = LayoutInflater.from
                (getContext()).inflate(R.layout.dialog_first_time_logged, null, false);
    }
}

