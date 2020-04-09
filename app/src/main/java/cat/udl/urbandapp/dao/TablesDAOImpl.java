package cat.udl.urbandapp.dao;

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
    public Call<ResponseBody> getInstrumentsTable(String auth) {
        return retrofit.create(ITablesDAO.class).getInstrumentsTable(auth);
    }

    @Override
    public Call<Void> setTableUserInstrument(Instrument instrument) {
        Call<Void> call = retrofit.create(ITablesDAO.class).setTableUserInstrument(instrument);
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
