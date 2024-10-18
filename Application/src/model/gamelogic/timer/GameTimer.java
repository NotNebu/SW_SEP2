package model.gamelogic.timer;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

// A game-specific countdown timer that manages and broadcasts time updates
public class GameTimer extends TimerTask {

    private final int TICK = 1000;
    // Indicates whether the timer is currently running
    private boolean running;

    private int timeLeft;

    private final int totalTimerTime = 40 * TICK;

    private List<TimerObserver> timerObservers;

    private Timer timer;


    public GameTimer() {
        timerObservers = new ArrayList<>();
        timeLeft = totalTimerTime;
    }

    // Starts the timer if it is not already running
    public void startTimer() {
        if (!running) {
            timer = new Timer();
            timer.scheduleAtFixedRate(this, 0, TICK);
            running = true;
        }
    }


    public int getTimeLeft() {
        return timeLeft / TICK;
    }


    public int getTotalTimerTimeInSeconds() {
        return totalTimerTime / TICK;
    }


    public boolean isRunning() {
        return running;
    }

    // Adds a TimerObserver to receive timer updates
    public void addListener(TimerObserver listener) {
        this.timerObservers.add(listener);
    }


    public void removeListener(TimerObserver listener) {
        this.timerObservers.remove(listener);
    }


    public void broadcastTimeLeft() {
        for (TimerObserver observer : timerObservers) {
            observer.updateTime(timeLeft / TICK);
        }
    }

    // Notifies all registered observers when the timer finishes
    public void broadcastTimerFinish() {
        for (TimerObserver observer : timerObservers) {
            observer.timerFinish();
        }
    }


    @Override
    public void run() {
        broadcastTimeLeft();
        if (timeLeft <= 0) {
            timer.cancel();
            broadcastTimerFinish();
        }
        timeLeft -= TICK;
    }
}
