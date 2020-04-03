package cat.udl.urbandapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import cat.udl.urbandapp.viewmodel.UserViewModel;

public class RegisterActivity extends AppCompatActivity {

    private UserViewModel userViewModel;
    Button registerButton;
    EditText username;
    EditText password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registerButton = findViewById(R.id.register);
        username = findViewById(R.id.usertel);
        password = findViewById(R.id.userpassword);

        userViewModel = new UserViewModel(this.getApplication());

        registerButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                String _username = username.getText().toString();
                String _password = password.getText().toString();
                if(_username != "" && _password != ""){

                    userViewModel.registerUser(_username,_password);
                }
                else{
                    Toast.makeText(RegisterActivity.this, "rellena los cmapos de registro!", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
}
