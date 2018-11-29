package com.hsh.amqp.sender;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.hsh.amqp.config.QueueConfig;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author hushihai
 * @version V1.0, 2018/11/8
 */
@Component
public class RabbitMqSender {

    @Autowired
    private AmqpTemplate template;

    /** 发送消息 */
    public void send(Object data,String queue) {
        if(StringUtils.isEmpty(queue)){
            return;
        }
        if(data!=null){
            template.convertAndSend(queue, JSON.toJSONString(data));
        }
    }

    /** 发送消息 */
    public void sendRouting(Object data,String queue) {
        if(StringUtils.isEmpty(queue)){
            return;
        }
        if(data!=null){
            template.convertAndSend(QueueConfig.TOPIC_EXCHANGE,queue, JSON.toJSONString(data));
        }
    }
}
