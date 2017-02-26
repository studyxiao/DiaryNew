package com.studyxiao.diarynew.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.studyxiao.diarynew.R;
import com.studyxiao.diarynew.bean.DiaryBean;
import com.studyxiao.diarynew.util.DateUtil;

import java.util.ArrayList;

/**
 * Created by Studyxiao on 17/2/22.
 */

public class DiaryAdapter extends RecyclerView.Adapter<DiaryAdapter.DairyViewHolder>  {

    private Context mContext;
    private ArrayList<DiaryBean> mData;
    private LayoutInflater mInflate;
    private DairyViewHolder mViewHolder;
    private DairyAdapterListener mListener;

    public DiaryAdapter(Context context, ArrayList<DiaryBean> data, DairyAdapterListener listener) {
        this.mContext = context;
        this.mData = data;
        this.mListener = listener;
        mInflate = LayoutInflater.from(mContext);
    }

    @Override
    public DairyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflate.inflate(R.layout.item_diary_layout, parent, false);
        mViewHolder = new DairyViewHolder(view);
        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(final DairyViewHolder holder, final int position) {
        DiaryBean bean = mData.get(mData.size()-1-position);

        holder.mDate.setText(bean.getDate());
        holder.mTitle.setText(bean.getTitle());
        holder.mInfo.setText(bean.getInfo());
        holder.mDiaryLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DiaryBean bean = mData.get(mData.size()-1-position);
                mListener.onClick(bean);
            }
        });
//        mPosition = holder.getAdapterPosition();
        if (bean.getDate().equals(DateUtil.getSystemDate())){
            holder.mCircle.setImageResource(R.drawable.circle_orange);
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    class DairyViewHolder extends RecyclerView.ViewHolder {

        TextView mDate;
        TextView mTitle;
        TextView mInfo;
        ImageView mCircle;
        RelativeLayout mDateLayout;
        RelativeLayout mDiaryLayout;

        public DairyViewHolder(View itemView) {
            super(itemView);
            mDate = (TextView) itemView.findViewById(R.id.date_view);
            mTitle = (TextView) itemView.findViewById(R.id.title_view);
            mInfo = (TextView) itemView.findViewById(R.id.info_view);
            mCircle = (ImageView) itemView.findViewById(R.id.circle_view);
            mDateLayout = (RelativeLayout) itemView.findViewById(R.id.date_layout);
            mDiaryLayout = (RelativeLayout) itemView.findViewById(R.id.diary_layout);
        }
    }

    public interface DairyAdapterListener{
        void onClick(DiaryBean bean);
    }
}
