package com.example.ferreteria;

import android.app.Activity;
import android.os.Bundle;
import android.widget.GridView;

import java.util.List;


public class GridActivity extends Activity {
    private List<String> strList;
    private GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid);
        initData();
        initView();
    }

    public void initData(){

    }

    private void initView() {
            }
}
