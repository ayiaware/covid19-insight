package commitware.ayia.covid19.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NewsWrapper {

    @SerializedName("articles")
    private List<News> articles;

    public NewsWrapper(List<News> articles) {
        this.articles = articles;
    }

    public List<News> getArticles() {
        return articles;
    }

}