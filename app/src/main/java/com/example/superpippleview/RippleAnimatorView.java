package com.example.superpippleview;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.animation.AccelerateInterpolator;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;

import com.example.superpippleview.ui.UIUtils;

import java.util.ArrayList;

/**
 * @author zhang
 */
public class RippleAnimatorView extends RelativeLayout {

  Paint mPaint;
  private int rippleColor;//水波纹颜色
  private int radius;//半径
  int strokeWidth;//线宽

  private ArrayList<RippleCircleView> viewList = new ArrayList<>();
  private AnimatorSet animatorSet;
  private boolean animationRunning = false;//当前动画状态
  ArrayList<Animator> animatorList;//存放所有波纹的动画

  public RippleAnimatorView(Context context) {
    this(context, null);
  }

  public RippleAnimatorView(Context context, @Nullable AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public RippleAnimatorView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init(context, attrs);
  }

  private void init(Context context, AttributeSet attrs) {
    animatorList = new ArrayList<>();
    mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RippleAnimatorView);
    //初始化
    rippleColor = typedArray.getColor(R.styleable.RippleAnimatorView_ripple_anim_color, Color.YELLOW);
    int rippleType = typedArray.getInt(R.styleable.RippleAnimatorView_ripple_anim_type, 0);
    radius = typedArray.getInteger(R.styleable.RippleAnimatorView_radius, 54);
    strokeWidth = typedArray.getInteger(R.styleable.RippleAnimatorView_strokeWidth, 4);
    mPaint.setStyle(rippleType == 0 ? Paint.Style.FILL : Paint.Style.STROKE);//设置水波类型
    mPaint.setColor(rippleColor);
    mPaint.setStrokeWidth(strokeWidth);
    typedArray.recycle();//用完记得回收

    //添加水波控件(屏幕适配)
    LayoutParams layoutParams = new LayoutParams(UIUtils.getInstance().getWidth(radius + strokeWidth / 2),
            UIUtils.getInstance().getWidth(radius + strokeWidth / 2));//子控件大小
    //子控件居中
    layoutParams.addRule(CENTER_IN_PARENT, TRUE);
    //计算最大放大倍数
    float maxValue = UIUtils.displayMetricsWidth * 1.6f / UIUtils.getInstance().getWidth(radius + strokeWidth / 2);
    //延时
    int rippleDuration = 3500;
    int singleDelay = rippleDuration / 6;//间隔时间
    for (int i = 0; i < 6; i++) {
      //添加水波
      RippleCircleView rippleCircleView = new RippleCircleView(getContext(), this);
      addView(rippleCircleView, layoutParams);
      viewList.add(rippleCircleView);
      //动画初始化
//      //x
//      ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(rippleCircleView, "scaleX",
//              1, maxValue);
//      scaleXAnimator.setDuration(rippleDuration);
//      scaleXAnimator.setRepeatCount(ValueAnimator.INFINITE);
//      scaleXAnimator.setRepeatMode(ValueAnimator.RESTART);
//      scaleXAnimator.setStartDelay(i * singleDelay);//延时
//      animatorList.add(scaleXAnimator);
//      //y
//      ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(rippleCircleView, "scaleY",
//              1, maxValue);
//      scaleYAnimator.setDuration(rippleDuration);
//      scaleYAnimator.setRepeatCount(ValueAnimator.INFINITE);
//      scaleYAnimator.setRepeatMode(ValueAnimator.RESTART);
//      scaleYAnimator.setStartDelay(i * singleDelay);//延时
//      animatorList.add(scaleYAnimator);
//      //y
//      ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(rippleCircleView, "alpha",
//              1, 0);
//      alphaAnimator.setDuration(rippleDuration);
//      alphaAnimator.setRepeatCount(ValueAnimator.INFINITE);
//      alphaAnimator.setRepeatMode(ValueAnimator.RESTART);
//      alphaAnimator.setStartDelay(i * singleDelay);//延时
//      animatorList.add(alphaAnimator);
      PropertyValuesHolder holder1 = PropertyValuesHolder.ofFloat("scaleX", maxValue, 1);
      PropertyValuesHolder holder2 = PropertyValuesHolder.ofFloat("scaleY", maxValue, 1);
      PropertyValuesHolder holder3 = PropertyValuesHolder.ofFloat("alpha", 0, 1);
      ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(rippleCircleView, holder1, holder2, holder3);
      animator.setDuration(rippleDuration);
      animator.setRepeatCount(ValueAnimator.INFINITE);
      animator.setRepeatMode(ValueAnimator.RESTART);
      animator.setStartDelay(i * singleDelay);//延时
      animatorList.add(animator);
    }
    animatorSet = new AnimatorSet();
    animatorSet.setInterpolator(new AccelerateInterpolator());
    animatorSet.playTogether(animatorList);//同步开启
  }

  /**
   * 获取当前控件动画状态
   *
   * @return
   */
  public boolean isAnimationRunning() {
    return animationRunning;
  }

  /**
   * 开启动画
   */
  public void startRippleAnimation() {
    if (!isAnimationRunning()) {
      for (RippleCircleView rippleCircleView : viewList) {
        rippleCircleView.setVisibility(VISIBLE);
      }
      animatorSet.start();
      animationRunning = true;
    }
  }

  /**
   * 停止动画
   */
  public void stopRippleAnimation() {
    if (isAnimationRunning()) {
      for (RippleCircleView rippleCircleView : viewList) {
        rippleCircleView.setVisibility(INVISIBLE);
      }
      animatorSet.end();
      animationRunning = false;
    }
  }

}
