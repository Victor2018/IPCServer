package com.victor.ipc.server;import android.support.v7.app.AppCompatActivity;import android.os.Bundle;import android.support.v7.widget.LinearLayoutManager;import android.support.v7.widget.RecyclerView;import com.victor.ipc.adapter.MovieAdapter;import com.victor.ipc.dao.DbDao;import com.victor.ipc.data.Movie;import com.victor.ipc.util.Constant;import java.util.ArrayList;import java.util.List;public class MainActivity extends AppCompatActivity {    private RecyclerView mRecyclerView;    private LinearLayoutManager linearLayoutManager;    private MovieAdapter movieAdapter;    private List<Movie> movieList = new ArrayList<>();    private DbDao dbDao;    @Override    protected void onCreate(Bundle savedInstanceState) {        super.onCreate(savedInstanceState);        setContentView(R.layout.activity_main);        initialize();        initData();    }    private void initialize () {        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);        linearLayoutManager = new LinearLayoutManager(this);//这里用线性显示 类似于listview        mRecyclerView.setLayoutManager(linearLayoutManager);        movieAdapter = new MovieAdapter(getApplicationContext(),null);        movieAdapter.setHeaderVisible(false);        movieAdapter.setFooterVisible(false);        movieAdapter.setDatas(movieList);        mRecyclerView.setAdapter(movieAdapter);        dbDao = new DbDao(this);    }    private void initData () {        List<Movie> tmpList = dbDao.query(Constant.Action.LIVE_ACTION,Constant.Action.LIVE_ACTION,true);        if (tmpList.size() == 0){            for(int i=0;i<5;i++){                Movie channel = new Movie();                channel.setName("从你的全世界路过" + i);                channel.setIcon(i + "xlj.png");                channel.setImg(i + "xlj1.png");                channel.setServer("192.168.1." + i);                channel.setChannelId("IPCSERVERIPCSERVER" + i);                channel.setLang("CHINESE");                channel.setArea("CHINA");                channel.setYear("2015");                channel.setTime("1:30");                channel.setType("爱情ipcserver");                channel.setDirector("张嘉佳");                channel.setMemo("影片简介：" + i);                tmpList.add(channel);            }            dbDao.insert(tmpList);        }        movieList.addAll(tmpList);        movieAdapter.notifyDataSetChanged();    }}