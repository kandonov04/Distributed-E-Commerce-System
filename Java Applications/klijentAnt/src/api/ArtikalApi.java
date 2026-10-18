package api;

import dto.RequestDTO;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;

public interface ArtikalApi {

    @POST("artikal/create")
    Call<ResponseBody> kreirajArtikal(@Body RequestDTO req);

    @POST("artikal/updateCena")
    Call<ResponseBody> updateCena(@Body RequestDTO req);

    @POST("artikal/updatePopust")
    Call<ResponseBody> updatePopust(@Body RequestDTO req);

    @POST("artikal/moji")
    Call<ResponseBody> mojiArtikli(@Body RequestDTO req);
}