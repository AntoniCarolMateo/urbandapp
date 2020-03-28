package cat.udl.urbandapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import cat.udl.urbandapp.models.User;
import cat.udl.urbandapp.preferences.PreferencesProvider;
import cat.udl.urbandapp.viewmodel.UserViewModel;

public class DefaultActivity extends AppCompatActivity {
    private Button logout;
    private SharedPreferences mPreferences;
    private String TAG = this.getClass().getSimpleName();
    private UserViewModel userViewModel;
    private TextView mbienvenida;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_default);
        this.mPreferences = PreferencesProvider.providePreferences();
        userViewModel = new UserViewModel(getApplication());
        mbienvenida = findViewById(R.id.bienvenidaText);

        userViewModel.getResponseLiveDataUser().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User s) {
                //mPreferences.edit().putString("token",s).apply();
                Log.d("DefaultActivity","Tenim user " + s.toString());
                mbienvenida.setText("Bienvenido usuario: " + s.getUsername());
               // Intent da = new Intent(LoginActivity.this,DefaultActivity.class);
                //startActivity(da);
                //Toast.makeText(LoginActivity.this, s, Toast.LENGTH_LONG).show();
            }
        });

        userViewModel.getProfileUser();


        logout = findViewById(R.id.buttonLogout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPreferences.edit().putString("token","").apply();
                Intent chooser = new Intent(DefaultActivity.this,ChooserActivity.class);
                startActivity(chooser);

            }
        });
    }
}
