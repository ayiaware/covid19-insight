package commitware.ayia.covid19.repositories;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;


import commitware.ayia.covid19.BuildConfig;
import commitware.ayia.covid19.Controllers.AppController;
import commitware.ayia.covid19.Controllers.AppUtils;
import commitware.ayia.covid19.models.NewsResponseWrapper;
import commitware.ayia.covid19.service.Retrofit.RestApiResponse;
import commitware.ayia.covid19.service.Retrofit.RestApiServiceNews;
import commitware.ayia.covid19.service.Retrofit.RetrofitInstanceNews;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsRepository {

    private final MutableLiveData<RestApiResponse> liveData = new MutableLiveData<>();

    private Application application;


    public NewsRepository(Application application) {
        this.application = application;
    }


    public MutableLiveData<RestApiResponse> getMutableLiveData(boolean isGetAll) {

        RestApiServiceNews endpoint = RetrofitInstanceNews.getRetrofitServiceNews();

        String code = AppController.getInstance().getCode();
        Call<NewsResponseWrapper> call;
        if (isGetAll) {
            call = endpoint.getNews(code, "health", BuildConfig.API_NEWS);
        } else {
            if (code.equals("")) {
                call = endpoint.getNewsAll(AppUtils.getLanguage(), "health", BuildConfig.API_NEWS);
            } else {
                call = endpoint.getNews(code, "health", BuildConfig.API_NEWS);
            }
        }
        call.enqueue(new Callback<NewsResponseWrapper>() {
            @Override
            public void onResponse(@NonNull Call<NewsResponseWrapper> call, @NonNull Response<NewsResponseWrapper> response) {

                if(response.isSuccessful())
                {
                    NewsResponseWrapper newsResponse =  response.body();
                    if (newsResponse != null  && newsResponse.getArticles() != null) {
                        liveData.postValue(new RestApiResponse(newsResponse.getArticles()));
                    }
                }

            }

            @Override
            public void onFailure(@NonNull Call<NewsResponseWrapper> call, Throwable t) {

                liveData.postValue(new RestApiResponse(t));

            }
        });

        return liveData;
    }
}
