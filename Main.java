import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.Set;

public class Main {
    public static int ip = 0;
    public static ArrayList<ParseTree> instruction = new ArrayList<ParseTree>();

    public static void updateVisit(int pos) {
        ip = pos - 1; // There is a ip++ in main
    }

    public static void main(String[] args) {
        Pattern pattern = Pattern.compile("label ([a-zA-Z0-9_]*) :");

        try {
            CharStream input = CharStreams.fromStream(System.in);
            TacLexer lexer = new TacLexer(input);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            TacParser parser = new TacParser(tokens);
            ParseTree tree = parser.prog();
            //System.out.println(tree.toStringTree(parser)); // Tree as text

            // Custom tree walk
            EvalVisitor eval = new EvalVisitor();

            // Storing labels and its instruction
            for (int i = 0; i < tree.getChildCount(); i++) {
                String line = tree.getChild(i).toStringTree(parser);
                Matcher m = pattern.matcher(line);
                
                if (m.find()) {
                    eval.label.put(m.group(1), i);
                }

                instruction.add(tree.getChild(i));
            }

            /* // Shows which instructions has each label 
            Set<String> keys = eval.label.keySet();
            for (String key: keys) {
                System.out.printf("========%s========\n", key);
                for (int i = eval.label.get(key); i < instruction.size(); i++) {
                    ParseTree pt = instruction.get(i);
                    System.out.println(pt.toStringTree(parser));
                }
            }
            */

            // Manual visit using instruction arraylist
            while (ip < instruction.size()) {
                ParseTree pt = instruction.get(ip);
                eval.visit(pt);
                ip++;
            }
            System.out.println();
        }  
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
