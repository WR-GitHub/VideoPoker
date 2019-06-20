/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package videopoker;

import java.util.Arrays;
import java.util.Scanner;

/**
 *
 * @author WR
 */
public class VideoPoker {
    
    private static String[] deck = {
        "\u2661A", "\u2661K", "\u2661Q", "\u2661J", "\u266110", "\u26619", "\u26618", "\u26617", "\u26616", "\u26615", "\u26614", "\u26613", "\u26612",  
        "\u2662A", "\u2662K", "\u2662Q", "\u2662J", "\u266210", "\u26629", "\u26628", "\u26627", "\u26626", "\u26625", "\u26624", "\u26623", "\u26622", 
        "\u2663A", "\u2663K", "\u2663Q", "\u2663J", "\u266310", "\u26639", "\u26638", "\u26637", "\u26636", "\u26635", "\u26634", "\u26633", "\u26632", 
        "\u2660A", "\u2660K", "\u2660Q", "\u2660J", "\u266010", "\u26609", "\u26608", "\u26607", "\u26606", "\u26605", "\u26604", "\u26603", "\u26602"
    };
    
    public static String[] shuffleDeck(String[] gotDeck) {
        String[] shuffledCards = gotDeck;
        
        //Shuffle
        String randomCard;
        int randomCardIndex;
        for (int i=0; i<shuffledCards.length; i++) {
            randomCardIndex = (int)(Math.random()*52);
            randomCard = shuffledCards[randomCardIndex];
            shuffledCards[randomCardIndex] = shuffledCards[i];
            shuffledCards[i] = randomCard;
        }
        
        return shuffledCards;
    }
    
    public static boolean validateInput(String gotString) {
        boolean valid = false;
        try {
            //Check if any data entered
            if (gotString.length() == 0) {
                System.out.println("No data entered");
                return false;
            }
            //Split string
            String[] numbersToDiscard = gotString.split(" ");
            
            //Check if only 0 entered
            if (numbersToDiscard.length == 1 && numbersToDiscard[0].equals("0")) {
                System.out.println("No cards to discard");
                return true;
            }
            
            //Check if not too many data entered
            if (numbersToDiscard.length > 5) {
                System.out.println("Too much data (max 5 numbers)");
                return false;
            }
            
            //Check if only numbers and only [1-5] are entered
            int[] numbers = {0, 0, 0, 0, 0};
            for (int n=0; n<numbersToDiscard.length; n++) {
                try {
                    numbers[n] = Integer.parseInt(numbersToDiscard[n]);
                } catch (Exception e) {
                    System.out.println("Entered data contains not a number or number format is bad: "+numbersToDiscard[n]);
                    return false;
                }
                if (numbers[n]<1 || numbers[n]>5) {
                    System.out.println("Numbers should be in the range [1-5]");
                    return false;
                }
            }
            
            //Check no duplicate values entered
            for (int j=0; j<numbersToDiscard.length-1; j++) {
                for (int k=j+1; k<numbersToDiscard.length; k++) {
                    if (numbersToDiscard[k].equals(numbersToDiscard[j])) {
                        System.out.println("Duplicate numbers found");
                        return false;
                    }
                }
            }
            
            //Valid entry
            System.out.println("Cards to change: "+gotString);
            valid = true;
            
        } catch (Exception e) {
            System.out.println("Exception on input string: "+gotString);
        }
        return valid;
    }
    
    public static String[] exchangeCards (String[] gotShuffledDeck, String gotCardsToExchange) {
        String[] newHand = { gotShuffledDeck[0], gotShuffledDeck[1], gotShuffledDeck[2], gotShuffledDeck[3], gotShuffledDeck[4] };
        String[] numbersToDiscard = gotCardsToExchange.split(" ");
        int currentCardForChangeIndex = 5;
        for (String c : numbersToDiscard) {
            switch (Integer.parseInt(c)) {
                case 1: newHand[0] = gotShuffledDeck[currentCardForChangeIndex];
                        currentCardForChangeIndex++;
                        break;
                case 2: newHand[1] = gotShuffledDeck[currentCardForChangeIndex];
                        currentCardForChangeIndex++;
                        break;
                case 3: newHand[2] = gotShuffledDeck[currentCardForChangeIndex];
                        currentCardForChangeIndex++;
                        break;
                case 4: newHand[3] = gotShuffledDeck[currentCardForChangeIndex];
                        currentCardForChangeIndex++;
                        break;
                case 5: newHand[4] = gotShuffledDeck[currentCardForChangeIndex];
                        currentCardForChangeIndex++;
                        break;
                default: break;
            }
        }
        return newHand;
    }
    
    public static int evaluateHand(String[] gotNewHand) {
        int winSum = 0;
        int countSuitHarts = 0;
        int countSuitDiamonds = 0;
        int countSuitClubs = 0;
        int countSuitSpades = 0;
        int countRankAces = 0;
        int countRankKings = 0;
        int countRankQueens = 0;
        int countRankJacks = 0;
        int countRankTens = 0;
        int countRankNines = 0;
        int countRankEights = 0;
        int countRankSevens = 0;
        int countRankSixes = 0;
        int countRankFives = 0;
        int countRankFours = 0;
        int countRankThrees = 0;
        int countRankTwos = 0;
        int countNumberOfRanks = 0;
        int maxSuite = 0;
        int minRank = 12;
        int maxRank = 0;
        int minRankAceMin = 12;
        int maxRankAceMin = 0;
        boolean quartet = false;
        boolean triples = false;
        for (int i=0; i<5; i++) {
            String[] cardSymbol = gotNewHand[i].split("");
            switch (cardSymbol[0]) {
                case "\u2661":  countSuitHarts++;
                                if (countSuitHarts>maxSuite) { maxSuite++; }
                                break;
                case "\u2662":  countSuitDiamonds++;
                                if (countSuitDiamonds>maxSuite) { maxSuite++; }
                                break;
                case "\u2663":  countSuitClubs++;
                                if (countSuitClubs>maxSuite) { maxSuite++; }
                                break;
                case "\u2660":  countSuitSpades++;
                                if (countSuitSpades>maxSuite) { maxSuite++; }
                                break;
            }
            switch (cardSymbol[1]) {
                case "A":   countRankAces++;
                            if (maxRank<12) { maxRank = 12; }
                            if (minRankAceMin>0) { minRankAceMin = 0; }
                            if (countRankAces==4) { quartet = true; triples = false; }
                            if (countRankAces==3) { triples = true; }
                            if (countRankAces==1) { countNumberOfRanks++; }
                            break;
                case "K":   countRankKings++;
                            if (minRank>11) { minRank = 11; }
                            if (maxRank<11) { maxRank = 11; }
                            if (maxRankAceMin<12) { maxRankAceMin = 12; }
                            if (countRankKings==4) { quartet = true; triples = false; }
                            if (countRankKings==3) { triples = true; }
                            if (countRankKings==1) { countNumberOfRanks++; }
                            break;
                case "Q":   countRankQueens++;
                            if (minRank>10) { minRank = 10; }
                            if (maxRank<10) { maxRank = 10; }
                            if (minRankAceMin>11) { minRankAceMin = 11; }
                            if (maxRankAceMin<11) { maxRankAceMin = 11; }
                            if (countRankQueens==4) { quartet = true; triples = false; }
                            if (countRankQueens==3) { triples = true; }
                            if (countRankQueens==1) { countNumberOfRanks++; }
                            break;
                case "J":   countRankJacks++;
                            if (minRank>9) { minRank = 9; }
                            if (maxRank<9) { maxRank = 9; }
                            if (minRankAceMin>10) { minRankAceMin = 10; }
                            if (maxRankAceMin<10) { maxRankAceMin = 10; }
                            if (countRankJacks==4) { quartet = true; triples = false; }
                            if (countRankJacks==3) { triples = true; }
                            if (countRankJacks==1) { countNumberOfRanks++; }
                            break;
                case "1":   countRankTens++;
                            if (minRank>8) { minRank = 8; }
                            if (maxRank<8) { maxRank = 8; }
                            if (minRankAceMin>9) { minRankAceMin = 9; }
                            if (maxRankAceMin<9) { maxRankAceMin = 9; }
                            if (countRankTens==4) { quartet = true; triples = false; }
                            if (countRankTens==3) { triples = true; }
                            if (countRankTens==1) { countNumberOfRanks++; }
                            break;
                case "9":   countRankNines++;
                            if (minRank>7) { minRank = 7; }
                            if (maxRank<7) { maxRank = 7; }
                            if (minRankAceMin>8) { minRankAceMin = 8; }
                            if (maxRankAceMin<8) { maxRankAceMin = 8; }
                            if (countRankNines==4) { quartet = true; triples = false; }
                            if (countRankNines==3) { triples = true; }
                            if (countRankNines==1) { countNumberOfRanks++; }
                            break;
                case "8":   countRankEights++;
                            if (minRank>6) { minRank = 6; }
                            if (maxRank<6) { maxRank = 6; }
                            if (minRankAceMin>7) { minRankAceMin = 7; }
                            if (maxRankAceMin<7) { maxRankAceMin = 7; }
                            if (countRankEights==4) { quartet = true; triples = false; }
                            if (countRankEights==3) { triples = true; }
                            if (countRankEights==1) { countNumberOfRanks++; }
                            break;            
                case "7":   countRankSevens++;
                            if (minRank>5) { minRank = 5; }
                            if (maxRank<5) { maxRank = 5; }
                            if (minRankAceMin>6) { minRankAceMin = 6; }
                            if (maxRankAceMin<6) { maxRankAceMin = 6; }
                            if (countRankSevens==4) { quartet = true; triples = false; }
                            if (countRankSevens==3) { triples = true; }
                            if (countRankSevens==1) { countNumberOfRanks++; }
                            break;
                case "6":   countRankSixes++;
                            if (minRank>4) { minRank = 4; }
                            if (maxRank<4) { maxRank = 4; }
                            if (minRankAceMin>5) { minRankAceMin = 5; }
                            if (maxRankAceMin<5) { maxRankAceMin = 5; }
                            if (countRankSixes==4) { quartet = true; triples = false; }
                            if (countRankSixes==3) { triples = true; }
                            if (countRankSixes==1) { countNumberOfRanks++; }
                            break;
                case "5":   countRankFives++;
                            if (minRank>3) { minRank = 3; }
                            if (maxRank<3) { maxRank = 3; }
                            if (minRankAceMin>4) { minRankAceMin = 4; }
                            if (maxRankAceMin<4) { maxRankAceMin = 4; }
                            if (countRankFives==4) { quartet = true; triples = false; }
                            if (countRankFives==3) { triples = true; }
                            if (countRankFives==1) { countNumberOfRanks++; }
                            break;
                case "4":   countRankFours++;
                            if (minRank>2) { minRank = 2; }
                            if (maxRank<2) { maxRank = 2; }
                            if (minRankAceMin>3) { minRankAceMin = 3; }
                            if (maxRankAceMin<3) { maxRankAceMin = 3; }
                            if (countRankFours==4) { quartet = true; triples = false; }
                            if (countRankFours==3) { triples = true; }
                            if (countRankFours==1) { countNumberOfRanks++; }
                            break;
                case "3":   countRankThrees++;
                            if (minRank>1) { minRank = 1; }
                            if (maxRank<1) { maxRank = 1; }
                            if (minRankAceMin>2) { minRankAceMin = 2; }
                            if (maxRankAceMin<2) { maxRankAceMin = 2; }
                            if (countRankThrees==4) { quartet = true; triples = false; }
                            if (countRankThrees==3) { triples = true; }
                            if (countRankThrees==1) { countNumberOfRanks++; }
                            break;
                case "2":   countRankTwos++;
                            if (minRank>0) { minRank = 0; }
                            if (minRankAceMin>1) { minRankAceMin = 1; }
                            if (maxRankAceMin<1) { maxRankAceMin = 1; }
                            if (countRankTwos==4) { quartet = true; triples = false; }
                            if (countRankTwos==3) { triples = true; }
                            if (countRankTwos==1) { countNumberOfRanks++; }
                            break;            
            }
        }
        
        //Check Royal Flush
        if (maxSuite == 5 && maxRank == 12 && minRank == 8) {
            System.out.println("You got ROYAL FLUSH");
            return winSum=800;
        }
        
        //Check Straight Flush
        if (maxSuite == 5 && (maxRank - minRank == 4)) {
            System.out.println("You got Straight Flush");
            return winSum=50;
        }
        
        //Check Four of a kind
        if (quartet) {
            System.out.println("You got Four of a kind");
            return winSum=25;
        }
        
        //Check Full House
        if (triples && countNumberOfRanks == 2) {
            System.out.println("You got Full House");
            return winSum=9;
        }
        
        //Check Flush
        if (maxSuite == 5) {
            System.out.println("You got Flush");
            return winSum=6;
        }
        
        //Check Straight
        if ((maxRank - minRank == 4 && countNumberOfRanks == 5) || (maxRankAceMin - minRankAceMin == 4 && countNumberOfRanks == 5)) {
            System.out.println("You got Straight");
            return winSum=4;
        }
        
        //Check Three of a kind
        if (triples) {
            System.out.println("You got Three of a kind");
            return winSum=3;
        }
        
        //Check Two Pair
        if (countNumberOfRanks == 3) {
            System.out.println("You got Two Pair");
            return winSum=2;
        }
        
        //Check Jacks or Better
        if (countNumberOfRanks == 4 && (countRankAces == 2 || countRankKings == 2 || countRankQueens == 2 || countRankJacks == 2)) {
            System.out.println("You got Jacks or Better");
            return winSum=1;
        }
        
        System.out.println("Sorry, not winning hand");
        return winSum;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        //Initial card deck
        System.out.println("Initial deck");
        for (String card : deck) {
            System.out.print(card+" ");
        }
        
        //New line
        System.out.println("");
        
        String[] shuffledDeck = shuffleDeck(deck);
        
        //Shuffled card deck (comment out to hide)
        System.out.println("Shuffled deck (comment out in code to hide it)");
        for (String card : shuffledDeck) {
            System.out.print(card+" ");
        }
        
        //New line
        System.out.println("");
        
        //Loop until valid input received
        boolean validated = false;
        String inputString = "";
        do {
        //Hand
        System.out.println("Your hand");
        System.out.println(shuffledDeck[0]+"(1) "+shuffledDeck[1]+"(2) "+shuffledDeck[2]+"(3) "+shuffledDeck[3]+"(4) "+shuffledDeck[4]+"(5)");

        //Discard
        System.out.println("Choose 1 to 5 cards to discard, separating numbers by \"space\" (0 - to leave current cards)");
        Scanner input = new Scanner(System.in);
        inputString = input.nextLine();
        validated = validateInput(inputString);
        } while (!validated);
        
        //Deal new cards
        System.out.println("Your new hand");
        String[] newHand = exchangeCards(shuffledDeck, inputString);
        System.out.println(Arrays.toString(newHand).replace("[", "").replace("]", "").replace(",", ""));
        
        //Payout
        int payout = evaluateHand(newHand);
        System.out.println("Your prize: "+payout);
        
        //Test
//        System.out.println("Test hand");
//        String[] testHand = { "\u2661A", "\u2661K", "\u2661Q", "\u2661J", "\u266110" };
//        System.out.println(Arrays.toString(testHand));
//        int payoutTest = evaluateHand(testHand);
//        System.out.println("Your prize: "+payoutTest);
    
    }
    
}
