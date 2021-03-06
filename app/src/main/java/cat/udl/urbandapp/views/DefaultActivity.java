package cat.udl.urbandapp.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.Observer;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import cat.udl.urbandapp.R;
import cat.udl.urbandapp.dialogs.DialogFirstTimeLogged;
import cat.udl.urbandapp.dialogs.DialogMatchUser;
import cat.udl.urbandapp.models.RolEnum;
import cat.udl.urbandapp.models.User;
import cat.udl.urbandapp.preferences.PreferencesProvider;
import cat.udl.urbandapp.viewmodel.UserViewModel;

//@Jordi: Tenemos que hablar de la seguridad de la API de Google, se puede indicar en el Google Cloud
// que solo vuestra app pueda utilizar esta key de google maps!


public class DefaultActivity extends CustomActivity implements OnMapReadyCallback{

    private SharedPreferences mPreferences;
    private String TAG = this.getClass().getSimpleName();

    private UserViewModel userViewModel;
    private CardView user;
    private ImageView rol_icon;
    private TextView username;

    private GoogleMap googleMap;
    private BottomNavigationView navigation;

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

        initView();

    }

    private void initView() {
        navigation = findViewById(R.id.naviation_default);
        user = findViewById(R.id.user_card);
        username = findViewById(R.id.textView_username_default);
        rol_icon = findViewById(R.id.imageView_rol_def);

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
                Log.d("DefaultActivity","Tenim user " + s.toString());
                username.setText(s.getUsername());
                if (s.getRol() != null) {
                    Log.d(TAG, "ROL " + s.getRol().getName());
                    rol_icon.setImageResource(RolEnum.getImageResource(s.getRol()));
                }

            }
        });

        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goTo(UserProfileActivity.class);
            }
        });

        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.match_navigation:
                        DialogMatchUser match_dialog = DialogMatchUser.newInstance(DefaultActivity.this,userViewModel);
                        match_dialog.show(getSupportFragmentManager(), "Match");
                        return true;
                    case R.id.profile_navigation:
                        goTo(UserProfileActivity.class);
                        return true;
                    case R.id.find_users:
                        goTo(FiltersActivity.class);
                        return true;
                }
                return false;
            }
        });
        userViewModel.getProfileUser();

    }

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
                goTo(ChooserActivity.class);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void closeAppDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setMessage("¿Quieres cerrar la aplicación?");
        dialog.setCancelable(true);
        dialog.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finishAffinity();
            }
        });
        dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog _dialog = dialog.create();
        _dialog.show();
    }


    @Override
    public void onBackPressed(){
        closeAppDialog();
    }


}
