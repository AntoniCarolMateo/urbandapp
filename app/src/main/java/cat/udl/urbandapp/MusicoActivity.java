package cat.udl.urbandapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import cat.udl.urbandapp.models.User;
import cat.udl.urbandapp.viewmodel.UserViewModel;

public class MusicoActivity extends AppCompatActivity {

    private Button subscribe;
    private Button desubscribe;
    private EditText username;
    private EditText genere;
    private EditText description;
    private UserViewModel userViewModel;
    private TextView labelDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_musico);
        Bundle extras = getIntent().getExtras();
        subscribe = findViewById(R.id.botonSubscribirse);
        desubscribe = findViewById(R.id.botonDesub);
        username = findViewById(R.id.musico_username);
        genere = findViewById(R.id.musico_genere);
        description = findViewById(R.id.musico_descripcion);
        userViewModel = new UserViewModel(getApplication());
        labelDesc = findViewById(R.id.label_descripcion);
        username.setFocusable(false);
        username.setEnabled(false);
        genere.setFocusable(false);
        genere.setEnabled(false);
        description.setFocusable(false);
        description.setEnabled(false);

        if (extras != null) {
            final String extra_username = extras.getString("username");
            Log.d("MusicoActivity","tenemos el username: " + extra_username);
            userViewModel.getUsersSubscribed(extra_username);
            userViewModel.getResponseUserSubscription().observe(this, new Observer<User>() {
                @Override
                public void onChanged(final User s) {
                    Log.d("MusicoActivity","Tenim  user  por retrofit" + s.getUsername() + " sub: " + s.isHasSubscribed());
                    //Toast.makeText(MusicoActivity.this, "eci", Toast.LENGTH_LONG).show();
                    username.setText(s.getUsername());
                    genere.setText(s.getGenere());
                    description.setText(s.getDescription());
                    if(s.isHasSubscribed()){
                        //Es un musico al que el usuario actual esta subscrito, enseñar mas informacio, y dar la opcion de desubscribise
                        //subscribe.setVisibility(View.GONE);
                        subscribe.setEnabled(false);
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
                        description.setVisibility(View.GONE);
                        labelDesc.setVisibility(View.GONE);
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
}
