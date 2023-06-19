/**
 * <H2> Hash </H2>
 *
 * @author Manan Sharma
 * @version 1
 * @since Saturday, 17 of June, 2023; 06:17:14
 */

package io.github.drmanan.whisper.collision;

import io.github.drmanan.whisper.exceptions.KeyAlreadyExistsException;
import io.github.drmanan.whisper.exceptions.KeyNotFoundException;
import io.github.drmanan.whisper.util.Utils;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static io.github.drmanan.whisper.util.Utils.recursiveDelete;

public class Hash {

    private static final String[] cubes = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F"};
    protected static int MAXFILESIZE = 65536;
    private static String TAG;
    private final CipherManager cipherManager;
    protected String path;
    protected String ext = ".cube";

    public Hash(String path, CipherManager cipherManager) {
        this.path = path;
        this.cipherManager = cipherManager;
    }

    public void storeHashByKey(HashMap hash, String searchKey) throws Exception {
        String file = getFileFromSearchKey(searchKey);
        KryoStoreUtils.serializeToDisk(hash, file, cipherManager);
    }

    public Object retrieveObject(Object key) throws Exception {
        return retrieveObject(key, false);
    }

    public Object removeObject(Object key) throws Exception {
        return retrieveObject(key, true);
    }

    public void storeObject(Object key, Object value) throws Exception {
        storeObject(key, value, false);
    }

    public void updateObject(Object key, Object value) throws Exception {
        storeObject(key, value, true);
    }

    public String getFileFromSearchKey(String searchKey) {
        String filePath = "";
        for (int i = searchKey.length(); i > 0; i--) {
            filePath = path;
            for (int j = 0; j < i; j++) {
                filePath += "/" + searchKey.charAt(j);
            }
            if (new File(filePath).isDirectory()) {
                String newfile = filePath + "/" + searchKey.charAt(i) + ext;
                return newfile;
            }
            filePath += ext;
            if (new File(filePath).exists()) {
                return filePath;
            }
        }
        return filePath;
    }

    public String getSearchKey(Object key) throws NoSuchAlgorithmException {
        final MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        messageDigest.reset();
        messageDigest.update(KryoStoreUtils.serialize(key));
        final byte[] resultByte = messageDigest.digest();
        final String result = Utils.toHexString(resultByte);
        String searchKey = result.substring(0, 8);
        return searchKey;
    }

    private Object retrieveObject(Object key, boolean remove) throws Exception {
        Object returning = null;
        try {
            String searchKey = getSearchKey(key);
            HashMap hash = getHashFromKey(searchKey);
            returning = hash.get(key);
            if (returning != null) {
                if (remove) {
                    hash.remove(key);
                    storeHashByKey(hash, searchKey);
                }
            } else {
                throw new KeyNotFoundException("key not found for key " + key);
            }
        } catch (Exception e) {
            throw e;
        }
        return returning;
    }


    public void storeObject(Object key, Object value, Boolean update) throws Exception {
        try {
            String searchKey = getSearchKey(key);
            HashMap hash = getHashFromKey(searchKey);

            if (!hash.containsKey(key) || update) {
                hash.put(key, value);
                storeHashByKey(hash, searchKey);
            } else {
                throw new KeyAlreadyExistsException("key already exists for value " + hash.get(key));
            }
        } catch (Exception e) {
            throw e;
        }
    }

    public HashMap getHashFromKey(String searchKey) throws Exception {
        HashMap hash;
        String file = getFileFromSearchKey(searchKey);
        if (!new File(file).exists()) {
            hash = new HashMap();
        } else {
            hash = (HashMap) KryoStoreUtils.readFromDisk(file, HashMap.class, cipherManager);
            long size = FileUtils.sizeOf(new File(file));
            if (size > MAXFILESIZE && hash.size() > 1) {
                explodeCube(file);
                return getHashFromKey(searchKey);
            }
        }
        return hash;
    }

    protected void explodeCube(String file) throws Exception {
        String tmpFile = file.substring(0, file.length() - 6) + "tmp" + ext;
        File file1 = new File(file);
        File file2 = new File(tmpFile);
        boolean success = file1.renameTo(file2);
        String newdirPath = file.substring(0, file.length() - 5);
        File newdir = new File(newdirPath);
        try {
            if (newdir.mkdir()) {
                HashMap hash;
                hash = (HashMap) KryoStoreUtils.readFromDisk(tmpFile, HashMap.class, cipherManager);
                for (Object k : hash.keySet()) {
                    Object o = hash.get(k);
                    storeObject(k, o);
                }
                file2.delete();
            } else {
                throw new Exception();
            }
        } catch (Exception e) {
            e.printStackTrace();
            recursiveDelete(newdir);
            success = file2.renameTo(file1);
            if (success) {
                throw new Exception("Unable to add more data - no more space left?");
            } else throw new Exception("FATAL ON FILESYSTEM DURING ADDING MORE DATA - possible corrupted data!!!");
        }
    }

    public <T> List<T> getAllValues(String path) {
        List<Object> returnable = new ArrayList<>();
        for (String s : cubes) {
            String currentPath = path + "/" + s;
            if (new File(currentPath).isDirectory()) {
                returnable.addAll(getAllValues(currentPath));
            }
            if (new File(currentPath + ext).exists()) {
                HashMap data;
                try {
                    data = (HashMap) KryoStoreUtils.readFromDisk(currentPath + ext, HashMap.class, cipherManager);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                returnable.addAll(data.values());
            }
        }
        return (List<T>) returnable;
    }

    public <T> List<T> getAllKeys(String path) {
        List<Object> returnable = new ArrayList<Object>();

        for (String s : cubes) {
            String currentPath = path + "/" + s;
            if (new File(currentPath).isDirectory()) {
                returnable.addAll(getAllKeys(currentPath));
            }
            if (new File(currentPath + ext).exists()) {
                HashMap data;
                try {
                    data = (HashMap) KryoStoreUtils.readFromDisk(currentPath + ext, HashMap.class, cipherManager);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                returnable.addAll(data.keySet());
            }
        }
        return (List<T>) returnable;
    }

    public HashMap getAllData(String p) throws Exception {
        if (p == null) p = path;
        HashMap returnable = new HashMap();

        for (String s : cubes) {
            String currentPath = p + "/" + s;
            if (new File(currentPath).isDirectory()) {
                returnable.putAll(getAllData(currentPath));
            }
            if (new File(currentPath + ext).exists()) {
                HashMap data;
                data = (HashMap) KryoStoreUtils.readFromDisk(currentPath + ext, HashMap.class, cipherManager);
                returnable.putAll(data);
            }
        }
        return returnable;
    }
}
