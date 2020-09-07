package edu.mit.compilers.semantic.checker.test;

import JunitTest.TestTool.IRMaker;
import edu.mit.compilers.ir.expression.literal.IRIntLiteral;
import edu.mit.compilers.semantic.checker.LiteralValueRule;
import edu.mit.compilers.semantic.checker.SemanticError;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LiteralValueRuleCheckerTest {

    @Test
    public void testIntLiteral() {
        innerTestIntLiteral("-1", false);
        innerTestIntLiteral("-9223372036854775807", false);
        innerTestIntLiteral("-9223372036854775808", false);
        innerTestIntLiteral("-9223372036854775809", true);
        innerTestIntLiteral("9223372036854775807", false);
    }

    private void innerTestIntLiteral(String literalValue, boolean hasError) {
        IRIntLiteral value = IRMaker.makeIRIntLiteral(literalValue);
        LiteralValueRule rule = new LiteralValueRule();
        SemanticError error = value.accept(rule);
        assertEquals(hasError, error.hasError(), error.toString());
    }
}
