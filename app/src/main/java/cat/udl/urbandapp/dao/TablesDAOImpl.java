package cat.udl.urbandapp.dao;


import androidx.recyclerview.widget.ItemTouchHelper;

import java.util.List;

import cat.udl.urbandapp.models.Instrument;
import cat.udl.urbandapp.models.MusicalGenere;
import cat.udl.urbandapp.network.RetrofitClientInstance;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;

public class TablesDAOImpl implements ITablesDAO {
    Retrofit retrofit = RetrofitClientInstance.getRetrofitInstance();


    @Override
    public Call<List<Instrument>> getTableUserInstrument(String auth) {
        return retrofit.create(ITablesDAO.class).getTableUserInstrument(auth);
    }

    public Call<ResponseBody> addInstrument (String auth, List<Instrument> instruments) {
        return retrofit.create(ITablesDAO.class).addInstrument(auth, instruments);
    }

    @Override
    public Call<ResponseBody> removeInstrument (String auth, String nameInstrument) {
        return retrofit.create(ITablesDAO.class).removeInstrument(auth, nameInstrument);
    }




    //----------------------------------------------------------GENERES


    @Override
    public Call<ResponseBody> addGenere(String auth, List<MusicalGenere> list_generes) {
        return retrofit.create(ITablesDAO.class).addGenere(auth, list_generes);
    }

    @Override
    public Call<ResponseBody> removeGenere(String auth, String nameGenere) {
        return retrofit.create(ITablesDAO.class).removeGenere(auth, nameGenere);
    }

    @Override
    public Call<List<MusicalGenere>> getTableUserGenere(String auth) {
        return retrofit.create(ITablesDAO.class).getTableUserGenere(auth);
    }
}



