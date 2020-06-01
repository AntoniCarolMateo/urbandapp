package cat.udl.urbandapp.viewmodel;

import android.app.Application;
import android.content.SharedPreferences;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.JsonObject;

import cat.udl.urbandapp.models.RolEnum;
import cat.udl.urbandapp.preferences.PreferencesProvider;
import cat.udl.urbandapp.services.ProfileSetUpServiceImpl;

public class ProfileSetUpViewModel extends AndroidViewModel {

    private ProfileSetUpServiceImpl profileSetUpRepo;
    private SharedPreferences mPreferences;
    private MutableLiveData responseLiveStep1;

    public ProfileSetUpViewModel(@NonNull Application application) {
        super(application);
        profileSetUpRepo = new ProfileSetUpServiceImpl();
        mPreferences = PreferencesProvider.providePreferences();
        responseLiveStep1 = profileSetUpRepo.getLiveDataProfileStep1();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void setProfileInfo(String name, String surname, float exp, String birth, String gender, String desc) {
        String header = this.mPreferences.getString("token","");
        JsonObject json = new JsonObject();
        json.addProperty("name", name);
        json.addProperty("surname", surname);
        json.addProperty("expirience", exp);
        json.addProperty("birthdate", birth);
        json.addProperty("gender", gender);
        json.addProperty("description", desc);
        this.profileSetUpRepo.setProfileInfo(header, json);
    }

    public void setUsername(String username) {
        String token = mPreferences.getString("token", "");
        profileSetUpRepo.setUsername(token,username);
    }
    public void setUserRol(RolEnum rol) {
        String token = mPreferences.getString("token", "");
        profileSetUpRepo.setUserRol(token,rol);
    }

    public LiveData<Boolean> getResponseLiveDataProfileStep1() {
        return this.responseLiveStep1;
    }

}
