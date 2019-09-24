import java.util.Random;
import java.util.Scanner;

public class Main_Game {
    private static final boolean SILLY_MODE = false;
    private static int SIZE = 3;
    private static char[][] map;
    private static final char DOT_EMPTY = '_';
    private static final char DOT_X = 'X';
    private static final char DOT_O = 'O';

    private static Scanner scanner = new Scanner(System.in);
    private static Random random = new Random();

    public static void main(String[] args) {
        initMap();
        printMap();

        while (true) {
            humanTurn();
            if (isEndGame(DOT_X)) {
                System.out.println("Игра закончена");
                break;
            }
            if (SILLY_MODE) {
                sillyComp();
            } else {
                smartComp();
            }
            if (isEndGame(DOT_O)) {
                System.out.println("Игра закончена");
                break;
            }

        }
    }

    private static void initMap() {
        map = new char[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                map[i][j] = DOT_EMPTY;
            }
        }
    }

    // Вывод поля на экран
    private static void printMap() {
        for (int i = 0; i <= SIZE; i++) {
            System.out.print(i + " ");
        }
        System.out.println();

        for (int i = 0; i < SIZE; i++) {
            System.out.print((i + 1) + " ");
            for (int j = 0; j < SIZE; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }

        System.out.println();
    }

    private static void humanTurn() {
        int x, y;
        do {
            System.out.println("Введите координаты ячейки через пробел (X Y)");
            y = scanner.nextInt() - 1; // Считывание номера строки
            x = scanner.nextInt() - 1; // Считывание номера столбца
        }
        while (!isCellValid(x, y));

        map[y][x] = DOT_X;
    }

    private static void sillyComp() {
        int x = -1;
        int y = -1;
        do {
            x = random.nextInt(SIZE);
            y = random.nextInt(SIZE);
        } while (!isCellValid(x, y));
        System.out.println("Silly!");
        System.out.println("Компьютер выбрал ячейку " + (y + 1) + " " + (x + 1));
        map[y][x] = DOT_O;
    }

    private static void smartComp() {
        int x = 0;
        int y = 0;

        int numOfSameSimybolsNearby = 0;

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {

                if (map[i][j] == DOT_EMPTY) {

                    int temp = checkCellsNearby(i, j);

                    if (temp > numOfSameSimybolsNearby) {

                        numOfSameSimybolsNearby = temp;
                        x = i;
                        y = j;
                    }
                }
            }
        }

        if (numOfSameSimybolsNearby > 0) {

            System.out.println("Компьютер выбрал ячейку " + (y + 1) + " " + (x + 1));
            map[y][x] = DOT_O;

        } else {
            sillyComp();
        }


    }

    private static int checkCellsNearby(int x, int y) {
        int quantity = 0;

        for (int i = x - 1; i < 2; i++) {
            for (int j = y - 1; j < 2; j++) {

                if (isCellExists(i, j) && map[i][j] == DOT_O) {

                    quantity++;
                }
            }
        }
        return quantity;
    }

    private static boolean isCellExists(int x, int y) {

        boolean result = true;
        if (x < 0 || y < 0 || x >= SIZE || y >= SIZE) {
            result = false;
        }
        return result;
    }

    private static boolean isCellValid(int x, int y) {
        boolean result = true;
        if (x < 0 || x >= SIZE || y < 0 || y >= SIZE) {
            result = false;
        } else if (map[y][x] != DOT_EMPTY) {
            result = false;
        }
        return result;
    }

    private static boolean isEndGame(char playerSymbol) {
        boolean result = false;
        printMap();

        if (checkWin(playerSymbol)) {
            System.out.println("Победили " + playerSymbol);
            result = true;
        } else if (mapIsFull()) {
            System.out.println("Ничья!");
            result = true;
        }
        return result;
    }

    private static boolean mapIsFull() {
        boolean result = true;
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (map[i][j] == DOT_EMPTY) {
                    result = false;
                    break;
                }
            }
            if (!result) {
                break;
            }
        }
        return result;
    }

    private static boolean checkWin(char playerSymbol) {
        boolean result = false;
        if (
                (map[0][0] == playerSymbol && map[0][1] == playerSymbol && map[0][2] == playerSymbol) ||
                        (map[1][0] == playerSymbol && map[1][1] == playerSymbol && map[1][2] == playerSymbol) ||
                        (map[2][0] == playerSymbol && map[2][1] == playerSymbol && map[2][2] == playerSymbol) ||
                        (map[0][0] == playerSymbol && map[1][0] == playerSymbol && map[2][0] == playerSymbol) ||
                        (map[0][1] == playerSymbol && map[1][1] == playerSymbol && map[2][1] == playerSymbol) ||
                        (map[0][2] == playerSymbol && map[1][2] == playerSymbol && map[2][2] == playerSymbol) ||
                        (map[0][0] == playerSymbol && map[1][1] == playerSymbol && map[2][2] == playerSymbol) ||
                        (map[2][0] == playerSymbol && map[1][1] == playerSymbol && map[0][2] == playerSymbol)
        )
            result = true;


        return result;

    }
}
