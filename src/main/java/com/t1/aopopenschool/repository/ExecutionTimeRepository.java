package com.t1.aopopenschool.repository;

import com.t1.aopopenschool.model.ExecutionTime;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExecutionTimeRepository extends JpaRepository<ExecutionTime, Long> {
    List<ExecutionTime> findByMethodName(String methodName);
}
