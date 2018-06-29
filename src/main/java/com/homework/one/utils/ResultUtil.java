package com.homework.one.utils;

import com.homework.one.bean.Result;
import com.homework.one.bean.User;

public class ResultUtil {

    public static Result<User> success(User user) {
        Result<User> result = new Result<>();
        result.setCode(0);
        result.setMsg("成功");
        result.setData(user);
        return result;
    }

    public static Result success() {

        return success(null);
    }

    public static Result<String> error(Integer code, String msg) {
        Result<String> result = new Result<>();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }

}
