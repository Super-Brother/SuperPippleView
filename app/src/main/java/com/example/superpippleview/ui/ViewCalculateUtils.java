package com.example.superpippleview.ui;

import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 2017年之后
 */
public class ViewCalculateUtils {

  /**
   * @param view
   * @param width
   * @param height
   * @param lefMargin
   * @param topMargin
   * @param rightMargin
   * @param bottomMargin
   * @param asWidth      依据宽进行缩放
   */
  public static void setViewRelativeLayoutParam(View view, int width, int height, int lefMargin,
                                                int topMargin, int rightMargin, int bottomMargin,
                                                boolean asWidth) {
    RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
    if (layoutParams != null) {
      if (width != ViewGroup.LayoutParams.MATCH_PARENT && width != ViewGroup.LayoutParams.WRAP_CONTENT
              && width != ViewGroup.LayoutParams.FILL_PARENT) {
        layoutParams.width = UIUtils.getInstance().getWidth(width);
      } else {
        layoutParams.width = width;
      }
      if (height != ViewGroup.LayoutParams.MATCH_PARENT && height != ViewGroup.LayoutParams.WRAP_CONTENT
              && height != ViewGroup.LayoutParams.FILL_PARENT) {
        layoutParams.height = asWidth ? UIUtils.getInstance().getWidth(height) : UIUtils.getInstance().getHeight(height);
      } else {
        layoutParams.height = height;
      }
      layoutParams.leftMargin = UIUtils.getInstance().getWidth(lefMargin);
      layoutParams.topMargin = asWidth ? UIUtils.getInstance().getWidth(topMargin)
              : UIUtils.getInstance().getHeight(topMargin);
      layoutParams.rightMargin = UIUtils.getInstance().getWidth(rightMargin);
      layoutParams.bottomMargin = asWidth ? UIUtils.getInstance().getWidth(bottomMargin)
              : UIUtils.getInstance().getHeight(bottomMargin);
      view.setLayoutParams(layoutParams);
    }
  }

  /**
   * 设置View内间距
   *
   * @param view
   * @param leftPadding
   * @param topPadding
   * @param rightPadding
   * @param bottomPadding
   * @param asWidth
   */
  public static void setViewPadding(View view, int leftPadding, int topPadding, int rightPadding,
                                    int bottomPadding, boolean asWidth) {
    view.setPadding(UIUtils.getInstance().getWidth(leftPadding),
            asWidth ? UIUtils.getInstance().getWidth(topPadding) : UIUtils.getInstance().getHeight(topPadding),
            UIUtils.getInstance().getWidth(rightPadding),
            asWidth ? UIUtils.getInstance().getWidth(bottomPadding) : UIUtils.getInstance().getHeight(bottomPadding));
  }

  /**
   * 设置字体缩放
   *
   * @param view
   * @param size
   */
  public static void setTextSize(TextView view, int size) {
    view.setTextSize(TypedValue.COMPLEX_UNIT_PX, UIUtils.getInstance().getHeight(size));
  }
}
