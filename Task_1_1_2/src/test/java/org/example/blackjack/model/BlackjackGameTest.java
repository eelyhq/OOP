package org.example.blackjack.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.example.blackjack.BlackjackGame;
import org.junit.jupiter.api.BeforeEach;
import java.io.ByteArrayInputStream;
import org.junit.jupiter.api.Test;

class BlackjackGameTest {
    private BlackjackGame game;

    @BeforeEach
    void setUp() {
        game = new BlackjackGame();
    }

    @Test
    public void testWinCheckPlayerWins() {
        game.getPlayer().takeCard(new Card(Card.Rank.TEN, Card.Suit.SPADES));
        game.getPlayer().takeCard(new Card(Card.Rank.TEN, Card.Suit.HEARTS));

        game.getDealer().takeCard(new Card(Card.Rank.TEN, Card.Suit.CLUBS));
        game.getDealer().takeCard(new Card(Card.Rank.SEVEN, Card.Suit.DIAMONDS));

        game.winCheck();

        assertEquals(1, game.getPlayerScore());
        assertEquals(0, game.getDealerScore());
    }

    @Test
    public void testWinCheckDealerBusted() {
        game.getPlayer().takeCard(new Card(Card.Rank.TEN, Card.Suit.SPADES));

        game.getDealer().takeCard(new Card(Card.Rank.TEN, Card.Suit.CLUBS));
        game.getDealer().takeCard(new Card(Card.Rank.TEN, Card.Suit.HEARTS));
        game.getDealer().takeCard(new Card(Card.Rank.FIVE, Card.Suit.DIAMONDS));

        game.winCheck();

        assertEquals(1, game.getPlayerScore());
        assertEquals(0, game.getDealerScore());
    }

    @Test
    public void testWinCheckPlayerBusted() {
        game.getPlayer().takeCard(new Card(Card.Rank.TEN, Card.Suit.SPADES));
        game.getPlayer().takeCard(new Card(Card.Rank.TEN, Card.Suit.CLUBS));
        game.getPlayer().takeCard(new Card(Card.Rank.TEN, Card.Suit.HEARTS));

        game.getDealer().takeCard(new Card(Card.Rank.FIVE, Card.Suit.DIAMONDS));
        game.getDealer().takeCard(new Card(Card.Rank.JACK, Card.Suit.DIAMONDS));

        game.winCheck();

        assertEquals(0, game.getPlayerScore());
        assertEquals(1, game.getDealerScore());
    }

    @Test
    public void testDraw() {
        game.getPlayer().takeCard(new Card(Card.Rank.TEN, Card.Suit.SPADES));
        game.getPlayer().takeCard(new Card(Card.Rank.TEN, Card.Suit.CLUBS));

        game.getDealer().takeCard(new Card(Card.Rank.TEN, Card.Suit.HEARTS));
        game.getDealer().takeCard(new Card(Card.Rank.JACK, Card.Suit.DIAMONDS));

        game.winCheck();

        assertEquals(0, game.getPlayerScore());
        assertEquals(0, game.getDealerScore());
    }

    @Test
    public void testCheckDealerHittingLogic() {
        game.getDealer().takeCard(new Card(Card.Rank.TEN, Card.Suit.HEARTS));
        game.getDealer().takeCard(new Card(Card.Rank.FIVE, Card.Suit.DIAMONDS));

        game.dealersTurn();
        assertTrue(game.getDealer().getScore() >= 17);
    }

    @Test
    public void testPlayersTurnInstantBlackjack() {
        BlackjackGame game = new BlackjackGame();
        game.getPlayer().takeCard(new Card(Card.Rank.ACE, Card.Suit.SPADES));
        game.getPlayer().takeCard(new Card(Card.Rank.TEN, Card.Suit.HEARTS));

        boolean roundFinished = game.playersTurn();

        assertTrue(game.getPlayer().isBlackJack());
        assertTrue(roundFinished);
        assertEquals(1, game.getPlayerScore());
    }

    @Test
    public void testWinCheckDealerWinsByScore() {
        game.getPlayer().takeCard(new Card(Card.Rank.TEN, Card.Suit.SPADES));
        game.getPlayer().takeCard(new Card(Card.Rank.SEVEN, Card.Suit.HEARTS));

        game.getDealer().takeCard(new Card(Card.Rank.TEN, Card.Suit.CLUBS));
        game.getDealer().takeCard(new Card(Card.Rank.NINE, Card.Suit.DIAMONDS));

        game.winCheck();

        assertEquals(0, game.getPlayerScore());
        assertEquals(1, game.getDealerScore());
    }

    
    @Test
    public void testPlayersTurnStand() {
        System.setIn(new ByteArrayInputStream("0\n".getBytes()));
        BlackjackGame testGame = new BlackjackGame();

        testGame.getPlayer().takeCard(new Card(Card.Rank.TEN, Card.Suit.SPADES));
        testGame.getPlayer().takeCard(new Card(Card.Rank.SEVEN, Card.Suit.HEARTS));

        boolean roundFinished = testGame.playersTurn();

        org.junit.jupiter.api.Assertions.assertFalse(roundFinished);
    }

    @Test
    public void testPlayersTurnInvalidInputThenStand() {
        System.setIn(new ByteArrayInputStream("9\n0\n".getBytes()));
        BlackjackGame testGame = new BlackjackGame();

        testGame.getPlayer().takeCard(new Card(Card.Rank.TEN, Card.Suit.SPADES));
        testGame.getPlayer().takeCard(new Card(Card.Rank.SEVEN, Card.Suit.HEARTS));

        boolean roundFinished = testGame.playersTurn();
        org.junit.jupiter.api.Assertions.assertFalse(roundFinished);
    }

    @Test
    public void testPlayersTurnHitAndBust() {
        System.setIn(new ByteArrayInputStream("1\n".getBytes()));
        BlackjackGame testGame = new BlackjackGame();

        testGame.getPlayer().takeCard(new Card(Card.Rank.SEVEN, Card.Suit.SPADES));
        testGame.getPlayer().takeCard(new Card(Card.Rank.SEVEN, Card.Suit.HEARTS));
        testGame.getPlayer().takeCard(new Card(Card.Rank.SEVEN, Card.Suit.CLUBS));

        testGame.getDealer().takeCard(new Card(Card.Rank.TEN, Card.Suit.DIAMONDS));

        boolean roundFinished = testGame.playersTurn();

        assertTrue(roundFinished);
        assertEquals(1, testGame.getDealerScore());
    }
}
