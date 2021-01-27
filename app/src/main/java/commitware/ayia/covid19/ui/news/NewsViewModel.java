package commitware.ayia.covid19.ui.news;

import android.app.Application;

import androidx.annotation.NonNull;

import androidx.lifecycle.AndroidViewModel;

import androidx.lifecycle.LiveData;

import androidx.lifecycle.MediatorLiveData;

import androidx.lifecycle.Observer;

import commitware.ayia.covid19.repositories.NewsRepository;

import commitware.ayia.covid19.services.retrofit.news.NewsApiResponse;


public class NewsViewModel extends AndroidViewModel {

    private final MediatorLiveData<NewsApiResponse> mObservableNews;

    private boolean isGetAll;

    public NewsViewModel(@NonNull Application application) {

        super(application);

        NewsRepository mRepository = new NewsRepository(application);

        mObservableNews = new MediatorLiveData<>();

        mObservableNews.addSource(mRepository.getMutableLiveData(isGetAll()), new Observer<NewsApiResponse>() {
            @Override
            public void onChanged(NewsApiResponse newsApiResponse) {

                mObservableNews.setValue(newsApiResponse);

            }

        });

        mRepository.getMutableLiveData(isGetAll());

    }

    public LiveData<NewsApiResponse> getNewsData() {

     return mObservableNews;

    }

    public boolean isGetAll() {
        return isGetAll;
    }

    public void setGetAll(boolean getAll) {

        isGetAll = getAll;
    }
}