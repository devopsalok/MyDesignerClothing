package com.mydesignerclothing.mobile.android.create;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.mydesignerclothing.mobile.android.R;
import com.mydesignerclothing.mobile.android.create.adapter.ProductDetailColorDialogAdapter;
import com.mydesignerclothing.mobile.android.create.adapter.ProductDetailSizeDialogAdapter;
import com.mydesignerclothing.mobile.android.create.services.model.ProductOptionsList;
import com.mydesignerclothing.mobile.android.create.view.CreateDialogClickListener;
import com.mydesignerclothing.mobile.android.create.view.CreateInfoListener;
import com.mydesignerclothing.mobile.android.create.view.CreateInfoView;
import com.mydesignerclothing.mobile.android.create.viewmodel.ProductDetailDialogViewModel;
import com.mydesignerclothing.mobile.android.create.viewmodel.ProductDetailSizeDialogViewModel;
import com.mydesignerclothing.mobile.android.databinding.ProductDetailColorDialogBinding;
import com.mydesignerclothing.mobile.android.databinding.ProductDetailSizeDialogBinding;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CreateDialogFragment extends DialogFragment {

    public static final String WHICH_DIALOG = "which_dialog";
    private static final String TAG = CreateDialogFragment.class.getSimpleName();
    public static final String NEW_DIALOG_ORDINAL = "new_dialog_name"; // name it better
    public static final String EXTRAS_COLORS_MODEL = TAG + ".EXTRAS_COLORS_MODEL";
    public static final String EXTRAS_SIZE_MODEL = TAG + ".EXTRAS_SIZE_MODEL";
    public static final String DEFAULT_FRAGMENT_TAG = "create_dialog";

    public enum SeatMapDialogType {
        DEFAULT_DIALOG,
        SIZE_DIALOG,
        COLORS_DIALOG,
        ADVISORY_DIALOG,
        GALLERY_DIALOG
    }

    private final List<SeatMapDialogType> fullScreenDialogs = Collections.unmodifiableList(Arrays.asList(
            SeatMapDialogType.SIZE_DIALOG,
            SeatMapDialogType.COLORS_DIALOG,
            SeatMapDialogType.ADVISORY_DIALOG,
            SeatMapDialogType.GALLERY_DIALOG
            )
    );
    private CreateInfoView createInfoViewListener;
    private CreateInfoListener createInfoListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCancelable(false);

        if (fullScreenDialogs.contains(getSeatDialogType())) {
            setStyle(DialogFragment.STYLE_NORMAL, R.style.App_Dialog_FullScreen);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        switch (getSeatDialogType()) {
            case COLORS_DIALOG:
                return buildColorsDialog(inflater, container, R.layout.product_detail_color_dialog, getArguments().getParcelable(EXTRAS_COLORS_MODEL));
            case SIZE_DIALOG:
                return buildSizeDialog(inflater, container, R.layout.product_detail_size_dialog, getArguments().getParcelable(EXTRAS_SIZE_MODEL));
            case GALLERY_DIALOG:
                return buildGalleryOptionDialog(inflateView(inflater, container, R.layout.product_gallery_option_dialog));
            case ADVISORY_DIALOG:
                return buildGalleryAdvisoryDialog(inflateView(inflater, container, R.layout.product_gallery_advisory_dialog));
            default:
                return null;
        }
    }

    @Override
    public void onAttach(Context context) {
        if (requireActivity() instanceof CreateDialogClickListener) {
            //  createDialogClickListener = (CreateDialogClickListener) requireActivity();
        }
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        //createDialogClickListener = null;
        super.onDetach();
    }

    private View inflateView(LayoutInflater inflater, ViewGroup container, @LayoutRes int resourceId) {
        return inflater.inflate(resourceId, container, false);
    }

    private SeatMapDialogType getSeatDialogType() {
        int whichDialog = getArguments().getInt(WHICH_DIALOG, SeatMapDialogType.DEFAULT_DIALOG.ordinal());
        return SeatMapDialogType.values()[whichDialog];
    }

    private View buildColorsDialog(LayoutInflater inflater, ViewGroup parent, @LayoutRes int resourceId, ProductOptionsList productOptionsList) {
        ProductDetailColorDialogBinding productDetailColorDialogBinding = DataBindingUtil.inflate(inflater, resourceId, parent, false);
        ProductDetailDialogViewModel productDetailDialogViewModel = new ProductDetailDialogViewModel(productOptionsList);
        productDetailColorDialogBinding.setProductDetailDialogViewModel(productDetailDialogViewModel);

        productDetailColorDialogBinding.colorRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        productDetailColorDialogBinding.colorRecyclerView.setAdapter(new ProductDetailColorDialogAdapter(productOptionsList.getProductColorsList(), createInfoViewListener));
        productDetailColorDialogBinding.confirmBtn.setOnClickListener(v -> createInfoViewListener.onColorDialogConfirmButtonClicked());
        productDetailColorDialogBinding.cancelBtn.setOnClickListener(v -> createInfoViewListener.onColorCancelButtonClicked());

        return productDetailColorDialogBinding.getRoot();
    }

    private View buildSizeDialog(LayoutInflater inflater, ViewGroup parent, @LayoutRes int resourceId, ProductOptionsList productOptionsList) {
        ProductDetailSizeDialogBinding productDetailSizeDialogBinding = DataBindingUtil.inflate(inflater, resourceId, parent, false);
        ProductDetailSizeDialogViewModel productDetailDialogViewModel = new ProductDetailSizeDialogViewModel(productOptionsList);
        productDetailSizeDialogBinding.setProductDetailSizeDialogViewModel(productDetailDialogViewModel);

        productDetailSizeDialogBinding.sizeRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        productDetailSizeDialogBinding.sizeRecyclerView.setAdapter(new ProductDetailSizeDialogAdapter(productOptionsList.getProductColorsList(), createInfoViewListener));
        productDetailSizeDialogBinding.confirmBtn.setOnClickListener(v -> createInfoViewListener.onSizeDialogConfirmButtonClicked());
        productDetailSizeDialogBinding.cancelBtn.setOnClickListener(v -> createInfoViewListener.onSizeCancelButtonClicked());

        return productDetailSizeDialogBinding.getRoot();
    }

    private View buildGalleryOptionDialog(View view) {
        return buildGalleryDialog(view);
    }

    private View buildGalleryDialog(View view) {
        Button takePictureButton = view.findViewById(R.id.take_picture_btn);
        Button chooseGalleryButton = view.findViewById(R.id.choose_gallery_btn);
        Button cancelButton = view.findViewById(R.id.cancel_btn);
        takePictureButton.setOnClickListener(v -> createInfoListener.onTakePictureButtonClicked());
        chooseGalleryButton.setOnClickListener(v -> createInfoListener.onChooseGalleryButtonClicked());
        cancelButton.setOnClickListener(v -> createInfoListener.onCancelButtonClicked());

        return view;
    }

    private View buildGalleryAdvisoryDialog(View view) {
        return buildAdvisoryDialog(view);
    }

    private View buildAdvisoryDialog(View view) {
        Button okButton = view.findViewById(R.id.ok_btn);
        okButton.setOnClickListener(v -> createInfoListener.onOkButtonClicked());
        return view;
    }

    public void setProductDetailFragmentViewListener(CreateInfoView createInfoViewListener) {
        this.createInfoViewListener = createInfoViewListener;
    }

    public void setLoadImageFragmentViewListener(CreateInfoListener createInfoListener) {
        this.createInfoListener = createInfoListener;
    }
}
