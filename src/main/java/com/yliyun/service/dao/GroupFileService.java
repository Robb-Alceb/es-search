package com.yliyun.service.dao;/**
 * Created by Administrator on 2016/10/14.
 */

import com.yliyun.model.Group_file;
import org.springframework.jdbc.core.JdbcTemplate;

/***
 * create with IDEA
 * User : manbuzhiwu
 * Date : 2016/10/14 - 17:24
 *
 */
public interface GroupFileService {


    Group_file getGroupFile(Long fileId);

    Group_file  updateGroupFile (String status);


}
