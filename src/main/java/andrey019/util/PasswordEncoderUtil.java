package andrey019.util;


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Scanner;

public class PasswordEncoderUtil {
    public static void main(String[] args) {
        System.out.print("Enter password: ");
        String password = new Scanner(System.in).nextLine();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        System.out.println(encoder.encode(password));
    }
}
