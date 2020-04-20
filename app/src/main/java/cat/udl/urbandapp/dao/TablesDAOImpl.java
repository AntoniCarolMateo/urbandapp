package cat.udl.urbandapp.dao;


import com.google.gson.JsonObject;

import cat.udl.urbandapp.models.Instrument;
import cat.udl.urbandapp.network.RetrofitClientInstance;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class TablesDAOImpl implements ITablesDAO {
    Retrofit retrofit = RetrofitClientInstance.getRetrofitInstance();


    @Override
    public Call<ResponseBody> getTableUserInstrument(String auth) {
        return retrofit.create(ITablesDAO.class).getTableUserInstrument(auth);
    }

    public Call<Void> addInstrument (String auth, JsonObject instruments){
            Call<Void> call = retrofit.create(ITablesDAO.class).addInstrument(auth, instruments);
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
        public Call<Void> removeInstrument (String auth, JsonObject instrument){
            Call<Void> call = retrofit.create(ITablesDAO.class).removeInstrument(auth, instrument);

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


    }



