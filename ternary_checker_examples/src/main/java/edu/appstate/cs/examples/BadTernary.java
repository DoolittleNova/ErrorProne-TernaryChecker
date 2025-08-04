package edu.appstate.cs.examples;

public class BadTernary {
    private static int param1 = 1;
    private static int param2 = 2;
    private static int param3 = 3;
    private static int param4 = 4;
    public static void someMethod() {
        // Contains a nested ternary
        int resulta = param1 > param2 ? param1 : (param2 > param3 ? param2 : (param3 > param4 ? param3 : param4));
        int resultb = (param1 > param2 ? param1 : param2) > param3 ? param3 : param4;
        int resultc = param1 > param2 ? param1 : param2;
        //System.out.printf("Result of ternary is: %d\n", result);
    }
}
