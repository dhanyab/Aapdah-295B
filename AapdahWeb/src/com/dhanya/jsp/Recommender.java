package com.dhanya.jsp;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.apache.commons.io.FileUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.Text;
import org.apache.mahout.math.NamedVector;
import org.apache.mahout.math.SequentialAccessSparseVector;
import org.apache.mahout.math.Vector;
import org.apache.mahout.math.RandomAccessSparseVector;
import org.apache.mahout.math.VectorWritable;
import org.apache.mahout.clustering.canopy.CanopyDriver;
import org.apache.mahout.clustering.classify.WeightedPropertyVectorWritable;
import org.apache.mahout.clustering.classify.WeightedVectorWritable;
import org.apache.mahout.clustering.Cluster;
import org.apache.mahout.clustering.iterator.ClusterWritable;
import org.apache.mahout.clustering.kmeans.KMeansDriver;
import org.apache.mahout.clustering.kmeans.Kluster;
import org.apache.mahout.clustering.topdown.postprocessor.ClusterOutputPostProcessorDriver;
import org.apache.mahout.common.distance.CosineDistanceMeasure;
import org.apache.mahout.common.distance.DistanceMeasure;
import org.apache.mahout.common.distance.EuclideanDistanceMeasure;
import org.apache.mahout.common.distance.ManhattanDistanceMeasure;
import org.apache.mahout.common.distance.MinkowskiDistanceMeasure;

public class Recommender {
	public static void main(String args[]) throws Exception {
		String abspath = "/Users/dhanyabalasundaran/Development/workspace-mongo/AapdahWeb/testData";
		String abspath1 = "/Users/dhanyabalasundaran/Development/workspace-mongo/AapdahWeb";
		List<Vector> finalPoints = new ArrayList<Vector>();
		String ct = null;
		String datafile = null;
		List<String> crimeTime = new ArrayList<String>();
		int k = 5;
		Scanner keyboard = new Scanner(System.in);
		System.out.println("City : ");
		String city = keyboard.nextLine();

		System.out.println("Time:");
		String time = keyboard.nextLine();
	//	System.out.println("Length of time : "+time.length());
		if (city.equalsIgnoreCase("Mountain View"))
			datafile = abspath1 + "/datafiles/mountainview.csv";
		else if (city.equalsIgnoreCase("Palo Alto"))
			datafile = abspath1 + "/datafiles/paloalto.csv";
		else if (city.equalsIgnoreCase("Santa Clara"))
			datafile = abspath1 + "/datafiles/santaclara.csv";
		else if (city.equalsIgnoreCase("Sunnyvale"))
			datafile = abspath1 + "/datafiles/sunnyvale.csv";
		else if (city.equalsIgnoreCase("SFO"))
			datafile = abspath1 + "/datafiles/sfo.csv";
		else if (city.equalsIgnoreCase("San Mateo"))
			datafile = abspath1 + "/datafiles/sanmateo.csv";

		List<Vector> vectors = getVectorPoints(datafile);

		File testData = new File(abspath);
		File outputDir = new File(abspath + "/output");

		if (!testData.exists()) {
			testData.mkdir();

		}
		testData = new File(abspath + "/points");
		if (!testData.exists()) {
			testData.mkdir();
		}

		Configuration conf = new Configuration();
		FileSystem fs = FileSystem.get(conf);
		writePointsToFile(vectors, abspath + "/points/file1", fs, conf);

		Path path = new Path(abspath + "/clusters/part-00000");
		SequenceFile.Writer writer = new SequenceFile.Writer(fs, conf, path,
				Text.class, Kluster.class);// );

		for (int i = 0; i < k; i++) {
			Vector vec = vectors.get(i);
			Kluster cluster = new Kluster(vec, i,
					new EuclideanDistanceMeasure());
			writer.append(new Text(cluster.getIdentifier()), cluster);
		}
		writer.close();
		Path output = null;
		if (!outputDir.exists()) {

			KMeansDriver.run(conf, new Path(abspath + "/points"), new Path(
					abspath + "/clusters"), new Path(abspath + "/output"),
					new EuclideanDistanceMeasure(), 0.001, 3, true, 0.0, false);
			Path input = new Path(abspath + "/output");
			output = new Path(abspath + "/postoutput");
			ClusterOutputPostProcessorDriver.run(input, output, true);
		}
		SequenceFile.Reader reader = new SequenceFile.Reader(fs, new Path(
				abspath + "/output/" + Cluster.CLUSTERED_POINTS_DIR
						+ "/part-m-00000"), conf);
		// IntWritable key = new IntWritable();
		// WeightedVectorWritable value = new WeightedVectorWritable();
		// while (reader.next(key, value)) {
		//
		// System.out.println(value.toString() + " belongs to cluster "
		// + key.toString());
		// }

		Vector newTimeVec = getPoints(time);

		int nearestPos = findNearestCluster(fs, new Path(abspath
				+ "/output/clusters-3-final/part-r-00000"), conf, newTimeVec);

		FileSystem fso = output.getFileSystem(conf);
		SequenceFile.Reader reader2 = new SequenceFile.Reader(fso, new Path(
				abspath + "/postoutput/" + nearestPos + "/part-m-0"), conf);
		LongWritable key2 = new LongWritable();
		VectorWritable value2 = new VectorWritable();

		while (reader2.next(key2, value2)) {
			//
			// System.out.println(value2.toString());
			finalPoints.add(value2.get());
			// System.out.println(finalPoints.size());

		}

		reader2.close();
		for (int t = 0; t < finalPoints.size(); t++) {
			String[] xy = finalPoints.get(t).toString().split(":");

			// System.out.println(xy[1]);
			// ct = xy[1].substring(0, 1) + ":" + xy[1].substring(1, 3);

			crimeTime.add(xy[1]);
		}
		CSVReader csvReader = new CSVReader(new FileReader(datafile));
		int a = 0;
		int b = 0;
		int r = 0;
		int o = 0;
		int t = 0;

		String[] data = null;
		List<CrimeList> list = new ArrayList();
		HashMap<String, Integer> crimeMap = new HashMap<String, Integer>();
		for (int j = 0; j < finalPoints.size(); j++) {
			while ((data = csvReader.readNext()) != null) {

				if(city.equalsIgnoreCase("San Mateo"))
				{
					System.out.println((data[2].replaceAll(" ", "").replaceAll(":", "")
							.substring(8, 10)));
					if (data[2].replaceAll(" ", "").replaceAll(":", "")
							.substring(8, 10)
							.contains(crimeTime.get(j).substring(0, 2))) {

						if (data[1].contains("Assault")) {
							crimeMap.put("Assault", ++a);
						} else if (data[1].contains("Theft"))
							crimeMap.put("Theft", ++t);
						else if (data[1].contains("Robbery"))
							crimeMap.put("Robbery", ++r);
						else if (data[1].contains("Breaking & Entering"))
							crimeMap.put("Breaking & Entering", ++b);
						else if (data[1].contains("Other sexual Offenses"))
							crimeMap.put("Other sexual Offenses", ++o);
						CrimeList c1 = new CrimeList(data[0], data[1], data[2],
								data[3], data[4]);
						list.add(c1);

					}

				}
				
				else if (data[2].replaceAll(" ", "").replaceAll(":", "")
						.substring(7, 9)
						.contains(crimeTime.get(j).substring(0, 2))) {

					if (data[1].contains("Assault")) {
						crimeMap.put("Assault", ++a);
					} else if (data[1].contains("Theft"))
						crimeMap.put("Theft", ++t);
					else if (data[1].contains("Robbery"))
						crimeMap.put("Robbery", ++r);
					else if (data[1].contains("Breaking & Entering"))
						crimeMap.put("Breaking & Entering", ++b);
					else if (data[1].contains("Other sexual Offenses"))
						crimeMap.put("Other sexual Offenses", ++o);
					CrimeList c1 = new CrimeList(data[0], data[1], data[2],
							data[3], data[4]);
					list.add(c1);

				}

			}

		}
		System.out.println();

		System.out.println("Crimes Occured in " + city
				+ " based on the past 6 months data");
		System.out
				.println("=============================================================");

		if (crimeMap.get("Assault") != null)
			System.out.println("Assault: " + crimeMap.get("Assault"));

		if (crimeMap.get("Breaking & Entering") != null)
			System.out.println("Breaking and Entering: "
					+ crimeMap.get("Breaking & Entering"));

		if (crimeMap.get("Theft") != null)
			System.out.println("Theft: " + crimeMap.get("Theft"));

		if (crimeMap.get("Robbery") != null)
			System.out.println("Robbery: " + crimeMap.get("Robbery"));

		if (crimeMap.get("Other sexual Offenses") != null)
			System.out.println("Other Sexual offenses: "
					+ crimeMap.get("Other sexual Offenses"));

		FileUtils.deleteDirectory(new File(abspath + "/testdata/"));

	}

	private static int findNearestCluster(FileSystem fs, Path path,
			Configuration conf, Vector newTimeVec) throws IOException {

		SequenceFile.Reader reader = null;

		try {
			reader = new SequenceFile.Reader(fs, path, conf);
		} catch (IOException e) {
			e.printStackTrace();
		}

		IntWritable key1 = new IntWritable();
		int nearestPos = 0;
		double smallestDist = 0;
		int clusterPos = -1;
		double dist = 0;
		IntWritable nearestCluster = null;

		ClusterWritable value1 = new ClusterWritable();
		while (reader.next(key1, value1)) {
			// System.out.println("Cluster =" + key1 + " Center = "
			// + value1.getValue().getCenter());

			dist = value1.getValue().getCenter().getDistanceSquared(newTimeVec);
			// System.out.println("Cluster =" + key1 + " Center = "
			// + value1.getValue().getCenter() + "Distance =" + dist);
			if (smallestDist == 0) {
				smallestDist = dist;
				nearestCluster = key1;
				clusterPos++;
			} else if (dist <= smallestDist) {
				smallestDist = dist;
				nearestCluster = key1;
				clusterPos++;
				nearestPos = clusterPos;
			} else {
				clusterPos++;
			}

		}
		// System.out.println("Nearest Cluster = " + nearestPos + "  distance= "
		// + smallestDist);

		reader.close();
		return nearestPos;
	}

	private static Vector getPoints(String time) {
		String d = time.replaceAll("[:/|,]", "");
		double[] dc = new double[2];
		dc[0] = Double.parseDouble(d);
		// dc[1] = 0.000;
		Vector vec = new RandomAccessSparseVector(dc.length);
		vec.assign(dc);
		return vec;
	}

	public static void writePointsToFile(List<Vector> points, String fileName,
			FileSystem fs, Configuration conf) throws IOException {
		Path path = new Path(fileName);
		SequenceFile.Writer writer = new SequenceFile.Writer(fs, conf, path,
				LongWritable.class, VectorWritable.class);
		long recNum = 0;
		VectorWritable vec = new VectorWritable();
		for (Vector point : points) {
			vec.set(point);
			writer.append(new LongWritable(recNum++), vec);
		}
		writer.close();
	}

	public static List<Vector> getVectorPoints(String inputFile)
			throws Exception {
		List<Vector> points = new ArrayList<Vector>();
		CSVReader csvReader = new CSVReader(new FileReader(inputFile));
		String[] row = null;
		StringBuilder sb = new StringBuilder();

		while ((row = csvReader.readNext()) != null)

		{
			String d = row[2].replaceAll("[:/|,]", "").substring(6);
			double[] dc = new double[2];
			dc[0] = Double.parseDouble(d);
			// dc[1] = 0.000;
			Vector vec = new RandomAccessSparseVector(dc.length);
			vec.assign(dc);
			points.add(vec);

		}

		csvReader.close();
		return points;
	}
}
