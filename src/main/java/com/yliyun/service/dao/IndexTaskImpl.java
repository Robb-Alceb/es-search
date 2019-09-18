package com.yliyun.service.dao;

/**
 * Created by Administrator on 2016/10/19.
 */

import com.yliyun.index.IndexDataService;
import com.yliyun.client.SearchDocumentFieldName;
import com.yliyun.model.CommonFile;
import com.yliyun.util.AppConfig;
import com.yliyun.util.AppConstants;
import com.yliyun.util.FileUpdates;
import com.yliyun.util.TikaUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/***
 * create with IDEA
 * User : manbuzhiwu
 * Date : 2016/10/19 - 9:01
 *
 */

@Service
public class IndexTaskImpl implements IndexTask {


    @Autowired
    private IndexDataService indexServices;

    @Autowired
    private FilesService filesService;

    @Autowired
    private AppConfig ac;

    private static final Logger log = LoggerFactory.getLogger(IndexTaskImpl.class);


    public void analysisAComFile() throws IOException, InterruptedException {


        CommonFile cf = AppConstants.getToQueueData();

      //  System.out.println("the queue size ***  : " + AppConstants.CACHE_STORE.size());

        if (cf.getFile_id() == null) return;

        log.info(">>>>>>>>>>>>>>>>>>  now is analysis  file : ", cf.toString());

        if (cf.getSearch_status() == 0) {
            log.info(">>>>>>>>>>>>>>>>>>  is  new file : ", cf.getFile_name());
            indexNewDoc(cf);

        } else {

            boolean is = indexServices.isDocExists(cf.getFile_id());
            if(!is){
                indexNewDoc(cf);
                return ;
            }

            FileUpdates upds = chk(cf.getSearch_status());

            if (upds.getStatusUp()) {

                log.info(">>>>>>>-----StatusUp---->>>>>>>>>>>  is  update file : ");

                if (cf.getDel_status() == 0) {
                    updateDocStatus(SearchDocumentFieldName.FILE_STATUS.getFieldName(), 0, cf.getFs_file_id());
                } else {
                    updateDocStatus(SearchDocumentFieldName.FILE_STATUS.getFieldName(), 1, cf.getFs_file_id());
                }
                filesService.updateFileStatus(cf, 1);
                return; // 跳出执行
            }
            //  更新文件内容
            if (upds.getFileUp()) {
                log.info(">>>>>>>-----Content-Up---->>>>>>>>>>>  is  update file : ");
                try {
                    getContents(cf);
                    updateDocStatus(SearchDocumentFieldName.FILE_CONTENTS.getFieldName(), cf.getFile_contents(), cf.getFs_file_id());
                    filesService.updateFileStatus(cf, 1);
                } catch (Exception e) {
                    e.printStackTrace();
                    filesService.updateFileStatus(cf, AppConstants.INDEX_FAIL);
                }

                return;
            }
            // 更新文件名
            if (upds.getReName()) {
                log.info(">>>>>>>-----reName-Up---->>>>>>>>>>>  is  update file : ");
                updateDocStatus(SearchDocumentFieldName.FILE_TITLE.getFieldName(), cf.getFile_name(), cf.getFs_file_id());
                filesService.updateFileStatus(cf, 1);
                return;
            }

        }

    }


    /***
     *  更新es ，根据文件的id 更新es 的文件状态，文件内容等等；
     * @param fieldName
     * @param Value
     * @param fileId
     */
    private void updateDocStatus(String fieldName, Object Value, Long fileId) {

        Map<String, Object> map = new HashMap<String, Object>();

        map.put(fieldName, Value);

        indexServices.updateData(map, fileId);
    }

    /***
     *  新文档索引
     * @param cf
     */
    private void indexNewDoc(CommonFile cf) {

        System.out.println("<------------------indexNewDoc----------------------------->" + cf.getFile_id());

        boolean is = indexServices.isDocExists(cf.getFile_id());

        System.out.println(is);
        if (!is) {
            // 分析文件
            //0其他  1 图片，2 文档，3 音乐， 4 视频， 5 应用， 6 压缩包
            if (cf.getFolder() == 0 && cf.getDoc_type() == 2) {

                try {
                    getContents(cf);
                } catch (Exception e) {

                    log.error("===================index fail==============,",cf.getFile_id());
                    e.printStackTrace();
                    // 索引失败
                    filesService.updateFileStatus(cf, AppConstants.INDEX_FAIL);
                }
            }
            // 插入索引
            if (indexServices.indexData(cf)) {
                // 索引成功
                filesService.updateFileStatus(cf, 1);
            } else {
                // 索引失败
                filesService.updateFileStatus(cf, AppConstants.INDEX_FAIL);
            }
        }else{

           /// System.out.println("***************************,update db!");

            log.info("this doc is exits!!!    id is :"+ cf.getFile_id());

            filesService.updateFileStatus(cf, 1);
        }


    }


    /***
     * 提取文档内容
     * @param cf
     * @return
     * @throws Exception
     */
    private CommonFile getContents(CommonFile cf) throws Exception {

        String storeAddr = AppConstants.DOWNLOAD_ADDR + cf.getFile_name();

        log.warn("get file content start --------------------: ", storeAddr);

        String storePath = filesService.getDownloadUrl(cf.getFs_file_id());

        String url = ac.getDownloadUrl() + storePath;

        filesService.download(url, cf.getFile_name());

        File indexFile = new File(storeAddr);

        log.warn("get file content start --------------------: ", indexFile.getName());

        String ext = AppConstants.getFileExt(cf.getFile_name());

        String txt = null;

        if(ext.equals("txt")||ext.equals("**")||ext.equals("md")){
            txt = TikaUtils.txtParser(indexFile);
        }else{
            txt = TikaUtils.fileToTxt(indexFile);
        }


        log.info("get file content end ------------------ : ", txt);

        cf.setFile_contents(txt);

        boolean l = indexFile.delete();
        if(l){
            log.info("del - "+cf.getFile_name());
        }

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
