package com.waspring.wasindoor.locale.knn;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

import com.waspring.wasindoor.locale.entry.LocationEntity;

//history-data based s算法，确定初始那几个点
public class HdbInitial {
	//FirstSamples 代表最初几次测量值返回的定位结果，有他们确定初始历史轨迹；int base1, int base2为两个位置标号，确定阈值间隔
	public LinkedList<Integer> InitialVale(String DirFileAddr, int buildingId, int floorId, LinkedList<Integer> FirstSamples, int base1, int base2)
	{		
		
		//TODO:打印各种中间结果
		//Step 1:读文件获得各个地点坐标
			
		LinkedList<Integer> ErrorReturn= new LinkedList<Integer>();
		 ErrorReturn.add(-1);
		
		
		int K=FirstSamples.size();             //通过前几个数据最终确定初始位置返回值
		double[] xs = new double[K];
		double[] ys = new double[K];
		
		String completeFileAddr = DirFileAddr + File.separator + buildingId + File.separator
		+ floorId + File.separator+"map.txt";
		System.out.println("completeFileAddr1="+completeFileAddr);
	
		File mapfile = new File(completeFileAddr);
		if(!mapfile.exists())
		{
			System.out.println("ERROR: 地图文件不存在14！！！" +
					"请检查输入的完整文件路径是否正确.");
			return ErrorReturn;
		}    
		

		int count = 0;
		
		try{			
			BufferedReader reader = new BufferedReader(new FileReader(mapfile));  			           
	       
	        String[] strs = null;
	        String lineString = null;	       
	        
	        while(((lineString = reader.readLine())!=null)){ 
	    		int size = 0;
	        	
	        	if(lineString.trim().length()==0)
	        	{
	        		continue;
	        	}
	        	
	        	strs = lineString.split("\\s+"); //一个或任意多个空格分隔
	        	
	        	if(strs.length!=3){
	        		System.out.println("ERROR: 地图文件" + completeFileAddr + 
	        				" 中: line " + (count+1) + " has a wrong format!!!");
          		return ErrorReturn;
	        	}
	        		        	
	        	int location = Integer.parseInt(strs[0]);
	        	double x = Double.parseDouble(strs[1]);
	        	double y = Double.parseDouble(strs[2]);
	        		        
	        		while(size<K)
	        		{
	        			if (location== FirstSamples.get(size))
	        			{	
	        				xs[size] = x;
		        	     	ys[size] = y;		        		   
		        		}
	        			 size++;
	        		}
	        	
	        	        		   	        
	        	count++;
	        }	        	        
	        reader.close();	       
		}catch(IOException ex){
			ex.printStackTrace();
			return ErrorReturn;
		}
			
			System.out.println("-------------坐标表---------------");
			for(int i=0;i<K;i++)
			{
				System.out.println(xs[i] + "   " + ys[i]);
				
			}
			System.out.println("-------------坐标表END-------------");
		
				
				
		//Step 2: 设定距离阈值
		LocationEntity entity = new LocationEntity();
		double threshold = entity.setBaseDist(DirFileAddr, buildingId, floorId, base1, base2);
		System.out.println("Threshold:" + threshold);
		
		//Step 3: 根据相互间距离分类：距离小于base1和base2之间距离的认为是一个类
		ArrayList<LinkedList> clusters = new ArrayList<LinkedList>();// clusters 里存放的是  linkedlist 的类型 
		boolean[] inflag = new boolean[K];
		boolean[] visited = new boolean[K];
		for(int i=0;i<K;i++)
		{
			inflag[i] = false;
			visited[i] = false;
		}
				
		for(int i=0;i<K;i++)
		{
			if(inflag[i]==false)
			{
				LinkedList<Integer> currentCluster = new LinkedList<Integer>();//无处使长度初始化创建对象
				currentCluster.add(i);	
				inflag[i] = true;
				
				int sizeOfCurrentCluster = 1;				
				int next2visit = 0;
				
				while(next2visit<sizeOfCurrentCluster)
				{				
					int currentInd = currentCluster.get(next2visit);//i=0时，currentind=0；
					for(int j=0;j<K;j++)
					{
						if(inflag[j]==false)
						{
							//IntIntDouble jIID = neighbors.get(j);						
							double dist = (xs[currentInd]-xs[j])*(xs[currentInd]-xs[j])+(ys[currentInd]-ys[j])*(ys[currentInd]-ys[j]);	
							if(dist<=threshold)
							{								
								currentCluster.add(j);								
								sizeOfCurrentCluster++;
								inflag[j] = true;
							}
						}						
					}
					visited[currentInd] = true;
					next2visit++;
				}
				clusters.add(currentCluster);				
			}
		}
		
		
		
		
		
		//计算score
		int mm = clusters.size();
		double[] score = new double[mm];
		
		for(int i=0;i<mm;i++)
		{
			LinkedList<Integer> currentList = clusters.get(i);
			score[i] = currentList.size(); //每一个类里边元素个数最多就行			
		}
		
		//先选择所有类中score和最大的类
		double max_score = -1;
		int index = -1;
		
		for(int i=0;i<mm;i++)
		{
			if(score[i]>max_score)
			{
				max_score = score[i];
				index = i;
			}
		}
		
		//在选定的类之中Score最大的位置
		LinkedList<Integer> InitialValue = clusters.get(index);	
		
		//此时的InitialValue 存放的是FirstSamples 的标号
		for (int i=0; i<InitialValue.size();i++)
		{
			InitialValue.set(i,  FirstSamples.get(InitialValue.get(i)));
		}
		return InitialValue;
	}
	
}
