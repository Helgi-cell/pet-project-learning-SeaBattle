/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seabattle;

import java.util.ArrayList;

/**
 *
 * @author User
 */
public class SailorField implements SeaBattleInterface {

    int[][] fieldPlayer; // поле игрока
    int[][] fieldEnemy;  // поле игрока для атаки соперника
    int brokenShips;  // количество уничтоженных кораблей соперника
    int X;
    int Y;
    boolean win;
    boolean ifWounded;// было ли попадание
    ArrayList<Integer[][]> arrayOfShips; // список кораблей на поле
    int[][] wound;
    Integer[][] iArr;
    int min;
    int max;

    SailorField() {
        this.arrayOfShips = new ArrayList<>();
        this.brokenShips = 0;
        this.ifWounded = false;
        this.win = false;
        this.fieldPlayer = new int[SIZE][SIZE];
        this.fieldEnemy = new int[SIZE][SIZE];
        this.wound = new int[2][5];
        this.X = 0;
        this.Y = 0;
        this.min = 10;
        this.max = 0;

    }

} // end CLASS
