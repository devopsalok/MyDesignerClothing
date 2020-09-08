package com.mydesignerclothing.mobile.android.create;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mydesignerclothing.mobile.android.R;
import com.mydesignerclothing.mobile.android.databinding.FragmentChooseImageBinding;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import static androidx.navigation.Navigation.findNavController;

public class ChooseImageFragment extends Fragment implements View.OnClickListener {
    private String imageUrl;
    private FragmentChooseImageBinding fragmentChooseImageBinding;

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
        if (fragmentChooseImageBinding == null) {
            fragmentChooseImageBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_choose_image, container, false);
            renderStartDesign();
        }
        return fragmentChooseImageBinding.getRoot();
    }

    private void renderStartDesign() {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 1;
        Bitmap bitmap = BitmapFactory.decodeFile(imageUrl, options);
        fragmentChooseImageBinding.productImageView.setImageBitmap(bitmap);

        fragmentChooseImageBinding.cancelBtn.setOnClickListener(this);
        fragmentChooseImageBinding.chooseBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Bundle bundle = new Bundle();
        if (v.getId() == fragmentChooseImageBinding.cancelBtn.getId()) {
            findNavController(requireView()).navigate(R.id.load_image, bundle);
        } else if (v.getId() == fragmentChooseImageBinding.chooseBtn.getId()) {
            bundle.putString("IMAGE_URL", imageUrl);
            findNavController(requireView()).navigate(R.id.load_image, bundle);
        }
    }
}