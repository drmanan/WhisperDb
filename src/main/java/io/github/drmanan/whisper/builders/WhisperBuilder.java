/*
 * Copyright (c) $author 2023.
 */

/**
 * <H2> WhisperBuilder </H2>
 *
 * @author Manan Sharma
 * @version 1
 * @since Friday, 23 of June, 2023; 15:20:02
 */

package io.github.drmanan.whisper.builders;

import io.github.drmanan.whisper.WhisperDb;
import io.github.drmanan.whisper.WhisperFactory;
import io.github.drmanan.whisper.WhisperListener;
import io.github.drmanan.whisper.exceptions.InsufficientInformationException;

public class WhisperBuilder implements Builder {

    String dbStoragePath;
    String dbName;
    String dbPassword;
    WhisperListener dbListener;

    @Override
    public void setPath(String dbStoragePath) {
        this.dbStoragePath = dbStoragePath;
    }

    @Override
    public void setName(String dbName) {
        this.dbName = dbName;
    }

    @Override
    public void setPassword(String dbPassword) {
        this.dbPassword = dbPassword;
    }

    @Override
    public void setListener(WhisperListener dbListener) {
        this.dbListener = dbListener;
    }

    // TODO Temp void
    public void build() {
        if (dbStoragePath == null) {
            throw new InsufficientInformationException("Database path not defined");
        }
        if (dbName == null) {
            throw new InsufficientInformationException("Database name not defined");
        }
        if (dbPassword == null) {
            throw new InsufficientInformationException("Database password not defined");
        }

        WhisperDb db ;

        if (dbListener == null){
//            db = new WhisperDb(dbStoragePath, dbName, dbPassword);
            WhisperFactory.openOrCreateDatabase(dbStoragePath, dbName, dbPassword);
        }else {
            WhisperFactory.openOrCreateDatabase(dbStoragePath, dbName, dbPassword, dbListener);
        }
    }
}
