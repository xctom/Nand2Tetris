How to compile the code

1.Go to folder src/ 

2.Open terminal in current path

3.Run command “javac CodeWriter.java”
	      “javac Parser.java”
	      “javac VMtranslator.java”
	      (java is required to be installed)

4.After compiling, there will be a “CodeWriter.class”, a “Parser.class” and a “VMtranslator.class” file in current path

How to run the code

1.Command format is “java VMtranslator directory”.

Example:
    a.java VMtranslator /home/xuc/system/VMtranslator
	
    If the argument is a directory, after translation finishing, there will be a VMtranslator.asm file which is named by the directory name and this file will be in this directory.

    And there will be a printed message in the terminal: “File created : /home/xuc/system/VMtranslator/VMtranslator.asm”

    b.java VMtranslator /home/xuc/system/VMtranslator/StaticTest.vm

    If the argument is a file, after translation finishing, there will be a StaticTest.asm file which is named by the file name and this file will be in the same directory as the vm file.

    And there will be a printed message in the terminal: “File created : /home/xuc/system/VMtranslator/StaticTest.asm”

About Exceptions: 

1.all translation errors will be presented by throwing Exceptions. 

	