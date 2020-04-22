package cat.udl.urbandapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import cat.udl.urbandapp.dialogs.DialogFirstTimeLogged;
import cat.udl.urbandapp.models.User;
import cat.udl.urbandapp.preferences.PreferencesProvider;
import cat.udl.urbandapp.viewmodel.UserViewModel;

public class DefaultActivity extends AppCompatActivity implements OnMapReadyCallback {
    private Button logout;
    private SharedPreferences mPreferences;
    private String TAG = this.getClass().getSimpleName();
    private UserViewModel userViewModel;
    private TextView mbienvenida;
    private Button profile;


    @Override
    public void onMapReady(final GoogleMap googleMap) {
        // Add a marker in Barcelona
        // and move the map's camera to the same location.
        final LatLng sydney = new LatLng(41.390205, 2.1504);
        googleMap.addMarker(new MarkerOptions().position(sydney)
                .title("Marker in Barcelona"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        userViewModel.getResponseLiveDataAllUsers().observe(this, new Observer<List<User>>() {

            @Override
            public void onChanged(List<User> users) {

                for (int i = 0; i < users.size(); i++ ){
                    User u = users.get(i);
                    LatLng userlatlong = new LatLng(u.getLatitude(),u.getLongitude());
                    googleMap.addMarker(new MarkerOptions().position(userlatlong)
                            .title(u.getUsername()));
                }


            }
        });
        userViewModel.getAllUsers();

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_default);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        this.mPreferences = PreferencesProvider.providePreferences();
        userViewModel = new UserViewModel(getApplication());
        mbienvenida = findViewById(R.id.bienvenidaText);
        profile = findViewById(R.id.button_profile);

        // TODO: implementar chec first time
        //if (userViewModel.checkFirstTime)
        DialogFirstTimeLogged first = DialogFirstTimeLogged.newInstance(DefaultActivity.this);
        first.show(getSupportFragmentManager(), "first set up profile");

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

        userViewModel = new UserViewModel(getApplication());



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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.logout:
                this.mPreferences.edit().remove("token").commit();
                Intent intent = new Intent(DefaultActivity.this, ChooserActivity.class);
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }






}
