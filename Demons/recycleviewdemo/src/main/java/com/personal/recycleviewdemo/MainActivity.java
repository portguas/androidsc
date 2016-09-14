package com.personal.recycleviewdemo;

import static com.personal.recycleviewdemo.DividerItemDecoration.VERTICAL_LIST;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements MyRecyclerAdapter.OnRecycleItemClickListener{

    private RecyclerView mRecycleView;
    private MyRecyclerAdapter mRecycleAdapter;
    private List<String> mLists;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);

        initDatas();
        initViews();
    }

    private void initViews() {
        mRecycleView = (RecyclerView) findViewById(R.id.rv_main);
        // 设置布局管理
        LinearLayoutManager linearManager = new LinearLayoutManager(this);
        linearManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecycleView.setLayoutManager(linearManager);
        // 设置分割线
        mRecycleView.addItemDecoration(new DividerItemDecoration(this, VERTICAL_LIST));
        // 设置适配器
        mRecycleAdapter = new MyRecyclerAdapter(this, mLists);
        mRecycleView.setAdapter(mRecycleAdapter);
        // 设置点击事件
        mRecycleAdapter.setOnRecycleItemClickListener(this);
    }

    private void initDatas() {
        mLists = new ArrayList<>();
        for (int i = 'A'; i<='z'; i++) {
            mLists.add("" + (char) i);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sample_actions, menu);
        return true;
    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Toast.makeText(this, "SS", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.menu_night_mode_system:
                break;
            case R.id.menu_night_mode_day:
                break;
            case R.id.menu_night_mode_night:
                break;
            case R.id.menu_night_mode_auto:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(View v, int pos) {
        Toast.makeText(this, ((TextView) v).getText() + "**" + pos, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, RecycleDetailActivity.class);
        startActivity(intent);
    }
}
