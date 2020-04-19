package cat.udl.urbandapp.viewmodel;

import android.app.Application;
import android.content.SharedPreferences;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

<<<<<<< Updated upstream
import java.util.ArrayList;
=======
import com.google.gson.JsonObject;

>>>>>>> Stashed changes
import java.util.List;

import cat.udl.urbandapp.models.Instrument;
import cat.udl.urbandapp.models.User;
import cat.udl.urbandapp.preferences.PreferencesProvider;
<<<<<<< Updated upstream
import cat.udl.urbandapp.services.TablesServiceI;
import cat.udl.urbandapp.services.TablesServiceImpl;
import cat.udl.urbandapp.services.UserServiceI;
import cat.udl.urbandapp.services.UserServiceImpl;

public class TablesViewModel extends AndroidViewModel {
    private UserServiceI repository;
    private TablesServiceI tablesRepository;
    private MutableLiveData<String> responseLiveDataToken = new MutableLiveData<>();
    private UserViewModel userViewModel;
    private MutableLiveData<User> responseLiveUser;
    private LiveData<List<Instrument>> instrumentsList;
    private SharedPreferences mPreferences = PreferencesProvider.providePreferences();

    public TablesViewModel(@NonNull Application application) {
        super(application);
        repository = new UserServiceImpl();
        userViewModel = new UserViewModel(getApplication());
        tablesRepository = new TablesServiceImpl();
        responseLiveUser = repository.getLiveDataUser();

        /*instrumentsList = Transformations.switchMap(responseLiveDataToken, new Function<String, LiveData<List<Instrument>>>() {
            @Override
            public LiveData<List<Instrument>> apply(String input) {
                instrumentsList = tablesRepository.getInstrumentsList();
                return instrumentsList;
            }

        });*/
        //loadData();
    }

    //USED FOR RECYCLER VIEW
=======
import cat.udl.urbandapp.services.TablesServiceImpl;
import cat.udl.urbandapp.services.UserServiceImpl;

public class TablesViewModel extends AndroidViewModel {
    private TablesServiceImpl tablesRepository;
    private UserServiceImpl userRepository;
    private UserViewModel userViewModel;
    private MutableLiveData<User> responseLiveUser;
    private SharedPreferences mPreferences;
    private LiveData<List<Instrument>> mInstruments;

    public TablesViewModel(@NonNull Application application) {
        super(application);
        tablesRepository = new TablesServiceImpl();
        userRepository = new UserServiceImpl();
        responseLiveUser = userRepository.getLiveDataUser();
        this.mPreferences = PreferencesProvider.providePreferences();

        mInstruments = Transformations.switchMap(responseLiveUser, new Function<User, LiveData<List<Instrument>>>() {
            @Override
            public LiveData<List<Instrument>> apply(User input) {
                String header = mPreferences.getString("token","");
                mInstruments = tablesRepository.getTableInstruments();
                return mInstruments;
            }
        });
    }

>>>>>>> Stashed changes

    public LiveData<List<Instrument>> getInstruments(){
        return instrumentsList;
    }

    public void addInstrument(String nameInstrument, int exp){
        Instrument ins = new Instrument(nameInstrument, exp);
<<<<<<< Updated upstream
        tablesRepository.setTableUserInstrument(ins);
=======
        Toast.makeText(getApplication(), ins.toString(), Toast.LENGTH_SHORT).show();
        JsonObject jsonIns = ins.toJson();
        String header = this.mPreferences.getString("token","");
        tablesRepository.addInstrument(header, jsonIns);
    }

    public  void removeInstrument(Instrument instrument){
        JsonObject json = new JsonObject();
        json.addProperty("nameInstrument", instrument.getNameInstrument());
        Toast.makeText(getApplication(), json.toString(), Toast.LENGTH_SHORT).show();
        String header = this.mPreferences.getString("token","");
        //tablesRepository.removeInstrument(header, json);
    }
    public void getUserListInstruments(){
        String header = mPreferences.getString("token","");
        tablesRepository.getTableUserInstrument(header);
>>>>>>> Stashed changes
    }
    public LiveData<User> getResponseLiveDataUser() {
        return this.responseLiveUser;
    }

<<<<<<< Updated upstream



=======
    public LiveData<List<Instrument>> getInstruments(){
        return mInstruments;
    }
>>>>>>> Stashed changes


}
