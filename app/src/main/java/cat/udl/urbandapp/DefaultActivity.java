package cat.udl.urbandapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import cat.udl.urbandapp.dialogs.DialogFirstTimeLogged;
import cat.udl.urbandapp.models.User;
import cat.udl.urbandapp.preferences.PreferencesProvider;
import cat.udl.urbandapp.viewmodel.UserViewModel;

//@Jordi: Tenemos que hablar de la seguridad de la API de Google, se puede indicar en el Google Cloud
// que solo vuestra app pueda utilizar esta key de google maps!


public class DefaultActivity extends AppCompatActivity implements OnMapReadyCallback{

    private Button filters;
    private Button logout;
    private SharedPreferences mPreferences;
    private String TAG = this.getClass().getSimpleName();
    private UserViewModel userViewModel;
    private TextView mbienvenida;
    private Button profile;
    private GoogleMap googleMap;


    @Override
    public void onMapReady(final GoogleMap googleMap) {
        // Add a marker in Barcelona
        // and move the map's camera to the same location.
        this.googleMap = googleMap;
        final LatLng sydney = new LatLng(41.390205, 2.2504);
        //googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Barcelona"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(6));
        userViewModel.getResponseLiveDataAllUsers().observe(this, new Observer<List<User>>() {

            @Override
            public void onChanged(List<User> users) {

                for (int i = 0; i < users.size(); i++ ){
                    User u = users.get(i);
                    LatLng userlatlong = new LatLng(u.getLatitude(),u.getLongitude());
                    Marker marker =  googleMap.addMarker(new MarkerOptions()
                            .position(userlatlong).title(u.getUsername()));
                    marker.setTag(u.getUsername());
                    //googleMap.addMarker(new MarkerOptions().position(userlatlong).title(u.getUsername()));
                }

                googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        //Toast.makeText(DefaultActivity.this, "marker clicado: " + marker.getTitle(), Toast.LENGTH_SHORT).show();
                        Intent theIntent = new Intent(DefaultActivity.this, MusicoActivity.class);
                        theIntent.putExtra("username", marker.getTitle());
                        startActivity(theIntent);
                        return false;
                    }
                });
            }
        });
        userViewModel.getAllUsers();

    }
    /*
    @Override
    public boolean onMarkerClick(Marker marker) {
        Toast.makeText(DefaultActivity.this, "marker clicado!", Toast.LENGTH_SHORT).show();

        return true;
    }
    */

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_default);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        this.mPreferences = PreferencesProvider.providePreferences();
        userViewModel = new UserViewModel(getApplication());
        mbienvenida = findViewById(R.id.bienvenidaText);
        profile = findViewById(R.id.button_profile);
        filters = findViewById(R.id.button_filters);
        logout = findViewById(R.id.buttonLogout);


        userViewModel.getFirstTime();

        userViewModel.getResponseIsFirstTime().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean firstTime) {
                if (firstTime){
                    DialogFirstTimeLogged first = DialogFirstTimeLogged.newInstance(DefaultActivity.this);
                    first.show(getSupportFragmentManager(), "first set up profile");
                }
            }
        });
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



        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPreferences.edit().putString("token", "").apply();
                Intent chooser = new Intent(DefaultActivity.this, ChooserActivity.class);
                startActivity(chooser);

            }
        });


        filters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent filter = new Intent(DefaultActivity.this, FiltersActivity.class);
                startActivity(filter);

            }
        });


        profile.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profile = new Intent(DefaultActivity.this, UserProfileActivity.class);
                startActivity(profile);
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
