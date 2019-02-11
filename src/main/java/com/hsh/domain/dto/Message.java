package com.hsh.domain.dto;

import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author hushihai
 * @version V1.0, 2019/2/11
 */
@AllArgsConstructor
@Data
public class Message {

    public static final String ENTER = "ENTER";
    public static final String SPEAK = "SPEAK";
    public static final String QUIT = "QUIT";
    /** 消息类型 */
    private String type;
    /** 发送人 */
    private String username;
    /** 发送消息 */
    private String msg;
    /** 在线用户数 */
    private int onlineCount;

    public static String jsonStr(String type, String username, String msg, int onlineTotal) {
        return JSON.toJSONString(new Message(type, username, msg, onlineTotal));
    }

}
