package cat.udl.urbandapp.views;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;


import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import cat.udl.urbandapp.R;
import cat.udl.urbandapp.utils.EULA;
import cat.udl.urbandapp.viewmodel.LoginViewModel;
import cat.udl.urbandapp.viewmodel.UserViewModel;

public class RegisterActivity extends CustomActivity {

    private final String TAG = getClass().getSimpleName();

    private LoginViewModel loginViewModel;
    Button registerButton;
    EditText username;
    EditText password;
    EditText repeat_password;
    final EULA eula_dialog = new EULA(this);
    CheckBox _agreement_terms_and_conditions;
    double longitude = 0;
    double latitude = 0;
    int PERMISSION_ID = 40;
    FusedLocationProviderClient mFusedLocationClient;
    protected MutableLiveData<Location> currentLocation;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        currentLocation = new MutableLiveData<>();
        currentLocation.setValue(null);
        getLastLocation();
        initView();
    }

    private void initView() {

        registerButton = findViewById(R.id.register);
        username = findViewById(R.id.usertel);
        password = findViewById(R.id.userpassword);
        repeat_password = findViewById(R.id.user_repeat_password);
        loginViewModel = new LoginViewModel(this.getApplication());

        registerButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                register();
            }
            });
        // Accepting Terms and conditions
        _agreement_terms_and_conditions = findViewById(R.id.agreement_terms_and_conditions);
        _agreement_terms_and_conditions.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(isChecked) {
                            eula_dialog.show(R.id.agreement_terms_and_conditions);
                        }
                    }
                });

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void register() {
        String _username = username.getText().toString();
        String _password = password.getText().toString();
        String _repeat_password = repeat_password.getText().toString();

        if(!_username.equals("") && !_password.equals("") && !_repeat_password.equals("")){
            LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            if(_password.equals(_repeat_password)){
                if(_agreement_terms_and_conditions.isChecked()){
                    //String gps = latitude + "," + longitude;

                    String gps = currentLocation.getValue().getLatitude() + "," + currentLocation.getValue().getLongitude();
                    loginViewModel.registerUser(_username,_password,gps);
                }
                else{
                    Toast.makeText(RegisterActivity.this, "Debes aceptar las condiciones para poder registrarte!", Toast.LENGTH_SHORT).show();
                }
            }
            else{
                Toast.makeText(RegisterActivity.this, "Debes repetir la contraseña correctamente!", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(RegisterActivity.this, "Rellena los tres campos para registrarte!", Toast.LENGTH_SHORT).show();
        }

        loginViewModel.getResponseLiveDataRegister().observe(this, new Observer<Boolean>() {

            @Override
            public void onChanged(Boolean aBoolean) {
                Log.d("Register","Tenim boolean " + aBoolean);
                if(aBoolean){
                    goTo(LoginActivity.class);
                }
                else {
                    Toast.makeText(RegisterActivity.this, "Register Error!!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onBackPressed(){
        goTo(ChooserActivity.class);
    }

    private void getLastLocation(){
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                mFusedLocationClient.getLastLocation().addOnCompleteListener(
                        new OnCompleteListener<Location>() {
                            @Override
                            public void onComplete(@NonNull Task<Location> task) {
                                Location location = task.getResult();
                                Log.d(TAG, "(getLastLocation()) -> location: " + location);
                                if (location == null) {
                                    requestNewLocationData();
                                } else {
                                    currentLocation.setValue(location);
                                }
                            }
                        }
                );
            } else {
                Toast.makeText(this, "Turn on location", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        } else {
            requestPermissions();
        }
    }


    private void requestNewLocationData(){

        Log.d(TAG, "(requestNewLocationData) -> getLastLocation()...");

        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(0);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setNumUpdates(1);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mFusedLocationClient.requestLocationUpdates(
                mLocationRequest, mLocationCallback,
                Looper.myLooper()
        );

    }

    private LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            currentLocation.setValue( locationResult.getLastLocation());
            Log.d(TAG, "(onLocationResult) -> Current location: " + currentLocation);
        }
    };


    private boolean checkPermissions() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        return false;
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(
                this,
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                PERMISSION_ID
        );
    }

    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(
                LocationManager.NETWORK_PROVIDER
        );
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_ID) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            }
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        if (checkPermissions()) {
            getLastLocation();
        }

    }
}

