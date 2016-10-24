package com.yliyun.model;

public class Fs_file {

  private Long fs_file_id;
  private String file_name;
  private Long file_size;
  private Long file_crc32;
  private String file_md5;
  private String file_thumb;
  private Long thumb_size;
  private String file_view;
  private Long view_size;
  private Long img_status;
  private Long conv_status;
  private Long conv_retry;
  private String create_time;



  public Long getFs_file_id() {
    return fs_file_id;
  }

  public void setFs_file_id(Long fs_file_id) {
    this.fs_file_id = fs_file_id;
  }

  public String getFile_name() {
    return file_name;
  }

  public void setFile_name(String file_name) {
    this.file_name = file_name;
  }

  public Long getFile_size() {
    return file_size;
  }

  public void setFile_size(Long file_size) {
    this.file_size = file_size;
  }

  public Long getFile_crc32() {
    return file_crc32;
  }

  public void setFile_crc32(Long file_crc32) {
    this.file_crc32 = file_crc32;
  }

  public String getFile_md5() {
    return file_md5;
  }

  public void setFile_md5(String file_md5) {
    this.file_md5 = file_md5;
  }

  public String getFile_thumb() {
    return file_thumb;
  }

  public void setFile_thumb(String file_thumb) {
    this.file_thumb = file_thumb;
  }

  public Long getThumb_size() {
    return thumb_size;
  }

  public void setThumb_size(Long thumb_size) {
    this.thumb_size = thumb_size;
  }

  public String getFile_view() {
    return file_view;
  }

  public void setFile_view(String file_view) {
    this.file_view = file_view;
  }

  public Long getView_size() {
    return view_size;
  }

  public void setView_size(Long view_size) {
    this.view_size = view_size;
  }

  public Long getImg_status() {
    return img_status;
  }

  public void setImg_status(Long img_status) {
    this.img_status = img_status;
  }

  public Long getConv_status() {
    return conv_status;
  }

  public void setConv_status(Long conv_status) {
    this.conv_status = conv_status;
  }

  public Long getConv_retry() {
    return conv_retry;
  }

  public void setConv_retry(Long conv_retry) {
    this.conv_retry = conv_retry;
  }

  public String getCreate_time() {
    return create_time;
  }

  public void setCreate_time(String create_time) {
    this.create_time = create_time;
  }
}
