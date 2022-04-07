package com.example.demo.controller;

import com.example.demo.config.TokenService;
import com.example.demo.model.Jeux;
import com.example.demo.model.Paniers;
import com.example.demo.model.UserDTO;
import com.example.demo.proxy.JeuxProxy;
import com.example.demo.proxy.PaniersProxy;
import com.example.demo.proxy.UtilisateurProxy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/paniers")
public class PaniersController {

    private PaniersProxy paniersProxy;
    private JeuxProxy jeuxProxy;
    private UtilisateurProxy utilisateurProxy;
    private TokenService tokenService;

    public PaniersController (PaniersProxy paniersProxy, JeuxProxy jeuxProxy, UtilisateurProxy utilisateurProxy, TokenService tokenService){
        this.paniersProxy = paniersProxy;
        this.jeuxProxy = jeuxProxy;
        this.utilisateurProxy = utilisateurProxy;
        this.tokenService = tokenService;
    }

    @GetMapping("/home")
    public String home( @CookieValue(value="token",defaultValue="none")String token, Model model,@RequestParam(required=false) String category,
                        @RequestParam(required=false) Double priceMin,
                        @RequestParam(required=false) Double priceMax,
                        @RequestParam(required=false) String direction){
        UserDTO userDTO=null;
        if(token!=null && !token.equals("none")){
            userDTO= utilisateurProxy.findById(token,tokenService.decode(token));
            try{
                Iterable <Paniers> paniers = paniersProxy.findByUserId(token, userDTO.getId());
                model.addAttribute("paniers", paniers);
                model.addAttribute("panier", new Paniers(
                        -1,
                        userDTO.getId(),
                        -1,
                        1
                ));
                Iterable<Jeux> listeJeux = jeuxProxy.findAll(category,priceMin,priceMax,direction);
                double prixTotal = 0;

                for (Paniers panier: paniers) {
                    Jeux jeu = jeuxProxy.findById(panier.getIdProduit());
                    prixTotal += panier.getQuantite() * jeu.getPrice();
                }

                model.addAttribute("prixTotal", prixTotal);
                model.addAttribute("jeux", listeJeux);
                model.addAttribute("jeu", new Jeux(
                        -1,
                        "",
                        "",
                        "",
                        0.0,
                        ""
                ));
            }catch(Error error){
                error.printStackTrace();
            }
            model.addAttribute("userId", userDTO.getId());


        }
        return "panierPage";
    }


    @GetMapping("/{id}")
    public ModelAndView createPanier(@CookieValue(value="token",defaultValue="none")String token, @PathVariable("id") int id) {
        System.out.println("here !!!! ");
        try {
            int idUser = tokenService.decode(token);
            Paniers panier = new Paniers(-1, idUser, id, 1);
            paniersProxy.savePanier(token, panier);

        }catch(Error error){
            error.printStackTrace();
        }
        return new ModelAndView(new RedirectView("http://localhost:9007/jeux"));
    }

    @GetMapping("/delete/{id}")
    public ModelAndView deletePanier(@CookieValue(value="token",defaultValue="none")String token, @PathVariable("id") int id) {
        try{
            paniersProxy.deletePanier(token, id);
        }catch(Error error){
            error.printStackTrace();
        }
        return new ModelAndView("redirect:/paniers/home/");
    }

    @GetMapping("/update/{id}")
    public String updatePage(@CookieValue(value="token",defaultValue="none")String token, Model model, @PathVariable("id") int id){
        UserDTO userDTO=null;
        if(token!=null && !token.equals("none")){
            userDTO= utilisateurProxy.findById(token,tokenService.decode(token));
        }
        try{
            int userId = tokenService.decode(token);
            Paniers panier = paniersProxy.getPanier(token, userId, id);
            model.addAttribute("panier", panier);
        }catch(Error error){
            error.printStackTrace();
        }
        return "update";
    }

    @PostMapping("/put/{id}")
    public ModelAndView updatePanier(@CookieValue(value="token",defaultValue="none")String token,
                                     @PathVariable("id") int id,
                                     @ModelAttribute Paniers panier) {
        try {
            int userId = tokenService.decode(token);
            paniersProxy.updatePanier(token, userId, id, panier);
        }catch(Error error){
            error.printStackTrace();
        }
        return new ModelAndView("redirect:/paniers/home/");
    }

    @GetMapping("/listeJeux")
    public ModelAndView redirectToListeJeux(){
        return new ModelAndView(new RedirectView("http://localhost:9007/jeux"));
    }

    @GetMapping("/commander")
    public String commander(@CookieValue(value="token",defaultValue="none")String token){
        UserDTO userDTO=null;
        if(token!=null && !token.equals("none")){
            userDTO= utilisateurProxy.findById(token,tokenService.decode(token));
        }
        try{
            int userId = tokenService.decode(token);
            Iterable<Paniers> paniers = paniersProxy.findByUserId(token, userId);
            for (Paniers panier : paniers) {
                paniersProxy.deletePanier(token, panier.getId());
            }
        }catch(Error error){
            error.printStackTrace();
        }
        return "validerCommande";
    }
}
