How to compile the code

1.Go to folder src/ 

2.Open terminal in current path

3.Run command “javac CompilationEngine.java JackCompiler.java JackTokenizer.java Symbol.java SymbolTable.java VMWriter.java”(java is required to be installed)

4.After compiling, there will be a “CompilationEngine.class”, a “JackCompiler.class”, a “JackTokenizer.class”, a “Symbol.class”, a “SymbolTable.class” and a “VMWriter.class” file in current path

How to run the code

1.Command format is “java JackCompiler directory”.

Example:
    a.single file: java JackCompiler /home/xuc/system/Average/Main.jack
	
    If the argument is a directory, JackAnalyzer will generate a Main.xml(result of parsing the class) and a MainT.xml(result of tokenizing) in the same directory of Main.jack.

    And there will be output message in the terminal:
	File created : /home/xuc/system/Average/Main.vm


    b.directory: java JackCompiler /home/xuc/system/Square/

    If the argument is a directory, JackAnalyzer will generate several .xml files according to the number of .jack files named by filename.vm in the same directory.

    And there will be output message in the terminal:
	File created : /home/xuc/system/Square/Main.vm
	File created : /home/xuc/system/Square/Square.vm
	File created : /home/xuc/system/Square/SquareGame.vm

About Exceptions: 

1.all translation errors will be presented by throwing Exceptions. 

