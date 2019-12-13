package top.imlgw.rbac.config;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author imlgw.top
 * @date 2019/12/6 22:26
 */
@Component
@ConfigurationProperties(prefix="mail")
@ToString
@Getter
@Setter
public class MailConfig {

    private String username;

    private String password;

    private String host;

    private int port;

    private String nickname;
}
