package com.orient.weibao.controller;

import java.util.Scanner;

public class Test {

    public static void main(String[] args) {
        //先从键盘输入它的行数，自己定

        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入杨辉三角的行数");
        int n = scanner.nextInt();
        int num[][] = new int[n][];//定义一个n行的二维数组，因为列随着行数的变化而变化
        for (int i = 0; i < num.length; i++) {
            num[i] = new int[i + 1];//因为第一行有一个元素，第二行俩个，i从0开始，所以i行有1+1个元素
            for (int j = 0; j < num[i].length; j++) {
                if (j == 0 || i == j) {
                    //对角和第一列的都为1
                    num[i][j] = 1;
                } else {
                    num[i][j] = num[i - 1][j] + num[i - 1][j - 1];//其他元素都等于它上一个数据和它左上角的和
                }
            }
        }
        for (int i = 0; i < num.length; i++) {
            for (int j = 0; j < num[i].length; j++) {
                System.out.print(num[i][j] + "\t");
            }
            System.out.println();

        }
    }
}
