package cat.udl.urbandapp.dao;

import java.util.List;

import cat.udl.urbandapp.models.Instrument;
import cat.udl.urbandapp.network.RetrofitClientInstance;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;

public class InstrumentsDAOImpl implements InstrumentsDAOI{

    Retrofit retrofit = RetrofitClientInstance.getRetrofitInstance();

    @Override
    public Call<ResponseBody> addInstrument(String auth, List<Instrument> instruments) {
        return retrofit.create(InstrumentsDAOI.class).addInstrument(auth, instruments);
    }

    @Override
    public Call<List<Instrument>> getTableUserInstrument(String auth) {
        return retrofit.create(InstrumentsDAOI.class).getTableUserInstrument(auth);
    }

    @Override
    public Call<ResponseBody> removeInstrument(String auth, String nameInstrument) {
        return retrofit.create(InstrumentsDAOI.class).removeInstrument(auth, nameInstrument);
    }
}
