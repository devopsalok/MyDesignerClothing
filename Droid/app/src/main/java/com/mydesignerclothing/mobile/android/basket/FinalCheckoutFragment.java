package com.mydesignerclothing.mobile.android.basket;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mydesignerclothing.mobile.android.R;

import androidx.fragment.app.Fragment;

public class FinalCheckoutFragment extends Fragment {
View view;
   /* @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_checkout_fragment);
    }*/
   @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup container,
                            Bundle savedInstanceState) {
       // Inflate the layout for this fragment
       view = inflater.inflate(R.layout.activity_final_checkout_fragment, container, false);
       // initComponents();
       return view;
   }
}
