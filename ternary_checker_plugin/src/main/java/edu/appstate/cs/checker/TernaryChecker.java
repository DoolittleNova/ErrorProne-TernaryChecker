package main.java.edu.appstate.cs.checker;

import com.google.auto.service.AutoService;
import com.google.errorprone.BugPattern;
import com.google.errorprone.VisitorState;
import com.google.errorprone.bugpatterns.BugChecker;
import com.google.errorprone.matchers.Description;
import com.sun.source.tree.*;
import com.sun.source.tree.Tree.Kind;

import javax.lang.model.element.Name;

import static com.google.errorprone.BugPattern.LinkType.CUSTOM;
import static com.google.errorprone.BugPattern.SeverityLevel.WARNING;

@AutoService(BugChecker.class)
@BugPattern(
        name = "TernaryChecker",
        summary = "Checks for nested ternaries",
        severity = WARNING,
        linkType = CUSTOM,
        link = "https://github.com/DoolittleNova/ErrorProne-TernaryChecker"
)
public class TernaryChecker extends BugChecker implements
        BugChecker.ConditionalExpressionTreeMatcher {

    @Override
    public Description matchConditionalExpression(ConditionalExpressionTree tree, VisitorState state) {
        // Learning that ternaries are called conditional expressions makes both sense and nonsense.
        // We really want to see if the ternary tree is nested because it's terrible.
        /*
         * int result = param1 > param2 ? param1 : (param2 > param3 ? param2 : (param3 > param4 ? param3 : param4));
         *                  GT              ID     P        GT          ID     P        GT          ID      ID
        */

        /*
         * The point of conditional expressions to to be short and sweet.
         * Meaning the moment a paranthesis enters the expression, the programmer has gone astray 
         */
        boolean stillGood;
        if (tree.getCondition() != null) {
            stillGood = checkConditionalComponent(tree.getCondition());
            if (tree.getTrueExpression() != null) stillGood = checkConditionalComponent(tree.getTrueExpression()); // should throw other errors before we get here
            if (tree.getFalseExpression() != null) stillGood = checkConditionalComponent(tree.getFalseExpression()); // same as above
            if (!stillGood) return buildDescription(tree).setMessage(String.format("%s is/contains a nested ternary!", tree.toString())).build();
        }
        return Description.NO_MATCH;
    }

    /**
     * Check to see if a portion of a conditional contains another conditional expression
     * @param tree Usually an ExpresstionTree
     * @return boolean
     */
    private boolean checkConditionalComponent(Tree tree) {
        // Honestly we should just check if there is a question mark
        if (tree.toString().contains("(") || tree.toString().contains(")")) return !tree.toString().contains("?");
        return true;
    }

    private static final IllegalStateException malformedMethodInvocationTree(MethodInvocationTree tree) {
        return new IllegalStateException(String.format("Method name %s is malformed.", tree));
    }
}