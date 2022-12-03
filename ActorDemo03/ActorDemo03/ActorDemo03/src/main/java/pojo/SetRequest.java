package pojo;

import java.io.Serializable;

/**
 * @description:
 * @author: shu
 * @createDate: 2022/11/28 11:51
 * @version: 1.0
 */
public class SetRequest implements Serializable {
    public final String key;
    public final Object value;
    public SetRequest(String key, Object value) {
        this.key = key;
        this.value = value;
    }
}



