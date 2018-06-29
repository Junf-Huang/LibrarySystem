package com.homework.one.responsitory;

import com.homework.one.bean.Book;
import com.homework.one.bean.Evaluator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.scheduling.annotation.Async;

import java.util.List;

public interface EvaluatorRepository extends JpaRepository<Evaluator, Integer> {

    @Async
    @Query(value = "select e from Evaluator e where e.bookId = ?1"
    )
    Page<Evaluator> findByBookId(String bookId, Pageable pageable);
    //参数只能 String

}
