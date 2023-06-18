package io.github.drmanan.whisper;

import io.github.drmanan.whisper.collision.CipherManager;
import io.github.drmanan.whisper.collision.KryoStoreUtils;
import io.github.drmanan.whisper.util.Salt;
import io.github.drmanan.whisper.util.Utils;

import java.io.File;

public class WhisperFactory {

    private static final String DB_NAME = "data.db";
    private static final String SALT_NAME = "salt";

    public static void openOrCreateDatabase(final String path, final String name, final String password,
                                            final WhisperListener<WhisperDb> listener){
        // TODO
    }

    protected static boolean existsDatabase(String path, String name) {
        try {
            WhisperDb db = new WhisperDb();
            db.setPath(path);
            db.setName(name);
            String directory;
            directory = db.getPath()+"/"+db.getName();

            return (new File(directory).exists());
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean destroyDatabase(WhisperDb db){
        try {
            String directory = db.getPath()+"/"+ Utils.md5(db.getName())+"/";
            if(new File(directory).exists()) { // if exists
                // delete recursively
                try {
                    Utils.recursiveDelete(new File(directory));
                    return true;
                }
                catch(Exception e) {
                    return false;
                }
            } else {
                return true;
            }
        } catch(Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    protected static boolean storeDatabase(WhisperDb db, CipherManager cipherManager)  {
        try {
            String directory = db.getPath()+"/"+db.getName();
            boolean success = true;
            if(!(new File(directory).exists())) success = (new File(directory)).mkdir();
            if(success) {
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
        if(password!=null && !Utils.checkForCryptoAvailable()) return null;
        CipherManager cipherManager = null;
        try {
            String realname = Utils.md5(name);
            WhisperDb db;

            Salt salt = (Salt) KryoStoreUtils.readFromDisk(path + "/" + realname +"/"+SALT_NAME,Salt.class, null);
            if(!Utils.isEmpty(password))
                cipherManager = CipherManager.getInstance(password.toCharArray(),salt.getSalt());
            db = (WhisperDb) KryoStoreUtils.readFromDisk(path + "/" + realname + "/" + DB_NAME, WhisperDb.class, cipherManager);

            db.setPath(path); // refresh the path to the current one
            db.setCipherManager(cipherManager); // set the password in the object
            return db;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}
