package com.seeds.economic.assistance.myapplication2.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.seeds.economic.assistance.myapplication2.AppAprilBrush;
import com.seeds.economic.assistance.myapplication2.R;
import com.seeds.economic.assistance.myapplication2.data.BrushData;

import java.util.Observable;
import java.util.Observer;



public class SliderView extends RelativeLayout {
    protected SeekBar seekBar;
    private TextView nameView;
    private TextView valueView;
    private int minValue;
    private int value;
    private int id;
    private SliderObservable sliderObservable;
    private static final SliderView sliderView = new SliderView();

    public SliderView(Context context) {
        super(context);
        sliderObservable = new SliderObservable();
        initComponent();
    }

    public SliderView() {
        super(AppAprilBrush.getContext());
        sliderObservable = new SliderObservable();
        initComponent();
    }
    public static SliderView getInstance() {
        return sliderView;
    }

    public SliderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        sliderObservable = new SliderObservable();
        initComponent();
    }

    public SeekBar getSeekBar() {
        return seekBar;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getValue() {
        return value;
    }


    public void setValue(int value) {
        seekBar.setProgress(value);
        valueView.setText(String.format("%d", value));
    }

    public String getName() {
        return nameView.getText().toString();
    }

    public void setName(String name) {
        nameView.setText(name);
    }

    public void setMax(int value) {
        seekBar.setMax(value - minValue);
    }

    public void setMin(int value) {
        this.minValue = value;
    }

    public void addObserver(Observer observer) {
        sliderObservable.addObserver(observer);
    }

    private void initComponent() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.slider, this, true);
        nameView = (TextView) findViewById(R.id.slider_name_textView);
        valueView = (TextView) findViewById(R.id.slider_value_textView);

        seekBar = (SeekBar) findViewById(R.id.slider_seekBar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStartTrackingTouch(SeekBar seekBar1) {
            }

            @Override
            public void onProgressChanged(SeekBar seekBar1, int progress, boolean fromUser) {
                value = minValue + progress;
                valueView.setText(String.format("%d", value));

               /* Log.e("minValue", String.valueOf(minValue));
                Log.e("progress", String.valueOf(progress));
                Log.e("value", String.valueOf(value));*/
                sliderObservable.onValueChanged(SliderView.this);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar1) {
            }
        });
    }

    public class SliderObservable extends Observable {
        public void onValueChanged(SliderView sliderView) {
            setChanged();
            notifyObservers(sliderView);
        }
    }
}
