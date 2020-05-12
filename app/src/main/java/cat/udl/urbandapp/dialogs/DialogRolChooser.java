package cat.udl.urbandapp.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

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
    private RadioGroup radioGroup;

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
                String _rol = "";
                if(usuari.isChecked()){
                    Log.d("KELOKEEE","YAFUEE");
                    _rol = "SOLO";
                }else if (patrocinador.isChecked()){
                    _rol = "SPONSOR";
                }
                Log.d("KELOKEEE", _rol);
                userViewModel.setUserRol(_rol);
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
        usuari = rootView.findViewById(R.id.radioButton_user__rol);
        banda = rootView.findViewById(R.id.radioButton_band_rol);
        patrocinador = rootView.findViewById(R.id.radioButton_sponsor_rol);
        radioGroup = rootView.findViewById(R.id.radioGroup_rol);

    }
}







