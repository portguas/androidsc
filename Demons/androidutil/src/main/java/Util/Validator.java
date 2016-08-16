package Util;

/**
 * Created by Andy on 2016/3/22.
 */
public class Validator {
    /**
     * check whether be empty/null or not
     *
     * @param string
     * @return
     */
    public static boolean isEffective(String string) {
        if ((string == null) || ("".equals(string)) || (" ".equals(string))
                || ("null".equals(string)) || ("\n".equals(string))) {
            return false;
        }
        else {
            return true;
        }
    }

    public static boolean isNumber( String s ){

        for( int i=0 ; i<s.length() ;  ++i ){

            char c = s.charAt(i);

            if( c < '0' || c>'9')
                return false;
        }
        return true;
    }

}
