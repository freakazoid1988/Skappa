package event;

/**
 * Created by davem on 24/09/2015.
 */

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import utils.Person;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.Map.Entry;

public class Event {

    GregorianCalendar date;
    private HashMap<Integer, Person> attendee_number;
    private HashMap<Integer, BufferedImage> attendee_qr;
    private LinkedList<Person> attendees;
    private String name, location, province;

    public Event(String name, String location, String province, GregorianCalendar date, LinkedList<Person> attendees){
        this.attendees = attendees;
        attendee_number = new HashMap<Integer, Person>();
        attendee_qr = new HashMap<Integer, BufferedImage>();
        this.name = name;
        this.location = location;
        this.province = province;
        this.date = date;
    }

    public HashMap<Integer, Person> getAttendee_number() {
        return attendee_number;
    }

    public void assignNumber(){
        int randomCode = (int)(Math.random()*10000);
        for(Person p:attendees){

			/* Let's generate a random number and make sure it's unique by checking whether it's already present in the Map */
            while(attendee_number.containsKey(randomCode)){
                randomCode = (int)(Math.random()*10000);
            }

			/* Finally we add the pair <Random number, Person> to the Map */
            attendee_number.put(randomCode, p);
        }
    }

    public LinkedList<Person> generateList(String path) {
        LinkedList<Person> attendees_list = new LinkedList<Person>();
        try {
            XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream(path));
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
            
            /* We set the titls of the cell */
            row = sheet.getRow(0);
            cell = row.createCell(4);
            cell.setCellValue("Numero assegnato");
            cell = row.createCell(5);
            cell.setCellValue("Ora entrata");
            cell = row.createCell(6);
            cell.setCellValue("Ora uscita");


            for (int r = 1; r < rows; r++) {
                row = sheet.getRow(r);
                if (row != null) {

		        	/* We create the object */
                    Person p = new Person();

                    boolean isEmpty = false;
                    
		        	/* Now we fill in all the values */
                    inner:
                    for (int c = 0; c < 5; c++) {
                        cell = row.getCell(c, XSSFRow.RETURN_BLANK_AS_NULL);
                        if (cell == null && c == 0) {
                            break inner;
                        }
                        if (cell != null && c == 0) {
                            p.setID((int) cell.getNumericCellValue());
                        }
                        if (cell != null && c == 1) {
                            p.setName(cell.getStringCellValue());
                        }
                        if (cell != null && c == 2) {
                            p.setSurname(cell.getStringCellValue());
                        }
                        if (cell != null && c == 3) {
                            p.setEmail(cell.getStringCellValue());
                        }
                        if (c == 4) {
                            row.createCell(c);
                            int randomCode = (int) (Math.random() * 10000);

                        	/* Let's generate a random number and make sure it's unique by checking whether it's already present in the Map */
                            while (attendee_number.containsKey(randomCode)) {
                                randomCode = (int) (Math.random() * 10000);
                            }
                            
                            /* We write it into the cell */
                            cell = row.getCell(c);
                            cell.setCellValue(randomCode);

                			/* Finally we add the pair <Random number, Person> to the Map */
                            attendee_number.put(randomCode, p);

                        }

                    }

		            /* Finally we add it to the list */
                    attendees_list.add(p);
                }
            }

            FileOutputStream outputFile = new FileOutputStream(new File(path));
            //write changes
            wb.write(outputFile);
            //close the stream
            outputFile.close();
            wb.close();

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        return attendees_list;
    }

    public void assignQR(String path) {
        int size = 500;
        String fileType = "png";
        Iterator<Entry<Integer, Person>> it = attendee_number.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            try {
                Hashtable<EncodeHintType, ErrorCorrectionLevel> hintMap = new Hashtable<EncodeHintType, ErrorCorrectionLevel>();
                hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
                QRCodeWriter qrCodeWriter = new QRCodeWriter();
                BitMatrix byteMatrix = qrCodeWriter.encode(String.valueOf(pair.getKey()),BarcodeFormat.QR_CODE, size, size, hintMap);
                int CrunchifyWidth = byteMatrix.getWidth();
                BufferedImage image = new BufferedImage(CrunchifyWidth, CrunchifyWidth,
                        BufferedImage.TYPE_INT_RGB);
                image.createGraphics();

	            /*We add it to the HashTable*/
                attendee_qr.put((Integer) pair.getKey(), image);


                Graphics2D graphics = (Graphics2D) image.getGraphics();
                graphics.setColor(Color.WHITE);
                graphics.fillRect(0, 0, CrunchifyWidth, CrunchifyWidth);
                graphics.setColor(Color.BLACK);

                for (int i = 0; i < CrunchifyWidth; i++) {
                    for (int j = 0; j < CrunchifyWidth; j++) {
                        if (byteMatrix.get(i, j)) {
                            graphics.fillRect(i, j, 1, 1);
                        }
                    }
                }
                //String home = System.getProperty("user.home");

                File QRDir = new File(path + "/QRs/");
                if(!QRDir.exists()){
                    QRDir.mkdirs();
                }
                String filePath = QRDir+"/"+String.valueOf(pair.getKey())+".png";
                File myFile = new File(filePath);
                ImageIO.write(image, fileType, myFile);
            } catch (WriterException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            //it.remove();
        }
    }

    public HashMap<Integer, Person> getAttendeeNumber(){
        return attendee_number;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public String getLocation() {
        return location;
    }


    public void setLocation(String location) {
        this.location = location;
    }


    public String getProvince() {
        return province;
    }


    public void setProvince(String province) {
        this.province = province;
    }


    public GregorianCalendar getDate() {
        return date;
    }


    public void setDate(GregorianCalendar date) {
        this.date = date;
    }


    /*
     * A quite trivial and obscene toString(), for testing purposes only :)
     *
     */
    @Override
    public String toString(){
        String s="";
        Iterator<Entry<Integer, Person>> it = attendee_number.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            System.out.println(pair.getKey() + " = " + pair.getValue());
            //it.remove(); // avoids a ConcurrentModificationException
        }
        return s;
    }
}
