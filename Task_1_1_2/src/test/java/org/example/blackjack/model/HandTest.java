package org.example.blackjack.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class HandTest {
    @Test 
    public void testAddingAndClearingHand() {
        Hand hand = new Hand();

        assertEquals(0, hand.size());

        Card card = new Card(Card.Rank.JACK, Card.Suit.CLUBS);
            
        hand.addCard(card);
        assertEquals(1, hand.size());

        hand.clear();
        assertEquals(0, hand.size());
    }

    @Test
    public void testPointsNum() {
        Hand hand = new Hand();
        assertEquals(0, hand.calculatePoints());

        Card card = new Card(Card.Rank.QUEEN, Card.Suit.CLUBS);
        hand.addCard(card);
        assertEquals(10, hand.calculatePoints());

        Card card2 = new Card(Card.Rank.NINE, Card.Suit.DIAMONDS);
        hand.addCard(card2);
        assertEquals(19, hand.calculatePoints());

        Card card3 = new Card(Card.Rank.ACE, Card.Suit.HEARTS);
        hand.addCard(card3);
        assertEquals(20, hand.calculatePoints());

        assertEquals(card, hand.getCards().get(0));
        assertEquals(card2, hand.getCards().get(1));
        assertEquals(card3, hand.getCards().get(2));

        hand.clear();

        hand.addCard(card3);
        hand.addCard(card2);

        assertEquals(20, hand.calculatePoints());
    }

    @Test
    public void testStingHand() {
        Hand hand = new Hand();

        Card card = new Card(Card.Rank.KING, Card.Suit.CLUBS);
        hand.addCard(card);

        Card card2 = new Card(Card.Rank.TWO, Card.Suit.DIAMONDS);
        hand.addCard(card2);

        assertEquals("[король крести (10), двойка бубны (2)]", hand.toString());
    }
    
}
