package org.custom.utils;

import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendEmailUtils {

  public static final String HOST = "yyy"; // TODO 主机名
  public static final String PROTOCOL = "stmp";
  public static final int PORT = 25;
  public static final String FROM = "xxx@xxx.com"; // TODO 发件人的email
  public static final String NAME = "xxx";    // TODO 发件人
  public static final String PWD = "xxx"; // TODO 发件人密码

  /**
   * 获取Session
   */
  private static Session getSession() {
    Properties props = new Properties();
    props.put("mail.smtp.host", HOST);          // 设置服务器地址
    props.put("mail.store.protocol", PROTOCOL); // 设置协议
    props.put("mail.smtp.port", PORT);          // 设置端口
    props.put("mail.smtp.auth", true);
    Authenticator authenticator = new Authenticator() {
      @Override
      protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(NAME, PWD);
      }
    };
    Session session = Session.getDefaultInstance(props, authenticator);
    return session;
  }

  public static void send(String toEmail, String title, String content) {
    Session session = getSession();
    try {
      final Message msg = new MimeMessage(session);
      msg.setFrom(new InternetAddress(FROM));
      InternetAddress[] address = { new InternetAddress(toEmail) };
      msg.setRecipients(Message.RecipientType.TO, address);
      msg.setSubject(title);
      msg.setSentDate(new Date());
      msg.setContent(content, "text/html;charset=utf-8");
      
      new Thread(new Runnable() {
            public void run() {
                try {
                    Transport.send(msg);
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    } catch (MessagingException mex) {
      mex.printStackTrace();
    }
  }
  
}
