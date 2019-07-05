package com.leti;

import java.util.Arrays;

class Graph {
    private int[][] vrtx;
    private static final int INF = 10_000_000;

    Graph(){
        vrtx=new int[26][26];
        for(int i=0;i<vrtx.length;i++) {
            Arrays.fill(vrtx[i], INF);
            vrtx[i][i]=0;
        }
    }

    void addEdge(int v,int u,int cap){
        vrtx[v][u]=cap;
    }

    int[][] ford_uorshell(){
        int[][] paths=vrtx.clone();

        for(int i=0;i<paths.length;i++) {
            for (int v = 0; v < paths.length; v++) {
                for (int u = 0; u < paths.length; u++) {
                    paths[v][u] = Math.min(paths[v][u], paths[v][i] + paths[i][u]);
                }
            }
        }

        return paths;
    }

    void printMatr(int[][] matr){
        System.out.print("  ");
        for(int i=0;i<matr.length;i++)
            System.out.print((char)(i+'a')+"    ");
        System.out.print('\n');

        for(int i=0;i<matr.length;i++){
            System.out.print((char)(i+'a')+" ");
            for(int j=0;j<matr[i].length;j++){
                if(matr[i][j]==INF)
                    System.out.print("INF  ");
                else
                    System.out.print(matr[i][j]+"    ");
            }
            System.out.print('\n');
        }
    }
}
