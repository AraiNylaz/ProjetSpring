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
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/jeux")
public class JeuController {
    private GameProxy proxy;
    private UtilisateurProxy userProxy;
    private TokenService tokenService;

    public JeuController(GameProxy proxy,UtilisateurProxy userProxy,TokenService tokenService) {
        this.proxy = proxy;
        this.userProxy=userProxy;
        this.tokenService=tokenService;
    }

    @GetMapping
    public ModelAndView home(@CookieValue(value="token",defaultValue="none")String token, ModelAndView modelAndView,@RequestParam(required=false) String category,
                              @RequestParam(required=false) Double priceMin,
                              @RequestParam(required=false) Double priceMax,
                              @RequestParam(required=false) String direction){

        UserDTO userDTO=null;
        if(token!=null && !token.equals("none")){
            userDTO= userProxy.findById(token,tokenService.decode(token));

        }

        if(category != null && category.equals("")){
            category = null;
        }
        Iterable<Game> jeux=proxy.findAll(category,priceMin,priceMax,direction);
        modelAndView.addObject("userDTO",userDTO);
        modelAndView.addObject("jeux", jeux);
        modelAndView.addObject("jeu", new Game(-1,"","","",-1.0,""));
        modelAndView.setViewName("liste_jeux");
        return modelAndView;
    }

}
