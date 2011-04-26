/*
 * Main.java
 *  java program model for www.programming-challenges.com
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.Scanner;

class Main {
    static final int BOARD_SIZE = 8;
    int game = 1;
    boolean oneTime = true;
    PrintStream cout = System.out;
    Scanner sc = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
    boolean inCheck = false;
    char attack = ' ';
    byte posX = 0;
    byte posY = 0;
    char[][] board = new char[8][8];
    

    public static void main(String[] args) throws IOException {
        Main check = new Main();
        char[] cells = new char[BOARD_SIZE];
        while ((check.sc.nextLine()) != null) {
            if (check.oneTime) {
                check.cout.println();
                check.oneTime = false;
            }
            if (cells[0] == '\n' || cells[0] == '\r') continue;
            for (int y = 0; y < BOARD_SIZE; y++) {
                for (int x = 0; x < BOARD_SIZE; x++) {
                    check.board[y][x] = cells[x];
                }
                if (check.sc.nextLine() == null) System.exit(0);
            }
            
            StringBuilder out = new StringBuilder("Game #");
            out.append(check.game);
            out.append(": ");
            
            char checked = check.findCheck();
            switch(checked) {
            case 'k':
                out.append("black ");
                break;
            case 'K':
                out.append("white ");
                break;
            case '.':
                out.append("no ");
                break;
            case '-':
                System.exit(0);
                break;
            }
            out.append("king is in check.");
            check.cout.println(out.toString());
            check.posY = 0;
            check.posX = 0;
            check.game++;          
        }
    }
    

    public char findCheck() {
        boolean hasPiece = false;
        char result = '.';
        // find each piece on the board -- it will either be the king, 
        // or have the potential to check the king. 
        char piece; 
        while ((piece = findNextPiece()) != ' ') {
            hasPiece = true;
            if (putsKingInCheck(piece)) return attack;
            if (posX == BOARD_SIZE - 1 && posY < BOARD_SIZE) {
                posX = 0;
                posY++;
            } else {
                posX++;
            }
        }
        if (!hasPiece) result = '-';
        return result;
    }
    

    private boolean putsKingInCheck(char piece) {
        boolean inCheck = false;
        switch(piece) {
        case 'p':
            inCheck = checkPawnMoves(false);
            break;
        case 'n':
            inCheck = checkKnightMoves(false);
            break;
        case 'b':
            inCheck = checkBishopMoves(false);
            break;
        case 'r':
            inCheck = checkRookMoves(false);
            break;
        case 'q':
            inCheck = checkQueenMoves(false);
            break;
        case 'P':
            inCheck = checkPawnMoves(true);
            break;
        case 'N':
            inCheck = checkKnightMoves(true);
            break;
        case 'B':
            inCheck = checkBishopMoves(true);
            break;
        case 'R':
            inCheck = checkRookMoves(true);
            break;
        case 'Q':
            inCheck = checkQueenMoves(true);
            break;
        case 'k':
        case 'K': 
            break;
        }
        return inCheck;
    }
    

    private boolean checkPawnMoves(boolean isWhite) {
        if (isWhite) {
            if (posY > 0) {
                if (posX > 0) {
                    attack = board[posY-1][posX-1];
                    if (attack == 'k') return true;
                } 
                if (posX < BOARD_SIZE-1) {
                    attack = board[posY-1][posX+1];
                    if (attack == 'k') return true;
                }
            }
        } else {
            if (posY < BOARD_SIZE-1) {
                if (posX > 0) {
                    attack = board[posY+1][posX-1];
                    if (attack == 'K') return true;
                } 
                if (posX < BOARD_SIZE-1 && posY < BOARD_SIZE-1) {
                    attack = board[posY+1][posX+1];
                    if (attack == 'K') return true;
                }
            }
        }       
        return false;
    }


    private boolean checkKnightMoves(boolean isWhite) {
        if (posY-2 >= 0) {
            if (posX-1 >= 0) {
                attack = board[posY-2][posX-1];
                if (isEnemyKing(attack, isWhite)) return true;
            }
            if (posX+1 < BOARD_SIZE) {
                attack = board[posY-2][posX+1];
                if (isEnemyKing(attack, isWhite)) return true;
            }
        }
        
        if (posY+2 < BOARD_SIZE) {
            if (posX-1 >= 0) {
                attack = board[posY+2][posX-1];
                if (attack != '.' && isEnemyKing(attack, isWhite)) return true;
            }
            if (posX+1 < BOARD_SIZE) {
                attack = board[posY+2][posX+1];
                if (attack != '.' && isEnemyKing(attack, isWhite)) return true;
            }
        }
        
        if (posX-2 >= 0) {
            if (posY-1 >= 0) {
                attack = board[posY-1][posX-2];
                if (attack != '.' && isEnemyKing(attack, isWhite)) return true;
            }
            if (posY+1 < BOARD_SIZE) {
                attack = board[posY+1][posX-2];
                if (attack != '.' && isEnemyKing(attack, isWhite)) return true;
            }
        }
        
        if (posX+2 < BOARD_SIZE) {
            if (posY-1 >= 0) {
                attack = board[posY-1][posX+2];
                if (attack != '.' && isEnemyKing(attack, isWhite)) return true;
            }
            if (posY+1 < BOARD_SIZE) {
                attack = board[posY+1][posX+2];
                if (attack != '.' && isEnemyKing(attack, isWhite)) return true;
            }
        }
        
        return false;
    }
    

    private boolean checkBishopMoves(boolean isWhite) {
        byte i = 1;
        for (i = 1; (posY + i < BOARD_SIZE) && (posX + i < BOARD_SIZE); i++){
            attack = board[posY+i][posX+i];
            if (attack != '.') {
                if (isEnemyKing(attack, isWhite)) return true; 
                break;
            }
        }
        
        for (i = 1; (posY - i >= 0) && (posX + i < BOARD_SIZE); i++) {
            attack = board[posY-i][posX+i];
            if (attack != '.') {
                if (isEnemyKing(attack, isWhite)) return true;
                break;
            }
        }
        
        for (i = 1; (posY + i < BOARD_SIZE) && (posX - i >= 0); i++) {
            attack = board[posY+i][posX-i];
            if (attack != '.') {
                if (isEnemyKing(attack, isWhite)) return true;
                break;
            }
        }
        
        for (i = 1; (posY - i >= 0) && (posX - i >= 0); i++) {
            attack = board[posY-i][posX-i];
            if (attack != '.') {
                if (isEnemyKing(attack, isWhite)) return true;
                break;
            }
        }
        return false;
    }
    

    private boolean checkRookMoves(boolean isWhite) {
        byte i = 1;
        for (i = 1; posX - i >= 0; i++) {
            attack = board[posY][posX-i];
            if (attack != '.') {
                if (isEnemyKing(attack, isWhite)) return true;
                break;
            }
        }
        
        for (i = 1; posX + i < BOARD_SIZE; i++) {
            attack = board[posY][posX+i];
            if (attack != '.') {
                if (isEnemyKing(attack, isWhite)) return true;
                break;
            }
        }
        
        for (i = 1; posY + i < BOARD_SIZE; i++) {
            attack = board[posY+i][posX];
            if (attack != '.') {
                if (isEnemyKing(attack, isWhite)) return true;
                break;
            }
        }
        
        for (i = 1; posY - i >= 0; i++) {
            attack = board[posY-i][posX];
            if (attack != '.') {
                if (isEnemyKing(attack, isWhite)) return true;
                break;
            }
        }
        return false;
    }

    
    private boolean checkQueenMoves(boolean isWhite) {
        return checkBishopMoves(isWhite) || checkRookMoves(isWhite);
    }
    

    private char findNextPiece() {
        while (posY < BOARD_SIZE) {
            while (posX < BOARD_SIZE) {
                if (board[posY][posX] != '.') {
                    return board[posY][posX];
                } else posX++;
            }
            posX = 0;
            posY++;
        }
        if (posX == BOARD_SIZE) posX--;
        if (posY == BOARD_SIZE) posY--;
        return ' ';
    }
    
    private boolean isEnemyKing(char piece, boolean isWhite) {
        boolean retval = false;
        if (isWhite) {
            if (piece == 'k') retval = true;
        } else if (piece == 'K') retval = true;
        return retval;
    }
    
}