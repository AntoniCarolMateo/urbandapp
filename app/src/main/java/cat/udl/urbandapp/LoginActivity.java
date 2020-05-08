package cat.udl.urbandapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.annotation.SuppressLint;

import android.app.Notification;

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

import cat.udl.urbandapp.preferences.PreferencesProvider;
import cat.udl.urbandapp.viewmodel.UserViewModel;

public class LoginActivity extends AppCompatActivity {


    private UserViewModel userViewModel;
    private Button login;
    EditText username;
    EditText password;
    private RadioGroup rol;
    private RadioButton usuario, banda, patrocinador;
//    RadioButton btn_usuari;
//    RadioButton btn_patrocinador;
//    RadioButton btn_banda;

    private SharedPreferences mPreferences;
    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.mPreferences = PreferencesProvider.providePreferences();
        login = findViewById(R.id.LoginButton);
        username = findViewById(R.id.loginUsername);
        password = findViewById(R.id.loginPassword);
        userViewModel = new UserViewModel(getApplication());

       /* usuario = findViewById(R.id.usuario);
        banda = findViewById(R.id.banda);
        patrocinador = findViewById(R.id.patrocinador);*/

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

               /* if (usuario.isChecked()) {

                    Toast.makeText(LoginActivity.this, usuario.getText(), Toast.LENGTH_SHORT).show();
                } else if (banda.isChecked()) {
                    Intent da = new Intent(LoginActivity.this, DefaultActivity.class);
                    startActivity(da);
                    Toast.makeText(LoginActivity.this, banda.getText(), Toast.LENGTH_SHORT).show();
                } else if (patrocinador.isChecked()){
                    Intent da = new Intent(LoginActivity.this, DefaultActivity.class);
                    startActivity(da);
                    Toast.makeText(LoginActivity.this, patrocinador.getText(), Toast.LENGTH_SHORT).show();
                }*/


            }
        });


//            rol = (RadioGroup) findViewById(R.id.rol);
            login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String _username = username.getText().toString();
                String _password = password.getText().toString();
                userViewModel.createTokenUser(_username, _password);
//                int selectedId = rol.getCheckedRadioButtonId();
//                usuario = (RadioButton) findViewById(selectedId);

            }
        });


    }

}
