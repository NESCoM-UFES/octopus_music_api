package  octopus.gui;


import javax.swing.border.Border;


/**
 * @version 1.0 03/06/99
 */
public interface CellBorder {

  public Border getBorder();
  public Border getBorder(int row, int column);

  public void setBorder(Border border);
  public void setBorder(Border border, int row, int column);

}
