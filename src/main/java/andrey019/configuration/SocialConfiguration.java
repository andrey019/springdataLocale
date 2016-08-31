package andrey019.configuration;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.social.UserIdSource;
import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurer;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.connect.web.ConnectController;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.google.connect.GoogleConnectionFactory;
import org.springframework.social.security.AuthenticationNameUserIdSource;
import org.springframework.social.vkontakte.connect.VKontakteConnectionFactory;

import javax.sql.DataSource;

@Configuration
@EnableSocial
public class SocialConfiguration implements SocialConfigurer {

    private final static String FACEBOOK_ID = "547901995417408";
    private final static String FACEBOOK_SECRET = "0ca404bdd6a11251f85a8253a2769267";
    private final static String GOOGLE_ID = "950741425322-cajgej1k39gu357ut0kaal666uv3s9h4.apps.googleusercontent.com";
    private final static String GOOGLE_SECRET = "oI7sf2lYg3MHdIahL1HvkXYr";
    private final static String VK_ID = "5612639";
    private final static String VK_SECRET = "6uBmM6SFLnKPBjawHRL2";


    @Autowired
    private DataSource dataSource;

    @Override
    public void addConnectionFactories(ConnectionFactoryConfigurer connectionFactoryConfigurer,
                                       Environment environment) {
        FacebookConnectionFactory facebookConnectionFactory = new FacebookConnectionFactory(FACEBOOK_ID, FACEBOOK_SECRET);
        facebookConnectionFactory.setScope("public_profile,email");
        connectionFactoryConfigurer.addConnectionFactory(facebookConnectionFactory);

        GoogleConnectionFactory googleConnectionFactory = new GoogleConnectionFactory(GOOGLE_ID, GOOGLE_SECRET);
        googleConnectionFactory.setScope("email");
        connectionFactoryConfigurer.addConnectionFactory(googleConnectionFactory);

        VKontakteConnectionFactory vKontakteConnectionFactory = new VKontakteConnectionFactory(VK_ID, VK_SECRET);
        vKontakteConnectionFactory.setScope("email");
        connectionFactoryConfigurer.addConnectionFactory(vKontakteConnectionFactory);
    }

    @Override
    public UserIdSource getUserIdSource() {
        return new AuthenticationNameUserIdSource();
    }

    @Override
    public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
        return new JdbcUsersConnectionRepository(dataSource, connectionFactoryLocator, Encryptors.noOpText());
    }

    @Bean
    public ConnectController connectController(ConnectionFactoryLocator connectionFactoryLocator,
                                               ConnectionRepository connectionRepository) {
        return new ConnectController(connectionFactoryLocator, connectionRepository);
    }

    @Bean
    public ProviderSignInUtils providerSignInUtils(ConnectionFactoryLocator connectionFactoryLocator,
                                                   UsersConnectionRepository connectionRepository) {
        return new ProviderSignInUtils(connectionFactoryLocator, connectionRepository);
    }
}
