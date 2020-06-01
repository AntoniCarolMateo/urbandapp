package cat.udl.urbandapp.views;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.Observer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import cat.udl.urbandapp.R;
import cat.udl.urbandapp.viewmodel.LoginViewModel;
import cat.udl.urbandapp.viewmodel.UserViewModel;

public class RegisterActivity extends CustomActivity {

    private final String TAG = getClass().getSimpleName();

    private LoginViewModel loginViewModel;
    Button registerButton;
    EditText username;
    EditText password;

    double longitude = 0;
    double latitude = 0;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
    }

    private void initView() {

        registerButton = findViewById(R.id.register);
        username = findViewById(R.id.usertel);
        password = findViewById(R.id.userpassword);

        loginViewModel = new LoginViewModel(this.getApplication());

        registerButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                register();
            }
            });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void register() {
        String _username = username.getText().toString();
        String _password = password.getText().toString();

        if(!_username.equals("") && !_password.equals("")){
            LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

            String gps = latitude + "," + longitude;

            loginViewModel.registerUser(_username,_password,gps);

        }
        else{
            Toast.makeText(RegisterActivity.this, "Rellena los dos campos para registrarte!", Toast.LENGTH_SHORT).show();

        }

        loginViewModel.getResponseLiveDataRegister().observe(this, new Observer<Boolean>() {

            @Override
            public void onChanged(Boolean aBoolean) {
                Log.d("Register","Tenim boolean " + aBoolean);
                if(aBoolean){
                    goTo(LoginActivity.class);
                }
                else {
                    Toast.makeText(RegisterActivity.this, "Register Error!!!", Toast.LENGTH_SHORT).show();

                }

            }
        });
    }

    @Override
    public void onBackPressed(){
        goTo(ChooserActivity.class);
    }
}

