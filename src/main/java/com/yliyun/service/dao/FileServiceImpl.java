package com.yliyun.service.dao;

/**
 * Created by Administrator on 2016/10/14.
 */

import com.yliyun.model.*;
import com.yliyun.util.AppConfig;
import com.yliyun.util.AppConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

/***
 * create with IDEA
 * User : manbuzhiwu
 * Date : 2016/10/14 - 17:27
 *
 */

@Service
public class FileServiceImpl implements FilesService {


    private static final Logger LOGGER = LoggerFactory.getLogger(FileServiceImpl.class);


    @Autowired
    @Qualifier("primaryJdbcTemplate")
    private JdbcTemplate fileJdbcTemplate;


    @Autowired
    private AppConfig ac;


    @Autowired
    private RestTemplate restTemplate;


    public void download(String url, String fileName)  {

        System.out.println("----------download-------, " + url);

        byte[] fileByte = restTemplate.getForObject("http://" + url, byte[].class);
        try {
            Files.write(Paths.get(AppConstants.DOWNLOAD_ADDR + fileName), fileByte);
        } catch (IOException e) {
            e.printStackTrace();
        }

        LOGGER.info("FileServiceImpl > download  success !! ", "download/" + fileName);
    }

    @Override
    public String getDownloadUrl(Long fsId) {

        String sql = "SELECT  * from fs_file WHERE fs_file_id = ? ";

        LOGGER.info("FileServiceImpl > getDownloadUrl   ", "sql: " + sql);


        Fs_file fsList = fileJdbcTemplate.queryForObject(sql, new Object[]{fsId}, new FsFileMapper());

        return fsList.getFile_name();
    }

    @Override
    public boolean updateFileStatus(CommonFile cf, int result) {
        String tableName = "personal_file";

        if (cf.getFile_category() == "group") {
            tableName = "group_file";
        } else if (cf.getFile_category() == "public") {
            tableName = "public_file";
        }

        String sql = "  update  " + tableName + " set   search_status = " + result + " WHERE  file_id = " + cf.getFile_id();

        int re = fileJdbcTemplate.update(sql);

        if (re > 0) {
            return true;
        }
        return false;
    }

    @Override
    public List<Group_member> getGroupList(String userId) {

        String sql = "SELECT  * from group_member WHERE user_id =  " + userId;

        LOGGER.info("FileServiceImpl > getGroupList   ", "sql: " + sql);

        return fileJdbcTemplate.query(sql, new GroupMemberMapper());

    }


    @Override
    public int getNoIndexCount() {

        // Constants.tables
        int indexCount = 0;

        for (int i = 0; i < AppConstants.TABLES.length; i++) {

            String sql = "select count(*) from  " + AppConstants.TABLES[i]

                    + "  where  search_status = ?";

            indexCount += fileJdbcTemplate.queryForObject(sql, new Object[]{0}, Integer.class);
        }

        LOGGER.info("FileServiceImpl > getNoIndexCount  result : " + indexCount);

        return indexCount;
    }

    @Override
    public int getIndexCount() {

        int indexCount = 0;

        for (int i = 0; i < AppConstants.TABLES.length; i++) {

            String sql = "select count(*) from  " + AppConstants.TABLES[i] + "  where  search_status = ?";

            indexCount += fileJdbcTemplate.queryForObject(sql, new Object[]{1}, Integer.class);
        }

        LOGGER.info("FileServiceImpl > getIndexCount  result : " + indexCount);

        return indexCount;
    }


    @Override
    public List<CommonFile> getUpFilesList(String tableName) {

        String sql = "select *  from  " + tableName + "  where search_status > 1 limit  0, "+ac.getDbPage();

        LOGGER.info("FileServiceImpl > getFilesList  tableName & sql : " + tableName + " & sql : " + sql);

        return fileJdbcTemplate.query(sql, new CommonFileMapper(tableName));
    }

    @Override
    public List<CommonFile> getFilesList(String tableName) {

        String sql = "select *  from  " + tableName + "  where  search_status = 0 limit  0,  "+ac.getDbPage();

        LOGGER.info("FileServiceImpl > getFilesList  tableName & sql : " + tableName + " & sql : " + sql);

        return fileJdbcTemplate.query(sql, new CommonFileMapper(tableName));
    }

    @Override
    public int[] batchUpdate(List<Long> ids, String tableName) {

        int[] updateCounts = fileJdbcTemplate.batchUpdate("update  " + tableName + "  set  search_status = 1  where file_id=?",
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                        preparedStatement.setLong(1, ids.get(i));
                    }

                    @Override
                    public int getBatchSize() {
                        return ids.size();
                    }
                });

        return updateCounts;
    }
}
