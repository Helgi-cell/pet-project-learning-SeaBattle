/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seabattle;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author User
 */
public class PlaySeaBattle implements SeaBattleInterface {

    SailorField player1;
    SailorField player2;

    PlaySeaBattle() {

        player1 = new SailorField();
        player2 = new SailorField();

    }

    public void initShips() {
        disposeShips(player1.fieldPlayer, player1.arrayOfShips);
        disposeShips(player2.fieldPlayer, player2.arrayOfShips);
    } // end initShips

    private void disposeShips(int[][] fieldPlayer, ArrayList arrayOfShips) {
        Random r = new Random();
        // Получаем направление размещения корабля 
        //sizeShip -  размер размещаемого корабля
        // если direct == 0 - горизонтально, если 1 - вертикально
        int direct;

        for (int i = 1; i < 5; i++) {
            int j = 0;

            while (j < i) {

                direct = r.nextInt(10);
                if (direct >= 5) {
                    disposeVertical(5 - i, fieldPlayer, arrayOfShips);
                } else {
                    disposeHorizontal(5 - i, fieldPlayer, arrayOfShips);
                }
                j++;
            }
        }
    } // end disposeShips

    private void disposeHorizontal(int sizeShip, int[][] fieldPlayer, ArrayList arrayOfShips) {
        Random rand = new Random();
        boolean bool = true;
        int x;
        int y;

        while (bool) {

            x = rand.nextInt(SIZE);
            y = rand.nextInt(SIZE - sizeShip + 1);
            boolean free = false;
            int count = 0;
            for (int i = y; i < y + sizeShip; i++) {
                count += fieldPlayer[x][i];
            }
            if (count == 0) {
                free = true;
                Integer[][] ship = new Integer[2][sizeShip + 1];
                ship[0][0] = sizeShip;
                ship[1][0] = 0;
                for (count = 1; count <= sizeShip; count++) {
                    ship[0][count] = x;
                    ship[1][count] = y + count - 1;
                }
                arrayOfShips.add(ship);
            }

            if (free) {
                bool = false;
                for (int i = y; i < y + sizeShip; i++) {
                    fieldPlayer[x][i] = 1;

                    // Проверка левого верхнего угла
                    if (x == 0 && y == 0) {
                        fieldPlayer[x + 1][i] = 2;
                        fieldPlayer[x + 1][y + sizeShip] = 2;
                        fieldPlayer[x][y + sizeShip] = 2;
                    }

                    // Проверка правого верхнего угла
                    if (x == 0 && y == SIZE - sizeShip) {
                        fieldPlayer[x + 1][i] = 2;
                        fieldPlayer[x + 1][y - 1] = 2;
                        fieldPlayer[x][y - 1] = 2;
                    }

                    // Проверка левой стороны
                    if (x > 0 && y == 0 && x < SIZE - 1) {
                        fieldPlayer[x + 1][i] = 2;
                        fieldPlayer[x - 1][i] = 2;
                        fieldPlayer[x][y + sizeShip] = 2;
                        fieldPlayer[x + 1][y + sizeShip] = 2;
                        fieldPlayer[x - 1][y + sizeShip] = 2;
                    }
                    // Проверка правой стороны
                    if (x > 0 && y == SIZE - sizeShip && x < SIZE - 1) {
                        fieldPlayer[x + 1][i] = 2;
                        fieldPlayer[x - 1][i] = 2;
                        fieldPlayer[x][y - 1] = 2;
                        fieldPlayer[x + 1][y - 1] = 2;
                        fieldPlayer[x - 1][y - 1] = 2;
                    }

                    // Проверка центра
                    if (x > 0 && y < SIZE - sizeShip && x < SIZE - 1 && y > 0) {
                        fieldPlayer[x + 1][i] = 2;
                        fieldPlayer[x - 1][i] = 2;
                        fieldPlayer[x][y - 1] = 2;
                        fieldPlayer[x + 1][y - 1] = 2;
                        fieldPlayer[x - 1][y - 1] = 2;
                        fieldPlayer[x][y + sizeShip] = 2;
                        fieldPlayer[x + 1][y + sizeShip] = 2;
                        fieldPlayer[x - 1][y + sizeShip] = 2;

                    }

                    // Проверка левого нижнего угла
                    if (x == SIZE - 1 && y == 0) {
                        fieldPlayer[x - 1][i] = 2;
                        fieldPlayer[x - 1][y + sizeShip] = 2;
                        fieldPlayer[x][y + sizeShip] = 2;
                    }

                    //  Проверка правого нижнего угла
                    if (x == SIZE - 1 && y == SIZE - sizeShip) {
                        fieldPlayer[x - 1][i] = 2;
                        fieldPlayer[x - 1][y - 1] = 2;
                        fieldPlayer[x][y - 1] = 2;
                    }

                    // Проверка верха
                    if (x == 0 && y > 0 && y < SIZE - sizeShip) {
                        fieldPlayer[x + 1][i] = 2;
                        fieldPlayer[x + 1][y + sizeShip] = 2;
                        fieldPlayer[x + 1][y - 1] = 2;
                        fieldPlayer[x][y + sizeShip] = 2;
                        fieldPlayer[x][y - 1] = 2;
                    }

                    // Проверка низа
                    if (x == SIZE - 1 && y > 0 && y < SIZE - sizeShip) {
                        fieldPlayer[x - 1][i] = 2;
                        fieldPlayer[x - 1][y + sizeShip] = 2;
                        fieldPlayer[x - 1][y - 1] = 2;
                        fieldPlayer[x][y + sizeShip] = 2;
                        fieldPlayer[x][y - 1] = 2;
                    }

                } // end FOR
            } // end if FREE

        } // end while

    } // end disposeHorizontal

    private void disposeVertical(int sizeShip, int[][] fieldPlayer, ArrayList arrayOfShips) {
        Random rand1 = new Random();
        boolean bool = true;
        int x;
        int y;
        int xx = SIZE - sizeShip + 1;
        int yy = SIZE;

        while (bool) {
            x = rand1.nextInt(xx);
            y = rand1.nextInt(yy);
            boolean free = false;
            int count = 0;
            for (int i = x; i < x + sizeShip; i++) {
                count += fieldPlayer[i][y];
            }
            if (count == 0) {
                free = true;
                Integer[][] ship = new Integer[2][sizeShip + 1];
                ship[0][0] = sizeShip;
                ship[1][0] = 0;
                for (count = 1; count <= sizeShip; count++) {
                    ship[0][count] = x + count - 1;
                    ship[1][count] = y;
                }
                arrayOfShips.add(ship);
            }

            if (free) {
                bool = false;
                for (int i = x; i < x + sizeShip; i++) {
                    fieldPlayer[i][y] = 1;

                    // Проверка левого верхнего угла
                    if (x == 0 && y == 0) {
                        fieldPlayer[i][y + 1] = 2;
                        fieldPlayer[x + sizeShip][y + 1] = 2;
                        fieldPlayer[x + sizeShip][y] = 2;
                    }

                    // Проверка правого верхнего угла
                    if (x == 0 && y == SIZE - 1) {
                        fieldPlayer[i][y - 1] = 2;
                        fieldPlayer[x + sizeShip][y - 1] = 2;
                        fieldPlayer[x + sizeShip][y] = 2;
                    }

                    // Проверка левой стороны
                    if (x > 0 && y == 0 && x < SIZE - sizeShip) {
                        fieldPlayer[i][y + 1] = 2;
                        fieldPlayer[x - 1][y] = 2;
                        fieldPlayer[x + sizeShip][y] = 2;
                        fieldPlayer[x + sizeShip][y + 1] = 2;
                        fieldPlayer[x - 1][y + 1] = 2;
                    }
                    // Проверка правой стороны
                    if (x > 0 && y == SIZE - 1 && x < SIZE - sizeShip) {
                        fieldPlayer[i][y - 1] = 2;
                        fieldPlayer[x - 1][y] = 2;
                        fieldPlayer[x - 1][y - 1] = 2;
                        fieldPlayer[x + sizeShip][y - 1] = 2;
                        fieldPlayer[x + sizeShip][y] = 2;
                    }

                    // Проверка центра
                    if (x > 0 && y < SIZE - 1 && x < SIZE - sizeShip && y > 0) {
                        fieldPlayer[i][y - 1] = 2;
                        fieldPlayer[i][y + 1] = 2;
                        fieldPlayer[x - 1][y - 1] = 2;
                        fieldPlayer[x - 1][y] = 2;
                        fieldPlayer[x - 1][y + 1] = 2;
                        fieldPlayer[x + sizeShip][y - 1] = 2;
                        fieldPlayer[x + sizeShip][y] = 2;
                        fieldPlayer[x + sizeShip][y + 1] = 2;

                    }

                    // Проверка левого нижнего угла
                    if (x == SIZE - sizeShip && y == 0) {
                        fieldPlayer[i][y + 1] = 2;
                        fieldPlayer[x - 1][y] = 2;
                        fieldPlayer[x - 1][y + 1] = 2;
                    }

                    //  Проверка правого нижнего угла
                    if (x == SIZE - sizeShip && y == SIZE - 1) {
                        fieldPlayer[i][y - 1] = 2;
                        fieldPlayer[x - 1][y] = 2;
                        fieldPlayer[x - 1][y - 1] = 2;
                    }

                    // Проверка верха
                    if (x == 0 && y > 0 && y < SIZE - 1) {
                        fieldPlayer[i][y + 1] = 2;
                        fieldPlayer[i][y - 1] = 2;
                        fieldPlayer[x + sizeShip][y] = 2;
                        fieldPlayer[x + sizeShip][y + 1] = 2;
                        fieldPlayer[x + sizeShip][y - 1] = 2;
                    }

                    // Проверка низа
                    if (x == SIZE - sizeShip && y > 0 && y < SIZE - 1) {
                        fieldPlayer[i][y + 1] = 2;
                        fieldPlayer[i][y - 1] = 2;
                        fieldPlayer[x - 1][y - 1] = 2;
                        fieldPlayer[x - 1][y] = 2;
                        fieldPlayer[x - 1][y + 1] = 2;
                    }

                } // end FOR
            } // end if FREE

        } // end while

    } // end disposeVertical

    void showArrayList(ArrayList arrayOfShips) {

        Integer[][] iArr = new Integer[2][5];
        for (int c = 0; c < arrayOfShips.size(); c++) {

            iArr = (Integer[][]) arrayOfShips.get(c);

        }
    }

}  // end class PlaySeaBattle
