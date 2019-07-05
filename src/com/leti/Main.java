package com.leti;

import java.util.Scanner;

public class Main {

    void print_arr(int[][] arr){
        for(int i=0;i<arr.length;i++){
            System.out.print((char)(i+'a')+" ");
        }
    }

    public static void main(String[] args) {
	// write your code here
        Graph graph=new Graph();

        Scanner in=new Scanner(System.in);
        System.out.print("Enter edges num: ");
        int n=in.nextInt();
        in.nextLine();
        for(int i=0;i<n;i++) {
            System.out.print("Add edge: ");
            String[] line=in.nextLine().split(" ");
            graph.addEdge(line[0].charAt(0)-'a',line[1].charAt(0)-'a',Integer.parseInt(line[2]));
        }
        int[][]paths=graph.ford_uorshell();

        graph.printMatr(paths);
    }

}
