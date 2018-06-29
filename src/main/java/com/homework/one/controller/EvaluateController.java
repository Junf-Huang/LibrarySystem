package com.homework.one.controller;

import com.homework.one.bean.Evaluator;
import com.homework.one.responsitory.EvaluatorRepository;
import com.homework.one.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.Date;

@Slf4j
@Controller
public class EvaluateController {

    @Autowired
    private EvaluatorRepository evaluatorRepository;

    @Autowired
    UserService userService;

    @PostMapping(value = "/evaluate")
    public String addEvaluate(@Valid Evaluator evaluator){
        evaluator.setStuId(userService.getCurrentUser().getStuID());
        evaluator.setDate(new Date());

        log.info("bookId={}",evaluator.getBookId());
        evaluatorRepository.save(evaluator);
        return "redirect:getBook?id="+evaluator.getBookId();
    }

    @GetMapping(value = "/evaluate/del")
    public String deleteEvaluate(Evaluator evaluator){
        evaluatorRepository.delete(evaluator);
        return "redirect:/evaluate";
    }

}
