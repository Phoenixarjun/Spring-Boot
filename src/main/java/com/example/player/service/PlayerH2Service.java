package com.example.player.service;

import com.example.player.model.Player;
import com.example.player.model.PlayerRowMapper;
import com.example.player.repository.PlayerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;

@Service
public class PlayerH2Service implements PlayerRepository {

    @Autowired
    private JdbcTemplate db;

    @Override
    public ArrayList<Player> getPlayers() {
        return (ArrayList<Player>) db.query("SELECT * FROM team", new PlayerRowMapper());
    }

    @Override
    public Player getPlayerById(int playerId) {
        try {
            return db.queryForObject("SELECT * FROM team WHERE playerId = ?", new PlayerRowMapper(), playerId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Player not found with ID: " + playerId);
        }
    }

    @Override
    public Player addPlayer(Player player) {
        String sql = "INSERT INTO team (playerName, jerseyNumber, role) VALUES (?, ?, ?)";
        db.update(sql, player.getPlayerName(), player.getJerseyNumber(), player.getRole());
        return db.queryForObject("SELECT * FROM team WHERE playerName = ? AND jerseyNumber = ?",
                new PlayerRowMapper(), player.getPlayerName(), player.getJerseyNumber());
    }

    @Override
    public void deletePlayer(int playerId) {
        db.update("DELETE FROM team WHERE playerId = ?", playerId);
    }

    @Override
    public Player updatePlayer(int playerId, Player player) {
        if (player.getPlayerName() != null) {
            db.update("UPDATE team SET playerName = ? WHERE playerId = ?", player.getPlayerName(), playerId);
        }
        if (player.getJerseyNumber() != 0) {
            db.update("UPDATE team SET jerseyNumber = ? WHERE playerId = ?", player.getJerseyNumber(), playerId);
        }
        if (player.getRole() != null) {
            db.update("UPDATE team SET role = ? WHERE playerId = ?", player.getRole(), playerId);
        }
        return getPlayerById(playerId);
    }
}
