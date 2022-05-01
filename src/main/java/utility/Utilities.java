package utility;

import com.github.javafaker.Faker;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Utilities {

    public static String root = System.getProperty("user.dir");

    public static Properties loadProperties(){
        Properties prop = new Properties();
        InputStream ism;

        try {
            ism = new FileInputStream(root+"/src/config.properties");
            prop.load(ism);
            ism.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return prop;
    }

    public static void main(String[] args) {
        Faker faker = new Faker();
        String password = faker.internet().password(8, 10);
        System.out.println(password);
    }
}
