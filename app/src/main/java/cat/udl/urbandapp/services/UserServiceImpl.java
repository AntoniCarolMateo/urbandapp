package cat.udl.urbandapp.services;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.google.gson.JsonObject;

import java.io.IOException;

import cat.udl.urbandapp.dao.IUserDAO;
import cat.udl.urbandapp.dao.UserDAOImpl;
import cat.udl.urbandapp.network.RetrofitClientInstance;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UserServiceImpl implements UserServiceI {

    private IUserDAO userDAO = new UserDAOImpl();
    Retrofit retrofit = RetrofitClientInstance.getRetrofitInstance();
    public final MutableLiveData<String> mResponseToken;

    public UserServiceImpl() {
        userDAO = new UserDAOImpl();
        mResponseToken = new MutableLiveData<>();
    }
    public MutableLiveData<String> getLiveDataToken(){
        return mResponseToken;
    }

   // String mResponse = RetrofitClientInstance.getRetrofitInstance().create(UserServiceI.class).createTokenUser();
   @Override
   public void registerUser(JsonObject userJson) {
       userDAO.registerUser(userJson);
   }

    @Override
    public void createTokenUser(String Auth){

        userDAO.createTokenUser(Auth).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200 ){
                    try {
                        String authToken = response.body().string().split(":")[1];
                        authToken=authToken.substring(2,authToken.length()-2);

                        Log.d("UserService", authToken);
                        mResponseToken.setValue(authToken);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else{
                    String aux = null;
                    try {
                        String r = response.errorBody().string();
                        Log.d("UserService", r);
                        mResponseToken.setValue(r);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("UserService", t.getMessage().toString());
                mResponseToken.setValue(t.getMessage().toString());
            }
        });
    }


}
