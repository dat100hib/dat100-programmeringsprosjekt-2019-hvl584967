package no.hvl.dat100ptc.oppgave4;

import no.hvl.dat100ptc.TODO;
import no.hvl.dat100ptc.oppgave1.GPSPoint;
import no.hvl.dat100ptc.oppgave2.GPSData;
import no.hvl.dat100ptc.oppgave2.GPSDataConverter;
import no.hvl.dat100ptc.oppgave2.GPSDataFileReader;
import no.hvl.dat100ptc.oppgave3.GPSUtils;

public class GPSComputer {

	private GPSPoint[] gpspoints;

	public GPSComputer(String filename) {

		GPSData gpsdata = GPSDataFileReader.readGPSFile(filename);
		gpspoints = gpsdata.getGPSPoints();

	}

	public GPSComputer(GPSPoint[] gpspoints) {
		this.gpspoints = gpspoints;
	}

	public GPSPoint[] getGPSPoints() {
		return this.gpspoints;
	}

	// beregn total distances (i meter)
	public double totalDistance() {

		double distance = 0;

		for (int i = 0; i < gpspoints.length - 1; i++) {
			distance += GPSUtils.distance(gpspoints[i], gpspoints[i + 1]);
		}

		return distance;

	}

	// beregn totale høydemeter (i meter)
	public double totalElevation() {

		double elevation = 0;
		double[] elevations = new double[gpspoints.length];

		for (int i = 0; i < gpspoints.length; i++) {
			elevations[i] = gpspoints[i].getElevation();
		}

		return GPSUtils.findMax(elevations);

	}

	// beregn total tiden for hele turen (i sekunder)
	public int totalTime() {

		int totalTime = 0;

		for (int i = gpspoints.length - 1; i > 0; i--) {
			totalTime += gpspoints[i].getTime() - gpspoints[i - 1].getTime();
		}

		return totalTime;
	}

	// beregn gjennomsnitshastighets mellom hver av gps punktene

	public double[] speeds() {

		double[] speeds = new double[gpspoints.length - 1];

		for (int i = 0; i < gpspoints.length - 1; i++) {
			speeds[i] = GPSUtils.speed(gpspoints[i], gpspoints[i + 1]);
		}

		return speeds;
	}

	public double maxSpeed() {

		double maxspeed = 0;

		double[] speeds = speeds();

		maxspeed = GPSUtils.findMax(speeds);
		
		return maxspeed;
	}

	public double averageSpeed() {

		double average = 0;
		double maxDistance = totalDistance();
		int maxTime = totalTime();
		
		average = maxDistance / maxTime  * 3.6;

		return average;

	}

	/*
	 * bicycling, <10 mph, leisure, to work or for pleasure 4.0 bicycling, general
	 * 8.0 bicycling, 10-11.9 mph, leisure, slow, light effort 6.0 bicycling,
	 * 12-13.9 mph, leisure, moderate effort 8.0 bicycling, 14-15.9 mph, racing or
	 * leisure, fast, vigorous effort 10.0 bicycling, 16-19 mph, racing/not drafting
	 * or >19 mph drafting, very fast, racing general 12.0 bicycling, >20 mph,
	 * racing, not drafting 16.0
	 */

	// conversion factor m/s to miles per hour
	public static double MS = 2.236936;

	// beregn kcal gitt weight og tid der kjøres med en gitt hastighet
	public double kcal(double weight, int secs, double speed) {

		double kcal;

		// MET: Metabolic equivalent of task angir (kcal x kg-1 x h-1)
		double met = 0;
		double speedmph = speed * MS;

		if (speedmph < 10) {
			met = 4.0;
		} else if (speedmph < 12) {
			met = 6.0;
		} else if (speedmph < 14) {
			met = 8.0;
		} else if (speedmph < 16) {
			met = 10.0;
		} else if (speedmph < 20) {
			met = 12.0;
		} else if (speedmph >= 20) {
			met = 16.0;
		} else {
			met = 0;
		}
		
		kcal = met * weight * secs/3600;

		return kcal;
	}

	public double totalKcal(double weight) {

		double totalkcal = 0;
		int time = totalTime();
		double speed = averageSpeed();
		
		totalkcal = kcal(weight, time, speed);
		
		return totalkcal;
	}

	private static double WEIGHT = 80.0;

	public String displayStatistics() {

		System.out.println("==============================================");

		String totTime = "Total Time";
		String totDistance = "Total Distance";
		String totElevation = "Total Elevation";
		String maxSpeed = "Max Speed";
		String avrgSpeed = "Average Speed";
		String energy = "Energy";
		
		totTime = String.format("%-15s",totTime);
		totDistance = String.format("%-15s",totDistance);
		totElevation = String.format("%-15s",totElevation);
		maxSpeed = String.format("%-15s",maxSpeed);
		avrgSpeed= String.format("%-15s",avrgSpeed);
		energy = String.format("%-15s",energy);
		
		totTime += ":";
		totDistance += ":";
		totElevation += ":";
		maxSpeed += ":";
		avrgSpeed += ":";
		energy += ":";
		
		totTime += GPSUtils.formatTime(totalTime());
		totDistance += GPSUtils.formatDouble(totalDistance()) + " km";
		totElevation += GPSUtils.formatDouble(totalElevation()) + " m";
		maxSpeed += GPSUtils.formatDouble(maxSpeed()) + " km/t";
		avrgSpeed += GPSUtils.formatDouble(averageSpeed()) + " km/t";
		energy += GPSUtils.formatDouble(totalKcal(WEIGHT)) + " kcal";
		
		String output = totTime + "\n" + totDistance + "\n" + totElevation + "\n" + maxSpeed + "\n" + avrgSpeed + "\n" + energy;
		
		return output;
	}

}

