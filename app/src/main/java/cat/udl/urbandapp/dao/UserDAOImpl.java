package cat.udl.urbandapp.dao;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.google.gson.JsonObject;

import java.io.IOException;

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
    public Call<ResponseBody> createTokenUser(String auth){

        return  retrofit.create(IUserDAO.class).createTokenUser(auth);

    }

    @Override
    public Call<ResponseBody> getProfileUser(String auth){

        return  retrofit.create(IUserDAO.class).getProfileUser(auth);

    }


    @Override
    public Call<ResponseBody> getAllUsers(){

        return  retrofit.create(IUserDAO.class).getAllUsers();

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
    public Call<ResponseBody> setProfileInfo(String Auth, JsonObject json) {
       return retrofit.create(IUserDAO.class).setProfileInfo(Auth, json);
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
        //public Call<Void> registerUser(JsonObject userJson) {
        public Call<ResponseBody> registerUser(JsonObject userJson) {
            return retrofit.create(IUserDAO.class).registerUser(userJson);

       /*
        Log.d("UserRegister", "userDAO");
        Call<Void> call = retrofit.create(IUserDAO.class).registerUser(userJson);
        call.enqueue(new Callback<Void>() {

            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

                Log.d("UserDAO","responseOK?");
                Log.d("UserDAO", "" + response.code());

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d("UserDAO","responseNOTOK?");
                Log.d("UserDAO",t.getMessage());
            }
        });
        return call;

        */
    }
}