package cn.wosdk.viewpagertab.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import cn.wosdk.viewpagertab.R;

public class ViewPagerIndicator extends LinearLayout{

    private Paint mPaint;

    private static final int COUNT_DEFAULT_TAB = 5;

    private int mTabVisibleCount = COUNT_DEFAULT_TAB;

    private List<String> mTabTitles;

    public ViewPager mViewPager;


    private static final int COLOR_TEXT_NORMAL = Color.rgb(160, 165, 170);

    private static final int COLOR_TEXT_HIGHLIGHTCOLOR = Color.rgb(255, 102, 153);

    private  TextView textView;
    private int mTop; // 指示符的top
    private int mLeft; // 指示符的left
    private int mWidth; // 指示符的width
    private int mHeight = 5; // 指示符的高度，固定了

    public ViewPagerIndicator(Context context)
    {
        this(context, null);
    }

    public ViewPagerIndicator(Context context, AttributeSet attrs)
    {
        super(context, attrs);
//        setBackgroundColor(Color.TRANSPARENT);
        // 获得自定义属性，tab的数量
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ViewPagerIndicator);
        mTabVisibleCount = a.getInt(R.styleable.ViewPagerIndicator_item_count, COUNT_DEFAULT_TAB);
        if (mTabVisibleCount < 0)
            mTabVisibleCount = COUNT_DEFAULT_TAB;
        a.recycle();

        // 初始化画笔
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(COLOR_TEXT_HIGHLIGHTCOLOR);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mTop = getMeasuredHeight(); // 测量的高度即指示符的顶部位置
        int width = getMeasuredWidth(); // 获取测量的总宽度
        int height = mTop + mHeight; // 重新定义一下测量的高度
        mWidth = width / 5; // 指示符的宽度为总宽度/item的个数

        setMeasuredDimension(width, height);
    }

    /**
     * 指示符滚动
     * @param position 现在的位置
     * @param positionOffset  偏移量 0 ~ 1
     */
    public void scroll(int position, float positionOffset) {
        mLeft = (int) ((position + positionOffset) * mWidth);
        invalidate();
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        Rect rect = new Rect(mLeft, mTop, mLeft + mWidth, mTop + mHeight);
        canvas.drawRect(rect, mPaint); // 绘制该矩形
        super.dispatchDraw(canvas);
    }

    /**
     * 设置可见的tab的数量

     * @param count
     */
    public void setVisibleTabCount(int count)
    {
        this.mTabVisibleCount = count;
    }


    /**
     * 对外的ViewPager的回调接口
     */
    public interface PageChangeListener
    {
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels);

        public void onPageSelected(int position);

        public void onPageScrollStateChanged(int state);
    }

    // 对外的ViewPager的回调接口
    private PageChangeListener onPageChangeListener;

    // 对外的ViewPager的回调接口的设置
    public void setOnPageChangeListener(PageChangeListener pageChangeListener) {
        this.onPageChangeListener = pageChangeListener;
    }


    // 设置关联的ViewPager
    public void setViewPager(ViewPager mViewPager, int pos, final TextView textView, List<String> datas) {
        this.mViewPager = mViewPager;
        this.mTabTitles = datas;
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener(){
            @Override
            public void onPageSelected(int position) {
                // 设置字体颜色高亮
                resetTextViewColor();
                highLightTextView(position);
                textView.setText(mTabTitles.get(position));

                // 回调
                if (onPageChangeListener != null){
                    onPageChangeListener.onPageSelected(position);
                }
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // 滚动
                scroll(position, positionOffset);
                // 回调
                if (onPageChangeListener != null){
                    onPageChangeListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state)
            {
                // 回调
                if (onPageChangeListener != null) {
                    onPageChangeListener.onPageScrollStateChanged(state);
                }
            }
        });
        // 设置当前页
        mViewPager.setCurrentItem(pos);
        // 高亮
        highLightTextView(pos);
    }

    /**
     * 高亮文本
     *
     * @param position
     */
    protected void highLightTextView(int position)
    {
        View view = getChildAt(position);
        if (view instanceof TextView)
        {
            ((TextView) view).setTextColor(COLOR_TEXT_HIGHLIGHTCOLOR);
            ((TextView) view).setTextSize( TypedValue.COMPLEX_UNIT_SP, 16);
        }
    }

    /**
     * 重置文本颜色
     */
    private void resetTextViewColor()
    {
        for (int i = 0; i < getChildCount(); i++)
        {
            View view = getChildAt(i);
            if (view instanceof TextView)
            {
                ((TextView) view).setTextColor(COLOR_TEXT_NORMAL);
                ((TextView) view).setTextSize( TypedValue.COMPLEX_UNIT_SP, 14);
            }
        }
    }

    /**
     * 设置tab的标题内容 可选，可以自己在布局文件中写死
     *
     * @param datas
     */
    public void setTabItemTitles(List<String> datas,TextView textView,int showPosition) {
        this.mTabTitles = datas;
        this.textView = textView;
        textView.setText(mTabTitles.get(showPosition));
        // 如果传入的list有值，则移除布局文件中设置的view
        if (datas != null && datas.size() > 0) {
            this.removeAllViews();
            this.mTabTitles = datas;

            for (String title : mTabTitles) {
                // 添加view
                addView(generateTextView(title));
            }

            // 设置item的click事件
            setItemClickEvent();
        }
    }

    /**
     * 设置点击事件
     */
    public void setItemClickEvent()
    {
        int cCount = getChildCount();
        for (int i = 0; i < cCount; i++)
        {
            final int j = i;
            final View view = getChildAt(i);
            view.setOnClickListener(new OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    mViewPager.setCurrentItem(j);
                    textView.setText(mTabTitles.get(j));
                }
            });
        }
    }

    /**
     * 根据标题生成我们的TextView
     *
     * @param text
     * @return
     */
    private TextView generateTextView(String text)
    {
        TextView tv = new TextView(getContext());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        lp.width = getScreenWidth() / mTabVisibleCount;
        tv.setGravity(Gravity.CENTER);
        tv.setTextColor(COLOR_TEXT_NORMAL);
        tv.setText(text);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        tv.setLayoutParams(lp);
        return tv;
    }


    /**
     * 获得屏幕的宽度
     *
     * @return
     */
    public int getScreenWidth()
    {
        WindowManager wm = (WindowManager) getContext().getSystemService(
                Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }
}
