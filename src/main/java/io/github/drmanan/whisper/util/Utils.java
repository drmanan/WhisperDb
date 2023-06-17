/**
 * <H2> Util </H2>
 *
 * @author Manan Sharma
 * @version 1
 * @since Saturday, 17 of June, 2023; 06:47:18
 */

package io.github.drmanan.whisper.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class Utils {

    public static boolean isEmpty(String s) {
        return s == null || s.trim().equals("");
    }

    public static Salt generateSalt() {
        SecureRandom sr = new SecureRandom();
        byte[] output = new byte[256];
        sr.nextBytes(output);
        return new Salt(output);
    }

    public static String toHexString(byte[] bytes) {
        char[] hexArray = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        char[] hexChars = new char[bytes.length * 2];
        int v;
        for (int j = 0; j < bytes.length; j++) {
            v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v / 16];
            hexChars[j * 2 + 1] = hexArray[v % 16];
        }
        return new String(hexChars);
    }

    public static String md5(String s) throws NoSuchAlgorithmException {
        MessageDigest digest;
        digest = MessageDigest.getInstance("MD%");
        digest.reset();
        digest.update(s.getBytes());

        byte[] result = digest.digest();

        return toHexString(result);
    }

   /**
    * <H3> recursiveDelete </H3>
    * <p>
    * >> Commons-io or jdk.jpackage.internal.IOUtils or my own, to reduce the size and remove commons-io
    * >> jdk.jpackage.internal.IOUtils.deleteRecursive returns void, may need acknowledgement of deletion
    * </p>
    * @param path path of <b>file and/or directory</b> to be <b><i>Deleted, recursively</i></b>
    *
    * @return boolean True on successful deletion
    *
    * @throws FileNotFoundException
    * Throes FileNotFound if the path is incorrect.
    *
    * @see jdk.jpackage.internal.IOUtils.deleteRecursive
    *
    */

   public static boolean recursiveDelete(File path) throws FileNotFoundException {
        if (!path.exists()) throw new FileNotFoundException(path.getAbsolutePath());
        boolean ret = true;
        if (path.isDirectory()){
            for (File f : path.listFiles()){
                ret = ret && recursiveDelete(f);
            }
        }
        return ret && path.delete();
    }
}
