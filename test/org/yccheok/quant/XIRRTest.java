/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.yccheok.quant;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author yccheok
 */
public class XIRRTest {
    
    public XIRRTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of Newtons_method method, of class XIRR.
     */
    @Test
    public void testNewtons_method() {
        System.out.println("Newtons_method");
        double[] payments = { -6800, 1000, 2000, 4000 };
        double[] days = { 1, 8, 16, 25 };
        double guess = 0.1;
        double expResult =  0.7467420788881806;
        double result = XIRR.Newtons_method(payments, days, guess);
        assertEquals(expResult, result, 0.0);
    }    
}
