package com.yliyun.search;/**
 * Created by Administrator on 2016/10/11.
 */

import java.util.ArrayList;
import java.util.List;

/***
 * create with IDEA
 * User : manbuzhiwu
 * Date : 2016/10/11 - 14:01
 *
 */
public class FacetResult {
    private String code;

    private List<FacetResultEntry> facetResultEntries = new ArrayList<FacetResultEntry>();

    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    public List<FacetResultEntry> getFacetResultEntries()
    {
        return facetResultEntries;
    }

    public void addFacetResultEntry(FacetResultEntry facetResultEntry)
    {
        facetResultEntries.add(facetResultEntry);
    }

}
