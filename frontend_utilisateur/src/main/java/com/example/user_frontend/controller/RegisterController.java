package com.example.user_frontend.controller;

import com.example.user_frontend.config.TokenService;
import com.example.user_frontend.model.User;
import com.example.user_frontend.model.UserDTO;
import com.example.user_frontend.proxy.UserProxy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/register")
public class RegisterController {
    private UserProxy userProxy;
    public TokenService tokenService;

    public RegisterController(UserProxy userProxy,TokenService tokenService){
        this.userProxy=userProxy;
        this.tokenService=tokenService;
    }
    
    @GetMapping()
    public ModelAndView register(@CookieValue(value="token",defaultValue="none")String token,ModelAndView modelAndView){

        if(token!=null && !token.equals("none")){
            if(tokenService.verifyToken(token)){
                return new ModelAndView(new RedirectView("http://localhost:9007/jeux"));
            }
        }
        modelAndView.addObject("user1",new User(-1,"","",null,"","","",false, ""));
        modelAndView.setViewName("register");
        return modelAndView;
    }

    @PostMapping()
    public ModelAndView registerUser(@ModelAttribute("User") User user1, BindingResult bindingResult, HttpServletResponse response) throws Exception {
        try{
            if(userProxy.findByEmail(user1.getEmail())!=null){
                return new ModelAndView("redirect:/register");
            }else{
                user1.setMotDePasse(new BCryptPasswordEncoder().encode(user1.getMotDePasse()));
                userProxy.createUser(user1);
                User userAdd=userProxy.findByEmail(user1.getEmail());
                UserDTO userDTO=new UserDTO(userAdd.getId(), userAdd.getNom(), userAdd.getPrenom(),userAdd.getDateNaissance(),userAdd.getAdresse(),userAdd.getEmail(),userAdd.isEstAdministrateur(), userAdd.getPseudo());
                Cookie cookie = new Cookie("token", tokenService.createToken(userDTO));
                cookie.setHttpOnly(true);
                response.addCookie(cookie);
                return new ModelAndView(new RedirectView("http://localhost:9007/jeux"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }catch(Error error){
            error.printStackTrace();
        }
        return new ModelAndView("redirect:/register");
    }
}
