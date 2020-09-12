package commitware.ayia.covid19.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NewsResponseWrapper {

    @SerializedName("status")

    private String status;

    @SerializedName("totalResult")
    private int totalResult;


    @SerializedName("articles")
    private List<News> articles;


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTotalResult() {
        return totalResult;
    }

    public void setTotalResult(int totalResult) {
        this.totalResult = totalResult;
    }

    public NewsResponseWrapper(List<News> articles) {
        this.articles = articles;
    }

    public List<News> getArticles() {
        return articles;
    }

    public void setArticles(List<News> articles) {
        this.articles = articles;
    }
}