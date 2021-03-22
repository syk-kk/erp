package com.sky.erp.sys.controller;

import cn.hutool.Hutool;
import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import com.sky.erp.sys.common.ActiveUser;
import com.sky.erp.sys.common.ResultObj;
import com.sky.erp.sys.entity.Loginfo;
import com.sky.erp.sys.entity.User;
import com.sky.erp.sys.service.ILoginfoService;
import com.sky.erp.sys.service.IUserService;
import com.sky.erp.sys.vo.UserVo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.OutputStream;
import java.util.Date;

@RestController
@RequestMapping("login")
public class LoginController {

    @Autowired
    private IUserService userService;
    @Autowired
    private ILoginfoService loginfoService;

    /**
     * 登录
     * @param userVo
     * @param session
     * @return
     */
    @RequestMapping("login")
    public ResultObj login(UserVo userVo, HttpSession session, HttpServletRequest request){
        String code = session.getAttribute("code").toString();
        if (code.equals(userVo.getCode())){
            Subject subject = SecurityUtils.getSubject();
            UsernamePasswordToken token = new UsernamePasswordToken(userVo.getLoginname(),userVo.getPwd());
            try {
                subject.login(token);
                ActiveUser activeUser = (ActiveUser) subject.getPrincipal();
                User user = activeUser.getUser();
                session.setAttribute("user", user);
                //添加用户登录日志
                loginfoService.save(new Loginfo(user.getId(),user.getName()+"-"+user.getLoginname(),
                       request.getRemoteAddr(),new Date()));
                return ResultObj.LOGIN_SUCCESS;
            }catch (AuthenticationException e){
                return ResultObj.LOGIN_ERROR_AUTHC;
            }

        }else {
            return ResultObj.LOGIN_ERROR_CODE;
        }


    }

    /**
     * 获取验证码图片
     * @param response
     * @param session
     */
    @RequestMapping("getCode")
    public void getCode(HttpServletResponse response, HttpSession session){
        try {
            LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(116, 36,4,10);
            session.setAttribute("code", lineCaptcha.getCode());
            BufferedImage image = lineCaptcha.getImage();
            OutputStream outputStream = response.getOutputStream();
            ImageIO.write(image, "jpg", outputStream);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
