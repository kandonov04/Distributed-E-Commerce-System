package klijentant;

import api.*;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.jaxb.JaxbConverterFactory;
import java.util.Base64;
import java.util.concurrent.TimeUnit;

public class RetrofitClient {

    private static final String BASE_URL = "http://localhost:8080/centralni2/api/";
    private static RetrofitClient instance;

    private final GradApi gradApi;
    private final KorisnikApi korisnikApi;
    private final ArtikalApi artikalApi;
    private final KorpaApi korpaApi;
    private final WishlistApi wishlistApi;
    private final NarudzbinaApi narudzbineApi;
    private final KategorijaApi kategorijaApi;

    private RetrofitClient(String username, String password) {
        String credentials = username + ":" + password;
        String encoded = Base64.getEncoder().encodeToString(credentials.getBytes());
        final String authHeader = "Basic " + encoded;

        OkHttpClient client = new OkHttpClient.Builder()
            .addInterceptor(chain -> {
                if (username.isEmpty() && password.isEmpty()) {
                    return chain.proceed(chain.request());
                }
                return chain.proceed(
                    chain.request().newBuilder()
                         .header("Authorization", authHeader)
                         .header("Content-Type", "application/xml")
                         .header("Accept", "application/xml")
                         .build()
                );
            })
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .build();

        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(JaxbConverterFactory.create())
            .build();

        gradApi = retrofit.create(GradApi.class);
        korisnikApi = retrofit.create(KorisnikApi.class);
        artikalApi = retrofit.create(ArtikalApi.class);
        korpaApi = retrofit.create(KorpaApi.class);
        wishlistApi = retrofit.create(WishlistApi.class);
        narudzbineApi = retrofit.create(NarudzbinaApi.class);
        kategorijaApi = retrofit.create(KategorijaApi.class);
    }

    public static void init(String username, String password) {
        instance = new RetrofitClient(username, password);
    }

    public static RetrofitClient getInstance() {
        return instance;
    }

    public GradApi grad() {
        return gradApi;
    }
    public KorisnikApi korisnik() {
        return korisnikApi;
    }
    public ArtikalApi artikal() {
        return artikalApi;
    }
    public KorpaApi korpa() {
        return korpaApi;
    }
    public WishlistApi wishlist() {
        return wishlistApi;
    }
    public NarudzbinaApi narudzbine() {
        return narudzbineApi;
    }
    public KategorijaApi kategorija() {
        return kategorijaApi;
    }
}
