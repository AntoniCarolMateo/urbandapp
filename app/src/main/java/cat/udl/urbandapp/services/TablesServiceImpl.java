package cat.udl.urbandapp.services;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import cat.udl.urbandapp.dao.TablesDAOImpl;
import cat.udl.urbandapp.models.Instrument;
import cat.udl.urbandapp.models.User;
import cat.udl.urbandapp.network.RetrofitClientInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class TablesServiceImpl implements TablesServiceI {

    private TablesDAOImpl tablesDAO;
    public MutableLiveData<List<Instrument>> allInsruments;
    Retrofit retrofit = RetrofitClientInstance.getRetrofitInstance();

    public TablesServiceImpl(){
        tablesDAO = new TablesDAOImpl();

    }

    public void getTableUserInstrument(String Auth) {
        //tablesDAO.getInstrumentsTable(Auth).enqueue()

    }

    @Override
    public void setTableUserInstrument(Instrument instrument) {

    }

    @Override
    public MutableLiveData<List<Instrument>> getInstrumentsList() {
        return allInsruments;
    }
}
