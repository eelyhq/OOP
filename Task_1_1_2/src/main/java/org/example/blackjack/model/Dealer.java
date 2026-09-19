package org.example.blackjack.model;

/*
    Class which inherits from player and define dealer essense
*/
public class Dealer extends Player {
    /*
        Constructor, which calls it's ancestor constructor
    */
    public Dealer() {
        super();
    }

    /*
        Method, whichsays the dealer needs to top up the order
    */
    public boolean shouldHit() {
        return getScore() < 17; 
    }
}