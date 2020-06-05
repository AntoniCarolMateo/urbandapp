package cat.udl.urbandapp.dialogs;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RatingBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cat.udl.urbandapp.viewmodel.ProfileSetUpViewModel;
import cat.udl.urbandapp.views.DefaultActivity;
import cat.udl.urbandapp.R;
import cat.udl.urbandapp.viewmodel.UserViewModel;

public class DialogSetProfileStep1 extends DialogFragment implements View.OnClickListener , LifecycleOwner {

    public final int REQUES_ID_MULTIPLE_PERMISIONS = 3;
    public View rootView;

    Context setProfileStep1;

    private Activity activity;
    private ProfileSetUpViewModel profileSetUpViewModel;
    private EditText name;
    private EditText calendar;
    private EditText surname;
    private EditText description;
    private RatingBar generalExp;
    private RadioButton radio_male;
    private RadioButton radio_female;

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
        profileSetUpViewModel = new ProfileSetUpViewModel(getActivity().getApplication());
        setProfileStep1 = this.getContext();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setPositiveButton("Next Step", new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            public void onClick(DialogInterface dialog, int id) {
                setProfileAttr();
                DialogSetProfileStep2 step2 = new DialogSetProfileStep2();
                step2.show(getParentFragmentManager(), "step 2");
            }
        });


        // Set other dialog properties

        AlertDialog alertDialog = builder.setView(rootView)
                .setCancelable(true)
                .create();

        alertDialog.setCanceledOnTouchOutside(false);
        return alertDialog;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setProfileAttr() {
        String _name = name.getText().toString();
        String _surname = surname.getText().toString();
        float _exp = generalExp.getRating();
        String _birth = calendar.getText().toString();
        String _gender = "";
        String _description = description.getText().toString();
        if (radio_female.isChecked()) {
            _gender = "FEMALE";
        } else if (radio_male.isChecked()) {
            _gender = "MALE";
        }

        profileSetUpViewModel.setProfileInfo(_name, _surname, _exp, _birth, _gender, _description);
    }


    //inicialtizar la vista del dialog
    private void initView() {
        rootView = LayoutInflater.from
                (getContext()).inflate(R.layout.dialog_set_profile_step1, null, false);
        generalExp = rootView.findViewById(R.id.ratingBar_gen_exp);
        name = rootView.findViewById(R.id.editText_name);
        surname = rootView.findViewById(R.id.editText_surname);
        radio_female = rootView.findViewById(R.id.radioButton_female);
        radio_male = rootView.findViewById(R.id.radioButton_male);
        description = rootView.findViewById(R.id.editText_desc);

        calendar = rootView.findViewById(R.id.editText_date);
        calendar.setOnClickListener(this);
    }

    //-----------------------------------DATEPICKER----------------------------_//
    @Override
    public void onClick(View v) {
        showDatePickerDialog();
    }


    private void showDatePickerDialog() {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                final String selectedDate = year + "-" + month + "-" + day;
                calendar.setText(selectedDate);
            }
        });
        newFragment.show(getActivity().getSupportFragmentManager(), "datePicker");
    }
}


