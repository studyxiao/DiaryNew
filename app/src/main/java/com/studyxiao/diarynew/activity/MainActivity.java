package com.studyxiao.diarynew.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.studyxiao.diarynew.R;
import com.studyxiao.diarynew.activity.base.BaseActivity;
import com.studyxiao.diarynew.adapter.DiaryAdapter;
import com.studyxiao.diarynew.bean.DiaryBean;
import com.studyxiao.diarynew.util.DateUtil;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    /**
     * UI
     */
    private RelativeLayout mHeaderLayout;
    private TextView mHeaderDate;
    private RecyclerView mRecyclerView;
    private FloatingActionButton fab;

    /**
     * Data
     */
    private DiaryAdapter mAdaper;
    private ArrayList<DiaryBean> mData = new ArrayList<>();

    private Context mContext;

    private boolean isWrited = false;//今天是否写日记了

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_layout);
        mContext = this;
        initData();

        initView();

    }

    private void initView() {
        mHeaderLayout = (RelativeLayout) findViewById(R.id.head_layout);
        mHeaderLayout.setOnClickListener(this);
        mHeaderDate = (TextView) findViewById(R.id.head_date_view);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mAdaper = new DiaryAdapter(this, mData, new DiaryAdapter.DairyAdapterListener() {
            @Override
            public void onClick(DiaryBean bean) {
                //打开日记
                DetailActivity.startActivity(mContext, bean.getId());
            }
        });
        LinearLayoutManager manager = new LinearLayoutManager(this);
        mRecyclerView.setAdapter(mAdaper);
        mRecyclerView.setLayoutManager(manager);

        fab = (FloatingActionButton) findViewById(R.id.float_view);
        fab.setOnClickListener(this);

        mHeaderDate.setText(DateUtil.getSystemDate());
        //今日已写，则隐藏头部
        if (writedToday()) {
            mHeaderLayout.setVisibility(View.GONE);
        } else {
            mHeaderLayout.setVisibility(View.VISIBLE);
        }
    }

    private void initData() {
        List<DiaryBean> list = DataSupport.findAll(DiaryBean.class);
        if (list != null) {
            mData.clear();
            mData = (ArrayList<DiaryBean>) list;
        }

    }

    /**
     * 是否写过今天的日记了
     *
     * @return
     */
    private boolean writedToday() {
        List<DiaryBean> temp = DataSupport.where("date like ?", DateUtil.getSystemDate()).find(DiaryBean.class);
        if (temp == null || temp.size() == 0) {

            //没写过
            isWrited = false;
            return false;
        } else {
            //今天已经写过了
            isWrited = true;
            return true;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.float_view:
                //新建笔记Activity
                if (writedToday()) {
                    Toast.makeText(mContext, R.string.toast_writed, Toast.LENGTH_SHORT).show();
                } else {
                    AddDiaryActivity.startActivity(mContext, AddDiaryActivity.ADD_DIARY);
                }
                break;
            case R.id.head_layout:
                onClick(fab);
                break;
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        mData.clear();
        mData.addAll((ArrayList<DiaryBean>) DataSupport.findAll(DiaryBean.class));
        if (writedToday()) {
            mHeaderLayout.setVisibility(View.GONE);
        } else {
            mHeaderLayout.setVisibility(View.VISIBLE);
        }
        mAdaper.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        //再次启动时，不再从splash启动
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);

    }
}
