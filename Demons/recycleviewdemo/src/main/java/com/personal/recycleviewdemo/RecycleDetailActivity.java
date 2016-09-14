package com.personal.recycleviewdemo;

import java.util.ArrayList;
import java.util.List;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class RecycleDetailActivity extends AppCompatActivity {

    private RecyclerView mRecycleViewDetail;
    private MyRecyclerAdapter mAdapterDetail;
    private List<String> mDetailDatas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle_detail);

        initDatas();

        initViews();
    }

    private void initViews() {
        /**
         *  toolbar、
         *  设置toolbar的返回按钮的作用方法
         *  1. setDisplayHomeAsUpEnabled(true); 显示那个返回按钮, 然后再Manifest设置这个activity的parentactivity即可
         *  2. setNavigationIcon设置图标, setNavigationOnClickListener设置点击事件, 这些操作必须在setSupportActionBar之后才会有效果
         *  3. onOptionsItemSelected重写此方法, 需要设setDisplayHomeAsUpEnabled, 这样那个返回按钮的id就是android.R.id.home
         */
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_detail);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_event);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(RecycleDetailActivity.this, "onClick", Toast.LENGTH_SHORT).show();
            }
        });

        // CollapsingToolbarLayout
        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle("Detail");

        loadBackdrop();
        // recycle view
        mRecycleViewDetail = (RecyclerView) findViewById(R.id.rv_main_detail);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecycleViewDetail.setLayoutManager(manager);
        mAdapterDetail = new MyRecyclerAdapter(this, mDetailDatas);
        mRecycleViewDetail.setAdapter(mAdapterDetail);
    }

    private void loadBackdrop() {
        final ImageView imageView = (ImageView) findViewById(R.id.backdrop);
        imageView.setImageResource(R.drawable.cheese_1);
//        Glide.with(this).load(Cheeses.getRandomCheeseDrawable()).centerCrop().into(imageView);
    }

    private void initDatas() {
        mDetailDatas = new ArrayList<>();
        for (int i = 'A'; i<='z'; i++) {
            mDetailDatas.add("" + (char) i);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sample_actions, menu);
        return true;
    }

    /**
     * This hook is called whenever an item in your options menu is selected.
     * The default implementation simply returns false to have the normal
     * processing happen (calling the item's Runnable or sending a message to
     * its Handler as appropriate).  You can use this method for any items
     * for which you would like to do processing without those other
     * facilities.
     * <p>
     * <p>Derived classes should call through to the base class for it to
     * perform the default menu handling.</p>
     *
     * @param item The menu item that was selected.
     *
     * @return boolean Return false to allow normal menu processing to
     * proceed, true to consume it here.
     *
     * @see #onCreateOptionsMenu
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Toast.makeText(this, "detail", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
