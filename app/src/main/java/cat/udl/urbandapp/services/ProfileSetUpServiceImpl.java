package cat.udl.urbandapp.services;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.google.gson.JsonObject;

import cat.udl.urbandapp.dao.IProfileSetUpDAO;
import cat.udl.urbandapp.models.RolEnum;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileSetUpServiceImpl {

    private IProfileSetUpDAO profileSetUpDao;

    public final MutableLiveData<Boolean> mSetProfileStep1;

    public ProfileSetUpServiceImpl(){
        profileSetUpDao = new ProfileSetUpDAOImpl();
        mSetProfileStep1 = new MutableLiveData<>();
    }

    public void setUsername(String auth, String username) {
        profileSetUpDao.setUsername(auth, username).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    public void setProfileInfo(String header, JsonObject json) {
        profileSetUpDao.setProfileInfo(header, json).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200) {

                    mSetProfileStep1.setValue(true);
                    Log.d("SetProfileStep1", "ok");
                    Log.d("SetProfileStep1", "Tenim boolean " + mSetProfileStep1.getValue());

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

    public void setUserRol(String auth, RolEnum rol) {
        profileSetUpDao.setUserRol(auth, rol).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200) {

                } else {


                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    public MutableLiveData<Boolean> getLiveDataProfileStep1() {
        return mSetProfileStep1;
    }


}
