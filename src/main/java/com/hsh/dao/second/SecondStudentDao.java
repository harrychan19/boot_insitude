package com.hsh.dao.second;

import com.hsh.domain.primary.PrimaryStudent;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SecondStudentDao {

    List<PrimaryStudent> getStudents();
}