package cat.udl.urbandapp.dao;

import java.util.List;

import cat.udl.urbandapp.models.MusicalGenere;
import cat.udl.urbandapp.network.RetrofitClientInstance;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;

public class MusicalGenreDAOImpl implements IMusicalGenresDAO {

    Retrofit retrofit = RetrofitClientInstance.getRetrofitInstance();

    @Override
    public Call<ResponseBody> addGenere(String auth, List<MusicalGenere> list_generes) {
        return retrofit.create(IMusicalGenresDAO.class).addGenere(auth, list_generes);
    }

    @Override
    public Call<ResponseBody> removeGenere(String auth, String nameGenere) {
        return retrofit.create(IMusicalGenresDAO.class).removeGenere(auth, nameGenere);
    }

    @Override
    public Call<List<MusicalGenere>> getTableUserGenere(String auth) {
        return retrofit.create(IMusicalGenresDAO.class).getTableUserGenere(auth);
    }
}
