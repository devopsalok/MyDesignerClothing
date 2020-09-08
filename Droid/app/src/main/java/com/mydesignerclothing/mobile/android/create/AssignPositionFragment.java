package com.mydesignerclothing.mobile.android.create;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mydesignerclothing.mobile.android.R;
import com.mydesignerclothing.mobile.android.databinding.FragmentAssignPositionBinding;
import com.mydesignerclothing.mobile.android.recycler.components.SpacesItemDecoration;
import com.mydesignerclothing.mobile.android.widget.AssignPositionImageAdapter;
import com.mydesignerclothing.mobile.android.widget.ImageAdapter;

import java.util.ArrayList;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

public class AssignPositionFragment extends Fragment {
    private FragmentAssignPositionBinding fragmentAssignPositionBinding;
    private ArrayList<Bitmap> frontBitmaps;
    private ArrayList<Bitmap> backBitmaps;
    private ArrayList<Bitmap> sleeveBitmaps;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TypedArray front = getResources().obtainTypedArray(R.array.front);
        TypedArray back = getResources().obtainTypedArray(R.array.back);
        TypedArray sleeve = getResources().obtainTypedArray(R.array.sleeve);

        frontBitmaps = new ArrayList<>();
        for (int i = 0; i < front.length(); i++) {
            frontBitmaps.add(decodeSampledBitmapFromResource(requireActivity().getResources(), front.getResourceId(i, -1), 350, 400));
        }

        backBitmaps = new ArrayList<>();
        for (int i = 0; i < back.length(); i++) {
            backBitmaps.add(decodeSampledBitmapFromResource(requireActivity().getResources(), back.getResourceId(i, -1), 350, 400));
        }

        sleeveBitmaps = new ArrayList<>();
        for (int i = 0; i < sleeve.length(); i++) {
            sleeveBitmaps.add(decodeSampledBitmapFromResource(requireActivity().getResources(), sleeve.getResourceId(i, -1), 350, 400));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (fragmentAssignPositionBinding == null) {
            fragmentAssignPositionBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_assign__position, container, false);
            renderAssignPosition();
        }
        return fragmentAssignPositionBinding.getRoot();
    }

    private void renderAssignPosition() {
        fragmentAssignPositionBinding.frontPositionRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        fragmentAssignPositionBinding.frontPositionRecyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 3));
        fragmentAssignPositionBinding.frontPositionRecyclerView.setItemAnimator(new DefaultItemAnimator());
        fragmentAssignPositionBinding.frontPositionRecyclerView.addItemDecoration(new SpacesItemDecoration(10));

        fragmentAssignPositionBinding.backPositionRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        fragmentAssignPositionBinding.backPositionRecyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 3));
        fragmentAssignPositionBinding.backPositionRecyclerView.setItemAnimator(new DefaultItemAnimator());
        fragmentAssignPositionBinding.backPositionRecyclerView.addItemDecoration(new SpacesItemDecoration(10));

        fragmentAssignPositionBinding.sleevesPositionRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        fragmentAssignPositionBinding.sleevesPositionRecyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 3));
        fragmentAssignPositionBinding.sleevesPositionRecyclerView.setItemAnimator(new DefaultItemAnimator());
        fragmentAssignPositionBinding.sleevesPositionRecyclerView.addItemDecoration(new SpacesItemDecoration(10));

        AssignPositionImageAdapter frontAdapter = new AssignPositionImageAdapter(requireActivity(), frontBitmaps);
//        adapter.setOnImageClickListener(this);
        fragmentAssignPositionBinding.frontPositionRecyclerView.setAdapter(frontAdapter);

        AssignPositionImageAdapter backAdapter = new AssignPositionImageAdapter(requireActivity(), backBitmaps);
//        adapter.setOnImageClickListener(this);
        fragmentAssignPositionBinding.backPositionRecyclerView.setAdapter(backAdapter);

        AssignPositionImageAdapter sleeveAdapter = new AssignPositionImageAdapter(requireActivity(), sleeveBitmaps);
//        adapter.setOnImageClickListener(this);
        fragmentAssignPositionBinding.sleevesPositionRecyclerView.setAdapter(sleeveAdapter);

    }


    public Bitmap decodeSampledBitmapFromResource(Resources res, int resId, int reqWidth, int reqHeight) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    public int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }
}