MiniJavaCompiler
=================

A compiler for [Mini Java](http://www.cambridge.org/resources/052182060X/MCIIJ2e/grammar.htm).

The compiler parses Minijava source code, generate the parse tree, and reports syntax and semantic errors.


Build && Run
-----

Building the tool from source requires the [Antlr4 java library](http://www.antlr.org/api/Java/).  

0.	Clone the project from the git [repository](https://github.com/jizh0u/MiniJavaCompilers.git)
1.	Download [Antlr4](http://www.antlr.org/download/antlr-4.6-complete.jar)
2.	Unzip antlr jar to your path.  
3.	Generate the lexer and parser by feeding Antlr4 the [MiniJava grammar](https://github.com/jizh0u/MiniJavaCompilers/blob/master/MiniJava.g4).

	`java -jar /path/to/antlr-4.6-complete.jar -visitor MiniJava.g4`

4.	Compile with the java 1.8 compiler

	`javac *.java`

5.	Run with test.java
	`java -classpath "/path/to/antlr-4.6-complete.jar" MiniJavaMain test.java`
    