package org.example.blackjack.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
    private Player player;

    @BeforeEach
    void setUp() {
        player = new Player();
    }

    @Test   
    public void testNewPlayerShouldHaveEmptyHand() {
        assertEquals(0, player.getScore());
        assertEquals(0, player.getHand().size());
        assertFalse(player.isBusted());
    }

    @Test
    public void testTakingCards() {
        Card card = new Card(Card.Rank.THREE, Card.Suit.DIAMONDS);
        Card card2 = new Card(Card.Rank.QUEEN, Card.Suit.HEARTS);
        Card card3 = new Card(Card.Rank.SIX, Card.Suit.SPADES);
        Card card4 = new Card(Card.Rank.JACK, Card.Suit.CLUBS);

        player.takeCard(card);
        player.takeCard(card2);
        player.takeCard(card3);
        assertEquals(19, player.getScore());
        assertFalse(player.isBusted());
        
        player.takeCard(card4);
        assertEquals(29, player.getScore());
        assertTrue(player.isBusted());
    }

    @Test
    public void testDemotedAces() {
        Card card = new Card(Card.Rank.ACE, Card.Suit.DIAMONDS);
        Card card2 = new Card(Card.Rank.NINE, Card.Suit.HEARTS);
        Card card3 = new Card(Card.Rank.ACE, Card.Suit.CLUBS);
        
        player.takeCard(card);
        player.takeCard(card2);
        assertEquals(20, player.getScore());
        assertFalse(player.isBusted());

        player.takeCard(card3);
        assertEquals(21, player.getScore());
        assertFalse(player.isBusted());
    }
        
    @Test
    public void testResetPlayersHand() {
        Card card = new Card(Card.Rank.THREE, Card.Suit.DIAMONDS);
        Card card2 = new Card(Card.Rank.QUEEN, Card.Suit.HEARTS);

        player.takeCard(card);
        player.takeCard(card2);
        
        assertEquals(2, player.getHand().size());
        
        player.resetHand();
        assertEquals(0, player.getScore());

        assertEquals(0, player.getHand().size());        
    }
}