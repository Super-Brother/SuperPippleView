package com.example.superpippleview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * @author zhang
 */
public class LoadingView extends View {

  private Path mPath;
  private Path mDst;
  private PathMeasure mPathMeasure;
  private float mLength;
  private Paint mPaint;
  private ValueAnimator mAnimator;
  //动画百分比
  private float mAnimatorValue;

  public LoadingView(Context context) {
    this(context, null);
  }

  public LoadingView(Context context, @Nullable AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public LoadingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    //初始化
    init();
  }

  private void init() {
    //画笔初始化
    mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);//设置抗锯齿
//    mPaint.setAntiAlias(true);
    mPaint.setColor(Color.RED);
    mPaint.setStrokeWidth(10f);
    mPaint.setStyle(Paint.Style.STROKE);
    //path
    mPath = new Path();
    mPath.addCircle(300f, 300f, 100f, Path.Direction.CW);
    //mDst
    mDst = new Path();
    //mPathMeasure
    mPathMeasure = new PathMeasure(mPath, true);
    mLength = mPathMeasure.getLength();

    mAnimator = ValueAnimator.ofFloat(0, 1f);
    mAnimator.setDuration(2000);
    mAnimator.setRepeatCount(ValueAnimator.INFINITE);
    mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
      @Override
      public void onAnimationUpdate(ValueAnimator valueAnimator) {
        //获取动画百分比
        mAnimatorValue = (float) valueAnimator.getAnimatedValue();
        invalidate();
//        可以跨线程发消息
//        postInvalidate();
      }
    });
    mAnimator.start();
  }

  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    //绘制
    mDst.reset();
    float start = mAnimatorValue > 0.5f ? ((mAnimatorValue - 0.5f) * 2 * mLength) : 0;
    float distance = mLength * mAnimatorValue;
    mPathMeasure.getSegment(start, distance, mDst, true);
    //获取到目标path
    canvas.drawPath(mDst, mPaint);
  }

}
