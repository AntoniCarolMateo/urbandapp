package cat.udl.urbandapp.views;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import cat.udl.urbandapp.R;
import cat.udl.urbandapp.preferences.PreferencesProvider;
import cat.udl.urbandapp.viewmodel.LoginViewModel;

public class ChooserActivity extends CustomActivity {

    private Button loginButton;
    private Button registerButton;
    private SharedPreferences mPreferences;
    private String TAG = this.getClass().getSimpleName();
    private LoginViewModel viewModel;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chooser);
        this.mPreferences = PreferencesProvider.providePreferences();
        viewModel = new LoginViewModel(getApplication());

        initView();
    }

    private void initView() {

        if (viewModel.checkToken()){
            goTo(DefaultActivity.class);
        }
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
    @Override
    public void onBackPressed(){
        finishAffinity();
    }
}
