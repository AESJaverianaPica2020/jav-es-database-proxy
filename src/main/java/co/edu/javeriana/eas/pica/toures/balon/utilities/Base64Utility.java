package co.edu.javeriana.eas.pica.toures.balon.utilities;

import org.apache.tomcat.util.codec.binary.Base64;

public class Base64Utility {

    private Base64Utility() {

    }

    public static String decodeString(String encode) {
        byte[] decodeBytes = Base64.decodeBase64(encode);
        return new String(decodeBytes);
    }

}
