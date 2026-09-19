package org.example.blackjack.model;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

/*
    Class, which determines deck, consisting of 52 cards
*/
class Deck {
    private List<Card> cards = new ArrayList<>();

    /*
        Constructor, which creates 52 cards by combine ranks and suits
    */
    public Deck() {
        for (Card.Suit suit : Card.Suit.values()) {
            for (Card.Rank rank : Card.Rank.values()) {
                cards.add(new Card(rank, suit));
            }
        }
    }

    /*
        Method, which shuffle cards in deck
    */
    public void shuffle() {
        Collections.shuffle(cards);
    }

    /* 
        Method, which take fierst card from deck and delete it from deck
    */
    public Card takeCard() {
        return cards.remove(0);
    }

    /*
        Method, which shows, how many cards are in deck
    */
    public int size() {
        return cards.size();
    }
}