package model.gamelogic.timer;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

// Manages a countdown timer, notifying observers about remaining time
public class CountdownTimer {
    private final Timer countdownTimer = new Timer();
    private final List<CountdownObserver> observers = new ArrayList<>();

    // Adds an observer to the countdown timer notifications
    public void addListener(CountdownObserver observer) {
        observers.add(observer);
    }


    public void removeListener(CountdownObserver observer) {
        observers.remove(observer);
    }


    private void notifyTimeUpdate(int timeLeft) {
        for (CountdownObserver observer : observers) {
            observer.countdownUpdateTime(timeLeft);
        }
    }


    private void notifyTimerFinish() {
        for (CountdownObserver observer : observers) {
            observer.countdownTimerFinish();
        }
    }

    // Starts the countdown timer and updates the remaining time for each second
    public void startCountdown(int countdownTime) {
        final int[] timeLeft = {countdownTime};
        countdownTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                notifyTimeUpdate(timeLeft[0]);
                if (timeLeft[0] <= 0) {
                    countdownTimer.cancel();
                    notifyTimerFinish();
                }
                timeLeft[0]--;
            }
        }, 0, 1000);
    }
}
