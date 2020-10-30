package cn.itcast.listener;

import cn.itcast.utils.MailUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.stereotype.Component;


/**
 * 发送邮件
 */
@Component
public class EmailListener implements MessageListener {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Override
    public void onMessage(Message message) {
        try {
            JsonNode jsonNode = MAPPER.readTree(message.getBody());
            String email = jsonNode.get("email").asText();
            String title = jsonNode.get("title").asText();
            String content = jsonNode.get("content").asText();

            // 打印测试
            System.out.println("获取队列中消息：" + email);
            System.out.println("获取队列中消息：" + title);
            System.out.println("获取队列中消息：" + content);

            // 发送邮件
            MailUtil.sendMsg(email, title, content);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
