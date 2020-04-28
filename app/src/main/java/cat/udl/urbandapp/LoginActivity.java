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
import android.widget.Toast;

import cat.udl.urbandapp.preferences.PreferencesProvider;
import cat.udl.urbandapp.viewmodel.UserViewModel;

public class LoginActivity extends AppCompatActivity {

    private UserViewModel userViewModel;
    private Button login;
    EditText username;
    EditText password;
//    RadioButton btn_usuari;
//    RadioButton btn_patrocinador;
//    RadioButton btn_banda;

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
       /* btn_usuari = findViewById(R.id.btn_Usuario);
        btn_patrocinador = findViewById(R.id.btn_Patrocinador);
        btn_banda = findViewById(R.id.btn_Banda);*/
        userViewModel.getResponseLiveDataToken().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                mPreferences.edit().putString("token", s).apply();
                Log.d("Login", "Tenim token " + s);
                Toast.makeText(LoginActivity.this, s, Toast.LENGTH_LONG).show();
                Intent da = new Intent(LoginActivity.this, DefaultActivity.class);
                startActivity(da);


                /*if (btn_usuari.isChecked()){
                    Intent da = new Intent(LoginActivity.this,DefaultActivity.class);
                    startActivity(da);

                }
                else if(btn_banda.isChecked()) {
                    Intent da = new Intent(LoginActivity.this, DefaultActivity.class);
                    startActivity(da);
                }
                else if(btn_patrocinador.isChecked()){
                    Intent da = new Intent(LoginActivity.this, DefaultActivity.class);
                    startActivity(da);
                }
                else{
                    Intent da = new Intent(LoginActivity.this, DefaultActivity.class);
                    startActivity(da);
                }

            }*/
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

}
