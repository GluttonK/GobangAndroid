package com.example.gobangandroid;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class PanelView extends View {

    private Paint paint = new Paint();
    private Bitmap whitePiece;
    private Bitmap blackPiece;


    private float pieceOfLineHeight = 3 * 1.0f / 4;

    private int mPanelWidth;
    private float mLineHeight;

    private int MAX_LINE = 20;
    private int MAX_COUNT_INLINE = 5;

    private boolean isWhite = true;//白棋先手，当前轮到白棋
    private ArrayList<Point> whiteArray = new ArrayList<>();
    private ArrayList<Point> blackArray = new ArrayList<>();

    private boolean isGameOver;//游戏是否结束
    private boolean isWhiteWinner;//谁是赢家


    public PanelView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    private void init(){
        paint.setColor(0x88000000);
        paint.setAntiAlias(true);//抗锯齿
        paint.setDither(true);//防抖动
        paint.setStyle(Paint.Style.STROKE);

        whitePiece = BitmapFactory.decodeResource(getResources(),R.mipmap.stone_w2);
        blackPiece = BitmapFactory.decodeResource(getResources(),R.mipmap.stone_b1);
    }
//get set 操作方法
    public boolean getIsWhite() {
        return isWhite;
    }

    public ArrayList getWhiteArray() {
        return whiteArray;
    }

    public ArrayList getBlackArray() {
        return blackArray;
    }

    public void setIsWhite(boolean isWhite) {
        this.isWhite = isWhite;
    }

    public void setWhiteArray(ArrayList whiteArray){
        this.whiteArray = whiteArray;
    }

    public void setBlackArray(ArrayList blackArray){
        this.blackArray = blackArray;
    }

    public void reStart(){
        whiteArray.clear();
        blackArray.clear();
        isGameOver = false;
        isWhiteWinner = false;
        invalidate();//view里包含的一个方法，刷新view
    }
    //视图宽高测量
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);

        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int width = Math.min(widthSize,heightSize);

        if (widthMode == MeasureSpec.UNSPECIFIED)
            width = heightSize;
        else if (heightMode == MeasureSpec.UNSPECIFIED)
            width = widthSize;
        //设置canvas大小
        setMeasuredDimension(width,width);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mPanelWidth = w;
        mLineHeight = mPanelWidth * 1.0f / MAX_LINE;

        int pieceWidth = (int) (mLineHeight * pieceOfLineHeight);

        whitePiece = Bitmap.createScaledBitmap(whitePiece,pieceWidth,pieceWidth,false);
        blackPiece = Bitmap.createScaledBitmap(blackPiece,pieceWidth,pieceWidth,false);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (isGameOver)
            return false;
        int action = event.getAction();
        if (action == MotionEvent.ACTION_UP){
            int x = (int) event.getX();
            int y = (int) event.getY();
            Point p = getPoint(x,y);



            if (whiteArray.contains(p) || blackArray.contains(p)){
                return false;
            }

            if (isWhite){
                whiteArray.add(p);
            }else {
                blackArray.add(p);
            }
            invalidate();
            isWhite = !isWhite;

        }
        return true;
    }




    private Point getPoint(int x, int y) {
        return new Point((int) (x / mLineHeight), (int) (y / mLineHeight));
    }

    @Override
    protected void onDraw(Canvas canvas) {



        //super.onDraw(canvas);
        //绘制棋盘
        drawBoard(canvas);
        //绘制棋子
        drawPiece(canvas);
        //判断输赢
        checkGameOver();

    }


    private void checkGameOver() {
        boolean whiteWin = checkFiveInline(whiteArray);
        boolean blackWin = checkFiveInline(blackArray);

        if (whiteWin || blackWin) {
            isGameOver = true;
            isWhiteWinner = whiteWin;

            String text = isWhiteWinner ? "白棋胜" : "黑棋胜";
            showDialog(text);
            Toast.makeText(getContext(),text,Toast.LENGTH_SHORT).show();

        }
    }

    public void showDialog(String text){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("提示");
        builder.setMessage(text);
        builder.setCancelable(false);
        builder.setPositiveButton("再来一局", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                reStart();
            }
        });
        builder.create().show();
    }

    private boolean checkFiveInline(List<Point> points) {
        for (Point p : points){
            int x = p.x;
            int y = p.y;

            boolean win = checkHorizontal(x,y,points);
            if (win) return true;
            win = checkVertical(x,y,points);
            if (win) return true;
            win = checkLeftDiagonal(x,y,points);
            if (win) return true;
            win = checkRightDiagonal(x,y,points);
            if (win) return true;
        }
        return false;
    }

    /**
     * 判断(x,y)位置的棋子，是否横向连成5颗
     * @param x
     * @param y
     * @param points
     */
    private boolean checkHorizontal(int x, int y, List<Point> points) {
        int count =1;
        //左边是否有连续5颗
        for (int i = 1; i < MAX_COUNT_INLINE;i++){
            if (points.contains(new Point(x-i,y))){
                count++;
            }else{
                break;
            }
        }
        if (count == MAX_COUNT_INLINE)
            return true;

        //右边是否有连续5颗
        for (int i = 1; i < MAX_COUNT_INLINE;i++){
            if (points.contains(new Point(x+i,y))){
                count++;
            }else{
                break;
            }
        }

        if (count == MAX_COUNT_INLINE)
            return true;

        return false;
    }

    /**
     * 判断(x,y)位置的棋子，是否纵向连成5颗
     * @param x
     * @param y
     * @param points
     */
    private boolean checkVertical(int x, int y, List<Point> points) {
        int count =1;
        //上边是否有连续5颗
        for (int i = 1; i < MAX_COUNT_INLINE;i++){
            if (points.contains(new Point(x,y-i))){
                count++;
            }else{
                break;
            }
        }
        if (count == MAX_COUNT_INLINE)
            return true;

        //下边是否有连续5颗
        for (int i = 1; i < MAX_COUNT_INLINE;i++){
            if (points.contains(new Point(x,y+i))){
                count++;
            }else{
                break;
            }
        }

        if (count == MAX_COUNT_INLINE)
            return true;

        return false;
    }

    /**
     * 判断(x,y)位置的棋子，是否左斜线连成5颗
     * @param x
     * @param y
     * @param points
     */
    private boolean checkLeftDiagonal(int x, int y, List<Point> points) {
        int count =1;
        //左上斜是否有连续5颗
        for (int i = 1; i < MAX_COUNT_INLINE;i++){
            if (points.contains(new Point(x-i,y+i))){
                count++;
            }else{
                break;
            }
        }
        if (count == MAX_COUNT_INLINE)
            return true;

        //右下斜是否有连续5颗
        for (int i = 1; i < MAX_COUNT_INLINE;i++){
            if (points.contains(new Point(x+i,y-i))){
                count++;
            }else{
                break;
            }
        }

        if (count == MAX_COUNT_INLINE)
            return true;

        return false;
    }

    /**
     * 判断(x,y)位置的棋子，是否右斜线连成5颗
     * @param x
     * @param y
     * @param points
     */
    private boolean checkRightDiagonal(int x, int y, List<Point> points) {
        int count =1;
        //右上斜是否有连续5颗
        for (int i = 1; i < MAX_COUNT_INLINE;i++){
            if (points.contains(new Point(x+i,y+i))){
                count++;
            }else{
                break;
            }
        }
        if (count == MAX_COUNT_INLINE)
            return true;

        //左下斜是否有连续5颗
        for (int i = 1; i < MAX_COUNT_INLINE;i++){
            if (points.contains(new Point(x-i,y-i))){
                count++;
            }else{
                break;
            }
        }

        if (count == MAX_COUNT_INLINE)
            return true;

        return false;
    }

    private void drawPiece(Canvas canvas) {
        for (int i =0; i < whiteArray.size(); i++){
            Point whitePoint = whiteArray.get(i);
            canvas.drawBitmap(whitePiece,
                    (whitePoint.x+(1-pieceOfLineHeight)/2)*mLineHeight,
                    (whitePoint.y+(1-pieceOfLineHeight)/2)*mLineHeight,null);
        }

        for (int i =0; i < blackArray.size(); i++){
            Point blackPoint = blackArray.get(i);
            canvas.drawBitmap(blackPiece,
                    (blackPoint.x+(1-pieceOfLineHeight)/2)*mLineHeight,
                    (blackPoint.y+(1-pieceOfLineHeight)/2)*mLineHeight,null);
        }
    }
    //绘制棋盘
    private void drawBoard(Canvas canvas) {
        int w = mPanelWidth;
        float lineHeight = mLineHeight;

        for (int i = 0; i < MAX_LINE; i++){
            int startX = (int) (lineHeight/2);
            int endX = (int) (w - lineHeight/2);

            int y = (int) ((0.5 + i) * lineHeight);
            canvas.drawLine(startX,y,endX,y,paint);

            canvas.drawLine(y,startX,y,endX,paint);
        }
    }

    private static final String INSTANCE = "instance";
    private static final String INSTANCE_GAMEOVER = "instance_game_over";
    private static final String INSTANCE_WHITE_ARRAY = "instance_white_array";
    private static final String INSTANCE_BLACK_ARRAY = "instance_black_array";

    //保存棋盘
    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(INSTANCE,super.onSaveInstanceState());
        bundle.putBoolean(INSTANCE_GAMEOVER,isGameOver);
        bundle.putParcelableArrayList(INSTANCE_WHITE_ARRAY,whiteArray);
        bundle.putParcelableArrayList(INSTANCE_BLACK_ARRAY,blackArray);
        return bundle;
    }

    //恢复棋盘
    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle){
            Bundle bundle = (Bundle) state;
            isGameOver = bundle.getBoolean(INSTANCE_GAMEOVER);
            whiteArray = bundle.getParcelableArrayList(INSTANCE_WHITE_ARRAY);
            blackArray = bundle.getParcelableArrayList(INSTANCE_BLACK_ARRAY);

            super.onRestoreInstanceState(bundle.getParcelable(INSTANCE));
            return;
        }
        super.onRestoreInstanceState(state);
    }
}
