package com.example.hello.victor.movies.model;

import java.io.Serializable;

public class Film implements Serializable {
    private String imdbId;
    private String title;
    private String year;
    private String linkPoster;
    private String plot;
    private String runtime;
    private String genre;
    private String director;
    private String country;


    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getRuntime() {
        return runtime;
    }

    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getLinkPoster() {
        return linkPoster;
    }

    public void setLinkPoster(String linkPoster) {
        this.linkPoster = linkPoster;
    }
//
//    public static Film selfConstruct(String title,String year,String runTime, String genre, String director,String actors, String plot,String country, String linkPoster,String imdbId){
//
//        Film film = new Film();
//        film.setTitle(title);
//        film.setLinkPoster(linkPoster);
//        film.setImdbId(imdbId);
//        film.setYear(year);
//        film.setPlot(plot);
//        return film;
//    }
}
