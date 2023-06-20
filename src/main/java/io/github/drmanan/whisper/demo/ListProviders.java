/*
 * Copyright (c) $author 2023.
 */

/**
 * <H2> ListProviders </H2>
 *
 * @author Manan Sharma
 * @version 1
 * @since Monday, 19 of June, 2023; 16:03:14
 */

package io.github.drmanan.whisper.demo;

import java.security.Provider;
import java.security.Security;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ListProviders {
    public static void main(String[] args) {
        List<String> algorithms = Arrays.stream(Security.getProviders())
                .flatMap(provider -> provider.getServices().stream())
                .filter(service -> "Cipher".equals(service.getType()))
                .map(Provider.Service::getAlgorithm)
                .collect(Collectors.toList());

        for (String s: algorithms) {
            System.out.println(s);
        }

        System.out.println("\n\nProviders' services\n");

        for (Provider provider : Security.getProviders()) {
            for (Provider.Service service : provider.getServices()) {
                String algorithm = service.getAlgorithm();
                System.out.println(algorithm);
            }
        }
    }
}
