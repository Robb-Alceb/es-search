package com.yliyun.service.dao;/**
 * Created by Administrator on 2016/10/19.
 */

import com.yliyun.index.IndexDataService;
import com.yliyun.model.CommonFile;
import com.yliyun.util.AppConfig;
import com.yliyun.util.AppConstants;
import com.yliyun.util.FileUpdates;
import com.yliyun.util.TikaUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.IOException;

/***
 * create with IDEA
 * User : manbuzhiwu
 * Date : 2016/10/19 - 9:01
 *
 */
public class IndexTask {


    @Autowired
    private IndexDataService indexServices;
    @Autowired
    private FilesService filesService;

    @Autowired
    private AppConfig ac;


    // todo : get date from queue & analysis date

    public void analysisAComFile() throws IOException {

        CommonFile cf = AppConstants.geToQueueData();

        if (cf.getSearch_status() == 0) {

            indexNewDoc(cf);


        } else {

            FileUpdates upds = chk(cf.getSearch_status());

            if (upds.getStatusUp()) {
                // todo update es status;

                return; // 跳出执行
            }

            if (upds.getFileUp()) {
                // todo update es status;
                return;
            }

            if (upds.getReName()) {
                // todo update es status;

            }

        }

    }


    // todo : add to es


    /*****
     *
     * @param cf
     */
    private void updateDocStatus(CommonFile cf){

      //  indexServices.updateData();

    }


    private void indexNewDoc(CommonFile cf) {

        if (indexServices.isDocExists(cf.getFile_id())) {
            // 分析文件
            try {
                getContents(cf);

            } catch (Exception e) {
                e.printStackTrace();
                filesService.updateFileStatus(cf, -1);
            }

            // 插入索引
            if (indexServices.indexData(cf)) {
                // todo : update  db;
                filesService.updateFileStatus(cf, 1);
            } else {
                filesService.updateFileStatus(cf, -1);
            }
        }

    }


    private CommonFile getContents(CommonFile cf) throws Exception {

        File indexFile = new File(ac.getDownloadUrl() + AppConstants.DOWNLOAD_ADDR + cf.getFile_name());

        String txt = TikaUtils.fileToTxt(indexFile);

        cf.setFile_contents(txt);

        return cf;
    }


    /***
     * 检查文件的更新状态
     * @param status
     * @return
     */
    private FileUpdates chk(long status) {

        int rename = 0b0010;
        int fileUp = 0b0100;
        int statusUp = 0b1000;

        FileUpdates fu = new FileUpdates();

        Long sta = new Long(status);

        if ((sta.byteValue() & rename) > 0) {
            fu.setReName(true);
        }

        if ((sta.byteValue() & fileUp) > 0) {
            fu.setFileUp(true);
        }

        if ((sta.byteValue() & statusUp) > 0) {
            fu.setStatusUp(true);
        }

        return fu;
    }

}
