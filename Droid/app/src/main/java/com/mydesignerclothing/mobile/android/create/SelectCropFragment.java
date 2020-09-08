package com.mydesignerclothing.mobile.android.create;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mydesignerclothing.mobile.android.R;
import com.mydesignerclothing.mobile.android.databinding.FragmentLoadImageBinding;
import com.mydesignerclothing.mobile.android.databinding.FragmentSelectCropBinding;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import static androidx.navigation.Navigation.findNavController;

public class SelectCropFragment extends Fragment implements View.OnClickListener {

    private String imageUrl;
    private FragmentSelectCropBinding fragmentSelectCropBinding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        if (bundle != null) {
            imageUrl = bundle.getString("IMAGE_URL");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (fragmentSelectCropBinding == null) {
            setHasOptionsMenu(true);
            fragmentSelectCropBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_select_crop, container, false);
        }
        fragmentSelectCropBinding.circleBtn.setOnClickListener(this);
        fragmentSelectCropBinding.rectangleBtn.setOnClickListener(this);
        fragmentSelectCropBinding.squareBtn.setOnClickListener(this);
        return fragmentSelectCropBinding.getRoot();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == fragmentSelectCropBinding.circleBtn.getId()) {
            Bundle bundle = new Bundle();
            bundle.putString("IMAGE_URL", imageUrl);
            bundle.putString("SHAPE_TYPE", "CIRCLE");
            findNavController(requireView()).navigate(R.id.crop_fragment, bundle);
        } else if (v.getId() == fragmentSelectCropBinding.rectangleBtn.getId()) {
            Bundle bundle = new Bundle();
            bundle.putString("IMAGE_URL", imageUrl);
            bundle.putString("SHAPE_TYPE", "RECTANGLE");
            findNavController(requireView()).navigate(R.id.crop_fragment, bundle);
        } else if (v.getId() == fragmentSelectCropBinding.squareBtn.getId()) {
            Bundle bundle = new Bundle();
            bundle.putString("IMAGE_URL", imageUrl);
            bundle.putString("SHAPE_TYPE", "SQUARE");
            findNavController(requireView()).navigate(R.id.crop_fragment, bundle);
        }
    }
}