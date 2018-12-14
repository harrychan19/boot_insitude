package com.hsh.dao;

import com.hsh.domain.primary.PrimaryStudent;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SecondStudentDao {

    List<PrimaryStudent> getStudents();
}