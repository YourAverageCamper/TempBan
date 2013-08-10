
package me.zeus.ExodusBans.Utils;


import java.util.concurrent.TimeUnit;



public class MathHelper
{
    
    
    
    @SuppressWarnings("incomplete-switch")
    public static long calculateMillis(TimeUnit unit, int amount)
    {
        long millis = 0;
        switch (unit)
        {
            case SECONDS:
                millis = 1000 * amount;
                break;
            case MINUTES:
                millis = 1000 * 60 * amount;
                break;
            case HOURS:
                millis = 1000 * 60 * 60 * amount;
                break;
            case DAYS:
                millis = 1000 * 60 * 60 * 24 * amount;
        }
        return millis;
    }
    
    
    
    public static int getSecondsRemaining(long time)
    {
        int a = (int) ((System.currentTimeMillis() / 1000) - (time / 1000));
        return (int) (a > 0 ? a : a * -1);
    }
    
    
    
    public static int getMinutesRemaining(long time)
    {
        return getSecondsRemaining(time) / 60;
    }
    
    
    
    public static int getHoursRemaining(long time)
    {
        return getMinutesRemaining(time) / 60;
    }
    
    
    
    public static int getDaysRemaining(long time)
    {
        return getHoursRemaining(time) / 24;
    }
    
    
    
}
