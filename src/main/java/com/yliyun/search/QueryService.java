package com.yliyun.search;

import com.yliyun.model.CommonFile;

import java.util.List;

public interface QueryService
{


    List<String> baseSearch(String keyword,String userId);

    CommonFile getProduct(Long fileId) throws Exception;
    
    List<AutoSuggestionEntry> getAutoSuggestions( String queryString);



//    List<DocumentData> findSimilarProducts( String[] fields, String productId);
}
