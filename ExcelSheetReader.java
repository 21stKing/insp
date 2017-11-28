package inventory_system;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

//import Apache POI
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

/**
 *
 * @author qxf3984
 */
public class ExcelSheetReader
{
    public  List cellDataList;                                     // Create a new instance for cellDataList 
    /**
     * This constructor is added by me (qxf3984). Its purpose is to initialize 
     * a public List variable to be accessed from other classes
     */
    public ExcelSheetReader()
    {
        cellDataList = new ArrayList();  
  
    }

/**
* This method is used to read the data's from an excel file.
* @param fileName - Name of the excel file.
*/
    public void readExcelFile(String fileName)
    {
       // List cellDataList = new ArrayList();                                     // Create a new instance for cellDataList
        
        try
        {
         FileInputStream fileInputStream = new FileInputStream(fileName);        // Create a new instance for FileInputStream class
         POIFSFileSystem fsFileSystem = new POIFSFileSystem(fileInputStream);    //Create a new instance for POIFSFileSystem class
         HSSFWorkbook workBook = new HSSFWorkbook(fsFileSystem);                 //Create a new instance for HSSFWorkBook Class
         HSSFSheet hssfSheet = workBook.getSheetAt(0);

         Iterator rowIterator = hssfSheet.rowIterator();                         //Iterate the rows and cells of the spreadsheet to get all the datas.

         while (rowIterator.hasNext())
              {
                HSSFRow hssfRow = (HSSFRow) rowIterator.next();
                Iterator iterator = hssfRow.cellIterator();
                List cellTempList = new ArrayList();
                
                while (iterator.hasNext())
                      {
                        HSSFCell hssfCell = (HSSFCell) iterator.next();
                        cellTempList.add(hssfCell);
                      }         
                cellDataList.add(cellTempList);
               }
        }
        catch (Exception e){e.printStackTrace();}

       // printToConsole(cellDataList);                                            //Call the printToConsole method to print the cell data in the console.
    }

/**
* This method is used to print the cell data to the console.
* @param cellDataList - List of the data's in the spreadsheet.
*/
    private void printToConsole(List cellDataList)
    {
        for (int i = 0; i < cellDataList.size(); i++)
        {
            List cellTempList = (List) cellDataList.get(i);
            for (int j = 0; j < cellTempList.size(); j++)
            {
                HSSFCell hssfCell = (HSSFCell) cellTempList.get(j);
                String stringCellValue = hssfCell.toString();
                System.out.print(stringCellValue + "\t");
            }
            System.out.println();
        }
    }

/**public static void main(String[] args)
{
    String fileName = "C:" + File.separator + "Users" +
    File.separator + "Giftsam" + File.separator + "Desktop" +
    File.separator + "sampleexcel.xls";
    new ExcelSheetReader().readExcelFile(fileName);
}*/
}
