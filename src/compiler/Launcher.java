package compiler;

import compiler.components.Parser;
import compiler.components.Scanner;
import compiler.components.models.*;
import vm.Runtime;
import yalc.Builder;

import java.io.*;

import static compiler.components.Tools.*;

public class Launcher {

    public static void main(String[] args) {
        Builder.build();
        File file = new File("./test.luna");
        Scanner scanner = Scanner.init(file.getName().split("\\.")[0]);
        try(BufferedReader fr = new BufferedReader(new FileReader(file))) {
            int line = 1;
            while(fr.ready()) {
                scanner.load(line ++, fr.readLine());
            }
            Cage cage = scanner.getCage();
            System.out.println(cage);
            System.out.println(cage.getSymbols());
            System.out.println(cage.getTokens());
            System.out.println(readableString(cage.getTokens(), cage.getSymbols()));
            Parser.init(cage).parse().getCage().getRoot().printTree();
            Runtime.load(cage);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
