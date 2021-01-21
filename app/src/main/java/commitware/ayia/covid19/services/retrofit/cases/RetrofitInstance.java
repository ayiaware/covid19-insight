package commitware.ayia.covid19.services.retrofit.cases;

import java.util.concurrent.TimeUnit;

import commitware.ayia.covid19.Urls;
import commitware.ayia.covid19.services.retrofit.RestApiService;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {

    private static final GsonConverterFactory gsonConverterFactory = GsonConverterFactory.create();

    private static Retrofit retrofit = null;

    private static final OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(5, TimeUnit.SECONDS)
            .build();



    public static RestApiService getRetrofitServiceCases(){

        String BASE_URL = Urls.BASE_URL_CASES;

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(gsonConverterFactory)
                    .client(okHttpClient)
                    .build();
        }

        return retrofit.create(RestApiService.class);
    }



}
