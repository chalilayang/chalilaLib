package com.reshape.app.model.search;

import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.chalilayang.scaleview.ScaleCalculator;
import com.nex3z.flowlayout.FlowLayout;
import com.reshape.app.BaseActivity;
import com.reshape.app.R;
import com.reshape.app.db.entity.SearchItemEntity;
import com.reshape.app.db.entity.SearchItemEntityDao;
import com.reshape.app.db.util.DaoManager;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends BaseActivity {

    private static final String TAG = "SearchActivity";
    private FlowLayout flowLayout;
    private FlowLayout searchHistoryView;
    private EditText editText;
    private View editClear;
    private TextView searchBtn;
    private SearchItemEntityDao entityDao;
    /**
     * 显示的文字
     */
    private String[] mDatas = new String[]{"QQ",
            "视频",
            "放开那三国",
            "电子书",
            "酒店",
            "单机",
            "小说",
            "斗地主",
            "优酷",
            "网游",
            "WIFI万能钥匙",
            "播放器",
            "捕鱼达人2",
            "机票",
            "游戏",
            "熊出没之熊大快跑",
            "美图秀秀",
            "浏览器",};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        entityDao = DaoManager.getInstance(getApplicationContext())
                .getDaoSession().getSearchItemEntityDao();
        editText = (EditText) findViewById(R.id.search_edit);
        editText.setFilters(new InputFilter[]{new MaxTextLengthFilter(31)});
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String content = editable.toString();
                if (TextUtils.isEmpty(content)) {
                    searchBtn.setText(getString(R.string.search_cancel));
                } else {
                    searchBtn.setText(getString(R.string.search));
                }
            }
        });
        editClear = findViewById(R.id.edit_clear);
        editClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setText("");
            }
        });
        searchBtn = (TextView) findViewById(R.id.search_tv);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String currentTitle = editText.getText().toString();
                if (!TextUtils.isEmpty(currentTitle)) {
                    search(currentTitle);
                } else {
                    finish();
                }
            }
        });
        flowLayout = findViewById(R.id.search_label_layout);
        int space = ScaleCalculator.getInstance(getApplicationContext()).scaleWidth(20);
        flowLayout.setChildSpacing(space);
        flowLayout.setRowSpacing(space);
        addLabel();

        searchHistoryView = (FlowLayout) findViewById(R.id.search_history_list);
        searchHistoryView.setChildSpacing(space);
        searchHistoryView.setRowSpacing(space);
        List<String> list = new ArrayList<>();
        for (int index = 0, count = mDatas.length; index < count; index ++) {
            list.add(mDatas[index]);
        }
        updateHistory();
    }

    private void addLabel() {
        // 循环添加TextView到容器
        int paddingLeft
                = ScaleCalculator.getInstance(getApplicationContext()).scaleTextSize(30);
        int paddingTop
                = ScaleCalculator.getInstance(getApplicationContext()).scaleTextSize(22);
        flowLayout.measure(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        int height = flowLayout.getMeasuredHeight();
        Log.i(TAG, "addLabel: " + height);
        for (int i = 0; i < mDatas.length; i++) {
            final TextView view = new TextView(this);
            view.setText(mDatas[i]);
            view.setTextColor(getResources().getColor(R.color.search_label_text));
            view.setGravity(Gravity.CENTER);
            view.setIncludeFontPadding(false);
            view.setPadding(paddingLeft, paddingTop, paddingLeft, paddingTop);
            view.setTextSize(
                    TypedValue.COMPLEX_UNIT_PX,
                    ScaleCalculator.getInstance(getApplicationContext()).scaleTextSize(26));
            view.setBackgroundResource(R.drawable.round_corner_rec);
            flowLayout.addView(view);
        }
    }

    private void updateHistory() {
        List<SearchItemEntity> list = entityDao.queryBuilder()
                .orderDesc(SearchItemEntityDao.Properties.Time).build().list();
        if (searchHistoryView != null) {
            searchHistoryView.removeAllViews();
        }
        if (list != null && list.size() != 0) {
            int paddingLeft
                    = ScaleCalculator.getInstance(getApplicationContext()).scaleTextSize(30);
            int paddingTop
                    = ScaleCalculator.getInstance(getApplicationContext()).scaleTextSize(22);
            for (int i = 0, count = list.size(); i < count; i++) {
                View container = LayoutInflater.from(getApplicationContext())
                        .inflate(R.layout.search_item_layout, null);
                final TextView tv = container.findViewById(R.id.history_title);
                tv.setText(list.get(i).getContent());
                tv.setIncludeFontPadding(false);
                container.setPadding(paddingLeft, paddingTop, paddingLeft, paddingTop);
                container.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        searchHistoryView.removeView(view);
                    }
                });
                container.setBackgroundResource(R.drawable.round_corner_rec);
                searchHistoryView.addView(container);
            }
        }
    }

    private void search(String content) {
        List<SearchItemEntity> list = entityDao.queryBuilder()
                .where(SearchItemEntityDao.Properties.Content.eq(content)).build().list();
        entityDao.deleteInTx(list);
        entityDao.insert(new SearchItemEntity(content, System.currentTimeMillis()));
        updateHistory();
    }

    class MaxTextLengthFilter implements InputFilter {
        private int mMaxLength;
        public MaxTextLengthFilter(int max) {
            mMaxLength = max - 1;
        }
        public CharSequence filter(CharSequence source, int start, int end,
                                   Spanned dest, int dstart, int dend) {
            int keep = mMaxLength - (dest.length() - (dend - dstart));
            if (keep < (end - start)) {
                showShortToast("视频标题最多输入30个字");
            }
            if (keep <= 0) {
                return "";
            } else if (keep >= end - start) {
                return null;
            } else {
                return source.subSequence(start, start + keep);
            }
        }
    }
}
