package cat.udl.urbandapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import cat.udl.urbandapp.preferences.PreferencesProvider;
import cat.udl.urbandapp.viewmodel.UserViewModel;

public class LoginActivity extends AppCompatActivity {

    private UserViewModel userViewModel;
    private Button login;
    EditText username;
    EditText password;
    private RadioGroup rol;
    private RadioButton rol_Button;


    private SharedPreferences mPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.mPreferences = PreferencesProvider.providePreferences();
        login = findViewById(R.id.LoginButton);
        username = findViewById(R.id.loginUsername);
        password = findViewById(R.id.loginPassword);


        userViewModel = new UserViewModel(getApplication());

        addListenerOnButton();

        userViewModel.getResponseLiveDataToken().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {

                mPreferences.edit().putString("token", s).apply();
                Log.d("Login", "Tenim token " + s);
                if (rol_Button.getText() == "usuari") {
                    Intent da = new Intent(LoginActivity.this, DefaultActivity.class);
                    startActivity(da);
                    Toast.makeText(LoginActivity.this, rol_Button.getText(), Toast.LENGTH_SHORT).show();
                } else if (rol_Button.getText() == "banda") {
                    Intent da = new Intent(LoginActivity.this, DefaultActivity.class);
                    startActivity(da);
                    Toast.makeText(LoginActivity.this, rol_Button.getText(), Toast.LENGTH_SHORT).show();
                } else {
                    Intent da = new Intent(LoginActivity.this, DefaultActivity.class);
                    startActivity(da);
                    Toast.makeText(LoginActivity.this, rol_Button.getText(), Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    public void addListenerOnButton() {

        rol = (RadioGroup) findViewById(R.id.rol);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String _username = username.getText().toString();
                String _password = password.getText().toString();
                userViewModel.createTokenUser(_username, _password);
                int selectedId = rol.getCheckedRadioButtonId();
                rol_Button = (RadioButton) findViewById(selectedId);



            }
        }
        );


    }

}





