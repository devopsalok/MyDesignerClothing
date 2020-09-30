package com.mydesignerclothing.mobile.android.shop;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.JsonElement;
import com.mydesignerclothing.mobile.android.R;
import com.mydesignerclothing.mobile.android.basket.ApiClient;
import com.mydesignerclothing.mobile.android.basket.services.ApiInterface;
import com.mydesignerclothing.mobile.android.create.CreateDialogFragment;
import com.mydesignerclothing.mobile.android.create.presenter.CreateInfoPresenter;
import com.mydesignerclothing.mobile.android.create.services.CreateInfoService;
import com.mydesignerclothing.mobile.android.create.services.apiclient.CreateInfoApiClient;
import com.mydesignerclothing.mobile.android.create.services.model.ProductColorsList;
import com.mydesignerclothing.mobile.android.create.services.model.ProductDetailImages;
import com.mydesignerclothing.mobile.android.create.services.model.ProductDetailModel;
import com.mydesignerclothing.mobile.android.create.services.model.ProductOptionsList;
import com.mydesignerclothing.mobile.android.create.services.model.ProductsResponseModel;
import com.mydesignerclothing.mobile.android.create.view.CreateInfoListener;
import com.mydesignerclothing.mobile.android.create.view.CreateInfoView;
import com.mydesignerclothing.mobile.android.databinding.FragmentProductDetailBinding;

import com.mydesignerclothing.mobile.android.login.constants.Constant;
import com.mydesignerclothing.mobile.android.network.apiclient.APIClientFactory;
import com.mydesignerclothing.mobile.android.network.models.NetworkError;
import com.mydesignerclothing.mobile.android.registration.RegistrationActivity;
import com.mydesignerclothing.mobile.android.uikit.view.CustomProgress;
import com.mydesignerclothing.mobile.android.util.Utility;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Callback;
import retrofit2.Response;

import static androidx.navigation.Navigation.findNavController;
import static com.mydesignerclothing.mobile.android.commons.util.StringUtils.EMPTY_STRING;
import static com.mydesignerclothing.mobile.android.create.CreateDialogFragment.EXTRAS_COLORS_MODEL;
import static com.mydesignerclothing.mobile.android.create.CreateDialogFragment.EXTRAS_SIZE_MODEL;
import static com.mydesignerclothing.mobile.android.create.CreateDialogFragment.SeatMapDialogType.COLORS_DIALOG;
import static com.mydesignerclothing.mobile.android.create.CreateDialogFragment.SeatMapDialogType.SIZE_DIALOG;
import static com.mydesignerclothing.mobile.android.create.CreateDialogFragment.WHICH_DIALOG;
import static com.mydesignerclothing.mobile.android.network.apiclient.RequestType.UNKNOWN;

public class ProductDetailFragment extends Fragment implements CreateInfoView, CreateInfoListener {
    private CreateInfoPresenter createInfoPresenter;
    private String productId, fromval;
    private FragmentProductDetailBinding fragmentProductDetailBinding;
    private CreateDialogFragment createDialogFragment;
    private ProductDetailModel productDetailModel;
    private List<ProductDetailImages> productDetailImagesList = new ArrayList<>();
    private ProductColorsList productColorsList;
    private int selectedItemIndex;
    RequestBody fbody;
    Bitmap bitmap;
    ImageView imageview;


 /*   public  static  ProductDetailFragment getInstance(boolean isCreateShop){
        ProductDetailFragment myFragment = new ProductDetailFragment();

        Bundle args = new Bundle();
        args.putBoolean("isCreateShop", isCreateShop);
        myFragment.setArguments(args);

        return myFragment;

    }*/

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            productId = bundle.getString("PRODUCT_ID");
            fromval = bundle.getString("from");

        }
        CreateInfoService createInfoService = new CreateInfoService(APIClientFactory.get(requireActivity(), UNKNOWN).get(CreateInfoApiClient.class));
        createInfoPresenter = new CreateInfoPresenter(createInfoService, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (fragmentProductDetailBinding == null) {
            fragmentProductDetailBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_product_detail, container, false);
            fragmentProductDetailBinding.setCreateFragmentListener(this);
            renderProductDetailByProductId();
          /*  Bundle bundle = getArguments();

            if (bundle != null ) {
                boolean isCreateShop =bundle.getBoolean("isCreateShop");
                 if (isCreateShop)
                    fragmentProductDetailBinding.addToCart.setVisibility(View.VISIBLE);
            }*/
            if (fromval.equals("shop")) {
                fragmentProductDetailBinding.addToCart.setVisibility(View.VISIBLE);
                fragmentProductDetailBinding.startDesignBtn.setVisibility(View.GONE);
            } else {
                fragmentProductDetailBinding.addToCart.setVisibility(View.GONE);
                fragmentProductDetailBinding.startDesignBtn.setVisibility(View.VISIBLE);
            }
            imageview = fragmentProductDetailBinding.imageview;

        }

     /*   int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
            //your codes here

        }*/
        return fragmentProductDetailBinding.getRoot();
    }

    @Override
    public void showProgressDialog() {
        CustomProgress.showProgressDialog(requireActivity(), EMPTY_STRING, false);
    }

    @Override
    public void hideProgressDialog() {
        CustomProgress.removeProgressDialog();
    }

    @Override
    public void showErrorDialog(@NonNull NetworkError error) {
        Toast.makeText(requireActivity(), error.getErrorMessage(getResources()), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProductsSuccessResponse(ProductsResponseModel productsResponseModel) {
        //NOSonar
    }

    @Override
    public void onProductDetailSuccessResponse(ProductDetailModel productDetailModel) {
        this.productDetailModel = productDetailModel;
        fragmentProductDetailBinding.descriptionText.setText(Html.fromHtml(productDetailModel.getProductDetail().getProductLongDesc()));
        fragmentProductDetailBinding.productDetailPromoViewFlipper.detailViewFlipperContainer.setupUI(productDetailModel.getProductDetailImagesList(), this);


    }

    @Override
    public void onColorDialogConfirmButtonClicked() {
        if (createDialogFragment != null) {
            createDialogFragment.dismiss();
        }

        fragmentProductDetailBinding.colorBtn.setText(String.format("Color - %s", productColorsList.getOptionName()));
        fragmentProductDetailBinding.productDetailPromoViewFlipper.detailViewFlipperContainer.setupUI(productDetailImagesList, this);


    }

    @Override
    public void onColorCancelButtonClicked() {
        if (createDialogFragment != null) {
            createDialogFragment.dismiss();
        }
    }

    @Override
    public void onSizeDialogConfirmButtonClicked() {
        if (createDialogFragment != null) {
            createDialogFragment.dismiss();
        }
        //Log.e("OPTION NAME", "" + productColorsList.getOptionName());

        fragmentProductDetailBinding.sizeBtn.setText(String.format("Size - %s", productColorsList.getOptionName()));


    }

    @Override
    public void onSizeCancelButtonClicked() {
        if (createDialogFragment != null) {
            createDialogFragment.dismiss();
        }
    }

    @Override
    public void onSelectColorButtonClicked() {
        createDialogFragment = new CreateDialogFragment();
        ProductOptionsList productOptionsList = productDetailModel.getProductOptionsList().get(1);
        createDialogFragment.setProductDetailFragmentViewListener(this);
        Bundle bundle = new Bundle();
        bundle.putInt(WHICH_DIALOG, COLORS_DIALOG.ordinal());
        bundle.putParcelable(EXTRAS_COLORS_MODEL, productOptionsList);
        createDialogFragment.setArguments(bundle);
        createDialogFragment.show(requireActivity().getSupportFragmentManager(), CreateDialogFragment.DEFAULT_FRAGMENT_TAG);
    }

    @Override
    public void onSelectSizeButtonClicked() {

        createDialogFragment = new CreateDialogFragment();
        ProductOptionsList productOptionsList = productDetailModel.getProductOptionsList().get(0);
        createDialogFragment.setProductDetailFragmentViewListener(this);
        Bundle bundle = new Bundle();
        bundle.putInt(WHICH_DIALOG, SIZE_DIALOG.ordinal());
        bundle.putParcelable(EXTRAS_SIZE_MODEL, productOptionsList);
        createDialogFragment.setArguments(bundle);
        createDialogFragment.show(requireActivity().getSupportFragmentManager(), CreateDialogFragment.DEFAULT_FRAGMENT_TAG);
    }

    @Override
    public void invokeProductDetailEventForColorSelected(ProductColorsList productColorsList) {
        this.productColorsList = productColorsList;
        productDetailImagesList.clear();
        for (int i = 0; i < productDetailModel.getProductOptionsList().get(1).getProductOptionsGroupList().size(); i++) {
            if (productDetailModel.getProductOptionsList().get(1).getProductOptionsGroupList().get(i).getOptionId().equals(productColorsList.getOptionId())) {
                ProductDetailImages productDetailImages = new ProductDetailImages();
                productDetailImages.setImageUrl(productDetailModel.getProductOptionsList().get(1).getProductOptionsGroupList().get(i).getImage());
                productDetailImagesList.add(productDetailImages);
            }
        }
    }

    @Override
    public void invokeProductDetailEventForSizeSelected(ProductColorsList productColorsList) {
        this.productColorsList = productColorsList;
        productDetailImagesList.clear();
        for (int i = 0; i < productDetailModel.getProductOptionsList().get(0).getProductOptionsGroupList().size(); i++) {
            if (productDetailModel.getProductOptionsList().get(0).getProductOptionsGroupList().get(i).getOptionId().equals(productColorsList.getOptionId())) {
                ProductDetailImages productDetailImages = new ProductDetailImages();
                productDetailImages.setImageUrl(productDetailModel.getProductOptionsList().get(0).getProductOptionsGroupList().get(i).getImage());
                productDetailImagesList.add(productDetailImages);
            }
        }
    }

    @Override
    public void onStartDesignButtonClicked() {
        List<ProductDetailImages> productDetailImagesList = productDetailModel.getProductDetailImagesList();
        Bundle bundle = new Bundle();
        bundle.putString("IMAGE_URL", productDetailImagesList.get(selectedItemIndex).getImageUrl());
        findNavController(requireView()).navigate(R.id.action_start_design, bundle);
    }

    @Override
    public void addToCart() {
        showProgressDialog();
        Log.e("ShowImage", "" + productDetailImagesList.get(selectedItemIndex).getImageUrl());
        new GetImageFromUrl().execute(productDetailImagesList.get(selectedItemIndex).getImageUrl());
       /* Bundle bundle = new Bundle();
        bundle.putString("IMAGE_URL", productDetailImagesList.get(selectedItemIndex).getImageUrl());
        bundle.putString("PRODUCTID", productDetailModel.getProductId());
       bundle.putString("USERID", Constant.USERID);
        findNavController(requireView()).navigate(R.id.start_designcart, bundle);*/
        /*try {
            URL url = new URL(productDetailImagesList.get(selectedItemIndex).getImageUrl());
            bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());

         fbody = RequestBody.create(MediaType.parse("image/*"),
                    getFile(bitmap));

        } catch(IOException e) {
            System.out.println(e);
        }*/
        //callServiceForAddtocart();
    }

    private File getFile(Bitmap bitmap1) {
       // File f = new File(getActivity().getCacheDir(), "sample");
       // File f = new File(Environment.getExternalStorageDirectory() + File.separator + "temporary_file.png");
        File  f =new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString() + File.separator + System.currentTimeMillis()+".jpeg");
        try {
            f.createNewFile();

//Convert bitmap to byte array

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap1.compress(Bitmap.CompressFormat.JPEG, 100 , bos);
            byte[] bitmapdata = bos.toByteArray();

//write the bytes in file
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(f);
                fos.write(bitmapdata);
                fos.flush();
                fos.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return f;
    }


    private void callServiceForAddtocart() {
        //  com.mydesignerclothing.mobile.android.util.Utility.showLoadingDialog(getActivity(), "Loading", false);

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        //   File file = new File(productDetailImagesList.get(selectedItemIndex).getImageUrl());
        if (bitmap != null) {
            File file = getFile(bitmap);

          /*  MultipartBody.Part body = MultipartBody.Part.createFormData(
                    "file",
                    file.getName(),
                    RequestBody.create(MediaType.parse("multipart/form-data"), file));



            RequestBody productid = RequestBody.create(okhttp3.MultipartBody.FORM, productId);
            RequestBody userid = RequestBody.create(okhttp3.MultipartBody.FORM,Constant.USERID);
            RequestBody quantity=RequestBody.create(okhttp3.MultipartBody.FORM,"1");*/



            //pass it like this

            RequestBody requestFile =
                    RequestBody.create(MediaType.parse("multipart/form-data"), file);

// MultipartBody.Part is used to send also the actual file name
            MultipartBody.Part body =
                    MultipartBody.Part.createFormData("file", file.getName(), requestFile);
          //  Log.e("filebody",""+body.toString());

// add another part within the multipart request
            RequestBody productid =
                    RequestBody.create(MediaType.parse("multipart/form-data"), productId);
            RequestBody userid =
                    RequestBody.create(MediaType.parse("multipart/form-data"), Constant.USERID);

            RequestBody quantity =
                    RequestBody.create(MediaType.parse("multipart/text/plain"), "1");
            apiInterface.getAddtocart(productid,userid,quantity,
                    body
            )
                    .enqueue(new Callback<JsonElement>()
                    {
                        @Override
                        public void onResponse(retrofit2.Call<JsonElement> call, Response<JsonElement> response) {
                            // Utility.hideLoadingDialog();
                            hideProgressDialog();
                            // Utility.showLog("Response", "Response : " + response.body());
                          Log.e("RESPONSE","Response data: " + response.body().toString());
                            if (response.isSuccessful()) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response.body().toString());
                                    if (jsonObject.optString("status").equals("success")) {
                                        Bundle bundle = new Bundle();
                                        bundle.putString("IMAGE_URL", productDetailImagesList.get(selectedItemIndex).getImageUrl());
                                        bundle.putString("PRODUCTID", productDetailModel.getProductId());
                                        bundle.putString("USERID", Constant.USERID);
                                        findNavController(requireView()).navigate(R.id.start_designcart, bundle);


                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                hideProgressDialog();
                                // Utility.setSnackBarEnglish(RegistrationActivity.this, edtName, "Invalid Credentials");
                                Toast.makeText(getContext(), "" + response.message(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(retrofit2.Call<JsonElement> call, Throwable t) {
                            hideProgressDialog();
                            // Utility.showLog("Response", "Error : " + t.getMessage());
                            Log.e("error", "" + t.getMessage());
                         //   Toast.makeText(getContext(), "something went wrong", Toast.LENGTH_SHORT).show();
                            findNavController(requireView()).navigate(R.id.start_designcart);
                        }
                    });
        } else {
            Toast.makeText(getActivity(), "No Bitmap..", Toast.LENGTH_SHORT).show();
        }


    }

    private void renderProductDetailByProductId() {
        createInfoPresenter.getProductDetail(productId);
    }

    @Override
    public void onProductImageClick(int index) {
        selectedItemIndex = index;
    }

    @Override
    public void invokeProductDetailEvent(String productId) {

    }

    @Override
    public void onCancelButtonClicked() {

    }

    @Override
    public void onTakePictureButtonClicked() {

    }

    @Override
    public void onChooseGalleryButtonClicked() {

    }

    @Override
    public void onOkButtonClicked() {

    }

    public class GetImageFromUrl extends AsyncTask<String, Void, Bitmap> {


        @Override
        protected Bitmap doInBackground(String... url) {
            String stringUrl = url[0];
            Bitmap bitmapNew = null;
            InputStream inputStream;
            try {
                inputStream = new java.net.URL(stringUrl).openStream();
                bitmapNew = BitmapFactory.decodeStream(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bitmapNew;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap1) {
            super.onPostExecute(bitmap1);
            bitmap = bitmap1;
            imageview.setImageBitmap(bitmap);

            callServiceForAddtocart();
            //imageView.setImageBitmap(bitmap);
        }
    }
}
