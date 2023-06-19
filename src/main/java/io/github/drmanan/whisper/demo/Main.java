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
import io.github.drmanan.whisper.collision.Hash;
import io.github.drmanan.whisper.demo.pojo.User;

import java.security.Provider;
import java.security.Security;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        var path = "C:\\Users\\manan\\";
        path = path.concat(".whisper");

        String databaseName = "testDb";
        String password = "passw0rd";

        List<String> algorithms = Arrays.stream(Security.getProviders())
                .flatMap(provider -> provider.getServices().stream())
                .filter(service -> "Cipher".equals(service.getType()))
                .map(Provider.Service::getAlgorithm)
                .collect(Collectors.toList());

        for (String s: algorithms) {
            System.out.println(s);
        }

        System.out.println("\n\nProviders\n");

        for (Provider provider : Security.getProviders()) {
            for (Provider.Service service : provider.getServices()) {
                String algorithm = service.getAlgorithm();
                System.out.println(algorithm);
            }
        }

       /* WhisperDb db = WhisperFactory.openOrCreateDatabase(path,databaseName,password);

        User user = new User();
        user.setUsername("User1");
        user.setEmail("user1@example.com");
        user.setTelephone("+91-9999555511");
        user.setAddress("No Address");

        WhisperHash users = db.openOrCreateHash("Users");

        users.put(user.getUsername(), user);

        System.out.println("Key: "+user.getUsername()+" Value: "+ users.get(user.getUsername()).toString());
*/
    }
}
