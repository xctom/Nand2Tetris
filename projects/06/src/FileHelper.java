import java.io.File;

/**
 * Created by xuchen on 10/25/14.
 */
public class FileHelper {

    /**
     * Delete comments(String after "//") from a String
     * @param strIn
     * @return
     */
    public static String noComments(String strIn){

        int position = strIn.indexOf("//");

        if (position != -1){

            strIn = strIn.substring(0, position);

        }

        return strIn;
    }

    /**
     * Delete spaces from a String
     * @param strIn
     * @return
     */
    public static String noSpaces(String strIn){
        String result = "";

        if (strIn.length() != 0){

            String[] segs = strIn.split(" ");

            for (String s: segs){
                result += s;
            }
        }

        return result;
    }

    /**
     * return whether a file is an .asm file
     * @param fileIn
     * @return
     */
    public static boolean isAsm(File fileIn){

        String filename = fileIn.getName();
        int position = filename.lastIndexOf(".");

        if (position != -1) {

            String ext = filename.substring(position);

            if (ext.toLowerCase().equals(".asm")) {
                return true;
            }
        }

        return false;
    }

    /**
     * pad 0 to the input string on the left until the length is equal to the input length
     * @param strIn
     * @param len
     * @return
     */
    public static String padLeftZero(String strIn, int len){

        for (int i = strIn.length(); i < len; i++){
            strIn = "0" + strIn;
        }

        return strIn;
    }

}
