package project;
import java.util.ArrayList;
import java.util.List;

public class Parser {
  private final String parseString;

  public Parser(String line) {
    parseString = line;
  }

  public List<Pair<tokenType, String>> tokenizer() {
    List<Pair<tokenType, String>> tokens = new ArrayList<>();
    StringBuilder buffer = new StringBuilder();
    tokenType currentType = null;
    for (int i = 0; i < parseString.length(); i++) {
      if (parseString.charAt(i) == '\'') { // <- если встретили одинарную ковычку
        if (currentType == null) {
          currentType = tokenType.ONE_QUOTE_LITERAL;
        } else if (currentType.equals(tokenType.ONE_QUOTE_LITERAL)) {// встретили закрывающуюся скобку
          tokens.add(new Pair<>(tokenType.ONE_QUOTE_LITERAL, buffer.toString()));
          buffer = new StringBuilder();
          currentType = null; // обнулили тип, обулили буфер, закинули строковый литерал
        } else if (currentType.equals(tokenType.TWO_QUOTE_LITERAL)) { // "-это часть строки 'dfd"dfd'
          buffer.append('\''); //просто добавили к буфферу
        } else if (currentType.equals(tokenType.WORD)) { // было слово, значит началась строка
          tokens.add(new Pair<>(tokenType.WORD, buffer.toString()));
          buffer = new StringBuilder();
          currentType = tokenType.ONE_QUOTE_LITERAL; //пушим слово, меняем тип на строковый литерал, обнуляем буффер
        } else { // если же у нас до этого был пробел
          buffer = new StringBuilder();
          currentType = tokenType.ONE_QUOTE_LITERAL;
          tokens.add(new Pair<>(tokenType.SPACE, " ")); //значит начилась строка, закинули пробел и обулили буффер
        }
      } else if (parseString.charAt(i) == '"') { // <- если встретили двойную кавычку
        if (currentType == null) { // если типа нет, то просто ставим тип строки
          currentType = tokenType.TWO_QUOTE_LITERAL;
        } else if (currentType.equals(tokenType.ONE_QUOTE_LITERAL)) { // если сейчас уже есть строка, то это просто символ
          buffer.append('"'); // например ' fgfg " ffg'
        } else if (currentType.equals(tokenType.TWO_QUOTE_LITERAL)) { // закрывающая скобочка
          tokens.add(new Pair<>(tokenType.TWO_QUOTE_LITERAL, buffer.toString()));
          buffer = new StringBuilder();
          currentType = null; // запушили литерал, обнулили буффер, сбросили тип
        } else if (currentType.equals(tokenType.SPACE)) {// был пробел
          currentType = tokenType.TWO_QUOTE_LITERAL;
          buffer = new StringBuilder();
          tokens.add(new Pair<>(tokenType.SPACE, " ")); // сменили тип на строку, запушили пробел
        } else { // если же было слово, то запушили слово и начали строку
          tokens.add(new Pair<>(tokenType.WORD, buffer.toString()));
          currentType = tokenType.TWO_QUOTE_LITERAL;
          buffer = new StringBuilder();
        }
      } else if (parseString.charAt(i) == ' ') { //<- если встретили пробел
        if (currentType == null) {// теперь тип пробела
          currentType = tokenType.SPACE;
        } else if (currentType.equals(tokenType.WORD)) { // было слово, значит оно закончилось
          tokens.add(new Pair<>(tokenType.WORD, buffer.toString()));
          currentType = tokenType.SPACE;
          buffer = new StringBuilder(); // запушили слово, обнулили буффер, сменили тип
        } else if (currentType.equals(tokenType.TWO_QUOTE_LITERAL) || currentType.equals(tokenType.ONE_QUOTE_LITERAL)) {
          buffer.append(' '); // иначе просто добавили
        }
      } else { // любой символ кроме _ ,' ,"
        if (currentType == null || currentType.equals(tokenType.SPACE)) {
          buffer.append(parseString.charAt(i));
          currentType = tokenType.WORD;
        } else {
          buffer.append(parseString.charAt(i));
        }
      }
    }
    if (buffer.length() != 0) {
      tokens.add(new Pair<>(currentType, buffer.toString()));
    }
    return tokens;
  }

}
