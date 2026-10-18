package api;

import dto.RequestDTO;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;

public interface KorisnikApi {
    
    @POST("korisnik/login")
    Call<ResponseBody> login(@Body RequestDTO req);
    
    @GET("korisnik/all")
    Call<ResponseBody> sviKorisnici();

    @POST("korisnik/create")
    Call<ResponseBody> kreirajKorisnika(@Body RequestDTO req);

    @POST("korisnik/dodajNovac")
    Call<ResponseBody> dodajNovac(@Body RequestDTO req);

    @POST("korisnik/promeniAdresu")
    Call<ResponseBody> promeniAdresu(@Body RequestDTO req);
}