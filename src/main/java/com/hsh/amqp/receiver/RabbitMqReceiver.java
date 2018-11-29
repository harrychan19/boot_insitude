package com.hsh.amqp.receiver;

import com.hsh.amqp.config.QueueConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author hushihai
 * @version V1.0, 2018/11/8
 */
@Component
public class RabbitMqReceiver {

    @RabbitListener(queues= QueueConfig.STUDENT_QUEUE)
    public void process(String str) {
        System.out.println("student_queue Receive:"+str);
    }

    @RabbitListener(queues= QueueConfig.TEACHER_MATH)
    public void mathProcess(String str) {
        System.out.println("teacher_math Receive:"+str);
    }

    @RabbitListener(queues= QueueConfig.TEACHER_CHINESE)
    public void chineseProcess(String str) {
        System.out.println("teacher_chinese Receive:"+str);
    }

    @RabbitListener(queues= QueueConfig.APPLE)
    public void appleProcess(String str) {
        System.out.println("apple Receive:"+str);
    }

    @RabbitListener(queues= QueueConfig.ORANGE)
    public void orangeProcess(String str) {
        System.out.println("orange Receive:"+str);
    }

    @RabbitListener(queues= QueueConfig.LEMON)
    public void lemonProcess(String str) {
        System.out.println("lemon Receive:"+str);
    }
}
