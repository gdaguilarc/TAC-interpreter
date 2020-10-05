import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

public class Main {
    public static void main(String[] args) {
        try {
            CharStream input = CharStreams.fromStream(System.in);
            TacLexer lexer = new TacLexer(input);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            TacParser parser = new TacParser(tokens);
            ParseTree tree = parser.prog();
            //System.out.println(tree.toStringTree(parser)); // Tree as text

            // Standard tree walk
            //ParseTreeWalker walker = new ParseTreeWalker();
            //walker.walk(new MiListener(), tree);

            // Custom tree walk
            EvalVisitor eval = new EvalVisitor();
            eval.visit(tree);
            System.out.println();
        }  
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
