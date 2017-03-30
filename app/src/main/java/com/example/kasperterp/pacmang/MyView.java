package com.example.kasperterp.pacmang;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import java.util.ArrayList;
import java.util.Random;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;



public class MyView extends View {

    Bitmap pacman = BitmapFactory.decodeResource(getResources(), R.drawable.pacman);
    Bitmap bitCoin = BitmapFactory.decodeResource(getResources(), R.drawable.coin);
    //The coordinates for our dear pacman: (0,0) is the top-left corner
    int pacx = 50;
    int pacy = 400;
    boolean newGame = true;
    int coinCounter = 0;

    int h, w; //used for storing our height and width
    ArrayList<GoldCoin> coins;

    public void setCoins(ArrayList getCoins) {
        coins = getCoins;
    }

    public void moveRight(int x) {
        //still within our boundaries?
        if (pacx + x + pacman.getWidth() < w)
            pacx = pacx + x;
        coinCollision();
        invalidate(); //redraw everything - this ensures onDraw() is called.
    }

    public void moveLeft(int x) {
        //still within our boundaries?
        if (pacx - x > 0)
            pacx = pacx - x;
        coinCollision();
        invalidate(); //redraw everything - this ensures onDraw() is called.
    }

    public void moveUp(int x) {
        //still within our boundaries?
        if (pacy - x > 0)
            pacy = pacy - x;
        coinCollision();
        invalidate(); //redraw everything - this ensures onDraw() is called.
    }

    public void moveDown(int x) {
        //still within our boundaries?
        if (pacy + x + pacman.getWidth() < h)
            pacy = pacy + x;
        coinCollision();
        invalidate(); //redraw everything - this ensures onDraw() is called.
    }

    /* The next 3 constructors are needed for the Android view system,
    when we have a custom view.
     */
    public MyView(Context context) {
        super(context);

    }

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    public MyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    public void resetGame(){
        newGame = true;
        pacx = 50;
        pacy = 400;
        coinCounter = 0;
        MainActivity.updateCounter(coinCounter);
        invalidate();
        System.out.println(newGame);
    }
    public void coinCollision() {
        for (GoldCoin coin : coins) {
            int sensorPacx = pacx + pacman.getWidth()/2;
            int sensorPacy = pacy + pacman.getHeight()/2;
            int sensorCoinx = coin.posX + bitCoin.getWidth()/2;
            int sensorCoiny = coin.posY + bitCoin.getHeight()/2;
            double xdist = pow((sensorCoinx - sensorPacx), 2);
            double ydist = pow((sensorCoiny - sensorPacy), 2);
            double dist = sqrt(xdist + ydist);
            if(dist<65 && coin.taken ==false ){
                coin.taken = true;
                coinCounter++;
                System.out.println(coinCounter);
                MainActivity.updateCounter(coinCounter);
            }

        }


    }

    //In the onDraw we put all our code that should be
    //drawn whenever we update the screen.
    @Override
    protected void onDraw(Canvas canvas) {
        Random rand = new Random();
        //Here we get the height and weight
        h = canvas.getHeight();
        w = canvas.getWidth();
        //Making a new paint object
        Paint paint = new Paint();
        //setting the color
		/*paint.setColor(Color.RED);
		canvas.drawColor(Color.WHITE); //clear entire canvas to white color*/

        if (newGame == true) {
            for (GoldCoin coin : coins) {
                coin.init = true;
                coin.taken = false;
                coin.posX = rand.nextInt(w);
                coin.posY = rand.nextInt(h);
            }
            newGame =false;
        }
        for (GoldCoin coin : coins) {
            if(coin.taken == false){
                canvas.drawBitmap(bitCoin, coin.posX, coin.posY, paint);
            }
        }
        canvas.drawBitmap(pacman, pacx, pacy, paint);
        super.onDraw(canvas);
    }

}