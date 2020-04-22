package cat.udl.urbandapp.dialogs;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;

import cat.udl.urbandapp.dialogs.DatePickerFragment;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.hardware.camera2.CameraAccessException;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cat.udl.urbandapp.DefaultActivity;
import cat.udl.urbandapp.R;
import cat.udl.urbandapp.viewmodel.UserViewModel;

public class DialogSetProfileStep1 extends DialogFragment implements View.OnClickListener {

    public final int REQUES_ID_MULTIPLE_PERMISIONS = 3;
    public View rootView;
    private Activity activity;
    private UserViewModel viewModel;
    private EditText name;
    private EditText calendar;
    private EditText surname;
    private EditText description;
    private RatingBar generalExp;
    private Button editProfilePicture;
    private RadioGroup radio_group_genere;
    private RadioButton radio_male;
    private RadioButton radio_female;
    private ImageView profile_photo;

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
                    String _birth  = calendar.getText().toString();
                    String _gender = "";
                    String _description = description.getText().toString();
                    if(radio_female.isChecked()){
                        _gender = "FEMALE";
                    }else if (radio_male.isChecked()){
                        _gender = "MALE";
                    }

                    viewModel.setProfileInfo(_name,_surname,_exp, _birth, _gender, _description);


                    Toast.makeText(getContext(), _name +"  "+ _surname+"  "+_exp+"  "+_birth + "  " + _gender, Toast.LENGTH_SHORT).show();


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
                //PEDIRá permisos
                checkAndroidVersion();
                if (checkCameraHardware(getContext())){
                    /*Intent intent = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 1);*/
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent,0);
                }

            }
        });

        viewModel.getResponseLiveDataProfileStep1().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    Log.d("step1","vamos al step2 con boolean: " + aBoolean);
                    DialogSetProfileStep2 step2 = new DialogSetProfileStep2();
                    step2.show(getParentFragmentManager(), "step 2");
                }
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
        radio_female = rootView.findViewById(R.id.radioButton_female);
        radio_male = rootView.findViewById(R.id.radioButton_male);
        radio_group_genere = rootView.findViewById(R.id.radio_group_genere);
        profile_photo = rootView.findViewById(R.id.imageView_profile);
        description = rootView.findViewById(R.id.editText_desc);

        calendar = rootView.findViewById(R.id.editText_date);
        calendar.setOnClickListener(this);
    }

    private void checkAndroidVersion(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            checkAndRequestPermissions();
        }
    }

    private boolean checkAndRequestPermissions() {
        int camera = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA);
        int read = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE);
        List<String> listPermisionsNeeded = new ArrayList<>();
        if (camera != PackageManager.PERMISSION_GRANTED){
            listPermisionsNeeded.add(Manifest.permission.CAMERA);
        }
        if (read != PackageManager.PERMISSION_GRANTED){
            listPermisionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if(!listPermisionsNeeded.isEmpty()){
            ActivityCompat.requestPermissions(getActivity(), listPermisionsNeeded.toArray(new String[listPermisionsNeeded.size()]), REQUES_ID_MULTIPLE_PERMISIONS);
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults){
        switch (requestCode){
            case REQUES_ID_MULTIPLE_PERMISIONS: {
                Map<String, Integer> perms = new HashMap<>();
                perms.put(Manifest.permission.CAMERA, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.READ_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
                if (grantResults.length > 0){
                    for (int i = 0;i< permissions.length; i++){
                        perms.put(permissions[i], grantResults[i]);
                    }
                    if (perms.get(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED &&
                            perms.get(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){

                    }else{
                        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),Manifest.permission.CAMERA)
                                || ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),Manifest.permission.READ_EXTERNAL_STORAGE)){
                            showDialogOK("Camera and Storage required for the app", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    switch (which){
                                        case DialogInterface.BUTTON_POSITIVE:
                                            checkAndRequestPermissions();
                                            break;
                                        case DialogInterface.BUTTON_NEGATIVE:
                                            break;

                                    }
                                }
                            });
                        }
                    }
                }
            }
        }
    }

    public void showDialogOK(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(getActivity())
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", okListener)
                .create()
                .show();
    }

    private boolean checkCameraHardware(Context context) {
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY)){
            return true;
        }else{
            return false;
        }
    }



    //-----------------------------------DATEPICKER----------------------------_//
    @Override
    public void onClick(View v) {
                showDatePickerDialog();
        }


    private void showDatePickerDialog() {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener(){
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                final String selectedDate = day + " / " + month + " / " + year;
                calendar.setText(selectedDate);
            }
        });
        newFragment.show(getActivity().getSupportFragmentManager(), "datePicker");
    }

    //TODO:   TERMINAR LOGICA DE LA CÄMARA, proponer dejarlo para el siguiente esprint.
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 0:
                Bitmap map = (Bitmap) data.getExtras().get("data");
                profile_photo.setImageBitmap(map);
                break;
            /*case 1:
                if (resultCode == Activity.RESULT_OK) {
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};

                    Cursor cursor = getActivity().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String filePath = cursor.getString(columnIndex);
                    cursor.close();

                    Bitmap selectedImg = BitmapFactory.decodeFile(filePath);*/
                }
        }
    }

