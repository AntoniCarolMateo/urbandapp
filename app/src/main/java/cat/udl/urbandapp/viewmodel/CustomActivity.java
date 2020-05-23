package cat.udl.urbandapp.viewmodel;

import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import cat.udl.urbandapp.R;

public class CustomActivity extends AppCompatActivity {

    protected String TAG = this.getClass().getSimpleName();

    private BottomNavigationView inferior_menú;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    public void goTo(){

    }
}
