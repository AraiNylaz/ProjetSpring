package com.example.user_frontend.controller;

import com.example.user_frontend.config.TokenService;
import com.example.user_frontend.model.User;
import com.example.user_frontend.model.UserDTO;
import com.example.user_frontend.proxy.UserProxy;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/login")
public class LoginController {

    private UserProxy userProxy;
    public TokenService tokenService;


    public LoginController(UserProxy userProxy,TokenService tokenService){
        this.userProxy=userProxy;
        this.tokenService=tokenService;
    }

    @GetMapping
    public ModelAndView login(@CookieValue(value="token",defaultValue="none")String token,ModelAndView modelAndView){
        if(token!=null && !token.equals("none")){
            if(tokenService.verifyToken(token)){
                return new ModelAndView(new RedirectView("http://localhost:9007/jeux"));
            }
        }
        modelAndView.addObject("user",new User(-1,"","",null,"","","",false, ""));
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @PostMapping
    public ModelAndView loginUser(@ModelAttribute User user, BindingResult bindingResult, HttpServletResponse response) throws Exception {
        try {
            if (userProxy.checkUser(user)){
                User u=userProxy.findByEmail(user.getEmail());
                UserDTO userDTO=new UserDTO(u.getId(),u.getNom(),u.getPrenom(),u.getDateNaissance(),u.getAdresse(),u.getEmail(),u.isEstAdministrateur(), u.getPseudo());
                Cookie cookie = new Cookie("token", tokenService.createToken(userDTO));
                cookie.setHttpOnly(true);
                response.addCookie(cookie);
                return new ModelAndView(new RedirectView("http://localhost:9007/jeux"));
            }
        }catch(Exception e){
            e.printStackTrace();
        }catch (Error error){
            error.printStackTrace();
        }

        return new ModelAndView("redirect:/login");
    }
}