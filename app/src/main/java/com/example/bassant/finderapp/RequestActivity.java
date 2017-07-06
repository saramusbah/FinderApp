package com.example.bassant.finderapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RequestActivity extends AppCompatActivity {


    ImageView viewImage;
    ImageView bPhoto;
    EditText firstName;
    EditText date;
    RadioGroup g;
    Request request;
    Toolbar toolbar;
    int i =0;
    EditText noImage;


    private void selectImage() {

        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(RequestActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo"))
                {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File f = new File(android.os.Environment.getExternalStorageDirectory(), "temp.jpg");
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                    startActivityForResult(intent, 1);
                }
                else if (options[item].equals("Choose from Gallery"))
                {
                    Intent intent = new   Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 2);

                }
                else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                File f = new File(Environment.getExternalStorageDirectory().toString());
                for (File temp : f.listFiles()) {
                    if (temp.getName().equals("temp.jpg")) {
                        f = temp;
                        break;
                    }
                }
                try {


                    Bitmap bitmap;
                    BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();

                    bitmap = BitmapFactory.decodeFile(f.getAbsolutePath(),
                            bitmapOptions);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos); //bm is the bitmap object
                    byte[] b = baos.toByteArray();
                    String encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
                    Log.i("image",encodedImage);
                    request.addImage(encodedImage);
//                    Log.i("requeeeeeeeeeeeeeeeeee",request.getImages().toString());




                    viewImage.setImageBitmap(bitmap);

                    String path = android.os.Environment
                            .getExternalStorageDirectory()
                            + File.separator
                            + "Phoenix" + File.separator + "default";
                    f.delete();
                    OutputStream outFile = null;
                    File file = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg");
                    try {
                        outFile = new FileOutputStream(file);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 85, outFile);
                        outFile.flush();
                        outFile.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else if (requestCode == 2) {

                Uri selectedImage = data.getData();
                String[] filePath = { MediaStore.Images.Media.DATA };
                Cursor c = getContentResolver().query(selectedImage, filePath, null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePath[0]);
                String picturePath = c.getString(columnIndex);
                c.close();
                Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                thumbnail.compress(Bitmap.CompressFormat.JPEG, 100, baos); //bm is the bitmap object
                byte[] b = baos.toByteArray();
                String encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
                Log.i("image",encodedImage);
                request.addImage(encodedImage);
//                Log.i("requeeeeeeeeeeeeeeeeee",request.getImages().toString());


                //Log.w("path of image from gallery......******************.........", picturePath+"");
                viewImage.setImageBitmap(thumbnail);

            }

        }
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);

        request= new Request();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        firstName = (EditText)findViewById(R.id.firstName) ;
        date = (EditText)findViewById(R.id.date);
        noImage=(EditText)findViewById(R.id.noImage);
        final RadioGroup g=(RadioGroup) findViewById(R.id.radioGroup1);

        final String firstNameString = firstName.getText().toString();
        String dateString = date.getText().toString();
        Character gender=null;
       // if (male.isPressed()){ gender ='m';}
        //else if (female.isPressed()){gender='f';}

        Intent intent = getIntent();
        final String foundOrMiss = intent.getStringExtra("fabValue");
        final String username = intent.getStringExtra("username");

        bPhoto=(ImageView) findViewById(R.id.bPhoto);
        viewImage=(ImageView)findViewById(R.id.viewImage);
        int selectedId = g.getCheckedRadioButtonId();
        if(selectedId==1){gender='m';}
        else{gender='f';}
        ////Log.i("GGGGGGGGGGGGG" , String.valueOf(selectedId));

        request.setUserName(username);
        request.setDate(dateString);
        request.setFirstName(firstNameString);
        request.setGender(gender);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);






        bPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });



        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //  Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //        .setAction("Action", null).show();
                List<String> now = request.getImages();
                int nnow = now.size();

                int j=request.getImages().size();
                Log.i("3adad el swar", now.toString());


                if (!isOnline()) {
                    Toast.makeText(getBaseContext(), "Please check the internet connection!", Toast.LENGTH_SHORT).show();

                } else {
                   if (!validatefirstName(firstName.getText().toString())) {
                        firstName.setError("First name should consist of 1 word of only letters");
                        i = 0;

                    } else {
                        i = i + 1;
                    }


                    if (!validatedate(date.getText().toString())) {
                        date.setError("date format should be: 2017-7-11");
                        i = 0;

                    } else {
                        i = i + 1;
                    }

                    if (j<1) {
                        noImage.setError("Please upload at least 1 photo!");

                        //Toast.makeText(getBaseContext(), "Please upload at least 1 photo!", Toast.LENGTH_SHORT).show();

                    } else {
                        i = i + 1;
                    }

                    if (i == 3) {
                        if (foundOrMiss.equals("f")) {
                            RequestActivity.HttpAsyncTask httpAsyncTask = new RequestActivity.HttpAsyncTask();
                            httpAsyncTask.execute("http://alexmorsi.pythonanywhere.com/api/users/find_request/");
                            i = 0;
                        } else if (foundOrMiss.equals("m")) {
                            RequestActivity.HttpAsyncTask httpAsyncTask = new RequestActivity.HttpAsyncTask();
                            httpAsyncTask.execute("http://alexmorsi.pythonanywhere.com/api/users/miss_request/");
                            i = 0;
                        }

                    }
                }

            }
           });
         }


    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }


    //Return true if UserName is valid and false if UserName is invalid
    protected boolean validatefirstName(String FirtstName) {
        String FirstPattern = "[a-zA-Z]+";
        Pattern pattern = Pattern.compile(FirstPattern);
        Matcher matcher = pattern.matcher(FirtstName);

        return matcher.matches();
    }


    protected boolean validatedate(String date) {
//        Matcher matcher;
        String DATE_PATTERN =
                "((19|20)\\d\\d)[/-](0?[1-9]|1[012])[/-](0?[1-9]|[12][0-9]|3[01])";
//        Pattern pattern;
        Pattern pattern = Pattern.compile(DATE_PATTERN);
        Matcher matcher = pattern.matcher(date);

//        matcher = Pattern.compile(DATE_PATTERN).matcher(Birthday);

        if(matcher.matches()){
            matcher.reset();

            if(matcher.find()){
                String day = matcher.group(3);
                String month = matcher.group(2);
                int year = Integer.parseInt(matcher.group(1));

                if (day.equals("31") &&
                        (month.equals("4") || month .equals("6") || month.equals("9") ||
                                month.equals("11") || month.equals("04") || month .equals("06") ||
                                month.equals("09"))) {
                    return false; // only 1,3,5,7,8,10,12 has 31 days
                }

                else if (month.equals("2") || month.equals("02")) {
                    //leap year
                    if(year % 4==0){
                        if(day.equals("30") || day.equals("31")){
                            return false;
                        }
                        else{
                            return true;
                        }
                    }
                    else{
                        if(day.equals("29")||day.equals("30")||day.equals("31")){
                            return false;
                        }
                        else{
                            return true;
                        }
                    }
                }

                else{
                    return true;
                }
            }

            else{
                return false;
            }
        }
        else{
            return false;
        }
    }


    public static String POST(String url, Request request){
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
            jsonObject.accumulate("userName", request.getUserName());
            jsonObject.accumulate("date", request.getDate());
            jsonObject.accumulate("fName", request.getFirstName());
            jsonObject.accumulate("gender", request.getGender());

            int m=request.getImages().size();
//            Log.i("3adad el swarjson", request.getImages().toString());

            jsonObject.accumulate("numberOfImages", request.getImages().size());

            jsonObject.accumulate("image1", request.getImages());



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

        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }

        // 11. return result
        return result;
    }

    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        /* private Context mContext;

         public HttpAsyncTask (Context context){
             mContext = context;
         }
        */
        @Override
        protected  void onPreExecute ()
        {
            request= new Request();
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            firstName = (EditText)findViewById(R.id.firstName) ;
            date = (EditText)findViewById(R.id.date);
            //male=(CheckBox)findViewById(R.id.male);
            //female=(CheckBox)findViewById(R.id.female);

            String firstNameString = firstName.getText().toString();
            String dateString = date.getText().toString();
            Character gender='m';
            //if (male.isPressed()){ gender ='m';}
            //else if (female.isPressed()){gender='f';}






            Intent intent = getIntent();
            final String foundOrMiss = intent.getStringExtra("fabValue");
            final String username = intent.getStringExtra("username");

            bPhoto=(ImageView) findViewById(R.id.bPhoto);
            viewImage=(ImageView)findViewById(R.id.viewImage);
            request.setUserName(username);
            request.setDate(dateString);
            request.setFirstName(firstNameString);
            request.setGender(gender);
            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);


        }
        @Override
        protected String doInBackground(String... urls) {

            return POST(urls[0],request);
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            int z=0;

            Log.i("Response",result);

           try {

                JSONObject jsonobject = new JSONObject(result);

                if(result.contains("already")){
                    Toast.makeText(getBaseContext(),"You have already sent your request, only a request per day!",Toast.LENGTH_SHORT).show();

                   // z=2;

                }
                else {
                    Toast.makeText(getBaseContext(),"You request has been sent successfully!",Toast.LENGTH_SHORT).show();

                    //z=1;
                }

               /* if( z==1)
                {
                Toast.makeText(getBaseContext(),"You request has been sent successfully!",Toast.LENGTH_SHORT).show();
                }

                else if(z==2){
                    Toast.makeText(getBaseContext(),"You have already sent your request, only a request per day!",Toast.LENGTH_SHORT).show();
                }*/


            } catch (JSONException e) {
                e.printStackTrace();
            }




        }
    }
}
