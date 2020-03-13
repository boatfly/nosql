package com.boatfly.codehup.nosql.kvdb.leveldb;

import org.iq80.leveldb.DB;
import org.iq80.leveldb.Options;

import java.io.File;
import java.io.IOException;

import static org.fusesource.leveldbjni.JniDBFactory.factory;

public class LevelDBUseage {
    public static void main(String[] args) throws IOException {
        Options options = new Options();
        options.createIfMissing(true);
        DB db = factory.open(new File("/Users/song/gitee/leveldb/data/test"), options);
        try {
            for (int i = 0; i < 1000000; i++) {
                byte[] key = new String("key" + i).getBytes();
                byte[] value = new String("value" + i).getBytes();
                db.put(key, value);
            }
            for (int i = 0; i < 1000000; i++) {
                byte[] key = new String("key" + i).getBytes();
                byte[] value = db.get(key);
                String targetValue = "value" + i;
                if (!new String(value).equals(targetValue)) {
                    System.out.println("something wrong!");
                }
            }
            for (int i = 0; i < 1000000; i++) {
                byte[] key = new String("key" + i).getBytes();
                db.delete(key);
            }
        } finally {
            db.close();
        }
    }
}
