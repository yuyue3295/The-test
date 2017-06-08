import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;


public class RandomWalk {
	
	static class MyMapper extends Mapper<LongWritable,Text,IntWritable,Text>{
		// �洢�ڵ��id
		private IntWritable id;
		//������ͣ���ڸýڵ�ĸ���
		private String probability;
		//�洢�ڵ�Ķ���
		private int degree;
		//��֪������������ڵ����ֵ�����������ߴ�����ڵ����ߵ���һ���ڽӽڵ�ĸ���
		private float average_pro;
		
		@Override
		protected void map(LongWritable key, Text value,
				Mapper<LongWritable, Text, IntWritable, Text>.Context context)
				throws IOException, InterruptedException {
			StringTokenizer string = new StringTokenizer(value.toString());
			if(string.hasMoreTokens()){
				//��ýڵ�id
				id=new IntWritable(Integer.parseInt(string.nextToken()));
			}else{return;}
			
			//��ȡ������ͣ���ڸýڵ�ĸ���
			probability=string.nextToken();
			degree=string.countTokens();
			average_pro=Float.parseFloat(probability)/degree;
			
			//��ýڵ���ھӽڵ㲢���
			while(string.hasMoreTokens()){
				String neighborNode=string.nextToken();
				Text t= new Text("&"+average_pro);
				IntWritable neighborID = new IntWritable(Integer.parseInt(neighborNode));
				Text tt=new Text("@"+neighborID.get());
				//����ڵ��id�Լ��ھӽڵ��id
				context.write(id, tt);
				//��"@average_pro"�ĸ�ʽ����ڵ��id��ת�Ƶ������ڵ�ĸ���ֵ
				context.write(neighborID, t);
				
				
				
				
			}
		}
		
		
		
	}

	static class MyReducer extends Reducer<IntWritable,Text,IntWritable,Text>{
		
		@Override
		protected void reduce(IntWritable key, Iterable<Text> values,
				Reducer<IntWritable, Text, IntWritable, Text>.Context context)
				throws IOException, InterruptedException {
			ArrayList<String> ids = new ArrayList<String>();//�洢�ڵ��ھӽڵ��id
			String lianjie =" ";//��string����ʽ�洢�ڵ��id
			float probability=0;//������ͣ���ڸýڵ����ֵ
			
			//����
			for(Text text: values){
				String string = text.toString();
				
				//�ж�value��ת�Ƹ��ʻ����ھӽڵ�
				if(string.substring(0, 1).equals("&")){//ת�Ƹ���
					probability+=Float.parseFloat(string.substring(1));
				}else if(string.substring(0, 1).equals("@")){
					ids.add(string.substring(1));
				}	
			}
			
			//�����е��ھӽڵ����ӳ��ַ���
			for(int i=0;i<ids.size();i++){
				lianjie+=ids.get(i)+" ";
			}
			
			//��ϳ�probability+lianjie��ԭ�ļ��ĸ�ʽ����
			String result=probability+lianjie;
			System.out.println("reduce result="+result);
			context.write(key,new Text(result));
			System.out.println("reduce ִ�����");
			
		}
	}

	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		Configuration conf = new Configuration();
		 String inputPath = "hdfs://hadoop:9000/Facebook341-";
		 String outputPath = "";
		 int n=441;
		 
		for(int i=341;i<n;i++){
			 System.out.println("ѭ������="+i);
			 Job job = new Job(conf,RandomWalk.class.getSimpleName());
			 outputPath = inputPath+i;
			 job.setMapperClass(MyMapper.class);
			 job.setReducerClass(MyReducer.class);
			 job.setOutputKeyClass(IntWritable.class);
			 job.setOutputValueClass(Text.class);
			 FileInputFormat.setInputPaths(job, new Path(inputPath));
			 FileOutputFormat.setOutputPath(job, new Path(outputPath));
			 inputPath = outputPath+"/part-r-00000";
			 job.waitForCompletion(true);
			 
		 }
		 
	}



}