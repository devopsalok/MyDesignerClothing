package com.mydesignerclothing.mobile.android.navigation;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ViewFlipper;

import com.mydesignerclothing.mobile.android.BR;
import com.mydesignerclothing.mobile.android.R;
import com.mydesignerclothing.mobile.android.create.services.model.ProductDetailImages;
import com.mydesignerclothing.mobile.android.create.view.CreateInfoListener;
import com.mydesignerclothing.mobile.android.create.viewmodel.ProductDetailImageViewModel;
import com.mydesignerclothing.mobile.android.view.PromoView;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;


public class ProductDetailCarousel extends PromoView {
    private CreateInfoListener createInfoListener;

    public ProductDetailCarousel(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * This method is to create upsell promo view flipper if we have any upsell
     *
     * @param productDetailImagesList - list of models for the user interface
     * @param createInfoListener      - onclick listener to be used in the carousel
     */
    public void setupUI(List<ProductDetailImages> productDetailImagesList,
                        CreateInfoListener createInfoListener) {
        if (hasPromo(productDetailImagesList)) {
            this.createInfoListener = createInfoListener;
            promoViewFlipper = setPromoViewFlipper(R.id.product_detail_view_flipper);
            promoViewFlipper.removeAllViews();
            setupPromoCarouselView(R.id.product_detail_page_view_control);
            setProductDetailImages(promoViewFlipper, productDetailImagesList);
            radioGroup.setupCarouselDots(promoViewFlipper.getChildCount());
            if (hasOnePromoIn()) {
                radioGroup.setVisibility(View.GONE);
                promoViewFlipper.setAutoStart(false);
                promoViewFlipper.stopFlipping();
            }
        } else {
            setVisibility(View.GONE);
        }
    }

    private void setProductDetailImages(ViewFlipper promoViewFlipper, List<ProductDetailImages> productDetailImagesList) {
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        ViewDataBinding promoContainer;
        for (int i = 0; i < productDetailImagesList.size(); i++) {
            View itemView = layoutInflater.inflate(R.layout.product_detail_promo_view, promoViewFlipper, false);
            promoContainer = DataBindingUtil.bind(itemView);
            ProductDetailImageViewModel productDetailImageViewModel = new ProductDetailImageViewModel(productDetailImagesList.get(i), getResources());
            promoContainer.setVariable(BR.productDetailViewModel, productDetailImageViewModel);

            promoViewFlipper.addView(promoContainer.getRoot());
        }
    }

    @Override
    protected void swipeFromRightToLeft(int displayedChild) {
        promoViewFlipper.setInAnimation(promoViewFlipper.getContext(), R.anim.in_from_right);
        promoViewFlipper.setOutAnimation(promoViewFlipper.getContext(), R.anim.out_to_left);
        promoViewFlipper.showNext();
        radioGroup.setChecked(displayedChild + 1);
        createInfoListener.onProductImageClick(displayedChild + 1);
    }

    @Override
    protected void swipeFromLeftToRight(int displayedChild) {
        promoViewFlipper.setInAnimation(promoViewFlipper.getContext(), R.anim.in_from_left);
        promoViewFlipper.setOutAnimation(promoViewFlipper.getContext(), R.anim.out_to_right);
        promoViewFlipper.showPrevious();
        radioGroup.setChecked(displayedChild - 1);
        createInfoListener.onProductImageClick(displayedChild + 1);
    }

    @Override
    protected void promoClick(int index) {
        createInfoListener.onProductImageClick(index);
    }
}
