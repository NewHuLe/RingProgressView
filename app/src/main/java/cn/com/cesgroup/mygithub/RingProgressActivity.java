package cn.com.cesgroup.mygithub;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Author        Hule  hu.le@cesgroup.com.cn
 * Date          2017/6/12 15:38
 * Description:  TODO: 自定义环形进度条
 */

public class RingProgressActivity extends Activity {

    private RingProgressView ringProgressView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ring_progress);
        ringProgressView = (RingProgressView) findViewById(R.id.ringProgress);
        excuteProgress();
    }

    private void excuteProgress() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        int currentProgress = ringProgressView.getCurrentProgress();
                        if (currentProgress >= 100) {
                            currentProgress = 0;
                        }
                        Thread.sleep(200);
                        currentProgress += 1;
                        ringProgressView.setCurrentProgress(currentProgress);
                        ringProgressView.postInvalidate();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
