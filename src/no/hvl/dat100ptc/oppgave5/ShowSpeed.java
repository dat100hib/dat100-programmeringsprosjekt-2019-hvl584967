package no.hvl.dat100ptc.oppgave5;

import javax.swing.JOptionPane;

import easygraphics.EasyGraphics;
import no.hvl.dat100ptc.TODO;
import no.hvl.dat100ptc.oppgave1.GPSPoint;
import no.hvl.dat100ptc.oppgave2.GPSData;
import no.hvl.dat100ptc.oppgave2.GPSDataFileReader;
import no.hvl.dat100ptc.oppgave3.GPSUtils;
import no.hvl.dat100ptc.oppgave4.GPSComputer;

public class ShowSpeed extends EasyGraphics {
			
	private static int MARGIN = 50;
	private static int BARHEIGHT = 200; // assume no speed above 200 km/t

	private GPSComputer gpscomputer;
	private GPSPoint[] gpspoints;
	
	public ShowSpeed() {

		String filename = JOptionPane.showInputDialog("GPS data filnavn: ");
		gpscomputer = new GPSComputer(filename);

		gpspoints = gpscomputer.getGPSPoints();
		
	}
	
	// read in the files and draw into using EasyGraphics
	public static void main(String[] args) {
		launch(args);
	}

	public void run() {

		int N = gpspoints.length-1; // number of data points
		
		makeWindow("Speed profile", 2*MARGIN + 2 * N, 2 * MARGIN + BARHEIGHT);
		
		showSpeedProfile(MARGIN + BARHEIGHT,N);
	}
	
	public void showSpeedProfile(int ybase, int N) {
		
		//System.out.println("Angi tidsskalering i tegnevinduet ...");
		//int timescaling = Integer.parseInt(getText("Tidsskalering"));
		
		int xStart = 10;
		int xPos = 10;
		
		int maxSpeed = 0;
		
		for(int i = 0; i < gpspoints.length-1; i++) {
			if(GPSUtils.speed(gpspoints[i], gpspoints[i + 1]) > maxSpeed) {
				maxSpeed = (int) GPSUtils.speed(gpspoints[i], gpspoints[i + 1]);
			}
		}
		
		int width = 1;
		
		setColor(0,0,255);
		
		for(int i = 0; i < N; i++) {
			double doubleHeight = GPSUtils.speed(gpspoints[i], gpspoints[i + 1]);
			
			int height = (int) ((2 * MARGIN + BARHEIGHT - (ybase/7)) * (doubleHeight / maxSpeed));
			
			fillRectangle(xPos, ybase - height + (ybase / 7), width, height);
			xPos += width + 1;
		}
		
		double doubleAvgSpeed = 0;
		int length = 0;
		
		for(int i = 0; i < gpspoints.length-1; i++) {
			doubleAvgSpeed += GPSUtils.speed(gpspoints[i], gpspoints[i + 1]);
			length++;
		}
		
		int avgSpeed = (int) ((2 * MARGIN + BARHEIGHT - (ybase/7)) * ((doubleAvgSpeed / length)/ maxSpeed));
		
		System.out.println(avgSpeed);
		
		for(Object i : gpspoints) {
			System.out.println(i);
		}
		
		setColor(0,255,0);
		
		fillRectangle(xStart, ybase - avgSpeed + (ybase / 7), width * gpspoints.length * 2, 1);
	}
}