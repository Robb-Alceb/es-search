package com.yliyun.model;

public class Group_member {
  private Long user_id;
  private Long group_id;
  private String member_type;
  private Long member_status;
  private Long update_user_id;
  private String update_user_name;
  private String update_time;

  public Long getUser_id() {
    return user_id;
  }

  public void setUser_id(Long user_id) {
    this.user_id = user_id;
  }

  public Long getGroup_id() {
    return group_id;
  }

  public void setGroup_id(Long group_id) {
    this.group_id = group_id;
  }

  public String getMember_type() {
    return member_type;
  }

  public void setMember_type(String member_type) {
    this.member_type = member_type;
  }

  public Long getMember_status() {
    return member_status;
  }

  public void setMember_status(Long member_status) {
    this.member_status = member_status;
  }

  public Long getUpdate_user_id() {
    return update_user_id;
  }

  public void setUpdate_user_id(Long update_user_id) {
    this.update_user_id = update_user_id;
  }

  public String getUpdate_user_name() {
    return update_user_name;
  }

  public void setUpdate_user_name(String update_user_name) {
    this.update_user_name = update_user_name;
  }

  public String getUpdate_time() {
    return update_time;
  }

  public void setUpdate_time(String update_time) {
    this.update_time = update_time;
  }
}
