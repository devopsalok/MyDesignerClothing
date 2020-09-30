package com.mydesignerclothing.mobile.android.basket;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Callback;
import retrofit2.Response;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.dynatrace.android.agent.util.Utility;
import com.google.gson.JsonElement;
import com.mydesignerclothing.mobile.android.R;
import com.mydesignerclothing.mobile.android.basket.adapter.BasketAdapter;
import com.mydesignerclothing.mobile.android.basket.model.BasketList;
import com.mydesignerclothing.mobile.android.basket.model.BasketListData;
import com.mydesignerclothing.mobile.android.basket.services.ApiInterface;
import com.mydesignerclothing.mobile.android.basket.view.BasketInfoView;
import com.mydesignerclothing.mobile.android.login.constants.Constant;
import com.mydesignerclothing.mobile.android.login.loginmain.SecondLoginActivity;
import com.mydesignerclothing.mobile.android.uikit.view.CustomProgress;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import static androidx.navigation.Navigation.findNavController;
import static com.mydesignerclothing.mobile.android.commons.util.StringUtils.EMPTY_STRING;

/**
 * A simple {@link Fragment} subclass.
 */
public class BasketFragment extends Fragment implements BasketInfoView ,View.OnClickListener{
    RecyclerView recyclerview;
    View view;
    TextView txtCheckout,txtContinueshopping;
    public List<BasketListData> basketListDataList;

    public BasketFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_basket, container, false);
        initComponents();
        return view;
    }

    private void initComponents() {
        setReferences();
        setClickListeners();
        callServiceForcart();
    }

    @Override
    public void showProgressDialog() {
        CustomProgress.showProgressDialog(requireActivity(), EMPTY_STRING, false);
    }

    @Override
    public void hideProgressDialog() {
        CustomProgress.removeProgressDialog();
    }

    private void callServiceForcart() {
        //com.mydesignerclothing.mobile.android.util.Utility.showLoadingDialog(getActivity(), "Loading", false);
        showProgressDialog();
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        apiInterface.getCart(Constant.USERID
        )
                .enqueue(new Callback<BasketList>() {
                    @Override
                    public void onResponse(retrofit2.Call<BasketList> call, Response<BasketList> response) {
                        hideProgressDialog();
                        Log.e("Response", "Response : " + response.body());
                        BasketList responseStatus = response.body();
                        if (responseStatus.getStatus().equals("success")) {
                            basketListDataList = responseStatus.getBasketListDataList();
                            setAdapter();
                        } else {
                            // Utility.setSnackBarEnglish(RegistrationActivity.this, edtName, "Invalid Credentials");
                            Toast.makeText(getContext(), "" + responseStatus.getStatus(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(retrofit2.Call<BasketList> call, Throwable t) {
                        hideProgressDialog();
                        //Utility.showLog("Response", "Error : " + t.getMessage());
                    }
                });

    }

    private void setAdapter() {
        BasketAdapter newLeadsAdapter=new BasketAdapter(getContext(),basketListDataList);
        recyclerview.setAdapter(newLeadsAdapter);
    }

    private void setClickListeners() {
txtContinueshopping.setOnClickListener(this);
txtCheckout.setOnClickListener(this);
    }

    private void setReferences() {
        recyclerview = view.findViewById(R.id.recyclerview);
        recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerview.setHasFixedSize(true);
        txtCheckout=view.findViewById(R.id.txtCheckout);
        txtContinueshopping=view.findViewById(R.id.txtContinueshopping);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.txtCheckout:
                /*Intent checkout=new Intent(getActivity(),CheckoutActivity.class);
                startActivity(checkout);*/
                TextView txtLogin, txtGuestLogin;
                final Dialog dialog = new Dialog(getActivity());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                View view = LayoutInflater.from(getActivity()).inflate(R.layout.login_dialog, null);
                txtLogin = (TextView) view.findViewById(R.id.txtLogin);
                txtGuestLogin = (TextView) view.findViewById(R.id.txtGuestLogin);

                //  dialog.getWindow().getAttributes().windowAnimations = R.style.AlertDialogCustom;
                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                Window window = dialog.getWindow();
                lp.copyFrom(window.getAttributes());
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

               // mTxtMessage.setText(message);
                txtLogin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                     Intent login=new Intent(getContext(), SecondLoginActivity.class);
                     startActivity(login);
                    }
                });

                txtGuestLogin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        findNavController(requireView()).navigate(R.id.basket_checkout);
                    }
                });

                dialog.setContentView(v);
                dialog.setCancelable(false);

                int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.90);
                int height = (int) (getResources().getDisplayMetrics().heightPixels * 0.30);
                dialog.getWindow().setLayout(width, lp.height);

                dialog.show();
             //   findNavController(requireView()).navigate(R.id.basket_checkout);

                break;
            case R.id.txtContinueshopping:
                findNavController(requireView()).navigate(R.id.cart_dest);

                break;
            default:
        }
    }
}
