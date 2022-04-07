package com.example.jeux_frontend.controller;

import com.example.jeux_frontend.config.TokenService;
import com.example.jeux_frontend.model.*;
import com.example.jeux_frontend.proxy.CommentProxy;
import com.example.jeux_frontend.proxy.GameProxy;
import com.example.jeux_frontend.proxy.UtilisateurProxy;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/jeux/detail")
public class GameController {

    private GameProxy gameProxy;
    private CommentProxy commentProxy;
    private UtilisateurProxy utilisateurProxy;
    private TokenService tokenService ;

    public GameController(GameProxy gameProxy, CommentProxy commentProxy, UtilisateurProxy utilisateurProxy,TokenService tokenService) {
        this.commentProxy = commentProxy;
        this.gameProxy = gameProxy;
        this.utilisateurProxy = utilisateurProxy;
        this.tokenService=tokenService;
    }

    @GetMapping("/{id}")
    public ModelAndView getJeuById(@CookieValue(value="token",defaultValue="none")String token,ModelAndView modelAndView, @PathVariable("id") int idJeu) {
        UserDTO userDTO=null;
        if(token!=null && !token.equals("none")){
            userDTO= utilisateurProxy.findById(token,tokenService.decode(token));
        }
        String email = "";
        Game game;
        float rate;
        String emailById = "";
        int idByEmail = -1;
        String dateCreation = "";
        long nombreCommentaires;
        List<Comment> commentList = new ArrayList<>();
        try {
            game = gameProxy.getJeux(idJeu);
        } catch (Exception e) {
            game = null;
        }
        try {
            commentList =  commentProxy.findByJeu(idJeu);
            nombreCommentaires = commentProxy.getnombreCommentaire(idJeu);
            for (Comment comment: commentList) {
                emailById = utilisateurProxy.findById(token, comment.getUtilisateur()).getEmail();
                idByEmail = utilisateurProxy.findByEmail(email).getId();
                dateCreation = String.valueOf(comment.getDate_creation());

            }

        } catch (Exception e) {;
            nombreCommentaires = 0;
        }
        try {
            rate = commentProxy.moyenneCommentaire(idJeu);
        } catch (Exception e) {
            rate = 0;
            System.out.println("rate !");
        }
        modelAndView.addObject("userDTO",userDTO);
        modelAndView.addObject("game", game);
        modelAndView.addObject("comments", commentList);
        modelAndView.addObject("rate", rate);
        modelAndView.addObject("email", email);
        modelAndView.addObject("emailById", emailById);
        modelAndView.addObject("idByEmail", idByEmail);
        modelAndView.addObject("dateCreation", dateCreation);
        modelAndView.addObject("nbrComments", nombreCommentaires);
        modelAndView.setViewName("detailJeu");
        return modelAndView;
    }

    @GetMapping("/{id}/except/{idUser}")//N'EST PAS LIE AU HTML
    public ModelAndView getCommentExceptUser(@CookieValue(value="token",defaultValue="none")String token,ModelAndView modelAndView, @PathVariable("id") int id, @PathVariable("idUser") int idUser) {
        UserDTO userDTO=null;
        if(token!=null && !token.equals("none")){
            userDTO= utilisateurProxy.findById(token,tokenService.decode(token));

            Iterable<Comment> utilisateurExceptComment = commentProxy.getCommentaireJeuSaufUtilisateur(id, userDTO.getId());
            modelAndView.addObject("exceptUser", utilisateurExceptComment);

            modelAndView.addObject("userDTO",userDTO);
            modelAndView.addObject("buttonExcept", "except");
        }
        modelAndView.setViewName("detailJeu");

        return modelAndView;
    }



    @GetMapping("/{id}/only/{idUser}") //N'EST PAS LIE AU HTML
    public ModelAndView getCommentwithUser(@CookieValue(value="token",defaultValue="none")String token,ModelAndView modelAndView, @PathVariable("id") int id, @PathVariable("idUser") int utilisateur) {
        UserDTO userDTO=null;
        if(token!=null && !token.equals("none")){
            userDTO= utilisateurProxy.findById(token,tokenService.decode(token));
            Iterable<Comment> utilisateurOnlyComment = commentProxy.findByJeuAndUtilisateur(id, utilisateur);
            modelAndView.addObject("onlyUser", utilisateurOnlyComment);
            modelAndView.addObject("userDTO",userDTO);
            modelAndView.addObject("buttonOnly", "only");
        }
        modelAndView.setViewName("detailJeu");
        return modelAndView;
    }

    @GetMapping("{id}/deleteComment/{idComment}")
    public ModelAndView deleteComment(@CookieValue(value="token",defaultValue="none")String token,@PathVariable("id") int id, @PathVariable("idComment") int idComment) { //, @CookieValue(value = "Authorization", defaultValue = "none") String token

        try{
            commentProxy.deleteCommentaire(token,idComment);
        }catch(Error error){
            error.printStackTrace();
        }
        return new ModelAndView("redirect:/jeux/detail/{id}");
    }


    @GetMapping("/{id}/addComment")
    public ModelAndView afficherForm(@CookieValue(value="token",defaultValue="none")String token,ModelAndView modelAndView, @PathVariable("id") int id) {
        if(token==null || token.equals("none")){
            return new ModelAndView(new RedirectView("http://localhost:9007/jeux"));
        }
        UserDTO userDTO= utilisateurProxy.findById(token,tokenService.decode(token));
        modelAndView.addObject("userDTO",userDTO);
        Comment commentaire = new Comment(-1, "", 1, LocalDate.now(), userDTO.getId(), id, "");
        modelAndView.addObject("comment", commentaire);
        modelAndView.addObject("jeu", gameProxy.getJeux(id));
        modelAndView.setViewName("ajouterCommentaire");
        return modelAndView;
    }

    @PostMapping("{id}/addComment")
    public ModelAndView addComment(@CookieValue(value="token",defaultValue="none")String token, @PathVariable("id") int id, @ModelAttribute Comment commentaire, BindingResult bindingResult, HttpServletResponse response) {
        try{
            commentProxy.createCommentaire(token,commentaire);
            gameProxy.getJeux(id);
        }catch(Error error){
            error.printStackTrace();
        }
        return new ModelAndView("redirect:/jeux/detail/{id}");
    }


    @GetMapping("/{id}/updateComment/{idComment}")
    public ModelAndView afficherForm(@CookieValue(value="token",defaultValue="none")String token,ModelAndView modelAndView, @PathVariable("id") int id, @PathVariable("idComment") int idComment) {
        UserDTO userDTO=null;
        if(token!=null && !token.equals("none")){
            userDTO= utilisateurProxy.findById(token,tokenService.decode(token));

        }
        Comment comment = commentProxy.findById(idComment);
        modelAndView.addObject("userDTO",userDTO);
        Comment commentaire =  new Comment(comment.getIdComment(), comment.getTexte(), comment.getEvaluation(), comment.getDate_creation(), comment.getUtilisateur(), id, "");
        modelAndView.addObject("commentaire", commentaire);
        modelAndView.addObject("jeu", gameProxy.getJeux(id));
        modelAndView.setViewName("updateCommentaire");
        return modelAndView;
    }

    @PostMapping("{id}/updateComment/{idComment}")
    public ModelAndView updateComment(@CookieValue(value="token",defaultValue="none")String token, @PathVariable("id") int id, @PathVariable("idComment") int idComment, @ModelAttribute Comment commentaire, BindingResult bindingResult, HttpServletResponse response) {
        try{
            commentProxy.updateCommentaire(token,idComment, commentaire);
            gameProxy.getJeux(id);
        }catch(Error error){
            error.printStackTrace();
        }
        return new ModelAndView("redirect:/jeux/detail/{id}");
    }
}