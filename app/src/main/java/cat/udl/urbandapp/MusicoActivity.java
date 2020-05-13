package cat.udl.urbandapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import cat.udl.urbandapp.models.User;
import cat.udl.urbandapp.viewmodel.UserViewModel;

public class MusicoActivity extends AppCompatActivity {

    private Button subscribe;
    private Button desubscribe;
    private TextView username;
    private TextView genere;
    private TextView description;
    private TextView role;
    private ImageView profile_photo;
    private UserViewModel userViewModel;
    private RatingBar expirience;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_musico);
        initView();
        Bundle extras = getIntent().getExtras();
        userViewModel = new UserViewModel(getApplication());


        if (extras != null) {
            final String extra_username = extras.getString("username");
            Log.d("MusicoActivity","tenemos el username: " + extra_username);
            userViewModel.getUsersSubscribed(extra_username);
            userViewModel.getResponseUserSubscription().observe(this, new Observer<User>() {
                @Override
                public void onChanged(final User s) {
                    Log.d("MusicoActivity","Tenim  user  por retrofit" + s.getUsername() + " sub: " + s.isHasSubscribed());
                    //Toast.makeText(MusicoActivity.this, "eci", Toast.LENGTH_LONG).show();
                    ;
                    if(s.isHasSubscribed()){
                        //Es un musico al que el usuario actual esta subscrito, enseñar mas informacio, y dar la opcion de desubscribise
                        //subscribe.setVisibility(View.GONE);
                        Log.d("MusicoActivity","OKEY");
                        subscribe.setEnabled(false);
                        Log.d("MusicoActivity", "User :  " + s.getUsername()+ " gen " + s.getGenere() + " exp" + s.getGen_exp());
                        username.setText(s.getUsername());
                        genere.setText(s.getGenere());
                        expirience.setRating(s.getGen_exp());
                        description.setText(s.getDescription());
                        role.setText(s.getRol());
                        Log.d("MusicoActivity", " estamos subscritos el boton subscribe se oculta");
                        desubscribe.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                userViewModel.removeSubscription(extra_username);
                            }
                        });
                    }
                    else{
                        //enseñar menos info, y dar la opcion de subscribirse
                        Log.d("MusicoActivity", "no estamos subscritos el boton desubscribe se oculta");
                        //desubscribe.setVisibility(View.GONE);
                        desubscribe.setEnabled(false);
                        subscribe.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                userViewModel.subscribeUser(extra_username);
                            }
                        });
                    }
                }
            });

            userViewModel.getResponseLiveDataSubscription().observe(this, new Observer<Boolean>() {
                @Override
                public void onChanged(Boolean s) {
                    Log.d("MusicoActivity","Tenim  subscripcion  por retrofit: "  + s);
                    if(s){
                        Toast.makeText(MusicoActivity.this, "Subscribido con exito", Toast.LENGTH_LONG).show();

                    }

                    else{
                        Toast.makeText(MusicoActivity.this, "Error en la subscripcion", Toast.LENGTH_LONG).show();

                    }
                }
            });

            userViewModel.getResponseLiveDataDeleteSubscription().observe(this, new Observer<Boolean>() {
                @Override
                public void onChanged(Boolean s) {
                    Log.d("MusicoActivity","Tenemso delete de  subscripcion  por retrofit: "  + s);
                    if(s){
                        Toast.makeText(MusicoActivity.this, "Desubscribido con exito", Toast.LENGTH_LONG).show();

                    }

                    else{
                        Toast.makeText(MusicoActivity.this, "Error en la desubscripcion", Toast.LENGTH_LONG).show();

                    }
                }
            });

        }
        else{
            super.onBackPressed();

        }

    }

    private void initView() {
        subscribe = findViewById(R.id.botonSubscribirse);
        desubscribe = findViewById(R.id.botonDesub);
        username = findViewById(R.id.textView_username_musico);
        genere = findViewById(R.id.textView_genere_musico);
        description = findViewById(R.id.textView_descr_musico);
        expirience = findViewById(R.id.ratingBar_exp_musico);
        role = findViewById(R.id.textView_rol_MUSICO);
    }
}
