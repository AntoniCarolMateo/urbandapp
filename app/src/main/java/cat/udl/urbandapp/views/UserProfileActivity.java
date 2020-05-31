package cat.udl.urbandapp.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import cat.udl.urbandapp.R;
import cat.udl.urbandapp.dialogs.DialogAddGenere;
import cat.udl.urbandapp.dialogs.DialogAddInstrument;
import cat.udl.urbandapp.models.Instrument;
import cat.udl.urbandapp.models.MusicalGenere;
import cat.udl.urbandapp.models.RolEnum;
import cat.udl.urbandapp.models.User;
import cat.udl.urbandapp.recyclerview.GenereDiffCallback;
import cat.udl.urbandapp.recyclerview.GeneresAdapter;
import cat.udl.urbandapp.recyclerview.InstrumentAdapter;
import cat.udl.urbandapp.recyclerview.InstrumentDiffCallback;
import cat.udl.urbandapp.viewmodel.TablesViewModel;
import cat.udl.urbandapp.viewmodel.UserViewModel;

public class UserProfileActivity extends CustomActivity {

    private TextView username;
    private TextView name;
    private TextView surname;
    private TextView birthday;
    private ImageView genre_icon;
    private RatingBar gen_expirience;
    private TextView description;
    private RecyclerView list_instruments;
    private RecyclerView list_genres;
    private FloatingActionButton addInstrument;
    private FloatingActionButton addGenre;

    private UserViewModel userViewModel;
    private TablesViewModel tablesViewModel;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        userViewModel = new UserViewModel(getApplication());
        tablesViewModel = new TablesViewModel(getApplication());
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        initView();
    }

    private void initView() {
        username = findViewById(R.id.textView_username_default);
        name = findViewById(R.id.textView_name);
        surname = findViewById(R.id.textView_surname);
        birthday = findViewById(R.id.textView_date);
        gen_expirience = findViewById(R.id.ratingBar_general_exp);
        description = findViewById(R.id.textView_desc);
        list_instruments = findViewById(R.id.recyclerView_instruments);
        list_genres = findViewById(R.id.recyclerView_generes_profile);
        addGenre = findViewById(R.id.floating_add_genre);
        addInstrument = findViewById(R.id.floating_add_instrument);
        genre_icon = findViewById(R.id.icon_genre);

        userViewModel.showPrivateProfile();

        userViewModel.getResponseLiveDataUser().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                updateData(user);
            }
        });
        initInstrumentsView();
        initMusicalGenreView();

    }

    private void updateData(User user) {
        RolEnum _rol = user.getRol();
        username.setText(user.getUsername());
        name.setText(user.getName());
        surname.setText(user.getSurname());
        gen_expirience.setRating(user.getGen_exp());
        birthday.setText(user.getBirthday());
        description.setText(user.getDescription());
        if (user.getGenere() == "F") {
            genre_icon.setImageResource(R.drawable.femaleicon);
        }else{
            genre_icon.setImageResource(R.drawable.maleicon);
        }
    }

    private void initInstrumentsView() {
        list_instruments.setLayoutManager(new LinearLayoutManager(this));
        list_instruments.setHasFixedSize(true);
        final InstrumentAdapter instrumentAdapter = new InstrumentAdapter(new InstrumentDiffCallback(), tablesViewModel, this);
        list_instruments.setAdapter(instrumentAdapter);

        tablesViewModel.getListInstruments();

        addInstrument.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogAddInstrument dialogAddInstrument = DialogAddInstrument.newInstance(UserProfileActivity.this, tablesViewModel);
                dialogAddInstrument.show(getSupportFragmentManager(), "probando");
            }
        });

        tablesViewModel.getInstruments().observe(this, new Observer<List<Instrument>>() {
            @Override
            public void onChanged(List<Instrument> i) {
                instrumentAdapter.submitList(i);
            }
        });

        tablesViewModel.getResponseChangedList().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean added) {
                if (added) {
                    tablesViewModel.getListInstruments();
                }
            }
        });

    }

    private void initMusicalGenreView() {

        list_genres.setLayoutManager(new LinearLayoutManager(this));
        list_genres.setHasFixedSize(true);
        final GeneresAdapter genereAdapter = new GeneresAdapter(new GenereDiffCallback(), tablesViewModel, this);
        list_genres.setAdapter(genereAdapter);

        tablesViewModel.getListGeneres();

        addGenre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogAddGenere dialogAddGenere = DialogAddGenere.newInstance(UserProfileActivity.this, tablesViewModel);
                dialogAddGenere.show(getSupportFragmentManager(), "probando");
            }
        });

        tablesViewModel.getGeneres().observe(this, new Observer<List<MusicalGenere>>() {
            @Override
            public void onChanged(List<MusicalGenere> i) {
                genereAdapter.submitList(i);
            }
        });

        tablesViewModel.getResponseChangedList().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean added) {
                if (added) {
                    tablesViewModel.getListGeneres();
                }
            }
        });

    }



    @Override
    public void onBackPressed(){
        goTo(DefaultActivity.class);
    }
}
