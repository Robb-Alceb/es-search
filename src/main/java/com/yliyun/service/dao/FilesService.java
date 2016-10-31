package com.yliyun.service.dao;/**
 * Created by Administrator on 2016/10/14.
 */

import com.yliyun.model.CommonFile;
import com.yliyun.model.Group_file;
import com.yliyun.model.Group_member;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.IOException;
import java.util.List;

/***
 * create with IDEA
 * User : manbuzhiwu
 * Date : 2016/10/14 - 17:24
 */
public interface FilesService {

    int getNoIndexCount();

    int getIndexCount();

    List<CommonFile> getFilesList(String fileCategory);

    List<CommonFile> getUpFilesList(String fileCategory);

    int[] batchUpdate(List<Long> ids, String tableName);

    void download(String url, String writeName) throws IOException;

    String getDownloadUrl(Long fsId);

    boolean updateFileStatus(CommonFile cf, int result);


    List<Group_member> getGroupList(String userId);

}
