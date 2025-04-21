package Utils;

import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class PredicateUtils {
    /**
     * Combines a list of predicates into a single predicate using a logical AND.
     *
     * @param <T>        The type of object the predicates apply to.
     * @param predicates A list of predicates.
     * @return A single predicate that represents the logical AND of all predicates.
     */

    public static <T> Predicate<T> combineFilters(Predicate<T>... predicates) {
        if (predicates == null) {
            return x -> true;
        }
        return Stream.of(predicates)
                .filter(Objects::nonNull)
                .reduce(x -> true, Predicate::and);
    }
}
