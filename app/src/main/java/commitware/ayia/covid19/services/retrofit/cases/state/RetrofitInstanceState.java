package commitware.ayia.covid19.services.retrofit.cases.state;

import java.util.concurrent.TimeUnit;

import commitware.ayia.covid19.Urls;
import commitware.ayia.covid19.services.retrofit.RestApiService;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstanceState {

    private static final GsonConverterFactory gsonConverterFactory = GsonConverterFactory.create();

    private static Retrofit retrofit = null;

    private static final OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(5, TimeUnit.SECONDS)
            .build();

    public static RestApiService getRetrofitServiceStateCases(){

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(Urls.BASE_URL_STATE)
                    .addConverterFactory(gsonConverterFactory)
                    .client(okHttpClient)
                    .build();
        }

        return retrofit.create(RestApiService.class);
    }


}
