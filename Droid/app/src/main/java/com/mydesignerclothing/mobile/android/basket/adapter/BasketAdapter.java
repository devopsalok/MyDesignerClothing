package com.mydesignerclothing.mobile.android.basket.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dynatrace.android.agent.util.Utility;
import com.mydesignerclothing.mobile.android.R;
import com.mydesignerclothing.mobile.android.basket.model.BasketListData;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BasketAdapter  extends RecyclerView.Adapter<BasketAdapter.ViewHolder> {
        Context mcontext;
      List<BasketListData> basketListDataList;
       // float totalprice;
        DecimalFormat decimalFormat = new DecimalFormat("##.00");
        Number parsed;


        public BasketAdapter(Context mcontext, List<BasketListData> basketListDataList) {
                this.mcontext = mcontext;
                this.basketListDataList = basketListDataList;

        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(mcontext).inflate(R.layout.item_mycart, parent, false);
                return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final BasketAdapter.ViewHolder holder, final int position) {
                BasketListData basketListData = basketListDataList.get(position);
                holder.mTxtItemtitle.setText(basketListData.getItemname());
                holder.mTxtQuantity.setText(basketListData.getQuantity());
            Glide.with(mcontext).load(basketListData.getImage()).into(holder.mImgItem);



               /* holder.mImgPlus.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                                int count = Integer.parseInt(holder.mTxtQuantity.getText().toString().trim());
                                if (count<Integer.parseInt(myCartModel.getTotalcount())){
                                        count++;
                                        myCartModel.setQuantity("" + count);
                                        holder.mTxtQuantity.setText("" + count);


                                                holder.mTxtAmount.setText((count * Integer.parseInt(myCartModel.getItempriceinlocal())) + " " + UserDetails.getInstance(mcontext).getCurrencyCode());
                                                myCartModel.setTotalprice((count * Integer.parseInt(myCartModel.getItempriceinlocal())) + "");

                                                totalprice += Integer.parseInt(myCartModel.getItempriceinlocal());


                                        MyCartFragment myCartFragment = new MyCartFragment();
                                        myCartFragment.setTotalPrice(totalprice, myCartModelArrayList);
                                        //  holder.mTxtDiscountprice.setText("BD " + (count * Integer.parseInt(myCartModel.getDiscountprice())));
                                }
                        }
                });

                holder.mImgMinus.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View view) {
                                int count = Integer.parseInt(holder.mTxtQuantity.getText().toString().trim());
                if (count<=Integer.parseInt(myCartModel.getTotalcount())){

                }
                                if (count > 1) {
                                        count--;
                                        myCartModel.setQuantity("" + count);
                                        holder.mTxtQuantity.setText("" + count);

                                                holder.mTxtAmount.setText((count * Integer.parseInt(myCartModel.getItempriceinlocal())) + " " + UserDetails.getInstance(mcontext).getCurrencyCode());
                                                myCartModel.setTotalprice("" + (count * Integer.parseInt(myCartModel.getItempriceinlocal())) + "");
                                                totalprice -= Integer.parseInt(myCartModel.getItempriceinlocal());


                                        MyCartFragment myCartFragment = new MyCartFragment();
                                        myCartFragment.setTotalPrice(totalprice, myCartModelArrayList);
                                }
                        }
                });*/


        }

        @Override
        public int getItemCount() {
                return 0;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
                TextView mTxtItemtitle, mTxtCountry, mTxtAmount, mTxtDealAmount, mTxtQuantity, mTxtPrice;
                ImageView mImgItem, mImgFlag, mImgPlus, mImgMinus, mImgDelete;

                public ViewHolder(View itemView) {
                        super(itemView);
                        mTxtItemtitle = (TextView) itemView.findViewById(R.id.txtTitle);

                        mTxtAmount = (TextView) itemView.findViewById(R.id.txtAmount);

                        mTxtQuantity = (TextView) itemView.findViewById(R.id.txtCount);
                        mImgItem = (ImageView) itemView.findViewById(R.id.imgItem);

                        mImgPlus = (ImageView) itemView.findViewById(R.id.imgPlus);
                        mImgMinus = (ImageView) itemView.findViewById(R.id.imgMinus);

                }
        }
}
