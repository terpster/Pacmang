package com.example.kasperterp.pacmang;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.ArrayList;
import android.view.View;
import android.view.View.OnClickListener;

import org.w3c.dom.Text;

public class MainActivity extends Activity implements OnClickListener {

    MyView myView;
    private Timer myTimer;
    private Timer myTimer2;
    private Timer myTimer3;
    public static int timerCount = 60;
    public boolean running = false;
    private int direction = 1;
    public static int level = 1 ;
    public static int ghostSpeed = 10;
    static TextView points;
    static TextView timer;
    static TextView lvl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button rightButton = (Button) findViewById(R.id.rightButton);
        Button leftButton = (Button) findViewById(R.id.leftButton);
        Button upButton = (Button) findViewById(R.id.upButton);
        Button downButton = (Button) findViewById(R.id.downButton);
        Button resetButton = (Button) findViewById(R.id.resetGame);
        Button pauseButton = (Button) findViewById(R.id.pause);
        pauseButton.setOnClickListener(this);
        Button continueButton =(Button) findViewById(R.id.continuebtn);
        continueButton.setOnClickListener(this);
        points = (TextView)  findViewById(R.id.points);
        myView = (MyView) findViewById(R.id.gameView);
        timer = (TextView) findViewById(R.id.timer);
        lvl = (TextView) findViewById(R.id.level);
        //listener of our pacman
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timerCount = 60;
                ghostSpeed = 10;
                myView.resetGame();
                running = false;
                timer.setText("Timer value: "+timerCount);
                running = true;
                level = 1 ;
                lvl.setText("Level : "+level);
            }
        });
        rightButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                direction = 1;

            }
        });
        leftButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                direction = 2;
            }
        });
        upButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                direction = 3;
            }
        });
        downButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                direction = 4;
            }
        });


        ArrayList<GoldCoin> coins = new ArrayList<GoldCoin>();
        for(int i=0; i<10;i++){
            coins.add(new GoldCoin(0,0, false, false));
        }
        myView.setCoins(coins);

        //make a new timer
        myTimer = new Timer();
        running = true; //should the game be running?
        //We will call the timer 5 times each second
        myTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                TimerMethod();
            }

        }, 0, 30); //0 indicates we start now, 100
        //is the number of miliseconds between each call
        myTimer2 = new Timer();
        myTimer2.schedule(new TimerTask() {
            @Override
            public void run() {
                TimerMethod2();
            }
        },0,1000);

        myTimer3 = new Timer();
        myTimer3.schedule(new TimerTask() {
            @Override
            public void run() {
                TimerMethod3();
            }
        },0,1000);
    }

    protected void onStop() {
        super.onStop();
        //just to make sure if the app is killed, that we stop the timer.
        myTimer.cancel();
        myTimer2.cancel();
        myTimer3.cancel();
    }

    private void TimerMethod()
    {
        //This method is called directly by the timer
        //and runs in the same thread as the timer.

        //We call the method that will work with the UI
        //through the runOnUiThread method.
        this.runOnUiThread(Timer_Tick);

    }
    private  void TimerMethod2(){
        this.runOnUiThread(Timer_time);
    }
    private  Runnable Timer_time = new Runnable() {
        @Override
        public void run() {
            if(running){
                timerCount--;
                timer.setText("Timer value: "+timerCount);
                gameTimer(timerCount);
            }

        }
    };
    private void TimerMethod3(){
        this.runOnUiThread(Timer_enemy);
    }
    private Runnable Timer_enemy = new Runnable() {
        @Override
        public void run() {
            if(running){
                myView.ghostDirection();
            }
        }
    };
    private Runnable Timer_Tick = new Runnable() {
        public void run() {

            //This method runs in the same thread as the UI.
            // so we can draw
            if (running && direction == 1)
            {
//                //update the counter - notice this is NOT seconds in this example
//                //you need TWO counters - one for the time and one for the pacman
//                timer.setText("Timer value: "+timerCount);
                myView.moveRight(10); //move the pacman.
                myView.ghostMove(ghostSpeed);

            }else if(running && direction == 2){
                myView.moveLeft(10);
                myView.ghostMove(ghostSpeed);
            }else if(running && direction == 3){
                myView.moveUp(10);
                myView.ghostMove(ghostSpeed);
            }else if(running && direction == 4){
                myView.moveDown(10);
                myView.ghostMove(ghostSpeed);
            }

        }
    };
    public static void updateTimer(int resetTimer){
        timerCount = resetTimer;
    }
    public static void updateLevler(int x){
        level = x;
        lvl.setText("Level : "+level);
        ghostSpeed = 10;
    }
    public void gameTimer(int x){
        if(x>=1 && myView.coinCounter >= 3){
            myView.resetGame();
            level++;
            System.out.println("YOU WON!");
            lvl.setText("Level : "+level);
            running = false;
            timerCount = 60;
            running = true;
            ghostSpeed++;
        }
        if(x== 0){
            timerCount = 60;
            myView.resetGame();
            running = false;
            timer.setText("Timer value: "+timerCount);
            running = true;
            level = 1 ;
            lvl.setText("Level : "+level);
        }
    }

    public static void updateCounter(int coinCounter){
        points.setText("Points : "+coinCounter);
    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.continuebtn)
        {
            running = true;
        }
        else if (v.getId()==R.id.pause)
        {
            running = false;
        }

    }
}