package com.seeds.economic.assistance.myapplication2.activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.FragmentActivity;

import com.seeds.economic.assistance.myapplication2.R;
import com.seeds.economic.assistance.myapplication2.data.BrushData;
import com.seeds.economic.assistance.myapplication2.data.CanvasData;
import com.seeds.economic.assistance.myapplication2.dialog.ColorDialog;
import com.seeds.economic.assistance.myapplication2.logic.BrushEngine;
import com.seeds.economic.assistance.myapplication2.logic.UndoManager;
import com.seeds.economic.assistance.myapplication2.view.PaintView;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;




public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener {
    public static final String PREFS_NAME = "prefs";
    public static final String FILE_PROVIDER_AUTHORITY = "com.seeds.economic.assistance.myapplication2.provider";
    private static final int SELECT_PICTURE = 1;
    private ColorDialog colorDialog;
    private UndoManager undoManager;
    private PaintView paintView;
    private Context context;
    private Context mContext;
    private Activity activity;
    private  Uri mSaveImageUri=null;
    String[] permissions = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA,
    };
    public AlertDialog alertMantra;
    private ArrayList<String> imageDataPic = new ArrayList<>();
    private Bitmap bitmapUserProfile =null,bitmapDoccument =null,bitmaps=null;
    public static final int REQUEST_IMAGE = 100;
    private int FlagImage=1;
    private boolean flagCapture=false;
    String selectedImagePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        mContext = this;
        activity = this;
        context = getApplicationContext();
        loadPreferences();
        setupButtons();
        checkPermissions();

        undoManager = UndoManager.getInstance();
        paintView = (PaintView) findViewById(R.id.paintView);
        colorDialog = new ColorDialog();
    }

    private void loadPreferences() {
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        BrushEngine brushEngine = BrushEngine.getInstance();
        BrushData brushData = BrushData.getInstance();
        for (int i = 0; i < brushEngine.getBrushList().length; i++) {
            int value = settings.getInt(BrushData.Property.values()[i].toString(), brushData.getProperty(i));
            brushEngine.setValue(i, value);
        }
        brushEngine.setupColor();

        boolean setBrushMode = settings.getBoolean("brushMode", true);
        BrushData.getInstance().setBrushMode(setBrushMode);
        int fillColor = settings.getInt("fillColor", Color.WHITE);
        float[] hsv = new float[3];
        Color.colorToHSV(fillColor, hsv);
        CanvasData.getInstance().setFillColor(hsv);
    }

    private void setupButtons() {
        // top tool bar
        ImageButton newButton = (ImageButton) findViewById(R.id.newImageButton);
        newButton.setOnClickListener(this);

        ImageButton loadButton = (ImageButton) findViewById(R.id.loadImageButton);
        loadButton.setOnClickListener(this);

        ImageButton saveButton = (ImageButton) findViewById(R.id.saveImageButton);
        saveButton.setOnClickListener(this);

        ImageButton shareButton = (ImageButton) findViewById(R.id.shareImageButton);
        shareButton.setOnClickListener(this);

        ImageButton helpButton = (ImageButton) findViewById(R.id.helpImageButton);
        helpButton.setOnClickListener(this);

        // bottom tool bar
        ImageButton undoButton = (ImageButton) findViewById(R.id.undoImageButton);
        undoButton.setOnClickListener(this);

        ImageButton brushButton = (ImageButton) findViewById(R.id.brushImageButton);
        brushButton.setOnClickListener(this);
        brushButton.setOnLongClickListener(this);

        ImageButton colorButton = (ImageButton) findViewById(R.id.colorImageButton);
        colorButton.setOnClickListener(this);

        ImageButton fillButton = (ImageButton) findViewById(R.id.fillImageButton);
        fillButton.setOnClickListener(this);
        fillButton.setOnLongClickListener(this);

        ImageButton redoButton = (ImageButton) findViewById(R.id.redoImageButton);
        redoButton.setOnClickListener(this);

        if (BrushData.getInstance().isBrushMode()) {
            brushButton.setImageResource(R.drawable.paintbrush);
        } else {
            brushButton.setImageResource(R.drawable.draw_eraser);
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.newImageButton:
                CanvasData.getInstance().newImage();
                paintView.invalidate();
                break;
            case R.id.loadImageButton:
                alertBoxSelectPhoto();
                break;
            case R.id.saveImageButton://
                CanvasData.getInstance().saveImage();
                break;

            case R.id.shareImageButton:
                shareImage();
                break;

            case R.id.helpImageButton:
                intent = new Intent(this, HelpActivity.class);
                startActivity(intent);
                break;
            case R.id.undoImageButton:
                undoManager.undo();
                paintView.invalidate();
                break;
            case R.id.brushImageButton:
                intent = new Intent(this, BrushSettingsActivity.class);
                startActivity(intent);
                //finish();
                break;
            case R.id.colorImageButton:
                colorDialog.show(getSupportFragmentManager(), "brush");
                break;
            case R.id.fillImageButton:
                CanvasData.getInstance().clear();
                paintView.invalidate();
                break;
            case R.id.redoImageButton:
                undoManager.redo();
                paintView.invalidate();
                break;
        }
    }


    private void shareImage() {

        if (CanvasData.getInstance().getFlag()) {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("image/*");
            intent.putExtra(Intent.EXTRA_STREAM, buildFileProviderUri(CanvasData.getInstance().getSaveBitmap()));
            startActivity(Intent.createChooser(intent, "Share Image"));

        }else {
            Toast.makeText(context, "Please save image to share", Toast.LENGTH_SHORT).show();
        }



    }

    private Uri buildFileProviderUri(@NonNull Uri uri) {
        return FileProvider.getUriForFile(this, FILE_PROVIDER_AUTHORITY, new File(uri.getPath()));
    }
    @Override
    public boolean onLongClick(View v) {
        switch (v.getId()) {
            case R.id.brushImageButton:
                BrushData brushData = BrushData.getInstance();
                brushData.toggleBrushMode();
                ImageButton brushButton = (ImageButton) findViewById(R.id.brushImageButton);
                if (brushData.isBrushMode()) {
                    brushButton.setImageResource(R.drawable.paintbrush);
                } else {
                    brushButton.setImageResource(R.drawable.draw_eraser);
                }
                break;
            case R.id.fillImageButton:
                colorDialog.show(getSupportFragmentManager(), "fill");
                break;
        }
        return true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        savePreferences();
    }

    private void savePreferences() {
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean("brushMode", BrushData.getInstance().isBrushMode());
        int color = Color.HSVToColor(CanvasData.getInstance().getFillColor());
        editor.putInt("fillColor", color);

        BrushEngine brushEngine = BrushEngine.getInstance();
        for (int i = 0; i < brushEngine.getBrushList().length; i++) {
            int value = brushEngine.getValue(i);
            editor.putInt(BrushData.Property.values()[i].toString(), value);
        }

        editor.commit();
    }





    public void alertBoxSelectPhoto() {

        final Dialog dialogMsg = new Dialog(mContext);
        dialogMsg.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogMsg.setContentView(R.layout.alert_select_image);
        dialogMsg.setCancelable(true);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialogMsg.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.gravity = Gravity.CENTER;
        dialogMsg.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialogMsg.getWindow().setAttributes(lp);
        dialogMsg.show();

        final LinearLayout photo = (LinearLayout) dialogMsg.findViewById(R.id.alert_take_photo);
        final LinearLayout gallery = (LinearLayout) dialogMsg.findViewById(R.id.take_from_gallery);

        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flagCapture=true;
                launchCameraIntent();
                dialogMsg.cancel();
            }
        });
        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flagCapture=false;
                launchGalleryIntent();
                dialogMsg.cancel();
            }
        });


    }


    public void launchCameraIntent() {
        Intent intent = new Intent(MainActivity.this, ImagePickerActivity.class);
        intent.putExtra(ImagePickerActivity.INTENT_IMAGE_PICKER_OPTION, ImagePickerActivity.REQUEST_IMAGE_CAPTURE);

        intent.putExtra(ImagePickerActivity.INTENT_LOCK_ASPECT_RATIO, false);
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_X, 1); // 16x9, 1x1, 3:4, 3:2
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_Y, 1);

        intent.putExtra(ImagePickerActivity.INTENT_SET_BITMAP_MAX_WIDTH_HEIGHT, false);
        intent.putExtra(ImagePickerActivity.INTENT_BITMAP_MAX_WIDTH, 1000);
        intent.putExtra(ImagePickerActivity.INTENT_BITMAP_MAX_HEIGHT, 1000);


        startActivityForResult(intent, REQUEST_IMAGE);
    }

    private void launchGalleryIntent() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                Uri uri = null;
                if(flagCapture){
                    uri = data.getParcelableExtra("path");
                }else{
                    uri = data.getData();
                }

                try {
                    /*Bitmap bitmap = MediaStore.Images.Media.getBitmap(MainActivity.this.getContentResolver(), uri);
                    bitmaps = bitmap;
                    bitmapUserProfile = bitmap;*/
                    mSaveImageUri=uri;
                    Log.e("Actual Path URI--->", String.valueOf(mSaveImageUri));
                }catch (Exception e){

                }

                try {

                    selectedImagePath = getPathFromURI(mContext,uri);
                    Log.e("Actual Path--->",selectedImagePath);
                    CanvasData.getInstance().loadImage(selectedImagePath);
                    paintView.invalidate();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }



    public static String getPathFromURI(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
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
                final String[] selectionArgs = new String[] {
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }


    private boolean checkPermissions() {
        int result;
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String p : permissions) {
            result = ContextCompat.checkSelfPermission(this, p);
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), 100);
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (requestCode == 100) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            }
            return;
        }
    }
}
