package api;

import dto.RequestDTO;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;

public interface NarudzbinaApi {

    @POST("narudzbina/plati")
    Call<ResponseBody> plati(@Body RequestDTO req);

    @POST("narudzbina/moje")
    Call<ResponseBody> mojeNarudzbine(@Body RequestDTO req);

    @GET("narudzbina/all")
    Call<ResponseBody> sveNarudzbine();

    @GET("narudzbina/transakcije")
    Call<ResponseBody> sveTransakcije();
}