package api;

import dto.RequestDTO;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;

public interface KorpaApi {

    @POST("korpa/dodaj")
    Call<ResponseBody> dodajUKorpu(@Body RequestDTO req);

    @POST("korpa/obrisi")
    Call<ResponseBody> obrisiIzKorpe(@Body RequestDTO req);

    @POST("korpa/get")
    Call<ResponseBody> sadrzajKorpe(@Body RequestDTO req);
}