package SyntaxAnalyzer;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by xuchen on 11/17/14.
 * top-level driver that sets up and invokes the other modules
 *
 * The analyzer program operates on a given source, where source is either a file name of the form Xxx.jack or a directory name containing one or more such files.
 * For each source Xxx.jack file, the analyzer goes through the following logic:
 * 1. Create a JackTokenizer from the Xxx.jack input file.
 * 2. Create an output file called Xxx.xml and prepare it for writing.
 * 3. Use the CompilationEngine to compile the input JackTokenizer into the output file.”
 */
public class JackAnalyzer {

    /**
     * Return all the .jack files in a directory
     * @param dir
     * @return
     */
    public static ArrayList<File> getJackFiles(File dir){

        File[] files = dir.listFiles();

        ArrayList<File> result = new ArrayList<File>();

        if (files == null) return result;

        for (File f:files){

            if (f.getName().endsWith(".jack")){

                result.add(f);

            }

        }

        return result;

    }

    public static void main(String[] args) {

        if (args.length != 1){

            System.out.println("Usage:java JackAnalyzer [filename|directory]");

        }else {

            String fileInName = args[0];

            File fileIn = new File(fileInName);

            String fileOutPath = "", tokenFileOutPath = "";

            File fileOut,tokenFileOut;

            ArrayList<File> jackFiles = new ArrayList<File>();

            if (fileIn.isFile()) {

                //if it is a single file, see whether it is a vm file
                String path = fileIn.getAbsolutePath();

                if (!path.endsWith(".jack")) {

                    throw new IllegalArgumentException(".jack file is required!");

                }

                jackFiles.add(fileIn);

            } else if (fileIn.isDirectory()) {

                //if it is a directory get all jack files under this directory
                jackFiles = getJackFiles(fileIn);

                //if no vn file in this directory
                if (jackFiles.size() == 0) {

                    throw new IllegalArgumentException("No jack file in this directory");

                }

            }

            for (File f: jackFiles) {

                fileOutPath = f.getAbsolutePath().substring(0, f.getAbsolutePath().lastIndexOf(".")) + ".xml";
                tokenFileOutPath = f.getAbsolutePath().substring(0, f.getAbsolutePath().lastIndexOf(".")) + "T.xml";
                fileOut = new File(fileOutPath);
                tokenFileOut = new File(tokenFileOutPath);

                CompilationEngine compilationEngine = new CompilationEngine(f,fileOut,tokenFileOut);
                compilationEngine.compileClass();

                System.out.println("File created : " + fileOutPath);
                System.out.println("File created : " + tokenFileOutPath);
            }

        }

    }
}
