package commitware.ayia.covid19.services.retrofit;

import java.util.List;

import commitware.ayia.covid19.utils.Urls;
import commitware.ayia.covid19.models.StatesWrapper;
import commitware.ayia.covid19.models.NewsWrapper;
import commitware.ayia.covid19.models.Continent;
import commitware.ayia.covid19.models.Country;
import commitware.ayia.covid19.models.Globe;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RestApiService {

    @GET("all/")
    Call<Globe> getGlobal(@Query("yesterday") String yesterday);

//    @GET("countries/{country}")
//    Call<InsightNew> getCountry(@Path(value = "country", encoded = true) String country, @Query("yesterday") String yesterday);
//
//    @GET("continents/{continent}")
//    Call<InsightNew> getContinent(@Path(value = "continent", encoded = true) String continent, @Query("yesterday") String yesterday);

    @GET("countries/")
    Call<List<Country>> getCountries(@Query("yesterday") String yesterday);

    @GET("continents/")
    Call<List<Continent>> getContinents(@Query("yesterday") String yesterday);

    @GET("api")
    Call<StatesWrapper> getStates();

    @GET(Urls.ENDPOINT_TOP_HEADLINE)
    Call<NewsWrapper> getNews(@Query("country") String country,
                              @Query("category") String category,
                              @Query("apiKey") String apiKey);

}
