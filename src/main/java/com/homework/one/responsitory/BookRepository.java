package com.homework.one.responsitory;

import com.homework.one.bean.Book;
import com.homework.one.bean.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.scheduling.annotation.Async;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Integer> {

    Optional<Book> findBookByBookISBN(String bookISBN);

 /*   @Async
    @Query(value = "select u.books from User u")*/
    Page<Book>findByUser(User user,Pageable pageable);
    List<Book>findBooksByUser(User user);

    @Async
    @Query(value = "select b from Book b where b.bookName like %?1% or b.bookISBN like %?1% or b.label like %?1%"
            //countQuery = "SELECT count(*) FROM t_book where bookName like '%?1%' or bookISBN like '?1%'",
            )
    Page<Book> findByBookNameOrBookISBNContaining(String keyword, Pageable pageable);

    @Async
    @Query(value = "SELECT b from Book b where b.bookName like %?1%")
    List<Book> findByBookNameLike(String bookName);

    //Page<Book> findByBookName(String bookName, Pageable page);
    //List<Book> findByBookName(String bookName, Sort sort);


    //
    //jpa 子查询
    //如果涉及到大量更新呢？批量更新
    @Query(value = "update t_book set bookName=?1 where ISBN=?2", nativeQuery = true)
    @Modifying
    @Transactional
    int setBook(String bookName, String ISBN);


}
