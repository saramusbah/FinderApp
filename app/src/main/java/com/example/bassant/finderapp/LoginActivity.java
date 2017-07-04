package com.example.bassant.finderapp;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;

public class LoginActivity extends AppCompatActivity {

    EditText userName ;
    EditText password ;
    User user ;
    Button button;
    TextView signup;
    UserLocalStore userLocalStore;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        user = new User();
        userName = (EditText)findViewById(R.id.userName);
        password = (EditText)findViewById(R.id.password);
        signup = (TextView)findViewById(R.id.signup) ;

        // String userNameString = userName.getText().toString();
        // String passwordString = password.getText().toString();

        button = (Button)findViewById(R.id.login);
        userLocalStore = new UserLocalStore(this);



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isOnline()){
                    Toast.makeText(getBaseContext(),"Please check the internet connection!",Toast.LENGTH_SHORT).show();

                }
                else {
                    HttpLoginAsyncTask httpLoginAsyncTask = new HttpLoginAsyncTask();
                    httpLoginAsyncTask.execute("http://alexmorsi.pythonanywhere.com/api/users/login/");
                }
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, MainActivity.class));

            }
        });
    }
    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }


    public static String POST(String url, User user){
        InputStream inputStream = null;
        String result = "";
        try {

            // 1. create HttpClient
            HttpClient httpclient = new DefaultHttpClient();

            // 2. make POST request to the given URL
            HttpPost httpPost = new HttpPost(url);

            String json = "";

            // 3. build jsonObject
            JSONObject jsonObject = new JSONObject();
            jsonObject.accumulate("userName", user.getUserName());
            jsonObject.accumulate("password", user.getPassword());
            // jsonObject.accumulate("email", user.getEmail());
            //jsonObject.accumulate("mobile", user.getMobile());


            // 4. convert JSONObject to JSON to String
            json = jsonObject.toString();
            Log.i("json",json);
            // ** Alternative way to convert Person object to JSON string usin Jackson Lib
            // ObjectMapper mapper = new ObjectMapper();
            // json = mapper.writeValueAsString(person);

            // 5. set json to StringEntity
            StringEntity se = new StringEntity(json);

            // 6. set httpPost Entity
            httpPost.setEntity(se);

            // 7. Set some headers to inform server about the type of the content
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");

            // 8. Execute POST request to the given URL
            HttpResponse httpResponse = httpclient.execute(httpPost);
            String responseBody = EntityUtils.toString(httpResponse.getEntity());

            result =responseBody;
            // Log.i("result",result);
        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }

        // 11. return result
        return result;
    }

    private class HttpLoginAsyncTask extends AsyncTask<String, Void, String> {
        /* private Context mContext;

         public HttpAsyncTask (Context context){
             mContext = context;
         }
        */
        @Override
        protected  void onPreExecute ()
        {
            user = new User();
            userName = (EditText)findViewById(R.id.username);
            password = (EditText)findViewById(R.id.password);
            // email = (EditText)findViewById(R.id.email) ;
            // mobile = (EditText)findViewById(R.id.mobile);
            //  button = (Button)findViewById(R.id.login);

            String userNameString = userName.getText().toString();
            //Log.i("@@@@@@@@@@@@@@@@@@@@",userNameString);
            String passwordString = password.getText().toString();
            //  String emailString = email.getText().toString();
            //  String mobileString = mobile.getText().toString();

            user.setUserName(userNameString);
            user.setPassword(passwordString);
            // user.setMobile(mobileString);
            // user.setEmail(emailString);


        }
        @Override
        protected String doInBackground(String... urls) {

            return POST(urls[0],user);
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            int z=0;
            Log.i("Response",result);
            //Toast.makeText(getBaseContext(),"Data Sent!",Toast.LENGTH_SHORT).show();

            try {
                String uN2=user.getUserName();
                JSONObject jsonobject = new JSONObject(result);

                if(result.contains("status")){
                   /* String st = jsonobject.getString("status");
                    Log.i("status", st); */
                    z=1;

                }
                else {
                   /* String uN = jsonobject.getString("userName");
                    Log.i("userName", uN);
                    String p = jsonobject.getString("password");
                    String e = jsonobject.getString("email");
                    String m = jsonobject.getString("mobile"); */
                    z=2;
                }




                if(z==2)
                {

                    Intent intent = new Intent(LoginActivity.this, UserActivity.class);
                    userLocalStore.storeUserData(user);
                    userLocalStore.setUserLoggedIn(true);

                    intent.putExtra("username", uN2);
                    LoginActivity.this.startActivity(intent);

                }

                else if (z==1){
                    Toast.makeText(getBaseContext(),"Error in Login, please check the inputs!",Toast.LENGTH_SHORT).show();
                }

                // z=1;

                //}


            } catch (JSONException e) {
                e.printStackTrace();
            }

            //if (z==1){
            //    Toast.makeText(getBaseContext(),"Error in Login, please check the inputs!",Toast.LENGTH_SHORT).show();

            //}


        }
    }





}
