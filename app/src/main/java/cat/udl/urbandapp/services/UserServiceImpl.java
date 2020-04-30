package cat.udl.urbandapp.services;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cat.udl.urbandapp.dao.IUserDAO;
import cat.udl.urbandapp.dao.UserDAOImpl;
import cat.udl.urbandapp.models.User;
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
    public final MutableLiveData<User> mUser;
    public final MutableLiveData<List<User>> mAllUsers;
    public final MutableLiveData<Boolean> mRegister;
    public final MutableLiveData<Boolean> mSetProfileStep1;

    public final MutableLiveData<Boolean> mFirstTime;

    public UserServiceImpl() {
        userDAO = new UserDAOImpl();
        mResponseToken = new MutableLiveData<>();
        mUser = new MutableLiveData<>();
        mAllUsers = new MutableLiveData<>();
        mRegister = new MutableLiveData<>();
        mSetProfileStep1 = new MutableLiveData<>();
        mFirstTime = new MutableLiveData<>();
    }
    public MutableLiveData<String> getLiveDataToken(){
        return mResponseToken;
    }
    public MutableLiveData<User> getLiveDataUser(){
        return mUser;
    }
    public MutableLiveData<Boolean> getLiveDataRegister(){return  mRegister;}
    public MutableLiveData<Boolean> getLiveDataProfileStep1() { return mSetProfileStep1; }

    public MutableLiveData<Boolean> getLiveDataFirstSetup() { return mFirstTime; }

    public MutableLiveData<List<User>> getLiveDataAllUsers(){
        return mAllUsers;
    }


    @Override
    public void getProfileUser(final String Auth){

        userDAO.getProfileUser(Auth).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200 ){
                    try {

                        String respuestaBody = response.body().string();
                        Log.d("getUser", "Ok getUser");
                        Log.d("getUser", respuestaBody);
                        JSONObject mUserjson = new JSONObject(respuestaBody);
                        Log.d("getUser", "El JSONObject es: " + mUserjson.toString());
                        User u = new User();

                        u.setUsername(mUserjson.getString("username"));
                        u.setCreated_at(mUserjson.getString("created_at"));

                        Log.d("getUser", u.getUsername());
                        Log.d("getUser", u.getCreated_at());

                        mUser.setValue(u);

                    } catch (IOException | JSONException e) {
                        e.printStackTrace();
                    }
                }
                else{
                    mUser.setValue(new User());
                    Log.d("getUser", "Error en la call a la API llamada retornada con codigo" + response.code() + " message:" + response.message() );
                    Log.d("getUser", "header es: " + Auth);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("getUser", t.getMessage().toString());
                mUser.setValue(new User());
            }
        });
    }

    @Override
    public void firstProfileSetUpDone(String header) {
        userDAO.firstTimeProfileSetUp(header).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code()==200){
                    //If the Call is done corrected
                    mFirstTime.setValue(false);
                    Log.d("UserServiceImp","First time done");
                }else{
                    mFirstTime.setValue(false);
                    Log.d("UserServiceImp","You need to complete all user profile!");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                mFirstTime.setValue(false);
            }
        });
    }

    @Override
    public void getfirstTime(String auth) {
        userDAO.getFirstTimeBoolean(auth).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.code()==200) {
                    Boolean isfirstTime = response.body();
                    mFirstTime.setValue(isfirstTime);
                }
                else{
                    Log.d("First Time", "Something went wrong while getting the data + "+response.message());
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Log.d("First Time", "Something went wrong while getting the data");
            }
        });
    }


    @Override
    public void getAllUsers(){

        userDAO.getAllUsers().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200 ){
                    try {

                        String respuestaBody = response.body().string();
                        Log.d("getAllUsers", "Ok getAllUser");
                        Log.d("getAllUsers", respuestaBody);
                        JSONArray mUsers = new JSONArray(respuestaBody);
                        List<User> mList = new ArrayList<>();
                        for (int i = 0; i < mUsers.length(); i++) {
                            JSONObject mUserjson =  mUsers.getJSONObject(i);
                            User u = new User();

                            u.setUsername(mUserjson.getString("username"));
                            u.setCreated_at(mUserjson.getString("created_at"));
                            String latlong = mUserjson.getString("gps");
                            String[] parts = latlong.split(",");
                            float latitude = Float.parseFloat(parts[0]);
                            float longitude = Float.parseFloat(parts[1]);
                            u.setLatitude(latitude);
                            u.setLongitude(longitude);
                            mList.add(u);
                        }
                        mAllUsers.setValue(mList);

                    } catch (IOException | JSONException e) {
                        e.printStackTrace();
                    }
                }
                else{
                    mUser.setValue(new User());
                    Log.d("getAllUsers", "Error en la call a la API llamada retornada con codigo" + response.code() + " message:" + response.message() );
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("getAllUser", t.getMessage().toString());
            }
        });
    }
    @Override
    public void setProfileInfo(String header, JsonObject json) {
        userDAO.setProfileInfo(header,json).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200) {

                    mSetProfileStep1.setValue(true);
                    Log.d("SetProfileStep1", "ok");
                    Log.d("SetProfileStep1","Tenim boolean " + mSetProfileStep1.getValue());

                } else {
                    Log.d("SetProfileStep1", "error else");
                    mSetProfileStep1.setValue(false);
                }
                }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("SetProfileStep1", t.toString());
                mSetProfileStep1.setValue(false);
            }
        });
    }

    // String mResponse = RetrofitClientInstance.getRetrofitInstance().create(UserServiceI.class).createTokenUser();
   @Override
   public void registerUser(JsonObject userJson) {

       //userDAO.registerUser(userJson);
       userDAO.registerUser(userJson).enqueue(new Callback<ResponseBody>() {
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
