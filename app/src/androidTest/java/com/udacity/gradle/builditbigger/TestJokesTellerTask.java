package com.udacity.gradle.builditbigger;


import android.support.test.runner.AndroidJUnit4;
import android.text.TextUtils;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.CountDownLatch;

@RunWith(AndroidJUnit4.class)
public class TestJokesTellerTask implements JokesTellerTask.JokesCallback {

    private final CountDownLatch signal = new CountDownLatch(1);

    @Test
    public void checkIsJokeEmpty () throws InterruptedException {
        JokesTellerTask jokesTellerTask = new JokesTellerTask(this);
        jokesTellerTask.execute();
        signal.await();
    }

    @Override
    public void onJokeRelieved(String joke) {
        Assert.assertNotNull(joke);
        Assert.assertFalse(TextUtils.isEmpty(joke));
        signal.countDown();
    }
}
