package top.imlgw.rbac.mail;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.springframework.beans.factory.annotation.Autowired;
import top.imlgw.rbac.config.MailConfig;

import java.util.HashSet;


@Slf4j
public class MailUtil {

    //todo
    @Autowired
    private MailConfig mailConfig;

    public static boolean send(Mail mail) {
        // TODO
        String from = "privateli@qq.com";
        int port = 587;
        String host = "smtp.qq.com";
        String pass = "zggkfcrkgnpjbdef";
        String nickname = "Resolmi";

        HtmlEmail email = new HtmlEmail();
        try {
            email.setHostName(host);
            email.setCharset("UTF-8");
            for (String str : mail.getReceivers()) {
                email.addTo(str);
            }
            email.setFrom(from, nickname);
            email.setSmtpPort(port);
            email.setAuthentication(from, pass);
            email.setSubject(mail.getSubject());
            email.setMsg(mail.getMessage());
            email.send();
            log.info("{} 发送邮件到 {}", from, StringUtils.join(mail.getReceivers(), ","));
            return true;
        } catch (EmailException e) {
            log.error(from + "发送邮件到" + StringUtils.join(mail.getReceivers(), ",") + "失败", e);
            return false;
        }
    }

    public static void main(String[] args) {
        send(new Mail("啊哈哈哈哈哈哈哈哈","窝窝头,10个____元", new HashSet<String>(){{add("lvsdian@qq.com");}}));
    }
}

