package com.yliyun.search;/**
 * Created by Administrator on 2016/10/11.
 */

/***
 * create with IDEA
 * User : manbuzhiwu
 * Date : 2016/10/11 - 15:26
 *
 */
public class AutoSuggestionEntry {

    private String term;

    private int count;

    public AutoSuggestionEntry(String term, int count)
    {
        this.term = term;
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

    public int getCount()
    {
        return count;
    }

    public void setCount(int count)
    {
        this.count = count;
    }

    @Override
    public String toString() {
        return "AutoSuggestionEntry{" +
                "term='" + term + '\'' +
                ", count=" + count +
                '}';
    }
}
