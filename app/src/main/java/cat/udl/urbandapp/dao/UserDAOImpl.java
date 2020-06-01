package cat.udl.urbandapp.dao;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.List;

import cat.udl.urbandapp.models.Instrument;
import cat.udl.urbandapp.models.MusicalGenere;
import cat.udl.urbandapp.models.RolEnum;
import cat.udl.urbandapp.models.User;
import cat.udl.urbandapp.network.RetrofitClientInstance;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.GET;

public class UserDAOImpl implements IUserDAO {
    Retrofit retrofit = RetrofitClientInstance.getRetrofitInstance();


    @Override
    public Call<ResponseBody> getProfileUser(String auth){

        return  retrofit.create(IUserDAO.class).getProfileUser(auth);

    }

    @Override
    public Call<User> getMatch(String auth){

        return  retrofit.create(IUserDAO.class).getMatch(auth);

    }

    @Override
    public Call<ResponseBody> getAllUsers(String auth){

        return  retrofit.create(IUserDAO.class).getAllUsers(auth);

    }

    @Override
    public Call<ResponseBody> getInfoSubscribed(String auth, String username){

        return  retrofit.create(IUserDAO.class).getInfoSubscribed(auth,username);

    }

    @Override
    public Call<ResponseBody> userSubscribe(String auth, String username){

        return  retrofit.create(IUserDAO.class).userSubscribe(auth,username);

    }

    @Override
    public Call<ResponseBody> userDeleteSubscribe(String auth, String username){

        return  retrofit.create(IUserDAO.class).userDeleteSubscribe(auth,username);

    }

    @Override
    public Call<ResponseBody> firstTimeProfileSetUp(String Auth) {
        return retrofit.create(IUserDAO.class).firstTimeProfileSetUp(Auth);
    }

    @Override
    public Call<Boolean> getFirstTimeBoolean(String auth) {
        return retrofit.create(IUserDAO.class).getFirstTimeBoolean(auth);
    }

    @Override
    public Call<User> showPrivateProfile(String auth) {
        return retrofit.create(IUserDAO.class).showPrivateProfile(auth);
    }



    @Override
    public Call<List<User>> getFilteredUsers(String auth, List<String> instruments, List<String> gen) {
        return retrofit.create(IUserDAO.class).getFilteredUsers(auth, instruments, gen);
    }



}