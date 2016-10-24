package com.yliyun.util;/**
 * Created by Administrator on 2016/10/19.
 */

/***
 * create with IDEA
 * User : manbuzhiwu
 * Date : 2016/10/19 - 17:33
 * 记录文件有哪些更新
 */
public class FileUpdates {

        private  Boolean reName = false;  // 0b0010
        private  Boolean fileUp = false;  // 0b0100
        private  Boolean statusUp = false; // 0b1000


        public Boolean getReName() {
            return reName;
        }

        public void setReName(Boolean reName) {
            this.reName = reName;
        }

        public Boolean getFileUp() {
            return fileUp;
        }

        public void setFileUp(Boolean fileUp) {
            this.fileUp = fileUp;
        }

        public Boolean getStatusUp() {
            return statusUp;
        }

        public void setStatusUp(Boolean statusUp) {
            this.statusUp = statusUp;
        }
}
