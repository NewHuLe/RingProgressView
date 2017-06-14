package cn.com.cesgroup.mygithub;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Author        Hule  hu.le@cesgroup.com.cn
 * Date          2017/6/12 13:30
 * Description:  TODO: 自定义环形进度条
 */

public class RingProgressView extends View {

    // 圆环的颜色
    private int ringColor = 0xFF00FF00;
    // 圆环进度的颜色
    private int ringProgressColor = 0xFFFF0000;
    //圆环的宽度
    private int ringWidth = 10;
    // 字体大小
    private int textSize = 20;
    // 字体颜色
    private int textColor = 0xFF0000FF;
    // 当前进度
    private int currentProgress = 60;
    // 最大进度
    private int maxProgress = 100;
    // 得到控件的宽度
    private int width;
    // 画笔对象
    private Paint paint;
    // 上下文
    private Context context;

    // 默认的构造方法，一般取这3个就够用了
    public RingProgressView(Context context) {
        this(context, null);
    }

    public RingProgressView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RingProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        // 得到自定义资源数组
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RingProgressView);
        ringColor = typedArray.getColor(R.styleable.RingProgressView_ringColor, ringColor);
        ringProgressColor = typedArray.getColor(R.styleable.RingProgressView_ringProgressColor, ringProgressColor);
        ringWidth = (int) typedArray.getDimension(R.styleable.RingProgressView_ringWidth, dip2px(10));
        textSize = (int) typedArray.getDimension(R.styleable.RingProgressView_textSize, dip2px(20));
        textColor = typedArray.getColor(R.styleable.RingProgressView_textColor, textColor);
        currentProgress = typedArray.getInt(R.styleable.RingProgressView_currentProgress, currentProgress);
        maxProgress = typedArray.getColor(R.styleable.RingProgressView_maxProgress, maxProgress);
        typedArray.recycle();

        paint = new Paint();
        // 抗锯齿
        paint.setAntiAlias(true);
    }


    // 测量
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = getMeasuredWidth();
    }

    // 绘制
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 1. 计算圆心坐标及半径
        float centerX = width / 2;
        float centerY = width / 2;
        float radius = width / 2 - ringWidth / 2;


        // 2. 画圆环
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(ringWidth);
        paint.setColor(ringColor);
        canvas.drawCircle(centerX, centerY, radius, paint);


        // 3. 画圆弧
        RectF rectF = new RectF(ringWidth / 2, ringWidth / 2, width - ringWidth / 2, width - ringWidth / 2);
        paint.setColor(ringProgressColor);
        canvas.drawArc(rectF, 0, currentProgress * 360 / maxProgress, false, paint);

        // 3. 画文本
        String text = currentProgress * 100 / maxProgress + "%";
        paint.setColor(textColor);
        paint.setTextSize(textSize);
        // 要重新设置宽度为0
        paint.setStrokeWidth(0);
        // 得到指定文本边界的指定大小
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);
//        float textWidth = paint.measureText(text);
//        float textHigh = textSize;
        canvas.drawText(text, width / 2 - bounds.width() / 2, width / 2 + bounds.height() / 2, paint);
    }

    // 布局
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    /**
     * 把dp转换成px
     *
     * @param dipValue
     * @return
     */
    private int dip2px(int dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public int getCurrentProgress() {
        return currentProgress;
    }

    public void setCurrentProgress(int currentProgress) {
        this.currentProgress = currentProgress;
    }

    public int getMaxProgress() {
        return maxProgress;
    }

    public void setMaxProgress(int maxProgress) {
        this.maxProgress = maxProgress;
    }
}
