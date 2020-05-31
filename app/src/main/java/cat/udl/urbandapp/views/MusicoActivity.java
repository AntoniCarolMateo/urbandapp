package cat.udl.urbandapp.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Group;
import androidx.lifecycle.Observer;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import cat.udl.urbandapp.R;
import cat.udl.urbandapp.models.User;
import cat.udl.urbandapp.viewmodel.UserViewModel;

public class MusicoActivity extends CustomActivity {

    private Button subscribe;
    private Button desubscribe;
    private TextView username;
    private ImageView genere;
    private TextView description;
    private TextView role;
    private ImageView icon_role;
    private ImageView profile_photo;
    private UserViewModel userViewModel;
    private RatingBar expirience;
    public Group notes_design;
    public Group etiquetes_design;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_musico);
        userViewModel = new UserViewModel(getApplication());
        initView();
    }

    private void checkSuscription(Bundle extras) {
        if (extras != null) {
            final String extra_username = extras.getString("username");

            username.setText(extra_username);
            userViewModel.getUsersSubscribed(extra_username);
            userViewModel.getResponseUserSubscription().observe(this, new Observer<User>() {
                @Override
                public void onChanged(final User s) {
                    if(s.isHasSubscribed()){
                        subscribe.setEnabled(false);
                        int _rol = 0;

                        if (s.getGenere() == "FEMALE") {
                            genere.setImageResource(R.drawable.femaleicon);
                        }else{
                            genere.setImageResource(R.drawable.maleicon);
                        }
                        expirience.setRating(s.getGen_exp());
                        description.setText(s.getDescription());
                        //role.setText(s.getRol().getName());
                        //icon_role.setImageAlpha(s.getRol().getImageResource(s.getRol()));

                        icon_role.setVisibility(View.VISIBLE);
                        username.setVisibility(View.VISIBLE);
                        genere.setVisibility(View.VISIBLE);
                        expirience.setVisibility(View.VISIBLE);
                        description.setVisibility(View.VISIBLE);

                        notes_design.setVisibility(View.VISIBLE);
                        etiquetes_design.setVisibility(View.VISIBLE);

                        Log.d("MusicoActivity", " estamos subscritos el boton subscribe se oculta");
                        desubscribe.setEnabled(true);
                        desubscribe.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                userViewModel.removeSubscription(extra_username);
                            }
                        });
                    }
                    else{
                        //enseñar menos info, y dar la opcion de subscribirse
                        emptyProfile();
                        Log.d("MusicoActivity", "no estamos subscritos el boton desubscribe se oculta");
                        //desubscribe.setVisibility(View.GONE);
                        desubscribe.setEnabled(false);
                        subscribe.setEnabled(true);
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
                        Toast.makeText(MusicoActivity.this, "Suscrito con exito", Toast.LENGTH_LONG).show();
                        userViewModel.getUsersSubscribed(extra_username);

                    }

                    else{
                        Toast.makeText(MusicoActivity.this, "Error añadiendo suscripción.", Toast.LENGTH_LONG).show();

                    }
                }
            });
            userViewModel.getResponseLiveDataDeleteSubscription().observe(this, new Observer<Boolean>() {
                @Override
                public void onChanged(Boolean s) {
                    if(s){
                        Toast.makeText(MusicoActivity.this, "Ya no estas suscrito a este usuario.", Toast.LENGTH_LONG).show();
                        userViewModel.getUsersSubscribed(extra_username);
                    }
                    else{
                        Toast.makeText(MusicoActivity.this, "Error eliminando suscripción.", Toast.LENGTH_LONG).show();
                    }
                }
            });

        }
        else{
            super.onBackPressed();

        }
    }

    private void emptyProfile() {
        genere.setVisibility(View.INVISIBLE);
        description.setVisibility(View.INVISIBLE);
        role.setVisibility(View.INVISIBLE);
        expirience.setVisibility(View.INVISIBLE);
        icon_role.setVisibility(View.INVISIBLE);
        notes_design.setVisibility(View.INVISIBLE);
        etiquetes_design.setVisibility(View.INVISIBLE);
    }

    private void initView() {
        subscribe = findViewById(R.id.botonSubscribirse);
        desubscribe = findViewById(R.id.botonDesub);
        username = findViewById(R.id.textView_username_musico);
        description = findViewById(R.id.textView_descr_musico);
        expirience = findViewById(R.id.ratingBar_exp_musico);
        role = findViewById(R.id.textView_rol_MUSICO);
        genere = findViewById(R.id.imageView_gender_public);
        icon_role = findViewById(R.id.imageView_rol_public);
        notes_design = findViewById(R.id.group_notes);
        etiquetes_design = findViewById(R.id.group_etiquetes);

        Bundle extras = getIntent().getExtras();
        checkSuscription(extras);

    }
}
