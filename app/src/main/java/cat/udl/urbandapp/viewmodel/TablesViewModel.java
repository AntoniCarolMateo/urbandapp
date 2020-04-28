package cat.udl.urbandapp.viewmodel;

import android.app.Application;
import android.content.SharedPreferences;
import android.graphics.drawable.TransitionDrawable;
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


import java.util.List;

import cat.udl.urbandapp.models.Instrument;
import cat.udl.urbandapp.models.User;
import cat.udl.urbandapp.preferences.PreferencesProvider;

import cat.udl.urbandapp.services.TablesServiceI;
import cat.udl.urbandapp.services.TablesServiceImpl;
import cat.udl.urbandapp.services.UserServiceI;
import cat.udl.urbandapp.services.UserServiceImpl;

public class TablesViewModel extends AndroidViewModel {

    private List<Instrument> addedInstruments;

    private UserServiceI repository;
    private TablesServiceI tablesRepository;
    private MutableLiveData<String> responseLiveDataToken;
    private MutableLiveData<User> responseLiveUser;
    private LiveData<List<Instrument>> mInstruments;
    private List<Instrument> userInstruments;
    private MutableLiveData<Boolean> responseAddedInstrument;
    private SharedPreferences mPreferences = PreferencesProvider.providePreferences();

    public TablesViewModel(@NonNull Application application) {
        super(application);
        repository = new UserServiceImpl();
        tablesRepository = new TablesServiceImpl();
        responseLiveDataToken = new MutableLiveData<>();
        responseLiveUser = repository.getLiveDataUser();
        responseAddedInstrument = tablesRepository.getLiveDataAddedIns();
        addedInstruments = new ArrayList<Instrument>();

        mInstruments  = tablesRepository.getTableInstruments();


        init();
    }

    public void init(){
        String header = this.mPreferences.getString("token","");
        tablesRepository.getTableUserInstrument(header);

    }

    //------------------------------------------------------------------------Instruments
    public void addInstrumentToList(String nameInstrument, int exp){
        Instrument ins = new Instrument(nameInstrument, exp);
        Toast.makeText(getApplication(), nameInstrument +"  "+ exp, Toast.LENGTH_SHORT).show();
        addedInstruments.add(ins);
    }

    public void addInstruments(){
        String header = this.mPreferences.getString("token","");
        tablesRepository.addInstrument(header, this.addedInstruments);
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

    public MutableLiveData<Boolean> getResponseAddedInstrument() { return responseAddedInstrument;}

    public void getListInstruments() {
        String header = this.mPreferences.getString("token","");
        tablesRepository.getTableUserInstrument(header);
    }

    //-------------------------------------------------------------------------GENERES
    public void addGenere(String name){
        String header = this.mPreferences.getString("token","");
        tablesRepository.addGenere(header, name);
    }

    public void removeGenere(String name){
        String header = this.mPreferences.getString("token","");
        tablesRepository.removeGenere(header,name);
    }
    public void getListGeneresString(){
        String header = this.mPreferences.getString("token","");
        tablesRepository.getTableUserInstrument(header);
    }

    public List<Instrument> getAddedInstruments() {
        return addedInstruments;
    }

}
