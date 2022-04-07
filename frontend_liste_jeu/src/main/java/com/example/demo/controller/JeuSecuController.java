package com.example.demo.controller;

import com.example.demo.config.TokenService;
import com.example.demo.model.Game;
import com.example.demo.proxi.GameProxy;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/jeux-secu")
public class JeuSecuController {
    private GameProxy proxy;
    private TokenService tokenService;
    public JeuSecuController(GameProxy proxy,TokenService tokenService) {
        this.proxy = proxy;
        this.tokenService=tokenService;
    }
    @GetMapping("/{id}")
    public ModelAndView deleteKit(@CookieValue(value="token",defaultValue="none")String token,@PathVariable("id") int id) {
        try{
            tokenService.decode(token);
            proxy.deleteGame(token,id);
        }catch(Error error){
            error.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }
        return new ModelAndView("redirect:/jeux");
    }
    @PostMapping("/update/{id}")
    public ModelAndView updateKit(@CookieValue(value="token",defaultValue="none")String token,@PathVariable("id") int id, @ModelAttribute Game jeu) {
        try{
        proxy.updateGame(token,id,jeu);
        }catch(Error error){
            error.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }
        return new ModelAndView("redirect:/jeux");
    }
}