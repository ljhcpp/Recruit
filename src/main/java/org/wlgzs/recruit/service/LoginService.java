package org.wlgzs.recruit.service;

import org.wlgzs.recruit.util.result.Result;

import javax.servlet.http.HttpSession;

public interface LoginService {

    Result login(String userName, String password, HttpSession session);
}
