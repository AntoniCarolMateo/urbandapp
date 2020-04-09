package cat.udl.urbandapp.dao;

import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.google.gson.JsonObject;

import java.io.IOException;

import cat.udl.urbandapp.models.Instrument;
import cat.udl.urbandapp.network.RetrofitClientInstance;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

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
    public Call<Void> setUserName(String name) {
        Call<Void> call = retrofit.create(IUserDAO.class).setUserName(name);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
        return call;
    }

    @Override
    public Call<Void> setUserSurname(String surname) {
        Call<Void> call = retrofit.create(IUserDAO.class).setUserSurname(surname);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
        return call;
    }

    @Override
    public Call<Void> setUserGenExp(int exp) {
        Call<Void> call = retrofit.create(IUserDAO.class).setUserGenExp(exp);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
        return call;
    }


    @Override
    public Call<Void> registerUser(JsonObject userJson) {
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
    }


}