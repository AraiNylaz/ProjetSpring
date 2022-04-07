package com.example.demo.controller;

import com.example.demo.config.TokenService;
import com.example.demo.model.UserDTO;
import com.example.demo.proxi.GameProxy;
import com.example.demo.proxi.UtilisateurProxy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/jeux")
public class UpdateController {
    private GameProxy proxy;
    private TokenService tokenService;
    private UtilisateurProxy userProxy;

    public UpdateController(GameProxy proxy,UtilisateurProxy userProxy,TokenService tokenService) {
        this.proxy = proxy;
        this.userProxy=userProxy;
        this.tokenService=tokenService;
    }

    @GetMapping("/{id}")
    public ModelAndView update(@CookieValue(value="token",defaultValue="none")String token,@PathVariable("id") int id, ModelAndView modelAndView) {
        UserDTO userDTO=null;
        if(token!=null && !token.equals("none")){
            userDTO= userProxy.findById(token,tokenService.decode(token));

        }
        modelAndView.addObject("userDTO",userDTO);
        modelAndView.addObject("jeu", proxy.findByid(id));
        modelAndView.setViewName("modifier_jeux");
        return modelAndView;
    }
}
