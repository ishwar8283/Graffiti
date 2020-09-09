package com.seeds.economic.assistance.myapplication2.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.seeds.economic.assistance.myapplication2.R;
import com.seeds.economic.assistance.myapplication2.SessionManager;
import com.seeds.economic.assistance.myapplication2.data.BrushData;
import com.seeds.economic.assistance.myapplication2.logic.BrushEngine;
import com.seeds.economic.assistance.myapplication2.view.SliderView;

import java.util.List;
import java.util.Observable;



public class BrushSettingsActivity extends Activity {
    LinearLayout ll_pencil,ll_dot_line,ll_join_dot_line,ll_brush,ll_medium_brush,ll_spry,ll_big_brush,ll_roll_brush,ll_dot_circle;
    LinearLayout layout;
    BrushEngine brushEngine;
    BrushData brushData;
    Context context;
    SliderView sliderView;
    SliderView sliderViewInstant;
    TextView txtOpacity;
    SeekBar sbSize;
    private SessionManager sessionManager;
    private SliderObservable sliderObservable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_bottom_properties_dialog);
        context=this;
        Context context = getBaseContext();
         brushEngine = BrushEngine.getInstance();
         brushData = BrushData.getInstance();
         sliderObservable = new SliderObservable();
        sessionManager = new SessionManager(context);

        ll_pencil = (LinearLayout)findViewById(R.id.ll_pencil);
        ll_dot_line = (LinearLayout)findViewById(R.id.ll_dot_line);
        ll_join_dot_line = (LinearLayout)findViewById(R.id.ll_join_dot_line);
        ll_brush = (LinearLayout)findViewById(R.id.ll_brush);
        ll_medium_brush = (LinearLayout)findViewById(R.id.ll_medium_brush);
        ll_spry = (LinearLayout)findViewById(R.id.ll_spry);
        ll_big_brush = (LinearLayout)findViewById(R.id.ll_big_brush);
        ll_roll_brush = (LinearLayout)findViewById(R.id.ll_roll_brush);
        ll_dot_circle = (LinearLayout)findViewById(R.id.ll_dot_circle);

        txtOpacity = (TextView) findViewById(R.id.txtOpacity);
        sbSize = (SeekBar)findViewById(R.id.sbSize);



        LinearLayout myRoot = (LinearLayout) findViewById(R.id.ll_main_dynamic);

        ScrollView scrollView = new ScrollView(context);
       // setContentView(scrollView);
        ScrollView.LayoutParams scrollViewParams = new ScrollView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        int padding = getResources().getDimensionPixelSize(R.dimen.brush_settings_padding);
        layout.setPadding(padding, padding, padding, padding);
        layout.setBackgroundColor(Color.parseColor("#FFFFFF"));

        scrollView.addView(layout);
        myRoot.addView(scrollView);

        call();


        ll_pencil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                brushData.BrushDataPencil();
                layout.removeAllViews();
                call1();
                sessionManager.setActive("P");
                finish();

            }
        });

        ll_dot_line.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                brushData.BrushDataDotPencil();
                layout.removeAllViews();
                call1();
                sessionManager.setActive("Dot");
                finish();

            }
        });

        ll_join_dot_line.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                brushData.BrushDataJoinDotPencil();
                layout.removeAllViews();
                call1();
                sessionManager.setActive("Join_Dot");
                finish();

            }
        });

        ll_brush.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                brushData.BrushDataBrush();
                layout.removeAllViews();
                call1();
                sessionManager.setActive("B");
                 finish();

            }
        });

        ll_medium_brush.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                brushData.BrushDataMediumBrush();
                layout.removeAllViews();
                call1();
                sessionManager.setActive("Medium_Brush");
                finish();

            }
        });

        ll_spry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                brushData.BrushDataSpray();
                layout.removeAllViews();
                call1();
                sessionManager.setActive("S");
                finish();

            }
        });

        ll_big_brush.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                brushData.BrushDataBigBrush();
                layout.removeAllViews();
                call1();
                sessionManager.setActive("Big_Brush");
                finish();

            }
        });

        ll_roll_brush.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                brushData.BrushDataRollBrush();
                layout.removeAllViews();
                call1();
                sessionManager.setActive("Roll");
                finish();

            }
        });

        ll_dot_circle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                brushData.BrushDataDotCircle();
                layout.removeAllViews();
                call1();
                sessionManager.setActive("Dot_Circle");
                finish();
            }
        });




    }

    public class SliderObservable extends Observable {
        public void onValueChanged(SliderView sliderView) {
            setChanged();
            notifyObservers(sliderView);
        }
    }


    public void call(){
        for (int i = 0; i < brushEngine.getBrushList().length; i++) {
            sliderView = new SliderView(getApplicationContext());
            sliderView.setLayoutParams(new ViewGroup.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            BrushData.Brush brush = brushData.getList().get(i);

            sliderView.setName(brush.getName());
            sliderView.setMin(brush.getMinValue());
            sliderView.setMax(brush.getMaxValue());
            sliderView.setValue(brushEngine.getValue(i));
            sliderView.setId(i);
            sliderView.addObserver(brushEngine);

            layout.addView(sliderView);
        }
    }

    public void call1(){
        for (int i = 0; i < brushEngine.getBrushList().length; i++) {
            sliderView = new SliderView(getApplicationContext());
            sliderView.setLayoutParams(new ViewGroup.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            BrushData.Brush brush = brushData.getList().get(i);

            sliderView.setName(brush.getName());
            sliderView.setMin(brush.getMinValue());
            sliderView.setMax(brush.getMaxValue());
            sliderView.setValue(brush.getCurrentValue());
            //sliderView.setValue(brushEngine.getValue(i));
            Log.e("value", String.valueOf(brush.getCurrentValue()));
            Log.e("enginevalue", String.valueOf(brushEngine.getValue(i)));
            sliderView.setId(i);
            //sliderView.addObserver(brushEngine);
            layout.addView(sliderView);
            //sliderObservable.onValueChanged(sliderView);
            brushEngine.setValue(i, brush.getCurrentValue());
        }
        //brushEngine.setupColor();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        /*Intent intent = new Intent(BrushSettingsActivity.this, MainActivity.class);
        startActivity(intent);
        finish();*/
    }
}
