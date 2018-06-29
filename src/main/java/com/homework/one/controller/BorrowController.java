package com.homework.one.controller;

import com.homework.one.bean.Book;
import com.homework.one.bean.User;
import com.homework.one.responsitory.BookRepository;
import com.homework.one.responsitory.UserRepository;
import com.homework.one.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class BorrowController {

    private final static Logger logger = LoggerFactory.getLogger(BookController.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    UserService userservice;

    @GetMapping(value = "/borrows")
    public String getBookList(@RequestParam(defaultValue = "0")int page, Model model) {
        User user = userservice.getCurrentUser();
        logger.info("user={}",user.toString());

        model.addAttribute("data", bookRepository.findByUser(user,PageRequest.of(page,3, Sort.Direction.DESC,"id")));
        model.addAttribute("currentPage",page);
        //return bookRepository.findBooksByUser(user);
        return "borrows";
    }

    @GetMapping(value = "/borrowBook")
    public String borrowBook(Integer bookId) {

        //得到登录用户的信息
        /*
        Get the username of the logged in user: getPrincipal()
        Get the password of the authenticated user: getCredentials()
        Get the assigned roles of the authenticated user: getAuthorities()
        Get further details of the authenticated user: getDetails()
        */
        User user = userservice.getCurrentUser();
        Book book = bookRepository.findById(bookId).get();

        book.setUser(user);
        user.getBooks().add(book);
        userRepository.save(user);
        return "redirect:/borrows";
    }

    @GetMapping(value = "/returnBook")
    public String returnBook(Integer bookId) {

        User user = userservice.getCurrentUser();
        Book book = bookRepository.findById(bookId).get();

        logger.info("bookId={}",bookId);

        book.setUser();
        user.getBooks().remove(book);
        userRepository.save(user);
        return "redirect:/borrows";
    }

}
