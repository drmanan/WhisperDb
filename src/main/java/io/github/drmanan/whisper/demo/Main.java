/*
 * Copyright (c) $author 2023.
 */

/**
 * <H2> Main </H2>
 *
 * @author Manan Sharma
 * @version 1
 * @since Monday, 19 of June, 2023; 09:24:13
 */

package io.github.drmanan.whisper.demo;

import io.github.drmanan.whisper.WhisperDb;
import io.github.drmanan.whisper.WhisperFactory;
import io.github.drmanan.whisper.WhisperHash;
import io.github.drmanan.whisper.WhisperListener;
import io.github.drmanan.whisper.demo.pojo.User;
import io.github.drmanan.whisper.util.log.Log;

import static io.github.drmanan.whisper.util.Utils.getLineNumber;

public class Main {

    // static WhisperDb db;

    public static void main(String[] args) {

        var path = "C:\\Users\\manan\\";
        // path = "D:\\";
        path = path.concat(".whisper");

        Log.info(path);

        String databaseName = "testDb";
        String password = "passw0rd";

        Log.info(getLineNumber() + " main: Db: " + databaseName);
        Log.info(getLineNumber() + " main: Password: " + password);

        /*WhisperFactory.openOrCreateDatabase(path, databaseName, password, new WhisperListener<WhisperDb>() {
            @Override
            public void onDone(WhisperDb whisperDb) {

                Log.info("main: WhisperListener: onDone");

                db = whisperDb;

                User user = new User();
                user.setUsername("User1");
                user.setEmail("user1@example.com");
                user.setTelephone("+91-9999555511");
                user.setAddress("No Address");

                Log.debug("main: WhisperListener: onDone: User: " + user);

                if (db == null) {
                    Log.info("main: WhisperListener: onDone: Db is null");
                } else {
                    Log.info("main: WhisperListener: onDone: Db is not null");
                    Log.info("main: WhisperListener: onDone: Db is " + db);
                }
                WhisperHash users = db.openOrCreateHash("Users");

                users.put(user.getUsername(), user);

                Log.info("Key: " + user.getUsername() + " Value: " + users.get(user.getUsername()).toString());
            }
        });*/

        /*
        WhisperDb db = WhisperFactory.openOrCreateDatabase(path, databaseName, password, new WhisperListener<WhisperDb>() {
            @Override
            public void onDone(WhisperDb ret) {
                User user = new User();
                user.setUsername("User1");
                user.setEmail("user1@example.com");
                user.setTelephone("+91-9999555511");
                user.setAddress("No Address");

                Log.debug("main: User: " + user);

                if (db == null) {
                    Log.debug("main: Db is null");
                } else {
                    Log.debug("main: Db is not null");
                    Log.debug("main: Db is " + db);
                }

                try {
                    Thread.sleep(1000);
                    Log.debug("main: After sleep");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                WhisperHash users = db.openOrCreateHash("Users");

                users.put(user.getUsername(/), user);

                Log.info("Key: " + user.getUsername() + " Value: " + users.get(user.getUsername()).toString());
            }
        });
        */

        // Log.criticalInfo("main: Is db available? " + db);
    }
}
