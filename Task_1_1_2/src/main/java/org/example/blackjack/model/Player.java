package org.example.blackjack.model;

/**
 *   Class, which determines player essense. It has hand with cards and method for communicate hand 
 *   with cards
 */
class Player {
    private Hand hand;

    /**
     *  Constructor, which initialize hand of player
     */
    public Player() {
         this.hand = new Hand();
    }

    /**
     *   Method, which return score of player's hand
     */
    public int getScore() {
        return hand.calculatePoints(); 
    }

    /**
     *   Method for taking card from deck into players hand
     */
    public void takeCard(Card card) {
        hand.addCard(card); 
    }

    /**
     *   Method, which returns flag, signifier, that player has more than 21 points and he is lose
     */
    public boolean isBusted() {
        return getScore() > 21;
    }

    /**
     *   Method, which clears player's hand from cards
     */
    public void resetHand() {
        hand.clear();
    }

    /** 
     *   Method, which returns player's hand
     */
    public Hand getHand() {
        return hand;
    }
}