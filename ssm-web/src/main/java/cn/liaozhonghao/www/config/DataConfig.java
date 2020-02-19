package cn.liaozhonghao.www.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.github.pagehelper.PageHelper;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@MapperScan("cn.liaozhonghao.www.dao")
public class DataConfig {

    @Bean
    public Properties config() throws IOException {
        Properties properties = new Properties();
        properties.load(new ClassPathResource("jdbc.properties").getInputStream());
        return properties;

    }

    @Bean(initMethod = "init",destroyMethod = "close")
    //@Bean
    public DataSource dataSource(@Qualifier("config") Properties configure) throws SQLException {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(configure.getProperty("jdbc.url"));
        dataSource.setUsername(configure.getProperty("jdbc.username"));
        dataSource.setPassword(configure.getProperty("jdbc.password"));
        /*
         * 配置初始化大小、最小、最
         */
        dataSource.setInitialSize(1);
        dataSource.setMinIdle(1);
        dataSource.setMaxActive(10);
        /*
         * 配置获取连接等待超时的时间
         */
        dataSource.setMaxWait(60000);
        /*
         * 配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒
         */
        dataSource.setTimeBetweenEvictionRunsMillis(60000);
        /*
         * 配置一个连接在池中最小生存的时间，单位是毫秒
         */
        dataSource.setMinEvictableIdleTimeMillis(300000);

        dataSource.setValidationQuery("SELECT 'X'");
        dataSource.setTestWhileIdle(true);
        dataSource.setTestOnBorrow(false);
        dataSource.setTestOnReturn(false);

        /*
         * 打开PSCache，并且指定每个连接上PSCache的大小
         */
        dataSource.setPoolPreparedStatements(false);
        ;
        dataSource.setMaxPoolPreparedStatementPerConnectionSize(20);
        /*
         * 配置监控统计拦截的filters
         */
        dataSource.setFilters("stat");

        return dataSource;
    }

    @Bean
    public SqlSessionFactoryBean sqlSessionFactory(DataSource dataSource, ApplicationContext applicationContext) throws IOException {
        SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource);
        //sessionFactoryBean.setConfigLocation(applicationContext.getResource("classpath:mybatis-config.xml"));
        //sessionFactoryBean.setMapperLocations(applicationContext.getResources("classpath:mapper/*.xml"));
        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        configuration.setMapUnderscoreToCamelCase(true);
        // 配置setting属性
        sessionFactoryBean.setConfiguration(configuration);
        PageHelper pageHelper = new PageHelper();
        Properties properties = new Properties();
        properties.setProperty("dialect", "mysql");
        pageHelper.setProperties(properties);
        // 配置插件
        sessionFactoryBean.setPlugins(pageHelper);
        return sessionFactoryBean;
    }

    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setBasePackage("cn.liaozhonghao.www.dao");
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
        return mapperScannerConfigurer;
    }

    @Bean
    public DataSourceTransactionManager transactionManager(DataSource dataSource) {
        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
        dataSourceTransactionManager.setDataSource(dataSource);
        return dataSourceTransactionManager;
    }
}
