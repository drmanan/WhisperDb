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

import com.esotericsoftware.minlog.Log;
import io.github.drmanan.whisper.WhisperDb;
import io.github.drmanan.whisper.WhisperFactory;
import io.github.drmanan.whisper.WhisperHash;
import io.github.drmanan.whisper.demo.pojo.User;

import java.util.logging.Logger;

public class Main {

//    static Logger log = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {

        var path = "C:\\Users\\manan\\";
//        path = "D:\\";
        path = path.concat(".whisper");

        Log.info(path);

        String databaseName = "testDb";
        String password = "passw0rd";

        Log.info( "main: Db: "+databaseName);
        Log.info( "main: Password: "+password);

        WhisperDb db = WhisperFactory.openOrCreateDatabase(path, databaseName, password);

        User user = new User();
        user.setUsername("User1");
        user.setEmail("user1@example.com");
        user.setTelephone("+91-9999555511");
        user.setAddress("No Address");

        WhisperHash users = db.openOrCreateHash("Users");

        users.put(user.getUsername(), user);

        Log.info("Key: " + user.getUsername() + " Value: " + users.get(user.getUsername()).toString());
    }
}
