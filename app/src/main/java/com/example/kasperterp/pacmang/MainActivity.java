package com.example.kasperterp.pacmang;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.Timer;
import java.util.TimerTask;
import java.util.ArrayList;
import android.view.View;
import android.view.View.OnClickListener;

import org.w3c.dom.Text;

public class MainActivity extends Activity implements OnClickListener {

    MyView myView;
    private Timer myTimer;


    private int timerCount = 0;
    private boolean running = false;
    static TextView points;
    static TextView timer;
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
        //listener of our pacman
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myView.resetGame();
                timerCount = 0;
                running = false;
                timer.setText("Timer value: "+timerCount);
            }
        });
        rightButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                myView.moveRight(10);
            }
        });
        leftButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                myView.moveLeft(10);
            }
        });
        upButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                myView.moveUp(10);
            }
        });
        downButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                myView.moveDown(10);
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

        }, 0, 200); //0 indicates we start now, 200
        //is the number of miliseconds between each call
    }
    protected void onStop() {
        super.onStop();
        //just to make sure if the app is killed, that we stop the timer.
        myTimer.cancel();
    }

    private void TimerMethod()
    {
        //This method is called directly by the timer
        //and runs in the same thread as the timer.

        //We call the method that will work with the UI
        //through the runOnUiThread method.
        this.runOnUiThread(Timer_Tick);
    }
    private Runnable Timer_Tick = new Runnable() {
        public void run() {

            //This method runs in the same thread as the UI.
            // so we can draw
            if (running)
            {
                timerCount++;
                //update the counter - notice this is NOT seconds in this example
                //you need TWO counters - one for the time and one for the pacman
                timer.setText("Timer value: "+timerCount);
                myView.moveRight(20); //move the pacman.
            }

        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the main; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    public static void updateCounter(int coinCounter){
        points.setText("Points : "+coinCounter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
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