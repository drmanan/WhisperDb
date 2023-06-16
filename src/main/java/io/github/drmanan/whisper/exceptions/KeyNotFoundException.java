package io.github.drmanan.whisper.exceptions;

public class KeyNotFoundException extends Exception{
    public KeyNotFoundException() {
        super("No such key found");
    }

    public KeyNotFoundException(String message) {
        super(message);
    }
}
