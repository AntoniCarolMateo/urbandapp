package cat.udl.urbandapp.dao;

import android.util.Log;

import com.google.gson.JsonObject;

import cat.udl.urbandapp.network.RetrofitClientInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UserDAOImpl implements IUserDAO {
    Retrofit retrofit = RetrofitClientInstance.getRetrofitInstance();


    @Override
    public Call<Void> createTokenUser(String auth){
        Call<Void> call = retrofit.create(IUserDAO.class).createTokenUser(auth);
        call.enqueue(new Callback<Void>() {

            @Override
            public void onResponse(Call<Void> call, Response<String> response) {

                Log.d("UserDAO","responseOK createTokenUser");
                Log.d("UserDAO", "" + response.code());
                //Como devolvemos esto?
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("UserDAO","responseNOTOK createTokenUser?");
                Log.d("UserDAO",t.getMessage());
            }
        });
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
