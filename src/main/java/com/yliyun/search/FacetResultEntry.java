package com.yliyun.search;/**
 * Created by Administrator on 2016/10/11.
 */

/***
 * create with IDEA
 * User : manbuzhiwu
 * Date : 2016/10/11 - 14:01
 *
 */
public class FacetResultEntry {

    private String term;

    private long count;

    public long getCount()
    {
        return count;
    }

    public void setCount(long count)
    {
        this.count = count;
    }

    public String getTerm()
    {
        return term;
    }

    public void setTerm(String term)
    {
        this.term = term;
    }
}
