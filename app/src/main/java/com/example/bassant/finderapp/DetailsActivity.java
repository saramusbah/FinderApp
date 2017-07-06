package com.example.bassant.finderapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class DetailsActivity extends AppCompatActivity implements View.OnClickListener {
    private ArrayList<String> strArr;
    private ArrayAdapter adapter;
    private ListView lv;
    private Boolean isFabOpen = false;
    private FloatingActionButton fab,fab2,fab3,right,wrong;
    private Animation fab_open,fab_close,rotate_forward,rotate_backward;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details2);

        lv = (ListView) findViewById(R.id.list);
        strArr = new ArrayList<String>();
        strArr.add("1 date: 2017-07-01");
        strArr.add("2 status: false");
        strArr.add("3 fName: Asmaa");
        strArr.add("4 gender: f");
        strArr.add("5 type: missed");
        //right = (FloatingActionButton)findViewById(R.id.right);
        //wrong = (FloatingActionButton)findViewById(R.id.wrong);

        fab = (FloatingActionButton)findViewById(R.id.fab);
        fab2 = (FloatingActionButton)findViewById(R.id.fab2);
        fab3 = (FloatingActionButton)findViewById(R.id.fab3);
        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_close);
        rotate_forward = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_forward);
        rotate_backward = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_backward);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab.setOnClickListener(this);
        //right.setOnClickListener(this);
        ///wrong.setOnClickListener(this);

       /* strArr.add(1,"date: 2017-07-01" );
        strArr.add(2,"status: false" );
        strArr.add(3,"fName: Asmaa" );
        strArr.add(1,"gender: f" );
        strArr.add(1,"type: missed" ); */

        adapter =  new ArrayAdapter(this,android.R.layout.simple_list_item_1,android.R.id.text1,strArr);

        lv.setAdapter(adapter);
        Toast.makeText(getBaseContext(),"Congratulations!",Toast.LENGTH_SHORT).show();


    }




    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.fab:

                animateFAB();
                break;
            case R.id.fab2:

                Log.d("Raj", "Fab 1");
                break;
            case R.id.fab3:

                Log.d("Raj", "Fab 2");
                break;
        }
    }
    public void animateFAB(){

        if(isFabOpen){

            fab.startAnimation(rotate_backward);
            fab2.startAnimation(fab_close);
            fab3.startAnimation(fab_close);
            fab2.setClickable(false);
            fab3.setClickable(false);
            isFabOpen = false;
            Log.d("Raj", "close");

        } else {

            fab.startAnimation(rotate_forward);
            fab2.startAnimation(fab_open);
            fab3.startAnimation(fab_open);
            fab2.setClickable(true);
            fab3.setClickable(true);
            isFabOpen = true;
            Log.d("Raj","open");

        }
    }
}
