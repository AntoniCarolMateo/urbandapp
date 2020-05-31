package cat.udl.urbandapp.viewmodel;

import android.app.Application;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import cat.udl.urbandapp.models.MusicalGenere;
import cat.udl.urbandapp.preferences.PreferencesProvider;
import cat.udl.urbandapp.services.MusicalGenresServiceImpl;

public class MusicalGenreViewModel extends AndroidViewModel {

    private final String TAG = getClass().getSimpleName();

    private MusicalGenresServiceImpl musicalGenresRepo;

    private SharedPreferences mPreferences = PreferencesProvider.providePreferences();
    private List<MusicalGenere> generes = new ArrayList<>();

    private LiveData<List<MusicalGenere>> mGeneres;
    private MutableLiveData<Boolean> responseChangedList;


    public MusicalGenreViewModel(@NonNull Application application) {
        super(application);
        musicalGenresRepo = new MusicalGenresServiceImpl();
        mGeneres = musicalGenresRepo.getTableGeneres();
        responseChangedList = musicalGenresRepo.getLiveWorkedOrNot();
    }

    public void addGenenres(List<MusicalGenere> list){
        this.generes = list;
        Log.d("AddGenere" , list.toString());
    }

    public  void saveGeneres(){
        String header = this.mPreferences.getString("token","");
        musicalGenresRepo.addGenere(header, this.generes);
    }

    public void resetGeneres(){
        this.generes = new ArrayList<>();
    }

    public void removeGenere(MusicalGenere genere){
        String name = genere.getName();
        String header = this.mPreferences.getString("token","");
        musicalGenresRepo.removeGenere(header, name);
    }

    public  void getListGeneres(){
        String header = this.mPreferences.getString("token","");
        musicalGenresRepo.getTableUserGenere(header);
    }



    public MutableLiveData<Boolean> getResponseChangedList() { return responseChangedList;}


    public LiveData<List<MusicalGenere>> getGeneres(){
        return mGeneres;
    }


}
