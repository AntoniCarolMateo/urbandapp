package cat.udl.urbandapp.services;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.google.gson.JsonObject;

import java.io.IOException;

import cat.udl.urbandapp.dao.ILoginDA0;
import cat.udl.urbandapp.dao.LoginDAOImpl;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginServiceImpl {

    private ILoginDA0 loginDAO;
    public final MutableLiveData<String> mResponseToken;
    public final MutableLiveData<Boolean> mRegister;

    public LoginServiceImpl(){
        loginDAO = new LoginDAOImpl();
        mResponseToken = new MutableLiveData<>();
        mRegister = new MutableLiveData<>();
    }

    /** registerUser(JsonObject userJson)
     * @param userJson : Json que representa usuario: username y pasword
     */
    public void registerUser(JsonObject userJson) {
        loginDAO.registerUser(userJson).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200) {

                    mRegister.setValue(true);
                    Log.d("Register", "ok");
                    //mResponseToken.setValue(authToken);

                } else {
                    Log.d("Register", "error else");
                    mRegister.setValue(false);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                Log.d("Register", t.toString());
                mRegister.setValue(false);
            }

        });
    }

    /** createTokenUser(String Auth)
     * @param Auth : authorización del usuario logeado
     */
    public void createTokenUser(String Auth) {

        loginDAO.createTokenUser(Auth).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200) {
                    try {
                        String authToken = response.body().string().split(":")[1];
                        authToken = authToken.substring(2, authToken.length() - 2);

                        Log.d("UserService", authToken);
                        mResponseToken.setValue(authToken);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
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

    public MutableLiveData<Boolean> getLiveDataRegister() {
        return mRegister;
    }
    public MutableLiveData<String> getLiveDataToken() {
        return mResponseToken;
    }


}
