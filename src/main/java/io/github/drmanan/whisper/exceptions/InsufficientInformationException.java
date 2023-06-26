/*
 * Copyright (c) $author 2023.
 */

/**
 * <H2> InsufficientInformation </H2>
 *
 * @author Manan Sharma
 * @version 1
 * @since Friday, 23 of June, 2023; 15:28:25
 */

package io.github.drmanan.whisper.exceptions;

import com.esotericsoftware.minlog.Log;

public class InsufficientInformationException extends UnsupportedOperationException{
    public InsufficientInformationException() {
        super("Minimum params to initialise database are not provided.");
    }

    public InsufficientInformationException(String message) {
        this();
        Log.error(message);
    }
}
