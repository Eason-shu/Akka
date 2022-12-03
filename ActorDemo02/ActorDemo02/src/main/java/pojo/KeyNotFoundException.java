package pojo;

import java.io.Serializable;

/**
 * @description:
 * @author: shu
 * @createDate: 2022/11/28 11:52
 * @version: 1.0
 */
public class KeyNotFoundException extends Exception implements
        Serializable {
    public final String key;
    public KeyNotFoundException(String key) {
        this.key = key;
    }
}
