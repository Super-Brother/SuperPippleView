package com.example.superpippleview.ui;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import java.lang.reflect.Field;

/**
 * @author zhang
 * 工具类
 */
public class UIUtils {

  private static UIUtils instance;
  //标准值
  public static float STANDARD_WIDTH = 1080f;
  public static float STANDARD_HEIGHT = 1920f;
  //获取屏幕宽高
  public static float displayMetricsWidth;
  public static float displayMetricsHeight;
  public static float statusBarHeight;

  private UIUtils(Context context) {
    //计算缩放系数
    if (displayMetricsWidth == 0 || displayMetricsHeight == 0) {
      WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
      DisplayMetrics displayMetrics = new DisplayMetrics();
      //注意,高度忽略了NavigationBar的高度
      windowManager.getDefaultDisplay().getMetrics(displayMetrics);
      //获取真实宽高
//      windowManager.getDefaultDisplay().getRealMetrics(displayMetrics);
      //判断横竖屏
      if (displayMetrics.widthPixels > displayMetrics.heightPixels) {
        //横屏
        displayMetricsWidth = displayMetrics.heightPixels;
        displayMetricsHeight = displayMetrics.widthPixels;
      } else {
        //竖屏
        displayMetricsWidth = displayMetrics.widthPixels;
        displayMetricsHeight = displayMetrics.heightPixels;
      }
    }
  }

  /**
   * application
   *
   * @param context
   * @return
   */
  public static UIUtils getInstance(Context context) {
    if (instance == null) {
      instance = new UIUtils(context);
    }
    return instance;
  }

  /**
   * activity
   *
   * @return
   */
  public static UIUtils getInstance() {
    if (instance == null) {
      throw new RuntimeException("UIUtils需要先初始化");
    }
    return instance;
  }

  public static UIUtils notifyInstance(Context context) {
    //更改标准值。。。
    if (instance == null) {
      instance = new UIUtils(context);
    }
    return instance;
  }

  public float getHorizontalScaleValue() {
    return displayMetricsWidth / STANDARD_WIDTH;
  }

  public float getVerticalScaleValue() {
    return displayMetricsHeight / STANDARD_HEIGHT;
  }

  public int getWidth(int width) {
    return Math.round(width * displayMetricsWidth / STANDARD_WIDTH);
  }

  public int getHeight(int height) {
    return Math.round(height * displayMetricsHeight / STANDARD_HEIGHT);
  }

  public static int getStatusBarHeight(Context context) {
    return getValue(context, "com.android.internal.R&dimen", "status_bar_height", 48);
  }

  private static int getValue(Context context, String dimenClass, String system_bar_height, int defaultValue) {
    //反射
    try {
      Class<?> clazz = Class.forName(dimenClass);
      Object instance = clazz.newInstance();
      Field field = clazz.getField(system_bar_height);
      int resId = Integer.parseInt(field.get(instance).toString());
      return context.getResources().getDimensionPixelSize(resId);//dp转px
    } catch (Exception e) {
      e.printStackTrace();
    }
    return defaultValue;
  }

}
