package za.co.wethinkcode.examples.toyrobot;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.min;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] arg) {
//        List<List<Integer>> integers = List.of(
//                List.of(1,2,3),
//                List.of(4,5,6),
//                List.of(9,8,9)
//        );
//        int output = Main.diagonalDifference(integers);
//
//        System.out.println(output);

        staircase(4);

    }


//
//    public static int formingMagicSquare(List<List<Integer>> s) {
//        // Write your code here
//
//    }


    public static void staircase(int n) {
        // Write your code here
        StringBuilder stringBuilder = new StringBuilder();
        int spaces = n-1;
        String wh = "#";
        for (int i = 1; i< n+1; i++){
            for (int spa = 0 ; spa< spaces; spa++){
                stringBuilder.append(" ");
            }
            for (int j=0 ; j< i;j++){
                stringBuilder.append(wh);
            }
            stringBuilder.append("\n");
            spaces--;
        }
        System.out.println(stringBuilder.toString());

    }



    public static int hourglassSum(List<List<Integer>> arr) {
        // Write your code here
        int holder = 0;
        List<Integer> integers = new ArrayList<>();
        for (int i = 0; i<arr.size()-2; i++){
            for (int j = 0; j< arr.size()-2; j++) {
                holder += arr.get(i).get(j);
                holder += arr.get(i).get(j+1);
                holder += arr.get(i).get(j+2);
                holder += arr.get(i+2).get(j);
                holder += arr.get(i+2).get(j+1);
                holder += arr.get(i+2).get(j+2);
                holder += arr.get(i+1).get(j+1);
                integers.add(holder);
                holder = 0;

            }
        }
        int higher = integers.get(0);
        for (int i = 1; i<integers.size(); i++){
            if(integers.get(i) > higher){
                higher = integers.get(i);
            }
        }
        return  higher;

    }



    public static int diagonalDifference(List<List<Integer>> arr) {
        // Write your code here
        int firstDigonal = 0;
        int secondDigonal = 0;
        int max = arr.size()-1;
        for (int i = 0; i< arr.size(); i++){
            for (int j = 0 ; j< arr.size(); j++){
                if (i==0){
                    firstDigonal += arr.get(j).get(j);
                } else if (i==1) {
                    secondDigonal += arr.get(max-j).get(j);
                }
            }
        }
        int bigger = Math.max(firstDigonal , secondDigonal);
        int smaller = Math.min(firstDigonal, secondDigonal);
        return bigger - smaller;
    }
}
