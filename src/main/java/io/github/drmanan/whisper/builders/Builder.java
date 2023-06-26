/*
 * Copyright (c) $author 2023.
 */

/**
 * Builder
 *
 * @author manan
 * @version 1
 * @since Friday, 23 of June, 2023; 15:16:08
 */

package io.github.drmanan.whisper.builders;

import io.github.drmanan.whisper.WhisperListener;

public interface Builder {
    void setPath(String dbStoragePath);
    void setName(String dbName);
    void setPassword(String dbPassword);
    void setListener(WhisperListener dbListener);
}
