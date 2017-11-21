package com.xubo.linescrollviewlib;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.text.BoringLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Author：xubo
 * Time：2017-11-19
 * Description：
 */

public class LineScrollView extends ListView {
    private static final int TEXT_COLOR = Color.BLACK;
    private static final float TEXT_SIZE = 16;
    private static final int TEXT_SPACE = 10;
    private static final int SCROLL_LINES = 5;
    private static final int SCROLL_TIME_FOR_SCREEN = 5000;

    private int scrollLines;
    private long scrollScreenTime;
    private int textSpace;
    private int textColor;
    private float textSize;
    private LineAdapter lineAdapter;
    private int textHeight;
    private int scrollDistance;
    private Timer timer;
    private ScrollTimerTask scrollTimerTask;
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            smoothScrollBy(scrollDistance, 0);
        }
    };
    private Handler handler = new Handler();

    public LineScrollView(Context context) {
        this(context, null);
    }

    public LineScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LineScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.LineScrollView, defStyleAttr, 0);
        if (typedArray != null) {
            textColor = typedArray.getColor(R.styleable.LineScrollView_textColor, TEXT_COLOR);
            textSize = typedArray.getFloat(R.styleable.LineScrollView_textSize, TEXT_SIZE);
            textSpace = (int) (typedArray.getDimension(R.styleable.LineScrollView_textSpace, dp2px(context, TEXT_SPACE)) / 2);
            scrollLines = typedArray.getInteger(R.styleable.LineScrollView_scrollLines, SCROLL_LINES);
            scrollScreenTime = typedArray.getInteger(R.styleable.LineScrollView_scrollScreenTime, SCROLL_TIME_FOR_SCREEN);
            typedArray.recycle();
        } else {
            textColor = TEXT_COLOR;
            textSize = TEXT_SIZE;
            textSpace = (int) (dp2px(context, TEXT_SPACE) / 2);
            scrollLines = SCROLL_LINES;
            scrollScreenTime = SCROLL_TIME_FOR_SCREEN;
        }
        init(context);
    }

    private void init(Context context) {
        setDividerHeight(0);
        setSelector(R.drawable.line_item_selector);

        lineAdapter = new LineAdapter(context, textSize, textSpace, textColor, scrollLines);

        TextPaint textPaint = new TextPaint();
        textPaint.setTextSize(dp2px(context, textSize));
        BoringLayout.Metrics boring = BoringLayout.isBoring("", textPaint);
        textHeight = boring.bottom - boring.top;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        setAdapter(lineAdapter);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measuredWidth = MeasureSpec.getSize(widthMeasureSpec);
        int measuredHeight = lineAdapter.lines() >= scrollLines ? getViewHeight(textSpace, scrollLines) : getViewHeight(textSpace, lineAdapter.lines());
        setMeasuredDimension(measuredWidth, measuredHeight);
        scrollDistance = (int) (measuredHeight * 10 / scrollScreenTime);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (scrollTimerTask != null) {
            scrollTimerTask.close();
            scrollTimerTask.cancel();
        }
        if (timer != null) {
            timer.cancel();
        }
        handler.removeCallbacks(runnable);
    }

    /**
     * 设置滚动内容(会清除之前的滚动内容)
     * @param listLines
     */
    public void setLines(List<String> listLines) {
        lineAdapter.setLineList(listLines);
        if (scrollTimerTask != null) {
            scrollTimerTask.close();
            scrollTimerTask.cancel();
        }
        if (timer != null) {
            timer.cancel();
        }
        handler.removeCallbacks(runnable);
        if (listLines.size() > scrollLines) {
            timer = new Timer();
            scrollTimerTask = new ScrollTimerTask();
            timer.schedule(scrollTimerTask, 100);
        }
        setSelection(0);
    }

    public void clear() {
        lineAdapter.clear();
        if (scrollTimerTask != null) {
            scrollTimerTask.close();
            scrollTimerTask.cancel();
        }
        if (timer != null) {
            timer.cancel();
        }
        handler.removeCallbacks(runnable);
        setSelection(0);
    }

    /**
     * 增加滚动内容
     * @param listLines
     */
    public void addLines(List<String> listLines) {
        lineAdapter.addLineList(listLines);
        if (lineAdapter.lines() >= scrollLines && lineAdapter.lines() - listLines.size() < scrollLines) {
            if (scrollTimerTask != null) {
                scrollTimerTask.close();
                scrollTimerTask.cancel();
            }
            if (timer != null) {
                timer.cancel();
            }
            handler.removeCallbacks(runnable);
            timer = new Timer();
            scrollTimerTask = new ScrollTimerTask();
            timer.schedule(scrollTimerTask, 100);
        }
    }

    /**
     * 增加滚动内容
     * @param itemLine
     */
    public void addLine(String itemLine) {
        lineAdapter.addLine(itemLine);
        if (lineAdapter.lines() >= scrollLines && lineAdapter.lines() - 1 < scrollLines) {
            if (scrollTimerTask != null) {
                scrollTimerTask.close();
                scrollTimerTask.cancel();
            }
            if (timer != null) {
                timer.cancel();
            }
            handler.removeCallbacks(runnable);
            timer = new Timer();
            scrollTimerTask = new ScrollTimerTask();
            timer.schedule(scrollTimerTask, 100);
        }
    }

    private int getViewHeight(int textSpace, int textLine) {
        return textHeight * textLine + textSpace * 2 * textLine + getPaddingBottom() + getPaddingTop() + (textLine != 0 ? textSpace * 2 : 0);
    }

    private float dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return dpValue * scale;
    }

    private float sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return spValue * fontScale;
    }

    public class ScrollTimerTask extends TimerTask {
        public boolean isClose;

        public void close() {
            isClose = true;
        }

        @Override
        public void run() {
            while (!isClose) {
                try {
                    Thread.sleep(10);
                    if (!isClose) {
                        handler.post(runnable);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_MOVE) {
            return true;
        }
        return super.onTouchEvent(ev);
    }
}
