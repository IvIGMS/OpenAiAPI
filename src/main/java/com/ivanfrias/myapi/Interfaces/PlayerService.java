package com.ivanfrias.myapi.Interfaces;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public interface PlayerService {
    public ArrayList<Object> createPlayer(PlayerDTO playerDTO);
    public List<Player> getFreePlayers();
    public List<Player> getTeam(Long id);
    public Long verifyToken(HttpServletRequest request);
    public Player getPlayerById(Long id);
    public boolean playerUp(Long playerId, Long userId, PlayerDTOUp playerDTOUp);
    public boolean playerDown(Long playerId, Long userId, PlayerDTOUp playerDTOUp);
}
