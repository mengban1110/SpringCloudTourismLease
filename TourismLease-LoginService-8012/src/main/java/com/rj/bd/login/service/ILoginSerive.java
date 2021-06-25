package com.rj.bd.login.service;

import cn.doo.framework.entity.pojo.EmployeePojo;

import javax.mail.MessagingException;
import java.util.Map;

public interface ILoginSerive {

    Map<String, Object> sendCode(String username, String password, EmployeePojo employeePojo) throws MessagingException;

    Map<String, Object> login(String username, String password, String code, EmployeePojo employeePojo);
}
