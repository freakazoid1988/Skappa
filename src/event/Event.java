package event;

/**
 * Created by davem on 24/09/2015.
 */

        import java.awt.Color;
        import java.awt.Graphics2D;
        import java.awt.image.BufferedImage;
        import java.io.File;
        import java.io.IOException;
        import java.util.Date;
        import java.util.GregorianCalendar;
        import java.util.HashMap;
        import java.util.Hashtable;
        import java.util.Iterator;
        import java.util.LinkedList;
        import java.util.Map;
        import java.util.Map.Entry;

        import javax.imageio.ImageIO;

        import com.google.zxing.BarcodeFormat;
        import com.google.zxing.EncodeHintType;
        import com.google.zxing.WriterException;
        import com.google.zxing.common.BitMatrix;
        import com.google.zxing.qrcode.QRCodeWriter;
        import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

        import utils.Person;

public class Event {

    private HashMap<Integer, Person> attendee_number;
    public HashMap<Integer, Person> getAttendee_number() {
        return attendee_number;
    }


    private HashMap<Integer, BufferedImage> attendee_qr;
    private LinkedList<Person> attendees;
    private String name, location, province;
    GregorianCalendar date;

    public Event(String name, String location, String province, GregorianCalendar date, LinkedList<Person> attendees){
        this.attendees = attendees;
        attendee_number = new HashMap<Integer, Person>();
        attendee_qr = new HashMap<Integer, BufferedImage>();
        this.name = name;
        this.location = location;
        this.province = province;
        this.date = date;
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

    public void assignQR(){
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
                String filePath = "C:\\QRs\\"+String.valueOf(pair.getKey())+".png";
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
