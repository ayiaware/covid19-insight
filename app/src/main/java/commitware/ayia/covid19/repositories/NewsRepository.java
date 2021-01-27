package commitware.ayia.covid19.repositories;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;


import commitware.ayia.covid19.BuildConfig;
import commitware.ayia.covid19.BasicApp;
import commitware.ayia.covid19.utils.AppUtils;
import commitware.ayia.covid19.models.NewsWrapper;
import commitware.ayia.covid19.services.retrofit.RestApiService;
import commitware.ayia.covid19.services.retrofit.news.NewsApiResponse;
import commitware.ayia.covid19.services.retrofit.news.RetrofitInstanceNews;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsRepository {

    private final MutableLiveData<NewsApiResponse> liveData = new MutableLiveData<>();

    private final Application application;


    public NewsRepository(Application application) {
        this.application = application;
    }


    public MutableLiveData<NewsApiResponse> getMutableLiveData(boolean isGetAll) {

        RestApiService endpoint = RetrofitInstanceNews.getRetrofitServiceNews();

        String code = BasicApp.getInstance().getLocation().getCode();

        Call<NewsWrapper> call;

        if (isGetAll) {
            call = endpoint.getNews(code, "health", BuildConfig.API_NEWS);
        } else {
            if (code.equals("")) {
                call = endpoint.getNews(AppUtils.getLanguage(), "health", BuildConfig.API_NEWS);
            } else {
                call = endpoint.getNews(code, "health", BuildConfig.API_NEWS);
            }
        }

        call.enqueue(new Callback<NewsWrapper>() {
            @Override
            public void onResponse(@NonNull Call<NewsWrapper> call, @NonNull Response<NewsWrapper> response) {

                if(response.isSuccessful()) {
                    NewsWrapper newsResponse =  response.body();
                    if (newsResponse != null  && newsResponse.getArticles() != null) {
                        liveData.postValue(new NewsApiResponse(newsResponse.getArticles()));
                    }
                }

            }

            @Override
            public void onFailure(@NonNull Call<NewsWrapper> call, Throwable t) {

                liveData.postValue(new NewsApiResponse(t));

            }
        });

        return liveData;
    }


}
