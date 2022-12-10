package pojo;

import java.io.Serializable;

/**
 * @description: 电表数据
 * @author: shu
 * @createDate: 2022/12/10 16:10
 * @version: 1.0
 */
public class Meter implements Serializable {
    private String id;
    private String name;

    public Meter(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
        return "Meter{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
