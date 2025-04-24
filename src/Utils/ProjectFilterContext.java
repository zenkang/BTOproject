package Utils;

import Project.Project;

import java.util.function.Predicate;

/**
 * Encapsulates the filtering logic for Project listings in the system.
 * Maintains state for various project filters such as neighbourhood, flat type, and visibility,
 * and provides methods to update and combine these filters.
 */
public class ProjectFilterContext {

    /** Combined filter composed of the currently active sub-filters. */
    public Predicate<Project> combinedFilter = null;

    /** Filter based on project neighbourhood. */
    public Predicate<Project> neighbourhoodFilter = null;

    /** Filter based on flat type (e.g., 2-room, 3-room). */
    public Predicate<Project> flatTypeFilter = null;

    /** Selected neighbourhood name. */
    public String neighbourhood = null;

    /** Selected flat type. */
    public String flatType = null;

    /** Filter based on visibility (e.g., open/closed). */
    public Predicate<Project> visibilityFilter = null;

    /** Selected visibility state. */
    public String visibility = null;

    /**
     * Resets all filters and their associated state.
     */
    public void reset() {
        combinedFilter = null;
        neighbourhoodFilter = null;
        flatTypeFilter = null;
        neighbourhood = null;
        flatType = null;
    }

    /**
     * Updates the combined filter based on current neighbourhood and flat type filters.
     */
    public void updateFilter() {
        combinedFilter = combineFilters(neighbourhoodFilter, flatTypeFilter);
    }

    /**
     * Combines multiple filters into a single predicate using logical AND.
     *
     * @param predicates a varargs array of predicates to combine
     * @return a single predicate representing the conjunction of all provided filters
     * @param <T> the type of object the predicate operates on
     */
    @SafeVarargs
    public static <T> Predicate<T> combineFilters(Predicate<T>... predicates) {
        if (predicates == null) return x -> true;
        return java.util.Arrays.stream(predicates)
                .filter(java.util.Objects::nonNull)
                .reduce(x -> true, Predicate::and);
    }

    /**
     * Updates the combined filter using neighbourhood, flat type, and visibility filters.
     */
    public void updateFilterWithVisibility() {
        combinedFilter = combineFilters(neighbourhoodFilter, flatTypeFilter, visibilityFilter);
    }


}
