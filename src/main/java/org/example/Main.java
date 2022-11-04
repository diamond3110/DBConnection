package org.example;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.metamodel.MetadataSources;

public class Main  {

    @Getter
    private static SessionFactory sessionFactory;
    static{
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure()
                .build();
        try{
            sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        }catch(Exception e){
            StandardServiceRegistryBuilder.destroy(registry);
        }

    }
    public static void main(String[] args) {
        System.out.println("Hello world!");
        MysqlPreference mysqlPreference = new MysqlPreference();
        System.out.println("Данные для подключения: " +
                "\nip: " + mysqlPreference.getIp() +
                "\n port: " + mysqlPreference.getPort() +
                "\n username: " + mysqlPreference.getUsername() +
                "\n password: " + mysqlPreference.getPassword() +
                "\n database: " + mysqlPreference.getDefaultSchema());
        //SessionFactory sessionFactory = buildSessionFactory(mysqlPreference);
        System.out.println();

    }
    static SessionFactory buildSessionFactory(MysqlPreference mysql) {

        Configuration config = new Configuration();

        config.setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
        config.setProperty("hibernate.connection.url", String.format("jdbc:mysql://%s:%d/%s", mysql.getIp(), mysql.getPort(), mysql.getDefaultSchema()));
        config.setProperty("hibernate.connection.CharSet", "utf8");
        config.setProperty("hibernate.connection.characterEncoding", "utf8");
        config.setProperty("hibernate.connection.useUnicode", "true");
        config.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        config.setProperty("hibernate.connection.username", mysql.getUsername());
        config.setProperty("hibernate.connection.password", mysql.getPassword());

        config.setProperty("show_sql", "true");
        config.setProperty("hibernate.jdbc.batch_size", "30");

        config.setProperty("hibernate.c3p0.min_size", "5");
        config.setProperty("hibernate.c3p0.max_size", "20");
        config.setProperty("hibernate.c3p0.timeout", "60");
        config.setProperty("hibernate.c3p0.max_statements", "50");
        config.setProperty("hibernate.c3p0.idle_test_period", "60");

        config.setProperty("hibernate.hbm2ddl.auto", "update");

        StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(config.getProperties()).build();
        return config.buildSessionFactory(serviceRegistry);
    }
}



@Getter
@Setter
class MysqlPreference {
    private String ip = "127.0.0.1";
    private int port = 3306;
    private String username = "root";
    private String password = "1234";
    private String defaultSchema = "piine";

}