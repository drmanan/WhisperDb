/*
 * Copyright (c) $author 2023.
 */

/**
 * <H2> WhisperHash </H2>
 *
 * @author Manan Sharma
 * @version 1
 * @since Monday, 19 of June, 2023; 15:06:01
 */

package io.github.drmanan.whisper;

import io.github.drmanan.whisper.collision.CipherManager;
import io.github.drmanan.whisper.collision.Hash;
import io.github.drmanan.whisper.exceptions.KeyNotFoundException;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.HashMap;
import java.util.List;

public class WhisperHash extends DbObservable {
    private String path;
    private Hash hash;

    public WhisperHash() {
    }

    public WhisperHash(CipherManager cipherManager, String path) {
        this.path = path;
        this.hash = new Hash(path,cipherManager);
    }

    public Boolean put(Object key, Object value) {
        try {
            hash.updateObject(key, value);
            notifyObservers();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public <T> T get(Object key) {
        try {
            return (T) hash.retrieveObject(key);
        } catch (KeyNotFoundException k) {
            k.printStackTrace();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public <T> List<T> getAllKeys() {
        try {
            return hash.getAllKeys(path);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public <T> List<T> getAllValues() {
        try {
            return hash.getAllValues(path);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public <K, V> HashMap<K, V> getAllData() {
        try {
            return hash.getAllData(path);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean remove(Object key) {
        try {
            Object obj = hash.removeObject(key);
            if (obj != null) {
                notifyObservers();
                return true;
            }
            return false;
        } catch (KeyNotFoundException k) {
            k.printStackTrace();
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void flush() {
        try {
            File currentDir = new File(path);
            for (File f : currentDir.listFiles()) {
                if (f.isDirectory())
                    FileUtils.deleteDirectory(f);
                else
                    FileUtils.deleteQuietly(f);
            }

            notifyObservers();
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
}