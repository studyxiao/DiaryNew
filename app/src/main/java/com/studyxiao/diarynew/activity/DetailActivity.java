package com.studyxiao.diarynew.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.studyxiao.diarynew.R;
import com.studyxiao.diarynew.activity.base.BaseActivity;
import com.studyxiao.diarynew.bean.DiaryBean;
import com.studyxiao.diarynew.behavior.AnimationUtil;

import org.litepal.crud.DataSupport;

import cc.trity.floatingactionbutton.FloatingActionsMenu;

public class DetailActivity extends BaseActivity implements View.OnClickListener {

    /**
     * count
     */
    private static final String DATA = "data";

    /**
     * UI
     */
    private TextView mDateView;
    private TextView mTitleView;
    private TextView mContentView;
    private FloatingActionsMenu mFloatMenu;
    private cc.trity.floatingactionbutton.FloatingActionButton mDeleteFab;
    private cc.trity.floatingactionbutton.FloatingActionButton mEditFab;
    private Toolbar mToolbar;
//    private RelativeLayout diaryLayout;

    private RelativeLayout mDetailLayout;

    /**
     * data
     */
    private int mId;
    private DiaryBean bean;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT>Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.color_3399cc));
        }
        setContentView(R.layout.activity_detail_layout);

        Intent intent = getIntent();
        mId = intent.getIntExtra(DATA, 0);
        bean = DataSupport.find(DiaryBean.class, mId);
        initView();
    }

    private void initView() {


        mDateView = (TextView) findViewById(R.id.date_view);
        mTitleView = (TextView) findViewById(R.id.detail_title_view);
        mContentView = (TextView) findViewById(R.id.detail_content_view);
        mDateView.setText(bean.getDate());
        mTitleView.setText(bean.getTitle());
        mContentView.setText(bean.getInfo());

//        diaryLayout = (RelativeLayout) findViewById(R.id.diary_layout);
//        diaryLayout.setOnClickListener(this);

        mFloatMenu = (FloatingActionsMenu) findViewById(R.id.fab_menu);
        mDeleteFab = (cc.trity.floatingactionbutton.FloatingActionButton) findViewById(R.id.fab_delete);
        mDeleteFab.setOnClickListener(this);
        mEditFab = (cc.trity.floatingactionbutton.FloatingActionButton) findViewById(R.id.fab_edit_add);
        mEditFab.setOnClickListener(this);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);

        }

        mDetailLayout = (RelativeLayout) findViewById(R.id.detail_layout);
        if (mDetailLayout.getVisibility()!=View.VISIBLE){
            mDetailLayout.setVisibility(View.VISIBLE);
            mEditFab.setIcon(R.drawable.edit_white);
        }
        mDetailLayout.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab_edit_add:
                mFloatMenu.collapse();
                AddDiaryActivity.startActivity(this, bean, AddDiaryActivity.UPDATE_DIARY);
                break;
            case R.id.fab_delete:
                final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("是否删除日记")
                        .setPositiveButton("是", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //删除日记
                                DataSupport.delete(DiaryBean.class, bean.getId());
                                MainActivity.startActivity(DetailActivity.this);
                            }
                        }).setNegativeButton("否", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
                break;
            case R.id.detail_layout:
                //点击日记界面
                if (mFloatMenu.isExpanded()) {
                    mFloatMenu.collapse();
                }

                if (mFloatMenu.getVisibility() != View.VISIBLE) {
                    AnimationUtil.animateInAndOut(mFloatMenu,AnimationUtil.ANIMATE_IN);
                } else {
                    AnimationUtil.animateInAndOut(mFloatMenu,AnimationUtil.ANIMATE_OUT);
                }
                break;
            case R.id.nested_scrill_view:
                Log.e(TAG, "onClick:点击 " );
                break;

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                break;
        }
        return true;
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        mId = getIntent().getIntExtra(DATA, 0);
        bean = DataSupport.find(DiaryBean.class, mId);
        initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

    public static void startActivity(Context context, int id) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(DATA, id);
        context.startActivity(intent);
    }
}
