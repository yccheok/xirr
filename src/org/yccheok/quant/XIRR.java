/*

This work is licensed under the Creative Commons Attribution-ShareAlike 3.0 
Unported License. To view a copy of this license, visit 
http://creativecommons.org/licenses/by-sa/3.0/ or send a letter to Creative 
Commons, 444 Castro Street, Suite 900, Mountain View, California, 94041, USA.

Ported from http://stackoverflow.com/a/5185144/72437 C# code.

*/

package org.yccheok.quant;

public class XIRR {
    public static final double tol = 0.001;
    
    public static double composeFunctions(double f1, double f2)
    {
        return f1 + f2;
    }
    
    public static double f_xirr(double p, double dt, double dt0, double x)
    {
        return p * Math.pow((1.0 + x), ((dt0 - dt) / 365.0));
    }
    
    public static double df_xirr(double p, double dt, double dt0, double x)
    {
        return (1.0 / 365.0) * (dt0 - dt) * p * Math.pow((x + 1.0), (((dt0 - dt) / 365.0) - 1.0));
    } 
    
    public static double total_f_xirr(double[] payments, double[] days, double x)
    {
        double resf = 0.0;

        for (int i = 0; i < payments.length; i++)
        {
            resf = composeFunctions(resf, f_xirr(payments[i], days[i], days[0], x));
        }

        return resf;
    }
    
    public static double total_df_xirr(double[] payments, double[] days, double x)
    {
        double resf = 0.0;
        
        for (int i = 0; i < payments.length; i++)
        {
            resf = composeFunctions(resf, df_xirr(payments[i], days[i], days[0], x));
        }

        return resf;
    }
    
    public static double Newtons_method(double[] payments, double[] days, double guess)
    {
        double x0 = guess;
        double x1 = 0.0;
        double err = 1e+100;

        while (err > tol)
        {
            x1 = x0 - total_f_xirr(payments, days, x0) / total_df_xirr(payments, days, x0);
            err = Math.abs(x1 - x0);
            x0 = x1;
        }

        return x0;
    }
    
    public static final void main(String[] args)
    {
        double[] payments = {-6800,1000,2000,4000}; // payments
        double[] days = {1,8,16,25};                // days of payment (as day of year)
        double xirr = Newtons_method(payments, days, 0.1);

        System.out.println("XIRR value is " + xirr);
    }    
}
