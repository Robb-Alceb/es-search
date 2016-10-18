package com.yliyun.model;/**
 * Created by Administrator on 2016/10/18.
 */


import com.yliyun.util.AppConstants;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/***
 * create with IDEA
 * User : manbuzhiwu
 * Date : 2016/10/18 - 11:57
 *
 */
public class CommonFileMapper implements RowMapper<CommonFile> {

    private String tableName;

    public CommonFileMapper(String tableName) {
        this.tableName = tableName;
    }

    @Override
    public CommonFile mapRow(ResultSet rs, int i) throws SQLException {
        CommonFile gf = new CommonFile();
        gf.setFile_id(rs.getLong("file_id"));
        gf.setCreate_time(rs.getString("create_time"));
        gf.setCreater_name(rs.getString("creater_name"));
        gf.setCreater_id(rs.getLong("creater_id"));
        gf.setDel_status(rs.getLong("del_status"));
        gf.setDoc_type(rs.getLong("doc_type"));
        gf.setFile_action(rs.getString("file_action"));
        gf.setFile_name(rs.getString("file_name"));
        gf.setFile_path(rs.getString("file_path"));
        gf.setFile_size(rs.getLong("file_size"));
        gf.setFile_version(rs.getLong("file_version"));
        gf.setFolder(rs.getLong("folder"));
        gf.setFs_file_id(rs.getLong("fs_file_id"));

        gf.setLayer(rs.getLong("layer"));
        gf.setParent_id(rs.getLong("parent_id"));
        gf.setSearch_status(rs.getLong("search_status"));
        gf.setThumb(rs.getString("thumb"));
        gf.setTrash_id(rs.getLong("trash_id"));
        gf.setUpdate_time(rs.getString("update_time"));
        gf.setUpdate_user_name(rs.getString("update_user_name"));
        gf.setUpdate_user_id(rs.getLong("update_user_id"));
        gf.setParent_ids(rs.getString("parent_ids"));

        if (this.tableName == AppConstants.TABLE_PERSONAL_FILE) {

            gf.setUser_id(rs.getLong("user_id"));

            gf.setFile_category("personal");

        } else if (this.tableName == AppConstants.TABLE_GROUP_FILE) {

            gf.setGroup_id(rs.getLong("group_id"));

            gf.setFile_category("group");

        } else {
            gf.setFile_category("public");

            gf.setDept_id(rs.getLong("dept_id"));

            gf.setTop_dept_folder(rs.getLong("top_dept_folder"));
        }
        return gf;
    }
}
