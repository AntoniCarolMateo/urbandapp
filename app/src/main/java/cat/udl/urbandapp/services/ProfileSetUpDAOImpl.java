package cat.udl.urbandapp.services;

import com.google.gson.JsonObject;

import cat.udl.urbandapp.dao.IProfileSetUpDAO;
import cat.udl.urbandapp.models.RolEnum;
import cat.udl.urbandapp.network.RetrofitClientInstance;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;

public class ProfileSetUpDAOImpl implements IProfileSetUpDAO {

    Retrofit retrofit = RetrofitClientInstance.getRetrofitInstance();

    @Override
    public Call<ResponseBody> setProfileInfo(String Auth, JsonObject json) {
        return retrofit.create(IProfileSetUpDAO.class).setProfileInfo(Auth, json);
    }

    @Override
    public Call<ResponseBody> setUsername(String auth, String username) {
        return retrofit.create(IProfileSetUpDAO.class).setUsername(auth, username);
    }

    @Override
    public Call<ResponseBody> setUserRol(String auth, RolEnum rol) {
        return retrofit.create(IProfileSetUpDAO.class).setUserRol(auth, rol);
    }
}
