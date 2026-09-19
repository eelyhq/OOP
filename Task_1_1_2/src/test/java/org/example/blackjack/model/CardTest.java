package org.example.blackjack.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class CardTest {
    @Test 
    public void testCardGetters() {
        Card card = new Card(Card.Rank.ACE, Card.Suit.SPADES);

        assertEquals(Card.Rank.ACE, card.getRank());
        assertEquals(Card.Suit.SPADES, card.getSuit());
        assertEquals(11, card.getValue());
    }

    @Test
    public void testStringCard() {
        Card card = new Card(Card.Rank.SIX, Card.Suit.HEARTS);

        assertEquals("шестёрка черви (6)", card.toString());
    }

    @Test 
    public void testEnums() {
        assertEquals(3, Card.Rank.THREE.getValue());
        assertEquals("десятка", Card.Rank.TEN.getName());
        assertEquals("бубны", Card.Suit.DIAMONDS.getName());

        assertEquals(13, Card.Rank.values().length);
        assertEquals(4, Card.Suit.values().length);
        
        assertEquals(Card.Rank.ACE, Card.Rank.valueOf("ACE"));
        assertEquals(Card.Suit.SPADES, Card.Suit.valueOf("SPADES"));
    }   
}