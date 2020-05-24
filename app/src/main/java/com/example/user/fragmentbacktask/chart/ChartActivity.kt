package com.example.user.fragmentbacktask.chart

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.os.Bundle
import android.util.SparseArray
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.user.fragmentbacktask.R
import com.example.user.fragmentbacktask.kotlin.base.BaseActivity
import com.example.user.fragmentbacktask.utils.ChartManager
import com.example.user.fragmentbacktask.utils.StringUtils
import com.example.user.fragmentbacktask.view.chart.BarMarkView
import com.example.user.fragmentbacktask.view.chart.DoubleLineInfo
import com.example.user.fragmentbacktask.view.chart.DoubleLineMarkView
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.CombinedChart
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.utils.MPPointF
import com.orhanobut.logger.Logger
import java.util.*
import kotlin.collections.ArrayList

class ChartActivity : BaseActivity() {

    lateinit var line_chart: LineChart
    lateinit var bar_chart: BarChart
    lateinit var combine_chart: CombinedChart

    override fun provideLayoutId(): Int {
        return R.layout.activity_chart
    }

    override fun initCreate(savedInstanceState: Bundle?) {
        combine_chart = findViewById(R.id.combine_chart)

        initCombineChart()
    }

    private fun initCombineChart() {
        combine_chart.description.isEnabled = false
        combine_chart.extraTopOffset = 40f
        combine_chart.isLogEnabled = true

        val leftAxis = combine_chart.getAxisLeft()
        leftAxis.setDrawGridLines(false)
        leftAxis.setDrawAxisLine(false)
        leftAxis.setTextSize(10f)
        leftAxis.setTextColor(0xff969FA9.toInt())
        leftAxis.setAxisMinimum(0f)
        leftAxis.setValueFormatter(object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                return "$value"
            }
        })

        val rightAxis = combine_chart.getAxisRight()
        rightAxis.setGranularity(1f)
        rightAxis.setDrawAxisLine(false)
        rightAxis.setDrawGridLines(false)
        rightAxis.setTextSize(10f)
        rightAxis.textColor = 0xff969FA9.toInt()
        rightAxis.setAxisMinimum(0f)

        val xAxis = combine_chart.getXAxis()
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM)
        xAxis.setDrawGridLines(false)
        xAxis.setGranularity(1f)
        xAxis.setAxisLineColor(0xff969FA9.toInt())
        xAxis.setAxisLineWidth(1f)
        xAxis.setTextSize(10f)
        xAxis.setTextColor(0xff969FA9.toInt())
        xAxis.setValueFormatter(object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                val formatValue = 2015 + value.toInt()
                return "$formatValue"
            }
        })

        combine_chart.setDrawOrder(arrayOf(
                CombinedChart.DrawOrder.BAR,
                CombinedChart.DrawOrder.LINE
        ))

        val data = CombinedData()
        val entries = ArrayList<Entry>()
        val entries1 = ArrayList<BarEntry>()
        val lineInfos = ArrayList<DoubleLineInfo>()
        for (index in 0..4) {
            val firstValue = getRandom(15f, 5f)
            val secondValue = getRandom(100f, 0f) * 1000000
            entries.add(Entry(index.toFloat(), firstValue))
            entries1.add(BarEntry(index.toFloat(), secondValue))
            val infoBean = DoubleLineInfo()
            infoBean.x = index
            infoBean.firstValue = firstValue
            infoBean.secondValue = secondValue
            lineInfos.add(infoBean)
        }

        data.setData(generateLineData(entries))
        data.setData(generateBarData(entries1))

        xAxis.setAxisMinimum(-0.5f)
        xAxis.setAxisMaximum(data.getXMax() + 0.5f)

        combine_chart.setData(data)

        val markView = CombineMarkerView(this, lineInfos)
        markView.chartView = combine_chart
        markView.setLeftMax(combine_chart.axisLeft.mAxisMaximum / 0.9f)
        markView.setRightMax(combine_chart.axisRight.mAxisMaximum / 0.9f)
        combine_chart.marker = markView
    }

    private fun initChart() {
        bar_chart = findViewById(R.id.bar_chart)
        ChartManager.getInstance().configBarChart(bar_chart)

        val entries = ArrayList<BarEntry>()
        for (i in 0..4) {
            entries.add(BarEntry(i.toFloat(), getRandom(50f, 0f)))
        }
        val dataSet = BarDataSet(entries, "")
        dataSet.color = -0x888021

        val barData = BarData()
        barData.addDataSet(dataSet)
        barData.barWidth = 0.3f

        val markView = BarMarkView(this)
        markView.chartView = bar_chart
        bar_chart.marker = markView

        bar_chart.setExtraTopOffset(15f)
        bar_chart.data = barData
    }


    private fun initLineChart() {
        line_chart = findViewById(R.id.line_chart)
        ChartManager.getInstance().configLineChart(line_chart, true)
        line_chart.setExtraTopOffset(15f)

        val xAxis = line_chart.xAxis
        xAxis.enableGridDashedLine(12f, 999f, 0f)
        xAxis.gridColor = ContextCompat.getColor(this, R.color.color_8A97A6)
        xAxis.setDrawGridLines(true)
        xAxis.gridLineWidth = 1f

        val leftAxis = line_chart.axisLeft
        leftAxis.enableGridDashedLine(5f, 5f, 0f)

        setLineDatas()
    }

    private fun setLineDatas() {
        val currentIndustryValues = ArrayList<Entry>()
        val allIndustryValues = ArrayList<Entry>()
        val list = ArrayList<DoubleLineInfo>()
        for (i in 0..4) {
            val firstValue = getRandom(40f, 10f)
            val secondValue = getRandom(10f, 5f)
            list.add(DoubleLineInfo(i, firstValue, secondValue))
            currentIndustryValues.add(Entry(i.toFloat(), firstValue))
            allIndustryValues.add(Entry(i.toFloat(), secondValue))
        }
        val currentDataSet = ChartManager.getInstance().lineDataSet(currentIndustryValues, "本行业")
        currentDataSet.color = ContextCompat.getColor(this, R.color.color_868DE3)
        val allDataSet = ChartManager.getInstance().lineDataSet(allIndustryValues, "全行业")
        currentDataSet.color = ContextCompat.getColor(this, R.color.color_F9607D)
        val data = LineData(currentDataSet, allDataSet)
        line_chart.data = data

        val markView = DoubleLineMarkView(this, list)
        // spacetop 30
        markView.setMaxY(line_chart.axisLeft.axisMaximum / 0.7f)
        markView.chartView = line_chart
        line_chart.marker = markView

    }

    class CombineMarkerView : MarkerView {
        lateinit var tvYear: TextView
        lateinit var tvFirstContent: TextView
        private val mPaint: Paint

        private val outerColor: Int
        private val innerColor: Int
        private val indicatorPaint: Paint

        private var leftMax: Float = 0.toFloat()
        private var rightMax: Float = 0.toFloat()

        private var currentInfo: DoubleLineInfo? = null
        private val infoMap: SparseArray<DoubleLineInfo>

        init {
            mPaint = Paint()
            mPaint.isAntiAlias = true
            mPaint.strokeWidth = StringUtils.dp2px(1f).toFloat()
            outerColor = 0xffFFD1DA.toInt()
            innerColor = 0xffF9607D.toInt()

            indicatorPaint = Paint()
            indicatorPaint.isAntiAlias = true

            infoMap = SparseArray()
        }

        constructor(context: Context) : super(context, 0)

        constructor(context: Context, infos: List<DoubleLineInfo>) : super(context, R.layout.view_chart_mark_layout) {
            tvYear = findViewById(R.id.tv_year)
            tvFirstContent = findViewById(R.id.tv_first_value)

            for (i in 0.until(infos.size)) {
                infoMap.put(i, infos[i])
            }
        }

        fun setLeftMax(maxValue: Float) {
            leftMax = maxValue
        }

        fun setRightMax(maxValue: Float) {
            rightMax = maxValue
        }

        override fun refreshContent(e: Entry, highlight: Highlight?) {
            val year = e.x.toInt()
            currentInfo = infoMap[year]
            tvYear.text = year.toString()
            tvFirstContent.text = e.getY().toString()
            super.refreshContent(e, highlight)
        }

        override fun getOffset(): MPPointF {
            return MPPointF(-(width / 2f), (-height).toFloat())
        }

        override fun draw(canvas: Canvas, posX: Float, posY: Float) {
            val offset = getOffsetForDrawingAtPoint(posX, posY)

            val saveId = canvas.save()
            // translate to the correct position and draw
            canvas.translate(posX + offset.x, 0f)
            draw(canvas)
            canvas.restoreToCount(saveId)

            if (posY > StringUtils.dp2px(43f)) {
                canvas.drawLine(posX, StringUtils.dp2px(45f).toFloat(), posX + 2, posY, mPaint)
            }

            currentInfo?.let {
                val ratio: Float = (1 - it.firstValue / rightMax) / (1 - it.secondValue / leftMax)
                val offsetY: Float = (posY - StringUtils.dp2px(28f)) * ratio + StringUtils.dp2px(28f)
                indicatorPaint.color = outerColor
                canvas.drawCircle(posX, offsetY, StringUtils.dp2px(6f).toFloat(), indicatorPaint)
            }
        }
    }


    private fun generateLineData(entries: List<Entry>): LineData {

        val d = LineData()

        val set = LineDataSet(entries, "数量")
        set.color = -0x69f83
        set.lineWidth = 1.5f
        set.setCircleColor(-0x1)
        set.circleRadius = 4f
        set.setDrawCircles(true)
        set.setDrawCircleHole(true)
        set.isHighlightEnabled = false

        set.circleHoleRadius = 3f
        set.circleHoleColor = -0x69f83

        set.setDrawHorizontalHighlightIndicator(false)
        set.setDrawValues(false)

        set.axisDependency = YAxis.AxisDependency.RIGHT
        d.addDataSet(set)

        return d
    }

    private fun generateBarData(entries1: List<BarEntry>): BarData {
        val set1 = BarDataSet(entries1, "金额")
        set1.color = -0x888021
        set1.setDrawValues(false)
        set1.axisDependency = YAxis.AxisDependency.LEFT

        val groupSpace = 0.06f
        val barSpace = 0.02f // x2 dataset

        val d = BarData(set1)
        d.barWidth = .3f

        return d
    }

    protected fun getRandom(range: Float, start: Float): Float {
        return (Math.random() * range).toFloat() + start
    }


    private fun initBarData() {
        val entries = ArrayList<BarEntry>()
        for (i in 0..4) {
            val index = 2015 + i
            val val1 = Random().nextInt(100)
            val val2 = Random().nextInt(100 - val1)
            val val3 = 100 - val1 - val2
//            map.put(index, 100)
            val barEntry = BarEntry(index.toFloat(), floatArrayOf(val1 / 100f, val2 / 100f, val3 / 100f))
            entries.add(barEntry)
        }

        val dataSet = BarDataSet(entries, "")
        dataSet.setDrawIcons(false)
        val colors = intArrayOf(-0x888021, -0x6a641a, -0x3c390f)
        dataSet.setColors(*colors)
        val labels = arrayOf("IPO", "新三板", "并购")
        dataSet.stackLabels = labels

        val data = BarData(dataSet)
        data.barWidth = 0.3f
        data.setDrawValues(false)
    }

    private fun initStackChartSetting(stackChart: BarChart) {
        val leftAxis = stackChart.axisLeft
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART)
        leftAxis.spaceTop = 30f
        leftAxis.setDrawGridLines(false)
        leftAxis.axisMinimum = 0f
        leftAxis.textColor = -0x696057
        leftAxis.textSize = 10f
        leftAxis.valueFormatter = object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                if (value > 1) {
                    return ""
                }
                val yValue = (value * 100).toInt()
                return "$yValue%"
            }
        }

        val xAxis = stackChart.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.setDrawGridLines(false)
        xAxis.textColor = -0x696057
        xAxis.textSize = 10f
        xAxis.granularity = 1f
        xAxis.labelCount = 5
        xAxis.setDrawGridLines(false)

        val xAxisFormat = object : ValueFormatter() {

            override fun getFormattedValue(value: Float): String {
                val index = value.toInt()
                //                if (index < 0 || index >= labels.size()) {
                //                    return ""
                //                }
                Logger.d("chart value: x $index")
                return index.toString()
            }
        }
        xAxis.valueFormatter = xAxisFormat

        val l = stackChart.legend
        l.verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
        l.horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
        l.orientation = Legend.LegendOrientation.HORIZONTAL
        l.setDrawInside(false)
        l.formSize = 6f
        l.textSize = 12f
        l.textColor = -0xe7e1da
        l.formToTextSpace = 6f
        l.xEntrySpace = 10f

        val entries = ArrayList<BarEntry>()
        for (i in 0..4) {
            val index = 2015 + i
            val val1 = Random().nextInt(100)
            val val2 = Random().nextInt(100 - val1)
            val val3 = 100 - val1 - val2
//            map.put(index, 100)
            val barEntry = BarEntry(index.toFloat(), floatArrayOf(val1 / 100f, val2 / 100f, val3 / 100f))
            entries.add(barEntry)
        }

        val dataSet = BarDataSet(entries, "")
        dataSet.setDrawIcons(false)
        val colors = intArrayOf(-0x888021, -0x6a641a, -0x3c390f)
        dataSet.setColors(*colors)
        val labels = arrayOf("IPO", "新三板", "并购")
        dataSet.stackLabels = labels

        val data = BarData(dataSet)
        data.barWidth = 0.3f
        data.setDrawValues(false)

        stackChart.data = data
    }
}
