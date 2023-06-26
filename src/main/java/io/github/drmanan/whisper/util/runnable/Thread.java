/*
 * Copyright (c) $author 2023.
 */

/**
 * <H2> Thread </H2>
 * This abstract class implements the Runnable interface and can be used to notify listeners
 * when the runnable thread has completed. To use this class, first extend it and implement
 * the doRun function - the doRun function is where all work should be performed.
 * Add any listener to update upon completion, then
 * create a new thread with this new object and run.
 * @author Manan Sharma
 * @version 1
 * @since Monday, 26 of June 2023; 23:48:32
 */

package io.github.drmanan.whisper.util.runnable;

import java.util.ArrayList;
import java.util.Collections;

public abstract class Thread implements Runnable {

    /**
     * An abstract function that children must implement. This function is where
     * all work - typically placed in the run of runnable - should be placed.
     */
    public abstract void doWork();

    /**
     * Our list of listeners to be notified upon thread completion.
     */
    private java.util.List<ThreadListener> listeners = Collections.synchronizedList(new ArrayList<ThreadListener>());

    /**
     * Adds a listener to this object.
     *
     * @param listener Adds a new listener to this object.
     */
    public void addListener(ThreadListener listener) {
        listeners.add(listener);
    }

    /**
     * Removes a particular listener from this object, or does nothing if the listener
     * is not registered.
     *
     * @param listener The listener to remove.
     */
    public void removeListener(ThreadListener listener) {
        listeners.remove(listener);
    }

    /**
     * Notifies all listeners that the thread has completed.
     */
    private final void notifyListeners() {
        synchronized (listeners) {
            for (ThreadListener listener : listeners) {
                listener.threadComplete(this);

            }
        }
    }

    /**
     * Implementation of the Runnable interface. This function first calls doRun(), then
     * <p>
     * notifies all listeners of completion.
     */
    public void run() {
        doWork();
        notifyListeners();
    }
}