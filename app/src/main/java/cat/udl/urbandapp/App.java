package cat.udl.urbandapp;
import android.app.Application;

import cat.udl.urbandapp.preferences.PreferencesProvider;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        PreferencesProvider.init(this);
    }
}