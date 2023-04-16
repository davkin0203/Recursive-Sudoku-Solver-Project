/* David King
 * CSE 017
 * 5 Oct. 2022
 * HW3
 */

import java.util.ArrayList;
import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;

public class Board{

    private ArrayList<ArrayList<Integer>> board = new ArrayList<ArrayList<Integer>>(9);
    private ArrayList<Integer> availableNumbers = new ArrayList<Integer>(9);
    final int EMPTY = 0;

    ArrayList<Integer> row = new ArrayList<Integer>(9);

    // ArrayList<Integer> block = new ArrayList<Integer>(9);
    // ArrayList<ArrayList<Integer>> blocks = new ArrayList<ArrayList<Integer>>(9);


    public Board(String fileName){
        board = new ArrayList<ArrayList<Integer>>(9);
        for(int  i = 0; i < board.size(); i++){
            board.add(new ArrayList<Integer>(9));
            board.get(i).add(EMPTY);
        }
        for(int i = 0; i < 9; i++){
            availableNumbers.add(9);
        }
        readBoard(fileName);
        
    }

    
    /** 
     * @param fileName
     * @throws IllegalArgumentException
     */
    private void readBoard(String fileName)
                        throws IllegalArgumentException{
        //read in board
        File file = new File(fileName);
        Integer element = 0;

        // reading in file
        try{
        Scanner fileScanner = new Scanner(file);
        while (fileScanner.hasNextLine()) {
            for(int i = 1; i <= 9; i++){
            row = new ArrayList<Integer>(9);

                for(int j = 1; j <= 9; j++){
                    element = fileScanner.nextInt();
                    // try{
                    //     isAvailable(i);
                    // } catch(IllegalArgumentException e){
                    //    System.out.println("IllegalArgumentException :: Cell that failed " + availableNumbers.get(i));
                    // }

                    row.add(element);
                }
                board.add(row);
            }

            // setting available numbers according to board that was read in
            for(int i = 0; i < 9; i++){
                for(int j = 0; j < 9; j++){
                    if(board.get(i).get(j) != 0){
                        availableNumbers.set(board.get(i).get(j) - 1, availableNumbers.get(board.get(i).get(j) - 1) - 1);
                    }
                }
            }


            // creating an array list of blocks
        //     for(int i = 1 ; i <= 9; i++){
        //         block = new ArrayList<Integer>(9);
        //                 if(i == 1){
        //                 for(int k = 0; k < 3; k++){
        //                     for(int j = 0; j < 3; j++){
        //                         block.add(board.get(k).get(j));
        //                     }
        //                 }
        //                 blocks.add(block);
        //             } else if(i == 2){
        //                 for(int k = 0; k < 3; k++){
        //                     for(int j = 3; j < 6; j++){
        //                         block.add(board.get(k).get(j));
        //                     }
        //                 }
        //                 blocks.add(block);
        //             } else if(i == 3){
        //                 for(int k = 0; k < 3; k++){
        //                     for(int j = 6; j < 9; j++){
        //                         block.add(board.get(k).get(j));
        //                     }
        //                 }
        //                 blocks.add(block);
        //             } else if(i == 4){
        //                 for(int k = 3; k < 6; k++){
        //                     for(int j = 0; j < 3; j++){
        //                         block.add(board.get(k).get(j));
        //                     }
        //                 }
        //                 blocks.add(block);
        //             } else if(i == 5){
        //                 for(int k = 3; k < 6; k++){
        //                     for(int j = 3; j < 6; j++){
        //                         block.add(board.get(k).get(j));
        //                     }
        //                 }
        //                 blocks.add(block);
        //             } else if(i == 6){
        //                 for(int k = 3; k < 6; k++){
        //                     for(int j = 6; j < 9; j++){
        //                         block.add(board.get(k).get(j));
        //                     }
        //                 }
        //                 blocks.add(block);
        //             } else if(i == 7){
        //                 for(int k = 6; k < 9; k++){
        //                     for(int j = 0; j < 3; j++){
        //                         block.add(board.get(k).get(j));
        //                     }
        //                 }
        //                 blocks.add(block);
        //             } else if(i == 8){
        //                 for(int k = 6; k < 9; k++){
        //                     for(int j = 3; j < 6; j++){
        //                         block.add(board.get(k).get(j));
        //                     }
        //                 }
        //                 blocks.add(block);
        //             } else if(i == 9){
        //                 for(int k = 6; k < 9; k++){
        //                     for(int j = 6; j < 9; j++){
        //                         block.add(board.get(k).get(j));
        //                     }
        //                 }
        //                 blocks.add(block);
        //             }
        // }

            // checking take make sure moves is legal
            for(int i = 0; i < 9; i++){
                for(int j = 0; j < 9; j++){
                    try{
                        checkMove(i, j);
                    } catch(IllegalArgumentException e){
                        System.out.println("IllegalArgumentException :: Cell that failed " + availableNumbers.get(i));
                    }
                }
            }
        }
            fileScanner.close();
        }catch(FileNotFoundException e){
            System.out.println("File not found :: " + file);
        }
    }

    
    /** 
     * @param row
     * @param col
     * @return boolean
     */
    private boolean checkMove(int row, int col){
        int num = board.get(row).get(col);
        if(num == 0){
            return true;
        }

        for(int j = 0; j < 9; j++){
            int temp = board.get(j).get(col);
                if(temp == num && j != row){
                    return false;
                }
        }

        for(int j = 0; j < 9; j++){
            int temp = board.get(row).get(j);
                if(temp == num && j != col){
                    return false;
                }
        }

        int rowNew = row % 3;
        int colNew = col % 3;
        for(int i = row - rowNew; i < row - rowNew + 3; ++i){
            for(int j = col - colNew; j < col - colNew + 3; ++j){
                if(i != row && j != col && board.get(i).get(j) == num){
                    return false;
                }
            }
        }
        return true;

        // for(int i = 0; i < 9; i++){
        //     // checks row
        //     if((num == board.get(row).get(i)) && (i != row)){
        //         return false;
        //     // checks column
        //     } else if((num == board.get(i).get(col)) && (i != col)){
        //         return false;

        //     } else if((row < 3 && col < 3) && (i != col && i != row)){
        //         if(num == blocks.get(0).get(i)){
        //             return false;
        //         }
        //     }else if((row < 3 && (col >= 3 && col < 6)) && (i != col && i != row)){
        //         if(num == blocks.get(1).get(i)){
        //             return false;
        //         }
        //     }else if((row < 3 && (col >= 6 && col < 9)) && (i != col && i != row)){
        //         if(num == blocks.get(2).get(i)){
        //             return false;
        //         }
        //     }else if(((row >= 3 && row < 6) && col < 3) && (i != col && i != row)){
        //         if(num == blocks.get(3).get(i)){
        //             return false;
        //         }
        //     }else if(((row >= 3 && row < 6) && (col >= 3 && col < 6)) && (i != col && i != row)){
        //         if(num == blocks.get(4).get(i)){
        //             return false;
        //         }
        //     }else if(((row >= 3 && row < 6) && (col >= 6 && col < 9)) && (i != col && i != row)){
        //         if(num == blocks.get(5).get(i)){
        //             return false;
        //         }
        //     }else if(((row >= 6 && row < 9) && col < 3) && (i != col && i != row)){
        //         if(num == blocks.get(6).get(i)){
        //             return false;
        //         }
        //     }else if(((row >= 6 && row < 9) && (col >= 3 && col < 6)) && (i != col && i != row)){
        //         if(num == blocks.get(7).get(i)){
        //             return false;
        //         }
        //     }else if(((row >= 6 && row < 9) && (col >= 6 && col < 9)) && (i != col && i != row)){
        //         if(num == blocks.get(8).get(i)){
        //             return false;
        //         }
        //     }
        // }
    }

    
    /** 
     * @param row
     * @param col
     * @param value
     */
    // private void updateBlocks(int row, int col, int value){
    //     row += 1;
    //     col += 1;
    //     int row1 = ((int)(row / 3)) * 3;
    //     int index = (col / 3) + row1;

    //     if((col == 1 && row == 1) || (col == 4 && row == 1) || (col == 7 && row == 1) || (col == 1 && row == 4) || (col == 4 && row == 4) || (col == 7 && row == 4) || (col == 1 && row == 7) || (col == 4 && row == 7) || (col == 7 && row == 7)){
    //         blocks.get(index).set(0, value);
    //     }else if((col == 2 && row == 1) || (col == 5 && row == 1) || (col == 8 && row == 1) || (col == 2 && row == 4) || (col == 5 && row == 4) || (col == 8 && row == 4) || (col == 2 && row == 7) || (col == 5 && row == 7) || (col == 8 && row == 7)){
    //         blocks.get(index).set(1, value);
    //     }else if((col == 3 && row == 1) || (col == 6 && row == 1) || (col == 9 && row == 1) || (col == 3 && row == 4) || (col == 6 && row == 4) || (col == 9 && row == 4) || (col == 3 && row == 7) || (col == 6 && row == 7) || (col == 9 && row == 7)){
    //         blocks.get(index).set(2, value);
    //     }else if((col == 1 && row == 2) || (col == 4 && row == 2) || (col == 7 && row == 2) || (col == 1 && row == 5) || (col == 4 && row == 5) || (col == 7 && row == 5) || (col == 1 && row == 8) || (col == 4 && row == 8) || (col == 7 && row == 8)){
    //         blocks.get(index).set(3, value);
    //     }else if((col == 2 && row == 2) || (col == 5 && row == 2) || (col == 8 && row == 2) || (col == 2 && row == 5) || (col == 5 && row == 5) || (col == 8 && row == 5) || (col == 2 && row == 8) || (col == 5 && row == 8) || (col == 8 && row == 8)){
    //         blocks.get(index).set(4, value);
    //     }else if((col == 3 && row == 2) || (col == 6 && row == 2) || (col == 9 && row == 2) || (col == 3 && row == 5) || (col == 6 && row == 5) || (col == 9 && row == 5) || (col == 3 && row == 8) || (col == 6 && row == 8) || (col == 9 && row == 8)){
    //         blocks.get(index).set(5, value);
    //     }else if((col == 1 && row == 3) || (col == 4 && row == 3) || (col == 7 && row == 3) || (col == 1 && row == 6) || (col == 4 && row == 6) || (col == 7 && row == 6) || (col == 1 && row == 9) || (col == 4 && row == 9) || (col == 7 && row == 9)){
    //         blocks.get(index).set(6, value);
    //     }else if((col == 2 && row == 3) || (col == 5 && row == 3) || (col == 8 && row == 3) || (col == 2 && row == 6) || (col == 5 && row == 6) || (col == 8 && row == 6) || (col == 2 && row == 9) || (col == 5 && row == 9) || (col == 8 && row == 9)){
    //         blocks.get(index).set(7, value);
    //     }else if((col == 3 && row == 3) || (col == 6 && row == 3) || (col == 9 && row == 3) || (col == 3 && row == 6) || (col == 6 && row == 6) || (col == 9 && row == 6) || (col == 3 && row == 9) || (col == 6 && row == 9) || (col == 9 && row == 9)){
    //         blocks.get(index).set(8, value);
    //     }


    //     blocks.get(index).set(col - 1, value);
    // }

    
    /** 
     * @param digit
     * @return boolean
     */
    private boolean isAvailable(int digit){
        if(availableNumbers.get(digit - 1) > 0){
            return true;
        } else {
            //availableNumbers.set(digit - 1, availableNumbers.get(digit - 1) - 1);
            return false;
        }
    }

    
    /** 
     * @return boolean
     */
    private boolean noNumbersLeft(){
        for(int i = 0; i < 9; i++){
            if(availableNumbers.get(i) > 0){
                return false;
            }
        }
        return true; 
    }

    public void solve(){
        solve(0,0);
    }

    
    /** 
     * @param row
     * @param col
     * @return boolean
     */
    public boolean solve(int row, int col){
        if(noNumbersLeft() || row >= 9){
            System.out.println("Solved!");
            return true;
        }
        if(!noNumbersLeft()){
            if(col >= 9){
                col = 0;
                return solve(row + 1, col);
            } 

            if(board.get(row).get(col) != EMPTY){
                return solve(row, col + 1);
            }

            if(board.get(row).get(col) == EMPTY){
                for(int i = 1; i <= 9; i++){
                    if(isAvailable(i)){
                        //availableNumbers.set(i - 1, availableNumbers.get(i - 1) - 1);
                        board.get(row).set(col, i);
                        //updateBlocks(row, col, i);

                        if(checkMove(row, col)){
                            if(solve(row, col)){
                                return true;
                            }
                        }
                            //availableNumbers.set(i - 1, availableNumbers.get(i - 1) + 1);
                            //updateBlocks(row, col, EMPTY);
                    }
                }
                board.get(row).set(col, EMPTY);
            }
        }
        return false;
    }

    
    /** 
     * @return String
     */
    @Override
    public String toString(){
        String str = "";
        for(int i = 0; i < 9; i++){
            for(int j = 0; j < 9; j++){
                str += board.get(i).get(j) + " ";
            }
            str += "\n";
        }
        return str;
    }
}