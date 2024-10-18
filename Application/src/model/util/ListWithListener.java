package model.util;

import java.util.ArrayList;

// A list implementation that triggers a listener when the size reaches a threshold
public class ListWithListener<T> extends ArrayList<T> {
    private Runnable listener;
    private final int sizeBeforeAction;

    // Initializes the list and sets the size threshold
    public ListWithListener(int sizeBeforeAction) {
        super();
        this.sizeBeforeAction = sizeBeforeAction;
    }

    // Sets a listener to be triggered when the size reaches the threshold
    public void setListener(Runnable listener) {
        this.listener = listener;
    }

    // Adds an item to the list and checks if the size threshold is met to trigger the listener
    @Override
    public boolean add(T item) {
        boolean result = super.add(item);
        if (listener != null && size() >= sizeBeforeAction) {
            listener.run();
        }
        return result;
    }

    // Removes an item from the list without affecting the triggering logic
    @Override
    public boolean remove(Object item) {
        return super.remove(item);
    }
}
