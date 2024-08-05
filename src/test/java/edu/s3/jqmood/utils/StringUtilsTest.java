package edu.s3.jqmood.utils;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.Test;

public class StringUtilsTest {

    @Test
    void shouldReturn1() throws IOException {

        List<String> actual = StringUtils.getMinimumCommonElements(List.of("a"), List.of("a"));
        List<String> expected = List.of("a");

        assertIterableEquals(expected, actual);
    }

    @Test
    void shouldReturn2() throws IOException {

        List<String> actual = StringUtils.getMinimumCommonElements(List.of("a"), List.of("b"));
        List<String> expected = List.of();

        assertIterableEquals(expected, actual);
    }

    @Test
    void shouldReturn3() throws IOException {

        List<String> actual = StringUtils.getMinimumCommonElements(List.of("a", "b", "b"), List.of("a", "b", "c"));
        List<String> expected = List.of("a", "b");

        assertIterableEquals(expected, actual);
    }

    @Test
    void shouldReturn4() throws IOException {
        
        List<String> actual = StringUtils.getMinimumCommonElements(List.of("a", "b", "b", "c", "d"), List.of("a", "b", "a", "d"));
        List<String> expected = List.of("a", "b");

        assertIterableEquals(expected, actual);
    }
}
