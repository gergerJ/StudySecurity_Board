package com.example.board.util.filter;

import org.junit.jupiter.api.Test;

public class StaticTestClass {

    @Test
    public void changeValues(){
        //Tester.staticFinalExample = "0";
        //Tester.staticExample = "2";
        //finalExample = "3";

        Tester tester = new Tester("2", "3");
        System.out.println("staticFinalExample = " + Tester.staticFinalExample);
        System.out.println("staticExample = " + Tester.staticExample);
        System.out.println("finalExample = " + tester.finalExample);

        Tester tester1 = new Tester("5", "6");
        //Tester.staticFinalExample  = "4";
        System.out.println("staticFinalExample = " + Tester.staticFinalExample);
        System.out.println("staticExample = " + Tester.staticExample);
        System.out.println("finalExample = " + tester1.finalExample);

    }
}
