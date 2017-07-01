/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.fer.tel.rovkp.topic9.example4;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

/**
 *
 * @author Krešimir Pripužić <kresimir.pripuzic@fer.hr>
 */
public class DEBSFullRecord {

    private final String medallion;
    private final String hackLicense;
    private final Date pickup;
    private final Date dropoff;
    private final int duration;
    private final double distance;
    private final double pickupLongitude;
    private final double pickupLatitude;
    private final double dropoffLongitude;
    private final double dropoffLatitude;
    private final String paymentType;
    private final double fare;
    private final double surcharge;
    private final double tax;
    private final double tip;
    private final double tolls;
    private final double total;

    public static final Comparator<DEBSFullRecord> FARE_COMPARATOR
            = (r1, r2) -> Double.compare(r1.getFare(), r2.getFare());
    public static final Comparator<DEBSFullRecord> HASH_COMPARATOR
            = (r1, r2) -> Integer.compare(r1.hashCode(), r2.hashCode());

    private static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    public DEBSFullRecord(String record) throws Exception {
        String[] splitted = record.split(",");
        medallion = splitted[0];
        hackLicense = splitted[1];
        pickup = DATE_FORMATTER.parse(splitted[2]);
        dropoff = DATE_FORMATTER.parse(splitted[3]);
        duration = Integer.parseInt(splitted[4]);
        distance = Double.parseDouble(splitted[5]);
        pickupLongitude = Double.parseDouble(splitted[6]);
        pickupLatitude = Double.parseDouble(splitted[7]);
        dropoffLongitude = Double.parseDouble(splitted[8]);
        dropoffLatitude = Double.parseDouble(splitted[9]);
        paymentType = splitted[10];
        fare = Double.parseDouble(splitted[11]);
        surcharge = Double.parseDouble(splitted[12]);
        tax = Double.parseDouble(splitted[13]);
        tip = Double.parseDouble(splitted[14]);
        tolls = Double.parseDouble(splitted[15]);
        total = Double.parseDouble(splitted[16]);
    }

    public String getMedallion() {
        return medallion;
    }

    public String getHackLicense() {
        return hackLicense;
    }

    public Date getPickup() {
        return pickup;
    }

    public Date getDropoff() {
        return dropoff;
    }

    public int getDuration() {
        return duration;
    }

    public double getDistance() {
        return distance;
    }

    public double getPickupLongitude() {
        return pickupLongitude;
    }

    public double getPickupLatitude() {
        return pickupLatitude;
    }

    public double getDropoffLongitude() {
        return dropoffLongitude;
    }

    public double getDropoffLatitude() {
        return dropoffLatitude;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public double getFare() {
        return fare;
    }

    public double getSurcharge() {
        return surcharge;
    }

    public double getTax() {
        return tax;
    }

    public double getTip() {
        return tip;
    }

    public double getTolls() {
        return tolls;
    }

    public double getTotal() {
        return total;
    }

    public static boolean isParsable(String line) {
        try {
            String[] splitted = line.split(",");
            DATE_FORMATTER.parse(splitted[2]);
            DATE_FORMATTER.parse(splitted[3]);
            Integer.parseInt(splitted[4]);
            Double.parseDouble(splitted[5]);
            Double.parseDouble(splitted[6]);
            Double.parseDouble(splitted[7]);
            Double.parseDouble(splitted[8]);
            Double.parseDouble(splitted[9]);
            Double.parseDouble(splitted[11]);
            Double.parseDouble(splitted[12]);
            Double.parseDouble(splitted[13]);
            Double.parseDouble(splitted[14]);
            Double.parseDouble(splitted[15]);
            Double.parseDouble(splitted[16]);
            return true;
        } catch (ParseException | NumberFormatException ex) {
            return false;
        }
    }
}
