package andrey019.service.auth;


import andrey019.model.json.JsonProfile;

public interface ProfileService {

    JsonProfile getProfile(String email);

    String updateProfile(String email, JsonProfile jsonProfile);
}
