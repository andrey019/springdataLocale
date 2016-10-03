package andrey019.configuration;

import andrey019.LiqPay.LiqPay;
import andrey019.LiqPay.LiqPayApi;
import andrey019.service.maintenance.ConfirmationCleanUpService;
import andrey019.service.maintenance.MailSenderService;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

import javax.annotation.PostConstruct;
import java.util.Properties;


@Configuration
@PropertySource("classpath:/properties/app.properties")
@ComponentScan("andrey019")
@EnableWebMvc
public class AppConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private Environment environment;

    @PostConstruct
    private void servicesInit() {
        MailSenderService.getInstance().start();
        ConfirmationCleanUpService.getInstance().start();
    }

    @Bean
    public LiqPayApi getLiqPay() {
        return new LiqPay(environment.getProperty("liqpay.id"), environment.getProperty("liqpay.pass"),
                environment.getProperty("liqpay.callback.url"), environment.getProperty("liqpay.redirect.url"));
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
        mailSender.setHost(environment.getProperty("mail.host"));
        mailSender.setPort(Integer.valueOf(environment.getProperty("mail.port")));
        mailSender.setUsername(environment.getProperty("mail.user"));
        mailSender.setPassword(environment.getProperty("mail.pass"));

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
