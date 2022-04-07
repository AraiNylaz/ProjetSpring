package com.example.demo.controller;

import com.example.demo.config.TokenService;
import com.example.demo.model.Game;
import com.example.demo.model.UserDTO;
import com.example.demo.proxi.GameProxy;
import com.example.demo.proxi.UtilisateurProxy;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/jeux-secu")
public class JeuAjouterController {
    private GameProxy proxy;
    private TokenService tokenService;
    private UtilisateurProxy userProxy;
    public JeuAjouterController(GameProxy proxy,UtilisateurProxy userProxy,TokenService tokenService) {
        this.proxy = proxy;
        this.userProxy=userProxy;
        this.tokenService=tokenService;
    }

    @GetMapping
    public ModelAndView afficherForm(@CookieValue(value="token",defaultValue="none")String token,ModelAndView modelAndView) {

        UserDTO userDTO=null;
        if(token!=null && !token.equals("none")){
            userDTO= userProxy.findById(token,tokenService.decode(token));

        }

        modelAndView.addObject("userDTO",userDTO);
        modelAndView.addObject("game", new Game(
                -1,
                "",
                "",
                "",
                0.0,
                ""
        ));

        modelAndView.setViewName("ajouter_jeux");
        return modelAndView;
    }



    @PostMapping
    public ModelAndView saveGame(@CookieValue(value="token",defaultValue="none")String token,@ModelAttribute Game game){
        try {
        proxy.saveGame(token,game);
        }catch(Error error){
            error.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }
        return new ModelAndView("redirect:/jeux");
    }

}