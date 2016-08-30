package andrey019.util;


import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.validator.routines.EmailValidator;

import java.text.SimpleDateFormat;
import java.util.Date;


public class TestsUtil {

    public static void main(String[] args) {

        EmailValidator emailValidator = EmailValidator.getInstance();
        System.out.println(emailValidator.isValid("sdfs@asdf"));
        System.out.println(emailValidator.isValid("sdfs@asdf.com"));
        System.out.println(emailValidator.isValid("sdfsasdf.com"));
        System.out.println(emailValidator.isValid("sdfs@as.ua"));

        StringBuilder stringBuilder = new StringBuilder();
        System.out.println(stringBuilder.toString());

        System.out.println(RandomStringUtils.random(10, true, true));

        System.out.println(System.currentTimeMillis());

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(dateFormat.format(System.currentTimeMillis()));

//        Long time = new Long(1477741569385);
//        System.out.println(time - System.currentTimeMillis());
    }
}
