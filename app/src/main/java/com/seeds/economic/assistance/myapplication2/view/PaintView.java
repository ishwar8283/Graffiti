package com.seeds.economic.assistance.myapplication2.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.seeds.economic.assistance.myapplication2.data.CanvasData;
import com.seeds.economic.assistance.myapplication2.logic.BrushEngine;

public class PaintView extends View {
    private BrushEngine brushEngine;
    private CanvasData canvasData;
    private Canvas canvasBuffer;
    private Paint bitmapPaint;

    public PaintView(Context context, AttributeSet attrs) {
        super(context, attrs);
        bitmapPaint = new Paint(Paint.DITHER_FLAG);
        if (!isInEditMode()) {
            canvasData = CanvasData.getInstance();
            brushEngine = BrushEngine.getInstance();
        }
    }

    @Override
    public void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        if (isInEditMode()) {
            return;
        }

        if (canvasData.getBitmap() == null) {
            canvasData.createBitmaps(w, h);
        }

        canvasBuffer = new Canvas(canvasData.getBuffer());
    }

    @Override
    public void onDraw(Canvas canvas) {
        if (isInEditMode()) {
            return;
        }
        canvas.drawColor(Color.WHITE);
        canvas.drawBitmap(canvasData.getBitmap(), 0, 0, bitmapPaint);
        canvas.drawBitmap(canvasData.getBuffer(), 0, 0, canvasData.getBufferPaint());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                brushEngine.setTouch(canvasBuffer, event);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                brushEngine.paintDabs(event);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                canvasData.applyBuffer();
                invalidate();
        }
        return true;
    }
}
