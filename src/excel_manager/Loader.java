package excel_manager;

/**
 * Created by davem on 24/09/2015.
 */
import java.io.FileInputStream;
import java.io.IOException;
import java.util.LinkedList;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import utils.Person;

public class Loader {

    public static LinkedList<Person> generateList(String path){
        LinkedList<Person> attendees_list = new LinkedList<Person>();
        try{
            XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream(path));
            XSSFSheet sheet = (XSSFSheet) wb.getSheetAt(0);
            XSSFRow row;
            XSSFCell cell;

            int rows; // No of rows
            rows = sheet.getPhysicalNumberOfRows();

            int cols = 0; // No of columns
            int tmp = 0;

		    /* This trick ensures that we get the data properly even if it doesn't start from first few rows */
            for(int i = 0; i < 10 || i < rows; i++) {
                row = sheet.getRow(i);
                if(row != null) {
                    tmp = sheet.getRow(i).getPhysicalNumberOfCells();
                    if(tmp > cols) cols = tmp;
                }
            }

            for(int r = 1; r < rows; r++) {
                row = sheet.getRow(r);
                if(row != null) {

		        	/* We create the object */
                    Person p = new Person();

		        	/* Now we fill in all the values */
                    for(int c = 0; c < cols; c++) {
                        cell = row.getCell(c);
                        if(cell != null && c==0) {
                            p.setID((int)cell.getNumericCellValue());
                        }
                        if(cell != null && c==1){
                            p.setName(cell.getStringCellValue());
                        }
                        if(cell != null && c==2){
                            p.setSurname(cell.getStringCellValue());
                        }
                        if(cell != null && c==3){
                            p.setEmail(cell.getStringCellValue());
                        }
                    }

		            /* Finally we add it to the list */
                    attendees_list.add(p);
                }
            }

            wb.close();

        }catch(IOException ioe)
        {
            ioe.printStackTrace();
        }

        return attendees_list;
    }



}
