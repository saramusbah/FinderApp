package com.example.bassant.finderapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import java.util.ArrayList;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class UserActivity extends AppCompatActivity implements View.OnClickListener {

    TextView welcomeUser ;

    private Button bt;
    private ListView lv;
    private Button logout;
    TextView userName ;
    private Boolean isFabOpen = false;
    private FloatingActionButton fab,fab2,fab3;
    private Animation fab_open,fab_close,rotate_forward,rotate_backward;

    private ArrayList<String> strArr;
    private ArrayAdapter<String> adapter;
    UserLocalStore userLocalStore;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setHasOptionsMenu(true);

        setContentView(R.layout.activity_user);

       //bt = (Button) findViewById(R.id.but2);
        lv = (ListView) findViewById(R.id.listView1);
        logout=(Button) findViewById(R.id.logout) ;
        userLocalStore = new UserLocalStore(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        userName = (TextView)findViewById(R.id.user_profile_name) ;
        Intent intent = getIntent();
        final String username = intent.getStringExtra("username");
        userName.setText(username);

        fab = (FloatingActionButton)findViewById(R.id.fab);
        fab2 = (FloatingActionButton)findViewById(R.id.fab2);
        fab3 = (FloatingActionButton)findViewById(R.id.fab3);
        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_close);
        rotate_forward = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_forward);
        rotate_backward = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_backward);

        fab.setOnClickListener(this);

       // welcomeUser = (TextView)findViewById(R.id.welcome) ;
        //Intent intent1 = getIntent();
        //final String username = intent.getStringExtra("username");
        //welcomeUser.setText(" Welcome "+username+"!");

        strArr = new ArrayList<String>();
        strArr.add("Your previous activities:");
      /*  adapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_list_item_1, strArr);*/
        adapter =  new ArrayAdapter(this,android.R.layout.simple_list_item_1,android.R.id.text1,strArr);

        lv.setAdapter(adapter);



        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Intent intent = new Intent(UserActivity.this, DetailsActivity.class);
                String message = "abc";
                intent.putExtra(EXTRA_MESSAGE, message);
                startActivity(intent);
            }
        });

      /*  logout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                userLocalStore.clearUserData();
                userLocalStore.setUserLoggedIn(false);
                startActivity(new Intent(UserActivity.this, LoginActivity.class));


            }
        });*/

        /*bt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                String  byDefault = "No previous activities to show!";
                //strArr.add(et.getText().toString());
                strArr.add(byDefault);
                adapter.notifyDataSetChanged();

            }
        });*/


        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                Intent intent = new Intent(UserActivity.this, RequestActivity.class);
                intent.putExtra("username", username);

                intent.putExtra("fabValue", "f");
                UserActivity.this.startActivity(intent);


            }
        });
        fab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                Intent intent = new Intent(UserActivity.this, RequestActivity.class);
                intent.putExtra("username", username);

                intent.putExtra("fabValue", "m");
                UserActivity.this.startActivity(intent);


            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_user, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.logout:{
                userLocalStore.clearUserData();
                userLocalStore.setUserLoggedIn(false);
                Intent intent = new Intent(this, LoginActivity.class);
                this.startActivity(intent);
                return true;}
            case R.id.show: {
                



                    // Create http cliient object to send request to server

                    HttpClient Client = new DefaultHttpClient();

                    // Create URL string

                    String URL = "http://alexmorsi.pythonanywhere.com/api/users/login/bassantMorsi/";

                    //Log.i("httpget", URL);

                    try
                    {

                        Toast.makeText(getBaseContext(),"ya mosahel !", Toast.LENGTH_SHORT).show();

                        String SetServerString = "";

                        // Create Request to server and get response

                        HttpGet httpget = new HttpGet(URL);
                        ResponseHandler<String> responseHandler = new BasicResponseHandler();
                        SetServerString = Client.execute(httpget, responseHandler);

                        // Show response on activity
                        if(SetServerString==null)
                        {
                            Toast.makeText(getBaseContext(),"Error !", Toast.LENGTH_SHORT).show();

                        }
                        else
                        {
                            Toast.makeText(getBaseContext(),"b3333333t",Toast.LENGTH_SHORT).show();

                        }

//                        content.setText(SetServerString);
                    }
                    catch(Exception ex)
                    {
                        ex.printStackTrace();                    }



                /*HttpUserAsyncTask httpUserAsyncTask = new HttpUserAsyncTask();
                httpUserAsyncTask.execute("http://alexmorsi.pythonanywhere.com/api/users/login/bassantMorsi/");*/
                //String byDefault = "No previous activities to show!";
                //strArr.add(et.getText().toString());
               ///// strArr.clear();00000
                /*strArr.add("1 Missed request, Date: 2017-07-01");
                strArr.add("2 Found request, Date: 2017-07-03");
                strArr.add("3 Found request, Date: 2017-07-05");

                adapter.notifyDataSetChanged();*/
                return true;

            }
            default:
                return super.onOptionsItemSelected(item);
        }
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
