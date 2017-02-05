package com.example.diceout;

import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    //field to hold random number
    Random rand;
    //field to hold the roll result text
    TextView rollResult;


    //fields to hold the die value
    int die1, die2, die3;
    //Array list to hold all 3 die values
    ArrayList<Integer> dice;

    //arraylist to hold images
    ArrayList<ImageView> diceImageViews;
    //field to old socre text
    TextView scoreText;
    //field to hold the score
    int score;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rollDice(view);
            }
        });
        //set initial score
        score=0;

        //create greeting
        Toast.makeText(getApplicationContext(),"Welcome to DiceOut!", Toast.LENGTH_SHORT).show();

        rollResult=(TextView)findViewById(R.id.rollResult);

        scoreText= (TextView)findViewById(R.id.scoreText);
        rand= new Random();
        dice=new ArrayList<Integer>();
        ImageView die1Image=(ImageView) findViewById(R.id.die1Image);
        ImageView die2Image=(ImageView) findViewById(R.id.die2Image);
        ImageView die3Image=(ImageView) findViewById(R.id.die3Image);


        diceImageViews=new ArrayList<ImageView>();
        diceImageViews.add(die1Image);
        diceImageViews.add(die2Image);
        diceImageViews.add(die3Image);
    }

    public void rollDice(View v){
        //roll dice
        die1=rand.nextInt(6)+1;
        die2=rand.nextInt(6)+1;
        die3=rand.nextInt(6)+1;

        //set dice value in arraylist
        dice.clear();
        dice.add(die1);
        dice.add(die2);
        dice.add(die3);
        for (int dieofset=0; dieofset<3; dieofset++){
            String imageName="die_"+dice.get(dieofset)+".png";

            try {
                InputStream stream = getAssets().open(imageName);
                Drawable d= Drawable.createFromStream(stream,null);
                diceImageViews.get(dieofset).setImageDrawable(d);
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        //build message with the result
        String msg;

        if(die1==die2&& die2==die3){
            int scoreDelta= die1*100;
            msg="Rolled a Triple "+ die1+" You score "+scoreDelta+" points!";
            score+=scoreDelta;
        }else if( die1== die2 || die2==die3 || die1==die3){
            msg=" You rolled Double for 50 points!";
            score+=50;

        }else{
            msg="Try again!";
        }
        //update the app to display the result
        rollResult.setText(msg);
        scoreText.setText("Score: "+score);
        }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
