package com.example.superpippleview;

import android.content.Context;
import android.graphics.Canvas;
import android.view.View;

/**
 * 水波
 */
public class RippleCircleView extends View {

  private float cx, cy;
  private float radius;
  private RippleAnimatorView rippleAnimatorView;

  public RippleCircleView(Context context, RippleAnimatorView rippleAnimatorView) {
    super(context);
    this.rippleAnimatorView = rippleAnimatorView;
    this.setVisibility(VISIBLE);
  }

  @Override
  protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    super.onSizeChanged(w, h, oldw, oldh);
    cx = w / 2f;
    cy = h / 2f;
    radius = Math.min(cx, cy);
  }

  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    //绘制圆

    canvas.drawCircle(cx, cy, radius - rippleAnimatorView.strokeWidth / 2f, rippleAnimatorView.mPaint);
  }

}
