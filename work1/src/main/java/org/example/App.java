package org.example;

import java.util.Scanner;

public class App
{
    public static void main( String[] args )
    {
        Funks f = new Funks();
        Scanner input = new Scanner(System.in);

        int x1 = input.nextInt();
        int y1 = input.nextInt();

        int x2 = input.nextInt();
        int y2 = input.nextInt();

        Funks.Point P1 = f.numberToPoint(x1);
        Funks.Point P2 = f.numberToPoint(y1);
        Funks.Point P3 = f.numberToPoint(x2);
        Funks.Point P4 = f.numberToPoint(y2);

        System.out.println(f.doIntersect(P1, P2, P3, P4));
    }
}
