package io.github.drmanan.whisper;

import com.esotericsoftware.minlog.Log;
import io.github.drmanan.whisper.collision.CipherManager;
import io.github.drmanan.whisper.util.Utils;

import java.io.File;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class WhisperDb {
    private String dbName; // db name
    private String path; // db path
    private CipherManager cipherManager;

    private List<String> hashes; // all hashes used

    protected WhisperDb() {
        // None
    }

    public WhisperHash openOrCreateHash(String hashName) {

        Log.info("WhisperDb: openOrCreateHash");

        WhisperHash hash;
        try {
            if (existsHash(hashName)) {
                hash = getHash(hashName);
            } else {
                hash = createHash(hashName);
            }
            return hash;
        } catch (Exception wfe) {
            wfe.printStackTrace();
            return null;
        }
    }

    public boolean existsHash(String hashName) {
        try {
            String realname = Utils.md5(hashName);
            String directory = path + "/" + Utils.md5(dbName) + "/" + realname;
            return new File(directory).exists();
        } catch (Exception e) {
            return false;
        }
    }

    protected WhisperHash getHash(String hashName) {
        try {
            String realname = Utils.md5(hashName);
            String directory = path + "/" + Utils.md5(dbName) + "/" + realname;
            if (new File(directory).exists()) { // already exists
                WhisperHash hash = new WhisperHash(cipherManager, directory);
                return hash;
            } else {
                return null;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    protected WhisperHash createHash(String hashName) {
        try {
            String realname = Utils.md5(hashName);
            String directory = path + "/" + Utils.md5(dbName) + "/" + realname;
            if (!new File(directory).exists()) { // if not exists
                // create a new one
                if (new File(directory).mkdir()) {
                    WhisperHash hash = new WhisperHash(cipherManager, directory);

                    if (hashes == null) hashes = new ArrayList<String>();
                    hashes.add(hashName);
                    persist(); // update db data on disk
                    return hash;
                } else {
                    return null;
                }
            } else {
                return getHash(hashName);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean removeHash(String hashName) {
        try {
            String realname = Utils.md5(hashName);
            String directory = path + "/" + Utils.md5(dbName) + "/" + realname;
            if (new File(directory).exists()) { // if exists
                // delete recursively
                try {
                    Utils.recursiveDelete(new File(directory));

                    if (hashes != null) hashes.remove(hashName);
                    persist(); // update db data on disk

                    return true;
                } catch (Exception e) {
                    return false;
                }
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private void persist() {
        WhisperFactory.storeDatabase(this, cipherManager);
    }

    public List<String> getAllHashes() {
        return hashes;
    }

    protected String getName() throws NoSuchAlgorithmException {
        return Utils.md5(dbName);
    }

    protected void setName(String name) {
        this.dbName = name;
    }

    protected String getPath() {
        return path;
    }

    protected void setPath(String path) {
        this.path = path;
    }

    protected CipherManager getCipherManager() {
        return cipherManager;
    }

    protected void setCipherManager(CipherManager cm) {
        this.cipherManager = cm;
    }

    protected void clearCipherInformation() {
        this.cipherManager = null;
    }

    @Override
    public String toString() {
        return "WaspDb [name=" + dbName + ", path=" + path + ", cipher enabled = " + (cipherManager != null) + "]";
    }

}
