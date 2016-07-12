package cn.wosdk.viewpagertab.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
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
import cn.wosdk.viewpagertab.ui.widget.ViewPagerIndicator;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.navigation_title)
    TextView mTextView;
    @BindView(R.id.navigation_left_container)
    LinearLayout mLinearLayout;
    @BindView(R.id.next_iv)
    ImageView mImageView;
    @BindView(R.id.id_indicator)
    ViewPagerIndicator mIndicator;
    @BindView(R.id.id_page_vp)
    ViewPager mViewPager;

    @BindView(R.id.id_tab1_tv)
    TextView id_tab1_tv;
    @BindView(R.id.id_tab2_tv)
    TextView id_tab2_tv;
    @BindView(R.id.id_tab3_tv)
    TextView id_tab3_tv;
    @BindView(R.id.id_tab4_tv)
    TextView id_tab4_tv;
    @BindView(R.id.id_tab5_tv)
    TextView id_tab5_tv;

    @BindViews({R.id.id_tab1_tv, R.id.id_tab2_tv, R.id.id_tab3_tv, R.id.id_tab4_tv, R.id.id_tab5_tv})
    List<TextView> mTabs;

    private List<String> mDatas = Arrays.asList("测试1", "测试2", "测试3", "测试4", "测试5");

    private TabFragment[] mFragments = new TabFragment[mDatas.size()];
    private FragmentPagerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initDatas();
        initEvents();
    }

    protected void initView() {
        ButterKnife.bind(this);
        mImageView.setVisibility(View.VISIBLE);
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
        mIndicator.setTabItemTitles(mDatas, mTextView, 0);
        //设置关联的ViewPager
        mIndicator.setViewPager(mViewPager, 0, mTextView, mDatas);
    }


    @OnClick(R.id.next_iv)
    void newActivity(View view) {
        Intent intent = new Intent(MainActivity.this, TwoActivity.class);
        startActivity(intent);
    }
}
