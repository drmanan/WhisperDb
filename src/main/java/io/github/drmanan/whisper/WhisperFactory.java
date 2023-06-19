package io.github.drmanan.whisper;

import io.github.drmanan.whisper.collision.CipherManager;
import io.github.drmanan.whisper.collision.KryoStoreUtils;
import io.github.drmanan.whisper.util.Salt;
import io.github.drmanan.whisper.util.Utils;

import java.io.File;

public class WhisperFactory {

    private static final String DB_NAME = "data.db";
    private static final String SALT_NAME = "salt";

    public static WhisperDb openOrCreateDatabase(String path, String name, String password) {
        if (WhisperFactory.existsDatabase(path, name)) {
            return WhisperFactory.loadDatabase(path, name, password);
        } else {
            return WhisperFactory.createDatabase(path, name, password);
        }
    }

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
        if (password != null && !Utils.checkForCryptoAvailable()) return null;
        Salt salt = Utils.generateSalt();
        WhisperDb db = new WhisperDb();
        db.setName(name);
        db.setPath(path);
        try {
            CipherManager cipherManager = null;
            if (!Utils.isEmpty(password)) {
                cipherManager = CipherManager.getInstance(password.toCharArray(), salt.getSalt());
            }

            if (storeDatabase(db, cipherManager)) {
                KryoStoreUtils.serializeToDisk(salt, db.getPath() + "/" + db.getName() + "/" + SALT_NAME, null);
                db.setCipherManager(cipherManager);
                return db;
            } else return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    protected static boolean existsDatabase(String path, String name) {
        try {
            WhisperDb db = new WhisperDb();
            db.setPath(path);
            db.setName(name);
            String directory;
            directory = db.getPath() + "/" + db.getName();

            return (new File(directory).exists());
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean destroyDatabase(WhisperDb db) {
        try {
            String directory = db.getPath() + "/" + Utils.md5(db.getName()) + "/";
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
            String directory = db.getPath() + "/" + db.getName();
            boolean success = true;
            if (!(new File(directory).exists())) success = (new File(directory)).mkdir();
            if (success) {
                // do not store the cipherManager
                db.clearCipherInformation();
                KryoStoreUtils.serializeToDisk(db, db.getPath() + "/" + db.getName() + "/" + DB_NAME, cipherManager);
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
        if (password != null && !Utils.checkForCryptoAvailable()) return null;
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
