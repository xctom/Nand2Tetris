import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by xuchen on 11/3/14.
 */
public class VMtranslator {

    /**
     * Return all the .vm files in a directory
     * @param dir
     * @return
     */
    public static ArrayList<File> getVMFiles(File dir){

        File[] files = dir.listFiles();

        ArrayList<File> result = new ArrayList<File>();

        for (File f:files){

            if (f.getName().endsWith(".vm")){

                result.add(f);

            }

        }

        return result;

    }

    public static void main(String[] args) {

        //String fileInName = "/Users/xuchen/Documents/IntroToComputerSystem/nand2tetris/projects/07/MemoryAccess/StaticTest/";

        if (args.length != 1){

            System.out.println("Usage:java VMtranslator [filename|directory]");

        }else {

            String fileInName = args[0];

            File fileIn = new File(fileInName);

            String fileOutPath = "";

            File fileOut;

            CodeWriter writer;

            ArrayList<File> vmFiles = new ArrayList<File>();

            if (fileIn.isFile()) {

                //if it is a single file, see whether it is a vm file
                String path = fileIn.getAbsolutePath();

                if (!Parser.getExt(path).equals(".vm")) {

                    throw new IllegalArgumentException(".vm file is required!");

                }

                vmFiles.add(fileIn);

                fileOutPath = fileIn.getAbsolutePath().substring(0, fileIn.getAbsolutePath().lastIndexOf(".")) + ".asm";

            } else if (fileIn.isDirectory()) {

                //if it is a directory get all vm files under this directory
                vmFiles = getVMFiles(fileIn);

                //if no vn file in this directory
                if (vmFiles.size() == 0) {

                    throw new IllegalArgumentException("No vm file in this directory");

                }

                fileOutPath = fileIn.getAbsolutePath() + "/" +  fileIn.getName() + ".asm";
            }

            fileOut = new File(fileOutPath);
            writer = new CodeWriter(fileOut);

            writer.writeInit();

            for (File f : vmFiles) {

                writer.setFileName(f);

                Parser parser = new Parser(f);

                int type = -1;

                //start parsing
                while (parser.hasMoreCommands()) {

                    parser.advance();

                    type = parser.commandType();

                    if (type == Parser.ARITHMETIC) {

                        writer.writeArithmetic(parser.arg1());

                    } else if (type == Parser.POP || type == Parser.PUSH) {

                        writer.writePushPop(type, parser.arg1(), parser.arg2());

                    } else if (type == Parser.LABEL) {

                        writer.writeLabel(parser.arg1());

                    } else if (type == Parser.GOTO) {

                        writer.writeGoto(parser.arg1());

                    } else if (type == Parser.IF) {

                        writer.writeIf(parser.arg1());

                    } else if (type == Parser.RETURN) {

                        writer.writeReturn();

                    } else if (type == Parser.FUNCTION) {

                        writer.writeFunction(parser.arg1(),parser.arg2());

                    } else if (type == Parser.CALL) {

                        writer.writeCall(parser.arg1(),parser.arg2());

                    }

                }

            }

            //save file
            writer.close();

            System.out.println("File created : " + fileOutPath);
        }
    }

}
