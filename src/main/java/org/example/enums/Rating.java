package org.example.enums;




import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum Rating {
    G("G"),
    PG("PG"),
    PG13("PG-13"),
    R("R"),
    NC17("NC-17");

    private static final Map<String, Rating> LOOKUP = Arrays.stream(values())
            .collect(Collectors.toMap(Rating::getRating, Function.identity()));

    private final String rating;

    Rating(final String rating) {
        this.rating = rating;
    }

    public String getRating() {
        return rating;
    }

    public static Rating fromString(final String rating) {
        return LOOKUP.get(rating);
    }
}