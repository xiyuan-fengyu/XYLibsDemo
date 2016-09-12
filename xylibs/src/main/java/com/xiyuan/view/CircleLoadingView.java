package com.xiyuan.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import com.xiyuan.xylibs.R;

/**
 * Created by xiyuan_fengyu on 2016/7/25.
 * loading动画
 * 和SwipeRefreshLayout的刷新动画效果一样
 */
public class CircleLoadingView extends View implements Animation.AnimationListener{

    private int circleRadius;

    private float strokeWidth;

    private int circleColor;

    private Paint paint;

    private RectF circleRect;

    public CircleLoadingView(Context context) {
        super(context);
        init(null);
    }

    public CircleLoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public CircleLoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        if(attrs != null)
        {
            TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.CircleLoadingView);
            circleColor = typedArray.getColor(R.styleable.CircleLoadingView_circleColor, Color.argb(255, 14, 149, 255));
            circleRadius = (int) typedArray.getDimension(R.styleable.CircleLoadingView_circleRadius, 25);
        }

        circleRect = new RectF(0, 0, 0, 0);

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(circleColor);
        paint.setStrokeCap(Paint.Cap.ROUND);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int w = MeasureSpec.getSize(widthMeasureSpec);
        int wModel = MeasureSpec.getMode(widthMeasureSpec);
        int h = MeasureSpec.getSize(heightMeasureSpec);
        int hModel = MeasureSpec.getMode(heightMeasureSpec);

        int size = Math.min(w, h);
        if (size != 0) {
            circleRadius = Math.min((int) (size * 0.25f), circleRadius);
        }
        strokeWidth = circleRadius * 0.3f;
        size = (int) (circleRadius / 0.25f);

        paint.setStrokeWidth(strokeWidth);

        float width = wModel == MeasureSpec.EXACTLY ? w : size + getPaddingLeft() + getPaddingRight();
        float height = hModel == MeasureSpec.EXACTLY ? h : size + getPaddingTop() + getPaddingBottom();

        float halfStroke = strokeWidth / 2;
        circleRect.left = width/ 2 - circleRadius - halfStroke;
        circleRect.top = height / 2 - circleRadius - halfStroke;
        circleRect.right = width / 2 + circleRadius + halfStroke;
        circleRect.bottom = height / 2 + circleRadius + halfStroke;

        setMeasuredDimension((int) width, (int) height);
    }

    private boolean isVisible = true;

    @Override
    protected void onVisibilityChanged(View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        isVisible = visibility == VISIBLE;
        if (isVisible) {
            invalidate();
        }
    }

    private float startAngle = 0;

    private float endAngle = 45;

    private int angleSpeedIndex = 0;

    private static int indexRate = 60;

    private static float M_PI = (float) Math.PI;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawArc(circleRect, startAngle, endAngle - startAngle, false, paint);
        if (isVisible) {
            angleSpeedIndex += 1;
            angleSpeedIndex %= (indexRate * 2);
            endAngle += (Math.sin(M_PI * angleSpeedIndex / indexRate) + 1.2f) * M_PI;
            startAngle += (Math.sin(M_PI * (angleSpeedIndex - indexRate / 3f) / indexRate) + 1.2f) * M_PI;
            float swapeAngle = endAngle - startAngle;
            startAngle %= 360;
            endAngle = startAngle + swapeAngle;
            invalidate();
        }
    }

    private Animation showAnimation;

    public void show() {
        show(true);
    }

    public void show(boolean animated) {
        if (getVisibility() != VISIBLE) {
            if (animated) {
                if (showAnimation == null) {
                    showAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.alpha_in);
                    showAnimation.setAnimationListener(this);
                }

                setVisibility(VISIBLE);
                startAnimation(showAnimation);
            }
            else {
                isVisible = true;
                setVisibility(VISIBLE);
            }
        }
    }

    private Animation hideAnimation;

    public void hide() {
        hide(true);
    }

    public void hide(boolean animated) {
        if (getVisibility() != GONE) {
            if (animated) {
                if (hideAnimation == null) {
                    hideAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.alpha_out);
                    hideAnimation.setAnimationListener(this);
                }

                startAnimation(hideAnimation);
            }
            else {
                isVisible = false;
                setVisibility(GONE);
            }
        }
    }

    @Override
    public void onAnimationStart(Animation animation) {
    }

    @Override
    public void onAnimationEnd(Animation animation) {
        if (animation == hideAnimation) {
            setVisibility(GONE);
        }
    }

    @Override
    public void onAnimationRepeat(Animation animation) {
    }

}
