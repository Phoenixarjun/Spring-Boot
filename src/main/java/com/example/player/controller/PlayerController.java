package com.example.player.controller;

import com.example.player.model.Player;
import com.example.player.service.PlayerH2Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
public class PlayerController {

    @Autowired
    private PlayerH2Service playerService;

    @GetMapping("/players")
    public ArrayList<Player> getPlayers() {
        return playerService.getPlayers();
    }

    @GetMapping("/players/{playerId}")
    public Player getPlayerById(@PathVariable("playerId") int playerId) {
        return playerService.getPlayerById(playerId);
    }

    @PostMapping("/players")
    @ResponseStatus(HttpStatus.CREATED)
    public Player addPlayer(@RequestBody Player player) {
        return playerService.addPlayer(player);
    }

    @PutMapping("/players/{playerId}")
    public Player updatePlayer(@PathVariable("playerId") int playerId, @RequestBody Player player) {
        return playerService.updatePlayer(playerId, player);
    }

    @DeleteMapping("/players/{playerId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePlayer(@PathVariable("playerId") int playerId) {
        playerService.deletePlayer(playerId);
    }
}
