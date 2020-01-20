package main;

import java.util.Scanner;
import java.io.IOException;
import java.util.Random; 


public class gameCommands {
    static public Scanner scan = new Scanner(System.in);
    static Random rand = new Random();
    static char cards[] = {'A', '2', '3', '4', '5', '6', '7', '8','9','T','J','Q','K'};
    static int bet;
    static int playersBalance = 500;
    
    public gameCommands()
    {

    }

    static public boolean init() {
        boolean valid = false;
        boolean returnValue = false;
        String player_choice;
        while(valid == false){
            System.out.print("Do you want to play blackjack, Yes or No? ");
            player_choice = scan.nextLine();
            if( //Checks if input is yes
                (player_choice.length() == 3) &&
                (player_choice.charAt(0) == 'Y' || player_choice.charAt(0) == 'y') &&
                (player_choice.charAt(1) == 'E' || player_choice.charAt(1) == 'e') &&
                (player_choice.charAt(2) == 'S' || player_choice.charAt(2) == 's')
            )
                {
                    valid = true;
                    returnValue = true;
                    break;
                }
            else if((player_choice.length() == 2) &&
                    (player_choice.charAt(0) == 'N' || player_choice.charAt(0) == 'n') &&
                    (player_choice.charAt(1) == 'O' || player_choice.charAt(1) == 'o')
            )
                {
                    valid = true;
                    returnValue = false;
                    clearConsole();

                }
            else
            {
                valid = false;
                clearConsole();
                System.out.println("Invalid input! Please enter valid input.");
                

            }
        }
        return returnValue;
    }

    public static void clearConsole() {  
        System.out.print("\033[H\033[2J");  
        System.out.flush();  
    } 

    public static void run()
    {
        clearConsole();
        System.out.println("You have $500 to use!");
        waitInput();
        clearConsole();
        while(true)
        {
            boolean status = true;
            while(status == true)
            {
                System.out.println("How much do you want to bet? Balance: $" + playersBalance);
                bet = scan.nextInt();
                if(bet > playersBalance)
                {
                    clearConsole();
                    System.out.println("Sorry but you dont have enough funds to bet that amount!");
                }
                else if(playersBalance == 0)
                {
                    status = false;
                    break;
                }
                else
                {
                    playersBalance = playersBalance - bet;
                    status = false;
                }
            }
            deal();
            clearConsole();
            boolean valid = false;
            boolean returnValue = false;
            String player_choice;
            while(valid == false){
                System.out.println("Do you want to play again? Yes or No? ");
                player_choice = scan.nextLine();
                if( //Checks if input is yes
                    (player_choice.length() == 3) &&
                    (player_choice.charAt(0) == 'Y' || player_choice.charAt(0) == 'y') &&
                    (player_choice.charAt(1) == 'E' || player_choice.charAt(1) == 'e') &&
                    (player_choice.charAt(2) == 'S' || player_choice.charAt(2) == 's')
                )
                    {
                        valid = true;
                        returnValue = true;
                        break;
                    }
                else if((player_choice.length() == 2) &&
                        (player_choice.charAt(0) == 'N' || player_choice.charAt(0) == 'n') &&
                        (player_choice.charAt(1) == 'O' || player_choice.charAt(1) == 'o')
                )
                    {
                        valid = true;
                        returnValue = false;
                        clearConsole();

                    }
                else
                {
                    valid = false;
                    clearConsole();
                    System.out.println("Invalid input! Please enter valid input.");
                }
            }
            if(returnValue == false)
            {
                break;
            }
        }
    }

    public static void deal()
    {
        int totalPlayersCards = 2;
        int totalDealersCards = 2;
        char playersCards[] = {'X', 'X', 'X', 'X'};
        playersCards[0] = cards[rand.nextInt(12)];
        playersCards[1] = cards[rand.nextInt(12)];
        char dealersCards[] = {'X', 'X', 'X', 'X'};
        dealersCards[0] = cards[rand.nextInt(12)];
        dealersCards[1] = cards[rand.nextInt(12)];
        boolean status = true;
        String inputt;
        int winner = 0;
        int crash = 0;
        while(status == true)
        {
            clearConsole();
            System.out.println("Current Balance: $" + playersBalance);
            System.out.println("Current Bet: $" + bet);
            System.out.println("Dealer's Hand: ");
            System.out.println("Card 1: " + dealersCards[0]);
            System.out.println("Card 2: ?");
            System.out.println();
            System.out.println();
            System.out.println("Your Hand: ");
            for(int x=0;x<totalPlayersCards;x++)
            {
                System.out.println("Card " + (x+1) + ": " + playersCards[x]);
            }
            System.out.println(counter(playersCards, totalPlayersCards));
            System.out.println();
            if(counter(playersCards, totalPlayersCards) == 21)
            {
                status = false;
                winner = 1;
                break;
            }
            else{
                System.out.println("Choose an option: ");
                System.out.println("a. Hit");
                System.out.println("b. Stand");
                inputt = scan.nextLine();

                if(inputt.equals("a") && totalPlayersCards != 4)
                {
                    totalPlayersCards++;
                    playersCards[totalPlayersCards-1] = cards[rand.nextInt(12)];
                    if(counter(playersCards, totalPlayersCards) == 21)
                    {
                        winner = 3;
                        status = false;
                        break;
                    }
                    if(counter(playersCards, totalPlayersCards) > 21)
                    {
                        winner = 2;
                        status = false;
                        break;
                    }
                }
                else if(inputt.equals("b"))
                {
                    totalDealersCards++;
                    dealersCards[totalDealersCards-1] = cards[rand.nextInt(12)];

                    if(counter(playersCards, totalPlayersCards) < 21 && counter(playersCards, totalPlayersCards) > counter(dealersCards, totalDealersCards))
                    {
                        winner = 1;
                        status = false;
                        break;
                    }
                    else if(counter(playersCards, totalPlayersCards) > 21 && counter(dealersCards, totalDealersCards) < 21)
                    {
                        winner = 2;
                        status = false;
                    }
                    else if(counter(dealersCards, totalDealersCards) > 21 && counter(playersCards, totalPlayersCards) < 21)
                    {
                        winner = 1;
                        status = false;
                        break;
                    }
                    else if(counter(dealersCards, totalDealersCards) == counter(playersCards, totalPlayersCards))
                    {
                        winner = 4;
                        status = false;
                        break;
                    }
                    else if(counter(dealersCards, totalDealersCards) < 21 && counter(playersCards, totalPlayersCards) < 21 && counter(dealersCards, totalDealersCards) > counter(playersCards, totalPlayersCards))
                    {
                        winner = 2;
                        status = false;
                        break;
                    }
                    else if(counter(dealersCards, totalDealersCards) < 21 && counter(playersCards, totalPlayersCards) < 21 && counter(dealersCards, totalDealersCards) < counter(playersCards, totalPlayersCards))
                    {
                        winner = 1;
                        status = false;
                        break;
                    }
                }
                else
                {
                    if(crash != 0)
                    {
                        crash++;
                        clearConsole();
                        System.out.println("Invalid Input!" + inputt);
                        waitInput();
                    }
                }
            }
        }
        clearConsole();
        System.out.println("Current Balance: " + playersBalance);
        System.out.println("Current Bet: " + bet);
        System.out.println("Dealer's Hand: ");
        for(int x=0;x<totalDealersCards;x++)
        {
            System.out.println("Card " + (x+1) + ": " + dealersCards[x]);
        }
        System.out.println(counter(dealersCards, totalDealersCards));
        System.out.println();
        System.out.println();
        System.out.println("Your Hand: ");
        for(int x=0;x<totalPlayersCards;x++)
        {
            System.out.println("Card " + (x+1) + ": " + playersCards[x]);
        }
        System.out.println(counter(playersCards, totalPlayersCards));
        System.out.println();
        if(winner == 1)
        {
            System.out.println("YOU WIN!");
            waitInput();
            int moneyEarned;
            moneyEarned = bet*2;
            System.out.println("You won $" + moneyEarned + "!");
            playersBalance += moneyEarned;
            waitInput();
        }
        else if(winner == 2)
        {
            System.out.println("YOU LOSE!");
            waitInput();
        }
        else if(winner == 3)
        {
            System.out.println("BLACKJACK!");
            waitInput();
            int moneyEarned;
            moneyEarned = bet*2;
            System.out.println("You won $" + moneyEarned + "!");
            playersBalance += moneyEarned;
            waitInput();
        }
        else if(winner == 4)
        {
            System.out.println("TIE!");
            waitInput();
        }
    }

    public static boolean move()
    {
        boolean valid = false;
        boolean returnValue = false;
        String player_choice;
        while(valid == false){
            System.out.print("Do you want to play blackjack, Yes or No? ");
            player_choice = scan.nextLine();
            if( //Checks if input is yes
                (player_choice.charAt(0) == 'Y' || player_choice.charAt(0) == 'y') &&
                (player_choice.charAt(1) == 'E' || player_choice.charAt(1) == 'e') &&
                (player_choice.charAt(2) == 'S' || player_choice.charAt(2) == 's')
            )
                {
                    valid = true;
                    returnValue = true;
                    break;
                }
            else if((player_choice.charAt(0) == 'N' || player_choice.charAt(0) == 'n') &&
                    (player_choice.charAt(1) == 'O' || player_choice.charAt(1) == 'o')
            )
                {
                    valid = true;
                    returnValue = false;
                    clearConsole();

                }
            else
            {
                valid = false;
                clearConsole();
                System.out.println("Invalid input! Please enter valid input.");
                

            }
        }
        return returnValue;
    }

    public static boolean flop()
    {
        boolean valid = false;
        boolean returnValue = false;
        while(valid == false)
        {
            clearConsole();
            System.out.print("Would you like another card? Yes or No? ");
            String player_choice = scan.nextLine();
            if( //Checks if input is yes
                (player_choice.charAt(0) == 'Y' || player_choice.charAt(0) == 'y') &&
                (player_choice.charAt(1) == 'E' || player_choice.charAt(1) == 'e') &&
                (player_choice.charAt(2) == 'S' || player_choice.charAt(2) == 's')
                )
                {
                    valid = true;
                    returnValue = true;
                    break;
                }
            else if((player_choice.charAt(0) == 'N' || player_choice.charAt(0) == 'n') &&
                    (player_choice.charAt(1) == 'O' || player_choice.charAt(1) == 'o')
            )
                {
                    valid = true;
                    returnValue = false;
                    clearConsole();

                }
            else
            {
                valid = false;
                clearConsole();
                System.out.println("Invalid input! Please enter valid input.");
                

            }
        }
        return returnValue;
    }


    public static Boolean move(String x)
    {
        boolean valid = false;
        boolean returnValue = false;
        while(valid == false)
        {
            clearConsole();
            System.out.print("Would you like another card? Yes or No? ");
            String player_choice = scan.nextLine();
            if( //Checks if input is yes
                (player_choice.charAt(0) == 'Y' || player_choice.charAt(0) == 'y') &&
                (player_choice.charAt(1) == 'E' || player_choice.charAt(1) == 'e') &&
                (player_choice.charAt(2) == 'S' || player_choice.charAt(2) == 's')
                )
                {
                    valid = true;
                    returnValue = true;
                    break;
                }
            else if((player_choice.charAt(0) == 'N' || player_choice.charAt(0) == 'n') &&
                    (player_choice.charAt(1) == 'O' || player_choice.charAt(1) == 'o')
            )
                {
                    valid = true;
                    returnValue = false;
                    clearConsole();

                }
            else
            {
                valid = false;
                clearConsole();
                System.out.println("Invalid input! Please enter valid input.");
                

            }
        }
        return returnValue;
    }
    public static String answer()
    {
        boolean valid = false;
        boolean returnValue = false;
        String player_choice = "";
        while(valid == false)
        {
            clearConsole();
            System.out.print("Would you like another card? Yes or No? ");
            player_choice = scan.nextLine();
            if( //Checks if input is yes
                (player_choice.charAt(0) == 'Y' || player_choice.charAt(0) == 'y') &&
                (player_choice.charAt(1) == 'E' || player_choice.charAt(1) == 'e') &&
                (player_choice.charAt(2) == 'S' || player_choice.charAt(2) == 's')
                )
                {
                    valid = true;
                    returnValue = true;
                    break;
                }
            else if((player_choice.charAt(0) == 'N' || player_choice.charAt(0) == 'n') &&
                    (player_choice.charAt(1) == 'O' || player_choice.charAt(1) == 'o')
            )
                {
                    valid = true;
                    returnValue = false;
                    clearConsole();

                }
            else
            {
                valid = false;
                clearConsole();
                System.out.println("Invalid input! Please enter valid input.");
                

            }
        }
        return player_choice;
    }

    public static void waitInput()
    {
        System.out.println("Press \"ENTER\" to continue...");
        try {
            int read = System.in.read(new byte[2]);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int counter(char[] array, int totalCards)
    {
        int count = 0;
        for(int x = 0; x<totalCards; x++)
        {
            if(array[x] == cards[0])
            {
                if(array[1] == 'T')
                {
                    count = 21;
                    break;
                }
                count += 1;
            }
            if(array[x] == cards[1])
            {
                count += 2;
            }
            if(array[x] == cards[2])
            {
                count += 3;
            }
            if(array[x] == cards[3])
            {
                count += 4;
            }
            if(array[x] == cards[4])
            {
                count += 5;
            }
            if(array[x] == cards[5])
            {
                count += 6;
            }
            if(array[x] == cards[6])
            {
                count += 7;
            }
            if(array[x] == cards[7])
            {
                count += 8;
            }
            if(array[x] == cards[8])
            {
                count += 9;
            }
            if(array[x] == cards[9])
            {
                count += 10;
            }
            if(array[x] == cards[10] || array[x] == cards[11] || array[x] == cards[12])
            {
                count += 10;
            }
        }
        return count;
    }
}