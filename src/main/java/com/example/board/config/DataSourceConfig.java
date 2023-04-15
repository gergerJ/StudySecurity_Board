package com.example.board.config;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@MapperScan(value = "com.example.board.mapper" , sqlSessionFactoryRef = "SqlSessionFactory")
@ConstructorBinding    // *.properties or *.yml 파일에 있는 property를 자바 클래스에 값을 가져와 사용할 수 있게 해주는 어노테이션이다. @Value를 통해서도 값을 가지고 올 수 있지만 클래스 파일로 관리할 수 있다는 점에서 차이가 있다.
@Slf4j
public class DataSourceConfig {

    private final String MAPPER_XML_PATH;  // mapper.xml 경로를 읽을 PATH 지정 

    public DataSourceConfig(@Value("${mybatis.mapper-locations}") String MAPPER_XML_PATH) {
        log.info("DataSourceConfig 1번 ");
        //@Value 를 통해 yml의 값을 가져와서 MAPPER_XML_PATH의 mapper.xml 경로 가져오기
        this.MAPPER_XML_PATH = MAPPER_XML_PATH;
    }

    @Bean(name = "dataSource")
    @ConfigurationProperties(prefix = "spring.datasource")   // @ConstructorBinding 을 통해 properties 의 값을 가져올 때 사용한다.
    // *.properties, *.yml에 있는에 있는 값을 가져와서 사용할 때 사용!
    public DataSource DataSource(){
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "SqlSessionFactory")
    public SqlSessionFactory SqlSessionFactory(@Qualifier("dataSource") DataSource DataSource, ApplicationContext applicationContext) throws Exception {
        log.info("DataSourceConfig 2번");
        // 의존성 주입 시 @Qualifier를 통해 Bean을 우선적으로 찾아서 맵핑 시켜준다.
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(DataSource);
        sqlSessionFactoryBean.setMapperLocations(applicationContext.getResources(MAPPER_XML_PATH));  // resources 에서의 위치
        sqlSessionFactoryBean.setTypeAliasesPackage("com.example.board.dto");
        return sqlSessionFactoryBean.getObject();
    }

    //SqlSessionTemplate에 SessionFactory 를 이용하여 Mybatis를 연결하여 위해 설정
    //root-context.xml에 SqlSessionFactory를 등록해서 사용하는 것과 동일함
    @Bean(name = "SessionTemplate")
    public SqlSessionTemplate SqlSessionTemplate(@Qualifier("SqlSessionFactory") SqlSessionFactory firstSqlSessionFactory){
        log.info("DataSourceConfig 3번");
        return new SqlSessionTemplate(firstSqlSessionFactory);
    }
}


