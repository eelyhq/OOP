package org.example.blackjack.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BlackjackGameTest {
    private BlackjackGame game;

    @BeforeEach
    void setUp() {
        game = new BlackjackGame();
    }
    
    @Test
    public void testWinCheckPlayerWins() {
        game.player.takeCard(new Card(Card.Rank.TEN, Card.Suit.SPADES));
        game.player.takeCard(new Card(Card.Rank.TEN, Card.Suit.HEARTS));

        game.dealer.takeCard(new Card(Card.Rank.TEN, Card.Suit.CLUBS));
        game.dealer.takeCard(new Card(Card.Rank.SEVEN, Card.Suit.DIAMONDS));

        game.winCheck();

        assertEquals(1, game.playerScore);
        assertEquals(0, game.dealerScore);
    }
    
    @Test
    public void testWinCheckDealerBusted() {
        game.player.takeCard(new Card(Card.Rank.TEN, Card.Suit.SPADES)); 

        game.dealer.takeCard(new Card(Card.Rank.TEN, Card.Suit.CLUBS));
        game.dealer.takeCard(new Card(Card.Rank.TEN, Card.Suit.HEARTS));
        game.dealer.takeCard(new Card(Card.Rank.FIVE, Card.Suit.DIAMONDS));

        game.winCheck();

        assertEquals(1, game.playerScore);
        assertEquals(0, game.dealerScore);
    }   

    @Test
    public void testWinCheckPlayerBusted() {
        game.player.takeCard(new Card(Card.Rank.TEN, Card.Suit.SPADES)); 
        game.player.takeCard(new Card(Card.Rank.TEN, Card.Suit.CLUBS));
        game.player.takeCard(new Card(Card.Rank.TEN, Card.Suit.HEARTS));
        
        game.dealer.takeCard(new Card(Card.Rank.FIVE, Card.Suit.DIAMONDS));
        game.dealer.takeCard(new Card(Card.Rank.JACK, Card.Suit.DIAMONDS));
        
        game.winCheck();

        assertEquals(1, game.playerScore);
        assertEquals(0, game.dealerScore);
    } 
    
    @Test
    public void testDraw() {
        game.player.takeCard(new Card(Card.Rank.TEN, Card.Suit.SPADES)); 
        game.player.takeCard(new Card(Card.Rank.TEN, Card.Suit.CLUBS));
        
        game.dealer.takeCard(new Card(Card.Rank.TEN, Card.Suit.HEARTS));
        game.dealer.takeCard(new Card(Card.Rank.JACK, Card.Suit.DIAMONDS));
        
        game.winCheck();

        assertEquals(0, game.playerScore);
        assertEquals(0, game.dealerScore);
    } 
    
    @Test 
    public void testCheckDealerHittingLogic() {
        game.dealer.takeCard(new Card(Card.Rank.TEN, Card.Suit.HEARTS));
        game.dealer.takeCard(new Card(Card.Rank.FIVE, Card.Suit.DIAMONDS));

        game.dealersTurn();
        assertTrue(game.dealer.getScore() >= 17);
    }

    @Test
    public void testPlayersTurnInstantBlackjack() {
        BlackjackGame game = new BlackjackGame();
        game.player.takeCard(new Card(Card.Rank.ACE, Card.Suit.SPADES));
        game.player.takeCard(new Card(Card.Rank.TEN, Card.Suit.HEARTS));

        boolean roundFinished = game.playersTurn();

        assertTrue(roundFinished);
        assertEquals(1, game.playerScore);
    }
}