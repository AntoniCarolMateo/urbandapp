package cat.udl.urbandapp.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.annotation.SuppressLint;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import cat.udl.urbandapp.R;
import cat.udl.urbandapp.preferences.PreferencesProvider;
import cat.udl.urbandapp.viewmodel.UserViewModel;

public class LoginActivity extends CustomActivity {

    private final String TAG = getClass().getSimpleName();
    private SharedPreferences mPreferences;

    private UserViewModel userViewModel;
    private Button login;

    private EditText username;
    private EditText password;


    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.mPreferences = PreferencesProvider.providePreferences();
        initView();
    }

    private void initView() {
        login = findViewById(R.id.LoginButton);
        username = findViewById(R.id.loginUsername);
        password = findViewById(R.id.loginPassword);
        userViewModel = new UserViewModel(getApplication());

        userViewModel.getResponseLiveDataToken().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                mPreferences.edit().putString("token", s).apply();
                Toast.makeText(LoginActivity.this, "Se ha iniciado sesión , token creado!", Toast.LENGTH_LONG).show();
                goTo(DefaultActivity.class);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String _username = username.getText().toString();
                String _password = password.getText().toString();
                userViewModel.createTokenUser(_username, _password);
            }
        });
    }
    @Override
    public void onBackPressed(){
        goTo(ChooserActivity.class);
    }

}
