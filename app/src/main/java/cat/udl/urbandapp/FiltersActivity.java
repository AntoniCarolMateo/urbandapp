package cat.udl.urbandapp;



import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import cat.udl.urbandapp.dialogs.FilterMultipleChoice;
import cat.udl.urbandapp.models.Instrument;
import cat.udl.urbandapp.models.User;
import cat.udl.urbandapp.preferences.PreferencesProvider;
import cat.udl.urbandapp.recyclerview.UserDiffCallback;
import cat.udl.urbandapp.recyclerview.UsersAdapter;
import cat.udl.urbandapp.viewmodel.TablesViewModel;
import cat.udl.urbandapp.viewmodel.UserViewModel;


public class FiltersActivity extends AppCompatActivity  {


    UserViewModel userViewModel;
    private TablesViewModel tablesViewModel;
    private Button aply;
    private EditText editText_instruments;
    private EditText editText_genres;
    private RecyclerView recyclerView_users;


    @SuppressLint("SourceLockedOrientationActivity")
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filters);

        initView();

        userViewModel = new UserViewModel(getApplication());
        tablesViewModel = new TablesViewModel(getApplication());

        //init recycler view
        recyclerView_users.setLayoutManager(new LinearLayoutManager(this));
        recyclerView_users.setHasFixedSize(true);
        final UsersAdapter usersAdapter = new UsersAdapter(new UserDiffCallback(), userViewModel);
        recyclerView_users.setAdapter(usersAdapter);

        userViewModel.getResponseLiveDataAllUsers().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                usersAdapter.submitList(users);
            }
        });

        editText_instruments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FilterMultipleChoice dialog = FilterMultipleChoice.newInstance(FiltersActivity.this, userViewModel, tablesViewModel, "INSTRUMENTS");
                dialog.show(getSupportFragmentManager(), "INSTRUMENTS");
                }
        });

        editText_genres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    FilterMultipleChoice dialog = FilterMultipleChoice.newInstance(FiltersActivity.this, userViewModel, tablesViewModel, "GENRES");
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




        aply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               userViewModel.getFilteredUsers();

            }
        });
    }

    private void initView() {
        aply = findViewById(R.id.button_aply);
        editText_genres = findViewById(R.id.editText_genre_filter);
        editText_instruments = findViewById(R.id.editText_ins_filter);
        recyclerView_users = findViewById(R.id.recyclerView_users_filter);


    }



}

