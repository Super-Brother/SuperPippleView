package com.example.superpippleview.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * 2017年之前使用
 */
public class UIRelativeLayout extends RelativeLayout {

  /**
   * 防止多次绘制
   */
  private boolean flag = true;

  public UIRelativeLayout(Context context) {
    super(context);
  }

  public UIRelativeLayout(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public UIRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  @Override
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    if (flag) {
      flag = false;
      float scaleX = UIUtils.getInstance().getHorizontalScaleValue();
      float scaleY = UIUtils.getInstance().getVerticalScaleValue();
      //获取子控件
      int childCount = getChildCount();
      for (int i = 0; i < childCount; i++) {
        //获取子控件对象
        View child = getChildAt(i);
        LayoutParams layoutParams = (LayoutParams) child.getLayoutParams();
        layoutParams.width = (int) (layoutParams.width * scaleX);
        layoutParams.height = (int) (layoutParams.height * scaleY);
        layoutParams.leftMargin = (int) (layoutParams.leftMargin * scaleX);
        layoutParams.topMargin = (int) (layoutParams.topMargin * scaleY);
        layoutParams.rightMargin = (int) (layoutParams.rightMargin * scaleX);
        layoutParams.bottomMargin = (int) (layoutParams.bottomMargin * scaleY);
        //padding
        child.setPadding((int) (child.getPaddingLeft() * scaleX), (int) (child.getPaddingTop() * scaleY),
                (int) (child.getPaddingRight() * scaleX), (int) (child.getPaddingBottom() * scaleY));
      }
    }
  }
}
