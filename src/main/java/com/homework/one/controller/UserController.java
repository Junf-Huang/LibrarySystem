package com.homework.one.controller;

import com.homework.one.bean.Role;
import com.homework.one.bean.User;
import com.homework.one.enums.ResultEnum;
import com.homework.one.exception.MyException;
import com.homework.one.responsitory.UserRepository;
import com.homework.one.service.StorageService;
import com.homework.one.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Controller
public class UserController {


    private final StorageService storageService;

    @Autowired
    public UserController(StorageService storageService) {
        this.storageService = storageService;
    }

    @Autowired
    private UserRepository userRepository;

    @Autowired
    UserService userservice;


    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping(value = "/users")
    public String getUserList(@RequestParam(defaultValue = "0")int page, Model model) {
        model.addAttribute("data", userRepository.findAll(PageRequest.of(page,3)));
        model.addAttribute("currentPage",page);
        return "users";
    }


    //chat with user
    @GetMapping(value = "/findUser")
    public String findOne(@RequestParam(defaultValue = "#")String stuId,
                          Model model){

        Optional<User> optionalUser = userRepository.findByStuID(stuId);

        //登录详细信息
        //optionalBook.ifPresent(System.out::println);  optional如果存在输出
        //optionalBook.orElse(null);                    反之使其null

        log.info("stuId={}",stuId);

        model.addAttribute("you",optionalUser.get().getStuID()) ;
        model.addAttribute("stuName",optionalUser.get().getStuName());
        model.addAttribute("me",userservice.getCurrentUser().getStuID());
        return "chat";
    }


    @GetMapping(value = "/deleteUser")
    public String delete(Integer id){
        userRepository.deleteById(id);
        return "redirect:/users";
    }

    @GetMapping(value = "/getRole/{stuId}")
    @ResponseBody
    public Set<Role> getRole(@PathVariable("stuId") String stuId) {

        return userRepository.findByStuID(stuId).get().getRoles();
    }

    //using security can ignore this login verify
    @PostMapping(value = "/verify")
    public String judgeUser(@RequestParam String stuID, @RequestParam String password, Model model) {
        if(userRepository.existsUserByStuIDAndPassword(stuID, password))
            model.addAttribute("uid",userRepository.findByStuID(stuID).get().getUid());
        return "redirect:home";
    }

    //@PreAuthorize("hasAnyRole('ADMIN')")
    @Transactional
    @PostMapping(value = "/addUser")
    public String addUser(@Valid User user, BindingResult bindingResult) throws Exception {
        log.info("add={}","this add user");
        if (bindingResult.hasErrors()) {
            throw new MyException(1, bindingResult.getFieldError().getDefaultMessage());
        }

        Role role = new Role();
        Set<Role> setRole = new HashSet<Role>();
        setRole.add(role);

     /*判断大于18岁
       if(userservice.getAgeFromBirthTime(user.getStuBirth())>18)
            user.setStuBirth(user.getStuBirth());*/

        user.setStuID(user.getStuID());
        user.setStuName(user.getStuName());
        user.setPassword(user.getPassword());
        user.setStuSex(user.getStuSex());
        user.setStuPro(user.getStuPro());
        user.setRoles(setRole);
        //if(0<=user.getStuCredit() && user.getStuCredit()<=80)
            user.setStuCredit(user.getStuCredit());

        userRepository.save(user);

        log.info("user={}",user.toString());

        return "/sign-in";
    }

    @PostMapping(value = "/save_user")
    public String save(User user){
        userRepository.save(user);
        return "sign-in";
    }

    @ResponseBody
    @GetMapping(value = "/get/{id}")
    public Optional<User> getUser(@PathVariable("id") Integer uid) {
        if (userRepository.existsUserByUid(uid))
            return userRepository.findById(uid);
        else
            throw new MyException(ResultEnum.NOTFOUND);
    }

}
