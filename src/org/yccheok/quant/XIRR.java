/*

This work is licensed under the Creative Commons Attribution-ShareAlike 3.0 
Unported License. To view a copy of this license, visit 
http://creativecommons.org/licenses/by-sa/3.0/ or send a letter to Creative 
Commons, 444 Castro Street, Suite 900, Mountain View, California, 94041, USA.

Ported from http://stackoverflow.com/a/5185144/72437 C# code.

*/

package org.yccheok.quant;

public class XIRR {
    
    public static class Bracket 
    {
        public final double left;
        public final double right;
        
        public Bracket(double left, double right) 
        {
            this.left = left;
            this.right = right;
        }
    }
    
    public static final double tol = 1E-8;
    
    public static final double epsilon = 1E-12;
    
    public static Bracket find_bracket(double[] payments, double[] days, double guess)
    {
        final int max_iteration = 108;
        final double step = 0.5;
        
        double left = guess;
        double right = guess;
        
        double resl = total_f_xirr(payments, days, left);
        
        double resr = resl;
        int iteration = 0;
        
        while ((resl * resr) > 0 && iteration++ < max_iteration)
        {
            left = left - step;
            resl = total_f_xirr(payments, days, left);
            
            if ((resl * resr) <= 0)
            {
                break;
            }
            
            right = right + step;
            resr = total_f_xirr(payments, days, right);
        }
        
        if ((resl * resr) <= 0)
        {
            return new Bracket(left, right);
        }
        
        return null;
    }
    
    public static double composeFunctions(double f1, double f2)
    {
        return f1 + f2;
    }
    
    public static double f_xirr(double p, double dt, double dt0, double x)
    {
        if (x <= -1)
        {
            x = -1 + epsilon; // Very funky ... Better check what an IRR <= -100% means    
        }
        
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

        final int max_iteration = 216;
        int iteration = 0;
        
        do
        {
            double x1 = x0 - total_f_xirr(payments, days, x0) / total_df_xirr(payments, days, x0);
            double err = Math.abs(x1 - x0);
            x0 = x1;
            
            if (err <= tol)
            {
                return x0;
            }
        }
        while (iteration++ < max_iteration);
                
        return Double.NaN;
    }
    
    public static double Bisection_method(double[] payments, double[] days, double guess)
    {
        Bracket bracket = find_bracket(payments, days, guess);

        if (bracket == null)
        {
            return Double.NaN;
        }
        
        double left = bracket.left;
        double right = bracket.right;
        
        final int max_iteration = 216;
        int iteration = 0;
        double c = 0;

        do
        {
            c = (left + right) / 2;
            
            double resc = total_f_xirr(payments, days, c);
            
            if (Math.abs(resc) <= epsilon || ((right - left) / 2.0) < tol)
            {
                break;
            }
            
            double resl = total_f_xirr(payments, days, left);
            
            if ((resc * resl) > 0)
            {
                left = c;
            }
            else
            {
                right = c;
            }
        }
        while (iteration++ < max_iteration);
        
        return c;
    }
        
    public static final void main(String[] args)
    {
        double[] payments = {-6800,1000,2000,4000}; // payments
        double[] days = {1,8,16,25};                // days of payment (as day of year)
        double xirr = Newtons_method(payments, days, 0.1);

        System.out.println("XIRR value is " + xirr);
    }    
}
