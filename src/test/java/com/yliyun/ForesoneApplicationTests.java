package com.yliyun;

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

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ForesoneApplicationTests {

	@Autowired
	@Qualifier("primaryJdbcTemplate")
	protected JdbcTemplate jdbcTemplate1;

	@Autowired
	@Qualifier("secondaryJdbcTemplate")
	protected JdbcTemplate jdbcTemplate2;

//	@Before
//	public void setUp() {
//		jdbcTemplate1.update("DELETE  FROM  USER ");
//		jdbcTemplate2.update("DELETE  FROM  USER ");
//	}

	@Test
	public void test() throws Exception {

        List<Map<String,Object>> list = jdbcTemplate1.queryForList("select * from group_file");

		System.out.println( Arrays.asList(list).toString());

	}




}
