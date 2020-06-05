package cat.udl.urbandapp.dialogs;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.LifecycleOwner;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import cat.udl.urbandapp.services.ProfileSetUpServiceImpl;
import cat.udl.urbandapp.viewmodel.ProfileSetUpViewModel;
import cat.udl.urbandapp.views.DefaultActivity;
import cat.udl.urbandapp.R;
import cat.udl.urbandapp.viewmodel.UserViewModel;

public class DialogFirstTimeLogged extends DialogFragment implements LifecycleOwner {

    public View rootView;
    private Activity activity;
    private EditText editText_username;
    private ProfileSetUpViewModel profileSetUpViewModel;

    public static DialogFirstTimeLogged newInstance(DefaultActivity activity) {
        DialogFirstTimeLogged dialog = new DialogFirstTimeLogged();
        dialog.activity = activity;
        return dialog;
    }

    @NonNull @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        profileSetUpViewModel = new ProfileSetUpViewModel(getActivity().getApplication());
        initView();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setPositiveButton(" Configura tu perfil por primera vez!", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                String username = editText_username.getText().toString();
                profileSetUpViewModel.setUsername(username);
                DialogRolChooser dialogRol = new DialogRolChooser().newInstance(getActivity());
                dialogRol.show(getActivity().getSupportFragmentManager(),"Rol");

            }
        });


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
        editText_username = rootView.findViewById(R.id.editText_username);
    }

}

