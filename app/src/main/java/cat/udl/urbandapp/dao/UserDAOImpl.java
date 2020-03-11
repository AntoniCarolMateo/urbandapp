package cat.udl.urbandapp.dao;

import android.util.Log;
import android.widget.Toast;

import com.google.gson.JsonObject;

import org.json.JSONObject;

import cat.udl.urbandapp.network.RetrofitClientInstance;
import cat.udl.urbandapp.services.UserServiceI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UserDAOImpl implements IUserDAO {
    Retrofit retrofit = RetrofitClientInstance.getRetrofitInstance();

    @Override
    public Call<Void> registerUser(JSONObject userJson) {
        Call<Void> call = retrofit.create(IUserDAO.class).registerUser(userJson);
        call.enqueue(new Callback<Void>() {

            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

                Log.d("UserDAO","responseOK?");

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d("UserDAO","responseNOTOK?");
            }
        });
        return call;
    }
}
