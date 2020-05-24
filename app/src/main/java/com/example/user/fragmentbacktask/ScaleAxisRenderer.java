package com.example.user.fragmentbacktask;

import android.graphics.Canvas;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.renderer.AxisRenderer;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.ViewPortHandler;

/**
 * Created by huangyuxi on 2019-06-20
 * Title:
 */
public class ScaleAxisRenderer extends AxisRenderer {

    public ScaleAxisRenderer(ViewPortHandler viewPortHandler, Transformer trans, AxisBase axis) {
        super(viewPortHandler, trans, axis);
    }

    @Override
    public void renderAxisLabels(Canvas c) {

    }

    @Override
    public void renderAxisLine(Canvas c) {

    }

    @Override
    public void renderGridLines(Canvas c) {

    }


    @Override
    public void renderLimitLines(Canvas c) {

    }
}
