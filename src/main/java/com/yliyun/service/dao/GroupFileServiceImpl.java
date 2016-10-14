package com.yliyun.service.dao;/**
 * Created by Administrator on 2016/10/14.
 */

import com.yliyun.model.Group_file;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

/***
 * create with IDEA
 * User : manbuzhiwu
 * Date : 2016/10/14 - 17:27
 *
 */

@Service
public class GroupFileServiceImpl implements GroupFileService {


    @Autowired
    @Qualifier("primaryJdbcTemplate")
    private JdbcTemplate fileJdbcTemplate;


    @Override
    public Group_file getGroupFile(Long fileId) {

//        fileJdbcTemplate.qu

        return null;
    }

    @Override
    public Group_file updateGroupFile(String status) {
        return null;
    }
}
