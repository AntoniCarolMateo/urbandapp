package cat.udl.urbandapp.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;

import cat.udl.urbandapp.DefaultActivity;
import cat.udl.urbandapp.R;
import cat.udl.urbandapp.viewmodel.UserViewModel;

public class DialogRolChooser extends DialogFragment {

    public View rootView;
    private FragmentActivity activity;
    private UserViewModel userViewModel;
    private RadioButton usuari;
    private RadioButton banda;
    private RadioButton patrocinador;

    public static DialogRolChooser newInstance(FragmentActivity activity) {
        DialogRolChooser dialog = new DialogRolChooser();
        dialog.activity = activity;
        return dialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        userViewModel = new UserViewModel(getActivity().getApplication());
        initView();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setPositiveButton(" Configura tu perfil por primera vez!", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                if (usuari.isChecked()){
                    userViewModel.setUserRol("SOLO");
                } else if(banda.isChecked()){
                    userViewModel.setUserRol("BAND");
                } else if(patrocinador.isChecked()){
                    userViewModel.setUserRol("PARTNER");
                }
                DialogSetProfileStep1 dialogstep1 = new DialogSetProfileStep1().newInstance(getActivity());
                dialogstep1.show(getActivity().getSupportFragmentManager(), "STEP 1");
                //cridarem dialog rol
            }
        });
        AlertDialog alertDialog = builder.setView(rootView)
                .setCancelable(true)
                .create();


        alertDialog.setCanceledOnTouchOutside(false);
        return alertDialog;
    }

    private void initView() {
        rootView = LayoutInflater.from
                (getContext()).inflate(R.layout.dialog_rol_chooser, null, false);
    }
}







