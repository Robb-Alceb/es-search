package com.yliyun.search;

import com.yliyun.model.DocumentData;

import java.util.ArrayList;
import java.util.List;

/***
 * create with IDEA
 * User : manbuzhiwu
 * Date : 2016/10/11 - 13:59
 *
 */
public class ProductSearchResult {

    private long totalCount;

    private List<DocumentData> products = new ArrayList<DocumentData>();

    private List<FacetResult> facets = new ArrayList<FacetResult>();

    public long getTotalCount()
    {
        return totalCount;
    }

    public void setTotalCount(long totalCount)
    {
        this.totalCount = totalCount;
    }

    public List<DocumentData> getProducts()
    {
        return products;
    }

    public void setProducts(List<DocumentData> products)
    {
        this.products = products;
    }

    public void addProduct(DocumentData product)
    {
        products.add(product);
    }

    public List<FacetResult> getFacets()
    {
        return facets;
    }

    public void addFacet(FacetResult facet)
    {
        facets.add(facet);
    }

}
