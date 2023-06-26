/*
 * Copyright (c) $author 2023.
 */

/**
 * ThreadListener
 * <p>
 *  An interface that can be used by the NotificationThread class to notify an
 *  object that a thread has completed.
 * </p>
 * @author manan
 * @version 1
 * @since Monday, 26 of June, 2023; 23:45:05
 */

package io.github.drmanan.whisper.util.runnable;

public interface ThreadListener {

    /**
     * Notifies this object that the Runnable object has completed its work.
     * @param runner The runnable interface whose work has finished.
     */
    public void threadComplete( Runnable runner );
}
