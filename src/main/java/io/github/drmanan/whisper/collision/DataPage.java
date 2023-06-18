/*
 * Copyright (c) $author 2023.
 */

/**
 * <H2> DataPage </H2>
 *
 * @author Manan Sharma
 * @version 1
 * @since Sunday, 18 of June, 2023; 16:00:15
 */

package io.github.drmanan.whisper.collision;

public class DataPage {
    private byte[] iv;
    private byte[] data;

    public DataPage() {
    }

    public byte[] getIv() {
        return iv;
    }

    public void setIv(byte[] iv) {
        this.iv = iv;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}
