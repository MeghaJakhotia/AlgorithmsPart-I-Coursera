import edu.princeton.cs.algs4.Stack;

import java.util.Arrays;

public class Board {
    private final int boardDimension;
    private final int lengthOfTheArray;
    private final int[] goalArray;
    private int[] currentArray;
    private final int[][] boardCopy;

    public Board(int[][] tiles) {
        boardDimension = tiles.length;
        boardCopy = new int[boardDimension][boardDimension];
        System.arraycopy(tiles, 0, this.boardCopy, 0, boardDimension);
        lengthOfTheArray = boardDimension * boardDimension;
        currentArray = new int[lengthOfTheArray];
        goalArray = new int[lengthOfTheArray];
        int iter = 0;
        for (int row = 0; row < boardDimension; row++) {
            for (int col = 0; col < boardDimension; col++) {
                currentArray[iter] = tiles[row][col];
                goalArray[iter++] = iter;
            }
        }
        goalArray[--iter] = 0;
    }

    public String toString() {
        String str = Integer.toString(boardDimension);
        for (int iter = 0; iter < lengthOfTheArray; iter++) {
            if (iter % boardDimension == 0) {
                str = str.concat("\n");
            }
            str = str.concat(Integer.toString(currentArray[iter]) + " ");
        }
        return str;
    }

    public int dimension() {
        return boardDimension;
    }

    public int hamming() {
        int hammingValue = 0;
        for (int iter = 0; iter < lengthOfTheArray; iter++) {
            if (currentArray[iter] != goalArray[iter] && currentArray[iter] != 0) hammingValue++;
        }
        return hammingValue;
    }

    public int manhattan() {
        int manhattanValue = 0;
        int actualRow, actualCol, currentRow, currentCol, presentManhattanValue;
        for (int iter = 0; iter < lengthOfTheArray; iter++) {
            if (currentArray[iter] != 0) {
                actualRow = (currentArray[iter] - 1) / boardDimension;
                actualCol = (currentArray[iter] - 1) % boardDimension;
                currentRow = iter / boardDimension;
                currentCol = iter % boardDimension;
                presentManhattanValue = Math.abs(actualRow - currentRow) + Math.abs(actualCol - currentCol);
                manhattanValue += presentManhattanValue;
            }
        }
        return manhattanValue;
    }

    public boolean isGoal() {
        return hamming() == 0;
    }

    public boolean equals(Object y) {
        if (y == null) return false;
        if (y == this) return true;
        if (y.getClass() != this.getClass()) return false;
        Board that = (Board) y;
        return Arrays.deepEquals(this.boardCopy, that.boardCopy);
    }

    private int[][] to2DArray(int[] oneD) {
        int row, col;
        int[][] theReturning = new int[boardDimension][boardDimension];
        for (int iteration = 0; iteration < oneD.length; iteration++) {
            row = iteration / boardDimension;
            col = iteration % boardDimension;
            theReturning[row][col] = oneD[iteration];
        }
        return theReturning;
    }

    public Iterable<Board> neighbors() {
        Stack<Board> allBoards = new Stack<>();
        Board currentBoard;
        int[] arrayValues;
        int zeroPosition = findingZeroLocation();
        int numberOfBoardsPossible = noOfBoardsPossibleFunction(zeroPosition);
        arrayValues = allPositionsPossible(numberOfBoardsPossible, zeroPosition);
        for (int iter = 0; iter < arrayValues.length; iter++) {
            currentBoard = new Board(this.boardCopy);
            currentBoard.swap(zeroPosition, arrayValues[iter]);
            allBoards.push(new Board(to2DArray(currentBoard.currentArray)));
        }
        return allBoards;
    }

    public Board twin() {
        Board toReturn = new Board(this.boardCopy);
        if (toReturn.currentArray[0] != 0 && toReturn.currentArray[1] != 0)
            toReturn.swap(0, 1);
        else
            toReturn.swap(2, 3);
        // int randomIndex1 = StdRandom.uniform(lengthOfTheArray);
        // int randomIndex2 = StdRandom.uniform(lengthOfTheArray);
        // toReturn.swap(randomIndex1, randomIndex2);
        return toReturn;
    }


    private int findingZeroLocation() {
        for (int iter = 0; iter < lengthOfTheArray; iter++) {
            if (currentArray[iter] == 0) return iter;
        }
        return -1;
    }

    private int noOfBoardsPossibleFunction(int zeroAt) {
        int noOfBoardsPossible;
        if (zeroAt == 0 || zeroAt == boardDimension - 1 || zeroAt == lengthOfTheArray - 1 ||
                zeroAt == boardDimension * (boardDimension - 1)) {
            noOfBoardsPossible = 2;
        } else if (zeroAt < boardDimension || zeroAt > ((boardDimension * boardDimension) - boardDimension) ||
                zeroAt % boardDimension == 0 || (zeroAt + 1) % boardDimension == 0) {
            noOfBoardsPossible = 3;
        } else {
            noOfBoardsPossible = 4;
        }
        return noOfBoardsPossible;
    }

    private int[] allPositionsPossible(int noOfBoards, int zeroAt) {
        int[] arrayValues = new int[noOfBoards];
        int index = -1;
        if ((zeroAt + 1) % boardDimension > 0) arrayValues[++index] = zeroAt + 1;
        if ((zeroAt) % boardDimension > 0) arrayValues[++index] = zeroAt - 1;
        if (zeroAt + boardDimension < lengthOfTheArray) arrayValues[++index] = zeroAt + boardDimension;
        if (zeroAt - boardDimension > 0) arrayValues[++index] = zeroAt - boardDimension;
        return arrayValues;
    }

    private void swap(int zeroAt, int randomIndex) {
        int temporary = this.currentArray[zeroAt];
        this.currentArray[zeroAt] = this.currentArray[randomIndex];
        this.currentArray[randomIndex] = temporary;
    }


}


