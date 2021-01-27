package commitware.ayia.covid19.services.retrofit.insight;

import java.util.concurrent.TimeUnit;

import commitware.ayia.covid19.utils.Urls;
import commitware.ayia.covid19.services.retrofit.RestApiService;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {

    //the instance is used by continent,globe and country to receive a response from the api service
    // because they have the same base url
    // InstanceState is used by only state

    private static final GsonConverterFactory gsonConverterFactory = GsonConverterFactory.create();

    private static Retrofit retrofit = null;

    private static final OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(5, TimeUnit.SECONDS)
            .build();


    public static RestApiService getRetrofitServiceInsight(){

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
