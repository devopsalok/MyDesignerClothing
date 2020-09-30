package com.mydesignerclothing.mobile.android.basket;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.mydesignerclothing.mobile.android.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import static androidx.navigation.Navigation.findNavController;

public class CheckoutActivity extends Fragment implements View.OnClickListener {
ImageView imgForward,imgBack;
    View view;
    /*@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_checkout);
        initComponents();

    }*/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.activity_checkout, container, false);
        initComponents();
        return view;
    }

    private void initComponents() {
        setReferences();
        setClickListeners();
    }

    private void setReferences() {
        imgBack=view.findViewById(R.id.imgBack);
        imgForward=view.findViewById(R.id.imgForward);
    }

    private void setClickListeners() {
        imgBack.setOnClickListener(this);
        imgForward.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imgBack:

                break;
            case R.id.imgForward:
                findNavController(requireView()).navigate(R.id.checkout_dest);
                break;
            default:
                break;
        }
    }
}
