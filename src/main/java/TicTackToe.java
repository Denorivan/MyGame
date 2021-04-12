import java.util.Random;
import java.util.Scanner;

public class TicTackToe {public static final String USER_SIGN = "X";
    public static final String USER_SIGN_SECOND = "O";
    public static final String AI_SIGN = "O";
    public static final String NOT_SIGN = "*";
    public static int aiLevel = 0;
    public static final int DIMENSION = 3;
    public static String[][] field = new String[DIMENSION][DIMENSION];

    public static void main(String[] args) {
        mainMenu();
    }

    public static void modeTwoPlayers() {
        int count = 0;
        initField();
        while (true) {
            printField();
            userShot(USER_SIGN, 1);
            count++;
            if (checkWin(USER_SIGN)) {
                System.out.println("USER 1 WIN!!!");
                printField();
                break;
            }
            userShot(USER_SIGN_SECOND, 2);
            count++;
            if (checkWin(USER_SIGN_SECOND)) {
                System.out.println("USER 2 WIN!!!");
                printField();
                break;
            }
            if (count == Math.pow(DIMENSION, 2)) {

                printField();
                break;
            }
        }
    }

    public static void modeAgainstAI() {
        int count = 0;
        initField();
        while (true) {
            printField();
            userShot(USER_SIGN, 0);
            count++;
            if (checkWin(USER_SIGN)) {
                System.out.println("USER WIN!!!");
                printField();
                break;
            }
            if (count == Math.pow(DIMENSION, 2)) {
                printField();
                System.out.println("It's Draw");
                break;
            }
            aiShot();
            for (int i = 0; i < 100; i++) {
                System.out.println();
            }
            count++;
            if (checkWin(AI_SIGN)) {
                System.out.println("AI WIN!!!");
                printField();
                break;
            }
            if (count == Math.pow(DIMENSION, 2)) {
                printField();
                break;
            }
        }
    }

    public static void mainMenu() {
        System.out.println("Выберите режим игры: ");
        System.out.println("1. Игра против компьютера.");
        System.out.println("2. 2 игрока.");
        System.out.println("3. Выход.");
        int i;
        Scanner sc = new Scanner(System.in);
        i = sc.nextInt();
        switch (i) {
            case 1: {
                aiLevel();
                break;
            }
            case 2: {
                modeTwoPlayers();
                break;
            }
            case 3: {
                System.exit(0);
                break;
            }
            default: {
                System.out.println("Было введено неверное значение!");
            }
        }
    }

    public static void aiLevel() {
        System.out.println("Выберите сложность компьютера: ");
        System.out.println("1. Простой.");
        System.out.println("2. Продвинутый.");
        System.out.println("3. Сложный.");
        System.out.println("4. Вернуться в предыдущее меню.");
        System.out.println("5. Выход.");
        int i;
        Scanner sc = new Scanner(System.in);
        i = sc.nextInt();
        switch (i) {
            case 1: {
                aiLevel = 0;
                modeAgainstAI();
                break;
            }
            case 2: {
                aiLevel = 1;
                modeAgainstAI();
                break;
            }
            case 3: {
                aiLevel = 2;
                modeAgainstAI();
                break;
            }
            case 4: {
                mainMenu();
                break;
            }
            case 5: {
                System.exit(0);
                break;
            }
            default: {
                System.out.println("Было введено неверное значение!");
            }
        }
    }

    public static void initField() {
        for (int i = 0; i < DIMENSION; i++) {
            for (int j = 0; j < DIMENSION; j++) {
                field[i][j] = NOT_SIGN;
            }
        }
    }

    public static void printField() {
        for (int i = 0; i <= DIMENSION; i++) {
            System.out.print(i + " ");
        }
        System.out.println();
        for (int i = 0; i < DIMENSION; i++) {
            System.out.print((i + 1) + " ");
            for (int j = 0; j < DIMENSION; j++) {
                System.out.print(field[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void userShot(String sign, int i) {
        int x;
        int y;
        do {
            if (i == 0) {
                System.out.println("Введите координаты x y (1 - " + DIMENSION + "): ");
            } else {
                System.out.println("Игрок " + i + ". Введите координаты x y (1 - " + DIMENSION + "): ");
            }
            Scanner sc = new Scanner(System.in);
            x = sc.nextInt() - 1;
            y = sc.nextInt() - 1;
        }
        while (isCellBusy(x, y));
        field[x][y] = sign;
    }

    public static void aiShot() {
        if (aiLevel == 2) {
            if (aiShotWithAiLevelEqual2()) {
                return;
            }
        }
        if (aiLevel > 0) {
            if (aiShotWithAiLevelMoreThan0()){
                return;
            }
        }
        makeRandomShot();
    }

    private static boolean aiShotWithAiLevelEqual2() {
        for (int i = 0; i < DIMENSION; i++) {
            for (int j = 0; j < DIMENSION; j++) {
                if (!isCellBusy(i, j)) {
                    field[i][j] = AI_SIGN;
                    if (checkWin(AI_SIGN)) {
                        return true;
                    } else {
                        field[i][j] = NOT_SIGN;
                    }
                }
            }
        }
        return false;
    }

    private static boolean aiShotWithAiLevelMoreThan0() {
        for (int i = 0; i < DIMENSION; i++) {
            for (int j = 0; j < DIMENSION; j++) {
                if (!isCellBusy(i, j)) {
                    field[i][j] = USER_SIGN;
                    if (checkWin(USER_SIGN)) {
                        return true;
                    } else {
                        field[i][j] = NOT_SIGN;
                    }
                }
            }
        }
        return false;
    }

    private static void makeRandomShot() {
        int y;
        int x;
        do {
            Random rnd = new Random();
            x = rnd.nextInt(DIMENSION);
            y = rnd.nextInt(DIMENSION);
        }
        while (isCellBusy(x, y));
    }


    public static boolean isCellBusy(int x, int y) {
        if (x < 0 || y < 0 || x > DIMENSION - 1 || y > DIMENSION - 1) {
            return false;
        }
        return !field[x][y].equals(NOT_SIGN);
    }

    @SuppressWarnings("RedundantIfStatement")
    public static boolean checkWin(String sign) {
        if (checkWinByRows(sign)) {
            return true;
        }
        if (checkWinByCols(sign)) {
            return true;
        }
        if (checkWinByMainDiagonal(sign)) {
            return true;
        }
        if (checkWinBySecondaryDiagonal(sign)) {
            return true;
        }
        return false;
    }

    private static boolean checkWinByRows(String sign) {
        for (int i = 0; i < DIMENSION; i++) {
            if (field[i][0].equals(sign) && field[i][1].equals(sign) && field[i][2].equals(sign)) {
                return true;
            }
        }
        return false;
    }

    private static boolean checkWinByCols(String sign) {
        for (int j = 0; j < DIMENSION; j++) {
            if (field[0][j].equals(sign) && field[1][j].equals(sign) && field[2][j].equals(sign)) {
                return true;
            }
        }
        return false;
    }

    private static boolean checkWinByMainDiagonal(String sign) {
        return field[0][0].equals(sign) && field[1][1].equals(sign) && field[2][2].equals(sign);
    }

    private static boolean checkWinBySecondaryDiagonal(String sign) {
        return field[0][2].equals(sign) && field[1][1].equals(sign) && field[2][0].equals(sign);
    }
}
