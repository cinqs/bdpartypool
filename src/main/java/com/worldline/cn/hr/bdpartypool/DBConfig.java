package com.worldline.cn.hr.bdpartypool;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

import com.worldline.cn.hr.bdpartypool.mapper.Mapper;
import com.worldline.cn.hr.bdpartypool.utils.FileUtils;

@org.springframework.context.annotation.Configuration
public class DBConfig {
	
	private Logger logger = Logger.getLogger(DBConfig.class);
	
	private @Value("${db.mybatis.config.path}") String mybatisConfigPath;
	private @Value("${db.mybatis.env}") String env;
	private @Value("${db.config.path}") String configPath;

	@Bean
	public SqlSessionFactory sqlSessionFactory() throws IOException {
		logger.info("creating a sqlSessionFactory bean...");
		
		try(InputStream mybatisIS = FileUtils.getInputStream(mybatisConfigPath);
				InputStream dbIS = FileUtils.getInputStream(configPath)) {
			Properties props = new Properties();
			props.load(dbIS);
			
			SqlSessionFactory ssf = new SqlSessionFactoryBuilder()
					.build(mybatisIS, env, props);
			
			ssf.getConfiguration().getMapperRegistry().addMappers("com.worldline.cn", Mapper.class);
						
			return ssf;
		}
	}
}
