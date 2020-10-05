all: Tac.g4
	antlr4 -no-listener -visitor Tac.g4
	javac Tac*.java
	javac Main.java
	javac EvalVisitor.java

clean:
	rm -f *.class
	rm -f TacL*
	rm -f TacB*.java
	rm -f Tac.i*
	rm -f Tac.to*
	rm -f TacParser.java
	rm -f TacVisitor.java
