main: compile run
	

compile:
	javac -d bin/ Gui.java
	jar cvfm Reversi.jar manifest.txt -C bin .

run: compile
	java -jar Reversi.jar 

clean: 
	find . -type f -name "*.class" | xargs rm -f
	find . -type f -name "*.jar" | xargs rm -f


