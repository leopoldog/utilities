package net.ghielmetti.utilities;

public interface Indentable {
  Indenter appendTo(Indenter inIndenter);

  int getPriority();

  void setPriority(int inPriority);
}
