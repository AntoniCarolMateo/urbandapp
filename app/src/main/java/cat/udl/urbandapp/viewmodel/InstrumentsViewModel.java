package cat.udl.urbandapp.viewmodel;

import android.app.Application;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import cat.udl.urbandapp.models.Instrument;
import cat.udl.urbandapp.models.User;
import cat.udl.urbandapp.preferences.PreferencesProvider;
import cat.udl.urbandapp.services.InstrumentsServiceImpl;

public class InstrumentsViewModel extends AndroidViewModel {

    private final String TAG = getClass().getSimpleName();

    private InstrumentsServiceImpl instrumentsRepo;

    private SharedPreferences mPreferences = PreferencesProvider.providePreferences();
    private List<Instrument> instruments = new ArrayList<>();

    private MutableLiveData<Boolean> responseChangedList;
    private LiveData<List<Instrument>> mInstruments;


    public InstrumentsViewModel(@NonNull Application application) {
        super(application);
        instrumentsRepo = new InstrumentsServiceImpl();
        mInstruments = instrumentsRepo.getTableInstruments();
        responseChangedList = instrumentsRepo.getLiveWorkedOrNot();
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
        instrumentsRepo.addInstrument(header, this.instruments);
    }

    public  void removeInstrument(Instrument instrument){
        String nameInstrument = instrument.getNameInstrument();
        Toast.makeText(getApplication(), nameInstrument, Toast.LENGTH_SHORT).show();
        String header = this.mPreferences.getString("token","");
        instrumentsRepo.removeInstrument(header, nameInstrument);
        Toast.makeText(getApplication(), nameInstrument + " Removed ", Toast.LENGTH_SHORT).show();
    }


    public LiveData<List<Instrument>> getInstruments(){
        return mInstruments;
    }

    public MutableLiveData<Boolean> getResponseChangedList() { return responseChangedList;}

    public void getListInstruments() {
        String header = this.mPreferences.getString("token","");
        instrumentsRepo.getTableUserInstrument(header);
    }

}
