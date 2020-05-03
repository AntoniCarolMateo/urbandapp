package cat.udl.urbandapp.viewmodel;

import android.app.Application;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;


import java.util.ArrayList;

import com.google.gson.JsonObject;


import org.bouncycastle.cms.CMSAuthenticatedGenerator;

import java.util.List;

import cat.udl.urbandapp.models.Instrument;
import cat.udl.urbandapp.models.MusicalGenere;
import cat.udl.urbandapp.models.User;
import cat.udl.urbandapp.preferences.PreferencesProvider;

import cat.udl.urbandapp.services.TablesServiceI;
import cat.udl.urbandapp.services.TablesServiceImpl;
import cat.udl.urbandapp.services.UserServiceI;
import cat.udl.urbandapp.services.UserServiceImpl;

public class TablesViewModel extends AndroidViewModel {

    private String TAG = "TablesViewModel";
    private UserServiceI repository;
    private TablesServiceI tablesRepository;
    private MutableLiveData<String> responseLiveDataToken = new MutableLiveData<>();
    private UserViewModel userViewModel;
    private MutableLiveData<User> responseLiveUser;

    private LiveData<List<Instrument>> mInstruments;
    private LiveData<List<MusicalGenere>> mGeneres;
    private MutableLiveData<Boolean> responseChangedList;
    private SharedPreferences mPreferences = PreferencesProvider.providePreferences();

    private List<Instrument> instruments = new ArrayList<>();
    private List<MusicalGenere> generes = new ArrayList<>();

    public TablesViewModel(@NonNull Application application) {
        super(application);
        repository = new UserServiceImpl();
        userViewModel = new UserViewModel(getApplication());
        tablesRepository = new TablesServiceImpl();
        responseLiveUser = repository.getLiveDataUser();
        responseChangedList = tablesRepository.getLiveWorkedOrNot();

        mInstruments = tablesRepository.getTableInstruments();
        mGeneres = tablesRepository.getTableGeneres();

//        mInstruments = Transformations.switchMap(responseLiveDataToken, new Function<String, LiveData<List<Instrument>>>() {
//            @Override
//            public LiveData<List<Instrument>> apply(String input) {
//                mInstruments = tablesRepository.getTableInstruments();
//                return mInstruments;
//            }
//
//        });

    }

    public void addInstrument(String nameInstrument, float exp){

        Instrument ins = new Instrument(nameInstrument, exp);
        this.instruments.add(ins);
    }

    public void resetInstruments(){
        this.instruments = new ArrayList<>();
    }

    public void saveInstrument(){
        String header = this.mPreferences.getString("token","");
        Log.d(TAG, "Token:"+ header + "New instrument:"+this.instruments);
        tablesRepository.addInstrument(header, this.instruments);
    }

    public  void removeInstrument(Instrument instrument){
        String nameInstrument = instrument.getNameInstrument();
        Toast.makeText(getApplication(), nameInstrument, Toast.LENGTH_SHORT).show();
        String header = this.mPreferences.getString("token","");
        tablesRepository.removeInstrument(header, nameInstrument);
        Toast.makeText(getApplication(), nameInstrument + " Removed ", Toast.LENGTH_SHORT).show();
    }

    public LiveData<User> getResponseLiveDataUser() {
        return this.responseLiveUser;
    }

    public LiveData<List<Instrument>> getInstruments(){
        return mInstruments;
    }

    public MutableLiveData<Boolean> getResponseChangedList() { return responseChangedList;}

    public void getListInstruments() {
        String header = this.mPreferences.getString("token","");
         tablesRepository.getTableUserInstrument(header);
    }


    //------------------------------------------------------------------GENERES

    public void addGenenres(List<MusicalGenere> list){
        this.generes = list;
        Log.d("AddGenere" , list.toString());
    }

    public  void saveGeneres(){
        String header = this.mPreferences.getString("token","");
        tablesRepository.addGenere(header, this.generes);
    }

    public void resetGeneres(){
        this.generes = new ArrayList<>();
    }

    public void removeGenere(MusicalGenere genere){
        String name = genere.getName();
        String header = this.mPreferences.getString("token","");
        tablesRepository.removeGenere(header, name);
    }

    public  void getListGeneres(){
        String header = this.mPreferences.getString("token","");
        tablesRepository.getTableUserGenere(header);
    }


    public LiveData<List<MusicalGenere>> getGeneres(){
        return mGeneres;
    }


}
