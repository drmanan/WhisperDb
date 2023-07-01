/*
 * Copyright (c) $author 2023.
 */

/**
 * <H2> Log </H2>
 *
 * @author Manan Sharma
 * @version 1
 * @since Tuesday, 27 of June, 2023; 14:44:21
 */

package io.github.drmanan.whisper.util.log;


import static io.github.drmanan.whisper.util.log.ConsoleColors.*;

public class Log {
    private static final boolean debug = true;

    public static void info(String message) {
        if (debug) {
            System.out.println(GREEN + message + GREEN);
        }
    }

    public static void criticalInfo(String message) {
        if (debug) {
            System.out.println(GREEN_BACKGROUND + BLACK + message + BLACK + GREEN_BACKGROUND);
        }
    }

    public static void debug(String message) {
        if (debug) {
            System.out.println(YELLOW + message + YELLOW);
        }
    }

    public static void error(String message) {
        if (debug) {
            System.out.println(RED + message + RED);
        }
    }

    public static void exception(Exception exception) {
        exception(exception.getMessage());
    }

    public static void exception(String message) {
        if (debug) {
            System.out.println(RED_BACKGROUND + message + RED_BACKGROUND);
        }
    }
}
