package no.hvl.dat100ptc.oppgave3;

import static java.lang.Math.*;

import no.hvl.dat100ptc.TODO;
import no.hvl.dat100ptc.oppgave1.GPSPoint;
import no.hvl.dat100ptc.oppgave2.GPSDataConverter;

public class GPSUtils {

	public static double findMax(double[] da) {

		double max; 
		
		max = da[0];
		
		for (double d : da) {
			if (d > max) {
				max = d;
			}
		}
		
		return max;
	}

	public static double findMin(double[] da) {

		double min;

		min = da[0];
		
		for(double d : da) {
			
			if(d < min) {
				
				min = d;
				
			}
			
		}
		
		return min;

	}

	public static double[] getLatitudes(GPSPoint[] gpspoints) {

		double[] lats = new double[gpspoints.length];
		
		for(int i = 0; i < lats.length; i++) {
			
			lats[i] = gpspoints[i].getLatitude();
			
		}
		
		return lats;
		
	}

	public static double[] getLongitudes(GPSPoint[] gpspoints) {

		double[] longs = new double[gpspoints.length];
		
		for(int i = 0; i < longs.length; i++) {
			
			longs[i] = gpspoints[i].getLongitude();
			
		}
		
		return longs;

	}

	private static int R = 6371000; // jordens radius

	public static double distance(GPSPoint gpspoint1, GPSPoint gpspoint2) {

		double d;
		double a;
		double c;
		double latitude1, longitude1, latitude2, longitude2;

		latitude1 = gpspoint1.getLatitude();
		longitude1 = gpspoint1.getLongitude();
		
		latitude2 = gpspoint2.getLatitude();
		longitude2 = gpspoint2.getLongitude();
		
		double latRad1 = toRadians(latitude1);
		double latRad2 = toRadians(latitude2);
		
		double deltaLat = toRadians(latitude2 - latitude1);
		double deltaLong = toRadians(longitude2 - longitude1);
		
		a = pow(sin(deltaLat/2),2) + cos(latRad1) * cos(latRad2) * pow(sin(deltaLong/2),2);
		c = 2 * atan2(sqrt(a),sqrt(1-a));
		d = R * c;
		
		return d;

	}

	public static double speed(GPSPoint gpspoint1, GPSPoint gpspoint2) {

		int secs;
		double speed;
		
		secs = gpspoint2.getTime() - gpspoint1.getTime();
		
		double d = distance(gpspoint1,gpspoint2);
		
		double mPerS = d/secs;
		speed = (mPerS * 3600) / 1000;
		
		return speed;

	}

	public static String formatTime(int secs) {

		String timestr;
		String TIMESEP = ":";
		
		System.out.println(secs);

		int hrs = secs / 3600;
		System.out.println(hrs);
		
		int mins = (secs % 3600) / 60;
		System.out.println(mins);
		
		int sec = (secs % 3600) % 60;
		System.out.println(sec);
		
		String hrsTxt = "" + hrs;
		String minsTxt = "" + mins;
		String secTxt = "" + sec;
		
		if(hrs < 10) {
			
			hrsTxt = 0 + "" + hrs;
			
		}
		if(mins < 10){
			
			minsTxt = 0 + "" + mins;
			
		}
		if(sec < 10) {
			
			secTxt = 0 + "" + sec;
			
		}
		
		timestr = hrsTxt + TIMESEP + minsTxt + TIMESEP + secTxt;
		timestr = String.format("%10s", timestr);
		
		return timestr;

	}

	public static String formatDouble(double d) {

		String str;

		double round = (double)((int)(d*100 + 0.5))/100;
		
		str = "" + round;
		
		str = String.format("%10s",str);
		
		return str;
		
	}
}
