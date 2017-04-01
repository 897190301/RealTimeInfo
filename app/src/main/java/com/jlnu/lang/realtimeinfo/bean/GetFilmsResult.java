package com.jlnu.lang.realtimeinfo.bean;

/**
 * Created by lilang on 2017/4/1.
 */

public class GetFilmsResult extends BaseInfo {

    FilmData result;

    public FilmData getFilmData() {
        return result;
    }

    public void setFilmData(FilmData result) {
        this.result = result;
    }
}
