/*
 * Copyright (c) $author 2023.
 */

/**
 * <H2> DbListner </H2>
 *
 * @author Manan Sharma
 * @version 1
 * @since Sunday, 18 of June, 2023; 14:34:49
 */

package io.github.drmanan.whisper;

public abstract class WhisperListener<T> {
    abstract public void onDone(T databaseInstance);

    public void onError(String error) {
        System.out.println("DB Error" + error);
    }
}
