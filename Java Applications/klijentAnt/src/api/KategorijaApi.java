package api;

import dto.RequestDTO;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;

public interface KategorijaApi {

    @GET("kategorija/all")
    Call<ResponseBody> sveKategorije();

    @POST("kategorija/create")
    Call<ResponseBody> kreirajKategoriju(@Body RequestDTO req);
}