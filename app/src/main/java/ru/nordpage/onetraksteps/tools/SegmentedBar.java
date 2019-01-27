package ru.nordpage.onetraksteps.tools;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import java.text.SimpleDateFormat;
import java.util.Date;

import ru.nordpage.onetraksteps.R;

public class SegmentedBar extends View {

    private int maxValue;
    private int walk;
    private int aerobic;
    private int run;
    private int divider;
    private int height;
    private long dateInMillis;

    private Paint fill_1;
    private Paint fill_2;
    private Paint fill_3;

    private Paint datePaint;
    private Paint stepsPaint;
    private Paint valuePaint;
    private Paint titlePaint;
    private int offset;

    private int mainColor;
    String date;
    int summ;

    public SegmentedBar(Context context) {
        super(context);
    }

    public SegmentedBar(Context context, int max, int walk, int aerobic, int run, long date) {
        super(context);
        this.maxValue = max;
        this.walk = walk;
        this.aerobic = aerobic;
        this.run = run;
        this.dateInMillis = date;
        this.divider = 3;
        this.height = 12;
    }

    public SegmentedBar(Context context, AttributeSet attrs) {
        super(context, attrs);


        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.SegmentedBar);

        try {
            walk = typedArray.getInt(R.styleable.SegmentedBar_sb_walk,walk);
            aerobic = typedArray.getInt(R.styleable.SegmentedBar_sb_aerobic,aerobic);
            run = typedArray.getInt(R.styleable.SegmentedBar_sb_run,run);
            maxValue = typedArray.getInt(R.styleable.SegmentedBar_sb_max,maxValue);
            divider = typedArray.getInt(R.styleable.SegmentedBar_sb_divider,3);
            height = typedArray.getInt(R.styleable.SegmentedBar_sb_height,12);
            mainColor = Color.parseColor("#000000");
        } finally {
            typedArray.recycle();
        }



       datePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
       datePaint.setTextSize(32.0f);
       datePaint.setColor(mainColor);
       datePaint.setTextAlign(Paint.Align.LEFT);
       datePaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

        stepsPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        stepsPaint.setTextSize(28.0f);
        stepsPaint.setColor(mainColor);
        stepsPaint.setTextAlign(Paint.Align.RIGHT);

        valuePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        valuePaint.setTextSize(32.0f);
        valuePaint.setColor(mainColor);
        valuePaint.setTextAlign(Paint.Align.LEFT);
        valuePaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

        titlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        titlePaint.setTextSize(24.0f);
        titlePaint.setColor(mainColor);
        titlePaint.setTextAlign(Paint.Align.LEFT);

        fill_1 = new Paint();
        fill_1.setColor(Color.parseColor("#B2E3F1"));
        fill_2 = new Paint();
        fill_2.setColor(Color.parseColor("#58C7E4"));
        fill_3 = new Paint();
        fill_3.setColor(Color.parseColor("#378397"));

        offset = 20;
    }

    private void drawRect(Canvas canvas){


        int width = (summ*100)/getContentWidth();
        int walk_width = ((walk *100)/width);
        int aerobic_width = ((aerobic *100)/width);
        int run_width = ((run *100)/width);



        float halfBarHeight = height / 2;
        float top = getPaddingTop()+Math.max(datePaint.getFontSpacing(),stepsPaint.getFontSpacing())+offset;
        float bottom = top + height;
        float left_1 = getPaddingLeft();
        float right_1 = getPaddingLeft() + walk_width;
        RectF rect = new RectF(left_1, top, right_1, bottom);
        canvas.drawRoundRect(rect, halfBarHeight, halfBarHeight, fill_1);

        float left_2 = walk_width+divider;
        float right_2 = right_1+aerobic_width;
        RectF rect_2 = new RectF(left_2, top, right_2, bottom);
        canvas.drawRoundRect(rect_2, halfBarHeight, halfBarHeight, fill_2);


        float left_3 = left_2+aerobic_width+divider;
        float right_3 = right_2+run_width;
        canvas.drawRoundRect(left_3, top, right_3, bottom, halfBarHeight, halfBarHeight, fill_3);



    }

    private void drawDateValue(Canvas canvas){
        float x = getPaddingLeft();
        Rect bounds = new Rect();
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        date = formatter.format(new Date(dateInMillis));
        datePaint.getTextBounds(date,0,date.length(),bounds);
        float y = getPaddingTop() + bounds.height();

        canvas.drawText(date,x,y,datePaint);
    }

    private void drawStepsValue(Canvas canvas){
        summ = walk + aerobic + run;
        String steps = String.format("%1$d / %2$d steps",summ,maxValue);
        Rect stepsValueRect = new Rect();
        stepsPaint.getTextBounds(steps,0,steps.length(),stepsValueRect);
        float x = getWidth() - getPaddingRight();
        float y = getPaddingTop() + stepsValueRect.height();
        canvas.drawText(steps,x,y,stepsPaint);

    }

    private void drawValue(Canvas canvas){
        String walkTit = "walk";
        String aerobicTit = "aerobic";
        String runTit = "run";
        int third = getContentWidth()/3;
        Rect valueRect = new Rect();
        valuePaint.getTextBounds(String.valueOf(walk),0,String.valueOf(walk).length(),valueRect);
        Rect walkRect = new Rect();
        titlePaint.getTextBounds(walkTit,0,walkTit.length(),walkRect);
        float x = getPaddingLeft()+third/2;
        float y = getPaddingTop()+Math.max(datePaint.getFontSpacing(),stepsPaint.getFontSpacing())+height+valuePaint.getFontSpacing()+offset+10;

        canvas.drawText(String.valueOf(walk),x,y,valuePaint);
        canvas.drawText(walkTit,x,y+walkRect.height()+10,titlePaint);


        Rect valueRect_2 = new Rect();
        valuePaint.getTextBounds(String.valueOf(aerobic),0,String.valueOf(aerobic).length(),valueRect_2);
        float x_1 = getPaddingLeft()+third+valueRect.width();
        Rect aerobicRect = new Rect();
        titlePaint.getTextBounds(aerobicTit,0,aerobicTit.length(),aerobicRect);
        canvas.drawText(aerobicTit,x_1,y+aerobicRect.height()+10,titlePaint);
        canvas.drawText(String.valueOf(aerobic),x_1,y,valuePaint);


        Rect valueRect_3 = new Rect();
        valuePaint.getTextBounds(String.valueOf(run),0,String.valueOf(run).length(),valueRect_3);
        float x_2 = getPaddingLeft()+third*2+valueRect.width()+valueRect_2.width()-third/2;
        Rect runRect = new Rect();
        titlePaint.getTextBounds(runTit,0,runTit.length(),runRect);
        canvas.drawText(String.valueOf(run),x_2,y,valuePaint);
        canvas.drawText(runTit,x_2,y+runRect.height()+10,titlePaint);





    }





    private int measureHeight(int measureSpec) {
        int size = getPaddingTop() + getPaddingBottom();
        size += Math.max(datePaint.getFontSpacing(), stepsPaint.getFontSpacing()) + height + valuePaint.getFontSpacing() + titlePaint.getFontSpacing()+offset*2f;
        return resolveSizeAndState(size, measureSpec, 0);
    }

    @Override
    protected void onMeasure (int widthMeasureSpec, int heightMeasureSpec) {

        setMeasuredDimension(widthMeasureSpec, measureHeight(heightMeasureSpec));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawDateValue(canvas);
        drawStepsValue(canvas);
        drawRect(canvas);
        drawValue(canvas);
    }

    private int getContentWidth() {
        return getWidth() - getPaddingLeft() - getPaddingRight();
    }

    private Rect getSize(TextPaint paint,String value){
        Rect bounds = new Rect();
        paint.getTextBounds(value, 0, value.length(), bounds);
        return bounds;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    public int getWalk() {
        return walk;
    }

    public void setWalk(int walk) {
        this.walk = walk;
    }

    public int getAerobic() {
        return aerobic;
    }

    public void setAerobic(int aerobic) {
        this.aerobic = aerobic;
    }

    public int getRun() {
        return run;
    }

    public void setRun(int run) {
        this.run = run;
    }

    public long getDate() {
        return dateInMillis;
    }

    public void setDate(long date) {
        this.dateInMillis = date;
    }

    public int getSumm(){
        return walk+aerobic+run;
    }
}
