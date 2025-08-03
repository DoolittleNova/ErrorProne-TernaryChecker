package edu.appstate.cs.examples;

public class BadTernary {
    private boolean someBool;
    private static int p1 = 1;
    private static int p2 = 2;
    private static int p3 = 3;
    private static int p4 = 4;
    public static void someMethod() {
        // Contains a nested ternary
        int result = 0;
        result = p1 > p2 ? p1 : (p2 > p3 ? p2 : (p3 > p4 ? p3 : p4));
        System.out.printf("Result of ternary is: %d\n", result);
    }
}
