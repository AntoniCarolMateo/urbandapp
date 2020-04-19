package cat.udl.urbandapp.dao;

<<<<<<< Updated upstream
=======
import com.google.gson.JsonObject;

>>>>>>> Stashed changes
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
<<<<<<< Updated upstream
    public Call<ResponseBody> getInstrumentsTable(String auth) {
        return retrofit.create(ITablesDAO.class).getInstrumentsTable(auth);
    }

    @Override
    public Call<Void> setTableUserInstrument(Instrument instrument) {
        Call<Void> call = retrofit.create(ITablesDAO.class).setTableUserInstrument(instrument);
=======
    public Call<Void> addInstrument(String auth, JsonObject instruments) {
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
    public Call<ResponseBody> getTableUserInstrument(String auth) {
        return retrofit.create(ITablesDAO.class).getTableUserInstrument(auth);
    }

    @Override
    public Call<Void> removeInstrument(String auth, JsonObject instrument) {
        Call<Void> call = retrofit.create(ITablesDAO.class).removeInstrument(auth, instrument);
>>>>>>> Stashed changes
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
<<<<<<< Updated upstream
        });
        return call;
    }

=======

        });
        return call;
    }
>>>>>>> Stashed changes
}
