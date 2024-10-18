package model.gamelogic.timer;

// Observer interface for listening to game timer updates
public interface TimerObserver {
    void updateTime(int remainingSeconds);
    void timerFinish();
}
