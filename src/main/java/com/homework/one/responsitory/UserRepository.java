package com.homework.one.responsitory;


import com.homework.one.bean.Book;
import com.homework.one.bean.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.scheduling.annotation.Async;

import java.util.List;
import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Integer> {

    boolean existsUserByUid(Integer uid);
    boolean existsUserByStuIDAndPassword(String stuID, String password);
    Optional<User> findByStuID(String stuID);

}

