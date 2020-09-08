package com.mydesignerclothing.mobile.android.create;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.ContentUris;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mydesignerclothing.mobile.android.GalleryUtils;
import com.mydesignerclothing.mobile.android.PhotoEditorActivity;
import com.mydesignerclothing.mobile.android.R;
import com.mydesignerclothing.mobile.android.commons.util.StringUtils;
import com.mydesignerclothing.mobile.android.create.view.CreateInfoListener;
import com.mydesignerclothing.mobile.android.databinding.FragmentLoadImageBinding;

import org.apache.commons.io.comparator.LastModifiedFileComparator;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileFilter;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.PermissionChecker;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static androidx.navigation.Navigation.findNavController;
import static com.mydesignerclothing.mobile.android.create.CreateDialogFragment.SeatMapDialogType.ADVISORY_DIALOG;
import static com.mydesignerclothing.mobile.android.create.CreateDialogFragment.SeatMapDialogType.GALLERY_DIALOG;
import static com.mydesignerclothing.mobile.android.create.CreateDialogFragment.WHICH_DIALOG;

public class LoadImageFragment extends Fragment implements View.OnClickListener, CreateInfoListener {
    private String imageUrl;
    private FragmentLoadImageBinding fragmentLoadImageBinding;
    protected static final int CAMERA_CODE = 0x0;
    protected static final int GALLERY_INTENT_CALLED = 0x1;
    protected static final int GALLERY_KITKAT_INTENT_CALLED = 0x2;
    protected static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE_GALLERY = 0x3;
    protected static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE_CAMERA = 0x4;
    final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
    private CreateDialogFragment createDialogFragment;
    private static final String PHOTO_PATH = "PhotoEditor";
    protected String selectedOutputPath;
    private Uri selectedImageUri;
    protected String selectedImagePath;
    protected Bitmap bitmap;
    protected boolean _taken;
    private boolean isAddImageButtonClicked = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            imageUrl = bundle.getString("IMAGE_URL");
        }
        setHasOptionsMenu(true);
//        Toolbar toolbar = requireActivity().findViewById(R.id.toolbar_home);
//        toolbar.inflateMenu(R.menu.menu_home);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (fragmentLoadImageBinding == null) {
            setHasOptionsMenu(true);
            fragmentLoadImageBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_load_image, container, false);
            renderImagesFromGallery();
        }
        return fragmentLoadImageBinding.getRoot();
    }

    private void renderImagesFromGallery() {
        if (!StringUtils.isNullOrEmpty(imageUrl)) {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 1;
            Bitmap bitmap = BitmapFactory.decodeFile(imageUrl, options);
            fragmentLoadImageBinding.productImageView.setImageBitmap(bitmap);
            fragmentLoadImageBinding.next.setVisibility(View.VISIBLE);
            fragmentLoadImageBinding.editImageBtn.setVisibility(View.VISIBLE);
            fragmentLoadImageBinding.uploadLabel.setVisibility(View.GONE);
        }
        fragmentLoadImageBinding.addImageBtn.setOnClickListener(this);
        fragmentLoadImageBinding.editImageBtn.setOnClickListener(this);
        fragmentLoadImageBinding.next.setOnClickListener(this);
    }

    public void onAddImageButtonClicked() {
        isAddImageButtonClicked = true;
        createDialogFragment = new CreateDialogFragment();
        createDialogFragment.setLoadImageFragmentViewListener(this);
        Bundle bundle = new Bundle();
        bundle.putInt(WHICH_DIALOG, ADVISORY_DIALOG.ordinal());
        createDialogFragment.setArguments(bundle);
        createDialogFragment.show(requireActivity().getSupportFragmentManager(), CreateDialogFragment.DEFAULT_FRAGMENT_TAG);
    }

    public void onEditImageButtonClicked() {
        isAddImageButtonClicked = false;
        if (!StringUtils.isNullOrEmpty(imageUrl)) {
            onPhotoTaken();
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == fragmentLoadImageBinding.addImageBtn.getId()) {
            onAddImageButtonClicked();
        } else if (v.getId() == fragmentLoadImageBinding.editImageBtn.getId()) {
            onEditImageButtonClicked();
        } else if (v.getId() == fragmentLoadImageBinding.next.getId()) {
            Bundle bundle = new Bundle();
            bundle.putString("IMAGE_URL", imageUrl);
            findNavController(requireView()).navigate(R.id.select_crop, bundle);
        }
    }

    public void showMenu(final int caller) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setMessage(getString(R.string.access_media_permissions_msg));
        builder.setPositiveButton(getString(R.string.continue_txt), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (caller == 1) {
                    ActivityCompat.requestPermissions(requireActivity(),
                            new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE_CAMERA);
                } else {
                    ActivityCompat.requestPermissions(requireActivity(),
                            new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE_GALLERY);
                }
            }
        });
        builder.setNegativeButton(getString(R.string.not_now), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(requireContext(), getString(R.string.media_access_denied_msg), Toast.LENGTH_SHORT).show();
            }
        });
        builder.show();

    }

    @Override
    public void onProductImageClick(int index) {

    }

    @Override
    public void invokeProductDetailEvent(String productId) {

    }

    @Override
    public void onCancelButtonClicked() {
        if (createDialogFragment != null) {
            createDialogFragment.dismiss();
        }
    }

    @Override
    public void onTakePictureButtonClicked() {
        int permissionCheck = PermissionChecker.checkCallingOrSelfPermission(requireContext(),
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            Intent photoPickerIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            photoPickerIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                    getOutputMediaFile());
            photoPickerIntent.putExtra("outputFormat",
                    Bitmap.CompressFormat.PNG.toString());
            startActivityForResult(
                    Intent.createChooser(photoPickerIntent, getString(R.string.upload_picker_title)),
                    CAMERA_CODE);
        } else {
            showMenu(1);
        }
    }

    @Override
    public void onChooseGalleryButtonClicked() {
        int permissionCheck = PermissionChecker.checkCallingOrSelfPermission(requireContext(),
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {

            if (!isKitKat) {
                Intent intent = new Intent();
                intent.setType("image/jpeg");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(
                        Intent.createChooser(intent, getString(R.string.upload_picker_title)),
                        GALLERY_INTENT_CALLED);
            } else {
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/jpeg");
                startActivityForResult(intent, GALLERY_KITKAT_INTENT_CALLED);
            }
        } else {
            showMenu(2);
        }
        if (createDialogFragment != null) {
            createDialogFragment.dismiss();
        }
    }

    @Override
    public void onOkButtonClicked() {
        if (createDialogFragment != null) {
            createDialogFragment.dismiss();
        }
        createDialogFragment = new CreateDialogFragment();
        createDialogFragment.setLoadImageFragmentViewListener(this);
        Bundle bundle = new Bundle();
        bundle.putInt(WHICH_DIALOG, GALLERY_DIALOG.ordinal());
        createDialogFragment.setArguments(bundle);
        createDialogFragment.show(requireActivity().getSupportFragmentManager(), CreateDialogFragment.DEFAULT_FRAGMENT_TAG);
    }

    /**
     * Create a file for saving an image
     */
    protected Uri getOutputMediaFile() {

        if (isSDCARDMounted()) {
            File mediaStorageDir = new File(
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), PHOTO_PATH);
            // Create a storage directory if it does not exist
            if (!mediaStorageDir.exists()) {
                if (!mediaStorageDir.mkdirs()) {
                    Log.d("MediaAbstractActivity", getString(R.string.directory_create_fail));
                    return null;
                }
            }
            // Create a media file name
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            File mediaFile;
            selectedOutputPath = mediaStorageDir.getPath() + File.separator
                    + "IMG_" + timeStamp + ".png";
            Log.d("MediaAbstractActivity", "selected camera path "
                    + selectedOutputPath);
            mediaFile = new File(selectedOutputPath);
            return Uri.fromFile(mediaFile);
        } else {
            return null;
        }
    }

    protected boolean isSDCARDMounted() {
        String status = Environment.getExternalStorageState();
        return status.equals(Environment.MEDIA_MOUNTED);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE_GALLERY: {
                // If request is cancelled, the result arrays are empty.
                int permissionCheck = PermissionChecker.checkCallingOrSelfPermission(requireContext(),
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
                if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
                    if (!isKitKat) {
                        Intent intent = new Intent();
                        intent.setType("image/jpeg");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(
                                Intent.createChooser(intent, getString(R.string.upload_picker_title)),
                                GALLERY_INTENT_CALLED);
                    } else {
                        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                        intent.addCategory(Intent.CATEGORY_OPENABLE);
                        intent.setType("image/jpeg");
                        startActivityForResult(intent, GALLERY_KITKAT_INTENT_CALLED);
                    }
                } else {
                    Toast.makeText(requireContext(), getString(R.string.media_access_denied_msg), Toast.LENGTH_SHORT).show();
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            case MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE_CAMERA: {
                // If request is cancelled, the result arrays are empty.
                int permissionCheck = PermissionChecker.checkCallingOrSelfPermission(requireContext(),
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
                if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
                    Intent photoPickerIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    photoPickerIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                            getOutputMediaFile());
                    photoPickerIntent.putExtra("outputFormat",
                            Bitmap.CompressFormat.JPEG.toString());
                    // photoPickerIntent.putExtra("return-data", true);
                    startActivityForResult(
                            Intent.createChooser(photoPickerIntent, getString(R.string.upload_picker_title)),
                            CAMERA_CODE);
                } else {
                    Toast.makeText(requireContext(), getString(R.string.media_access_denied_msg), Toast.LENGTH_SHORT).show();
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        switch (resultCode) {
            case RESULT_CANCELED:
                break;
            case RESULT_OK:
                if (requestCode == GALLERY_INTENT_CALLED || requestCode == CAMERA_CODE
                        || requestCode == GALLERY_KITKAT_INTENT_CALLED) {
                    if (requestCode == GALLERY_INTENT_CALLED) {
                        selectedImageUri = data.getData();
                        selectedImagePath = getPath(selectedImageUri);
                    } else if (requestCode == GALLERY_KITKAT_INTENT_CALLED) {
                        selectedImageUri = data.getData();
                        final int takeFlags = data.getFlags()
                                & (Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                        // Check for the freshest data.
                        if (selectedImageUri != null) {
                            requireActivity().getContentResolver().takePersistableUriPermission(
                                    selectedImageUri, takeFlags);
                            selectedImagePath = getPath(selectedImageUri);
                        }
                    } else {
                        selectedImagePath = selectedOutputPath;
                    }

                    if (!StringUtils.isNullOrEmpty(selectedImagePath)) {
                        // decode image size
                        BitmapFactory.Options o = new BitmapFactory.Options();
                        o.inJustDecodeBounds = true;
                        BitmapFactory.decodeFile(selectedImagePath, o);
                        // Find the correct scale value. It should be the power of
                        // 2.
                        int width_tmp = o.outWidth, height_tmp = o.outHeight;
                        Log.d("MediaActivity", "MediaActivity : image size : "
                                + width_tmp + " ; " + height_tmp);
                        final int MAX_SIZE = getResources().getDimensionPixelSize(
                                R.dimen.image_loader_post_width);
                        int scale = 1;
                        // while (true) {
                        // if (width_tmp / 2 < MAX_SIZE
                        // || height_tmp / 2 < MAX_SIZE)
                        // break;
                        // width_tmp /= 2;
                        // height_tmp /= 2;
                        // scale *= 2;
                        // }
                        if (height_tmp > MAX_SIZE || width_tmp > MAX_SIZE) {
                            if (width_tmp > height_tmp) {
                                scale = Math.round((float) height_tmp
                                        / (float) MAX_SIZE);
                            } else {
                                scale = Math.round((float) width_tmp
                                        / (float) MAX_SIZE);
                            }
                        }
                        Log.d("MediaActivity", "MediaActivity : scaling image by factor : " + scale);
                        BitmapFactory.Options options = new BitmapFactory.Options();
                        options.inSampleSize = scale;
                        bitmap = BitmapFactory.decodeFile(selectedImagePath, options);
                        _taken = true;
                        onPhotoTaken();
                        System.gc();
                    }
                }
                break;
            case 10:
                if (data != null) {
                    if (!StringUtils.isNullOrEmpty(data.getStringExtra("imagePath"))) {
                        imageUrl = data.getStringExtra("imagePath");
                        BitmapFactory.Options options = new BitmapFactory.Options();
                        options.inSampleSize = 1;
                        Bitmap bitmap = BitmapFactory.decodeFile(imageUrl, options);
                        fragmentLoadImageBinding.productImageView.setImageBitmap(bitmap);
                    }
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    protected String getPath(final Uri uri) {
        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(requireContext(), uri)) {
            // ExternalStorageProvider
            if (GalleryUtils.isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/"
                            + split[1];
                }
            }
            // DownloadsProvider
            else if (GalleryUtils.isDownloadsDocument(uri)) {
                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"),
                        Long.valueOf(id));
                return GalleryUtils.getDataColumn(requireActivity(), contentUri, null, null);
            }
            // MediaProvider
            else if (GalleryUtils.isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};

                return GalleryUtils.getDataColumn(requireActivity(), contentUri, selection,
                        selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return GalleryUtils.getDataColumn(requireActivity(), uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    protected void onPhotoTaken() {
        if (isAddImageButtonClicked) {
            Bundle bundle = new Bundle();
            bundle.putString("IMAGE_URL", selectedImagePath);
            findNavController(requireView()).navigate(R.id.choose_image, bundle);
        } else {
            Intent intent = new Intent(requireActivity(), PhotoEditorActivity.class);
            intent.putExtra("selectedImagePath", imageUrl);
            startActivityForResult(intent, 10);
        }
    }

    @Override
    public void onDestroy() {
        if (bitmap != null) {
            bitmap.recycle();
            bitmap = null;
        }
        System.gc();
        super.onDestroy();
    }

    @Override
    public void onCreateOptionsMenu(@NotNull Menu menu, MenuInflater inflater) {
        // Do something that differs the Activity's menu here
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.menu_home, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.next) {// Do onlick on menu action here
            Bundle bundle = new Bundle();
            bundle.putString("selectedImagePath", selectedImagePath);
            findNavController(requireView()).navigate(R.id.choose_image, bundle);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public File getTheNewestFile(String filePath, String ext) {
        File theNewestFile = null;
        File dir = new File(filePath);
        FileFilter fileFilter = new WildcardFileFilter("*." + ext);
        File[] files = dir.listFiles(fileFilter);

        if (files.length > 0) {
            /** The newest file comes first **/
            Arrays.sort(files, LastModifiedFileComparator.LASTMODIFIED_REVERSE);
            theNewestFile = files[0];
        }

        return theNewestFile;
    }
}