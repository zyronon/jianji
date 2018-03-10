package com.ttentau.jianji.adapter;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ttentau.jianji.R;
import com.ttentau.jianji.bean.UserNote;
import com.ttentau.jianji.util.DateUtils;
import com.ttentau.jianji.util.UIUtils;

import java.util.ArrayList;

/**
 * Created by ttentau on 2017/6/13.
 * 2017-07-03 这是用了SwipeLAyout之后的adapter
 * 但是出了一个bug,就调用了notifydatasetchanged之后界面无法滑动
 * 而用listview则不会出现这种情况
 */

public class NoteRecyclerAdapter2 extends RecyclerView.Adapter<NoteRecyclerAdapter2.MyViewHolder> {

    private ArrayList<UserNote> mList;
    private onLClickListener mListener;

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new MyViewHolder(UIUtils.inflate(R.layout.rv_list_item2, viewGroup));

    }

    @SuppressLint("WrongConstant")
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int i) {
        UserNote note = mList.get(i);
        holder.mType.setText(note.type);
        if (note.dbtype == 0) {
            holder.mMoney.setTextColor(Color.RED);
            holder.mMoney.setText(note.money + "￥");
        } else {
            holder.mMoney.setText("+" + note.money + "￥");
            holder.mMoney.setTextColor(UIUtils.getColor(R.color.colorPrimaryDark));
        }
        holder.mTime.setText(DateUtils.formatDayOrMinuteToDisplay(note.minute, false,true,true));
        holder.mComment.setText(note.comment);
        switch (note.from){
            case 0:
                holder.mFrom.setText("付款方式：现金");
                break;
            case 1:
                holder.mFrom.setText("付款方式：微信");
                break;
            case 2:
                holder.mFrom.setText("付款方式：支付宝");
                break;
        }
        if (i == 0) {
            holder.mDate.setVisibility(View.VISIBLE);
            holder.mDate.setText(DateUtils.formatDayOrMinuteToDisplay(note.day, true,true,true));
        } else if (note.day != mList.get(i - 1).day) {
            holder.mDate.setVisibility(View.VISIBLE);
            holder.mDate.setText(DateUtils.formatDayOrMinuteToDisplay(note.day,true, true,true));
        } else {
            holder.mDate.setVisibility(View.GONE);
        }
        if (mList.get(0).day == mList.get(mList.size() - 1).day) {
            holder.mDate.setVisibility(View.GONE);
        } else {
        }

        holder.tv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                notifyDataSetChanged();
                mListener.onDeleteClick(holder.view, i);
            }
        });
        holder.tv_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onEditClick(v, i);
            }
        });
        holder.ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onSClick(holder.mComment,holder.mFrom, i);
            }
        });
//        }

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        public final TextView mDate,tv_edit,tv_delete;
        public final TextView mTime;
        public final TextView mType;
        public final TextView mMoney;
        public final TextView mComment;
        public final TextView mFrom;
        public  LinearLayout ll;
        public  View view;

        public MyViewHolder(View view) {
            super(view);
            this.view=view;
            mTime = (TextView) view.findViewById(R.id.rcv_item_tv_time);
            mType = (TextView) view.findViewById(R.id.rcv_item_tv_type);
            mMoney = (TextView) view.findViewById(R.id.rcv_item_tv_money);
            mComment = (TextView) view.findViewById(R.id.rcv_item_tv_comment);
            mDate = (TextView) view.findViewById(R.id.rcv_item_tv_data);
            mFrom = (TextView) view.findViewById(R.id.rcv_item_tv_from);
            tv_edit = (TextView) view.findViewById(R.id.tv_edit);
            tv_delete = (TextView) view.findViewById(R.id.tv_delete);
            ll = (LinearLayout) view.findViewById(R.id.ll);

        }
    }

    public void setDatas(ArrayList<UserNote> list) {
        mList = list;
    }

    public interface onLClickListener {
        void onDeleteClick(View v, int pos);
        void onEditClick(View v, int pos);
        void onSClick(TextView comment, TextView v, int pos);
    }

    public void setOnDeleteClickListener(onLClickListener listener) {
        mListener = listener;
    }
}
