package cat.udl.urbandapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import cat.udl.urbandapp.dialogs.DialogSetProfileStep1;

public class UserProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        DialogSetProfileStep1 dialog = DialogSetProfileStep1.newInstance(UserProfileActivity.this);
        dialog.show(getSupportFragmentManager(),"Probando dialogs");
    }
}
