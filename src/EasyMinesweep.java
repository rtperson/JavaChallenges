import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 */

/**
 * @author Turnau-Roger
 *
 */
public class EasyMinesweep {
    
    protected int numField = 0;
    protected int curLine = 0;
    Integer lines = null;
    Integer columns = null;
    char [][] minearray = null;

    /**
     * @param args
     */
    public static void main(String[] args) {
        // create test input (creating all these in args would take mucho time)
        String[] field = new String[10];
        field[0] = "4 4 ";
        field[1] = "*...";
        field[2] = "....";
        field[3] = ".*..";
        field[4] = "....";
        field[5] = "3 5";
        field[6] = "**...";
        field[7] = ".....";
        field[8] = ".*...";
        field[9] = "0 0";
        
        // run it through our Minesweeper program and see how we do.
        EasyMinesweep minesweep = new EasyMinesweep();
        minesweep.playMinesweeper(field);
        

    }
    
    public void playMinesweeper(String [] field) {
        for (String f : field) {
            Pattern linePat = Pattern.compile("([\\d]+)[\\s]+([\\d]+)[\\s]*$");
            Matcher m = linePat.matcher(f);
            if (m.matches()) {
                if (minearray != null) {
                    writeOutField();
                }
                String strLines = m.group(1);
                String strCol = m.group(2);
                this.lines = Integer.valueOf(strLines);
                this.columns = Integer.valueOf(strCol);
                if (lines.intValue() == 0 && columns.intValue() == 0) {
                    System.exit(0);
                } else {
                    System.out.println();
                    System.out.println("Field #" + ++numField);
                    minearray = 
                        createMinefield(lines.intValue(), columns.intValue());
                    continue;
                }
            }
            parseLine(f, minearray, curLine++);
            
        }    
    }
 
    /**
     * Take an O(n) pass to initialize the minefields
     * @param columns
     * @param lines
     * @return
     */
    private char[][] createMinefield(int columns, int lines) {
        char[][] minefield = new char[columns][lines];
        for (int i = 0; i < lines; i++) {
            for (int j = 0; j < columns; j++) {
                minefield[j][i] = '0';
            }
        }
        return minefield;   
    }
    
    /**
     * For the current input line, read up to bomb. On bomb, increment
     * all adjacent fields.
     * @param in - the string to be parsed 
     * @param minefield - the current field for output
     * @param curLine - the line in the minefield represented by the current input
     */
    private void parseLine(String in, char[][] minefield, int curLine) {
        for (int i = 0; i < in.length(); i++) {
            char c = in.charAt(i);
            if (c == '*') {
                minefield[curLine][i] = '*';
                incAdjacentCells(minefield, curLine, i);
            }
        } 
    }
    
    /**
     * There are a maximum of 8 adjacent cells in our matrix -- 
     * 3 on top, 3 on the bottom, and 2 to each side. 
     * 
     * @param minefield
     * @param curLine
     * @param curCol
     */
    private void incAdjacentCells(char[][] minefield, int curLine, int curCol) {
        // get x-1, y-1
        int x = curLine - 1;
        int y = curCol - 1;
        
        while (y <= curCol + 1) {
            while (x <= curLine + 1) {
                if (x >= 0 && y >= 0
                    && x < (lines.intValue()-1) && y < (columns.intValue()-1)
                    && minefield[x][y] != '*') {
                    minefield[x][y]++;
                }
                ++x; 
            }
            ++y; 
            x = curLine - 1;
        }
        
    }
   
    private void writeOutField() {
        for (int i = 0; i < lines.intValue(); i++) {
            String output = new String(minearray[i]);
            System.out.println(output);
        }
        this.minearray = null; 
        this.curLine = 0;
        
    }

}
