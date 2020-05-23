package cat.udl.urbandapp.dialogs;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;

import cat.udl.urbandapp.DefaultActivity;
import cat.udl.urbandapp.R;
import cat.udl.urbandapp.models.RolEnum;
import cat.udl.urbandapp.viewmodel.UserViewModel;

public class DialogRolChooser extends DialogFragment {

    public View rootView;
    private FragmentActivity activity;
    private UserViewModel userViewModel;
    private ImageView usuari;
    private ImageView banda;
    private ImageView patrocinador;
    private RolEnum _rol;


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
        usuari.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                usuari.setBackgroundColor(R.color.primaryLightColor);
                _rol = RolEnum.SOLO;
            }
        });
        banda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _rol = RolEnum.BAND;
            }
        });
        patrocinador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _rol = RolEnum.SPONSOR;
            }
        });

        builder.setPositiveButton(" Configura tu perfil por primera vez!", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                DialogSetProfileStep1 dialogstep1 = new DialogSetProfileStep1().newInstance(getActivity());
                dialogstep1.show(getActivity().getSupportFragmentManager(), "STEP 1");
                //cridarem dialog rol
                if (_rol == null){
                    Toast.makeText(getContext(),"Selecciona un rol de usuario!", Toast.LENGTH_SHORT).show();
                }
                userViewModel.setUserRol(_rol);
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
        usuari = rootView.findViewById(R.id.button_user);
        banda = rootView.findViewById(R.id.button_band);
        patrocinador = rootView.findViewById(R.id.button_sponsor);


    }
}







