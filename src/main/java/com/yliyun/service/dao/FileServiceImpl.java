package com.yliyun.service.dao;

/**
 * Created by Administrator on 2016/10/14.
 */

import com.yliyun.model.CommonFile;
import com.yliyun.model.CommonFileMapper;
import com.yliyun.model.Group_file;
import com.yliyun.util.AppConstants;
import com.yliyun.util.RedisConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.PreparedStatement;
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
    private RestTemplate restTemplate;


    public void download(String url, String fileName) throws IOException {

        byte[] fileByte = restTemplate.getForObject(url, byte[].class);
        Files.write(Paths.get("download/" + fileName), fileByte);

        LOGGER.info("FileServiceImpl > download  success !! ","download/" + fileName);
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
    public List<CommonFile> getFilesList(String tableName) {

        String sql = "select *  from  " + tableName + "  where  search_status = 0  limit  0, 10";

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
