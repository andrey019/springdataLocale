package andrey019.model.dao;


public enum  SocialMediaService {

//    USER("USER"),
//    DBA("DBA"),
    FACEBOOK("FACEBOOK");

    private String provider;

    private SocialMediaService(final String role){
        this.provider = role;
    }

    public String getProvider(){
        return provider;
    }
}
