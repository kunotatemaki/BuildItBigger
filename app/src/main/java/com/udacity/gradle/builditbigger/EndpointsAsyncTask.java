package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.AsyncTask;

import com.example.iruler.myapplication.backend.myApi.MyApi;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.rukiasoft.rulerapps.myjokedisplay.JokeDisplayActivity;

import java.io.IOException;

/**
 * Created by iRuler on 25/8/15.
 */
public class EndpointsAsyncTask extends AsyncTask<RefreshActivity, Void, String> {
    public static String ASYNCTASK_NOT_OK = "com.udacity.gradle.builditbigger.endpointasynctask.not_ok";
    private static MyApi myApiService = null;
    private RefreshActivity refreshActivity;
    private EndpointsAsyncTaskListener mListener = null;


    @Override
    protected String doInBackground(RefreshActivity... params) {
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

        refreshActivity = params[0];
        try {
            Thread.sleep(5000); //just to show refresh indicator a few more seconds
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            return myApiService.tellJoke().execute().getJoke();
        } catch (IOException e) {
            return ASYNCTASK_NOT_OK;
        }
    }

    @Override
    protected void onPostExecute(String result) {
        if(this.mListener != null){
            if(result.equals("") || result.equals(ASYNCTASK_NOT_OK)){
                this.mListener.onComplete(ASYNCTASK_NOT_OK);
            }else {
                this.mListener.onComplete(result);
            }
        }
        if(refreshActivity != null) {
            refreshActivity.hideRefreshLayoutSwipeProgress();
            if(result.equals(ASYNCTASK_NOT_OK)){
                result = refreshActivity.getResources().getString(R.string.no_response);
            }
            showJoke(result);

        }
    }

    @Override
    protected void onCancelled(){
        if(this.mListener != null){
            this.mListener.onComplete(ASYNCTASK_NOT_OK);
        }
    }

    public EndpointsAsyncTask setListener(EndpointsAsyncTaskListener listener) {
        this.mListener = listener;
        return this;
    }

    public static interface EndpointsAsyncTaskListener {
        public void onComplete(String result);
    }

    public void showJoke(String result) {
        Intent intent = new Intent(refreshActivity, JokeDisplayActivity.class);
        intent.putExtra(JokeDisplayActivity.JOKE_AS_INTENT, result);
        refreshActivity.startActivity(intent);
    }
}
