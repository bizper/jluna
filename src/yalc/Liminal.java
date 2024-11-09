package yalc;

import compiler.components.models.Token;
import yalc.d.Pattern;
import yalc.d.SyntaxStatus;

import java.util.List;

import static yalc.d.Pattern.getPattern;
import static yalc.d.SyntaxStatus.*;

public class Liminal {

    private int _i = 0;
    private List<Token> list;
    private SyntaxStatus status = UNDETERMINED;
    private Builder builder = Builder.build();

    private Liminal(List<Token> list) {
        this.list = list;
    }

    private static Liminal getInstance(List<Token> list) {
        return new Liminal(list);
    }

    /**
     * yet another luna compiler
     */
    public void yalc() {
        this.status = SUCCESS;
    }

    public SyntaxStatus getStatus() {
        return status;
    }

    public void defines() {

    }

    public void expressions() {

    }



}