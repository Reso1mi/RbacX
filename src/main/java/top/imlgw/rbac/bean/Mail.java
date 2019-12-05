package top.imlgw.rbac.bean;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Set;

/**
 * @author imlgw.top
 * @date 2019/12/5 23:09
 */
@Getter
@Setter
@ToString
public class Mail {
    public Mail() {

    }

    public Mail(String subject, String message, Set<String> receivers) {
        this.subject = subject;
        this.message = message;
        this.receivers = receivers;
    }

    private String subject;

    private String message;

    private Set<String> receivers;
}
