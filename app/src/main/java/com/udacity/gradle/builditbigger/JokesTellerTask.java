package com.udacity.gradle.builditbigger;

import android.os.AsyncTask;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.udacity.jokesbackend.jokeApi.JokeApi;

import java.io.IOException;

public class JokesTellerTask extends AsyncTask<Void, Void, String> {

    public interface JokesCallback {
        void onJokeRelieved(String joke);
    }

    private JokesCallback mJokesCallback;
    private static JokeApi sJokeApiService = null;

    public JokesTellerTask(JokesCallback jokesCallback) {
        mJokesCallback = jokesCallback;
    }

    @Override
    protected String doInBackground(Void... params) {
        if (sJokeApiService == null) {
            // i'm using Genymotion so the local host is at 10.0.3.2
            //TODO: change it to your local host
            JokeApi.Builder builder = new JokeApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    .setRootUrl("http://10.0.3.2:8080/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            sJokeApiService = builder.build();
        }

        try {
            return sJokeApiService.getJoke().execute().getJoke();
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String joke) {
        mJokesCallback.onJokeRelieved(joke);
    }
}
