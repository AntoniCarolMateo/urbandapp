package cat.udl.urbandapp.dialogs;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;

import cat.udl.urbandapp.R;
import cat.udl.urbandapp.models.RolEnum;
import cat.udl.urbandapp.viewmodel.ProfileSetUpViewModel;
import cat.udl.urbandapp.viewmodel.UserViewModel;

public class DialogRolChooser extends DialogFragment {

    public View rootView;
    private FragmentActivity activity;
    private ProfileSetUpViewModel profileSetUpViewModel;
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
        profileSetUpViewModel = new ProfileSetUpViewModel(getActivity().getApplication());

        initView();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setPositiveButton(" Configura tu perfil por primera vez!", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                //cridarem dialog rol
                if (_rol == null){
                    Toast.makeText(getContext(),"Selecciona un rol de usuario!", Toast.LENGTH_SHORT).show();
                }
                profileSetUpViewModel.setUserRol(_rol);

                DialogSetProfileStep1 dialogstep1 = new DialogSetProfileStep1().newInstance(getActivity());
                dialogstep1.show(getActivity().getSupportFragmentManager(), "STEP 1");


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

        _rol = RolEnum.user;
        usuari.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                rolSelected(RolEnum.user);
            }
        });
        banda.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                rolSelected(RolEnum.band);
            }
        });
        patrocinador.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                rolSelected(RolEnum.sponsor);
            }
        });


    }

    private void rolSelected(RolEnum rol) {
        switch (rol){
            case user:
                patrocinador.setBackgroundResource(R.color.primaryTextColor);
                banda.setBackgroundResource(R.color.primaryTextColor);
                usuari.setBackgroundResource(R.color.primaryLightColor);
                _rol = RolEnum.user;
                break;
            case band:
                patrocinador.setBackgroundResource(R.color.primaryTextColor);
                usuari.setBackgroundResource(R.color.primaryTextColor);
                banda.setBackgroundResource(R.color.primaryLightColor);
                _rol = RolEnum.band;
                break;
            case sponsor:
                usuari.setBackgroundResource(R.color.primaryTextColor);
                banda.setBackgroundResource(R.color.primaryTextColor);
                patrocinador.setBackgroundResource(R.color.primaryLightColor);
                _rol = RolEnum.sponsor;
                break;
        }
    }
}







