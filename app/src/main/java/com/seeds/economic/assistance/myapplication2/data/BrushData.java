package com.seeds.economic.assistance.myapplication2.data;

import android.content.Context;
import android.content.res.Resources;
import android.widget.Toast;

import com.seeds.economic.assistance.myapplication2.AppAprilBrush;
import com.seeds.economic.assistance.myapplication2.R;
import com.seeds.economic.assistance.myapplication2.SessionManager;

import java.util.ArrayList;
import java.util.List;



public class BrushData {
    public static final String PREF_ITEM_NAME = "Brush";
    private static final BrushData brushData = new BrushData();

    Context context;
    Resources res;
    private List<Brush> list;
    private boolean brushMode = true;
    private SessionManager sessionManager;

    private BrushData() {

        context = AppAprilBrush.getContext();
        sessionManager = new SessionManager(context);
        res = context.getResources();
        list = new ArrayList<Brush>();
        /*list.add(new Brush(res.getString(R.string.brush_size), 1, 150, 20));
        list.add(new Brush(res.getString(R.string.brush_opacity), 0, 100, 90));
        list.add(new Brush(res.getString(R.string.brush_flow), 0, 100, 25));
        list.add(new Brush(res.getString(R.string.brush_hardness), 0, 100, 80));
        list.add(new Brush(res.getString(R.string.brush_spacing), 1, 100, 15));
        list.add(new Brush(res.getString(R.string.brush_roundness), 1, 100, 100));
        list.add(new Brush(res.getString(R.string.brush_angle), 0, 180, 0));
        list.add(new Brush(res.getString(R.string.brush_scatter), 0, 500, 0));
        list.add(new Brush(res.getString(R.string.color_hue), 0, 360, 0));
        list.add(new Brush(res.getString(R.string.color_saturation), 0, 100, 100));
        list.add(new Brush(res.getString(R.string.color_value), 0, 100, 100));*/
        //BrushDataPencil();


        if (sessionManager.getActive().equalsIgnoreCase("P")){
            BrushDataPencil();
        }else if(sessionManager.getActive().equalsIgnoreCase("Dot")){
            BrushDataDotPencil();
        }else if(sessionManager.getActive().equalsIgnoreCase("Join_Dot")){
            BrushDataJoinDotPencil();
        }else if(sessionManager.getActive().equalsIgnoreCase("B")){
            BrushDataBrush();
        }else if(sessionManager.getActive().equalsIgnoreCase("Medium_Brush")){
            BrushDataMediumBrush();
        }else if(sessionManager.getActive().equalsIgnoreCase("S")){
            BrushDataSpray();
        }else if(sessionManager.getActive().equalsIgnoreCase("Big_Brush")){
            BrushDataBigBrush();
        }else if(sessionManager.getActive().equalsIgnoreCase("Roll")){
            BrushDataRollBrush();
        }else if(sessionManager.getActive().equalsIgnoreCase("Dot_Circle")){
            BrushDataDotCircle();
        }


    }

    public void BrushDataPencil() {
        context = AppAprilBrush.getContext();
        sessionManager = new SessionManager(context);
        res = context.getResources();
        list = new ArrayList<Brush>();
        list.add(new Brush(res.getString(R.string.brush_size), 1, 150, 15));
        list.add(new Brush(res.getString(R.string.brush_opacity), 0, 100, 100));
        list.add(new Brush(res.getString(R.string.brush_flow), 0, 100, 100));
        list.add(new Brush(res.getString(R.string.brush_hardness), 0, 100, 100));
        list.add(new Brush(res.getString(R.string.brush_spacing), 1, 100, 5));
        list.add(new Brush(res.getString(R.string.brush_roundness), 1, 100, 100));
        list.add(new Brush(res.getString(R.string.brush_angle), 0, 180, 0));
        list.add(new Brush(res.getString(R.string.brush_scatter), 0, 500, 0));
        list.add(new Brush(res.getString(R.string.color_hue), 0, 360, 0));
        list.add(new Brush(res.getString(R.string.color_saturation), 0, 100, 100));
        list.add(new Brush(res.getString(R.string.color_value), 0, 100, 100));

    }

    public void BrushDataDotPencil() {
        context = AppAprilBrush.getContext();
        sessionManager = new SessionManager(context);
        res = context.getResources();
        list = new ArrayList<Brush>();
        list.add(new Brush(res.getString(R.string.brush_size), 1, 150, 15));
        list.add(new Brush(res.getString(R.string.brush_opacity), 0, 100, 100));
        list.add(new Brush(res.getString(R.string.brush_flow), 0, 100, 100));
        list.add(new Brush(res.getString(R.string.brush_hardness), 0, 100, 100));
        list.add(new Brush(res.getString(R.string.brush_spacing), 1, 200, 200));
        list.add(new Brush(res.getString(R.string.brush_roundness), 1, 100, 100));
        list.add(new Brush(res.getString(R.string.brush_angle), 0, 180, 0));
        list.add(new Brush(res.getString(R.string.brush_scatter), 0, 500, 0));
        list.add(new Brush(res.getString(R.string.color_hue), 0, 360, 0));
        list.add(new Brush(res.getString(R.string.color_saturation), 0, 100, 100));
        list.add(new Brush(res.getString(R.string.color_value), 0, 100, 100));

    }

    public void BrushDataJoinDotPencil() {
        context = AppAprilBrush.getContext();
        sessionManager = new SessionManager(context);
        res = context.getResources();
        list = new ArrayList<Brush>();
        list.add(new Brush(res.getString(R.string.brush_size), 1, 150, 15));
        list.add(new Brush(res.getString(R.string.brush_opacity), 0, 100, 100));
        list.add(new Brush(res.getString(R.string.brush_flow), 0, 100, 100));
        list.add(new Brush(res.getString(R.string.brush_hardness), 0, 100, 100));
        list.add(new Brush(res.getString(R.string.brush_spacing), 1, 100, 100));
        list.add(new Brush(res.getString(R.string.brush_roundness), 1, 100, 100));
        list.add(new Brush(res.getString(R.string.brush_angle), 0, 180, 0));
        list.add(new Brush(res.getString(R.string.brush_scatter), 0, 500, 0));
        list.add(new Brush(res.getString(R.string.color_hue), 0, 360, 0));
        list.add(new Brush(res.getString(R.string.color_saturation), 0, 100, 100));
        list.add(new Brush(res.getString(R.string.color_value), 0, 100, 100));

    }

    public void BrushDataBrush() {
        context = AppAprilBrush.getContext();
        sessionManager = new SessionManager(context);
        res = context.getResources();
        list = new ArrayList<Brush>();
        list.add(new Brush(res.getString(R.string.brush_size), 1, 150, 30));
        list.add(new Brush(res.getString(R.string.brush_opacity), 0, 100, 90));
        list.add(new Brush(res.getString(R.string.brush_flow), 0, 100, 80));
        list.add(new Brush(res.getString(R.string.brush_hardness), 0, 100, 30));
        list.add(new Brush(res.getString(R.string.brush_spacing), 1, 100, 5));
        list.add(new Brush(res.getString(R.string.brush_roundness), 1, 100, 70));
        list.add(new Brush(res.getString(R.string.brush_angle), 0, 180, 100));
        list.add(new Brush(res.getString(R.string.brush_scatter), 0, 500, 0));
        list.add(new Brush(res.getString(R.string.color_hue), 0, 360, 360));
        list.add(new Brush(res.getString(R.string.color_saturation), 0, 100, 100));
        list.add(new Brush(res.getString(R.string.color_value), 0, 100, 100));

    }

    public void BrushDataMediumBrush() {
        context = AppAprilBrush.getContext();
        sessionManager = new SessionManager(context);
        res = context.getResources();
        list = new ArrayList<Brush>();
        list.add(new Brush(res.getString(R.string.brush_size), 1, 150, 50));
        list.add(new Brush(res.getString(R.string.brush_opacity), 0, 100, 90));
        list.add(new Brush(res.getString(R.string.brush_flow), 0, 100, 90));
        list.add(new Brush(res.getString(R.string.brush_hardness), 0, 100, 10));
        list.add(new Brush(res.getString(R.string.brush_spacing), 1, 100, 15));
        list.add(new Brush(res.getString(R.string.brush_roundness), 1, 100, 70));
        list.add(new Brush(res.getString(R.string.brush_angle), 0, 180, 100));
        list.add(new Brush(res.getString(R.string.brush_scatter), 0, 500, 0));
        list.add(new Brush(res.getString(R.string.color_hue), 0, 360, 360));
        list.add(new Brush(res.getString(R.string.color_saturation), 0, 100, 100));
        list.add(new Brush(res.getString(R.string.color_value), 0, 100, 100));

    }

    public void BrushDataBigBrush() {
        context = AppAprilBrush.getContext();
        sessionManager = new SessionManager(context);
        res = context.getResources();
        list = new ArrayList<Brush>();
        list.add(new Brush(res.getString(R.string.brush_size), 1, 150, 75));
        list.add(new Brush(res.getString(R.string.brush_opacity), 0, 100, 85));
        list.add(new Brush(res.getString(R.string.brush_flow), 0, 100, 90));
        list.add(new Brush(res.getString(R.string.brush_hardness), 0, 100, 10));
        list.add(new Brush(res.getString(R.string.brush_spacing), 1, 100, 15));
        list.add(new Brush(res.getString(R.string.brush_roundness), 1, 100, 70));
        list.add(new Brush(res.getString(R.string.brush_angle), 0, 180, 100));
        list.add(new Brush(res.getString(R.string.brush_scatter), 0, 500, 0));
        list.add(new Brush(res.getString(R.string.color_hue), 0, 360, 360));
        list.add(new Brush(res.getString(R.string.color_saturation), 0, 100, 100));
        list.add(new Brush(res.getString(R.string.color_value), 0, 100, 100));

    }

    public void BrushDataRollBrush() {
        context = AppAprilBrush.getContext();
        sessionManager = new SessionManager(context);
        res = context.getResources();
        list = new ArrayList<Brush>();
        list.add(new Brush(res.getString(R.string.brush_size), 1, 150, 100));
        list.add(new Brush(res.getString(R.string.brush_opacity), 0, 100, 80));
        list.add(new Brush(res.getString(R.string.brush_flow), 0, 100, 90));
        list.add(new Brush(res.getString(R.string.brush_hardness), 0, 100, 10));
        list.add(new Brush(res.getString(R.string.brush_spacing), 1, 100, 15));
        list.add(new Brush(res.getString(R.string.brush_roundness), 1, 100, 80));
        list.add(new Brush(res.getString(R.string.brush_angle), 0, 180, 100));
        list.add(new Brush(res.getString(R.string.brush_scatter), 0, 500, 0));
        list.add(new Brush(res.getString(R.string.color_hue), 0, 360, 360));
        list.add(new Brush(res.getString(R.string.color_saturation), 0, 100, 100));
        list.add(new Brush(res.getString(R.string.color_value), 0, 100, 100));

    }

    public void BrushDataDotCircle() {
        context = AppAprilBrush.getContext();
        sessionManager = new SessionManager(context);

        res = context.getResources();
        list = new ArrayList<Brush>();
        list.add(new Brush(res.getString(R.string.brush_size), 1, 150, 80));
        list.add(new Brush(res.getString(R.string.brush_opacity), 0, 100, 85));
        list.add(new Brush(res.getString(R.string.brush_flow), 0, 100, 100));
        list.add(new Brush(res.getString(R.string.brush_hardness), 0, 100, 15));
        list.add(new Brush(res.getString(R.string.brush_spacing), 1, 100, 100));
        list.add(new Brush(res.getString(R.string.brush_roundness), 1, 100, 100));
        list.add(new Brush(res.getString(R.string.brush_angle), 0, 180, 180));
        list.add(new Brush(res.getString(R.string.brush_scatter), 0, 500, 0));
        list.add(new Brush(res.getString(R.string.color_hue), 0, 360, 360));
        list.add(new Brush(res.getString(R.string.color_saturation), 0, 100, 100));
        list.add(new Brush(res.getString(R.string.color_value), 0, 100, 100));

    }

    public void BrushDataSpray() {
        context = AppAprilBrush.getContext();
        sessionManager = new SessionManager(context);

        res = context.getResources();
        list = new ArrayList<Brush>();
        list.add(new Brush(res.getString(R.string.brush_size), 1, 150, 5));
        list.add(new Brush(res.getString(R.string.brush_opacity), 0, 100, 100));
        list.add(new Brush(res.getString(R.string.brush_flow), 0, 100, 100));
        list.add(new Brush(res.getString(R.string.brush_hardness), 0, 100, 100));
        list.add(new Brush(res.getString(R.string.brush_spacing), 1, 100, 20));
        list.add(new Brush(res.getString(R.string.brush_roundness), 1, 100, 100));
        list.add(new Brush(res.getString(R.string.brush_angle), 0, 180, 180));
        list.add(new Brush(res.getString(R.string.brush_scatter), 0, 500, 500));
        list.add(new Brush(res.getString(R.string.color_hue), 0, 360, 360));
        list.add(new Brush(res.getString(R.string.color_saturation), 0, 100, 100));
        list.add(new Brush(res.getString(R.string.color_value), 0, 100, 100));

    }

    public static BrushData getInstance() {
        return brushData;
    }

    public boolean isBrushMode() {
        return brushMode;
    }

    public void setBrushMode(boolean brushMode) {
        this.brushMode = brushMode;
    }

    public void toggleBrushMode() {
        brushMode = !brushMode;
        if (brushMode) {
            Toast.makeText(context, res.getString(R.string.brush_mode), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, res.getString(R.string.eraser_mode), Toast.LENGTH_SHORT).show();
        }
    }

    public List<Brush> getList() {
        /*if (sessionManager.getActive().equalsIgnoreCase("P")){
            BrushDataPencil();
        }else if(sessionManager.getActive().equalsIgnoreCase("B")){
            BrushDataBrush();
        }else  if(sessionManager.getActive().equalsIgnoreCase("S")){
            BrushDataSpray();
        }*/
        return list;
    }


    public int getProperty(Property property) {
        int index = property.ordinal();
        return list.get(index).getCurrentValue();
    }

    public int getProperty(int index) {
        return list.get(index).getCurrentValue();
    }

    // order of the elements is same as order of adding its to the list
    public enum Property {
        SIZE, OPACITY, FLOW, HARDNESS, SPACING, ROUNDNESS, ANGLE, SCATTER, HUE, SATURATION, VALUE
    }

    public class Brush {
        private String name;
        private int minValue;
        private int maxValue;
        private int currentValue;

        public Brush(String name, int minValue, int maxValue, int currentValue) {
            this.name = name;
            this.minValue = minValue;
            this.maxValue = maxValue;
            this.currentValue = currentValue;
        }

        public String getName() {
            return name;
        }

        public int getMinValue() {
            return minValue;
        }

        public int getMaxValue() {
            return maxValue;
        }

        public int getCurrentValue() {
            return currentValue;
        }

        public void setCurrentValue(int currentValue) {
            this.currentValue = currentValue;
        }
    }
}
