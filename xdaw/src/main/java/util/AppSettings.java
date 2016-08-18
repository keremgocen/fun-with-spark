package util;

import lombok.Data;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by kerem on 8/18/16.
 */
@Data
public class AppSettings {
    private String externalBaseUrl;
    private String listenPort;
    private String cacheControlMaxAge;

    public void init() {
        Properties prop = new Properties();
        InputStream input = null;

        try {

            String filename = "config.properties";
            /*input = AppSettings.class.getClassLoader().getResourceAsStream(filename);
            if(input==null){
                System.out.println("Sorry, unable to find " + filename);
                return;
            }*/

            //load a properties file from class path, inside static method
            prop.load(getClass().getClassLoader().getResourceAsStream(filename));

            //get the property values
            externalBaseUrl = prop.getProperty("external.base.url");
            listenPort = prop.getProperty("internal.listen.port");
            cacheControlMaxAge = prop.getProperty("cachecontrol.maxage");

            System.out.println("================================");
            System.out.println("App Settings - (config.properties)");
            System.out.println(this);
            System.out.println("================================");

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally{
            if(input!=null){
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public String toString() {
        return "AppSettings{" +
                "externalBaseUrl='" + externalBaseUrl + '\'' +
                ", listenPort='" + listenPort + '\'' +
                ", cacheControlMaxAge='" + cacheControlMaxAge + '\'' +
                '}';
    }
}
