package hiber.config;

import hiber.model.Car;
import hiber.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;


@Configuration//Аннотация @Configuration: Обозначает класс как конфигурационный класс для Spring.
@PropertySource("classpath:db.properties")//Аннотация @PropertySource: Указывает на файл свойств db.properties, который содержит настройки базы данных.
@EnableTransactionManagement//@EnableTransactionManagement: Включает управление транзакциями для приложения.
@ComponentScan(value = "hiber")//@ComponentScan: Указывает Spring, где искать компоненты, которые должны быть автоматически обнаружены и зарегистрированы
public class AppConfig {

   @Autowired// @Autowired: Используется для внедрения зависимостей, в данном случае, для внедрения Environmen
   private Environment env;

   @Bean
   public DataSource getDataSource() {//Метод getDataSource(): Создает и настраивает источник данных для подключения к базе данных
      DriverManagerDataSource dataSource = new DriverManagerDataSource();
      dataSource.setDriverClassName(env.getProperty("db.driver"));
      dataSource.setUrl(env.getProperty("db.url"));
      dataSource.setUsername(env.getProperty("db.username"));
      dataSource.setPassword(env.getProperty("db.password"));
      return dataSource;
   }

   @Bean
   public LocalSessionFactoryBean getSessionFactory() {//Метод getSessionFactory(): Создает и настраивает фабрику сессий Hibernate, которая обеспечивает взаимодействие с базой данных.
      LocalSessionFactoryBean factoryBean = new LocalSessionFactoryBean();
      factoryBean.setDataSource(getDataSource());

      Properties props=new Properties();
      props.put("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
      props.put("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));

      factoryBean.setHibernateProperties(props);
      factoryBean.setAnnotatedClasses(User.class, Car.class); //добавила кар класс
      return factoryBean;
   }

   @Bean
   public HibernateTransactionManager getTransactionManager() {//Метод getTransactionManager(): Создает и настраивает менеджер транзакций Hibernate, который управляет транзакциями базы данных.
      HibernateTransactionManager transactionManager = new HibernateTransactionManager();
      transactionManager.setSessionFactory(getSessionFactory().getObject());
      return transactionManager;
   }
}
