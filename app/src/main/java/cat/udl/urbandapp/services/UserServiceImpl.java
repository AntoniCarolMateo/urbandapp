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
import cat.udl.urbandapp.models.Instrument;
import cat.udl.urbandapp.models.MusicalGenere;
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
    public final MutableLiveData<User> mUserSubscribed;
    public final MutableLiveData<Boolean> mSubscription;
    public final MutableLiveData<Boolean> mDeleteSubscription;
    public final MutableLiveData<User> mMatch;

    public final MutableLiveData<Boolean> mFirstTime;

    public UserServiceImpl() {
        userDAO = new UserDAOImpl();
        mResponseToken = new MutableLiveData<>();
        mUser = new MutableLiveData<>();
        mAllUsers = new MutableLiveData<>();
        mRegister = new MutableLiveData<>();
        mSetProfileStep1 = new MutableLiveData<>();
        mFirstTime = new MutableLiveData<>();
        mUserSubscribed = new MutableLiveData<>();
        mSubscription = new MutableLiveData<>();
        mDeleteSubscription  = new MutableLiveData<>();
        mMatch = new MutableLiveData<>();

    }
    public MutableLiveData<String> getLiveDataToken(){
        return mResponseToken;
    }
    public MutableLiveData<User> getLiveDataUser(){
        return mUser;
    }

    public MutableLiveData<User> getLiveDataUserSubscription(){
        return mUserSubscribed;
    }
    public MutableLiveData<Boolean> getLiveDataSubscription(){
        return mSubscription;
    }
    public MutableLiveData<Boolean> getLiveDataDeleteSubscription(){
        return mDeleteSubscription;
    }

    public MutableLiveData<Boolean> getLiveDataFirstSetup() { return mFirstTime; }

    public MutableLiveData<List<User>> getLiveDataAllUsers(){
        return mAllUsers;
    }

    @Override
    public MutableLiveData<Boolean> getLiveDataRegister() {
        return mRegister;
    }

    @Override
    public MutableLiveData<Boolean> getLiveDataProfileStep1() {
        return mSetProfileStep1;
    }

    @Override
    public MutableLiveData<Boolean> getLiveDataFirstTime() {
        return mFirstTime;
    }

    @Override
    public MutableLiveData<User> getLiveDataMatch(){
        Log.d("DefaultActivity","userServiceImpls match");
        return mMatch;
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
    public void getMatch(final String Auth){

        userDAO.getMatch(Auth).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200 ){
                    try {

                        String respuestaBody = response.body().string();
                        Log.d("getMatch", "Ok getMatch");
                        Log.d("getMatch", respuestaBody);
                        JSONObject mUserjson = new JSONObject(respuestaBody);
                        Log.d("getMatch", "El JSONObject es: " + mUserjson.toString());
                        User u = new User();

                        u.setUsername(mUserjson.getString("username"));
                        u.setCreated_at(mUserjson.getString("created_at"));

                        Log.d("getMatch", u.getUsername());
                        Log.d("getMatch", u.getCreated_at());

                        mMatch.setValue(u);

                    } catch (IOException | JSONException e) {
                        e.printStackTrace();
                    }
                }
                else{
                    mMatch.setValue(new User());
                    Log.d("getMatch", "Error en la call a la API llamada retornada con codigo" + response.code() + " message:" + response.message() );
                    Log.d("getMatch", "header es: " + Auth);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("getMatch", t.getMessage().toString());
                mMatch.setValue(new User());
            }
        });
    }

    @Override
    public void firstTimeProfileSetUp(String header) {
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
    public void getFirstTimeBoolean(String auth) {
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
    public void showPrivateProfile(String auth) {
        userDAO.showPrivateProfile(auth).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.code() == 200) {
                    User usr = response.body();
                    Log.d("showPrivateProfile", "we got user :  " + usr.toString() + "exp :" + usr.getGen_exp());
                    mUser.setValue(usr);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                mUser.setValue(null);
                Log.d("showPrivateProfile", "Something wrong happened");
            }
        });
    }

    @Override
    public void setUsername(String auth, String username) {
        userDAO.setUsername(auth, username).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    @Override
    public void getFilteredUsers(String header, List<String> instruments, List<String> gen) {
        Log.d("KELOKE", instruments.toString() + " and " + gen.toString());
        userDAO.getFilteredUsers(header, instruments, gen).enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                // ACTIUALIZAR LA Mutavle lie data dels user filtered
                if (response.code() == 200) {
                    List<User> mlist = response.body();
                    mAllUsers.setValue(mlist);
                    Log.d("UserServiceImpl", mAllUsers.getValue().toString());
                }
                else{
                    mAllUsers.setValue(new ArrayList<User>());
                    Log.d("values", "kepasoooo");
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                mAllUsers.setValue(new ArrayList<User>());
                Log.d("UserServiceImpl", t.toString());
            }
        });
    }




    @Override
    public void getAllUsers(String auth){

        userDAO.getAllUsers(auth).enqueue(new Callback<ResponseBody>() {
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

    @Override
    public void getInfoSubscribed(String Auth, String username) {
        userDAO.getInfoSubscribed(Auth, username).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200) {
                    // mResponseWorked.setValue(true);
                    String respuestaBody = null;
                    try {
                        respuestaBody = response.body().string();
                        Log.d("getInfoSubscribed ", "respuesta: " + respuestaBody);

                        JSONArray respuesta = new JSONArray(respuestaBody);
                        JSONObject mUserjson = respuesta.getJSONObject(0);
                        User u = new User();

                        u.setUsername(mUserjson.getString("username"));
                        //Log.d("getInfoSubscribed ", "usernmae: "  +u.getUsername());
                        u.setGenere(mUserjson.getString("genere"));
                        //Log.d("getInfoSubscribed ", "genere: "  +u.getGenere());
                        u.setDescription(mUserjson.getString("description"));
                        JSONObject subscription = respuesta.getJSONObject(1);
                        Boolean sub = subscription.getBoolean("subscribed");
                        u.setHasSubscribed(sub);
                        mUserSubscribed.setValue(u);
                        //Log.d("getInfoSubscribed ", "description: "  +u.getDescription());
                        //Log.d("getInfoSubscribed ", "sub: "  +sub);


                    } catch (IOException | JSONException e) {
                        e.printStackTrace();
                        mUserSubscribed.setValue(new User());
                    }

                    Log.d("getInfoSubscribed ", "getInfoSubscribed succesfully");
                } else {
                    //mResponseWorked.setValue(false);
                    mUserSubscribed.setValue(new User());
                    Log.d("getInfoSubscribed", "Failed to getInfoSubscribed");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("getInfoSubscribed", t.getMessage().toString());
            }
        });
    }

    @Override
    public void userSubscribe(String Auth, String username) {
        userDAO.userSubscribe(Auth,username).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200){
                    // mResponseWorked.setValue(true);
                    String respuestaBody = null;

                    try {
                        respuestaBody = response.body().string();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Log.d("userSubscribe ", "respuesta: "  +respuestaBody);

                    if(Integer.parseInt(respuestaBody) == 1){
                        Log.d("userSubscribe ", "respuesta es OK" );
                        mSubscription.setValue(true);
                    }
                    else{
                        Log.d("userSubscribe ", "la respuesta NO es OK, es: " + respuestaBody );
                        mSubscription.setValue(false);
                    }



                    Log.d("userSubscribe ", "userSubscribe succesfully");
                }else{
                    //mResponseWorked.setValue(false);
                    mSubscription.setValue(false);

                    Log.d("userSubscribe", "Failed to userSubscribe");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("userSubscribe", t.getMessage().toString());
            }
        });
    }

    @Override
    public void userDeleteSubscribe(String Auth, String username) {
        userDAO.userDeleteSubscribe(Auth, username).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200) {
                    // mResponseWorked.setValue(true);
                    String respuestaBody = null;

                    try {
                        respuestaBody = response.body().string();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Log.d("userDeleteSubscribe ", "respuesta: " + respuestaBody);

                    if (Integer.parseInt(respuestaBody) == 1) {
                        Log.d("userDeleteSubscribe ", "respuesta es 1");
                        mDeleteSubscription.setValue(true);
                    } else {
                        Log.d("userDeleteSubscribe ", "la respuesta NO es correcta, es: " + respuestaBody);
                        mDeleteSubscription.setValue(false);
                    }


                    Log.d("userDeleteSubscribe ", "userDeleteSubscribe succesfully");
                } else {
                    //mResponseWorked.setValue(false);
                    mDeleteSubscription.setValue(false);

                    Log.d("userDeleteSubscribe", "Failed to userDeleteSubscribe");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("userDeleteSubscribe", t.getMessage().toString());
            }
        });
    }
}

