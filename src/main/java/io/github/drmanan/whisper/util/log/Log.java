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

public class Log {

    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";


    private static final boolean debug = true;

    public static void info(String message) {
        if (debug) {
            System.out.println(ANSI_GREEN + message + ANSI_GREEN);
        }
    }

    public static void criticalInfo(String message) {
        if (debug) {
            System.out.println(ANSI_GREEN_BACKGROUND + ANSI_BLACK + message + ANSI_BLACK + ANSI_GREEN_BACKGROUND);
        }
    }

    public static void debug(String message) {
        if (debug) {
            System.out.println(ANSI_YELLOW + message + ANSI_YELLOW);
        }
    }

    public static void error(String message) {
        if (debug) {
            System.out.println(ANSI_RED + message + ANSI_RED);
        }
    }

    public static void exception(Exception exception) {
        exception(exception.getMessage());
    }

    public static void exception(String message) {
        if (debug) {
            System.out.println(ANSI_RED_BACKGROUND + message + ANSI_RED_BACKGROUND);
        }
    }
}
