package tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class AssertTests {

    @Test
    public void AssertJString() {
        String actual = "Бармен";
        String expected = "Бар-";
        assertThat(actual).as("Проверка что %s начинается на %s", actual, expected).startsWith(expected);
    }

    @Test
    public void AssertJListOfString() {
        List<String> actual = new ArrayList<>();
        actual.add("Бармен");
        actual.add("Официант");
        actual.add("Управляющий");
        String expected = "Бар";
        assertThat(actual).as("Проверка элементов списка %s на то что каждый начинается с %s", actual, expected)
                .allMatch(a -> a.startsWith(expected));
    }

    @Test
    public void AssertJListOfStringSecond() {
        List<String> actual = List.of("Бармен",
                "Официант",
                "Управляющий");
        String expected = "Бармен";
        assertThat(actual).as("Проверка элементов списка %s на то что каждый начинается с %s", actual, expected)
                .containsExactlyInAnyOrder(expected);
    }

    @Test
    public void AssertString() {
        String actual = "Бармен";
        String expected = "Бар-";
        Assertions.assertTrue(actual.startsWith(expected));
    }
}
