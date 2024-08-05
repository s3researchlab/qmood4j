package edu.s3.jqmood.utils;

import java.util.ArrayList;
import java.util.List;

public class StringUtils {

    /**
     * Private Constructor will prevent the instantiation of this class directly
     */
    private StringUtils() {
        throw new UnsupportedOperationException("This class cannot be instantiated");
    }

    public static List<String> getMinimumCommonElements(List<String> listA, List<String> listB) {

        List<String> output = new ArrayList<>();

        int max = Math.min(listA.size(), listB.size());

        for (int i = 0; i < max; i++) {

            if (listA.get(i).contentEquals(listB.get(i))) {
                output.add(listA.get(i));
            }
        }

        return output;
    }
}
