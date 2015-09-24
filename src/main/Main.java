package main;

/**
 * Created by davem on 24/09/2015.
 */
import java.util.GregorianCalendar;
import java.util.LinkedList;

import event.Event;
import excel_manager.ExcelGenerator;
import excel_manager.Loader;
import utils.Mailer;
import utils.Person;

public class Main {

    public static void main(String[] args) {
        String path = args[0];
        String path2 = args[1];

        LinkedList<Person> l = Loader.generateList(path);

        for(Person p:l){
            System.out.println(p.toString());
        }

        Event ev = new Event("Birra al pub", "Cotronei", "KR", new GregorianCalendar(2015, 8, 26, 21, 30), l);
        ev.assignNumber();
        ev.toString();
        ev.assignQR();

        ExcelGenerator eg = new ExcelGenerator(ev.getAttendee_number());
        eg.create(path2);


        // Temporarily comment
        Mailer mailer = new Mailer(ev);
        mailer.send();
    }

}
