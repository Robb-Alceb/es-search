package com.yliyun.search;

import com.yliyun.model.DocumentData;
import com.yliyun.model.EsIndexConfig;

import java.util.List;

public interface ProductQueryService
{
    ProductSearchResult searchProducts(EsIndexConfig config,SearchCriteria searchCriteria);

    ProductSearchResult baseSearch(EsIndexConfig config,String keyword);

    DocumentData getProduct(EsIndexConfig config, String productId);
    
    List<AutoSuggestionEntry> getAutoSuggestions(EsIndexConfig config, String queryString);

    List<AutoSuggestionEntry> getAutoSuggestionsUsingTermsFacet(EsIndexConfig config, String string);

    List<DocumentData> findSimilarProducts(EsIndexConfig config, String[] fields, String productId);
}
