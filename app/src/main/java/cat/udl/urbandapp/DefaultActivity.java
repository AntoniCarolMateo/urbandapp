package cat.udl.urbandapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import cat.udl.urbandapp.preferences.PreferencesProvider;

public class DefaultActivity extends AppCompatActivity {
    private Button logout;
    private SharedPreferences mPreferences;
    private String TAG = this.getClass().getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_default);
        this.mPreferences = PreferencesProvider.providePreferences();

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
