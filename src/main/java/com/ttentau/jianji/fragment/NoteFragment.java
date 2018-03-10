package com.ttentau.jianji.fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.view.View;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;

import com.ttentau.jianji.R;
import com.ttentau.jianji.adapter.DialogAdapter;
import com.ttentau.jianji.adapter.NoteListViewAdapter;
import com.ttentau.jianji.base.BaseFragment;
import com.ttentau.jianji.bean.UserNote;
import com.ttentau.jianji.db.NoteDb;
import com.ttentau.jianji.util.DateUtils;
import com.ttentau.jianji.util.SpUtils;
import com.ttentau.jianji.util.TypeDataUtils;
import com.ttentau.jianji.util.UIUtils;
import com.ttentau.jianji.widget.SwipeLayoutManager;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ttentau on 2017/6/13.
 */

public class NoteFragment extends BaseFragment implements View.OnClickListener {

    @BindView(R.id.main_vp1_rcv)
    ListView mMain_vp1_rcv;
    @BindView(R.id.rv_head_tv_time)
    TextView mRv_head_tv_time;
    @BindView(R.id.income_sum)
    TextView mIncome_sum;
    @BindView(R.id.outlay_sum)
    TextView mOutlay_sum;
    @BindView(R.id.remainder_sum)
    TextView mRemainder_sum;
    @BindView(R.id.today_income_sum)
    TextView mToday_income_sum;
    @BindView(R.id.today_outday_sum)
    TextView mToday_outday_sum;


    private final static int WEEK = 0;
    private final static int DAY = 1;
    private final static int MOUTH = 2;
    private final static int YEAR = 3;
    private final static int ALL = 4;
    private NoteListViewAdapter mAdapter;
    private LinearLayoutManager mLlmaanger;
    private ViewPager mDialog_vp;
    private Dialog mDialog;
    private DialogAdapter mDlAdapter;
    private ArrayList<View> mDialog_views;
    private ArrayList<UserNote> mQuery;
    private NoteDb mInstence;
    private int toDay;
    private int mCurrentType;
    private PopupWindow mPp;

    @Override
    public View initView() {
        View view = View.inflate(getActivity(), R.layout.main_vp1, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void setData() {
        mRv_head_tv_time.setText("最近7天");

        mAdapter.setDatas(mQuery);
//        mMain_vp1_rcv.setLayoutManager(mLlmaanger);
        mMain_vp1_rcv.setAdapter(mAdapter);
        mMain_vp1_rcv.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState==SCROLL_STATE_TOUCH_SCROLL){
                    SwipeLayoutManager.getInstance().close();
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            }
        });
//        FristQuerySync sync = new FristQuerySync(mAdapter, mInstence);
//        sync.execute();
        mAdapter.setOnDeleteClickListener(new NoteListViewAdapter.onLClickListener() {
            @Override
            public void onDeleteClick(final int pos) {
                UserNote note = mQuery.get(pos);
                mInstence.dalete(note.itemid);
                mQuery.remove(pos);
                reFreshPager();
                reFreshData(note, true);
            }

            @Override
            public void onEditClick(int pos) {
                UIUtils.Toast("暂未实现！");
            }

            @SuppressLint("WrongConstant")
            @Override
            public void onSClick(TextView comment, TextView v, int pos) {
                SwipeLayoutManager.getInstance().close();
                if (v.getVisibility() == View.GONE) {
                    v.setVisibility(View.VISIBLE);
                    comment.setVisibility(View.VISIBLE);
                } else {
                    v.setVisibility(View.GONE);
                    comment.setVisibility(View.GONE);
                }
                if (UIUtils.isEmpty(comment.getText().toString()))
                    comment.setText("备注：空");
                else {
                    comment.setText("备注：" + mQuery.get(pos).comment);
                }
            }
        });
        reFreshData(new UserNote(), false);
    }

    @Override
    public void initData() {
        toDay = DateUtils.getDbSaveDay();
        mCurrentType = WEEK;

        mDialog_views = new ArrayList<>();
        mDialog_views.add(UIUtils.inflate(R.layout.dialog_content));
        mDialog_views.add(UIUtils.inflate(R.layout.dialog_content));

        mInstence = NoteDb.getInstence(getContext());
        mQuery = mInstence.queryCustomDateData(toDay, -7);
//        mQuery =new ArrayList<>();

        mAdapter = new NoteListViewAdapter();
        mLlmaanger = new LinearLayoutManager(getContext());
        mLlmaanger.setOrientation(OrientationHelper.VERTICAL);
    }

    @OnClick({R.id.main_vp1_iv_add, R.id.rl_remainder_sum, R.id.rv_head_tv_time, R.id.rv_head_tv_calendar})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.main_vp1_iv_add:
                View dialog_note = UIUtils.inflate(R.layout.dialog_note);
                mDialog_vp = (ViewPager) dialog_note.findViewById(R.id.dialog_vp);
                dialog_note.findViewById(R.id.dialog_bottom_enter).setOnClickListener(this);
                dialog_note.findViewById(R.id.dialog_bottom_exit).setOnClickListener(this);
                dialog_note.findViewById(R.id.dialog_top_in).setOnClickListener(this);
                dialog_note.findViewById(R.id.dialog_top_out).setOnClickListener(this);
                mDlAdapter = new DialogAdapter(getActivity());
                mDlAdapter.setDatas(mDialog_views);
                mDialog_vp.setAdapter(mDlAdapter);

                mDialog = new Dialog(getContext());
                mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                mDialog.setContentView(dialog_note);
                mDialog.show();
                break;
            case R.id.rl_remainder_sum:
                AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                dialog.setTitle("修改余额");
                View view = UIUtils.inflate(R.layout.dialog_remainder_sum_et);
                final EditText editText_money = (EditText) view.findViewById(R.id.et_dialog_money);
                final EditText editText_weixin = (EditText) view.findViewById(R.id.et_dialog_weixin);
                final EditText editText_zhifubao = (EditText) view.findViewById(R.id.et_dialog_zhifubao);
                float remainder = SpUtils.getRemainder("remainder", 0);
                float remainder_weixin = SpUtils.getRemainder("remainder_weixin", 0);
                float remainder_zhifubao = SpUtils.getRemainder("remainder_zhifubao", 0);
                editText_money.setText(remainder + "");
                editText_weixin.setText("" + remainder_weixin);
                editText_zhifubao.setText("" + remainder_zhifubao);
                dialog.setView(view);
                dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mRemainder_sum.setText(editText_money.getText().toString());
                        SpUtils.putRemainder("remainder", Float.parseFloat(editText_money.getText().toString()));
                        SpUtils.putRemainder("remainder_weixin", Float.parseFloat(editText_weixin.getText().toString()));
                        SpUtils.putRemainder("remainder_zhifubao", Float.parseFloat(editText_zhifubao.getText().toString()));
                        dialog.dismiss();
                    }
                });
                dialog.show();
                break;
            case R.id.rv_head_tv_calendar:
                new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear,
                                          int dayOfMonth) {
                        String day = DateUtils.formatDayOrMinuteToDisplay(year, monthOfYear + 1, dayOfMonth, true);
                        toDay = DateUtils.formatDayOrMinuteToSava(day, true);
                        mCurrentType = DAY;
                        reFreshPager();
                    }
                }, DateUtils.getYear(), DateUtils.getMouth() - 1, DateUtils.getDay()).show();
                break;
            case R.id.rv_head_tv_time:
                View inflate = UIUtils.inflate(R.layout.pp_time);
                inflate.findViewById(R.id.pp_tv_today).setOnClickListener(this);
                inflate.findViewById(R.id.pp_tv_week).setOnClickListener(this);
                inflate.findViewById(R.id.pp_tv_mouth).setOnClickListener(this);
                inflate.findViewById(R.id.pp_tv_year).setOnClickListener(this);
                inflate.findViewById(R.id.pp_tv_all).setOnClickListener(this);
                mPp = new PopupWindow(inflate, mRv_head_tv_time.getWidth(), LinearLayout.LayoutParams.WRAP_CONTENT, true);
                mPp.setBackgroundDrawable(new ColorDrawable(UIUtils.getColor(R.color.transparent)));
                mPp.showAsDropDown(v);
                break;
        }
    }

    public void reFreshData(UserNote data, boolean isDelete) {
        double remainder_sum = SpUtils.getRemainder("remainder", 0);
        double remainder_weixin = SpUtils.getRemainder("remainder_weixin", 0);
        double remainder_zhifubao = SpUtils.getRemainder("remainder_zhifubao", 0);
        //此处不知为何，如果delete数据，余额会乱。。。。
        switch (data.from) {
            case 0:
                if (isDelete) {
                    remainder_sum = remainder_sum - data.money;
                } else {
                    remainder_sum = remainder_sum + data.money;
                }
                break;
            case 1:
                if (isDelete) {
                    remainder_weixin = remainder_weixin - data.money;
                } else {
                    remainder_weixin = remainder_weixin + data.money;
                }
                break;
            case 2:
                if (isDelete) {
                    remainder_zhifubao = remainder_zhifubao - data.money;
                } else {
                    remainder_zhifubao = remainder_zhifubao + data.money;
                }
                break;
        }
        SpUtils.putRemainder("remainder", (float) remainder_sum);
        SpUtils.putRemainder("remainder_weixin", (float) remainder_weixin);
        SpUtils.putRemainder("remainder_zhifubao", (float) remainder_zhifubao);
        double todayOutMoney = mInstence.queryTodayMoney(0);
        double todayInMoney = mInstence.queryTodayMoney(1);
        double MouthOutMoney = mInstence.queryThisMouthMoney(0);
        double MouthInMoney = mInstence.queryThisMouthMoney(1);
        mRemainder_sum.setText(remainder_sum + "");
        mToday_outday_sum.setText(0 - todayOutMoney + "");
        mToday_income_sum.setText(todayInMoney + "");
        mOutlay_sum.setText(0 - MouthOutMoney + "");
        mIncome_sum.setText(MouthInMoney + "");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_bottom_enter:
                if (saveData() != -1) {
                    mDialog.dismiss();
                    reFreshPager();
                }
                break;
            case R.id.dialog_bottom_exit:
                mDialog.dismiss();
                break;
            case R.id.dialog_top_in:
                mDialog_vp.setCurrentItem(0);
                break;
            case R.id.dialog_top_out:
                mDialog_vp.setCurrentItem(1);
                break;
            case R.id.pp_tv_today:
                mRv_head_tv_time.setText(DateUtils.getCurrentDay());
                mCurrentType = DAY;
                mPp.dismiss();
                reFreshPager();
                break;
            case R.id.pp_tv_week:
                mCurrentType = WEEK;
                mPp.dismiss();
                reFreshPager();
                break;
            case R.id.pp_tv_mouth:
                mCurrentType = MOUTH;
                mPp.dismiss();
                reFreshPager();
                break;
            case R.id.pp_tv_year:
                mCurrentType = YEAR;
                mPp.dismiss();
                reFreshPager();
                break;
            case R.id.pp_tv_all:
                mCurrentType = ALL;
                mPp.dismiss();
                reFreshPager();
                break;
        }
    }

    private void reFreshPager() {
        switch (mCurrentType) {
            case DAY:
                mQuery = mInstence.queryTodayData(toDay);
                mRv_head_tv_time.setText(DateUtils.formatDayOrMinuteToDisplay(toDay, true, true, true));
                break;
            case WEEK:
                mQuery = mInstence.queryCustomDateData(toDay, -7);
                mRv_head_tv_time.setText("最近7天");
                break;
            case MOUTH:
                mQuery = mInstence.queryCustomDateData(toDay, -30);
                mRv_head_tv_time.setText("最近一月");
                break;
            case YEAR:
                mQuery = mInstence.queryCustomDateData(toDay, -365);
                mRv_head_tv_time.setText("最近一年");
                break;
            case ALL:
                mQuery = mInstence.queryAllData();
                mRv_head_tv_time.setText("所有记录");
                break;

        }
        mAdapter.setDatas(mQuery);
        mAdapter.notifyDataSetChanged();
        SwipeLayoutManager.getInstance().close();
}

    private long saveData() {
        View view = mDialog_views.get(mDialog_vp.getCurrentItem());
        EditText dialog_et_money = (EditText) view.findViewById(R.id.dialog_et_money);
        EditText dialog_et_comment = (EditText) view.findViewById(R.id.dialog_et_comment);
        TextView dialog_tv_minute = (TextView) view.findViewById(R.id.dialog_tv_minute);
        TextView dialog_tv_day = (TextView) view.findViewById(R.id.dialog_tv_day);
        Spinner dialog_vp_sp_type = (Spinner) view.findViewById(R.id.dialog_vp_sp_type);
        Spinner dialog_vp_sp_from = (Spinner) view.findViewById(R.id.dialog_vp_sp_from);
        String money = dialog_et_money.getText().toString();
        if (UIUtils.isEmpty(money)) {
            UIUtils.Toast("金额不能为空");
            return -1;
        }
        UserNote note = new UserNote();
        note.day = DateUtils.formatDayOrMinuteToSava(dialog_tv_day.getText().toString(), true);
        note.minute = DateUtils.formatDayOrMinuteToSava(dialog_tv_minute.getText().toString(), false);
        int position = dialog_vp_sp_type.getSelectedItemPosition();
        int fromPosition = dialog_vp_sp_from.getSelectedItemPosition();
        note.from = fromPosition;
        if (mDialog_vp.getCurrentItem() == 0) {
            note.type = TypeDataUtils.getTypeOut()[position];
            note.dbtype = 0;
            note.money = -Double.parseDouble(money);
        } else {
            note.type = TypeDataUtils.getTypeIn()[position];
            note.dbtype = 1;
            note.money = Double.parseDouble(money);
        }
        note.comment = dialog_et_comment.getText().toString();
        note.userid = "1";
        note.itemid = System.currentTimeMillis() + "";
        long result = -1;
        result = NoteDb.getInstence(UIUtils.getContext()).insert(note);
        if (result != -1) {
            dialog_et_money.setText("");
            dialog_et_comment.setText("");
            dialog_vp_sp_type.setSelection(0);
            reFreshData(note, false);
        }else {
            UIUtils.Toast("插入失败");
        }
        return result;
    }
}
