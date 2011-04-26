// Programming Challenges problem 110107
// Valladolid problem 10196
/* sample inputs from Uva Judge: 
 * 
TEST CASE 1:
..k.....
ppp.pppp
........
.R...B..
........
........
PPPPPPPP
K.......

rnbqkbnr
pppppppp
........
........
........
........
PPPPPPPP
RNBQKBNR

rnbqk.nr
ppp..ppp
....p...
...p....
.bPP....
.....N..
PP..PPPP
RNBQKB.R

........
........
........
...p....
....K...
........
........
........

........
........
........
........
........
........
......p.
.......K

......kb
........
........
........
........
........
.K......
.......R

........
........
...k....
........
.B.R....
........
........
.K...... 

N......N
........
.k......
........
........
........
........
N......N

n......n
........
........
........
........
......K.
........
n......n

Q......Q
........
........
........
........
........
......k.
Q......Q

q......q
........
........
........
........
........
.K......
q......q

........
........
...k....
........
.B.R....
........
........
.K......

..k.....
ppp.pppp
........
.R...B..
........
........
PPPPPPPP
K.......

rnbqkbnr
pppppppp
........
........
........
........
PPPPPPPP
RNBQKBNR

rnbqk.nr
ppp..ppp
....p...
...p....
.bPP....
.....N..
PP..PPPP
RNBQKB.R

........
........
........
........
........
........
........
........

rnbqk.nr
ppp..ppp
....p...
...p....
.bPP....
.....N..
PP..PPPP
RNBQKB.R

TEST CASE 2:
K......k
........
........
........
........
........
........
.......b

K......k
........
..p.....
........
........
........
........
.......b

Kp.....k
prn.....
..p.....
........
........
........
........
.......b

K..R...k
........
..p.....
........
........
........
........
.......b

K.pr...k
b....q..
..p..nn.
........
..b.....
........
........
bq.....b

K..RB..k
.....R.P
..p.....
.......Q
........
........
........
.......b

......B.
........
..p.....
........
..Kpk..r
........
........
.......b

......P.
...K.k..
..p.....
........
........
........
........
.......b

........
...K....
pppppppp
..n.n...
........
........
.k......
.......b

........
...K....
pppppppp
........
........
........
k.......
.......b

........
........
........
........
........
........
........
........
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.Scanner;


public class CheckTheCheck {
    
    static final int BOARD_SIZE = 8;
    int game = 1;
    boolean oneTime = true;
    PrintStream cout = System.out;
    Scanner sc = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
    boolean inCheck = false;
    char attack = ' ';
    byte posX = 0;
    byte posY = 0;
    char[][] board =  new char[BOARD_SIZE][BOARD_SIZE]; 
    

    /**
     * @param args
     */
    public static void main(String[] args) throws IOException {
        CheckTheCheck check = new CheckTheCheck();
        String line = null;
        while ((line = check.sc.nextLine()) != null) {
            if (check.oneTime) {
                check.cout.println();
                check.oneTime = false;
            }
            if (line.length() == 0) line = check.sc.nextLine();
            for (int y = 0; y < BOARD_SIZE; y++) {
                if (line == null) System.exit(0);
                for (int x = 0; x < BOARD_SIZE; x++) {
                    check.board[y][x] = line.charAt(x);
                }
                line = check.sc.nextLine();
            }
            check.drawBoard();
            
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
/*        while (check.sc.hasNextLine()) {
            if (check.oneTime) {
                check.cout.println();
                check.oneTime = false;
            }
            for (int y = 0; y < BOARD_SIZE; y++) {
                    if ((line = check.sc.nextLine()) == null) System.exit(0);
                for (int x = 0; x < BOARD_SIZE; x++) {
                    check.board[y][x] = line.charAt(x);
                }
            }
            check.drawBoard();
            
            StringBuilder out = new StringBuilder("game #");
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
            out.append("king is in check");
            check.cout.println(out.toString());
            check.posY = 0;
            check.posX = 0;
            check.game++;
            check.sc.nextLine();
        } */
    }
    
    /**
     * given the board in memory, find if either king is in check.
     * @return 'K' if white king in check, 'k' if black king in check, '.' if neither
     *    '-' if board has no pieces (end of game)
     */
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
    
    /**
     * Checks if the piece in question puts the opposing king in check
     * Assumes posX and posY index position of current piece.
     * Future enhancement -- make pieces aware of their own pos
     * @param piece
     * @return
     */
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
        case 'k':
            inCheck = checkKingMoves(false);
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
        case 'K':
            inCheck = checkKingMoves(true);
        }
        return inCheck;
    }
    
    /**
     * legal capture moves for white pawn: board[row+1][column+1, column-1]
     * same for black pawn, except row-1
     * @param isWhite
     * @return
     */
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

    /**
     * legal moves for knight: board[column-2][row-1]
     *                              [column-2][row+1]
     *                              [column+2][row-1]
     *                              [column+2][row+1]
     *                              [column-1][row-2]
     *                              [column+1][row-2]
     *                              [column-1][row+2]
     *                              [column+1][row+2]
     *           except when move is off the edge of board
     *           (is there a way to optimize the logic here?)
     *                              
     * @param isWhite
     * @return
     */
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
    
    /**
     * rules for Bishop moves: diagonals (x+n, y+n) (x+n, y-n)
     *                                   (x-n, y+n) (x-n, y-n)
     *      test each direction until you run into another piece. 
     *      if that piece isn't the king, test next direction.
     * @param isWhite
     * @return
     */
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
    
    /**
     * rules for rook moves: horizontals: [posY][posX+i]
     *                                    [posY][posX-i]
     *                                    [posY+i][posX]
     *                                    [posY-i][posX]
     *      test each direction until you run into another piece. 
     *      if that piece isn't the king, test next direction.
     * @param isWhite
     * @return
     */
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
    
    /**
     * The queen is simply a combination of Rook and Bishop moves.
     * @param isWhite
     * @return
     */
    private boolean checkQueenMoves(boolean isWhite) {
        return checkBishopMoves(isWhite) || checkRookMoves(isWhite);
    }
    
    /**
     * The rules for King moves: one move in to any adjacent square
     *                            [posY-1][posX-1]
     *                            [posY-1][posX]
     *                            [posY-1][posX+1]
     *                            [posY][posX-1]
     *                            [posY][posX+1]
     *                            [posY+1][posX-1]
     *                            [posY+1][posX]
     *                            [posY+1][posX+1]
     * @param isWhite
     * @return
     */
    private boolean checkKingMoves(boolean isWhite) {
        if (posY > 0) {
            if (posX > 0) {
                attack = board[posY-1][posX-1];
                if (isEnemyKing(attack, isWhite)) return true;
            }
            attack = board[posY-1][posX];
            if (isEnemyKing(attack, isWhite)) return true;
            if (posX < BOARD_SIZE - 1) {
                attack = board[posY-1][posX];
                if (isEnemyKing(attack, isWhite)) return true;
            }
        }
        
        if (posX > 0) {
            attack = board[posY][posX-1];
            if (isEnemyKing(attack, isWhite)) return true;
        }
        if (posX < BOARD_SIZE - 1) {
            attack = board[posY][posX+1];
            if (isEnemyKing(attack, isWhite)) return true;
        }
        
        if (posY < BOARD_SIZE - 1) {
            if (posX > 0) {
                attack = board[posY+1][posX-1];
                if (isEnemyKing(attack, isWhite)) return true;
            }
            attack = board[posY+1][posX];
            if (isEnemyKing(attack, isWhite)) return true;
            if (posX < BOARD_SIZE - 1) {
                attack = board[posY+1][posX+1];
                if (isEnemyKing(attack, isWhite)) return true;
            }
        }      
        return false;
    }
    
    /**
     * Search down each row. If at the end of the row, go to beginning of next column.
     * Return the letter of the piece you find.
     * @return letter of piece. blank space if no more pieces on the board.
     */
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
    
    public void drawBoard() {
        /*cout.println("+---+---+---+---+---+---+---+---+");
        cout.println("|   |   |   |   |   |   |   |   |");
        cout.println("+---+---+---+---+---+---+---+---+");
        cout.println("|   |   |   |   |   |   |   |   |");
        cout.println("+---+---+---+---+---+---+---+---+");
        cout.println("|   |   |   |   |   |   |   |   |");
        cout.println("+---+---+---+---+---+---+---+---+");
        cout.println("|   |   |   |   |   |   |   |   |");
        cout.println("+---+---+---+---+---+---+---+---+");
        cout.println("|   |   |   |   |   |   |   |   |");
        cout.println("+---+---+---+---+---+---+---+---+");
        cout.println("|   |   |   |   |   |   |   |   |");
        cout.println("+---+---+---+---+---+---+---+---+");
        cout.println("|   |   |   |   |   |   |   |   |");
        cout.println("+---+---+---+---+---+---+---+---+");
        cout.println("|   |   |   |   |   |   |   |   |");
        cout.println("+---+---+---+---+---+---+---+---+"); */
       for (int i = 0; i < BOARD_SIZE; i++) {
           cout.println("+---+---+---+---+---+---+---+---+");
            for (int y = 0; y < BOARD_SIZE; y++) {
                cout.print("| ");
                cout.print(board[i][y]);
                cout.print(" ");   
            }
            cout.print("|\n");
        }
       cout.println("+---+---+---+---+---+---+---+---+");
    }

}
