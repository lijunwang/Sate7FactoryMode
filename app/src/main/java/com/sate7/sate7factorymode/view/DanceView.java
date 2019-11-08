package com.sate7.sate7factorymode.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.sate7.sate7factorymode.R;
import com.sate7.sate7factorymode.XLog;

import java.util.Random;

public class DanceView extends View {
    private final int ITEM_WIDTH_DEFAULT = 20;
    private final int SPACE_WIDTH_DEFAULT = 20;
    private int mItemWidth;
    private int mSpaceWidth;
    private int mItemColor;
    private int mSpaceColor;
    private int mWidth;
    private int mHeight;
    private int mPadding;
    private int mItemCounts;
    private Paint mPaintItem = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mPaintSpace = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Point mCenter = new Point();
    private final int MSG_START_DANCE = 0x10;
    private Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_START_DANCE:
                    sendEmptyMessageDelayed(MSG_START_DANCE, 200);
                    invalidate();
                    break;
                default:
                    break;
            }
        }
    };

    public DanceView(Context context) {
        this(context, null);
    }

    public DanceView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DanceView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.DanceView);
        mItemWidth = typedArray.getDimensionPixelSize(R.styleable.DanceView_DanceItemWidth, ITEM_WIDTH_DEFAULT);
        mSpaceWidth = typedArray.getDimensionPixelSize(R.styleable.DanceView_DanceSpaceWidth, SPACE_WIDTH_DEFAULT);
        mItemColor = typedArray.getColor(R.styleable.DanceView_DanceItemColor, Color.RED);
        mSpaceColor = typedArray.getColor(R.styleable.DanceView_DanceSpaceColor, Color.BLUE);
        typedArray.recycle();

        mPaintItem.setColor(mItemColor);
//        mPaintItem.setStyle(Paint.Style.STROKE);
//        mPaintItem.setStrokeWidth(2);

        mPaintSpace.setColor(mSpaceColor);
//        mPaintSpace.setStyle(Paint.Style.STROKE);
//        mPaintItem.setStrokeWidth(2);
        handler.sendEmptyMessage(MSG_START_DANCE);
    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        mWidth = getWidth();
        mHeight = getHeight();
        mCenter.x = mWidth / 2;
        mCenter.y = mHeight / 2;
        mPadding = Math.max(getPaddingLeft(), getPaddingRight());
        mItemCounts = mWidth - mPadding - mPadding / (mItemWidth + mSpaceWidth);
        XLog.d("DanceView onLayout ... " + mPadding + "," + mItemCounts);
    }

    private Random random = new Random();
    private int currentLeft = 0;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < mItemCounts; i++) {
            currentLeft = (mItemWidth + mSpaceWidth) * i + mPadding;
//            XLog.d("DanceView onDraw ... " + "i == " + i + " --- " + currentLeft);
            canvas.drawRect(currentLeft, mCenter.y - Math.abs(random.nextFloat() * 100), currentLeft + mItemWidth, mCenter.y, mPaintItem);
            canvas.drawRect(currentLeft + mItemWidth, mCenter.y - Math.abs(random.nextFloat() * 100), currentLeft + mItemWidth + mSpaceWidth, mCenter.y, mPaintSpace);
        }

    }
}
