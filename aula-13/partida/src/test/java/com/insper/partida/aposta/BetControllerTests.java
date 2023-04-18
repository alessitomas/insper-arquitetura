package com.insper.partida.aposta;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.insper.partida.equipe.Team;
import com.insper.partida.equipe.TeamController;
import com.insper.partida.equipe.TeamService;
import com.insper.partida.game.Game;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class BetControllerTests {

    MockMvc mockMvc;

    @InjectMocks
    BetController betController;

    @Mock
    BetService betService;

    @BeforeEach
    void setup() {
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(betController)
                .build();
    }

    @Test
    void test_listBets() throws Exception {
        // create a bet
        Bet bet = new Bet();
        bet.setResult(BetResult.HOME);
        bet.setStatus(BetStatus.WON);
        // create a game
        Game game = new Game();
        game.setIdentifier("123");
        // set the game to the bet
        bet.setGame(game);

        List<Bet> bets = new ArrayList<>();
        bets.add(bet);

        Mockito.when(betService.listBets()).thenReturn(bets);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/bet"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        Bet[] betsArray = mapper.readValue(content, Bet[].class);

        Assertions.assertEquals(1, betsArray.length);
        Assertions.assertEquals(BetResult.HOME, betsArray[0].getResult());
        Assertions.assertEquals(BetStatus.WON, betsArray[0].getStatus());


    }

    @Test
    void test_createBets() throws Exception {

        Bet bet = new Bet();
        bet.setResult(BetResult.HOME);

        Game game = new Game();
        game.setIdentifier("123");

        bet.setGame(game);

        Mockito.when(betService.saveBet(Mockito.any(Bet.class))).thenReturn(bet);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/bet")
                .contentType("application/json")
                .content("{\"result\": \"HOME\", \"game\": {\"identifier\": \"123\"}}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        Bet betResponse = mapper.readValue(content, Bet.class);

        Assertions.assertEquals(BetResult.HOME, betResponse.getResult());
    }

    @Test
    void test_verifyBets() throws Exception {
            
            Bet bet = new Bet();
            bet.setResult(BetResult.HOME);
            bet.setStatus(BetStatus.WON);
    
            Game game = new Game();
            game.setIdentifier("123");
    
            bet.setGame(game);
    
            Mockito.when(betService.verifyBet(Mockito.any(Integer.class))).thenReturn(bet);
    
            MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/bet/1/verify"))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andReturn();
    
            String content = result.getResponse().getContentAsString();
            ObjectMapper mapper = new ObjectMapper();
            Bet betResponse = mapper.readValue(content, Bet.class);
    
            Assertions.assertEquals(BetResult.HOME, betResponse.getResult());
            Assertions.assertEquals(BetStatus.WON, betResponse.getStatus());


    }



}
