package cat.udl.urbandapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.Observer;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.JsonObject;

import cat.udl.urbandapp.viewmodel.UserViewModel;

public class MainActivity extends AppCompatActivity {

    private UserViewModel userViewModel;
    Button registerButton;
    EditText username;
    EditText password;
    Context register;
    double longitude = 0;
    double latitude = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        register = this;
        registerButton = findViewById(R.id.register);
        username = findViewById(R.id.usertel);
        password = findViewById(R.id.userpassword);
        Log.d("UserRegister", "onCreate");

        userViewModel = new UserViewModel(this.getApplication());

        registerButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                Log.d("UserRegister", "registerButton");
                String _username = username.getText().toString();
                String _password = password.getText().toString();

                if(!_username.equals("") && !_password.equals("")){
                    LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                    /* Vuelve a la activity anterior al ejecutar getLastKnownLocation, algo hay que cambiar
                    if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        Log.d("UserRegister", "afterLocation");
                        longitude = location.getLongitude();
                        latitude = location.getLatitude();
                    }
                    */


                    String gps = latitude + "," + longitude;

                    userViewModel.registerUser(_username,_password,gps);

                }
                else{
                    Toast.makeText(MainActivity.this, "rellena los campos de registro!", Toast.LENGTH_SHORT).show();

                }


            }
        });
        userViewModel.getResponseLiveDataRegister().observe(this, new Observer<Boolean>() {

            @Override
            public void onChanged(Boolean aBoolean) {
                Log.d("Register","Tenim boolean " + aBoolean);
                if(aBoolean){
                    //register OK
                    Toast.makeText(MainActivity.this, "Register Ok", Toast.LENGTH_SHORT).show();
                    Intent da = new Intent(MainActivity.this,LoginActivity.class);
                    startActivity(da);
                }
                else {
                    Toast.makeText(MainActivity.this, "Register Error!!!", Toast.LENGTH_SHORT).show();

                }

            }
        });
    }
}
