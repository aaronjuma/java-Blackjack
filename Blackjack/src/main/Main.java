package main;

import main.gameCommands;
public class Main {

    public static void main(String[] args)
    {
        gameCommands.clearConsole();
        boolean hello = gameCommands.init();
        // run();
        if(hello == true)
        {
            gameCommands.run();
        }
    }

}