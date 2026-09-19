package org.example.blackjack.model;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

class DealerTest {
    @Test
    public void testShouldHit() {
        Dealer dealer = new Dealer();

        Card card = new Card(Card.Rank.EIGHT, Card.Suit.HEARTS);
        Card card2 = new Card(Card.Rank.JACK, Card.Suit.SPADES);

        dealer.takeCard(card);
        assertTrue(dealer.shouldHit());
        dealer.takeCard(card2);
        assertFalse(dealer.shouldHit());
    }
}