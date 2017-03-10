package com.victor.ipc.interfaces;

import com.victor.ipc.data.Movie;

import java.util.List;

/**
 * Created by victor on 2015/12/25.
 */
public interface DbInterface {
    void insert(List<Movie> datas);
    void delete(int categoryType, int actionType);
    void update(Movie movie);
    List<Movie> query(int categoryType, int actionType, boolean isLocalDb);
    List<Movie> queryOtherApp(int categoryType, int actionType);

}
