package com.mydesignerclothing.mobile.android.basket;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.mydesignerclothing.mobile.android.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import static androidx.navigation.Navigation.findNavController;

public class ShippingMethodActivity extends Fragment implements View.OnClickListener{
View view;
Button btnCheckout;
    /*@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipping_method);
    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.activity_shipping_method, container, false);
       initComponents();
        return view;
    }

    private void initComponents() {
        setReferences();
        setClickListeners();
    }

    private void setClickListeners() {
       btnCheckout.setOnClickListener(this);
    }

    private void setReferences() {
        btnCheckout=view.findViewById(R.id.btnCheckout);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnCheckout:
                findNavController(view).navigate(R.id.checkout_dest_ship);
                break;
            default:
                break;
        }
    }
}
