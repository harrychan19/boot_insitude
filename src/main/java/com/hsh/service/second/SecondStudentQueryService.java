package com.hsh.service.second;

import com.hsh.common.annos.DataSource;
import com.hsh.common.config.DataSourceNames;
import com.hsh.dao.SecondStudentDao;
import com.hsh.domain.primary.PrimaryStudent;
import com.hsh.service.primary.PrimaryStudentQueryService;
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
    @Autowired
    PrimaryStudentQueryService primaryStudentQueryService;
    /**
     * 查询学生
     * @author:hushihai
     * @date:15:27 2018/11/8
     * @params:[]
     * @return : java.util.List<com.hsh.domain.primary.PrimaryStudent>
     */
    @DataSource(name = DataSourceNames.SECOND)
    public List<PrimaryStudent> getStudents() {
        List<PrimaryStudent> secondList = secondStudentDao.getStudents();
        List<PrimaryStudent> primaryList = primaryStudentQueryService.getStudents();
        secondList.addAll(primaryList);
        return secondList;
    }
}
