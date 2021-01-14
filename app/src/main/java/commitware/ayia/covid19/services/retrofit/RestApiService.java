package commitware.ayia.covid19.services.retrofit;

import commitware.ayia.covid19.Urls;
import commitware.ayia.covid19.models.Cases;
import commitware.ayia.covid19.models.CasesWrapper;
import commitware.ayia.covid19.models.NewsWrapper;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RestApiService {

    @GET("all/")
    Call<Cases> getCasesGlobal(@Query("yesterday") String yesterday);

    @GET("countries/{country}")
    Call<Cases> getCasesCountry(@Path(value = "country", encoded = true) String country, @Query("yesterday") String yesterday);

    @GET("continents/{continent}")
    Call<Cases> getCasesContinent(@Path(value = "continent", encoded = true) String continent, @Query("yesterday") String yesterday);

    @GET("api")
    Call<CasesWrapper> getCasesState();

    @GET(Urls.ENDPOINT_TOP_HEADLINE)
    Call<NewsWrapper> getNews(@Query("country") String country,
                              @Query("category") String category,
                              @Query("apiKey") String apiKey);

}
