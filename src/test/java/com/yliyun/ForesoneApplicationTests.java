package com.yliyun;

import com.yliyun.service.dao.FilesService;
import com.yliyun.util.AppConstants;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ForesoneApplicationTests {


    @Autowired
    private FilesService filesService;


    @Test
    public void testJDBC(){
        filesService.getFilesList("personal_file");
        filesService.getFilesList("group_file");

        filesService.getFilesList("public_file");

    }


//	@Before
//	public void setUp() {
//		jdbcTemplate1.update("DELETE  FROM  USER ");
//		jdbcTemplate2.update("DELETE  FROM  USER ");
//	}

    @Test
    public void test() throws Exception {

        System.out.println(filesService.getNoIndexCount());

        //  List<Map<String,Object>> list = jdbcTemplate1.queryForList("select * from group_file");

        //	System.out.println( Arrays.asList(list).toString());


    }

//    @Test
//    public void testGetFiles(){
//        System.out.println(filesService.getFilesList(AppConstants.TABLE_GROUP_FILE));
//    }
//
//    @Test
//    public  void testDownload(){
//
//        try {
//            filesService.download("http://192.168.0.130/group1/M00/00/0B/wKgAglgAkpeAFhKYAAFA4xm-U2A687.pdf", "test.pdf");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//



}
