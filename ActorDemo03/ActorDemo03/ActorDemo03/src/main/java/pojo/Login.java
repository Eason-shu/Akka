package pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * @description: 登录请求
 * @author: shu
 * @createDate: 2022/12/10 19:46
 * @version: 1.0
 */
public class Login implements Serializable {
    public String terminalId;
    public Date loginTime;

    public Login(String terminalId, Date loginTime) {
        this.terminalId = terminalId;
        this.loginTime = loginTime;
    }
}
