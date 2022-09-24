package simulator;

import java.util.ArrayList;
import java.util.Arrays;

public class Helper {
    public static String intToBinary(int num, int wordLength) {
        String binString = Integer.toBinaryString(num);


        int numMissing = wordLength - binString.length();

        if( numMissing == 0) {
            return binString;
        }
        String missingZeros = new String(new char[numMissing]).replace('\0', '0');

        return missingZeros.concat(binString);
    }
    public static int binaryToInt(String str) {
        return Integer.parseInt(str, 2);
    }

    public static String arrToString(int[] array) {
        String binString = Arrays.toString(array)
                .replace("[", "")
                .replace("]", "")
                .replace(",", "")
                .replace(" ", "");
        return binString;
    }

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
    public static int arrToInt(int[] array) {
        return binaryToInt(arrToString(array));
    }

    public static int[] intToBinArray(int num, int wordLength) {
        String binString = intToBinary(num, wordLength);

        int[] result = new int[wordLength];

        for (int i = 0; i < wordLength; i++) {
            result[i] = Integer.parseInt(String.valueOf(binString.charAt(i)));
        }

        return result;
    }
}
