/**
 * <H2> Salt </H2>
 *
 * @author Manan Sharma
 * @version 1
 * @since Saturday, 17 of June, 2023; 06:47:13
 *
 */


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
