package commitware.ayia.covid19.services.retrofit.news;

import java.util.List;

import commitware.ayia.covid19.models.News;


public class NewsApiResponse {

    private List<News> newsPosts;
    private Throwable error;

    public NewsApiResponse(List<News> newsPosts) {
        this.newsPosts = newsPosts;
        this.error = null;
    }

    public NewsApiResponse(Throwable error) {
        this.error = error;
        this.newsPosts = null;
    }

    public List<News> getNewsPosts() {
        return newsPosts;
    }

    public void setNewsPosts(List<News> newsPosts) {
        this.newsPosts = newsPosts;
    }

    public Throwable getError() {
        return error;
    }

    public void setError(Throwable error) {
        this.error = error;
    }
}
