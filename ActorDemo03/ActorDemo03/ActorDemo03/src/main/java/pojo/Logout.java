package pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * @description: 注销请求
 * @author: shu
 * @createDate: 2022/12/10 19:47
 * @version: 1.0
 */
public class Logout implements Serializable {
    public String terminalId;
    public Date logoutTime;

    public Logout(String terminalId, Date logoutTime) {
        this.terminalId = terminalId;
        this.logoutTime = logoutTime;
    }
}
