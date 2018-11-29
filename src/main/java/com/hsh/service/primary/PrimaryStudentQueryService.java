package com.hsh.service.primary;

import com.hsh.dao.primary.PrimaryStudentDao;
import com.hsh.domain.primary.PrimaryStudent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author hushihai
 * @version V1.0, 2018/11/8
 */
@Service
public class PrimaryStudentQueryService {

    @Autowired
    PrimaryStudentDao primaryStudentDao;

    /**
     * @author:hushihai
     * @date:15:23 2018/11/8
     * @params:[]
     * @return : java.util.List<com.hsh.domain.primary.PrimaryStudent>
     */
    public List<PrimaryStudent> getStudents() {
        return primaryStudentDao.getStudents();
    }
}
