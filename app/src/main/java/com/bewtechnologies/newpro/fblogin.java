package com.bewtechnologies.newpro;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.Arrays;

/**
 * Created by Aman  on 10/4/2015.
 * Note: code for fb login consisting of a button, textview and an imageview.
 * Features: Log in, get name and id, get profile image.
 * Does not: handles logout.
 */
public class fblogin extends AppCompatActivity {

    private Button fbbutton;
    private ImageView fbimage;
    private TextView greet;
    String str_id;


    // Creating Facebook CallbackManager Value
    public static CallbackManager callbackmanager;

    private final String TAG_CANCEL="Cancelled login.";
    private  final String TAG_ERROR="Error logging in.";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // Initialize SDK before setContentView(Layout ID)
        FacebookSdk.sdkInitialize(getApplicationContext());

        setContentView(R.layout.fblogin);

        // Initialize layout button
        fbbutton = (Button) findViewById(R.id.login_button);

        //intialize image
        fbimage = (ImageView) findViewById(R.id.imageView);


        //say hello to mr..?
        greet = (TextView) findViewById(R.id.greetings);

        fbbutton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Call private method
                onFblogin();
            }
        });






    }

    @Override
    protected void onResume() {
        super.onResume();
 }


    // Private method to handle Facebook login and callback
    private void onFblogin() {
        callbackmanager = CallbackManager.Factory.create();

        // Set permissions
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("email", "user_photos", "public_profile"));

        LoginManager.getInstance().registerCallback(callbackmanager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {

                        System.out.println("Success");
                        GraphRequest.newMeRequest(
                                loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(JSONObject json, GraphResponse response) {
                                        if (response.getError() != null) {
                                            // handle error
                                            System.out.println("ERROR");
                                        } else {
                                            System.out.println("Success");
                                            try {

                                                String jsonresult = String.valueOf(json);
                                                System.out.println("JSON Result" + jsonresult);

                                               /* String str_email = json.getString("email");*/
                                                 str_id = json.getString("id");
                                                String str_firstname = json.getString("name");


                                                System.out.println("JSON-->"+ " "+str_firstname);

                                                //greet the user
                                                greet.setText("Welcome, "+ str_firstname);

                                                //start downloading image
                                                new DownloadfbImage(fbimage).execute("https://graph.facebook.com/" + str_id + "/picture?type=large");

                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }

                                }).executeAsync();

                    }

                    @Override
                    public void onCancel() {
                        Log.d(TAG_CANCEL, "On cancel");
                    }

                    @Override
                    public void onError(FacebookException error) {
                        Log.d(TAG_ERROR, error.toString());
                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        callbackmanager.onActivityResult(requestCode, resultCode, data);
    }

    private class DownloadfbImage extends AsyncTask<String,Void,Bitmap>
    {
        ImageView image;

        public DownloadfbImage(ImageView fbimage) {

            this.image=fbimage;

        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            Toast.makeText(getApplicationContext(),"image received",Toast.LENGTH_SHORT).show();
            image.setImageBitmap(bitmap);
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            String url = params[0];
            Bitmap returnbitmap=null;
            try {
                URL rurl= new URL(url);
                returnbitmap = BitmapFactory.decodeStream(rurl.openConnection().getInputStream());


            } catch (Exception e) {
                e.printStackTrace();
            }


            return returnbitmap;
        }
    }
}
