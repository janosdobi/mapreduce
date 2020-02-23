package home.janos;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class AirportPerCountryDriver extends Configured implements Tool {

    public static void main(String[] args) throws Exception {
        int exitCode = ToolRunner.run(new AirportPerCountryDriver(), args);
        System.exit(exitCode);
    }

    @Override
    public int run(String[] args) throws Exception {
        Configuration conf = getConf();

        Job job = Job.getInstance(conf);

        job.setJobName("AirportCount");
        job.setJarByClass(AirportPerCountryDriver.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        job.setMapperClass(AirportPerCountryMap.class);
        job.setReducerClass(AirportPerCountryReduce.class);

        Path inputFile = new Path(args[0]);
        Path outputFile = new Path(args[1]);

        FileInputFormat.addInputPath(job, inputFile);
        FileOutputFormat.setOutputPath(job, outputFile);
        return job.waitForCompletion(true) ? 0 : 1;
    }
}
