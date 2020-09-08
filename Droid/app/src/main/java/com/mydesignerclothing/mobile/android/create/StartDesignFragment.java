package com.mydesignerclothing.mobile.android.create;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mydesignerclothing.mobile.android.R;
import com.mydesignerclothing.mobile.android.databinding.FragmentStartDesignBinding;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import static androidx.navigation.Navigation.findNavController;

public class StartDesignFragment extends Fragment implements View.OnClickListener {
    private String imageUrl;
    private FragmentStartDesignBinding fragmentStartDesignBinding;

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
        if (fragmentStartDesignBinding == null) {
            fragmentStartDesignBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_start_design, container, false);
            renderStartDesign();
        }
        return fragmentStartDesignBinding.getRoot();
    }

    private void renderStartDesign() {
        fragmentStartDesignBinding.productImageView.setUrl(imageUrl);
        fragmentStartDesignBinding.printBtn.setOnClickListener(this);
        fragmentStartDesignBinding.embroideryBtn.setOnClickListener(this);
    }

    public void onPrintButtonClicked() {
        findNavController(requireView()).navigate(R.id.load_image);
    }

    public void onEmbroideryClicked() {

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == fragmentStartDesignBinding.printBtn.getId()) {
            onPrintButtonClicked();
        } else if (v.getId() == fragmentStartDesignBinding.embroideryBtn.getId()) {
            onEmbroideryClicked();
        }
    }
}