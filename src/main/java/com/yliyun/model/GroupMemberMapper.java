package com.yliyun.model;/**
 * Created by Administrator on 2016/10/18.
 */


import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/***
 * create with IDEA
 * User : manbuzhiwu
 * Date : 2016/10/18 - 11:57
 *
 */
public class GroupMemberMapper implements RowMapper<Group_member> {

    @Override
    public Group_member mapRow(ResultSet rs, int i) throws SQLException {

        Group_member gf = new Group_member();

        gf.setGroup_id(rs.getLong("group_id"));
        gf.setUser_id(rs.getLong("user_id"));
        gf.setMember_type(rs.getString("member_type"));
        gf.setMember_status(rs.getLong("member_status"));

        gf.setUpdate_user_name(rs.getString("update_user_name"));
        gf.setUpdate_time(rs.getString("update_time"));
        gf.setUpdate_user_id(rs.getLong("update_user_id"));

        return gf;
    }
}
