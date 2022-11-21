package config;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;

import java.util.Objects;

public class HibernateProvider {
    private static SessionFactory factory;

    public Session openSession() {
        return factory.openSession();
    }

    synchronized static void init() {
        if (Objects.nonNull(factory)) {
            return;
        }
        try {
            final ServiceRegistry build = new StandardServiceRegistryBuilder()
                    .configure()
                    .build();
            factory = new MetadataSources(build)
                    .buildMetadata()
                    .buildSessionFactory();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    static synchronized void destroy() {
        if (Objects.nonNull(factory)) {
            factory.close();
        }
    }
}
