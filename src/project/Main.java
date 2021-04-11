package project;
public class Main {
  public static void main(String[] args) {
    if (args.length != 1) {
      throw new IllegalArgumentException("you need exactly one argument");
    }
    Duplicate cls = new Duplicate();
    cls.runner(args[0]);
  }
}
