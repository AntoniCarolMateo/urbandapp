package cat.udl.urbandapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import cat.udl.urbandapp.dialogs.Dialog_Set_Profile_Step1;

public class UserProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        //TODO: Comprobar que es la primera vez que se logea, mediante boleano añadido a la api

        Dialog_Set_Profile_Step1 dialog = Dialog_Set_Profile_Step1.newInstance(UserProfileActivity.this);
        dialog.show(getSupportFragmentManager(),"Probando dialogs");
    }
}
