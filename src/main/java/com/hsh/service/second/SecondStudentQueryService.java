package com.hsh.service.second;

import com.hsh.dao.second.SecondStudentDao;
import com.hsh.domain.primary.PrimaryStudent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author hushihai
 * @version V1.0, 2018/11/8
 */
@Service
public class SecondStudentQueryService {

    @Autowired
    SecondStudentDao secondStudentDao;

    /**
     * 查询学生
     * @author:hushihai
     * @date:15:27 2018/11/8
     * @params:[]
     * @return : java.util.List<com.hsh.domain.primary.PrimaryStudent>
     */
    public List<PrimaryStudent> getStudents() {
        return secondStudentDao.getStudents();
    }
}
