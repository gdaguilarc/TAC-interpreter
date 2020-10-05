import java.util.HashMap;
import java.util.Map;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.RuleContext;
import org.antlr.v4.runtime.tree.ParseTree;
import java.util.Set;
import java.util.ArrayList;

public class EvalVisitor extends TacBaseVisitor<Integer> {
    Map<String, Integer> memory = new HashMap<String, Integer>();
    public static Map<String, ArrayList<ParseTree>> label = 
        new HashMap<String, ArrayList<ParseTree>>();
    int mark = 1;

    @Override
    public Integer visitLabel(TacParser.LabelContext ctx) {
        //String label = ctx.ID().getText();
        // Every label is registered in Main
        return 0; // dummy return
    }

    @Override
    public Integer visitId(TacParser.IdContext ctx) {
        String id = ctx.ID().getText();
        if (memory.containsKey(id)) return memory.get(id);
        return 0;
    }

    @Override
    public Integer visitNum(TacParser.NumContext ctx) {
        return Integer.valueOf(ctx.NUM().getText());
    }

    @Override
    public Integer visitSet(TacParser.SetContext ctx) {
        String id = ctx.ID().getText();
        int value = visit(ctx.value());

        memory.put(id, value);
        return value;
    }

    @Override 
    public Integer visitSum(TacParser.SumContext ctx) {
        String id = ctx.ID().getText();
        int param1 = visit(ctx.value(0));
        int param2 = visit(ctx.value(1));
        int value = param1 + param2;

        memory.put(id, value);
        return value;
    }

    @Override 
    public Integer visitSub(TacParser.SubContext ctx) {
        String id = ctx.ID().getText();
        int param1 = visit(ctx.value(0));
        int param2 = visit(ctx.value(1));
        int value = param1 - param2;

        memory.put(id, value);
        return value;
    }

    @Override 
    public Integer visitMul(TacParser.MulContext ctx) {
        String id = ctx.ID().getText();
        int param1 = visit(ctx.value(0));
        int param2 = visit(ctx.value(1));
        int value = param1 * param2;

        memory.put(id, value);
        return value;
    }

    @Override
    public Integer visitDiv(TacParser.DivContext ctx) {
        String id = ctx.ID().getText();
        int param1 = visit(ctx.value(0));
        int param2 = visit(ctx.value(1));
        int value = param1 / param2;

        memory.put(id, value);
        return value;
    }

    @Override
    public Integer visitLe(TacParser.LeContext ctx) {
        String id = ctx.ID().getText();
        int param1 = visit(ctx.value(0));
        int param2 = visit(ctx.value(1));
        int value = (param1 < param2) ? 1 : 0;

        memory.put(id, value);
        return value;
    }

    @Override
    public Integer visitEq(TacParser.EqContext ctx) {
        String id = ctx.ID().getText();
        int param1 = visit(ctx.value(0));
        int param2 = visit(ctx.value(1));
        int value = (param1 == param2) ? 1 : 0;

        memory.put(id, value);
        return value;
    }

    @Override
    public Integer visitAnd(TacParser.AndContext ctx) {
        String id = ctx.ID().getText();
        int param1 = visit(ctx.value(0));
        int param2 = visit(ctx.value(1));
        int value = (param1 != 0 && param2 != 0 ) ? 1 : 0;

        memory.put(id, value);
        return value;
    }

    @Override
    public Integer visitOr(TacParser.OrContext ctx) {
        String id = ctx.ID().getText();
        int param1 = visit(ctx.value(0));
        int param2 = visit(ctx.value(1));
        int value = (param1 != 0 || param2 != 0 ) ? 1 : 0;

        memory.put(id, value);
        return value;
    }

    @Override
    public Integer visitGo(TacParser.GoContext ctx) {
        if (mark != 0) {
            mark = 0;
            ArrayList<ParseTree> tmp = label.get(ctx.ID().getText());
            for (ParseTree pt: tmp) {
                visit(pt);
            }
        }

        return 0;
    }

    @Override
    public Integer visitArrVar(TacParser.ArrVarContext ctx) {
        String base = ctx.ID().getText();
        int offset = visit(ctx.value(0));
        String id = base + String.valueOf(offset);   
        int value = visit(ctx.value(1));

        memory.put(id, value);
        return value;
    }

    @Override
    public Integer visitVarArr(TacParser.VarArrContext ctx) {
        String id = ctx.ID(0).getText();
        String base = ctx.ID(1).getText();
        int offset = visit(ctx.value());
        String arrkey = base + String.valueOf(offset);   
        int value;
        
        if (memory.containsKey(arrkey)) {
            value = memory.get(arrkey);
        }
        else {
            value = 0;
        }

        memory.put(id, value);
        return value;
    }

   @Override
    public Integer visitPrint(TacParser.PrintContext ctx) {
        System.out.printf("%d\n", visit(ctx.value()));
        return 0; // dummy return
    }
} 
