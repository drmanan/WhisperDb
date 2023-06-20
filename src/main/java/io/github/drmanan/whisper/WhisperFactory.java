package io.github.drmanan.whisper;

import com.esotericsoftware.minlog.Log;
import io.github.drmanan.whisper.collision.CipherManager;
import io.github.drmanan.whisper.collision.KryoStoreUtils;
import io.github.drmanan.whisper.util.Salt;
import io.github.drmanan.whisper.util.Utils;

import java.io.File;

import static io.github.drmanan.whisper.util.Utils.getLineNumber;

public class WhisperFactory {

    private static final String DB_NAME = "data.db";
    private static final String SALT_NAME = "salt";

    /**
     * Open/create a Db instance.
     *
     * @param path     the path for the database folder
     * @param name     name of the database
     * @param password password - set as null if you don't need encryption / for better performances
     * @return Database instance
     */
    public static WhisperDb openOrCreateDatabase(String path, String name, String password) {
        Log.info(getLineNumber() + "WhisperFactory: openOrCreateDatabase");
        if (WhisperFactory.existsDatabase(path, name)) {
            Log.info(getLineNumber() + "WhisperFactory: Db Exists");
            return WhisperFactory.loadDatabase(path, name, password);
        } else {
            Log.info(getLineNumber() + "WhisperFactory: Create new Db");
            return WhisperFactory.createDatabase(path, name, password);
        }
    }

    /**
     * Open/create a Db instance.
     *
     * <p>The operation requires some time, according to device CPU power.</p>
     *
     * @param path     the path for the database folder
     * @param name     name of the database
     * @param password password - set as null if you don't need encryption / for better performances
     * @param listener a WaspListener instance, to get the database when is ready
     * @return Database instance
     * @implNote Will Move to an async approach.
     */
    public static WhisperDb openOrCreateDatabase(final String path, final String name, final String password, final WhisperListener<WhisperDb> listener) {
        WhisperDb db;
        if (WhisperFactory.existsDatabase(path, name)) {
            db = WhisperFactory.loadDatabase(path, name, password);
        } else {
            db = WhisperFactory.createDatabase(path, name, password);
        }

        if (db != null) {
            listener.onDone(db);
        } else {
            listener.onError("error on openOrCreateDatabase");
        }

        return db;
    }

    protected static WhisperDb createDatabase(final String path, final String name, final String password) {
        Log.debug("WhisperFactory: CreateDb");
        if (password != null && !Utils.checkForCryptoAvailable()) {
            Log.debug(getLineNumber() + "WhisperFactory: password: " + password);
            return null;
        }
        Salt salt = Utils.generateSalt();
        Log.debug("WhisperFactory: Salt generated: " + salt);
        WhisperDb db = new WhisperDb();
        Log.debug("WhisperFactory: New Db");
        db.setName(name);
        db.setPath(path);
        Log.debug("WhisperFactory: Db Name: " + name);
        Log.debug("WhisperFactory: Db Path: " + path);
        try {
            CipherManager cipherManager = null;
            if (!Utils.isEmpty(password)) {
                Log.debug("WhisperFactory: Password is provided, setting cipherManager");
                cipherManager = CipherManager.getInstance(password.toCharArray(), salt.getSalt());
            }

            if (storeDatabase(db, cipherManager)) {
                Log.debug("WhisperFactory: Storing Db");
                KryoStoreUtils.serializeToDisk(salt, db.getPath() + "\\" + db.getName() + "\\" + SALT_NAME, null);
                db.setCipherManager(cipherManager);
                return db;
            } else return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    protected static boolean existsDatabase(String path, String name) {
        Log.info(getLineNumber() + "WhisperFactory: existsDatabase");
        try {
            WhisperDb db = new WhisperDb();
            db.setPath(path);
            db.setName(name);
            String directory;
            directory = db.getPath() + "\\" + db.getName();
            Log.info(getLineNumber() + "WhisperFactory: existsDatabase: trying if Db Exists at " + directory);
            boolean doesFileExist = new File(directory).exists();
            Log.info(getLineNumber() + "WhisperFactory: existsDatabase: doesFileExist: " + doesFileExist);
            return doesFileExist;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean destroyDatabase(WhisperDb db) {
        try {
            String directory = db.getPath() + "\\" + Utils.md5(db.getName()) + "\\";
            if (new File(directory).exists()) { // if exists
                // delete recursively
                try {
                    Utils.recursiveDelete(new File(directory));
                    return true;
                } catch (Exception e) {
                    return false;
                }
            } else {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    protected static boolean storeDatabase(WhisperDb db, CipherManager cipherManager) {
        try {
            String directory = db.getPath() + "\\" + db.getName();
            boolean success = true;
            if (!(new File(directory).exists())) success = (new File(directory)).mkdir();
            if (success) {
                // do not store the cipherManager
                db.clearCipherInformation();
                KryoStoreUtils.serializeToDisk(db, db.getPath() + "\\" + db.getName() + "\\" + DB_NAME, cipherManager);
                db.setCipherManager(cipherManager);

                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    protected static WhisperDb loadDatabase(final String path, final String name, final String password) {
        Log.info("WhisperFactory: Load Db");
        if (password != null && !Utils.checkForCryptoAvailable()) {
            if (password == null) {
                Log.info("WhisperFactory: Load Db Password is null");
            }

            return null;
        }
        CipherManager cipherManager = null;
        try {
            String realname = Utils.md5(name);
            WhisperDb db;

            Salt salt = (Salt) KryoStoreUtils.readFromDisk(path + "\\" + realname + "\\" + SALT_NAME, Salt.class, null);
            if (!Utils.isEmpty(password))
                cipherManager = CipherManager.getInstance(password.toCharArray(), salt.getSalt());
            db = (WhisperDb) KryoStoreUtils.readFromDisk(path + "\\" + realname + "\\" + DB_NAME, WhisperDb.class, cipherManager);
            db.setPath(path);
            db.setCipherManager(cipherManager);
            return db;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}
