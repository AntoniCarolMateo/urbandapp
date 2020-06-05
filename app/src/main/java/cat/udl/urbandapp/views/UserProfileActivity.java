package cat.udl.urbandapp.views;

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
import cat.udl.urbandapp.viewmodel.InstrumentsViewModel;
import cat.udl.urbandapp.viewmodel.MusicalGenreViewModel;
import cat.udl.urbandapp.viewmodel.UserViewModel;

public class UserProfileActivity extends CustomActivity {

    private final String TAG = getClass().getSimpleName();

    private TextView username;
    private TextView name;
    private TextView surname;
    private TextView birthday;
    private ImageView genre_icon;
    private RatingBar gen_expirience;
    private TextView description;
    private ImageView rol_icon;
    private RecyclerView list_instruments;
    private RecyclerView list_genres;
    private FloatingActionButton addInstrument;
    private FloatingActionButton addGenre;

    private UserViewModel userViewModel;
    private InstrumentsViewModel instrumentsViewModel;
    private MusicalGenreViewModel musicalGenreViewModel;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        userViewModel = new UserViewModel(getApplication());
        instrumentsViewModel = new InstrumentsViewModel(getApplication());
        musicalGenreViewModel = new MusicalGenreViewModel(getApplication());
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
        rol_icon = findViewById(R.id.imageView_rol_priv);

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
        rol_icon.setImageResource(RolEnum.getImageResource(_rol));
    }

    private void initInstrumentsView() {
        list_instruments.setLayoutManager(new LinearLayoutManager(this));
        list_instruments.setHasFixedSize(true);
        final InstrumentAdapter instrumentAdapter = new InstrumentAdapter(new InstrumentDiffCallback(), instrumentsViewModel, this);
        list_instruments.setAdapter(instrumentAdapter);

        instrumentsViewModel.getListInstruments();

        addInstrument.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogAddInstrument dialogAddInstrument = DialogAddInstrument.newInstance(UserProfileActivity.this, instrumentsViewModel);
                dialogAddInstrument.show(getSupportFragmentManager(), "probando");
            }
        });

        instrumentsViewModel.getInstruments().observe(this, new Observer<List<Instrument>>() {
            @Override
            public void onChanged(List<Instrument> i) {
                instrumentAdapter.submitList(i);
            }
        });

        instrumentsViewModel.getResponseChangedList().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean added) {
                if (added) {
                    instrumentsViewModel.getListInstruments();
                }
            }
        });

    }

    private void initMusicalGenreView() {

        list_genres.setLayoutManager(new LinearLayoutManager(this));
        list_genres.setHasFixedSize(true);
        final GeneresAdapter genereAdapter = new GeneresAdapter(new GenereDiffCallback(), musicalGenreViewModel, this);
        list_genres.setAdapter(genereAdapter);

        musicalGenreViewModel.getListGeneres();

        addGenre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogAddGenere dialogAddGenere = DialogAddGenere.newInstance(UserProfileActivity.this, musicalGenreViewModel);
                dialogAddGenere.show(getSupportFragmentManager(), "probando");
            }
        });

        musicalGenreViewModel.getGeneres().observe(this, new Observer<List<MusicalGenere>>() {
            @Override
            public void onChanged(List<MusicalGenere> i) {
                genereAdapter.submitList(i);
            }
        });

        musicalGenreViewModel.getResponseChangedList().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean added) {
                if (added) {
                    musicalGenreViewModel.getListGeneres();
                }
            }
        });

    }



    @Override
    public void onBackPressed(){
        goTo(DefaultActivity.class);
    }
}
