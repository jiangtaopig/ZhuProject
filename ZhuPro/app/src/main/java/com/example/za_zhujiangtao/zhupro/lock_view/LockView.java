package com.example.za_zhujiangtao.zhupro.lock_view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import com.example.za_zhujiangtao.zhupro.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by za-zhujiangtao on 2018/12/17.
 */

public class LockView extends View {
    // 状态常量
    private static final int STATE_NORMAL = 0x001; // 默认状态
    private static final int STATE_SELECT = 0x002; // 选中状态
    private static final int STATE_CORRECT = 0x003; // 正确状态
    private static final int STATE_WRONG = 0x004; // 错误状态
    // 自定义属性
    private int normalColor = Color.GRAY; // 默认显示的颜色
    private int selectColor = Color.YELLOW; // 选中时显示的颜色
    private int correctColor = Color.GREEN; // 正确时显示的颜色
    private int wrongColor = Color.RED; // 错误时显示的颜色
    private int lineWidth = -1; // 连线的宽度
    // 宽高相关
    private int width; // 父布局分配给这个View的宽度
    private int height; // 父布局分配给这个View的高度
    private int rectRadius; // 每个小圆圈的宽度（直径）
    // 元素相关
    private List<CircleRect> rectList; // 存储所有圆圈对象的列表
    private List<CircleRect> pathList; // 存储用户绘制的连线上的所有圆圈对象
    // 绘制相关
    private Canvas mCanvas; // 用于绘制元素的画布
    private Bitmap mBitmap; // 用户绘制元素的Bitmap
    private Path mPath; // 用户绘制的线条
    private Path tmpPath; // 记录用户以前绘制过的线条
    private Paint circlePaint; // 用户绘制圆圈的画笔
    private Paint pathPaint; // 用户绘制连线的画笔
    // 触摸相关
    private int startX; // 上一个节点的X坐标
    private int startY; // 上一个节点的Y坐标
    private boolean isUnlocking; // 是否正在解锁（手指落下时是否刚好在一个节点上）
    // 结果相关
    private OnUnlockListener listener;

    public LockView(Context context) {
        this(context, null);
    }

    public LockView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LockView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // 初始化一些对象（List等）
        rectList = new ArrayList<>();
        pathList = new ArrayList<>();
        // 获取自定义属性
        TypedArray array = context.getTheme().obtainStyledAttributes(attrs, R.styleable.LockView, defStyleAttr, 0);
        int count = array.getIndexCount();
        for (int i = 0; i < count; i++) {
            int attr = array.getIndex(i);
            switch (attr) {
                case R.styleable.LockView_normalColor:
                    normalColor = array.getColor(attr, Color.GRAY);
                    break;
                case R.styleable.LockView_selectColor:
                    selectColor = array.getColor(attr, Color.YELLOW);
                    break;
                case R.styleable.LockView_correctColor:
                    correctColor = array.getColor(attr, Color.GREEN);
                    break;
                case R.styleable.LockView_wrongColor:
                    wrongColor = array.getColor(attr, Color.RED);
                    break;
                case R.styleable.LockView_lineWidth:
                    lineWidth = (int) array.getDimension(attr, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, context.getResources().getDisplayMetrics()));
                    break;
            }
        }
        if (lineWidth == -1) {
            lineWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, context.getResources().getDisplayMetrics());
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 获取到控件的宽高属性值
        width = getMeasuredWidth();
        height = getMeasuredHeight();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        // 初始化绘制相关的元素
        mBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);
        circlePaint = new Paint();
        circlePaint.setAntiAlias(true);
        circlePaint.setDither(true);
        mPath = new Path();
        tmpPath = new Path();
        pathPaint = new Paint();
        pathPaint.setDither(true);
        pathPaint.setAntiAlias(true);
        pathPaint.setStyle(Paint.Style.STROKE);
        pathPaint.setStrokeCap(Paint.Cap.ROUND);
        pathPaint.setStrokeJoin(Paint.Join.ROUND);
        pathPaint.setStrokeWidth(lineWidth);
        // 初始化一些宽高属性
        int horizontalSpacing;
        int verticalSpacing;
        if (width <= height) {
            horizontalSpacing = 0;
            verticalSpacing = (height - width) / 2;
            rectRadius = width / 14;
        } else {
            horizontalSpacing = (width - height) / 2;
            verticalSpacing = 0;
            rectRadius = height / 14;
        }
        // 初始化所有CircleRect对象
        for (int i = 1; i <= 9; i++) {
            int x = ((i - 1) % 3 * 2 + 1) * rectRadius * 2 + horizontalSpacing + getPaddingLeft() + rectRadius;
            int y = ((i - 1) / 3 * 2 + 1) * rectRadius * 2 + verticalSpacing + getPaddingTop() + rectRadius;
            CircleRect rect = new CircleRect(i, x, y, STATE_NORMAL);
            rectList.add(rect);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(mBitmap, 0, 0, null);
        for (int i = 0; i < rectList.size(); i++) {
            drawCircle(rectList.get(i), rectList.get(i).getState());
        }
        canvas.drawPath(mPath, pathPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int currX = (int) event.getX();
        int currY = (int) event.getY();
        CircleRect rect = getOuterRect(currX, currY);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // 保证手指按下后所有元素都是初始状态
                this.reset();
                // 判断手指落点是否在某个圆圈中，如果是则设置该圆圈为选中状态
                if (rect != null) {
                    rect.setState(STATE_SELECT);
                    startX = rect.getX();
                    startY = rect.getY();
                    tmpPath.moveTo(startX, startY);
                    pathList.add(rect);
                    isUnlocking = true;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (isUnlocking) {
                    mPath.reset();
                    mPath.addPath(tmpPath);
                    mPath.moveTo(startX, startY);
                    mPath.lineTo(currX, currY);
                    if (rect != null) {
                        rect.setState(STATE_SELECT);
                        startX = rect.getX();
                        startY = rect.getY();
                        tmpPath.lineTo(startX, startY);
                        pathList.add(rect);
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                isUnlocking = false;
                if (pathList.size() > 0) {
                    mPath.reset();
                    mPath.addPath(tmpPath);
                    StringBuilder result = new StringBuilder();
                    for (int i = 0; i < pathList.size(); i++) {
                        result.append(pathList.get(i).getCode());
                    }
                    if (listener.isUnlockSuccess(result.toString())) {
                        listener.onSuccess();
                        setWholePathState(STATE_CORRECT);
                    } else {
                        this.reset();
//                        mPath.reset();
                        listener.onFailure();
//                        setWholePathState(STATE_WRONG);
                    }
                }
                break;
        }
        invalidate();
        return true;
    }

    /**
     * 根据状态（解锁成功/失败）改变整条路径上所有元素的颜色
     *
     * @param state 状态（解锁成功/失败）
     */
    private void setWholePathState(int state) {
        pathPaint.setColor(getColorByState(state));
        for (final CircleRect rect : pathList) {
            rect.setState(state);
        }
    }

    /**
     * 通过状态得到应显示的颜色
     *
     * @param state 状态
     * @return 给定状态下应该显示的颜色
     */
    private int getColorByState(int state) {
        int color = normalColor;
        switch (state) {
            case STATE_NORMAL:
                color = normalColor;
                break;
            case STATE_SELECT:
                color = selectColor;
                break;
            case STATE_CORRECT:
                color = correctColor;
                break;
            case STATE_WRONG:
                color = wrongColor;
                break;
        }
        return color;
    }

    /**
     * 根据参数中提供的圆圈参数绘制圆圈
     *
     * @param rect  存储圆圈所有参数的CircleRect对象
     * @param state 圆圈的当前状态
     */
    private void drawCircle(CircleRect rect, int state) {
        circlePaint.setColor(getColorByState(state));
        mCanvas.drawCircle(rect.getX(), rect.getY(), rectRadius, circlePaint);
    }

    /**
     * 判断参数中的x、y坐标对应的点是否在某个圆圈内，如果在则返回这个圆圈，否则返回null
     *
     * @param x 给定的点的X坐标
     * @param y 给定的点的Y坐标
     * @return 给定点所在的圆圈对象，如果不在任何一个圆圈内则返回null
     */
    private CircleRect getOuterRect(int x, int y) {
        for (int i = 0; i < rectList.size(); i++) {
            CircleRect rect = rectList.get(i);
            if ((x - rect.getX()) * (x - rect.getX()) + (y - rect.getY()) * (y - rect.getY()) <= rectRadius * rectRadius) {
                if (rect.getState() != STATE_SELECT) {
                    return rect;
                }
            }
        }
        return null;
    }

    /**
     * 解锁，手指抬起后回调的借口
     */
    interface OnUnlockListener {
        // 由用户来判断解锁是否成功
        boolean isUnlockSuccess(String result);

        // 当解锁成功时回调的方法
        void onSuccess();

        // 当解锁失败时回调的方法
        void onFailure();
    }

    /**
     * 为当前View设置结果监听器
     */
    public void setOnUnlockListener(OnUnlockListener listener) {
        this.listener = listener;
    }

    /**
     * 重置所有元素的状态到初始状态
     */
    public void reset() {
        setWholePathState(STATE_NORMAL);
        pathPaint.setColor(selectColor);
        mPath.reset();
        tmpPath.reset();
        pathList = new ArrayList<>();
    }
}