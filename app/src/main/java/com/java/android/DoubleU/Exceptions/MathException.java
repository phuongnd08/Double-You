package com.java.android.DoubleU.Exceptions;

public class MathException extends Exception {

    /**
     *  To throw exceptions related to Maths handling 
     */
    private static final long serialVersionUID = 1L;
    
    public MathException(String message){
        super(message);
    }
}
