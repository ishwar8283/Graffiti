package com.seeds.economic.assistance.myapplication2.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.seeds.economic.assistance.myapplication2.R;


public class ColorPickerView extends View {
    private final static float RING_RATIO = 0.7f;
    private final static int HUE = 0;
    private final static int SATURATION = 1;
    private final static int VALUE = 2;
    private boolean hueGrab = false;
    private boolean satValGrab = false;
    private ImageView colorNewImageView;
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint satPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Bitmap ringBitmap;
    private Bitmap satBitmap;
    private Bitmap valBitmap;
    private Canvas satCanvas;
    private float outerRingRadius;
    private float innerRingRadius;
    private float rectSize;
    private float[] hsv = new float[3];
    private float xyRect;
    private float xyOrigin;

    public ColorPickerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint.setColor(Color.WHITE);
        int strokeWidth = 3;
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(strokeWidth);
    }

    public float[] getHsv() {
        return hsv;
    }

    public void setHsv(float[] hsv) {
        System.arraycopy(hsv, 0, this.hsv, 0, hsv.length);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        float containerWidth = Math.min(w, h);
        if (containerWidth > 0) {
            xyOrigin = containerWidth / 2;
            drawColorPicker(containerWidth);

            View colorPickerLayout = (View) getParent();
            colorNewImageView = (ImageView) colorPickerLayout.findViewById(R.id.colorNewImageView);
        }
    }

    private void drawColorPicker(float width) {
        drawColorRing(width);

        rectSize = (float) (width * RING_RATIO / Math.sqrt(2f));
        xyRect = (width - rectSize) / 2f;

        satBitmap = Bitmap.createBitmap((int) rectSize, (int) rectSize, Bitmap.Config.ARGB_8888);
        satCanvas = new Canvas(satBitmap);

        drawSatRectangle(rectSize);
        drawValRectangle(rectSize);

        outerRingRadius = width / 2;
        innerRingRadius = outerRingRadius * RING_RATIO;
    }

    private void drawColorRing(float width) {
        ringBitmap = Bitmap.createBitmap((int) width, (int) width, Bitmap.Config.ARGB_8888);
        ringBitmap.eraseColor(Color.TRANSPARENT);
        Canvas canvas = new Canvas(ringBitmap);

        float center = width / 2;
        Shader ringShader = new SweepGradient(center, center, gradientColors(), null);

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setShader(ringShader);

        canvas.drawCircle(center, center, center, paint);

        paint.reset();
        paint.setAntiAlias(true);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));

        canvas.drawCircle(center, center, Math.round(RING_RATIO * center), paint);
    }

    private void drawSatRectangle(float width) {
        int hueColor = Color.HSVToColor(255, new float[]{hsv[HUE], 1.0f, 1.0f});
        Shader saturationShader = new LinearGradient(0, 0, width, 0, Color.WHITE, hueColor, Shader.TileMode.CLAMP);
        satPaint.setShader(saturationShader);
        satCanvas.drawRect(0, 0, width, width, satPaint);
    }

    private void drawValRectangle(float width) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        Shader valueShader = new LinearGradient(0, 0, 0, 0 + width, 0x00000000, 0xFF000000, Shader.TileMode.CLAMP);
        paint.setShader(valueShader);

        valBitmap = Bitmap.createBitmap((int) width, (int) width, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(valBitmap);
        canvas.drawRect(0, 0, width, width, paint);
    }

    private void drawHueSelector(Canvas canvas) {
        float selectorWidth = rectSize * (1 - RING_RATIO);
        float selectorHeight = rectSize / 30;

        canvas.save();
        canvas.translate(xyOrigin, xyOrigin);
        canvas.rotate(360 - hsv[HUE]);
        canvas.drawRect(innerRingRadius, 0, selectorWidth + innerRingRadius, selectorHeight, paint);
        canvas.restore();
    }

    private void drawSatValSelector(Canvas canvas) {
        float circleSize = Math.round(rectSize / 20);

        canvas.save();
        canvas.translate(xyOrigin - rectSize / 2, xyOrigin + rectSize / 2);
        canvas.drawCircle(rectSize * hsv[SATURATION], -rectSize * hsv[VALUE], circleSize, paint);
        canvas.restore();
    }

    int[] gradientColors() {
        int segments = 7;
        int hueMax = 360;
        float hueStep = hueMax / (segments - 1);
        int[] colors = new int[segments];
        float[] hsv = {0, 100, 100};
        for (int i = 0; i < segments; i++) {
            colors[segments - i - 1] = Color.HSVToColor(hsv);
            hsv[HUE] += hueStep;
        }
        return colors;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(ringBitmap, 0, 0, paint);
        canvas.drawBitmap(satBitmap, xyRect, xyRect, paint);
        canvas.drawBitmap(valBitmap, xyRect, xyRect, paint);
        drawHueSelector(canvas);
        drawSatValSelector(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // origin moved to the center of color ring
        float x = event.getX() - xyOrigin;
        float y = xyOrigin - event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                processTouchDown(x, y);
                break;
            case MotionEvent.ACTION_MOVE:
                processTouchMove(x, y);
                break;
            case MotionEvent.ACTION_UP:
                processTouchUp();
        }
        return true;
    }

    private void processTouchDown(float x, float y) {
        double angle = Math.atan2(x, y);
        double length = Math.hypot(x, y);
        if (length >= innerRingRadius && length <= outerRingRadius) {
            changeHue(angle);
            hueGrab = true;
        }

        if (length < innerRingRadius) {
            changeSatVal(x, y);
            satValGrab = true;
        }
        invalidate();
    }

    private void processTouchMove(float x, float y) {
        double angle = Math.atan2(x, y);
        if (hueGrab) {
            changeHue(angle);
        }
        if (satValGrab) {
            changeSatVal(x, y);
        }
        invalidate();
    }

    private void changeHue(double angle) {
        int hue = (int) (angle * 180 / Math.PI - 90);
        if (hue < 0) {
            hue += 360;
        }
        hsv[HUE] = 360 - hue;
        drawSatRectangle(rectSize);
        colorNewImageView.setBackgroundColor(Color.HSVToColor(hsv));
    }

    private void changeSatVal(float x, float y) {
        float saturation = (x + rectSize / 2) / rectSize;
        hsv[SATURATION] = Math.max(0, Math.min(saturation, 1));

        float value = (y + rectSize / 2) / rectSize;
        hsv[VALUE] = Math.max(0, Math.min(value, 1));

        colorNewImageView.setBackgroundColor(Color.HSVToColor(hsv));
    }

    private void processTouchUp() {
        hueGrab = false;
        satValGrab = false;
    }
}
