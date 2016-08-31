package com.guoxiaoxing.toolkit.other;

/**
 * Author: guoxiaoxing
 * Date: 16/8/31 下午4:51
 * Function: For more information, you can visit https://github.com/guoxiaoxing or contact me by
 * guoxiaoxingv@163.com
 */
public class Singleton {

    private volatile static Singleton singleton;

    private Singleton() {
    }

    public static Singleton getInstance() {
        if (singleton == null) {
            synchronized (Singleton.class) {
                if (singleton == null) {
                    singleton = new Singleton();
                }
            }
        }
        return singleton;
    }
}  