/*

This work is licensed under the Creative Commons Attribution-ShareAlike 3.0 
Unported License. To view a copy of this license, visit 
http://creativecommons.org/licenses/by-sa/3.0/ or send a letter to Creative 
Commons, 444 Castro Street, Suite 900, Mountain View, California, 94041, USA.

Ported from http://stackoverflow.com/a/5185144/72437 C# code.

*/

package org.yccheok.quant;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

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
        
        /*
        // this scenario fails with Newton's but succeeds with slower Bisection
        new CashItem(new DateTime(2012, 6, 1), 0.01),
        new CashItem(new DateTime(2012, 7, 23), 3042626.18),
        new CashItem(new DateTime(2012, 11, 7), -491356.62),
        new CashItem(new DateTime(2012, 11, 30), 631579.92),
        new CashItem(new DateTime(2012, 12, 1), 19769.5),
        new CashItem(new DateTime(2013, 1, 16), 1551771.47),
        new CashItem(new DateTime(2013, 2, 8), -304595),
        new CashItem(new DateTime(2013, 3, 26), 3880609.64),
        new CashItem(new DateTime(2013, 3, 31), -4331949.61)        
        */ 
        
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(0);
        System.out.println(calendar);
        
        Calendar calendar0 = Calendar.getInstance();
        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        Calendar calendar3 = Calendar.getInstance();
        Calendar calendar4 = Calendar.getInstance();
        Calendar calendar5 = Calendar.getInstance();
        Calendar calendar6 = Calendar.getInstance();
        Calendar calendar7 = Calendar.getInstance();
        Calendar calendar8 = Calendar.getInstance();

        calendar0.set(2012, 5, 1, 0, 0, 0);
        calendar1.set(2012, 6, 23, 0, 0, 0);
        calendar2.set(2012, 10, 7, 0, 0, 0);
        calendar3.set(2012, 10, 30, 0, 0, 0);
        calendar4.set(2012, 11, 1, 0, 0, 0);
        calendar5.set(2013, 0, 16, 0, 0, 0);
        calendar6.set(2013, 1, 8, 0, 0, 0);
        calendar7.set(2013, 2, 26, 0, 0, 0);
        calendar8.set(2013, 2, 31, 0, 0, 0);
        
        payments = new double[] {
            0.01, 
            3042626.18,
            -491356.62,
            631579.92,
            19769.5,
            1551771.47,
            -304595,
            3880609.64,
            -4331949.61
        };
        
        days = new double[] {
            (double)getDateDiff(calendar0, calendar),
            (double)getDateDiff(calendar1, calendar),
            (double)getDateDiff(calendar2, calendar),
            (double)getDateDiff(calendar3, calendar),
            (double)getDateDiff(calendar4, calendar),
            (double)getDateDiff(calendar5, calendar),
            (double)getDateDiff(calendar6, calendar),
            (double)getDateDiff(calendar7, calendar),
            (double)getDateDiff(calendar8, calendar),
        };
        
        guess = 0.1;
        expResult = 0.0970640616333018;
        result = XIRR.Newtons_method(payments, days, guess);
        assertEquals(expResult, result, 0.0);        
    }

    public static long getDateDiff(Calendar calendar1, Calendar calendar2) {
        return getDateDiff(calendar1.getTime(), calendar2.getTime(), TimeUnit.DAYS);
    }
    
    public static long getDateDiff(Date date1, Date date2) {
        return getDateDiff(date1, date2, TimeUnit.DAYS);
    }
    
    public static long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
        long diffInMillies = Math.abs(date2.getTime() - date1.getTime());
        return timeUnit.convert(diffInMillies, TimeUnit.MILLISECONDS);
    }

    /**
     * Test of total_f_xirr method, of class XIRR.
     */
    @Test
    public void testTotal_f_xirr() {
        System.out.println("total_f_xirr");
        double[] payments = { -6800, 1000, 2000, 4000 };
        double[] days = { 1, 8, 16, 25 };
        double guess = 0.7;
        double expResult =  9.578124885343641;
        double result = XIRR.total_f_xirr(payments, days, guess);
        assertEquals(expResult, result, 0.0);
        
        guess = 0;
        expResult =  200;
        result = XIRR.total_f_xirr(payments, days, guess);
        assertEquals(expResult, result, 0.0);        
        
        guess = -0.7;
        expResult =   654.3372321995989;
        result = XIRR.total_f_xirr(payments, days, guess);
        assertEquals(expResult, result, 0.0);           
    }
}
