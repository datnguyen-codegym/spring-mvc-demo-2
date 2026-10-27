package codegym.vn;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

/**
 * Cấu hình tầng persistence (DataSource, JPA/Hibernate, transaction).
 * Tách riêng khỏi {@link AppConfig} (vốn chỉ lo phần web/MVC) để rõ trách nhiệm.
 *
 * Giá trị kết nối DB đọc từ application.properties, ưu tiên override bằng
 * biến môi trường DB_* (DB_DRIVER, DB_URL, DB_USERNAME, DB_PASSWORD).
 * Không hardcode password trong source.
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "codegym.vn.jpa_repo")
@PropertySource("classpath:application.properties")
public class PersistenceConfig {

    @Value("${DB_DRIVER:${db.driver}}")
    private String dbDriver;

    @Value("${DB_URL:${db.url}}")
    private String dbUrl;

    @Value("${DB_USERNAME:${db.username}}")
    private String dbUsername;

    @Value("${DB_PASSWORD:${db.password:}}")
    private String dbPassword;

    @Value("${hibernate.dialect:org.hibernate.dialect.MySQL8Dialect}")
    private String hibernateDialect;

    @Value("${hibernate.show_sql:false}")
    private String hibernateShowSql;

    @Value("${hibernate.hbm2ddl.auto:update}")
    private String hibernateHbm2ddl;

    // Cần thiết để phân giải placeholder ${...} cho các field @Value ở trên.
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(dbDriver);
        dataSource.setUrl(dbUrl);
        dataSource.setUsername(dbUsername);
        dataSource.setPassword(dbPassword);
        return dataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean emf =
                new LocalContainerEntityManagerFactoryBean();
        emf.setDataSource(dataSource());
        emf.setPackagesToScan("codegym.vn.entity");
        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        emf.setJpaVendorAdapter(vendorAdapter);
        emf.setJpaProperties(jpaProperties());
        return emf;
    }

    private Properties jpaProperties() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.dialect", hibernateDialect);
        properties.setProperty("hibernate.show_sql", hibernateShowSql);
        properties.setProperty("hibernate.hbm2ddl.auto", hibernateHbm2ddl);
        return properties;
    }

    @Bean
    public PlatformTransactionManager transactionManager(
            EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
