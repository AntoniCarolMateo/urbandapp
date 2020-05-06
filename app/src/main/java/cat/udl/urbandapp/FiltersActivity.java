package cat.udl.urbandapp;



import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;

import androidx.appcompat.app.AppCompatActivity;

import cat.udl.urbandapp.preferences.PreferencesProvider;
import cat.udl.urbandapp.viewmodel.UserViewModel;


public class FiltersActivity extends AppCompatActivity {


    SharedPreferences mPreferences;
    UserViewModel userViewModel;
    private RatingBar generalExperience;
    private Button aply;


    @SuppressLint("SourceLockedOrientationActivity")
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filters);

        this.mPreferences = PreferencesProvider.providePreferences();
        userViewModel = new UserViewModel(getApplication());

        aply = findViewById(R.id.button_aply);
        aply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPreferences.edit().putString("token", "").apply();
                Intent def = new Intent(FiltersActivity.this, DefaultActivity.class);
                startActivity(def);

            }
        });
    }}

//int _