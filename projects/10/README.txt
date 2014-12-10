How to compile the code

1.Go to folder src/ 

2.Open terminal in current path

3.Run command “javac CompilationEngine.java JackAnalyzer.java JackTokenizer.java”
	      (java is required to be installed)

4.After compiling, there will be a “CompilationEngine.class”, a “JackAnalyzer.class” and a “JackTokenizer.class” file in current path

How to run the code

1.Command format is “java JackAnalyzer directory”.

Example:
    a.single file: java JackAnalyzer /home/xuc/system/ArrayTest/Main.jack
	
    If the argument is a directory, JackAnalyzer will generate a Main.xml(result of parsing the class) and a MainT.xml(result of tokenizing) in the same directory of Main.jack.

    And there will be output message in the terminal:
	File created : /home/xuc/system/ArrayTest/Main.xml
	File created : /home/xuc/system/ArrayTest/MainT.xml


    b.directory: java JackAnalyzer /home/xuc/system/Square/

    If the argument is a directory, JackAnalyzer will generate several .xml files according to the number of .jack files named by filename.xml(result of parsing the class) and a filenameT.xml(result of tokenizing) in the same directory.

    And there will be output message in the terminal:
	File created : /home/xuc/system/Square/Main.xml
	File created : /home/xuc/system/Square/MainT.xml
	File created : /home/xuc/system/Square/Square.xml
	File created : /home/xuc/system/Square/SquareT.xml
	File created : /home/xuc/system/Square/SquareGame.xml
	File created : /home/xuc/system/Square/SquareGameT.xml

About Exceptions: 

1.all translation errors will be presented by throwing Exceptions. 

