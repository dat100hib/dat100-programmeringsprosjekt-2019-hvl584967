package no.hvl.dat100ptc.oppgave2;

import no.hvl.dat100ptc.TODO;
import no.hvl.dat100ptc.oppgave1.GPSPoint;

import static java.lang.Integer.parseInt;
import static java.lang.Double.parseDouble;

public class GPSDataConverter {

	// konverter tidsinformasjon i gps data punkt til antall sekunder fra midnatt
	// dvs. ignorer information om dato og omregn tidspunkt til sekunder
	// Eksempel - tidsinformasjon (som String): 2017-08-13T08:52:26.000Z
    // skal omregnes til sekunder (som int): 8 * 60 * 60 + 52 * 60 + 26 
	
	private static int TIME_STARTINDEX = 11; // startindex for tidspunkt i timestr

	public static int toSeconds(String timestr) {
		
		int secs;
		int hr, min, sec;
		
		
		
		String hrtxt = timestr.charAt(11) + "" + timestr.charAt(12);
		String mintxt = timestr.charAt(14) + "" + timestr.charAt(15);
		String sectxt = timestr.charAt(17) + "" + timestr.charAt(18);
		
		hr = parseInt(hrtxt);
		min = parseInt(mintxt);
		sec = parseInt(sectxt);
		
		secs = (hr*3600) + (min*60) + sec;
		
		return secs;
		
	}

	public static GPSPoint convert(String timeStr, String latitudeStr, String longitudeStr, String elevationStr) {

		GPSPoint gpspoint;

		int sec = toSeconds(timeStr);
		
		double latitude = parseDouble(latitudeStr);
		
		double longitude = parseDouble(longitudeStr);
		
		double elevation = parseDouble(elevationStr);
		
		gpspoint = new GPSPoint(sec,latitude,longitude,elevation);
		
		return gpspoint;
	    
	}
	
}
