package main;

/**
 * Created by davem on 24/09/2015.
 */
import java.io.File;
import java.util.GregorianCalendar;
import java.util.LinkedList;

import event.Event;
import excel_manager.Loader;
import utils.Person;

public class Main {

    public static void main(String[] args) {
        String path = args[0];
        String path2 = args[1];
        String path3 = args[2];

        LinkedList<Person> l = Loader.generateList(path);

        for(Person p:l){
            System.out.println(p.toString());
        }

        Event ev = new Event("Birra al pub", "Cotronei", "KR", new GregorianCalendar(2015, 8, 26, 21, 30), l);
        ev.assignNumber();
        ev.toString();
        ev.assignQR();

        Loader.create(ev.getAttendee_number(), path2);

        // Temporarily comment
        //Mailer mailer = new Mailer(ev);
        //mailer.send();

        File f = new File(path3);
        Loader.loadParticipants(f);


    }

}
