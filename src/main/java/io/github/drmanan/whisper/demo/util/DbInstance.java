/*
 * Copyright (c) $author 2023.
 */

/**
 * <H2> DbInstance </H2>
 *
 * @author Manan Sharma
 * @version 1
 * @since Friday, 23 of June, 2023; 14:56:45
 */

package io.github.drmanan.whisper.demo.util;

import com.esotericsoftware.minlog.Log;
import io.github.drmanan.whisper.WhisperDb;

public class DbInstance {
    private static WhisperDb db;

    private DbInstance(){
        Log.info("DbInstance constructor");
    }

    public static WhisperDb getInstance(){
        if (db == null) {
//            TODO
//            db = new WhisperDb();
        }
        return db;
    }
}
