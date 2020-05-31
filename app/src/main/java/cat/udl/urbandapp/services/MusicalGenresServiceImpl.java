package cat.udl.urbandapp.services;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import cat.udl.urbandapp.dao.MusicalGenreDAOImpl;
import cat.udl.urbandapp.models.Instrument;
import cat.udl.urbandapp.models.MusicalGenere;
import cat.udl.urbandapp.models.User;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MusicalGenresServiceImpl {

    private final String TAG = getClass().getSimpleName();

    private MusicalGenreDAOImpl musicalGenresDAO;

    public final MutableLiveData<User> mUser;
    public final MutableLiveData<List<MusicalGenere>> mlistGeneres;
    public final MutableLiveData<Boolean> mResponseWorked;

    public MusicalGenresServiceImpl(){
        musicalGenresDAO = new MusicalGenreDAOImpl();
        mlistGeneres = new MutableLiveData<>();
        mUser = new MutableLiveData<>();
        mResponseWorked = new MutableLiveData<>();
    }


    public void addGenere(String auth, List<MusicalGenere> list_generes) {
        musicalGenresDAO.addGenere(auth, list_generes).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code()==200){
                    mResponseWorked.setValue(true);
                }else{
                    mResponseWorked.setValue(false);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                mResponseWorked.setValue(false);
            }
        });
    }

    public void removeGenere(String auth, String nameGenere) {
        musicalGenresDAO.removeGenere(auth, nameGenere).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code()==200){
                    mResponseWorked.setValue(true);
                }else{
                    mResponseWorked.setValue(false);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                mResponseWorked.setValue(false);
            }
        });
    }


    public void getTableUserGenere(final String auth) {
        musicalGenresDAO.getTableUserGenere(auth).enqueue(new Callback<List<MusicalGenere>>() {
            @Override
            public void onResponse(Call<List<MusicalGenere>> call, Response<List<MusicalGenere>> response) {
                if (response.code() == 200) {
                    List<MusicalGenere> mList = new ArrayList<>();
                    mList = response.body();
                    mlistGeneres.setValue(mList);
                    Log.d("getUserGeneres", "Number of generes: " + mList.size());
                } else {
                    mlistGeneres.setValue(new ArrayList<MusicalGenere>());
                    Log.d("getUser", "Error " + response.code() + " message:" + response.message());
                    Log.d("getUser", "header es: " + auth);

                }
            }

            @Override
            public void onFailure(Call<List<MusicalGenere>> call, Throwable t) {
                Log.d("getInstrumentUserList", t.getMessage().toString());
            }
        });
    }
    public MutableLiveData<User> getLiveDataUser(){
        return mUser;
    }
    public LiveData<List<MusicalGenere>> getTableGeneres() {
        return mlistGeneres;
    }
    public MutableLiveData<Boolean> getLiveWorkedOrNot() { return mResponseWorked; }
}
