/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seabattle;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import static seabattle.SeaBattleInterface.SIZE;

/**
 *
 * @author User
 */
public class FightSea implements SeaBattleInterface {

    SailorField player1;
    SailorField player2;

    FightSea(SailorField player1, SailorField player2) {
        this.player1 = player1;
        this.player2 = player2;

    }

    public void shootChoice() throws InterruptedException {

        while (player1.win == player2.win) {

            shoot(player1, player2);
            showFields();
            TimeUnit.SECONDS.sleep(1);
            shoot(player2, player1);
            showFields();
            TimeUnit.SECONDS.sleep(1);

        }
        if (player1.win) {
            System.out.println("Wound of Player1.");
        } else {
            System.out.println("Wound of Player2.");
        }

    } // end shootChoice

    //
// Делается выстрел. 
//
    private void shoot(SailorField player, SailorField enemy) throws InterruptedException {
        int x;
        int y;
        boolean ok = true;

        while (ok) {
            //если имеется подбитый корабль

            if (player.ifWounded) {
                int[] result = new int[2];
                result = shootNewIfWounded(player);
                x = result[0];
                y = result[1];
            } // если подбитого корабля нет
            else {
                x = getXY();
                y = getXY();
            }

            // если не попал
            if (player.fieldEnemy[x][y] == 0) {
                if (enemy.fieldPlayer[x][y] == 0 || enemy.fieldPlayer[x][y] == 2) {
                    enemy.fieldPlayer[x][y] = 3;
                    player.fieldEnemy[x][y] = 3;
                    showFields();
                    ok = false;

                    // если попал        
                } else {
                    if (enemy.fieldPlayer[x][y] == 1) {
                        enemy.fieldPlayer[x][y] = 4;
                        player.fieldEnemy[x][y] = 4;
                        player.X = x;
                        player.Y = y;
                        ifWound(player);////ПРОВЕРКА ifWound
                        ifGot(player, enemy);// ПРОВЕРКА ifGot

                        //ok = true;
                        showFields();
                        TimeUnit.SECONDS.sleep(1);
                    }
                }
            }
        }
    } // end shoot

    // Высчитываем новое поле для выстрела, если имеется подбитый корабль
    private int[] shootNewIfWounded(SailorField player) {

        int x = player.X;
        int y = player.Y;
        int[] res = new int[2];

        if (player.wound[0][0] > 1) {
            if (player.wound[0][1] == player.wound[0][2]) {

                if (player.min > 0 && player.fieldEnemy[x][player.min - 1] != 3 && player.fieldEnemy[x][player.min - 1] != 4) {
                    y = player.min - 1;
                } else {
                    if (player.max < SIZE - 1 && player.fieldEnemy[x][player.max + 1] != 3 && player.fieldEnemy[x][player.max + 1] != 4) {
                        y = player.max + 1;
                    } else {
                        if (player.max > 0 && player.fieldEnemy[x][player.max - 1] != 3 && player.fieldEnemy[x][player.max - 1] != 4) {
                            y = player.max - 1;
                        } else {
                            if (player.min < SIZE - 1 && player.fieldEnemy[x][player.min + 1] != 3 && player.fieldEnemy[x][player.min + 1] != 4) {
                                y = player.min + 1;
                            }
                        }
                    }
                }

            } else {
                if (player.wound[1][1] == player.wound[1][2]) {

                    if (player.min > 0 && player.fieldEnemy[player.min - 1][y] != 3 && player.fieldEnemy[player.min - 1][y] != 4) {
                        x = player.min - 1;
                    } else {
                        if (player.max < SIZE - 1 && player.fieldEnemy[player.max + 1][y] != 3 && player.fieldEnemy[player.max + 1][y] != 4) {
                            x = player.max + 1;
                        } else {
                            if (player.min < SIZE - 1 && player.fieldEnemy[player.min + 1][y] != 3 && player.fieldEnemy[player.min + 1][y] != 4) {
                                x = player.min + 1;
                            } else {
                                if (player.max > 0 && player.fieldEnemy[player.max - 1][y] != 3 && player.fieldEnemy[player.max - 1][y] != 4) {
                                    x = player.max - 1;
                                }
                            }
                        }
                    }
                }
            }
        } else {

            if (player.wound[0][0] == 1) {

                if ((y > 0) && (player.fieldEnemy[x][y - 1] != 3) && player.fieldEnemy[x][y - 1] != 4) {
                    y = y - 1;
                } else {
                    if (y < SIZE - 1 && player.fieldEnemy[x][y + 1] != 3 && player.fieldEnemy[x][y + 1] != 4) {
                        y = y + 1;
                    } else {
                        if (x > 0 && player.fieldEnemy[x - 1][y] != 3 && player.fieldEnemy[x - 1][y] != 4) {
                            x = x - 1;
                        } else {
                            if (x < SIZE - 1 && player.fieldEnemy[x + 1][y] != 3 && player.fieldEnemy[x + 1][y] != 4) {
                                x = x + 1;
                            }
                        }
                    }
                }

            }
        }
        res[0] = x;
        res[1] = y;

        return res;

    } // end shootNewIfWounded

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//
//
// Если попадание, создаем или меняем коллекцию подбитых кораблей у противника
//  
    private void ifGot(SailorField player, SailorField enemy) {

        //Integer [][] iArr ;//= new Integer[2][5];
        int x = player.X;
        int y = player.Y;
        boolean tRUE = false;
        int c = 0;
        if (player.ifWounded == false) {
            while (!tRUE) {
                player.iArr = enemy.arrayOfShips.get(c);
                // player.ifWounded = true;    
                for (int i = 1; i < player.iArr[1].length; i++) {

                    if (player.iArr[0][i] == x && player.iArr[1][i] == y) {
                        player.iArr[1][0]++;
                        player.ifWounded = true;
                        tRUE = true;

                    }
                }
                c++;
            }
            if (player.iArr[0][0] == player.iArr[1][0]) {
                ifKill(player);
                player.ifWounded = false;
                zeroWound(player);
                player.brokenShips++;
                if (player.brokenShips == 10) {
                    player.win = true;

                }
            }

        } else {
            player.iArr[1][0]++;
            if (player.iArr[1][0] == player.iArr[0][0]) {
                player.brokenShips++;
                ifKill(player);
                player.ifWounded = false;
                //ifWound(player);
                zeroWound(player);

                if (player.brokenShips == 10) {
                    player.win = true;

                }

            } else {
                player.ifWounded = true;

            }
        }
    }

    //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//
    private void ifKill(SailorField player) {
        int x = player.iArr[0][1];
        int y = player.iArr[1][1];
        if ((player.iArr[0][0] > 1) && (player.iArr[0][1] == player.iArr[0][2])) {  // горизонталь

            for (int i = 0; i <= player.iArr[0][0] - 1; i++) {        // ??????
                if (x > 0 && x < SIZE - 1) {  //центр. Отмечаем сверху и снизу
                    player.fieldEnemy[x + 1][y + i] = 3;
                    player.fieldEnemy[x - 1][y + i] = 3;
                }
                if (x == 0) {                //верхняя строка. Отмечаем только снизу
                    player.fieldEnemy[x + 1][y + i] = 3;
                }
                if (x == SIZE - 1) {           //нижняя строка. Отмечаем только сверху
                    player.fieldEnemy[x - 1][y + i] = 3;
                }
            }// здесь
            if (y > 0 && y < SIZE - player.iArr[0][0]) { // центр . отмечаем два боковых столбца
                if (x > 0 && x < SIZE - 1) {    // отмечаем все. середина           
                    player.fieldEnemy[x][y - 1] = 3;
                    player.fieldEnemy[x - 1][y - 1] = 3;
                    player.fieldEnemy[x + 1][y - 1] = 3;
                    player.fieldEnemy[x][y + player.iArr[0][0]] = 3;
                    player.fieldEnemy[x - 1][y + player.iArr[0][0]] = 3;
                    player.fieldEnemy[x + 1][y + player.iArr[0][0]] = 3;
                }

                if (x == 0) {                    // верхний край. отмечаем только внизу и саму строку
                    player.fieldEnemy[x][y - 1] = 3;
                    player.fieldEnemy[x + 1][y - 1] = 3;
                    player.fieldEnemy[x][y + player.iArr[0][0]] = 3;
                    player.fieldEnemy[x + 1][y + player.iArr[0][0]] = 3;
                }

                if (x == SIZE - 1) {                    // нижний край. отмечаем только вверху и саму строку
                    player.fieldEnemy[x][y - 1] = 3;
                    player.fieldEnemy[x - 1][y - 1] = 3;
                    player.fieldEnemy[x][y + player.iArr[0][0]] = 3;
                    player.fieldEnemy[x - 1][y + player.iArr[0][0]] = 3;
                }
            }

            if (y == 0 && x > 0 && x < SIZE - 1) {
                player.fieldEnemy[x][y + player.iArr[0][0]] = 3;
                player.fieldEnemy[x - 1][y + player.iArr[0][0]] = 3;
                player.fieldEnemy[x + 1][y + player.iArr[0][0]] = 3;
            }
            if (y == SIZE - player.iArr[0][0] && x > 0 && x < SIZE - 1) {
                player.fieldEnemy[x][y - 1] = 3;
                player.fieldEnemy[x - 1][y - 1] = 3;
                player.fieldEnemy[x + 1][y - 1] = 3;
            }
            if (y == 0 && x == 0) {
                player.fieldEnemy[x][y + player.iArr[0][0]] = 3;
                player.fieldEnemy[x + 1][y + player.iArr[0][0]] = 3;
            }
            if (y == 0 && x == SIZE - 1) {
                player.fieldEnemy[x][y + player.iArr[0][0]] = 3;
                player.fieldEnemy[x - 1][y + player.iArr[0][0]] = 3;
            }
            if (y == SIZE - player.iArr[0][0] && x == 0) {
                player.fieldEnemy[x][y - 1] = 3;
                player.fieldEnemy[x + 1][y - 1] = 3;
            }

            if (y == SIZE - player.iArr[0][0] && x == SIZE - 1) {
                player.fieldEnemy[x][y - 1] = 3;
                player.fieldEnemy[x - 1][y - 1] = 3;
            }

            //}
        } else {

            // вертикаль
            if (player.iArr[0][0] > 1 && (player.iArr[1][1] == player.iArr[1][2])) {

                for (int i = 0; i <= player.iArr[0][0] - 1; i++) {
                    if (y > 0 && y < SIZE - 1) {  //центр. Отмечаем сверху и снизу
                        player.fieldEnemy[x + i][y + 1] = 3;
                        player.fieldEnemy[x + i][y - 1] = 3;
                    }
                    if (y == 0) {                //верхняя строка. Отмечаем только снизу
                        player.fieldEnemy[x + i][y + 1] = 3;
                    }
                    if (y == SIZE - 1) {           //нижняя строка. Отмечаем только сверху
                        player.fieldEnemy[x + i][y - 1] = 3;
                    }
                }
                if (x > 0 && x < SIZE - player.iArr[0][0]) { // центр . отмечаем два боковых столбца
                    if (y > 0 && y < SIZE - 1) {    // отмечаем все. середина           
                        player.fieldEnemy[x - 1][y] = 3;
                        player.fieldEnemy[x - 1][y - 1] = 3;
                        player.fieldEnemy[x - 1][y + 1] = 3;
                        player.fieldEnemy[x + player.iArr[0][0]][y] = 3;
                        player.fieldEnemy[x + player.iArr[0][0]][y - 1] = 3;
                        player.fieldEnemy[x + player.iArr[0][0]][y + 1] = 3;
                    }

                    if (y == 0) {                    // верхний край. отмечаем только внизу и саму строку
                        player.fieldEnemy[x - 1][y] = 3;
                        player.fieldEnemy[x - 1][y + 1] = 3;
                        player.fieldEnemy[x + player.iArr[0][0]][y] = 3;
                        player.fieldEnemy[x + player.iArr[0][0]][y + 1] = 3;
                    }

                    if (y == SIZE - 1) {                    // нижний край. отмечаем только вверху и саму строку
                        player.fieldEnemy[x - 1][y] = 3;
                        player.fieldEnemy[x - 1][y - 1] = 3;
                        player.fieldEnemy[x + player.iArr[0][0]][y] = 3;
                        player.fieldEnemy[x + player.iArr[0][0]][y - 1] = 3;
                    }
                }

                if (x == 0 && y > 0 && y < SIZE - 1) {
                    player.fieldEnemy[x + player.iArr[0][0]][y] = 3;
                    player.fieldEnemy[x + player.iArr[0][0]][y + 1] = 3;
                    player.fieldEnemy[x + player.iArr[0][0]][y - 1] = 3;
                }
                if (x == SIZE - player.iArr[0][0] && y > 0 && y < SIZE - 1) {
                    player.fieldEnemy[x - 1][y] = 3;
                    player.fieldEnemy[x - 1][y - 1] = 3;
                    player.fieldEnemy[x - 1][y + 1] = 3;
                }
                if (y == 0 && x == 0) {
                    player.fieldEnemy[x + player.iArr[0][0]][y] = 3;
                    player.fieldEnemy[x + player.iArr[0][0]][y + 1] = 3;
                }
                if (x == 0 && y == SIZE - 1) {
                    player.fieldEnemy[x + player.iArr[0][0]][y] = 3;
                    player.fieldEnemy[x + player.iArr[0][0]][y - 1] = 3;
                }
                if (x == SIZE - player.iArr[0][0] && y == 0) {
                    player.fieldEnemy[x - 1][y] = 3;
                    player.fieldEnemy[x - 11][y + 1] = 3;
                }

                if (x == SIZE - player.iArr[0][0] && y == SIZE - 1) {
                    player.fieldEnemy[x - 1][y] = 3;
                    player.fieldEnemy[x - 1][y - 1] = 3;
                }

                // }
            }
        }
        if (player.iArr[0][0] == 1) { // одиночный корабль
            if (x > 0 && y > 0 && y < SIZE - 1 && x < SIZE - 1) {
                player.fieldEnemy[x - 1][y] = 3;
                player.fieldEnemy[x + 1][y] = 3;
                player.fieldEnemy[x][y - 1] = 3;
                player.fieldEnemy[x][y + 1] = 3;
                player.fieldEnemy[x - 1][y - 1] = 3;
                player.fieldEnemy[x - 1][y + 1] = 3;
                player.fieldEnemy[x + 1][y + 1] = 3;
                player.fieldEnemy[x + 1][y - 1] = 3;
            } else {
                if (y == 0 && x == 0) {// левый верхний угол
                    player.fieldEnemy[x][y + 1] = 3;
                    player.fieldEnemy[x + 1][y] = 3;
                    player.fieldEnemy[x + 1][y + 1] = 3;
                } else {
                    if (x == 0 && y == SIZE - 1) { // правый верхний угол
                        player.fieldEnemy[x + 1][y] = 3;
                        player.fieldEnemy[x][y - 1] = 3;
                        player.fieldEnemy[x + 1][y - 1] = 3;
                    } else {
                        if (x == SIZE - 1 && y == 0) {  // левый нижний угол
                            player.fieldEnemy[x - 1][y] = 3;
                            player.fieldEnemy[x][y + 1] = 3;
                            player.fieldEnemy[x - 1][y + 1] = 3;
                        } else {
                            if (x == SIZE - 1 && y == SIZE - 1) {
                                player.fieldEnemy[x - 1][y] = 3;
                                player.fieldEnemy[x - 1][y - 1] = 3;
                                player.fieldEnemy[x][y - 1] = 3;

                            } else {
                                if (x == 0 && y > 0 && y < SIZE - 1) { // верхняя сторона
                                    player.fieldEnemy[x + 1][y] = 3;
                                    player.fieldEnemy[x][y - 1] = 3;
                                    player.fieldEnemy[x][y + 1] = 3;
                                    player.fieldEnemy[x + 1][y + 1] = 3;
                                    player.fieldEnemy[x + 1][y - 1] = 3;

                                } else {
                                    if (x == SIZE - 1 && y > 0 && y < SIZE - 1) {  // нижняя сторона
                                        player.fieldEnemy[x - 1][y] = 3;
                                        player.fieldEnemy[x][y - 1] = 3;
                                        player.fieldEnemy[x][y + 1] = 3;
                                        player.fieldEnemy[x - 1][y + 1] = 3;
                                        player.fieldEnemy[x - 1][y - 1] = 3;
                                    } else {
                                        if (y == 0 && x > 0 && x < SIZE - 1) { //левая сторона
                                            player.fieldEnemy[x + 1][y] = 3;
                                            player.fieldEnemy[x - 1][y] = 3;
                                            player.fieldEnemy[x][y + 1] = 3;
                                            player.fieldEnemy[x + 1][y + 1] = 3;
                                            player.fieldEnemy[x - 1][y + 1] = 3;

                                        } else {  // правая сторона
                                            if (y == SIZE - 1 && x > 0 && x < SIZE - 1) {
                                                player.fieldEnemy[x + 1][y] = 3;
                                                player.fieldEnemy[x - 1][y] = 3;
                                                player.fieldEnemy[x][y - 1] = 3;
                                                player.fieldEnemy[x + 1][y - 1] = 3;
                                                player.fieldEnemy[x - 1][y - 1] = 3;

                                            }

                                        }
                                    }

                                }

                            }
                        }
                    }

                }
            }

        }

        // end else одиночный корабль
    }

    // Если попадание, создаем или меняем коллекцию подбитых кораблей
//
    private void ifWound(SailorField player) {

        //вносим в массив подбитого корабля противника координату
        ++player.wound[0][0];
        int addres = player.wound[0][0];
        player.wound[0][addres] = player.X;
        player.wound[1][addres] = player.Y;

        if (player.wound[0][0] > 1) {

            if (player.wound[0][1] == player.wound[0][2]) {
                for (int i = 1; i <= player.wound[0][0]; i++) {
                    if (player.wound[1][i] < player.min) {
                        player.min = player.wound[1][i];
                    }
                    if (player.wound[1][i] > player.max) {
                        player.max = player.wound[1][i];
                    }
                }
            }

            if (player.wound[1][1] == player.wound[1][2]) {
                for (int i = 1; i <= player.wound[0][0]; i++) {
                    if (player.wound[0][i] < player.min) {
                        player.min = player.wound[0][i];
                    }
                    if (player.wound[0][i] > player.max) {
                        player.max = player.wound[0][i];
                    }
                }
            }

        }

    }

    private void zeroWound(SailorField player) {
        for (int i = 0; i < player.wound.length; i++) {
            for (int j = 0; j < player.wound[i].length; j++) {
                player.wound[i][j] = 0;
            }
        }
        player.iArr = null;
        player.min = SIZE;
        player.max = 0;
    }

    private int getXY() {

        Random shoot = new Random();
        int c = shoot.nextInt(SIZE);
        return c;
    } // end detXY

    //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//
    // Показываем поля игроков
    //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//    
    private void showFields() {
// 0 - пустое поле
// 1 - поле корабля 
// 2 - поле рядом с кораблем 
// 3 - поле после попадания мимо 
// 4 - поле поврежденного корабля
        System.out.println("--PLAYER 1----------------------------PLAYER 2----");
        System.out.println("---------------------------------------------------");
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (player1.fieldPlayer[i][j] == 1) {
                    System.out.print("O ");
                } else {
                    if (player1.fieldPlayer[i][j] == 0 || player1.fieldPlayer[i][j] == 2) {
                        System.out.print(". ");
                    } else {
                        if (player1.fieldPlayer[i][j] == 3) {
                            System.out.print("* ");
                        } else {
                            if (player1.fieldPlayer[i][j] == 4) {
                                System.out.print("X ");
                            }
                        }
                    }
                }
            }
            System.out.print("          ");
            for (int c = 0; c < SIZE; c++) {
                if (player2.fieldPlayer[i][c] == 1) {
                    System.out.print("O ");
                } else {
                    if (player2.fieldPlayer[i][c] == 0 || player2.fieldPlayer[i][c] == 2) {
                        System.out.print(". ");
                    } else {
                        if (player2.fieldPlayer[i][c] == 3) {
                            System.out.print("* ");
                        } else {
                            if (player2.fieldPlayer[i][c] == 4) {
                                System.out.print("X ");
                            }
                        }
                    }
                }
            }
            System.out.println();

        }
        System.out.println("-ROUTE for shoot P1----------Route for shoot P2-----");

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (player1.fieldEnemy[i][j] == 4) {
                    System.out.print("X ");
                } else {
                    if (player1.fieldEnemy[i][j] == 0) {
                        System.out.print(". ");
                    } else {
                        if (player1.fieldEnemy[i][j] == 3) {
                            System.out.print("* ");
                        }
                    }
                }

            }

            System.out.print("          ");

            for (int c = 0; c < SIZE; c++) {
                if (player2.fieldEnemy[i][c] == 4) {
                    System.out.print("X ");
                } else {
                    if (player2.fieldEnemy[i][c] == 0) {
                        System.out.print(". ");
                    } else {
                        if (player2.fieldEnemy[i][c] == 3) {
                            System.out.print("* ");
                        }
                    }

                }
            }
            System.out.println();

        }
        System.out.println("---------------------------------------------------");
        System.out.println("---------------------------------------------------");
    }// end showFields
}
