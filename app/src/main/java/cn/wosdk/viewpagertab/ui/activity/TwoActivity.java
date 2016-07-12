package cn.wosdk.viewpagertab.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.wosdk.viewpagertab.R;
import cn.wosdk.viewpagertab.ui.fragment.TabFragment;
import cn.wosdk.viewpagertab.ui.widget.ColorTrackView;

public class TwoActivity extends AppCompatActivity{
    @BindView(R.id.navigation_title)
    TextView mTextView;
    @BindView(R.id.navigation_left_container)
    LinearLayout mLinearLayout;
    @BindView(R.id.next_iv)
    ImageView mImageView;

    @BindView(R.id.id_page_vp)
    ViewPager mViewPager;

    @BindView(R.id.id_tab1_tv)
    ColorTrackView id_tab1_tv;
    @BindView(R.id.id_tab2_tv)
    ColorTrackView id_tab2_tv;
    @BindView(R.id.id_tab3_tv)
    ColorTrackView id_tab3_tv;
    @BindView(R.id.id_tab4_tv)
    ColorTrackView id_tab4_tv;
    @BindView(R.id.id_tab5_tv)
    ColorTrackView id_tab5_tv;

    @BindView(R.id.id_tab_line_iv)
    View id_tab_line_iv;

    @BindViews({R.id.id_tab1_tv, R.id.id_tab2_tv, R.id.id_tab3_tv, R.id.id_tab4_tv, R.id.id_tab5_tv})
    List<ColorTrackView> mTabs;

    private List<String> mDatas = Arrays.asList("测试1","测试2","测试3","测试4","测试5");

    private TabFragment[] mFragments = new TabFragment[mDatas.size()];
    private FragmentPagerAdapter mAdapter;

    private int screenWidth;//屏幕的宽度
    private LinearLayout.LayoutParams lp;
    /**
     * 标题正常时的颜色
     */
    private static final int COLOR_TEXT_NORMAL = Color.rgb(160, 165, 170);
    /**
     * 标题选中时的颜色
     */
    private static final int COLOR_TEXT_HIGHLIGHTCOLOR = Color.rgb(255, 102, 153);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initView();
        initTabLineWidth();
        initDatas();
        initEvents();
    }

    protected void initView() {
        ButterKnife.bind(this);
    }

    private void initDatas() {

        for (int i = 0; i < mDatas.size(); i++) {
            mFragments[i] = (TabFragment) TabFragment.newInstance(mDatas.get(i));
        }

        mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public int getCount() {
                return mDatas.size();
            }

            @Override
            public Fragment getItem(int position) {
                return mFragments[position];
            }
        };
    }


    private void initEvents() {
        mViewPager.setAdapter(mAdapter);
        highLightTextView(0);
        mTextView.setText(mDatas.get(0));
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (positionOffset > 0)
                {
                    ColorTrackView left = mTabs.get(position);
                    ColorTrackView right = mTabs.get(position + 1);

                    left.setDirection(1);
                    right.setDirection(0);
//                    Log.e("TAG", positionOffset+"");
                    left.setProgress( 1-positionOffset);
                    right.setProgress(positionOffset);
                }
            }

            @Override
            public void onPageSelected(int position) {
                resetTextViewColor();
                highLightTextView(position);
                lp.leftMargin = position * (screenWidth * 1/5);
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    /**
     * 给指针设置合适的宽度
     */
    private void initTabLineWidth(){
        DisplayMetrics dpMetrics = new DisplayMetrics();
        getWindow().getWindowManager().getDefaultDisplay().getMetrics(dpMetrics);
        screenWidth = dpMetrics.widthPixels;
        lp = (LinearLayout.LayoutParams)id_tab_line_iv.getLayoutParams();
        lp.width=screenWidth/5;
        id_tab_line_iv.setLayoutParams(lp);
        id_tab_line_iv.setBackgroundColor(COLOR_TEXT_HIGHLIGHTCOLOR);
    }

    /**
     * 高亮文本
     * @param position
     */
    protected void highLightTextView(int position)
    {
        View view = mTabs.get(position);
        if (view instanceof ColorTrackView)
        {
            ((ColorTrackView) view).setTextSize(48);
            mTextView.setText(mDatas.get(position));
        }
    }

    /**
     * 重置文本颜色
     */
    private void resetTextViewColor()
    {
        for (int i = 0; i < mTabs.size(); i++)
        {
            View view = mTabs.get(i);
            if (view instanceof ColorTrackView)
            {
                ((ColorTrackView) view).setTextSize(40);
            }
        }
    }

    @OnClick(R.id.navigation_left_container)
    void finishActivity(View view){
        finish();
    }

}
