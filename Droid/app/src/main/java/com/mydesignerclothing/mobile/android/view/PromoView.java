package com.mydesignerclothing.mobile.android.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.Animation;
import android.widget.LinearLayout;
import android.widget.ViewFlipper;

import com.mydesignerclothing.mobile.android.R;
import com.mydesignerclothing.mobile.android.exception.SuperMethodNotImplementedException;
import com.mydesignerclothing.mobile.android.navigation.SegmentedRadioGroup;


import org.springframework.util.CollectionUtils;

import java.util.List;

import androidx.annotation.Nullable;

public class PromoView extends LinearLayout {
  protected static final int X_SWIPE_THRESHOLD = 50;
  protected static final int Y_SWIPE_THRESHOLD = 80;
  private static final int ONE_PROMO = 1;
  protected float touchDownX;
  protected float touchDownY;
  protected ViewFlipper promoViewFlipper;
  protected SegmentedRadioGroup radioGroup;

  public PromoView(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
  }

  /**
   * Set promo view flipper
   */
  protected void setupPromoCarouselView(int radioGroupID) {
    promoViewFlipper.setAutoStart(false);
    promoViewFlipper.setInAnimation(getContext(), R.anim.in_from_right);
    promoViewFlipper.setOutAnimation(getContext(), R.anim.out_to_left);
    radioGroup = setRadioGroup(radioGroupID);
    promoViewFlipper.getInAnimation().setAnimationListener(new Animation.AnimationListener() {
      @Override
      @SuppressWarnings("EmptyMethod")
      public void onAnimationStart(Animation animation) {
        //NOSONAR
      }

      @Override
      public void onAnimationEnd(Animation animation) {
        radioGroup.setChecked(promoViewFlipper.getDisplayedChild());
      }

      @Override
      @SuppressWarnings("EmptyMethod")
      public void onAnimationRepeat(Animation animation) {
        //NOSONAR
      }
    });
  }

  @Override
  public boolean onTouchEvent(MotionEvent event) {
    switch (event.getAction()) {
      case MotionEvent.ACTION_DOWN:
        touchDownX = event.getX();
        break;
      case MotionEvent.ACTION_UP:
        float currentX = event.getX();

        //Check swipe VS click
        //if swiped then true but if clicked then false
        if (Math.abs(touchDownX - currentX) > X_SWIPE_THRESHOLD) {
          if (isSwipeFromRightToLeft(currentX) && hasNextChild(promoViewFlipper.getDisplayedChild())) {
            swipeFromRightToLeft(promoViewFlipper.getDisplayedChild());
          } else if (isSwipeFromLeftToRight(currentX) && hasPreviousChild(promoViewFlipper.getDisplayedChild())) {
            swipeFromLeftToRight(promoViewFlipper.getDisplayedChild());
          }
        } else {
          promoClick(promoViewFlipper.getDisplayedChild());
        }
        break;
    }
    return true;
  }

  @Override
  public boolean onInterceptTouchEvent(MotionEvent ev) {
    return true;
  }

  protected void promoClick(int index) {
    throw new SuperMethodNotImplementedException(getClass().getSimpleName(), "promoClick");
  }

  protected void swipeFromRightToLeft(int displayedChild) {
    throw new SuperMethodNotImplementedException(getClass().getSimpleName(), "swipeFromRightToLeft");
  }

  protected void swipeFromLeftToRight(int displayedChild) {
    throw new SuperMethodNotImplementedException(getClass().getSimpleName(), "swipeFromLeftToRight");
  }

  protected boolean hasOnePromoIn() {
    return promoViewFlipper.getChildCount() == ONE_PROMO;
  }

  protected boolean hasPromo(List<?> models) {
    return !CollectionUtils.isEmpty(models);
  }

  protected boolean hasNoPromosIn() {
    return promoViewFlipper.getChildCount() == 0;
  }

  private SegmentedRadioGroup setRadioGroup(int id) {
    return findViewById(id);
  }

  protected ViewFlipper setPromoViewFlipper(int id) {
    return findViewById(id);
  }

  private boolean isSwipeFromRightToLeft(float currentX) {
    return touchDownX > currentX;
  }

  private boolean isSwipeFromLeftToRight(float currentX) {
    return currentX > touchDownX;
  }

  private boolean hasNextChild(int index) {
    return index != promoViewFlipper.getChildCount() - 1;
  }

  private boolean hasPreviousChild(int index) {
    return index != 0;
  }
}
