package org.example.blackjack.model;

/**
 *    Class, which implement essence card.
 */
public class Card {
    /**
     *  Enum, which determines the card's rank and its value.
     */
    public enum Rank {
        TWO(2, "двойка"),
        THREE(3, "тройка"),
        FOUR(4, "четверка"),
        FIVE(5, "пятрка"),
        SIX(6, "шестёрка"),
        SEVEN(7, "семёрка"),
        EIGHT(8, "восьмёрка"),
        NINE(9, "девятка"),
        TEN(10, "десятка"),
        JACK(10, "валет"),
        QUEEN(10, "королева"),
        KING(10, "король"),
        ACE(11, "туз");     

        private final int value;
        private final String name;
    
        Rank(int value, String name) {
            this.value = value;
            this.name = name;
        }

        /**
         *  Getter for card value.
         */
        public int getValue() {
            return value;
        }

        /**
         * Getter for card name.
         */
        public String getName() {
            return name;
        }
    }

    /**
     *   Enum, which determines the card's suits.
     */
    public enum Suit {
        DIAMONDS("бубны"),
        SPADES("пики"),
        CLUBS("крести"),
        HEARTS("черви");

        private final String name;

        Suit(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
            }
    }

    private final Rank rank;
    private final Suit suit;

    /**
     *   Constructor, which establishes card's rank and suit.
     */
    public Card(Rank rank, Suit suit) {
        this.rank = rank;
        this.suit = suit;
    }

    /**
     *   Method, which returns card's rank from enum.
     */
    public Rank getRank() {
        return rank;
    }

    /**
     *   Method, which returns card's suit from enum.
     */
    public Suit getSuit() {
        return suit;
    }

    /**
     *  Method, which returns card's value from enum.
     */
    public int getValue() {
        return rank.getValue();
    }

    @Override
    public String toString() {
        return rank.getName() + " " + suit.getName() + " (" + rank.getValue() + ")";
    }
}