package com.hsh.dubbo;

import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.stereotype.Component;

/**
 * @author hushihai
 * @version V1.0, 2018/11/9
 */
@Service
@Component
public class StudentDubboServiceImpl implements StudentDubboService{

    @Override
    public void sayHello() {
        System.out.println("hello dubbo!");
    }

}
