package project;
import java.io.*;
import java.util.*;

public class Duplicate {
  public void runner(String filename) {
    Map<String, Integer> allLiteral = new HashMap<>();
    Map<String, Set<Integer>> duplicates = new HashMap<>();
    try {
      File file = new File(filename);
      FileReader fr = new FileReader(file);
      BufferedReader reader = new BufferedReader(fr);
      String line = reader.readLine();
      int lineNumber = 0;
      while (line != null) {
        Parser parser = new Parser(line);
        for (var pair : parser.tokenizer()) {
          tokenType curType = pair.getKey();
          String literal = pair.getValue();
          if (curType.equals(tokenType.ONE_QUOTE_LITERAL) || curType.equals(tokenType.TWO_QUOTE_LITERAL)) {
            if (allLiteral.containsKey(literal)) {
              if (duplicates.containsKey(literal)) {
                duplicates.get(literal).add(lineNumber);
              } else {
                Set<Integer> initSet = new HashSet<>();
                initSet.add(lineNumber);
                initSet.add(allLiteral.get(literal));
                duplicates.put(literal, initSet);
              }
            } else {
              allLiteral.put(literal, lineNumber);
            }
          }
        }
        lineNumber += 1;
        line = reader.readLine();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    for (String literal : duplicates.keySet()) {
      System.out.print("Lines with '" + literal + "' : ");
      int count = 0;
      for (Integer lineIndex : duplicates.get(literal)) {
        if (count < duplicates.size() - 1) {
          System.out.print(lineIndex + ", ");
        } else {
          System.out.println(lineIndex);
        }
        count += 1;
      }
    }
  }
}
