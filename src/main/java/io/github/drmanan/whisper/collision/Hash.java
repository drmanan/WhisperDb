/**
 * <H2> Hash </H2>
 *
 * @author Manan Sharma
 * @version 1
 * @since Saturday, 17 of June, 2023; 06:17:14
 */

package io.github.drmanan.whisper.collision;

public class Hash {

    protected String path;
    protected String ext = ".cube";

    protected static int MAXFILESIZE = 65536;

    private CipherManager cipherManager;

    private static String TAG;

    private static String[] cubes = {"0","1","2","3","4","5","6","7","8","9","A","B","C","D","E","F"};

    public Hash(String path, CipherManager cipherManager) {
        this.path = path;
        this.cipherManager = cipherManager;
    }

    public void storeObject(Object key, Object value){
        // TODO
    }

    public void storeObject(Object key, Object value, Boolean update){
        // TODO
    }

}
