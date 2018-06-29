package com.homework.one.controller;

import com.homework.one.bean.Book;
import com.homework.one.bean.Evaluator;
import com.homework.one.exception.MyException;
import com.homework.one.responsitory.BookRepository;
import com.homework.one.responsitory.EvaluatorRepository;
import com.homework.one.service.FileSystemStorageService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.support.ServletContextResource;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import javax.activation.FileTypeMap;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.Null;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Controller
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private EvaluatorRepository evaluatorRepository;

    private String KEYWORD;

    private final FileSystemStorageService storageService;
    private final String folder = "books";

    @Autowired
    public BookController(FileSystemStorageService storageService) {
        this.storageService = storageService;
    }


    @Transactional  //事务
    @PostMapping(value = "/book/add")
    public String addBook(@Valid Book book, @RequestParam("photo") MultipartFile file, BindingResult bindingResult) throws Exception {

        if (bindingResult.hasErrors()) {
            throw new MyException(1, bindingResult.getFieldError().getDefaultMessage());
        }

        Optional<Book> optionalBook = bookRepository.findBookByBookISBN(book.getBookISBN());
        optionalBook.ifPresent(u-> book.setId(u.getId()));

        storageService.init(folder);
        if(!file.isEmpty()) {
            storageService.store(file, folder);
            book.setBookPhoto(StringUtils.cleanPath(file.getOriginalFilename()));
        }
        log.info("bookID={}",book.getId());
        bookRepository.save(book);
        return "redirect:/home";
    }

    @GetMapping("/test")
    public String test(Model model) throws IOException{
        Path pa = Paths.get("upload-dir");

        model.addAttribute("files",
                Files.walk(pa)
                .filter(path -> path.endsWith("cover.jpg"))   //条件匹配过滤
                /*.map(path -> pa.relativize(path))   */                  //相对路径
                .map(path -> MvcUriComponentsBuilder.fromMethodName(BookController.class,  //工程路径处理
                        "serveFile", folder, path.getFileName().toString()).build().toString())
                .collect(Collectors.toList())
        );

        return "test";
    }

    @GetMapping(value = "/resource/{folder}/{fileName:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable("folder")String folder, @PathVariable String fileName) {
        Resource file = storageService.loadAsResource(folder, fileName);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }


    @GetMapping(value = "/deleteBook")
    public String delete(Integer id){
        bookRepository.deleteById(id);
        return "redirect:/home";   //redirect 重导向到 动作函数    //return "books" 跳转到html
    }

    @GetMapping(value = "/findBook")
    @ResponseBody
    public Book findOne(Integer id){
        Optional<Book> optionalBook = bookRepository.findById(id);
        //optionalBook.ifPresent(System.out::println);  optional如果存在输出
        //optionalBook.orElse(null);                    反之使其null
        return optionalBook.get();
    }

    @GetMapping(value = "/getBook")
    public String getOne(@RequestParam(defaultValue = "0")int page, Integer id, Model model){
        Optional<Book> optionalBook = bookRepository.findById(id);

        model.addAttribute("book", optionalBook.get());
        log.info("bookPhoto={}",optionalBook.get().getBookPhoto());

        model.addAttribute("data", evaluatorRepository.findByBookId(id.toString(), PageRequest.of(page, 3,Sort.Direction.DESC,"id")));
        model.addAttribute("currentPage", page);
        return "/book";

    }

    @GetMapping(value = "/search")
    public String SearchBook(@RequestParam(defaultValue = "&") String keyword, @RequestParam(defaultValue = "0")int page, Model model) {
        if (keyword!="&")
            KEYWORD = keyword;
        model.addAttribute("data", bookRepository.findByBookNameOrBookISBNContaining(keyword, PageRequest.of(page,3)));
        model.addAttribute("currentPage",page);
        return "/search";
    }

    @GetMapping(value = "/searchList")
    public String searchList(@RequestParam(defaultValue = "0")int page, Model model) {

        model.addAttribute("data", bookRepository.findByBookNameOrBookISBNContaining(KEYWORD, PageRequest.of(page,3)));
        model.addAttribute("currentPage",page);
        return "/search";
    }

    @GetMapping(value = "/home")
    public String getBookList(@RequestParam(defaultValue = "0")int page, Model model) {
        model.addAttribute("data", bookRepository.findAll(PageRequest.of(page,3, Sort.Direction.DESC,"id")));
        model.addAttribute("currentPage",page);
        return "/home";
    }


    public Pageable createPageRequest() {
        //Create a new Pageable object here.

        return PageRequest.of(1, 10, Sort.Direction.ASC, "bookISBN");

       /**
        * page：当前页码
        * size：每页获取的条数
        * direction：排序方式，ASC、DESC
        * properties：排序的字段
        * sort：排序对象
        */

        //example
        //PageRequest(1, 10, Sort.Direction.ASC, "title", "description");
        //
        //PageRequest(1,
        //            10,
        //            new Sort(Sort.Direction.DESC, "description")
        //                    .and(new Sort(Sort.Direction.ASC, "title"));
        //    );
    }


}
