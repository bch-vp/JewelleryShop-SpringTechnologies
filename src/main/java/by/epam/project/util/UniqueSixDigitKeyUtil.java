package by.epam.project.util;

import java.util.Random;

public class UniqueSixDigitKeyUtil {
    private static final int DIFF_RANGE = 900_000;
    private static final int MIN_RANGE = 100_000;

    private UniqueSixDigitKeyUtil() {
    }

    public static int generate(){
        return MIN_RANGE + new Random().nextInt(DIFF_RANGE);
    }
}
