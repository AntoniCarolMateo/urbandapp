package cat.udl.urbandapp.dialogs;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;


import android.content.pm.PackageManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import cat.udl.urbandapp.DefaultActivity;
import cat.udl.urbandapp.R;
import cat.udl.urbandapp.viewmodel.UserViewModel;

public class DialogSetProfileStep1 extends DialogFragment {

    public final int REQUEST_CODE_CAMERA = 1;
    public View rootView;
    private Activity activity;
    private UserViewModel viewModel;
    private EditText name;
    private EditText surname;
    private RatingBar generalExp;
    private Button editProfilePicture;

    public static DialogSetProfileStep1 newInstance(DefaultActivity activity) {
        DialogSetProfileStep1 dialog = new DialogSetProfileStep1();
        dialog.activity = activity;
        return dialog;
    }

    public static DialogSetProfileStep1 newInstance(FragmentActivity activity) {
        DialogSetProfileStep1 dialog = new DialogSetProfileStep1();
        dialog.activity = activity;
        return dialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        initView();
        viewModel = new UserViewModel(getActivity().getApplication());
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setPositiveButton("Next Step", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                    String _name = name.getText().toString();
                    String _surname = surname.getText().toString();
                    int _exp = (int) generalExp.getRating();
                Toast.makeText(getContext(), _name +"  "+ _surname+"  "+_exp, Toast.LENGTH_SHORT).show();
                    //Acutalitzem els valors en la api
                    viewModel.setProfileName(_name);
                    viewModel.setProfileSurname(_surname);
                    viewModel.setProfileGenExp(_exp);
                    DialogSetProfileStep2 step2 = new DialogSetProfileStep2();
                    step2.show(getParentFragmentManager(), "probando");
                }

        });


        // Set other dialog properties

        AlertDialog alertDialog = builder.setView(rootView)
                .setCancelable(true)
                .create();

        editProfilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: abrir dialogo que pida permisos de la cámara

            }
        });
        alertDialog.setCanceledOnTouchOutside(false);
        return alertDialog;
    }



    //inicialtizar la vista del dialog
    private void initView() {
        rootView = LayoutInflater.from
                (getContext()).inflate(R.layout.dialog_set_profile_step1, null, false);
        editProfilePicture = rootView.findViewById(R.id.button_edit_picture);
        generalExp = rootView.findViewById(R.id.ratingBar_gen_exp);
        name = rootView.findViewById(R.id.editText_name);
        surname = rootView.findViewById(R.id.editText_surname);


    }

}
