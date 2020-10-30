package cn.itcast.consumer;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Consumer {
    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext ctx =
                new ClassPathXmlApplicationContext("classpath:spring/applicationContext-activemq-consumer.xml");
        System.in.read();
    }
}
