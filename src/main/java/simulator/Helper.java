package simulator;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * A series of helper functions, mostly type conversions, used throughout the computer
 */
public class Helper {
    /**
     * Converts a given positive integer into a binary string which represents the same value
     * @param num the number to convert
     * @param wordLength the number of bits in the output binary string
     * @return the binary version of num
     */
    public static String intToBinary(int num, int wordLength) {
        String binString = Integer.toBinaryString(num);


        int numMissing = wordLength - binString.length();

        if( numMissing == 0) {
            return binString;
        }
        String missingZeros = new String(new char[numMissing]).replace('\0', '0');

        return missingZeros.concat(binString);
    }

    /**
     * Converts a binary string into its corresponding decimal representation
     * @param str the binary number to be converted
     * @return the binary number in decimal as an int variable
     */
    public static int binaryToInt(String str) {
        return Integer.parseInt(str, 2);
    }

    /**
     * Converts an array of integers to a String representing the array
     * Used only on int arrays which represetn binary numbers (thus they only contain 1s and 0s)
     * @param array an array representing a binary number
     * @return the binary number as a string
     */
    public static String arrToString(int[] array) {
        String binString = Arrays.toString(array)
                .replace("[", "")
                .replace("]", "")
                .replace(",", "")
                .replace(" ", "");
        return binString;
    }

    /**
     * Int array to string, then splits string into groups of 4 for readability
     * @param array represents a binary number
     * @return a version of the string which is appropriately readable and can be shown on the GUI
     */
    public static String arrToDisplayString(int[] array) {
        String str = arrToString(array);
        String disp = "";
        for (int i = 0; i < array.length; i+=4) {
            disp += str.substring(i, i+4);
            if (i+4 < array.length) {
                disp += " ";
            }
        }
        return disp;
    }

    /**
     * Converts an in array representing a binary number to an int primitive
     * @param array represents binary number
     * @return base 10 integer
     */
    public static int arrToInt(int[] array) {
        return binaryToInt(arrToString(array));
    }

    /**
     * Decimal integer to array of 1 and 0
     * @param num decminal integer
     * @param wordLength number of bits in output
     * @return type int[]
     */
    public static int[] intToBinArray(int num, int wordLength) {
        String binString = intToBinary(num, wordLength);

        int[] result = new int[wordLength];

        for (int i = 0; i < wordLength; i++) {
            result[i] = Integer.parseInt(String.valueOf(binString.charAt(i)));
        }

        return result;
    }
}
