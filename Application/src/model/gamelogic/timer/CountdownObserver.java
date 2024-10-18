package model.gamelogic.timer;

// Observer interface for listening to countdown timer updates
public interface CountdownObserver {
    void countdownUpdateTime(int timeLeft);
    void countdownTimerFinish();
}
