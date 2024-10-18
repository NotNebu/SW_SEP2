package game;

import model.gamelogic.timer.GameTimer;
import model.gamelogic.timer.TimerObserver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class TestTimer {

    private GameTimer timer;
    private TimerObserver observer;

    @BeforeEach
    public void setUp() {
        timer = new GameTimer();
        observer = Mockito.mock(TimerObserver.class);
        timer.addListener(observer);
    }

    // Test if the timer notifies the observer when the time is updated
    @Test
    public void testNotifyOnTimeUpdate() {
        timer.startTimer();
        Mockito.verify(observer, Mockito.times(1)).updateTime(40);
    }



}
