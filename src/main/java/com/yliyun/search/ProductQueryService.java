package com.yliyun.search;

import com.yliyun.model.DocumentData;

import java.util.List;

public interface ProductQueryService
{
    ProductSearchResult searchProducts(SearchCriteria searchCriteria);

    ProductSearchResult baseSearch(String keyword);

    DocumentData getProduct( String productId);
    
    List<AutoSuggestionEntry> getAutoSuggestions( String queryString);

    List<AutoSuggestionEntry> getAutoSuggestionsUsingTermsFacet( String string);

    List<DocumentData> findSimilarProducts( String[] fields, String productId);
}
