import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by xuchen on 11/3/14.
 * Translates VM commands into HACK assembly code
 */
public class CodeWriter {

    private int arthJumpFlag;
    private PrintWriter outPrinter;
    private static final Pattern labelReg = Pattern.compile("^[^0-9][0-9A-Za-z\\_\\:\\.\\$]+");
    private static int labelCnt = 0;
    private static String fileName = "";

    /**
     * Open an output file and be ready to write content
     * @param fileOut can be a directory!
     */
    public CodeWriter(File fileOut) {

        try {

            fileName = fileOut.getName();
            outPrinter = new PrintWriter(fileOut);
            arthJumpFlag = 0;

        } catch (FileNotFoundException e) {

            e.printStackTrace();

        }

    }

    /**
     *“If the program’s argument is a directory name rather than a file name,
     * the main program should process all the .vm files in this directory.
     * In doing so, it should use a separate Parser for handling each input file and a single CodeWriter for handling the output.”
     *
     * Inform the CodeWrither that the translation of a new VM file is started
     */
    public void setFileName(File fileOut){

        fileName = fileOut.getName();

    }

    /**
     * Write the assembly code that is the translation of the given arithmetic command
     * @param command
     */
    public void writeArithmetic(String command){

        if (command.equals("add")){

            outPrinter.print(arithmeticTemplate1() + "M=M+D\n");

        }else if (command.equals("sub")){

            outPrinter.print(arithmeticTemplate1() + "M=M-D\n");

        }else if (command.equals("and")){

            outPrinter.print(arithmeticTemplate1() + "M=M&D\n");

        }else if (command.equals("or")){

            outPrinter.print(arithmeticTemplate1() + "M=M|D\n");

        }else if (command.equals("gt")){

            outPrinter.print(arithmeticTemplate2("JLE"));//not <=
            arthJumpFlag++;

        }else if (command.equals("lt")){

            outPrinter.print(arithmeticTemplate2("JGE"));//not >=
            arthJumpFlag++;

        }else if (command.equals("eq")){

            outPrinter.print(arithmeticTemplate2("JNE"));//not <>
            arthJumpFlag++;

        }else if (command.equals("not")){

            outPrinter.print("@SP\nA=M-1\nM=!M\n");

        }else if (command.equals("neg")){

            outPrinter.print("D=0\n@SP\nA=M-1\nM=D-M\n");

        }else {

            throw new IllegalArgumentException("Call writeArithmetic() for a non-arithmetic command");

        }

    }

    /**
     * Write the assembly code that is the translation of the given command
     * where the command is either PUSH or POP
     * @param command PUSH or POP
     * @param segment
     * @param index
     */
    public void writePushPop(int command, String segment, int index){

        if (command == Parser.PUSH){

            if (segment.equals("constant")){

                outPrinter.print("@" + index + "\n" + "D=A\n@SP\nA=M\nM=D\n@SP\nM=M+1\n");

            }else if (segment.equals("local")){

                outPrinter.print(pushTemplate1("LCL",index,false));

            }else if (segment.equals("argument")){

                outPrinter.print(pushTemplate1("ARG",index,false));

            }else if (segment.equals("this")){

                outPrinter.print(pushTemplate1("THIS",index,false));

            }else if (segment.equals("that")){

                outPrinter.print(pushTemplate1("THAT",index,false));

            }else if (segment.equals("temp")){

                outPrinter.print(pushTemplate1("R5", index + 5,false));

            }else if (segment.equals("pointer") && index == 0){

                outPrinter.print(pushTemplate1("THIS",index,true));

            }else if (segment.equals("pointer") && index == 1){

                outPrinter.print(pushTemplate1("THAT",index,true));

            }else if (segment.equals("static")){
                //every file has its static space
                outPrinter.print("@" + fileName + index + "\n" + "D=M\n@SP\nA=M\nM=D\n@SP\nM=M+1\n");

            }

        }else if(command == Parser.POP){

            if (segment.equals("local")){

                outPrinter.print(popTemplate1("LCL",index,false));

            }else if (segment.equals("argument")){

                outPrinter.print(popTemplate1("ARG",index,false));

            }else if (segment.equals("this")){

                outPrinter.print(popTemplate1("THIS",index,false));

            }else if (segment.equals("that")){

                outPrinter.print(popTemplate1("THAT",index,false));

            }else if (segment.equals("temp")){

                outPrinter.print(popTemplate1("R5", index + 5,false));

            }else if (segment.equals("pointer") && index == 0){

                outPrinter.print(popTemplate1("THIS",index,true));

            }else if (segment.equals("pointer") && index == 1){

                outPrinter.print(popTemplate1("THAT",index,true));

            }else if (segment.equals("static")){
                //every file has its static space
                outPrinter.print("@" + fileName + index + "\nD=A\n@R13\nM=D\n@SP\nAM=M-1\nD=M\n@R13\nA=M\nM=D\n");

            }

        }else {

            throw new IllegalArgumentException("Call writePushPop() for a non-pushpop command");

        }

    }

    /**
     * Write assembly code that effects the label command
     * @param label
     */
    public void writeLabel(String label){

        Matcher m = labelReg.matcher(label);

        if (m.find()){

            outPrinter.print("(" + label +")\n");

        }else {

            throw new IllegalArgumentException("Wrong label format!");

        }

    }

    /**
     * Write assembly code that effects the goto command
     * @param label
     */
    public void writeGoto(String label){

        Matcher m = labelReg.matcher(label);

        if (m.find()){

            outPrinter.print("@" + label +"\n0;JMP\n");

        }else {

            throw new IllegalArgumentException("Wrong label format!");

        }

    }

    /**
     * Write assembly code that effects the if-goto command
     * @param label
     */
    public void writeIf(String label){

        Matcher m = labelReg.matcher(label);

        if (m.find()){

            outPrinter.print(arithmeticTemplate1() + "@" + label +"\nD;JNE\n");

        }else {

            throw new IllegalArgumentException("Wrong label format!");

        }

    }

    /**
     * Write assembly code that effects the VM initialization
     * also called BOOTSTRAP CODE.
     * This code must be placed at the beginning of the output file
     */
    public void writeInit(){

        outPrinter.print("@256\n" +
                         "D=A\n" +
                         "@SP\n" +
                         "M=D\n");
        writeCall("Sys.init",0);

    }

    /**
     * Write assembly code that effects the call command
     * @param functionName
     * @param numArgs
     */
    public void writeCall(String functionName, int numArgs){

        String newLabel = "RETURN_LABEL" + (labelCnt++);

        outPrinter.print("@" + newLabel + "\n" + "D=A\n@SP\nA=M\nM=D\n@SP\nM=M+1\n");//push return address
        outPrinter.print(pushTemplate1("LCL",0,true));//push LCL
        outPrinter.print(pushTemplate1("ARG",0,true));//push ARG
        outPrinter.print(pushTemplate1("THIS",0,true));//push THIS
        outPrinter.print(pushTemplate1("THAT",0,true));//push THAT

        outPrinter.print("@SP\n" +
                        "D=M\n" +
                        "@5\n" +
                        "D=D-A\n" +
                        "@" + numArgs + "\n" +
                        "D=D-A\n" +
                        "@ARG\n" +
                        "M=D\n" +
                        "@SP\n" +
                        "D=M\n" +
                        "@LCL\n" +
                        "M=D\n" +
                        "@" + functionName + "\n" +
                        "0;JMP\n" +
                        "(" + newLabel + ")\n"
                        );

    }

    /**
     * Write assembly code that effects the return command
     */
    public void writeReturn(){

        outPrinter.print(returnTemplate());

    }

    /**
     * Write assembly code that effects the function command
     * @param functionName
     * @param numLocals
     */
    public void writeFunction(String functionName, int numLocals){

        outPrinter.print("(" + functionName +")\n");

        for (int i = 0; i < numLocals; i++){

            writePushPop(Parser.PUSH,"constant",0);

        }

    }

    /**
     * save value of pre frame to given position
     * @param position
     * @return
     */
    public String preFrameTemplate(String position){

        return "@R11\n" +
                "D=M-1\n" +
                "AM=D\n" +
                "D=M\n" +
                "@" + position + "\n" +
                "M=D\n";

    }

    /**
     * assembly code template for return command
     * use R13 for FRAME R14 for RET
     * @return
     */
    public String returnTemplate(){

        return "@LCL\n" +
                "D=M\n" +
                "@R11\n" +
                "M=D\n" +
                "@5\n" +
                "A=D-A\n" +
                "D=M\n" +
                "@R12\n" +
                "M=D\n" +
                popTemplate1("ARG",0,false) +
                "@ARG\n" +
                "D=M\n" +
                "@SP\n" +
                "M=D+1\n" +
                preFrameTemplate("THAT") +
                preFrameTemplate("THIS") +
                preFrameTemplate("ARG") +
                preFrameTemplate("LCL") +
                "@R12\n" +
                "A=M\n" +
                "0;JMP\n";
    }

    /**
     * Close the output file
     */
    public void close(){

        outPrinter.close();

    }

    /**
     * Template for add sub and or
     * @return
     */
    private String arithmeticTemplate1(){

        return "@SP\n" +
                "AM=M-1\n" +
                "D=M\n" +
                "A=A-1\n";

    }

    /**
     * Template for gt lt eq
     * @param type JLE JGT JEQ
     * @return
     */
    private String arithmeticTemplate2(String type){

        return "@SP\n" +
                "AM=M-1\n" +
                "D=M\n" +
                "A=A-1\n" +
                "D=M-D\n" +
                "@FALSE" + arthJumpFlag + "\n" +
                "D;" + type + "\n" +
                "@SP\n" +
                "A=M-1\n" +
                "M=-1\n" +
                "@CONTINUE" + arthJumpFlag + "\n" +
                "0;JMP\n" +
                "(FALSE" + arthJumpFlag + ")\n" +
                "@SP\n" +
                "A=M-1\n" +
                "M=0\n" +
                "(CONTINUE" + arthJumpFlag + ")\n";

    }


    /**
     * Template for push local,this,that,argument,temp,pointer,static
     * @param segment
     * @param index
     * @param isDirect Is this command a direct addressing?
     * @return
     */
    private String pushTemplate1(String segment, int index, boolean isDirect){

        //When it is a pointer, just read the data stored in THIS or THAT
        String noPointerCode = (isDirect)? "" : "@" + index + "\n" + "A=D+A\nD=M\n";

        return "@" + segment + "\n" +
                "D=M\n"+
                noPointerCode +
                "@SP\n" +
                "A=M\n" +
                "M=D\n" +
                "@SP\n" +
                "M=M+1\n";

    }

    /**
     * Template for pop local,this,that,argument,temp,pointer,static
     * @param segment
     * @param index
     * @param isDirect Is this command a direct addressing?
     * @return
     */
    private String popTemplate1(String segment, int index, boolean isDirect){

        //When it is a pointer R13 will store the address of THIS or THAT
        String noPointerCode = (isDirect)? "D=A\n" : "D=M\n@" + index + "\nD=D+A\n";

        return "@" + segment + "\n" +
                noPointerCode +
                "@R13\n" +
                "M=D\n" +
                "@SP\n" +
                "AM=M-1\n" +
                "D=M\n" +
                "@R13\n" +
                "A=M\n" +
                "M=D\n";

    }

}
