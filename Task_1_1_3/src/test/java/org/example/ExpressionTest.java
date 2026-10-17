package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Tests for Expression hierarchy and operations.
 */
public class ExpressionTest {

    @Test
    public void testAddMulAndPipeline() {
        Expression e = Parser.parse("(3 + (2 * x))");
        e.print();
        
        assertEquals("(3+(2*x))", e.toString());
        assertEquals(23, e.eval("x = 10")); 
        Expression de = e.derivative("x");
        assertEquals("(0+((0*x)+(2*1)))", de.toString()); 
    }


    @Test
    public void testSubDivAndEdgeCases() {

        Expression e = Parser.parse("((10-x)/(y/2))");
        assertEquals("((10-x)/(y/2))", e.toString());
        assertEquals(4, e.eval("x = 2; y = 4")); 

        Expression de = e.derivative("x");
        assertNotNull(de.toString()); 


        assertThrows(IllegalArgumentException.class, () -> e.eval("x = 2"));
    }


    @Test
    public void testLeavesAndMain() {
        assertEquals("42", Parser.parse("42").toString());
        assertEquals("foo", Parser.parse("foo").toString());

        Main.main(new String[0]);
    }
}