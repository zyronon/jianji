package com.ttentau.jianji.adapter;

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ttentau.jianji.R;
import com.ttentau.jianji.bean.UserNote;
import com.ttentau.jianji.util.DateUtils;
import com.ttentau.jianji.util.UIUtils;
import com.ttentau.jianji.widget.SwipeLayoutManager;

import java.util.ArrayList;

/**
 * Created by ttentau on 2017/7/3.
 * 这是listview的adapter,因为用recyclerview,调用notifydatasetchange之后
 * 界面不能滑动,所以改用listview
 */

public class NoteListViewAdapter extends BaseAdapter {


    private ArrayList<UserNote> mList;
    private onLClickListener mListener;

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int i, View convertView, ViewGroup parent) {
        if (convertView==null){
            convertView= UIUtils.inflate(R.layout.rv_list_item2);
        }
        final MyViewHolder holder = MyViewHolder.getInstence(convertView);

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
        holder.mDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SwipeLayoutManager.getInstance().close();
            }
        });
        holder.tv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                notifyDataSetChanged();
                mListener.onDeleteClick(i);
            }
        });
        holder.tv_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onEditClick(i);
            }
        });
        holder.ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onSClick(holder.mComment,holder.mFrom, i);
            }
        });
        return convertView;
    }
    static class MyViewHolder {

        public final TextView mDate,tv_edit,tv_delete;
        public final TextView mTime;
        public final TextView mType;
        public final TextView mMoney;
        public final TextView mComment;
        public final TextView mFrom;
        public LinearLayout ll;
        public  View view;

        private MyViewHolder(View view) {
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
        public static MyViewHolder getInstence(View view){
            MyViewHolder holder= (MyViewHolder) view.getTag();
            if (holder==null){
                holder=new MyViewHolder(view);
                view.setTag(holder);
            }
            return holder;
        }
    }


    public void setDatas(ArrayList<UserNote> list) {
        mList = list;
    }

    public interface onLClickListener {
        void onDeleteClick(int pos);
        void onEditClick(int pos);
        void onSClick(TextView comment, TextView v, int pos);
    }

    public void setOnDeleteClickListener(onLClickListener listener) {
        mListener = listener;
    }

}
