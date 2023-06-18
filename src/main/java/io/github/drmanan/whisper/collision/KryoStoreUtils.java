/*
 * Copyright (c) $author 2023.
 */

/**
 * <H2> KryoStoreUtils </H2>
 *
 * @author Manan Sharma
 * @version 1
 * @since Sunday, 18 of June, 2023; 15:55:20
 */

package io.github.drmanan.whisper.collision;


import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import javax.crypto.Cipher;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class KryoStoreUtils {
    private static Kryo kryoInstance;

    private static Kryo getKryoInstance() {
        if(kryoInstance==null) {
            kryoInstance = new Kryo();
            kryoInstance.register(DataPage.class);
        }
        return kryoInstance;
    }

    public static void serializeToDisk(Object obj, String filename, CipherManager cipherManager) throws Exception {
        try {
            //Long start = System.currentTimeMillis();
            //Log.d(TAG,start+": starting serializeToDisk with password");

            Output output = new Output(new FileOutputStream(filename));
            DataPage dataPage = new DataPage();
            if(cipherManager!=null) {
                Cipher cipher = cipherManager.getEncCipher();
                dataPage.setIv(cipher.getIV());
                dataPage.setData(cipher.doFinal(serialize(obj)));
            } else {
                dataPage.setData(serialize(obj));
            }
            getKryoInstance().writeObject(output, dataPage);
            output.close();

            //Long end = System.currentTimeMillis();
            //Log.d(TAG,end+": starting serializeToDisk with password");
            //Log.d(TAG,"total time (ms): "+(end-start));
        }
        catch(Exception e) {
            e.printStackTrace();
            throw new Exception("\nERROR on serializeToDisk:"+e.getMessage());
        }
    }

    public static Object readFromDisk(String filename, Class type, CipherManager cipherManager) throws Exception {
        try {
            //Long start = System.currentTimeMillis();
            //Log.d(TAG,start+": starting readFromDisk with password");

            File f = new File(filename);
            Object hash;
            if(f.exists()) {
                Input input = new Input(new FileInputStream(f));
                DataPage dataPage = getKryoInstance().readObject(input, DataPage.class);
                if(cipherManager!=null) {
                    Cipher decipher = cipherManager.getDecCipher(dataPage.getIv());
                    hash = unserialize(decipher.doFinal(dataPage.getData()),type);
                }
                else {
                    hash = unserialize(dataPage.getData(),type);
                }
                input.close();

                return hash;
            }
            else {
                throw new Exception("\nERROR on readFromDisk: can't find "+filename);
            }
        }
        catch(Exception e) {
            e.printStackTrace();
            throw new Exception("\nERROR on readFromDisk:"+e.getMessage());
        }
    }

    // serializer for keys
    public static byte[] serialize(Object o) {
        byte[] ret = new byte[Hash.MAXFILESIZE*2];
        Output output = new Output(ret);
        getKryoInstance().writeObject(output, o);
        return output.toBytes();
    }

    public static Object unserialize(byte[] buffer, Class type) {
        Input input = new Input(buffer);
        return getKryoInstance().readObject(input, type);
    }
}
