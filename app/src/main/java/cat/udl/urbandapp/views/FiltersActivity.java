package cat.udl.urbandapp.views;



import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import cat.udl.urbandapp.R;
import cat.udl.urbandapp.dialogs.FilterMultipleChoice;
import cat.udl.urbandapp.models.User;
import cat.udl.urbandapp.recyclerview.UserDiffCallback;
import cat.udl.urbandapp.recyclerview.UsersAdapter;
import cat.udl.urbandapp.viewmodel.InstrumentsViewModel;
import cat.udl.urbandapp.viewmodel.MusicalGenreViewModel;
import cat.udl.urbandapp.viewmodel.UserViewModel;


public class FiltersActivity extends CustomActivity  {

    private final String TAG = getClass().getSimpleName();

    private UserViewModel userViewModel;
    private InstrumentsViewModel instrumentsViewModel;
    private MusicalGenreViewModel musicalGenreViewModel;
    private ImageView aply;
    private EditText editText_instruments;
    private EditText editText_genres;
    private RecyclerView recyclerView_users;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filters);

        userViewModel = new UserViewModel(getApplication());
        instrumentsViewModel = new InstrumentsViewModel(getApplication());
        musicalGenreViewModel = new MusicalGenreViewModel(getApplication());

        initView();
    }

    private void initView() {
        aply = findViewById(R.id.button_aply);
        editText_genres = findViewById(R.id.editText_genre_filter);
        editText_instruments = findViewById(R.id.editText_ins_filter);
        recyclerView_users = findViewById(R.id.recyclerView_users_filter);

        //init recycler view
        aply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userViewModel.getFilteredUsers();

            }
        });

        editText_instruments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FilterMultipleChoice dialog = FilterMultipleChoice.newInstance(FiltersActivity.this, userViewModel,"INSTRUMENTS");
                dialog.show(getSupportFragmentManager(), "INSTRUMENTS");
            }
        });

        editText_genres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FilterMultipleChoice dialog = FilterMultipleChoice.newInstance(FiltersActivity.this, userViewModel, "GENRES");
                dialog.show(getSupportFragmentManager(), "GENRES");

            }
        });

        userViewModel.getSelectedFilterInstruments().observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> strings) {
                editText_instruments.setText(strings.toString());
            }
        });
        userViewModel.getSelectedFilterGenres().observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> strings) {
                editText_genres.setText(strings.toString());
            }
        });

        initUsersList();
    }

    private void initUsersList() {

        recyclerView_users.setLayoutManager(new LinearLayoutManager(this));
        recyclerView_users.setHasFixedSize(true);
        final UsersAdapter usersAdapter = new UsersAdapter(new UserDiffCallback(), userViewModel);
        recyclerView_users.setAdapter(usersAdapter);

        usersAdapter.setOnItemClickListener(new UsersAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(User usr) {
                Intent theIntent = new Intent(FiltersActivity.this, MusicoActivity.class);
                theIntent.putExtra("username", usr.getUsername());
                startActivity(theIntent);
            }
        });

        userViewModel.getResponseLiveDataAllUsers().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                usersAdapter.submitList(users);
            }
        });

    }

    @Override
    public void onBackPressed(){
        goTo(DefaultActivity.class);
    }


}

