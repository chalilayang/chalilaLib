package com.baogetv.app.model.search;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.baogetv.app.apiinterface.VideoListService;
import com.baogetv.app.bean.ResponseBean;
import com.baogetv.app.net.CustomCallBack;
import com.baogetv.app.net.RetrofitManager;
import com.chalilayang.scaleview.ScaleCalculator;
import com.nex3z.flowlayout.FlowLayout;
import com.baogetv.app.BaseActivity;
import com.baogetv.app.R;
import com.baogetv.app.db.entity.SearchItemEntity;
import com.baogetv.app.db.entity.SearchItemEntityDao;
import com.baogetv.app.db.util.DaoManager;

import java.util.List;

import retrofit2.Call;

public class SearchActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "SearchActivity";
    private static final int KEY = "SEARCH_KEY".hashCode();
    private FlowLayout flowLayout;
    private FlowLayout searchHistoryView;
    private EditText editText;
    private View editClear;
    private View historyClear;
    private TextView searchBtn;
    private SearchItemEntityDao entityDao;

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
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener(){
            @Override
            public boolean onEditorAction(TextView arg0, int actionId, KeyEvent arg2) {
                // TODO Auto-generated method stub
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    // 当按了搜索之后关闭软键盘
                    hideKeyBoard();
                    searchBtn.performClick();
                    return true;
                }
                return false;
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
        flowLayout = (FlowLayout) findViewById(R.id.search_label_layout);
        int space = ScaleCalculator.getInstance(getApplicationContext()).scaleWidth(20);
        flowLayout.setChildSpacing(space);
        flowLayout.setRowSpacing(space);
        getLabels();
        historyClear = findViewById(R.id.search_history_clear);
        historyClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<SearchItemEntity> list = entityDao.queryBuilder().build().list();
                entityDao.deleteInTx(list);
                updateHistory();
            }
        });
        searchHistoryView = (FlowLayout) findViewById(R.id.search_history_list);
        searchHistoryView.setChildSpacing(space);
        searchHistoryView.setRowSpacing(space);
        updateHistory();
    }

    private void hideKeyBoard() {
        ((InputMethodManager) SearchActivity.this.getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }

    @Override
    public void onClick(View v) {
        Object object = v.getTag(KEY);
        if (object != null && object instanceof String) {
            String str = (String) object;
            if (!TextUtils.isEmpty(str)) {
                search(str);
            }
        }
    }

    private void addLabel(List<String> mDatas) {
        int paddingLeft
                = ScaleCalculator.getInstance(getApplicationContext()).scaleTextSize(30);
        int paddingTop
                = ScaleCalculator.getInstance(getApplicationContext()).scaleTextSize(22);
        flowLayout.measure(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        int height = flowLayout.getMeasuredHeight();
        Log.i(TAG, "addLabel: " + height);
        for (int index = 0, count = mDatas.size(); index < count; index ++) {
            final TextView view = new TextView(this);
            view.setText(mDatas.get(index));
            view.setTextColor(getResources().getColor(R.color.search_label_text));
            view.setGravity(Gravity.CENTER);
            view.setIncludeFontPadding(false);
            view.setPadding(paddingLeft, paddingTop, paddingLeft, paddingTop);
            view.setTextSize(
                    TypedValue.COMPLEX_UNIT_PX,
                    ScaleCalculator.getInstance(getApplicationContext()).scaleTextSize(26));
            view.setBackgroundResource(R.drawable.search_label_bg);
            view.setTag(KEY, mDatas.get(index));
            view.setOnClickListener(this);
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
                final View container = LayoutInflater.from(getApplicationContext())
                        .inflate(R.layout.search_item_layout, null);
                final TextView tv = container.findViewById(R.id.history_title);
                tv.setText(list.get(i).getContent());
                tv.setIncludeFontPadding(false);
                container.setPadding(paddingLeft, paddingTop, paddingLeft, paddingTop);
                View delete = container.findViewById(R.id.history_item_delete);
                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Object object = container.getTag(KEY);
                        if (object != null && object instanceof String) {
                            String str = (String) object;
                            if (!TextUtils.isEmpty(str)) {
                                List<SearchItemEntity> list = entityDao.queryBuilder()
                                        .where(SearchItemEntityDao.Properties.Content.eq(str)
                                        ).build().list();
                                entityDao.deleteInTx(list);
                                updateHistory();
                            }
                        }
                    }
                });
                Log.i(TAG, "updateHistory: " + list.get(i).getContent());
                container.setBackgroundResource(R.drawable.search_label_bg);
                container.setTag(KEY, list.get(i).getContent());
                container.setOnClickListener(this);
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
        if (!TextUtils.isEmpty(content)) {
            Intent intent = new Intent(this, SearchResultActivity.class);
            intent.putExtra(SearchResultActivity.KEY_SEARCH, content);
            startActivity(intent);
        }
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

    private void getLabels() {
        VideoListService listService
                = RetrofitManager.getInstance().createReq(VideoListService.class);
        Call<ResponseBean<List<String>>> listBeanCall = listService.getHotWord();
        if (listBeanCall != null) {
            listBeanCall.enqueue(new CustomCallBack<List<String>>() {
                @Override
                public void onSuccess(List<String> data, String msg, int state) {
                    addLabel(data);
                }

                @Override
                public void onFailed(String error, int state) {

                }
            });
        }
    }
}
