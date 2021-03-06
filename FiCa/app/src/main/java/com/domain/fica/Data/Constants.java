package com.domain.fica.Data;

public class Constants {

    //API Constants **DO NOT CHANGE**
    public static final String MOVIES = "results";
    public static final String ID = "id";
    public static final String TITLE = "title";
    public static final String ADULT = "adult";
    public static final String OVERVIEW = "overview";
    public static final String POSTERURL = "poster_path";
    public static final String LANGUAGE = "original_language";
    public static final String BACKDROPURL = "backdrop_path";
    public static final String GENREIDS = "genre_ids";
    public static final String RELEASEDATE = "release_date";
    public static final String RATING = "popularity";

    //URL GET COMPONENTS
    public static final String URL1 = "https://api.themoviedb.org/3/discover/movie?";
    public static final String URL2 = "&api_key=179637ebc077ce9fa33365aa808e3985";
    public static final String URLNA = "https://i.imgur.com/KhFyGwd.png";
    public static final String URLBLK = "https://i.imgur.com/6hhIzRr.png";
    public static final String PAGE = "&page=";
    public static final String ISADULT = "&include_adult=";
    public static final String FALSE = "false";
    public static final String TRUE = "true";
    public static final String GENRE = "&with_genres=";
    public static final String URL_IMG_L="http://image.tmdb.org/t/p/original/";
    public static final String URL_IMG_S="http://image.tmdb.org/t/p/w300/";

    public static String AdultBool = "true";
    public static String genreId = "";
    public static final String SORT = "&sort_by=";
    public static final String SORT_ALPH_ASC = "original_title.asc";
    public static final String SORT_ALPH_DESC = "original_title.desc";
    public static final String SORT_RELEASE_ASC = "release_date.asc";
    public static final String SORT_RELEASE_DESC = "release_date.desc";
    public static final String SORT_RATING_ASC = "popularity.asc";
    public static final String SORT_RATING_DESC = "popularity.desc";
}
