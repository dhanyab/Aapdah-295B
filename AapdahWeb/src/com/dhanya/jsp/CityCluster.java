package com.dhanya.jsp;

import com.dhanya.jsp.ClusterBasedonAgency;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.Text;
import org.apache.mahout.math.Vector;
import org.apache.mahout.math.RandomAccessSparseVector;
import org.apache.mahout.math.VectorWritable;
import org.apache.mahout.clustering.canopy.CanopyDriver;
import org.apache.mahout.clustering.classify.WeightedPropertyVectorWritable;
import org.apache.mahout.clustering.classify.WeightedVectorWritable;
import org.apache.mahout.clustering.Cluster;
import org.apache.mahout.clustering.kmeans.KMeansDriver;
import org.apache.mahout.clustering.kmeans.Kluster;
import org.apache.mahout.clustering.topdown.postprocessor.ClusterOutputPostProcessorDriver;
import org.apache.mahout.common.distance.CosineDistanceMeasure;
import org.apache.mahout.common.distance.DistanceMeasure;
import org.apache.mahout.common.distance.EuclideanDistanceMeasure;
import org.apache.mahout.common.distance.ManhattanDistanceMeasure;
import org.apache.mahout.common.distance.MinkowskiDistanceMeasure;

public class CityCluster {
	public static void main(String args[]) throws Exception {

		String crimefile = "/Users/dhanyabalasundaran/Desktop/paloalto.csv";
		String abspath = "/Users/dhanyabalasundaran/Development/workspace-mongo/AapdahWeb/testData";
		File data = new File("/Users/dhanyabalasundaran/Desktop/paloalto.csv");
		ClusterBasedonAgency c = new ClusterBasedonAgency();
		if (!data.exists()) {
			//c.cluster();
		}
		List<Vector> vectors = getVectorPoints(crimefile);
		File testData = new File(abspath);
		File outputDir = new File(abspath + "/outputdata");
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
		int k = 3;
		Path path = new Path(abspath + "/clusters/part-00000");
		SequenceFile.Writer wr = new SequenceFile.Writer(fs, conf, path,
				Text.class, Kluster.class);

		for (int i = 0; i < k; i++) {
			Vector vec = vectors.get(i);
			Kluster cluster = new Kluster(vec, i,
					new EuclideanDistanceMeasure());
			wr.append(new Text(cluster.getIdentifier()), cluster);
		}
		wr.close();
		// outputDir.mkdir();
		Path outpath = new Path(abspath + "/outputdata");
		
		KMeansDriver.run(conf, new Path(abspath + "/points"), new Path(abspath
				+ "/clusters"), outpath, new EuclideanDistanceMeasure(), 0.001,
				2, false,0, false);
//
//		 CanopyDriver.run(conf, new Path(abspath+"/clusters/"), new
//		 Path(abspath+"/output"), new EuclideanDistanceMeasure(), 0.9, 0.1,
//		 true, 0.0, false);
//		Path input1 = new Path(abspath + "/output");
//		Path output1 = new Path(abspath + "/output/postoutput1");
//		ClusterOutputPostProcessorDriver.run(input1, output1, true);
		SequenceFile.Reader reader = new SequenceFile.Reader(fs, new Path(
				abspath + "/output/" + Cluster.CLUSTERED_POINTS_DIR
						+ "/part-m-00000"), conf);
		// SequenceFile.Reader reader = new SequenceFile.Reader(fs, new Path(
		// "output/clusters-1/part-r-00000"), conf);
		IntWritable key = new IntWritable();
		WeightedPropertyVectorWritable value = new WeightedPropertyVectorWritable();

		while (reader.next(key, value)) {
			System.out.println(value.toString() + " belongs to cluster "
					+ key.toString());

		}
		reader.close();

	}

	private static void writePointsToFile(List<Vector> points, String fileName,
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

	private static List<Vector> getVectorPoints(String crimefile)
			throws IOException {
		List<Vector> points = new ArrayList<Vector>();
		CSVReader csvReader = new CSVReader(new FileReader(crimefile));
		String[] row = null;

		while ((row = csvReader.readNext()) != null)

		{
			String dc = null;

			dc = row[2].substring(8);
			String d1 = dc.replaceAll(" ", "");
			String d = d1.replaceAll("[:/|,]", "");
			System.out.println(d);

			Vector vec = new RandomAccessSparseVector(dc.length());
			vec.assign(Double.parseDouble(d));

			points.add(vec);
		}
		csvReader.close();

		return points;
	}

}
