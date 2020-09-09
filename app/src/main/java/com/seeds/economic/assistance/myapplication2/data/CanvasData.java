package com.seeds.economic.assistance.myapplication2.data;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.seeds.economic.assistance.myapplication2.AppAprilBrush;
import com.seeds.economic.assistance.myapplication2.CustomResponseDialog;
import com.seeds.economic.assistance.myapplication2.R;
import com.seeds.economic.assistance.myapplication2.logic.BrushEngine;
import com.seeds.economic.assistance.myapplication2.logic.UndoManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;




public class CanvasData {
    private static CanvasData canvasData = new CanvasData();
    private Bitmap bitmap;
    private Bitmap buffer;
    private Uri mSaveImageUri;
    private Paint bufferPaint;
    private UndoManager undoManager;
    private Context context;
    private Resources resources;
    private String imageFolderPath;
    private String imagePath;
    private int fillColor;
    int widths, heights;
    private boolean Flagshare=false;
    private MediaScannerConnection msConn=null;
    private CustomResponseDialog dialog;

    private CanvasData() {
        context = AppAprilBrush.getContext();
        dialog = new CustomResponseDialog(context);
        resources = context.getResources();
        undoManager = UndoManager.getInstance();
        bufferPaint = new Paint(Paint.DITHER_FLAG);

        imageFolderPath = Environment.getExternalStorageDirectory().toString() + "/SourceKode";
        File dir = new File(imageFolderPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    public static CanvasData getInstance() {
        return canvasData;
    }

    public float[] getFillColor() {
        float[] hsv = new float[3];
        Color.colorToHSV(fillColor, hsv);
        return hsv;
    }

    public void setFillColor(float[] hsv) {
        fillColor = Color.HSVToColor(hsv);
        BrushEngine.getInstance().setEraserColors(hsv);
    }

    public Paint getBufferPaint() {
        return bufferPaint;
    }

    public Bitmap getBuffer() {
        return buffer;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = Bitmap.createBitmap(bitmap);
    }

    public void setOpacity(int opacity) {
        int alpha = Math.round((float) opacity / 100 * 255);
        bufferPaint.setAlpha(alpha);
    }

    public void clear() {
        try {
            if (bitmap != null) {
                bitmap.eraseColor(fillColor);
                undoManager.add(bitmap);
            }
        }catch (Exception e){

        }

    }

    public void createBitmaps(int width, int height) {
        widths=width;
        heights=height;
        Log.e("ADAD--->", String.valueOf(widths));
        Log.e("hhh--->", String.valueOf(heights));
        bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        buffer = Bitmap.createBitmap(bitmap);
        newImage();
    }

    public void newImage() {
        Flagshare=false;
        bitmap.eraseColor(fillColor);
        buffer.eraseColor(Color.TRANSPARENT);
        undoManager.clear();
        undoManager.add(bitmap);
        imagePath = imageFolderPath + "/SourceKode_" + System.currentTimeMillis() + ".png";

        Toast.makeText(context, resources.getString(R.string.message_new_picture), Toast.LENGTH_SHORT).show();
    }

    public void loadImage(String path) {
        Flagshare=false;
        imagePath = path;
        bitmap = getResizedBitmap(BitmapFactory.decodeFile(path),2060);
        bitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);

        buffer.eraseColor(Color.TRANSPARENT);
        undoManager.clear();
        undoManager.add(bitmap);

        Toast.makeText(context, resources.getString(R.string.message_load_picture), Toast.LENGTH_SHORT).show();
    }

    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float)width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, 1080, 2060, true);
    }

    public void saveImage() {
        CustomResponseDialog dialog = new CustomResponseDialog(context);
        dialog.showCustomDialog();

        File file = new File(imagePath);
        file.delete(); // change the name of a picture to force update the gallery thumbnails
        imagePath = imageFolderPath + "/SourceKode_" + System.currentTimeMillis() + ".png";
        file = new File(imagePath);

        OutputStream fOut;
        try {
            fOut = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
            msConn = new MediaScannerConnection(context,
                    new MediaScannerConnection.MediaScannerConnectionClient() {
                        public void onMediaScannerConnected() {
                            msConn.scanFile(imagePath, null);
                        }

                        public void onScanCompleted(String path, Uri uri) {
                            msConn.disconnect();
                        }
                    });
            msConn.connect();
            fOut.flush();
            fOut.close();
        } catch (Exception e) {
            Log.d("Image Writer", "Problem with the image. Stacktrace: ", e);
        }

       // context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse("file://" + Environment.getExternalStorageDirectory())));
        Toast.makeText(context, resources.getString(R.string.message_save_picture), Toast.LENGTH_SHORT).show();
        dialog.hideCustomeDialog();
        Flagshare=true;

        mSaveImageUri = Uri.fromFile(new File(imagePath));
    }

    public String getPath(){
        return imagePath;
    }

    public boolean getFlag(){
        return Flagshare;
    }

    public Uri getSaveBitmap() {
        return mSaveImageUri;
    }

    public void applyBuffer() {
        try{
            Canvas canvas = new Canvas(bitmap);
            Paint paint = new Paint();
            int opacity = BrushEngine.getInstance().getValue(BrushData.Property.OPACITY);
            int alpha = Math.round(opacity / 100f * 255);
            paint.setAlpha(alpha);
            canvas.drawBitmap(buffer, 0, 0, paint);
            buffer.eraseColor(Color.TRANSPARENT);

            undoManager.add(bitmap);
        }catch (Exception e){

        }

    }
}
