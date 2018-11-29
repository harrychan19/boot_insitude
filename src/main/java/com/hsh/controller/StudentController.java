package com.hsh.controller;

import com.hsh.amqp.config.QueueConfig;
import com.hsh.amqp.sender.RabbitMqSender;
import com.hsh.common.config.RestResult;
import com.hsh.common.config.ResultCode;
import com.hsh.domain.primary.PrimaryStudent;
import com.hsh.service.primary.PrimaryStudentQueryService;
import com.hsh.service.second.SecondStudentQueryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author hushihai
 * @version V1.0, 2018/11/8
 */
@RestController
@RequestMapping(value = "/student")
public class StudentController {

    @Autowired
    PrimaryStudentQueryService primaryStudentQueryService;

    @Autowired
    SecondStudentQueryService secondStudentQueryService;

    @Autowired
    RabbitMqSender rabbitMqSender;

     private final Logger log = LoggerFactory.getLogger(this.getClass());

    @GetMapping(value = "/getPrimaryStudent")
    public RestResult getPrimaryStudent(){
        List<PrimaryStudent> students;
        try {
            students = primaryStudentQueryService.getStudents();
            rabbitMqSender.send(students, QueueConfig.STUDENT_QUEUE);
            rabbitMqSender.send(students, QueueConfig.TEACHER_CHINESE);
            rabbitMqSender.send(students, QueueConfig.TEACHER_MATH);
            rabbitMqSender.sendRouting(students, QueueConfig.LEMON);
            rabbitMqSender.sendRouting(students, QueueConfig.APPLE_SWEET);
            rabbitMqSender.sendRouting(students, QueueConfig.ORANGE_SWEET);
        } catch (Exception e) {
            log.error("查询学生信息失败！",e);
            return RestResult.failed(ResultCode.INTERFACE_INNER_INVOKE_ERROR);
        }
        return RestResult.success(students);
    }

    @GetMapping(value = "/getSecondStudent")
    public RestResult getSecondStudent(){
        List<PrimaryStudent> students;
        try {
            students = secondStudentQueryService.getStudents();
        } catch (Exception e) {
            log.error("查询学生信息失败！",e);
            return RestResult.failed(ResultCode.INTERFACE_INNER_INVOKE_ERROR);
        }
        return RestResult.success(students);
    }
}
