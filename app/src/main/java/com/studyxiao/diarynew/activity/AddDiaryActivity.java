package com.studyxiao.diarynew.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.inputmethodservice.KeyboardView;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.studyxiao.diarynew.R;
import com.studyxiao.diarynew.activity.base.BaseActivity;
import com.studyxiao.diarynew.bean.DiaryBean;
import com.studyxiao.diarynew.listener.KeyboardChangeListener;
import com.studyxiao.diarynew.util.DateUtil;

import org.litepal.crud.DataSupport;

import cc.trity.floatingactionbutton.FloatingActionsMenu;

public class AddDiaryActivity extends BaseActivity implements View.OnClickListener {

    /**
     * UI
     */
    private RelativeLayout mEditLayout;
//    private RelativeLayout mDetailLayout;
    private TextView mDateView;
    private EditText mTitleView;
    private EditText mContentView;
    private FloatingActionsMenu floatMenu;
    private cc.trity.floatingactionbutton.FloatingActionButton addFab;
    private cc.trity.floatingactionbutton.FloatingActionButton backFab;

    private Context mContext;

    /**
     * data
     */
    private DiaryBean bean;

    public final static String DATA = "data";
    public static final int ADD_DIARY = 0; // 新增日记状态
    public static final int UPDATE_DIARY = 1; //修改日记状态
    public static int mStatu = ADD_DIARY;  //默认是新增

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(getResources().getColor(R.color.color_3399cc));

        setContentView(R.layout.activity_edit_layout);
        Toolbar toolbar= (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        mContext = this;
        //界面不自动弹出软键盘
//        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        initView();
    }

    private void initView() {

        mDateView = (TextView) findViewById(R.id.date_view);
        mTitleView = (EditText) findViewById(R.id.et_diary_title);
        mContentView = (EditText) findViewById(R.id.et_diary_content);
        floatMenu = (FloatingActionsMenu) findViewById(R.id.fab_menu);

        addFab = (cc.trity.floatingactionbutton.FloatingActionButton) findViewById(R.id.fab_edit_add);
        addFab.setOnClickListener(this);
        backFab = (cc.trity.floatingactionbutton.FloatingActionButton) findViewById(R.id.fab_delete);
        backFab.setOnClickListener(this);
        mDateView.setText(DateUtil.getSystemDate());


        mEditLayout = (RelativeLayout) findViewById(R.id.edit_layout);
        if (mEditLayout.getVisibility()!=View.VISIBLE){
            mEditLayout.setVisibility(View.VISIBLE);
//            addFab.setIcon(R.drawable.save);
            addFab.setIconDrawable(getResources().getDrawable(R.drawable.save));
        }

        new KeyboardChangeListener(this).setKeyBoardListener(new KeyboardChangeListener.KeyBoardListener() {
            @Override
            public void onKeyboardChange(boolean isShow, int keyboardHeight) {
                if (isShow) {
                    floatMenu.setVisibility(View.INVISIBLE);
                } else {
                    floatMenu.setVisibility(View.VISIBLE);
                }
            }
        });

        if (mStatu == UPDATE_DIARY) {
            Intent intent = getIntent();
            bean = (DiaryBean) intent.getSerializableExtra(DATA);

            mDateView.setText(bean.getDate());
            mTitleView.setText(bean.getTitle());
            mContentView.setText(bean.getInfo());
        }
    }

    @Override
    public void onClick(View v) {

        //没有发生修改，直接返回
        if (!isChanged()){
            if (mStatu==ADD_DIARY){
                MainActivity.startActivity(mContext);
            }else if (mStatu == UPDATE_DIARY){
                DetailActivity.startActivity(mContext,bean.getId());
            }
            return;
        }

        final String date = DateUtil.getSystemDate();
        final String title = mTitleView.getText().toString();
        final String content = mContentView.getText().toString();

        switch (v.getId()) {
            //点击取消按钮
            case R.id.fab_delete:
                if (!title.trim().isEmpty() || !content.trim().isEmpty()) {
                    //有内容
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setMessage("是否保存日记内容？")
                            .setPositiveButton("保存", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (mStatu == ADD_DIARY) {
                                        //添加文章
                                        bean = new DiaryBean();
                                        bean.setDate(date);
                                        bean.setTitle(title);
                                        bean.setInfo(content);
                                        bean.save();
                                        MainActivity.startActivity(mContext);
                                    } else if (mStatu == UPDATE_DIARY) {
                                        //修改文章
                                        bean.setTitle(title);
                                        bean.setInfo(content);
                                        bean.update(bean.getId());
                                        DetailActivity.startActivity(mContext, bean.getId());
                                    }
                                }
                            }).setNegativeButton("不保存", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
//                            if (bean != null) {
//                                DataSupport.delete(DiaryBean.class, bean.getId());
//                            }
                            if (mStatu == ADD_DIARY){
                                MainActivity.startActivity(mContext);
                            }else if (mStatu == UPDATE_DIARY){
                                DetailActivity.startActivity(mContext,bean.getId());
                            }

                        }
                    }).show();

                } else {
                    //没有内容
                    if (mStatu == ADD_DIARY) {
                        MainActivity.startActivity(mContext);
                    } else if (mStatu == UPDATE_DIARY && bean != null) {
                        DetailActivity.startActivity(mContext, bean.getId());
                    }
                }
                break;
            case R.id.fab_edit_add:
                //点击保存按钮
                if (!title.trim().isEmpty() || !content.trim().isEmpty()) {
                    if (mStatu == ADD_DIARY) {
                        //添加文章
                        bean = new DiaryBean();
                        bean.setDate(date);
                        bean.setTitle(title);
                        bean.setInfo(content);
                        bean.save();
                        MainActivity.startActivity(mContext);
                    } else if (mStatu == UPDATE_DIARY) {
                        //修改文章
                        bean.setTitle(title);
                        bean.setInfo(content);
                        bean.update(bean.getId());
                        DetailActivity.startActivity(mContext, bean.getId());
                    }
                }
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                onClick(backFab);
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        onClick(backFab);
    }

    public static void startActivity(Context context, int statu) {
        Intent intent = new Intent(context, AddDiaryActivity.class);
        context.startActivity(intent);
        mStatu = statu;
    }

    public static void startActivity(Context context, DiaryBean bean, int statu) {
        Intent intent = new Intent(context, AddDiaryActivity.class);
        intent.putExtra(DATA, bean);
        context.startActivity(intent);
        mStatu = statu;
    }

    /**
     * 日记是否修改
     *
     * @return
     */
    private boolean isChanged() {

        if (bean == null && (!mTitleView.getText().toString().trim().isEmpty()
                || !mContentView.getText().toString().isEmpty())) {
            return true;
        } else if (bean != null) {
            if (!bean.getTitle().equals(mTitleView.getText().toString())
                    || !bean.getInfo().equals(mContentView.getText().toString())) {
                return true;
            }
        }

        return false;
    }
}
