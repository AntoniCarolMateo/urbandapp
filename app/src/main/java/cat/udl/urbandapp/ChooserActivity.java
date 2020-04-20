package cat.udl.urbandapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import cat.udl.urbandapp.preferences.PreferencesProvider;

public class ChooserActivity extends AppCompatActivity {

    private Button loginButton;
    private Button registerButton;
    private SharedPreferences mPreferences;
    private String TAG = this.getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chooser);
        this.mPreferences = PreferencesProvider.providePreferences();
        comprovarToken();
        registerButton = findViewById(R.id.ButtonRegister);
        loginButton = findViewById(R.id.buttonLogin);
        Log.d("UserRegister", "chooser");

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent register = new Intent(ChooserActivity.this,MainActivity.class);
                startActivity(register);

            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent login = new Intent(ChooserActivity.this,LoginActivity.class);
                startActivity(login);

            }
        });
    }
    protected void comprovarToken(){
        String token = this.mPreferences.getString("token","");
        if(token != null && !token.equals("")){
            Intent da = new Intent(ChooserActivity.this,DefaultActivity.class);
            startActivity(da);
        }
    }
}
