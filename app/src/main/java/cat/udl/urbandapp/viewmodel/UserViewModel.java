package cat.udl.urbandapp.viewmodel;

import android.app.Application;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Base64;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.JsonObject;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import cat.udl.urbandapp.models.Instrument;
import cat.udl.urbandapp.models.MusicalGenere;
import cat.udl.urbandapp.models.RolEnum;
import cat.udl.urbandapp.models.User;
import cat.udl.urbandapp.preferences.PreferencesProvider;
import cat.udl.urbandapp.services.UserServiceImpl;
import cat.udl.urbandapp.utils.Utils;

public class UserViewModel extends AndroidViewModel {
    private UserServiceImpl repository;
    private MutableLiveData<User> responseLiveUser;
    private MutableLiveData<List<User>> responseAllUsers;
    private MutableLiveData<Boolean> responseIsFirstTime;

    private MutableLiveData<User> responseUserSubscription;
    private MutableLiveData<Boolean> responseSubscription;
    private MutableLiveData<Boolean> responseDeleteSubscription;

    private MutableLiveData<List<String>> mFilterInstruments;
    private MutableLiveData<List<String>> mFilterGenres;


    private List<String> list_filter_ins = new ArrayList<>();
    private List<String> list_filter_gen = new ArrayList<>();


    private MutableLiveData<User> responseMatch;
    private SharedPreferences mPreferences;
    public UserViewModel(@NonNull Application application) {
        super(application);
        repository = new UserServiceImpl();

        responseLiveUser = repository.getLiveDataUser();
        responseAllUsers = repository.getLiveDataAllUsers();
        responseIsFirstTime = repository.getLiveDataFirstTime();
        responseUserSubscription = repository.getLiveDataUserSubscription();
        responseSubscription = repository.getLiveDataSubscription();
        responseDeleteSubscription = repository.getLiveDataDeleteSubscription();
        responseMatch = repository.getLiveDataMatch();

        mFilterInstruments = new MutableLiveData<>();
        mFilterGenres = new MutableLiveData<>();

        this.mPreferences = PreferencesProvider.providePreferences();
    }

    public void getProfileUser(){
        String header = this.mPreferences.getString("token","");
        repository.getProfileUser(header);
    }

    public void getAllUsers(){
        String header = this.mPreferences.getString("token","");
        repository.getAllUsers(header);
    }

    public void getFilteredUsers(){
        String header = this.mPreferences.getString("token","");
        Log.d("KELOKE,","InstrumentsFilter:  "+list_filter_ins.toString());
        Log.d("KELOKE,","Generes filter :   " + list_filter_gen.toString());
        repository.getFilteredUsers(header,list_filter_ins, list_filter_gen);
    }

    public void filterInstruments(List<Instrument> my_sel_items) {
        List<String> instrToShow = new ArrayList<>();
        for (int i = 0; i < my_sel_items.size(); i++){
            instrToShow.add(my_sel_items.get(i).getNameInstrument());
        }
        this.list_filter_ins = instrToShow;
        Log.d("KELOKE",list_filter_ins.toString());
        mFilterInstruments.setValue(instrToShow);
    }

    public void filterGenres(List<MusicalGenere> my_sel_items) {
        List<String> genresToShow = new ArrayList<>();
        for (int i = 0; i < my_sel_items.size(); i++){
            genresToShow.add(my_sel_items.get(i).getName());
        }

        this.list_filter_gen = genresToShow;
        Log.d("KELOKE VIRE",list_filter_ins.toString());
        mFilterGenres.setValue(genresToShow);
    }

    public void getMatch(){
        Log.d("DefaultAcitivity","UserVIewModel getMatch");
        String header = this.mPreferences.getString("token","");
        repository.getMatch(header);
    }
    public void getUsersSubscribed(String username){
        String header = this.mPreferences.getString("token","");
        repository.getInfoSubscribed(header,username);

    };

    public void subscribeUser(String username){
        String header = this.mPreferences.getString("token","");
        repository.userSubscribe(header,username);

    };

    public void removeSubscription(String username){
        String header = this.mPreferences.getString("token","");
        repository.userDeleteSubscribe(header,username);

    };


    public void getFirstTime() {
        String header = this.mPreferences.getString("token","");
        this.repository.getFirstTimeBoolean(header);
    }

    public void firstSetUpDone() {
        String header = this.mPreferences.getString("token","");
        this.repository.firstTimeProfileSetUp(header);
    }

    public LiveData<Boolean> getResponseLiveDataDeleteSubscription(){
        return this.responseDeleteSubscription;
    }


    public void showPrivateProfile() {
        String token = mPreferences.getString("token","");
        repository.showPrivateProfile(token);
    }

    public LiveData<User> getResponseLiveDataUser() {
        return this.responseLiveUser;
    }
    public LiveData<List<User>> getResponseLiveDataAllUsers() {
        return this.responseAllUsers;
    }

    public LiveData<Boolean> getResponseIsFirstTime(){ return this.responseIsFirstTime;}


    public LiveData<User> getResponseLiveDataMatch() {
        return this.responseMatch;
    }

    public LiveData<User> getResponseUserSubscription(){return this.responseUserSubscription;}

    public LiveData<Boolean> getResponseLiveDataSubscription(){
        return this.responseSubscription;
    }

    public  LiveData<List<String>> getSelectedFilterInstruments(){ return mFilterInstruments;}
    public LiveData<List<String>> getSelectedFilterGenres(){ return mFilterGenres;}

    public User getRecentMatch() {
        User match_usr = responseMatch.getValue();
        Log.d("MATCH", match_usr.toString());
        return match_usr;
    }
}
