package cn.wosdk.viewpagertab.ui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;


public class ScrollTabView extends View {


    private int mTabNum,mCurrentNum;
    private float mWidth,mTabWidth,mOffset;

    private final Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private int mBeginColor,mEndColor;
    private LinearGradient mGradient;
    public ScrollTabView(Context context) {
        this(context,null);
    }

    public ScrollTabView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setTabNum(int num){
        mTabNum = num;
    }

    public void setCurrentNum(int num){
        mCurrentNum = num;
        mOffset = 0;
    }

    public void setOffset(int position,float offset){
        if(offset == 0){
            return;
        }

        mCurrentNum = position;
        mOffset = offset;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(mTabWidth == 0){
            mWidth = getWidth();
            mTabWidth = mWidth / mTabNum;
        }

        //根据位置和偏移量来计算滑动条的位置
        float left = (mCurrentNum + mOffset) * mTabWidth;
        float right = left + mTabWidth;
        float top = getPaddingTop();
        float bottom = getHeight() - getPaddingBottom();

        LinearGradient gradient = new LinearGradient(left,getHeight(),right,getHeight(),mBeginColor,mEndColor, Shader.TileMode.CLAMP);
        mPaint.setShader(gradient);

        canvas.drawRect(left,top,right,bottom,mPaint);
    }

    public void setSelectedColor(int preColor,int nextColor){
        mBeginColor = preColor;
        mEndColor = nextColor;
    }

}
