import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.IllegalFormatException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by xuchen on 11/3/14.
 * “Handles the parsing of a single .vm file, and encapsulates access to the input code.
 * It reads VM commands, parses them, and provides convenient access to their components.
 * In addition, it removes all white space and comments.”
 */
public class Parser {
    private Scanner cmds;
    private String currentCmd;
    public static final int ARITHMETIC = 0;
    public static final int PUSH = 1;
    public static final int POP = 2;
    public static final int LABEL = 3;
    public static final int GOTO = 4;
    public static final int IF = 5;
    public static final int FUNCTION = 6;
    public static final int RETURN = 7;
    public static final int CALL = 8;
    public static final ArrayList<String> arithmeticCmds = new ArrayList<String>();
    private int argType;
    private String argument1;
    private int argument2;

    static {

        arithmeticCmds.add("add");arithmeticCmds.add("sub");arithmeticCmds.add("neg");arithmeticCmds.add("eq");arithmeticCmds.add("gt");
        arithmeticCmds.add("lt");arithmeticCmds.add("and");arithmeticCmds.add("or");arithmeticCmds.add("not");

    }

    /**
     * Opens the input file and get ready to parse it
     * @param fileIn
     */
    public Parser(File fileIn) {

        argType = -1;
        argument1 = "";
        argument2 = -1;

        try {
            cmds = new Scanner(fileIn);

            String preprocessed = "";
            String line = "";

            while(cmds.hasNext()){

                line = noComments(cmds.nextLine()).trim();

                if (line.length() > 0) {
                    preprocessed += line + "\n";
                }
            }

            cmds = new Scanner(preprocessed.trim());

        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
        }

    }


    /**
     * Are there more command to read
     * @return
     */
    public boolean hasMoreCommands(){

       return cmds.hasNextLine();
    }

    /**
     * Reads next command from the input and makes it current command
     * Be called only when hasMoreCommands() returns true
     */
    public void advance(){

        currentCmd = cmds.nextLine();
        argument1 = "";//initialize arg1
        argument2 = -1;//initialize arg2

        String[] segs = currentCmd.split(" ");

        if (segs.length > 3){

            throw new IllegalArgumentException("Too much arguments!");

        }

        if (arithmeticCmds.contains(segs[0])){

            argType = ARITHMETIC;
            argument1 = segs[0];

        }else if (segs[0].equals("return")) {

            argType = RETURN;
            argument1 = segs[0];

        }else {

            argument1 = segs[1];

            if(segs[0].equals("push")){

                argType = PUSH;

            }else if(segs[0].equals("pop")){

                argType = POP;

            }else if(segs[0].equals("label")){

                argType = LABEL;

            }else if(segs[0].equals("if-goto")){

                argType = IF;

            }else if (segs[0].equals("goto")){

                argType = GOTO;

            }else if (segs[0].equals("function")){

                argType = FUNCTION;

            }else if (segs[0].equals("call")){

                argType = CALL;

            }else {

                throw new IllegalArgumentException("Unknown Command Type!");

            }

            if (argType == PUSH || argType == POP || argType == FUNCTION || argType == CALL){

                try {

                    argument2 = Integer.parseInt(segs[2]);

                }catch (Exception e){

                    throw new IllegalArgumentException("Argument2 is not an integer!");

                }

            }
        }

    }

    /**
     * Return the type of current command
     * ARITHMETIC is returned for all ARITHMETIC type command
     * @return
     */
    public int commandType(){

        if (argType != -1) {

            return argType;

        }else {

            throw new IllegalStateException("No command!");

        }

    }

    /**
     * Return the first argument of current command
     * When it is ARITHMETIC, return it self
     * When it is RETURN, should not to be called
     * @return
     */
    public String arg1(){

        if (commandType() != RETURN){

            return argument1;

        }else {

            throw new IllegalStateException("Can not get arg1 from a RETURN type command!");

        }

    }

    /**
     * Return the second argument of current command
     * Be called when it is PUSH, POP, FUNCTION or CALL
     * @return
     */
    public int arg2(){

        if (commandType() == PUSH || commandType() == POP || commandType() == FUNCTION || commandType() == CALL){

            return argument2;

        }else {

            throw new IllegalStateException("Can not get arg2!");

        }

    }

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
     * Get extension from a filename
     * @param fileName
     * @return
     */
    public static String getExt(String fileName){

        int index = fileName.lastIndexOf('.');

        if (index != -1){

            return fileName.substring(index);

        }else {

            return "";

        }
    }
}
