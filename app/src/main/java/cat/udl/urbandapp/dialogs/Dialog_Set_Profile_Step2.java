package cat.udl.urbandapp.dialogs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import cat.udl.urbandapp.DefaultActivity;
import cat.udl.urbandapp.R;

public class Dialog_Set_Profile_Step2 extends DialogFragment {

    public View rootView;
    private FragmentActivity activity;
    private Button nextStep;

    public static Dialog_Set_Profile_Step2 newInstance(FragmentActivity activity) {
        Dialog_Set_Profile_Step2 dialog = new Dialog_Set_Profile_Step2();
        dialog.activity = activity;
        return dialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        initView();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setPositiveButton("Next Step", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Dialog_Set_Profile_Step3 dialog3 = Dialog_Set_Profile_Step3.newInstance(getActivity());
                dialog3.show(getParentFragmentManager(),"probando");
            }
        });
        builder.setNegativeButton("Prevous Step", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Dialog_Set_Profile_Step1 dialog1 = Dialog_Set_Profile_Step1.newInstance(getActivity());
                dialog1.show(getParentFragmentManager(),"probando");
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
                (getContext()).inflate(R.layout.dialog_set_profile_step2, null, false);
    }
}

