package com.example.player.service;

import com.example.player.model.Player;
import com.example.player.model.PlayerRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;

@Service
public class PlayerH2Service {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public ArrayList<Player> getPlayers() {
        return new ArrayList<>(jdbcTemplate.query("SELECT * FROM team", new PlayerRowMapper()));
    }

    public Player getPlayerById(int playerId) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM team WHERE playerId = ?", new PlayerRowMapper(), playerId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Player not found");
        }
    }

    public Player addPlayer(Player player) {
        jdbcTemplate.update("INSERT INTO team (playerName, jerseyNumber, role) VALUES (?, ?, ?)",
                player.getPlayerName(), player.getJerseyNumber(), player.getRole());
        return getPlayerById(player.getPlayerId());
    }

    public Player updatePlayer(int playerId, Player player) {
        jdbcTemplate.update("UPDATE team SET playerName = ?, jerseyNumber = ?, role = ? WHERE playerId = ?",
                player.getPlayerName(), player.getJerseyNumber(), player.getRole(), playerId);
        return getPlayerById(playerId);
    }

    public void deletePlayer(int playerId) {
        jdbcTemplate.update("DELETE FROM team WHERE playerId = ?", playerId);
    }
}
