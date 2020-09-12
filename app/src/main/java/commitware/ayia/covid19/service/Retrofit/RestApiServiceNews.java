package commitware.ayia.covid19.service.Retrofit;

import commitware.ayia.covid19.Controllers.AppUtils;
import commitware.ayia.covid19.models.NewsResponseWrapper;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RestApiServiceNews {

    @GET(AppUtils.ENDPOINT_TOP_HEADLINE_NEWS)
    Call<NewsResponseWrapper> getNews(@Query("country") String country,
                                      @Query("category") String category,
                                      @Query("apiKey") String apiKey);

    @GET(AppUtils.ENDPOINT_TOP_HEADLINE)
    Call<NewsResponseWrapper> getNewsAll(@Query("language") String language,
                                         @Query("category") String category,
                                         @Query("apiKey") String apiKey);
}
