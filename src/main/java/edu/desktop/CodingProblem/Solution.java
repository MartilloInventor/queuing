package edu.desktop.CodingProblem;

import java.util.List;
import java.util.ArrayList;
import java.lang.String;

// CLASS BEGINS, THIS CLASS IS REQUIRED
public class Solution {
    private static int[] Dispensers = new int[3];
    private static int[] WaitTimes = null;
    private static int[] BlockTimes = new int[3];

    private static boolean CarNeedsToFuel(int[] Cars) {
        for (int c : Cars) {
            if (c > 0) {
                return true;
            }
        }
        return false;
    }

    private static boolean EnoughGas(int[] Cars) {
        boolean value = false;
        for (int c : Cars) {
            value = false;
            for (int d : Dispensers) {
                value = (c <= d);
                if(value)
                    break;
            }
            if(!value)
                break;
        }
        return value;
    }

    private static void DecreaseBlockTimes() {
        for (int i = 0; i < BlockTimes.length; ++i) {
            if (BlockTimes[i] > 0) {
                --BlockTimes[i];
            }
        }
    }

    private static int solution(int[] Cars) {
        int wait_time = 0;
        while (CarNeedsToFuel( Cars )) {
            if (!EnoughGas( Cars )) {
                return -1;
            }
            for (int c = 0; c < Cars.length; ++c) {
                if (Cars[c] > 0) {
                    for (int d = 0; d < Dispensers.length; ++d) {
                        if ((Cars[c] <= Dispensers[d]) && (BlockTimes[d] == 0)) {
                            BlockTimes[d] = Cars[c];
                            Dispensers[d] -= Cars[c];
                            Cars[c] = 0;
                            System.err.printf("Car %d fills up at dispenser %d and blocks for %d sec.\n", c, d, BlockTimes[d]);
                            break;
                        }
                    }
                    if (Cars[c] != 0) {
                        ++WaitTimes[c];
                    }
                }
            }
            DecreaseBlockTimes();
        }
        for(int time: WaitTimes) {
            if(time > wait_time) {
                wait_time = time;
            }
        }
        return wait_time;
    }


    public static void main(String args[]) {

        List<Integer> initial_configuration = new ArrayList<>();
        int[] initial_ints;
        int initial_length;
        int maximum;
        int[] Cars;

        //<String> list_of_substring_lists = new HashSet<String>();

        if (args.length == 0) {
            System.err.print( "Program must have args.\n" );
            System.exit( -1 );
        }

        if (args.length < 4) {
            System.err.print( "Not enough arguments.\n" );
        }

        for (String tmp : args) {
            initial_configuration.add( Integer.parseInt(tmp) );
        }
        initial_length = initial_configuration.size() - 3;
        WaitTimes = new int[initial_length];
        Cars = new int[initial_length];

        for (int index = 0; index < initial_length; ++index) {
            Cars[index] = initial_configuration.get( index );
            WaitTimes[index] = 0;
        }
        for (int index = initial_length; index < (initial_length + 3); ++index) {
            Dispensers[index - initial_length] = initial_configuration.get( index );
            BlockTimes[index - initial_length] = 0;
        }
        maximum = solution( Cars );
        if(maximum == -1) {
            System.out.println( "Maximum wait time is infinite.");
        } else {
            System.out.println( "Maximum wait time is " + maximum );
        }
    }
}

// METHOD SIGNATURE BEGINS, THIS METHOD IS REQUIRED

