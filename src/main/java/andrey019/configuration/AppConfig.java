package andrey019.configuration;

import andrey019.service.maintenance.ConfirmationCleanUpService;
import andrey019.service.maintenance.MailSenderService;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

import javax.annotation.PostConstruct;
import java.util.Properties;
import java.util.TimeZone;


@org.springframework.context.annotation.Configuration
@ComponentScan("andrey019")
@EnableWebMvc
public class AppConfig extends WebMvcConfigurerAdapter {

    @PostConstruct
    private void servicesInit() {
        MailSenderService.getInstance().start();
        ConfirmationCleanUpService.getInstance().start();
    }

    @Bean
    public MailSenderService getMailSenderService() {
        return MailSenderService.getInstance();
    }

    @Bean
    public ConfirmationCleanUpService getConfirmationCleanUpService() {
        return ConfirmationCleanUpService.getInstance();
    }

    @Bean
    public EmailValidator getEmailValidator() {
        return EmailValidator.getInstance();
    }

    @Bean
    public JavaMailSender getMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.mail.ru");
        mailSender.setPort(465);
        mailSender.setUsername("wunderwaffelapp@mail.ru");
        mailSender.setPassword("90gdfg70gsds897");

        Properties javaMailProperties = new Properties();
        javaMailProperties.put("mail.smtp.starttls.enable", "true");
        javaMailProperties.put("mail.smtp.auth", "true");
        javaMailProperties.put("mail.transport.protocol", "smtps");
        javaMailProperties.put("mail.debug", "false");

        mailSender.setJavaMailProperties(javaMailProperties);
        return mailSender;
    }

    @Bean
    public UrlBasedViewResolver setupViewResolver() {
        UrlBasedViewResolver resolver = new UrlBasedViewResolver();
        resolver.setPrefix("/pages/");
        resolver.setSuffix(".jsp");
        resolver.setViewClass(JstlView.class);
        resolver.setOrder(1);
        return resolver;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/").setCachePeriod(31536000);
    }
}
