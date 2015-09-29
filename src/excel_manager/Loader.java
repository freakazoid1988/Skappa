package excel_manager;

/**
 * Created by davem on 24/09/2015.
 */

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import utils.Participant;
import utils.Person;

import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

public class Loader {

    public static LinkedList<Person> generateList(String path){
        LinkedList<Person> attendees_list = new LinkedList<Person>();
        try{
            XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream(path));
            XSSFSheet sheet = wb.getSheetAt(0);
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

    public static HashMap<Integer, Participant> loadParticipants(File f){
        HashMap<Integer, Participant> participantHashMap = new HashMap<Integer, Participant>();

        String extension = "";
        String fileName = f.getName();

        int i = fileName.lastIndexOf('.');
        if (i > 0) {
            extension = fileName.substring(i+1).toLowerCase();
        }

        if(!extension.equals("txt")){
            System.err.println("Che cazzo di file m'hai dato?");
        }
        else{
            BufferedReader br;
            String linea = null;
            try {
                br = new BufferedReader(new FileReader(f));
                for(;;){
                    linea = br.readLine();
                    if(linea==null)
                        break;
                    String[] st = linea.split("\\s");
                    int number = Integer.parseInt(st[0]);
                    Participant p = new Participant(number, st[2], st[4]);
                    participantHashMap.put(number, p);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return participantHashMap;
    }

    public static void createOutput(HashMap<Integer, Person> attendee_number, String file) {
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
            Iterator<Map.Entry<Integer, Person>> it = attendee_number.entrySet().iterator();
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

    public static boolean writeInOut(String excelFile, HashMap<Integer, Participant> participantHashMap) {
        try {
            XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream(excelFile));
            XSSFSheet sheet = wb.getSheetAt(0);
            XSSFRow row;
            XSSFCell cell;

            int rows; // No of rows
            rows = sheet.getPhysicalNumberOfRows();

            int cols = 0; // No of columns
            int tmp = 0;

		    /* This trick ensures that we get the data properly even if it doesn't start from first few rows */
            for (int i = 0; i < 10 || i < rows; i++) {
                row = sheet.getRow(i);
                if (row != null) {
                    tmp = sheet.getRow(i).getPhysicalNumberOfCells();
                    if (tmp > cols) cols = tmp;
                }
            }

            for (int r = 1; r < rows; r++) {
                row = sheet.getRow(r);
                if (row != null) {
                    cell = row.getCell(3);
                    int cellValue = (int) cell.getNumericCellValue();
                    if (participantHashMap.containsKey(cellValue)) {
                        Participant p1 = participantHashMap.get(cellValue);
                        cell = row.getCell(4);
                        cell.setCellValue(p1.getEntrataString());
                        cell = row.getCell(5);
                        cell.setCellValue(p1.getUscitaString());
                    }
                }
            }

            FileOutputStream outputFile = new FileOutputStream(new File(excelFile));
            //write changes
            wb.write(outputFile);
            //close the stream
            outputFile.close();
            wb.close();

        } catch (IOException ioe) {
            ioe.printStackTrace();
            return false;
        }

        return true;
    }

}
