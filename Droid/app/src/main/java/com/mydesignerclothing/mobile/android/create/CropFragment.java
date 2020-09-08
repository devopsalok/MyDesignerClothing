package com.mydesignerclothing.mobile.android.create;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mydesignerclothing.mobile.android.R;
import com.mydesignerclothing.mobile.android.databinding.FragmentCropImageBinding;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import static androidx.navigation.Navigation.findNavController;

public class CropFragment extends Fragment implements View.OnClickListener {

    private String imageUrl;
    private String shapeType;
    private FragmentCropImageBinding fragmentCropImageBinding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        if (bundle != null) {
            imageUrl = bundle.getString("IMAGE_URL");
        }
        if (bundle != null) {
            shapeType = bundle.getString("SHAPE_TYPE");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (fragmentCropImageBinding == null) {
            setHasOptionsMenu(true);
            fragmentCropImageBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_crop_image, container, false);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 1;
            Bitmap bitmap = BitmapFactory.decodeFile(imageUrl, options);
            Uri uri = Uri.parse(imageUrl);
            if ("CIRCLE".equals(shapeType)) {
                fragmentCropImageBinding.circleImageView.setImageBitmap(bitmap);
            } else if ("RECTANGLE".equals(shapeType)) {
                fragmentCropImageBinding.rectangleImageView.setImageBitmap(bitmap);
            } else if ("SQUARE".equals(shapeType)) {
                fragmentCropImageBinding.squareImageView.setImageBitmap(bitmap);
            }
            fragmentCropImageBinding.chooseBtn.setOnClickListener(this);
        }
        return fragmentCropImageBinding.getRoot();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == fragmentCropImageBinding.chooseBtn.getId()) {
//            Bundle bundle = new Bundle();
//            bundle.putString("IMAGE_URL", imageUrl);
//            bundle.putString("SHAPE_TYPE", "CIRCLE");
            findNavController(requireView()).navigate(R.id.assign_position_fragment);
        }
    }
}