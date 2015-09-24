package excel_manager;

/**
 * Created by davem on 24/09/2015.
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import utils.Person;

/**
 * @author Gaetano Belcastro
 *
 */

public class ExcelGenerator {

    private HashMap<Integer, Person> attendee_number;

    public ExcelGenerator(HashMap<Integer, Person> attendee_number){
        this.attendee_number = attendee_number;
    }

    public void create(String file){
        try {

            // Let's instantiate some useful things we'll be using later
            FileOutputStream out = new FileOutputStream(new File(file));
            XSSFWorkbook wb = new XSSFWorkbook();
            Sheet s = wb.createSheet();
            Row r = null;
            Cell c = null;
            int rownum = attendee_number.size()+1;
            String cellValue;

            Person p = null;
            Integer number = null;


			/*Can we do this in a cleaner way? Of course we can, but we would need two more for loops.
			 We definitely have to evaluate this one.
			*/
            Iterator<Entry<Integer, Person>> it = attendee_number.entrySet().iterator();
            for(short i = 0; i < rownum; i++){
                r = s.createRow(i);
                if(i>0 && it.hasNext()){
                    Map.Entry pair = (Map.Entry)it.next();
                    p = (Person) pair.getValue();
                    number = (Integer)pair.getKey();
                }

                for(short j = 0; j < 6; j++){
                    c = r.createCell(j);
                    if(i==0){
                        switch(j){
                            case 0: cellValue = "ID";
                                c.setCellValue(cellValue);
                                break;
                            case 1: cellValue = "Nome";
                                c.setCellValue(cellValue);
                                break;
                            case 2: cellValue = "Cognome";
                                c.setCellValue(cellValue);
                                break;
                            case 3: cellValue = "Numero assegnato";
                                c.setCellValue(cellValue);
                                break;
                            case 4: cellValue = "Ora entrata";
                                c.setCellValue(cellValue);
                                break;
                            case 5: cellValue = "Ora uscita";
                                c.setCellValue(cellValue);
                                break;
                        }
                    }
                    else{
                        switch(j){
                            case 0: c.setCellValue(p.getID());
                                break;
                            case 1: c.setCellValue(p.getName());
                                break;
                            case 2: c.setCellValue(p.getSurname());
                                break;
                            case 3: c.setCellValue(number);
                                break;
                        }
                    }
                }
            }

            try {
                wb.write(out);
                out.close();
                wb.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
