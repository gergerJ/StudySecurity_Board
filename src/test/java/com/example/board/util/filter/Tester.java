package com.example.board.util.filter;


import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Tester {
    public static final String staticFinalExample = "1";
    public static String staticExample ;
    public final String finalExample;

    Tester(String staticExample , String finalExample){
        this.staticExample = staticExample;
        this.finalExample = finalExample;

    }


}
