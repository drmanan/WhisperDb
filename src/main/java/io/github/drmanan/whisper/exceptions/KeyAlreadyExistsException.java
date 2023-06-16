package io.github.drmanan.whisper.exceptions;

public class KeyAlreadyExistsException extends IllegalArgumentException{

    public KeyAlreadyExistsException() {
        super("Key already exists");
    }

    public KeyAlreadyExistsException(String s) {
        super(s);
    }
}
