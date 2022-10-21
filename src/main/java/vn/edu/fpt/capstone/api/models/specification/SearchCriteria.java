package vn.edu.fpt.capstone.api.models.specification;

import vn.edu.fpt.capstone.api.constants.SearchOperation;

public record SearchCriteria(String key, Object value, SearchOperation searchOperation) {

    public static SearchCriteria of(String key, Object value, SearchOperation searchOperation) {
        return new SearchCriteria(key, value, searchOperation);
    }

}
