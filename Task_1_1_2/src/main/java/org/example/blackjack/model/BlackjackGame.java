package org.example.blackjack.model;

import java.util.Scanner;

/**
 *    Basic game class
 */
public class BlackjackGame {
    Scanner scanner = new Scanner(System.in);
    Deck deck;
    Player player;
    Dealer dealer;
    private int roundNum = 1;

    int playerScore = 0;
    int dealerScore = 0;

    /**
     *   Constructor, which initialize essense
     */
    public BlackjackGame() {
        this.deck = new Deck();
        this.player = new Player();
        this.dealer = new Dealer();
    }

    private void printPlayerCards() {
       System.out.println("Ваши карты: " + player.getHand() + " => " + player.getScore());
    }

    private void printDealerCards(boolean closeCard) {
        if (closeCard) {
            System.out.println("Карты дилера: [" + dealer.getHand().getCards().get(0) + ", <закрытая карта>]");
        } else {
            System.out.println("Карты дилера: " + dealer.getHand() + " => " + dealer.getScore());
        }
    }

    private void favorite() {
        if (playerScore > dealerScore) {
            System.out.println(" в вашу пользу.");
        } else if (playerScore < dealerScore) {
            System.out.println(" в пользу дилера.");
        }
    }   
    
    void winCheck() {
        if (player.getScore() > dealer.getScore() || dealer.isBusted()) {
            playerScore++;
            System.out.print("Вы выиграли раунд! Счет " + playerScore + ":" + dealerScore);
            favorite();
        } else if (player.getScore() < dealer.getScore()) {
            dealerScore++;
            System.out.print("Дилер выиграл раунд! Счет " + playerScore + ":" + dealerScore);
            favorite();
        } else {
            System.out.print("Ничья! Счет " + playerScore + ":" + dealerScore);
            favorite();
        }
    }

    boolean playersTurn() {
        if (player.getScore() == 21) {
            System.out.print("Блэкджек! Вы выиграли раунд! Счет " + playerScore + ":" + dealerScore);
            favorite();
            playerScore++;
            return true;
        }
        
        System.out.println("\nВаш ход");
        System.out.println("-------");
        while (true) {
            System.out.println("Введите “1”, чтобы взять карту, и “0”, чтобы остановиться...");

            int playerMove = scanner.nextInt();

            if (playerMove == 0) {
                return false;
            } else if (playerMove == 1) {
                Card card = deck.takeCard();
                System.out.println("Вы открыли карту " + card.toString());
                player.takeCard(card);
                printPlayerCards();
                printDealerCards(true);

                if (player.isBusted()) {
                    dealerScore++;
                    System.out.print("Дилер выиграл раунд, у вас перебор! Счет " + playerScore + ":" + dealerScore);
                    favorite();
                    return true;
                }
            } else {
                System.out.println("Неверная команда");
            }   
        }
    }

    void dealersTurn() {
        System.out.println("\nХод дилера");
        System.out.println("-------");

        System.out.println("Дилер открывает закрытую карту " + dealer.getHand().getCards().get(1));

        printPlayerCards();
        printDealerCards(false);

        while (dealer.shouldHit()) {
            Card card = deck.takeCard();
            System.out.println("Дилер открывает карту " + card.toString());
            dealer.takeCard(card);
            printPlayerCards();
            printDealerCards(false);
        }

        winCheck();
    }

    private void playRound() {
        player.resetHand();
        dealer.resetHand();

        deck = new Deck();
        deck.shuffle();

        player.takeCard(deck.takeCard());
        dealer.takeCard(deck.takeCard());

        player.takeCard(deck.takeCard());
        dealer.takeCard(deck.takeCard());

        System.out.println("Дилер раздал карты");

        printPlayerCards();
        printDealerCards(true);

        boolean roundFinished = playersTurn();

        if (!roundFinished){
            dealersTurn();
        }
    }

    /**
     *   Method, from which game is starts
     */
    public void start() {
        System.out.println("Добро пожаловать в Блэкджек!");

        while (true) {
            System.out.println("\n\nРаунд " + roundNum);

            playRound();

            roundNum++;
        }
    }
}
