package dev.toannv.interview.walk;

import dev.toannv.interview.walk.repository.base.WalkRepositoryFactoryBean;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import tech.jhipster.config.DefaultProfileUtil;
import tech.jhipster.config.JHipsterConstants;
import dev.toannv.interview.walk.config.ApplicationProperties;

import javax.annotation.PostConstruct;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;
import java.util.TimeZone;

@Slf4j
@SpringBootApplication(exclude = UserDetailsServiceAutoConfiguration.class)
@RequiredArgsConstructor
@EnableConfigurationProperties({ApplicationProperties.class})
@Configuration
@EntityScan(basePackageClasses = WalkApplication.class)
@EnableTransactionManagement
@EnableJpaRepositories(basePackageClasses = WalkApplication.class, repositoryFactoryBeanClass =
        WalkRepositoryFactoryBean.class)
@EnableAspectJAutoProxy(exposeProxy = true)
public class WalkApplication {

    private final Environment env;

    /**
     * Main method, used to run the application.
     *
     * @param args the command line arguments.
     */
    public static void main(final String[] args) {
        final SpringApplication app = new SpringApplication(WalkApplication.class);
        DefaultProfileUtil.addDefaultProfile(app);
        System.setProperty("hibernate.types.print.banner", "false");
        final Environment env = app.run(args).getEnvironment();
        logApplicationStartup(env);
    }

    private static void logApplicationStartup(final Environment env) {
        final String protocol = Optional.ofNullable(env.getProperty("server.ssl.key-store")).map(key -> "https")
                .orElse("http");
        final String serverPort = env.getProperty("server.port");
        final String contextPath = Optional.ofNullable(env.getProperty("server.servlet.context-path"))
                .filter(StringUtils::isNotBlank).orElse("/");
        String hostAddress = "localhost";
        try {
            hostAddress = InetAddress.getLocalHost().getHostAddress();
        } catch (final UnknownHostException e) {
            log.warn("The host name could not be determined, using `localhost` as fallback");
        }
        log.info("""
                Current system time: {}
                ----------------------------------------------------------\t
                    Application '{}[{}]' is running! Access URLs:\t
                    Local: \t\t{}://localhost:{}{}\t
                    External: \t{}://{}:{}{}\t
                    Profile(s): \t{}
                ----------------------------------------------------------
                """, new Date(), env.getProperty("spring.application.name"),
                env.getProperty("application" + ".app" + "-version"), protocol, serverPort, contextPath, protocol,
                hostAddress, serverPort, contextPath, env.getActiveProfiles());
    }

    /**
     * Initializes walk.
     * <p>
     * Spring profiles can be configured with a program argument --spring.profiles.active=your-active-profile
     * <p>
     * You can find more information on how profiles work with JHipster on
     * <a href="https://www.jhipster.tech/profiles/">https://www.jhipster.tech/profiles/</a>.
     */
    @PostConstruct
    public void initApplication() {
        final Collection<String> activeProfiles = Arrays.asList(env.getActiveProfiles());
        if (activeProfiles.contains(JHipsterConstants.SPRING_PROFILE_DEVELOPMENT) && activeProfiles.contains(JHipsterConstants.SPRING_PROFILE_PRODUCTION)) {
            log.error("You have miss configured your application! It should not run " + "with both the 'dev' and " +
                    "'prod' profiles at the same time.");
        }
        if (activeProfiles.contains(JHipsterConstants.SPRING_PROFILE_DEVELOPMENT) && activeProfiles.contains(JHipsterConstants.SPRING_PROFILE_CLOUD)) {
            log.error("You have miss configured your application! It should not " + "run with both the 'dev' and " +
                    "'cloud' profiles at the same time.");
        }

        // Setting Spring Boot SetTimeZone
        TimeZone.setDefault(TimeZone.getTimeZone(ZoneOffset.UTC));
    }

}
