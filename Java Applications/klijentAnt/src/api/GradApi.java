package api;

import dto.RequestDTO;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;

public interface GradApi {

    @GET("grad/all")
    Call<ResponseBody> sviGradovi();

    @POST("grad/create")
    Call<ResponseBody> kreirajGrad(@Body RequestDTO req);
}