package utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by freakazoid on 29/09/15.
 */
public class Participant {
    private int number;
    private long entrata, uscita;


    private String entrataString, uscitaString;

    public Participant(int number, long entrata, long uscita) {
        this.number = number;
        this.entrata = entrata;
        this.uscita = uscita;
    }

    public Participant(int number, String entrataString, String uscitaString){
        this.number = number;
        this.entrataString = entrataString;
        this.uscitaString = uscitaString;
    }

    public Participant() {

    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public long getEntrata() {
        return entrata;
    }

    public void setEntrata(long entrata) {
        this.entrata = entrata;
    }

    public long getUscita() {
        return uscita;
    }

    public void setUscita(long uscita) {
        this.uscita = uscita;
    }

    public String getEntrataString() {
        return entrataString;
    }

    public void setEntrataString(String entrataString) {
        this.entrataString = entrataString;
    }

    public String getUscitaString() {
        return uscitaString;
    }

    public void setUscitaString(String uscitaString) {
        this.uscitaString = uscitaString;
    }

    @Override
    public String toString(){
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(getEntrata() * 1000); //Because we stored it in seconds
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String entrata = sdf.format(c.getTime()).toString();
        c.setTimeInMillis(getUscita() * 1000); //Because we stored it in seconds
        String uscita = sdf.format(c.getTime()).toString();

        return Integer.toString(getNumber())+" Entrata: "+entrata+" Uscita: "+uscita;
    }
}