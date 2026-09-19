package org.example.blackjack.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class DeckTest {
    @Test
    public void testDeckLength() {
        Deck deck = new Deck();
    
        assertEquals(52, deck.size());

        deck.takeCard();
        assertEquals(51, deck.size());
    }

    @Test
    public void testShuffle() {
        Deck deck = new Deck();
        deck.shuffle();
        assertEquals(52, deck.size());
       }   
}