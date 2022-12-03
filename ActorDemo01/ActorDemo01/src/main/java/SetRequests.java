/**
 * @description:
 * @author: shu
 * @createDate: 2022/10/27 20:17
 * @version: 1.0
 */
public class SetRequests {
    private final String key;
    private final Object value;


    public SetRequests(String key, Object value) {
        this.key = key;
        this.value = value;
    }


    public String getKey() {
        return key;
    }

    public Object getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "SetRequests{" +
                "key='" + key + '\'' +
                ", value=" + value +
                '}';
    }
}
