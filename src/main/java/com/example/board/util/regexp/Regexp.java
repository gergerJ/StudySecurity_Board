package com.example.board.util.regexp;

public class Regexp {
    //정규식을 상수로 선언하여 사용! -> 메모리 사용이 많기 때문에 별도의 상수로 만듬
    public final static String ID_REG = "^[a-zA-Z0-9]*$";
    public final static String PASSWORD_REG  = "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[$@$!%*#?&])[A-Za-z[0-9]$@$!%*#?&]{8,16}$";
    public final static String NICKNAME_REG = "^[0-9a-zA-Z가-힣]{2,10}$";
    public final static String EMAIL_REG = "[0-9a-zA-Z]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";
}
