package no.hvl.dat100ptc.oppgave5;

import javax.swing.JOptionPane;

import easygraphics.EasyGraphics;
import no.hvl.dat100ptc.TODO;
import no.hvl.dat100ptc.oppgave1.GPSPoint;
import no.hvl.dat100ptc.oppgave3.GPSUtils;
import no.hvl.dat100ptc.oppgave4.GPSComputer;

public class ShowRoute extends EasyGraphics {

	private static int MARGIN = 50;
	private static int MAPXSIZE = 800;
	private static int MAPYSIZE = 800;
	final int AREA = 600;

	private GPSPoint[] gpspoints;
	private GPSComputer gpscomputer;
	
	public ShowRoute() {

		String filename = JOptionPane.showInputDialog("GPS data filnavn: ");
		gpscomputer = new GPSComputer(filename);

		gpspoints = gpscomputer.getGPSPoints();

	}

	public static void main(String[] args) {
		launch(args);
	}

	public void run() {

		makeWindow("Route", MAPXSIZE + 2 * MARGIN, MAPYSIZE + 2 * MARGIN);

		showRouteMap(MARGIN + MAPYSIZE);

		playRoute(MARGIN + MAPYSIZE);
		
		showStatistics();
	}

	// antall x-pixels per lengdegrad
	public double xstep() {

		double maxlon = GPSUtils.findMax(GPSUtils.getLongitudes(gpspoints));
		double minlon = GPSUtils.findMin(GPSUtils.getLongitudes(gpspoints));

		double xstep = MAPXSIZE / (Math.abs(maxlon - minlon));

		return xstep;
	}

	// antall y-pixels per breddegrad
	public double ystep() {
	
		double maxlat = GPSUtils.findMax(GPSUtils.getLatitudes(gpspoints));
		double minlat = GPSUtils.findMin(GPSUtils.getLatitudes(gpspoints));
		
		double ystep = AREA / (Math.abs(maxlat - minlat));
		
		return ystep;
		
	}

	public void showRouteMap(int ybase) {

		double ystep = ystep();
		double xstep = xstep();
		
		int rad = 5;
		
		int xPos = 0;
		int yPos = 0;
		int xPos2 = 0;
		int yPos2 = 0;
		
		setColor(0,255,0);
		
		for(int i = 0; i < gpspoints.length; i++) {
			
			double longitude = gpspoints[i].getLongitude();
			double latitude = gpspoints[i].getLatitude();
			
			double minlat = GPSUtils.findMin(GPSUtils.getLatitudes(gpspoints));
			double minlon = GPSUtils.findMin(GPSUtils.getLongitudes(gpspoints));
			
			yPos = (int)(AREA - (ystep * (latitude-minlat)));
			xPos = (int)(xstep * (longitude-minlon) + 30);
			
			fillCircle(xPos,yPos,rad);
			
			if(i < gpspoints.length - 1) {
				
				double longitude2 = gpspoints[i+1].getLongitude();
				double latitude2 = gpspoints[i+1].getLatitude();
				
				yPos2 = (int)(AREA - (ystep * (latitude2-minlat)));
				xPos2 = (int)(xstep * (longitude2-minlon) + 30);
				
			}
			
			drawLine(xPos,yPos,xPos2,yPos2);
			
		}
		
	}

	public void showStatistics() {

		int TEXTDISTANCE = 20;

		setColor(0,0,0);
		setFont("Courier",12);
		
		int yPos = 20;
		
		String regex = "\n";
		
		String[] stats = gpscomputer.displayStatistics().split(regex);
		
		for(int i = 0; i < stats.length; i++) {
			
			System.out.println(stats[i]);
			drawString(stats[i],TEXTDISTANCE,yPos);
			
			yPos += 10;
		}
		
	}

	public void playRoute(int ybase) {

		double xstep = xstep();
		double ystep = ystep();
		
		setSpeed(5);
		setColor(0,0,255);
		
		double minlat = GPSUtils.findMin(GPSUtils.getLatitudes(gpspoints));
		double minlon = GPSUtils.findMin(GPSUtils.getLongitudes(gpspoints));
		
		int rad = 6;
		int xPos = (int)(xstep * (gpspoints[0].getLongitude()-minlon) + 30);
		int yPos = (int)(AREA-(ystep * (gpspoints[0].getLatitude()-minlat)));
		
		int id = fillCircle(xPos,yPos,rad);
		
		for(int i = 0; i < gpspoints.length; i++) {
			
			double longitude = gpspoints[i].getLongitude();
			double latitude = gpspoints[i].getLatitude();
			
			yPos = (int)(AREA-(ystep * (latitude-minlat)));
			xPos = (int)(xstep * (longitude-minlon) + 30);
			
			moveCircle(id,xPos,yPos);
			
		}
		
	}

}
