package com.example.superpippleview;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.superpippleview.ui.UIUtils;
import com.example.superpippleview.ui.ViewCalculateUtils;

public class MainActivity extends AppCompatActivity {

  private ImageView imageView;
  private RippleAnimatorView rippleAnimatorView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
//    setContentView(new LoadingView(this));
    UIUtils.getInstance(this);

    setContentView(R.layout.activity_main);

    imageView = findViewById(R.id.image);
    ViewCalculateUtils.setViewRelativeLayoutParam(imageView,150,150,0,
            0,0,0,true);

    rippleAnimatorView = findViewById(R.id.ripple_view);
    imageView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        if(rippleAnimatorView.isAnimationRunning()) {
          rippleAnimatorView.stopRippleAnimation();
        } else {
          rippleAnimatorView.startRippleAnimation();
        }
      }
    });
  }

}
