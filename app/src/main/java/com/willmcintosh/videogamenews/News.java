package com.willmcintosh.videogamenews;

public class News {
    private String articleTitle;
    private String articleSection;
    private String articleAuthor;
    private String articleDate;
    private String articleUrl;

    public News(String articleTitle, String articleSection, String
            articleAuthor, String articleDate, String articleURL) {
        this.articleTitle = articleTitle;
        this.articleSection = articleSection;
        this.articleAuthor = articleAuthor;
        this.articleDate = articleDate;
        this.articleUrl = articleURL;
    }

    public String getArticleTitle() {
        return articleTitle;
    }

    public String getArticleSection() {
        return articleSection;
    }

    public String getArticleAuthor() {
        return articleAuthor;
    }

    public String getArticleDate() {
        return articleDate;
    }

    public String getArticleUrl() {
        return articleUrl;
    }
}
