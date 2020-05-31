package cat.udl.urbandapp.views;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import cat.udl.urbandapp.R;
import cat.udl.urbandapp.preferences.PreferencesProvider;

public class ChooserActivity extends CustomActivity {

    private Button loginButton;
    private Button registerButton;
    private SharedPreferences mPreferences;
    private String TAG = this.getClass().getSimpleName();

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chooser);
        this.mPreferences = PreferencesProvider.providePreferences();
        initView();

    }

    private void initView() {

        comprovarToken();
        registerButton = findViewById(R.id.ButtonRegister);
        loginButton = findViewById(R.id.buttonLogin);


        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goTo(RegisterActivity.class);

            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goTo(LoginActivity.class);

            }
        });
    }



    //TODO: Esta función la deberia realizar el viewModel
    // y notificar a la vista de si hay token o no! Es una parida, no tiene más importancia
    protected void comprovarToken(){
        String token = this.mPreferences.getString("token","");
        if(token != null && !token.equals("")){
            goTo(DefaultActivity.class);
        }
    }
}
