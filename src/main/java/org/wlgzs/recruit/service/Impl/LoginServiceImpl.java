package org.wlgzs.recruit.service.Impl;

import org.springframework.stereotype.Service;
import org.wlgzs.recruit.service.LoginService;
import org.wlgzs.recruit.util.result.Result;
import org.wlgzs.recruit.util.result.ResultCodeEnum;

import javax.servlet.http.HttpSession;

/**
 * @author 阿杰
 * Create 2018-08-18 9:59
 * Description:
 */
@Service
public class LoginServiceImpl implements LoginService {

    @Override
    public Result login(String userName, String password, HttpSession session) {
        Result result = new Result(ResultCodeEnum.FAIL);
        if(userName.equals("123") && password.equals("123")){
            session.setMaxInactiveInterval(60 * 60);
            session.setAttribute("userName", userName);
            result.setMsg("登录成功");
            return result;
        }
        result.setMsg("账号或密码错误");
        return result;
    }
}
