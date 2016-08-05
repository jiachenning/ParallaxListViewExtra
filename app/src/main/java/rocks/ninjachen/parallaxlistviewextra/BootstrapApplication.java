package rocks.ninjachen.parallaxlistviewextra;

import android.app.Application;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by ninja on 8/3/16.
 */
public class BootstrapApplication extends Application {
    // timer for mark application is background
    private Timer mActivityTransitionTimer;
    private TimerTask mActivityTransitionTimerTask;
    public boolean wasInBackground;
    private final long MAX_ACTIVITY_TRANSITION_TIME_MS = 2000;
    private static int ACTIVE_ACTIVITY_COUNT=0;

    private static BootstrapApplication instance;

    /**
     * Create main application
     */
    public BootstrapApplication() {
    }


    @Override
    public void onCreate() {
        super.onCreate();

//        init();

        instance = this;

//        onAfterInjection();

    }
//    protected abstract void onAfterInjection();
//
//    protected abstract void init();

    public static BootstrapApplication getInstance() {
        return instance;
    }

    public void startActivityTransitionTimer() {
        this.mActivityTransitionTimer = new Timer();
        this.mActivityTransitionTimerTask = new TimerTask() {
            public void run() {
                BootstrapApplication.this.wasInBackground = true;
            }
        };

        this.mActivityTransitionTimer.schedule(mActivityTransitionTimerTask,
                MAX_ACTIVITY_TRANSITION_TIME_MS);
    }

    public void stopActivityTransitionTimer() {
        if (this.mActivityTransitionTimerTask != null) {
            this.mActivityTransitionTimerTask.cancel();
        }

        if (this.mActivityTransitionTimer != null) {
            this.mActivityTransitionTimer.cancel();
        }

        this.wasInBackground = false;
    }
}