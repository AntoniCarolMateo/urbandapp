package cat.udl.urbandapp.dao;

import java.util.List;

import cat.udl.urbandapp.models.MusicalGenere;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface IMusicalGenresDAO {
    //----------------------------------------------------------GENERES
    @POST("users/profile/musical_genres/add")
    Call<ResponseBody> addGenere (@Header("Authorization") String auth, @Body List<MusicalGenere> list_generes);

    @DELETE("users/profile/musical_genres/delete/{name}")
    Call<ResponseBody> removeGenere(@Header("Authorization") String auth, @Path("name") String nameGenere);

    @GET("users/profile/musical_genres/list")
    Call<List<MusicalGenere>> getTableUserGenere(@Header("Authorization") String auth);
}
