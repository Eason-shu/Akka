package pojo;

import java.io.Serializable;

/**
 * @description: 需要请求电表数据
 * @author: shu
 * @createDate: 2022/12/10 16:18
 * @version: 1.0
 */
public class MeterRequest implements Serializable {
    public String id;
    public int meterId;

    public MeterRequest(String id, int meterId) {
        this.id = id;
        this.meterId = meterId;
    }


}
