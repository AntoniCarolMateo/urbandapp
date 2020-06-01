package cat.udl.urbandapp.dao;

import com.google.gson.JsonObject;

import cat.udl.urbandapp.network.RetrofitClientInstance;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;

public class LoginDAOImpl implements  ILoginDA0 {

    Retrofit retrofit = RetrofitClientInstance.getRetrofitInstance();

    @Override
    public Call<ResponseBody> registerUser(JsonObject userJson) {
        return retrofit.create(ILoginDA0.class).registerUser(userJson);
    }

    @Override
    public Call<ResponseBody> createTokenUser(String auth) {
        return retrofit.create(ILoginDA0.class).createTokenUser(auth);
    }
}
