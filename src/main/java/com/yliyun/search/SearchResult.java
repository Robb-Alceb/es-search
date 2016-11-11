package com.yliyun.search;/**
 * Created by Administrator on 2016/10/31.
 */

import com.yliyun.model.CommonFile;

import java.util.List;
import java.util.Map;

/***
 * create with IDEA
 * User : manbuzhiwu
 * Date : 2016/10/31 - 14:13
 *
 */
public class SearchResult {

    /***
     * 建议结果
     */
    private Map<String, Long> types;

    /***
     * 文件列表结果
     */
    private List<CommonFile> fileList;


    public SearchResult() {
    }

    public Map<String, Long> getTypes() {
        return types;
    }

    public void setTypes(Map<String, Long> types) {
        this.types = types;
    }

    public List<CommonFile> getFileList() {
        return fileList;
    }

    public void setFileList(List<CommonFile> fileList) {
        this.fileList = fileList;
    }
}
