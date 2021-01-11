package commitware.ayia.covid19.service.Retrofit;

import java.util.concurrent.TimeUnit;

import commitware.ayia.covid19.AppUtils;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstanceNews {

    private static final GsonConverterFactory gsonConverterFactory = GsonConverterFactory.create();

    private static Retrofit retrofit = null;

    private static final OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(5, TimeUnit.SECONDS)
            .build();


    public static RestApiServiceNews getRetrofitServiceNews(){

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(AppUtils.BASE_URL_NEWS)
                    .addConverterFactory(gsonConverterFactory)
                    .client(okHttpClient)
                    .build();
        }

        return retrofit.create(RestApiServiceNews.class);
    }

}
