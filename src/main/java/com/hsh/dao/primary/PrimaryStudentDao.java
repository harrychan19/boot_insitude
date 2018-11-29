package com.hsh.dao.primary;

import com.hsh.domain.primary.PrimaryStudent;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrimaryStudentDao {

    List<PrimaryStudent> getStudents();
}