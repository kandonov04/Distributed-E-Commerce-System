package api;

import dto.RequestDTO;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;

public interface WishlistApi {

    @POST("listazelja/dodaj")
    Call<ResponseBody> dodajUListuZelja(@Body RequestDTO req);

    @POST("listazelja/obrisi")
    Call<ResponseBody> obrisiIzListeZelja(@Body RequestDTO req);

    @POST("listazelja/get")
    Call<ResponseBody> sadrzajListeZelja(@Body RequestDTO req);
}