package com.ivanfrias.myapi.Services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class UsersServiceImplTest {
    @Mock
    private PlayerRepository playerRepository;

    @InjectMocks
    private PlayerServiceImpl playerService;

    private Player player;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        player = Mockito.mock(Player.class);
    }

    @Test
    void testGetFreePlayersOk() {
        List<Player> mockFreePlayers = Arrays.asList(player);

        when(playerRepository.findAllByUserIdEqualsZero()).thenReturn(mockFreePlayers);
        List<Player> freePlayers = playerService.getFreePlayers();
        Assertions.assertEquals(mockFreePlayers.size(), freePlayers.size());
    }

    @Test
    void testGetFreePlayersEmpty() {
        List<Player> mockFreePlayers = new ArrayList<>();

        when(playerRepository.findAllByUserIdEqualsZero()).thenReturn(mockFreePlayers);
        List<Player> freePlayers = playerService.getFreePlayers();
        Assertions.assertEquals(mockFreePlayers.size(), freePlayers.size());
    }

//    @Test
//    public void getPlayerByIdResponseOk() {
//        Player mockPlayer = new Player(1L, "Gavi", "Medio", "Spain", 6, 0L, 1_000_000.0);
//        Optional<Player> player = Optional.of(mockPlayer);
//
//        when(playerRepository.findById(Mockito.anyLong())).thenReturn(player);
//        ResponseEntity<Player> responseplayer = playerService.getPlayerById(mockPlayer.getId());
//        Assertions.assertEquals(ResponseEntity.status(HttpStatus.OK).body(mockPlayer), responseplayer);
//    }
//
//    @Test
//    public void getPlayerByIdResponseBadRequest() {
//        Player mockPlayer = new Player(1L, "Gavi", "Medio", "Spain", 6, 0L, 1_000_000.0);
//        Optional<Player> player = playerRepository.findById(1L);
//
//        when(playerRepository.findById(2L)).thenReturn(player);
//        ResponseEntity<Player> responseplayer = playerService.getPlayerById(2L);
//        Assertions.assertEquals(ResponseEntity.status(HttpStatus.BAD_REQUEST).build(), responseplayer);
//    }
}


















