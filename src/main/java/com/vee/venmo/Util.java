package com.vee.venmo;

import java.text.NumberFormat;
import java.util.Locale;

public class Util {
	
	public final static NumberFormat CURRENCY_FORMAT = 
		NumberFormat.getCurrencyInstance(Locale.US);
	
	public static boolean validateCard(String card)
    {
            int sum = 0;
            boolean alternate = false;
            if(card.length() > 19)
            	return false;
            for (int i = card.length() - 1; i >= 0; i--)
            {
                    int n = card.charAt(i)-48;
                    if (n < 0 && n > 9)
                    	return false;
                    if (alternate)
                    {
                            n <<= 1;
                            if (n > 9)
                            {
                                    n = (n % 10) + 1;
                            }
                    }
                    sum += n;
                    alternate = !alternate;
            }
            return (sum % 10 == 0);
    }
}
