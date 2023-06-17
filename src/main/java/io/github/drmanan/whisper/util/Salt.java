package io.github.drmanan.whisper.util;

public class Salt {
    private byte[] salt;

    public Salt() {
    }

    public Salt(byte[] salt) {
        this.salt = salt;
    }

    public byte[] getSalt() {
        return salt;
    }
}
