package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.nocom.androidlibirary.JokeActivity;
import com.udacity.gradle.builditbigger.backend.myApi.MyApi;

import java.io.IOException;

public class EndpointsAsyncTask extends AsyncTask<Context, String, String> {
    private static MyApi myApiService = null;
    private Context context;
    String moha ;


    @Override
    protected String doInBackground(Context... params) {

        if(myApiService == null) {  // Only do this once
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    // options for running against local devappserver
                    // - 10.0.2.2 is localhost's IP address in Android emulator
                    // - turn off compression when running against local devappserver
                    .setRootUrl("http://10.0.2.2:8080/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            // end options for devappserver

            myApiService = builder.build();
        }

        context = params[0];
        // String name = params[0].second;

        try {
            moha =  myApiService.sayHi().execute().getData();
            return moha ;
        } catch (IOException e) {
            return e.getMessage();
        }


    }

    @Override
    protected void onPostExecute(String result) {

        //MyBean myBean = new MyBean();
        // MyClass myClass = null;
        // String joke = myBean.getData();

        // String joke = result ;


        Toast.makeText(context,result,Toast.LENGTH_LONG).show();


        Intent intent2 = new Intent(context,JokeActivity.class);
        intent2.putExtra("moha", result);
        Log.i("message",result);
        context.startActivity(intent2);





        //  Intent intent = new Intent(this, ); // destination  libirary activitty

       /* Intent intent = new Intent(context,MainActivity.class);
        intent.putExtra(JokeActivity.JOKE_KEY, joke);
      context.startActivity(intent);
*/
        //     Toast.makeText(context, joke, Toast.LENGTH_LONG).show();






    }
}