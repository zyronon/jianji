package com.ttentau.jianji.fragment;

import android.os.AsyncTask;

import com.ttentau.jianji.adapter.NoteRecyclerAdapter;
import com.ttentau.jianji.bean.UserNote;
import com.ttentau.jianji.db.NoteDb;
import com.ttentau.jianji.util.DateUtils;

import java.util.ArrayList;

/**
 * Created by ttentau on 2017/6/27.
 */

public class FristQuerySync extends AsyncTask<NoteDb,Void,ArrayList<UserNote>> {


    private final NoteRecyclerAdapter mAdapter;
    private final NoteDb mMInstence;

    public FristQuerySync(NoteRecyclerAdapter adapter, NoteDb mInstence){
        mAdapter = adapter;
        mMInstence = mInstence;
    }
    @Override
    protected ArrayList<UserNote> doInBackground(NoteDb... noteDbs) {
        return  mMInstence.queryCustomDateData( DateUtils.getDbSaveDay(), -7);
    }

    @Override
    protected void onPostExecute(ArrayList<UserNote> userNotes) {
        super.onPostExecute(userNotes);
        mAdapter.setDatas(userNotes);
        mAdapter.notifyDataSetChanged();
    }
}
