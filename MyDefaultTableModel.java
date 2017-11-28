package inventory_system;

import javax.swing.table.DefaultTableModel;

public class MyDefaultTableModel extends DefaultTableModel
{
  private static final long serialVersionUID = 1L;
  private int indicat;

  public MyDefaultTableModel(int indicatin)
  {
    indicat = indicatin;
  }

  @Override
  public boolean isCellEditable(int row, int col)
  {
    switch (indicat) 
    {
        case 0:
        case 1:
        case 2:
        case 5:
        case 6:
          return false;
        case 3:
          return (col == 18) || (col == 19) || (col == 20) || (col == 21);
        case 4:
        return col == 12;
    }

    return false;
  }
}