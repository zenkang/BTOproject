package Utils;

import Project.Project;

import java.util.function.Predicate;

public class ProjectFilterContext {
    public Predicate<Project> combinedFilter = null;
    public Predicate<Project> neighbourhoodFilter = null;
    public Predicate<Project> flatTypeFilter = null;
    public String neighbourhood = null;
    public String flatType = null;
    public Predicate<Project> visibilityFilter = null;
    public String visibility = null;

    public void reset() {
        combinedFilter = null;
        neighbourhoodFilter = null;
        flatTypeFilter = null;
        neighbourhood = null;
        flatType = null;
    }

    public void updateFilter() {
        combinedFilter = combineFilters(neighbourhoodFilter, flatTypeFilter);
    }

    @SafeVarargs
    public static <T> Predicate<T> combineFilters(Predicate<T>... predicates) {
        if (predicates == null) return x -> true;
        return java.util.Arrays.stream(predicates)
                .filter(java.util.Objects::nonNull)
                .reduce(x -> true, Predicate::and);
    }


    public void updateFilterWithVisibility() {
        combinedFilter = combineFilters(neighbourhoodFilter, flatTypeFilter, visibilityFilter);
    }


}
