package com.hsh.amqp.config;

/**
 * @author hushihai
 * @version V1.0, 2018/11/8
 */
public class QueueConfig {

    /** 学生队列 */
    public static final String STUDENT_QUEUE = "student_queue";

    /** 数学教师队列 */
    public static final String TEACHER_MATH = "teacher_math";

    /** 语文教师队列 */
    public static final String TEACHER_CHINESE = "teacher_chinese";

    /** 芒果队列 */
    public static final String LEMON = "topic.lemon";

    /** 苹果队列 */
    public static final String APPLE = "topic.apple";

    /** 橘子 */
    public static final String ORANGE = "topic.orange";

    /** 苹果routing消费队列 */
    public static final String APPLE_SWEET = "topic.apple.sweet";

    /** 橘子routing消费队列 */
    public static final String ORANGE_SWEET = "topic.sweet.orange";

    /** 交换机 */
    public static final String TOPIC_EXCHANGE = "topic_exchange";


}
