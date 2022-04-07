package com.example.jeux_frontend.proxy;

import com.example.jeux_frontend.model.Game;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;


@FeignClient(name="gateway")
@RequestMapping("/jeux")
@Component
public interface GameProxy {

    @GetMapping("/{id}")
    Game getJeux(@PathVariable int id);

}
