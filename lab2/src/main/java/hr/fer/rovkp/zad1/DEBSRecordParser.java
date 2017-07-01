package hr.fer.rovkp.zad1;

/**
 * Created by Igor Farszky on 3.4.2017..
 */
public class DEBSRecordParser {

    private String input;
    private String medallion;
    private int trip_time;
    private double pickup_long;
    private double pickup_lat;
    private double drop_long;
    private double drop_lat;
    private int putnici;

    public void parse(String record){

        String[] splitted = record.split(",");

        this.input = record;
        this.medallion = splitted[0];
        this.trip_time = Integer.parseInt(splitted[8]);
        this.pickup_long = Double.parseDouble(splitted[10]);
        this.pickup_lat = Double.parseDouble(splitted[11]);
        this.drop_long = Double.parseDouble(splitted[12]);
        this.drop_lat = Double.parseDouble(splitted[13]);
        this.putnici = Integer.parseInt(splitted[7]);

    }

    public String getMedallion() {
        return medallion;
    }

    public int getTrip_time() {
        return trip_time;
    }

    public double getPickup_long() {
        return pickup_long;
    }

    public double getPickup_lat() {
        return pickup_lat;
    }

    public double getDrop_long() {
        return drop_long;
    }

    public double getDrop_lat() {
        return drop_lat;
    }

    public int getPutnici() {
        return putnici;
    }

    public String getInput() {
        return input;
    }
}
