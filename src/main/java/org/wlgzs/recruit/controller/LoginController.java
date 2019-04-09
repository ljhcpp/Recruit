package org.wlgzs.recruit.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.wlgzs.recruit.base.BaseController;
import org.wlgzs.recruit.util.result.Result;

import javax.servlet.http.HttpSession;

/**
 * @author 阿杰
 * Create 2018-08-18 9:50
 * Description: 登录
 */
@RestController
public class LoginController extends BaseController {

    @RequestMapping("/toLogin")
    public ModelAndView toLogin(){
        return new ModelAndView("login");
    }

    @RequestMapping("/login")
    public Result login(String userName,String password,HttpSession session){
        return loginService.login(userName,password,session);
    }

    @RequestMapping("/login/exit")
    public ModelAndView exit(HttpSession session){
        session.removeAttribute("userName");
        return new ModelAndView("redirect:/toLogin");
    }

    @RequestMapping("/index")
    public ModelAndView index(){
        return new ModelAndView("index");
    }
}
