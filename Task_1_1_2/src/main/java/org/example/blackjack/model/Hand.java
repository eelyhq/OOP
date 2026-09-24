package org.example.blackjack.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *   Class, which determines essense hand, consists of cards.
 */
public class Hand {
    private List<Card> cards = new ArrayList<>();
    private int demotedAces = 0;

    /**
     *   Method for adding card to hand from deck.
     */
    public void addCard(Card card) { 
        cards.add(card);
    }

    /**
     *   Method for clearing hand from cards.
     */
    public void clear() {
        cards.clear();
        demotedAces = 0;
    }

    /**
     *   Method, which calculete sum of points by player's cards in hand. 
     *   also are taken into account demoted Aces.
     */
    public int calculatePoints() {
        int points = 0;
        int aceNum = 0;
        
        for (Card card : cards) {
            points += card.getValue();
            if (card.getRank() == Card.Rank.ACE) {
                aceNum++;
            }
        }
        demotedAces = 0;
        while (points > 21 && aceNum > 0) {
            aceNum--;
            points -= 10;
            demotedAces++;
        }
        
        return points;
    }

    /**
     *   Method, which return cards in hand.
     */
    public List<Card> getCards() {
        return  Collections.unmodifiableList(cards);
    }

    @Override
    public String toString() {
        calculatePoints();

        int acesAsOne = this.demotedAces;
        StringBuilder sb = new StringBuilder("[");

        for (int i = 0; i < cards.size(); i++) {
            Card card = cards.get(i);

            if (card.getRank() == Card.Rank.ACE && acesAsOne > 0) {
                sb.append(card.getRank().getName()).append(" ")
                    .append(card.getSuit().getName()).append(" (1)");
                acesAsOne--;
            } else {
                sb.append(card.toString());
            }

            if (i < cards.size() - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    /**
     *   Method, which returns number of cards in hand.
     */
    public int size() {
        return cards.size();
    }
       
}