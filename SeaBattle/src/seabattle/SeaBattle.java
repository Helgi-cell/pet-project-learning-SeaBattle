/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seabattle;

/**
 *
 * @author Oleg
 */
public class SeaBattle {

    /**
     * @param args the command line arguments
     * @throws java.lang.InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {

        PlaySeaBattle play = new PlaySeaBattle();
        play.initShips();

        FightSea a = new FightSea(play.player1, play.player2);
        a.shootChoice();

    } // end MAIN
}  // end SEABATTLE
