package top.imlgw.rbac.config;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author imlgw.top
 * @date 2019/12/13 20:37
 */
@Getter
@Setter
@ToString
@Component
@ConfigurationProperties(prefix = "acl")
public class AclConfig {
    private List<String> exclusionUrls;

    private String noAuthUrl;
}
