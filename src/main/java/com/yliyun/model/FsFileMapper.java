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
public class FsFileMapper implements RowMapper<Fs_file> {

    @Override
    public Fs_file mapRow(ResultSet rs, int i) throws SQLException {
        Fs_file gf = new Fs_file();

        gf.setFs_file_id(rs.getLong("fs_file_id"));

        gf.setCreate_time(rs.getString("create_time"));
        gf.setFile_name(rs.getString("file_name"));
        gf.setFile_size(rs.getLong("file_size"));
        gf.setFile_crc32(rs.getLong("file_crc32"));
        gf.setFile_md5(rs.getString("file_md5"));
        gf.setFile_thumb(rs.getString("file_thumb"));
        gf.setFile_view(rs.getString("file_view"));
        gf.setImg_status(rs.getLong("img_status"));
        gf.setView_size(rs.getLong("view_size"));
        gf.setConv_status(rs.getLong("conv_status"));

        return gf;
    }
}
