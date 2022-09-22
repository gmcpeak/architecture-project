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
    public static int arrToInt(int[] array) {
        String binString = Arrays.toString(array)
                .replace("[", "")
                .replace("]", "")
                .replace(",", "")
                .replace(" ", "");
        return binaryToInt(binString);
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
