package cn.itcast.utils;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class MailUtil {
    // 发件人邮箱地址，你的邮箱
    private static final String FROM = "604893299@qq.com";
    // 邮件发送服务器地址
    private static final String SMTP = "smtp.qq.com";

    //实现邮件发送的方法
    public static void sendMsg(String to,String subject,String content) throws Exception {
        Properties props = new Properties();
        //设置主机地址
        props.setProperty("mail.smtp.host", SMTP);
        //认证
        props.setProperty("mail.smtp.auth", "true");
        //2.产生一个用于邮件发送的Session对象
        Session session = Session.getInstance(props);
        //3.产生一个邮件的消息对象
        MimeMessage message = new MimeMessage(session);
        //4.设置消息的发送者
        Address fromAddr = new InternetAddress(FROM);
        message.setFrom(fromAddr);
        //5.设置消息的接收者
        Address toAddr = new InternetAddress(to);
        //TO 直接发送  CC抄送    BCC密送
        message.setRecipient(Message.RecipientType.TO,toAddr);
        //6.设置主题
        message.setSubject(subject);
        //7.设置正文
        message.setText(content);
        //8.准备发送，得到火箭
        Transport transport = session.getTransport("smtp");
        //9.设置火箭的发射目标
        transport.connect(SMTP, FROM, "qlcafsxnfksvbfgg");
        //10.发送
        transport.sendMessage(message, message.getAllRecipients());
        //11.关闭
        transport.close();
    }
}
