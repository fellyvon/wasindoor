package com.waspring.wasindoor.locale.knn;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;

import com.waspring.wasindoor.locale.GeomagneticEntity;
import com.waspring.wasindoor.locale.entry.IntIntDouble;
import com.waspring.wasindoor.locale.entry.IntIntDoubleComparator;
import com.waspring.wasindoor.locale.entry.LocationEntity;
import com.waspring.wasindoor.locale.entry.TripleDouble;

public class KnnLocator {
	
	
	//对单个记录定位，返回定位到的位置编号. K为选取的近邻个数
	public int locate(GeomagneticEntity entity, String DirFileAddr,  Integer rowsInOneline, Integer K,  int base1, int base2)//base1为一个位置标号，base2为另一个位置标号，二者确定分组阈值
	{
		if(entity==null){
			System.out.println("ERROR:要定位的entity为空！！！");
			return -1;
		}
		
		KnnScaler scaler1 = new KnnScaler();		
		
	//	boolean success = scaler.scaleTrainFile(DirFileAddr,  entity.getBuildingId(),  entity.getFloor(), 5);//首先要有这一个，队训练数据标准化完毕
	
	
		
		entity = scaler1.scaleEntity(DirFileAddr, entity);//在test文件中，首先对训练数据标准化完毕了，所以可以直接调用这个函数
		
		if(entity==null)
		{
			//System.out.println("标准化成功！");			
	//	}
		//else
		//{
			System.out.println("标准化失败！");
			return -1;
		}	
		
		//Linkedlist 设计一个相当于一个矩阵
		//Int1: 位置编号，int2:在训练文件中的行编号， double:距离的平方
		LinkedList<IntIntDouble> neighbors = new LinkedList<IntIntDouble>(); //neighbors中每个元素第一个值:地点,第二个值：行号，第三个值：距离
		for(int i=0;i<K;i++)//对链表进行初始化
		{
			IntIntDouble iid = new IntIntDouble(-1,-1,Double.MAX_VALUE);
			neighbors.add(iid);//初始化neighbors，一共有k个
		}		
			
		
		TripleDouble entityTuple = new TripleDouble(entity.getxMagnetic(), 
				entity.getyMagnetic(), entity.getzMagnetic());	//存储标准化后的x,y,z三个磁场强度	
		TripleDouble currentTuple = null;
		
		//Step 1:  读训练文件	
		String completeFileAddr = DirFileAddr + File.separator + entity.getBuildingId() + File.separator
		+ entity.getFloor() + File.separator+"train_scale.txt";
					
		File trainfile = new File(completeFileAddr);
		if(!trainfile.exists())
		{
			System.out.println("ERROR1: 训练文件不存在！！！" +
					"请检查输入的完整文件路径是否正确.");
			return -1;
		}    		
		
		int count = 0;
		
		try{			
			BufferedReader reader = new BufferedReader(new FileReader(trainfile));  			           
	       
	        String[] strs = null;
	        String lineString = null;	       
	        
	        while((lineString = reader.readLine()) != null){            	
	        	
	        	if(lineString.trim().length()==0)
	        	{
	        		continue;//作用：防止第一行全是空格，是空格继续读
	        	}
	        	
	        	strs = lineString.split("\\s+"); //一个或任意多个空格分隔
	        	
	        	if(strs.length!=rowsInOneline){
	        		System.out.println("ERROR: 训练文件2" + completeFileAddr + 
	        				" 中: line " + (count+1) + " has a wrong format!!!");
            		return -1;
	        	}
	        	
	        	double zAngle = Double.parseDouble(strs[0]);  
	        	double xMag = Double.parseDouble(strs[1]);
	        	double yMag = Double.parseDouble(strs[2]);
	        	double zMag = Double.parseDouble(strs[3]);
	        	int location = Integer.parseInt(strs[4]);//行编号
	        	
	        	currentTuple = new TripleDouble(xMag, yMag, zMag);	        	
	        	double distpow = squreDist(entityTuple,currentTuple,1,0); //距离，1 0 代表距离的计算方式
	        	
	        		        	
	        	IntIntDouble lastNeighbor = neighbors.getLast();
	        	if(lastNeighbor.getDoubleValue()>distpow)//插入有序的neighbor
	        	{
	        		int weizhi = insert(neighbors, distpow, K);
	        		IntIntDouble iid = new IntIntDouble(location,count+1,distpow);
	        		neighbors.add(weizhi,iid);	//add 的用法是在这个位置加入元素，即在weizhi这个孙因伤加入iid
	        		
	        		int size = neighbors.size();
	        		while(size>K)
	        		{
	        			neighbors.removeLast();
	        			size = neighbors.size();
	        		}
	        	}  
	        		        		   	        
	        	count++;
	        }
	        	        
	        reader.close();	       
		}catch(IOException ex){
			ex.printStackTrace();
			return -1;
		}
		
	//	System.out.println("所有近邻如下：");
		Iterator it = neighbors.iterator(); // 获得一个迭代子
	//	while(it.hasNext()) {
		//	IntIntDouble obj = (IntIntDouble)it.next();
	//		System.out.println(obj.getInt1() + "\t" + obj.getInt2() + "\t" + obj.getDoubleValue());
	//	}
	//	System.out.println("==========");
		
		int current = 0;
		//设置权重,将IntIntDouble中的double从距离改成权重
		it = neighbors.iterator(); // 获得一个迭代子
		while(it.hasNext()) {
			current = current+1;
			IntIntDouble obj = (IntIntDouble)it.next();
			obj.setDoubleValue(1-current*0.01); //权重递减:1,0.99,0.98,0.97,...
		}
		
		
		//neighbors按照地点排序
		IntIntDoubleComparator comparator = new IntIntDoubleComparator();
		Collections.sort(neighbors,comparator);
		
	//	System.out.println("所有近邻按照位置编号排序后如下：");
	//	it = neighbors.iterator(); // 获得一个迭代子
	//	while(it.hasNext()) {
	//		IntIntDouble obj = (IntIntDouble)it.next();
	//		System.out.println(obj.getInt1() + "\t" + obj.getInt2() + "\t" + obj.getDoubleValue());
	//	}
	//	System.out.println("==========");
		
		// step2: 从近邻中选择最终位置		
		int result = findFinalLocation(DirFileAddr, entity.getBuildingId(), entity.getFloor(), neighbors, K, base1, base2);
		
		return result;		
	}
	
	//根据K个近邻确定最终的定位结果，正常返回定位位置编号，有错返回-1。定位是通过把所有近邻就近聚类(neighbors长度为K)
	public int findFinalLocation(String DirFileAddr, int buildingId, int floorId, LinkedList<IntIntDouble> neighbors, int K, int base1, int base2)
	{		
		
		//TODO:打印各种中间结果
		//Step 1:读文件获得各个地点坐标
		int size = 0;			
		
		double[] xs = new double[K];
		double[] ys = new double[K];
		
		String completeFileAddr =  DirFileAddr + File.separator+ buildingId +File.separator
		+ floorId + File.separator+"map.txt";
		System.out.println("completeFileAddr2="+completeFileAddr);
		File mapfile = new File(completeFileAddr);
	 
		if(!mapfile.exists())
		{
			  
			System.out.println("ERROR3: 地图文件不存在！！！" +
					"请检查输入的完整文件路径是否正确.");
			return -1;
		}    		
		
		int count = 0;
		
		try{			
			BufferedReader reader = new BufferedReader(new FileReader(mapfile));  			           
	       
	        String[] strs = null;
	        String lineString = null;	       
	        
	        while(((lineString = reader.readLine())!=null)&&size<K){            	
	        	
	        	if(lineString.trim().length()==0)
	        	{
	        		continue;
	        	}
	        	
	        	strs = lineString.split("\\s+"); //一个或任意多个空格分隔
	        	
	        	if(strs.length!=3){
	        		System.out.println("ERROR: 地图文件4" + completeFileAddr + 
	        				" 中: line " + (count+1) + " has a wrong format!!!");
            		return -1;
	        	}
	        		        	
	        	int location = Integer.parseInt(strs[0]);
	        	double x = Double.parseDouble(strs[1]);
	        	double y = Double.parseDouble(strs[2]);
	        	
	        	if(location == neighbors.get(size).getInt1())
	        	{
	        		xs[size] = x;
	        		ys[size] = y;
	        		size++;
	        		while(size<K&&neighbors.get(size).getInt1()==location)
	        		{
	        			xs[size] = x;
		        		ys[size] = y;
		        		size++;
	        		}
	        	}
	        		        		   	        
	        	count++;
	        }	        	        
	        reader.close();	       
		}catch(IOException ex){
			ex.printStackTrace();
			return -1;
		}
		
		if(size!=K)
		{
			System.out.println("ERROR:Map文件中没有给定的K个地点!!!");
			return -1;
		}
	//	else
		//{
		//	System.out.println("-------------坐标表---------------");
		//	for(int i=0;i<K;i++)
		//	{
			//	System.out.println(xs[i] + "   " + ys[i]);
				
		//	}
		//	System.out.println("-------------坐标表END-------------");
	//	}
				
				
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
		
		
		//output
		System.out.println("共" + clusters.size() + "个类:");
		for (int i=0;i<clusters.size();i++)
		{
			System.out.print((i+1) + ":");
			LinkedList<Integer> list = clusters.get(i);
			for (int j=0;j<list.size();j++)
			{
				Integer weizhi = list.get(j);
				weizhi = neighbors.get(weizhi).getInt1();
				System.out.print(weizhi + " ");				
			}
			System.out.println();
		}
		
		
		
		//计算score
		int mm = clusters.size();
		double[] score = new double[mm];
		for(int i=0;i<mm;i++)
		{
			LinkedList<Integer> currentList = clusters.get(i);
			for(int j=0;j<currentList.size();j++)
			{
				Integer index = currentList.get(j);
				score[i] = score[i]+neighbors.get(index).getDoubleValue(); 
			}
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
		LinkedList<Integer> cluster = clusters.get(index);
		max_score = -1;
		index = -1;
		
		for(int i=0;i<cluster.size();i++)
		{
			Integer realIndex = cluster.get(i);
			IntIntDouble iid = neighbors.get(realIndex);
			if(iid.getDoubleValue()>max_score)
			{
				max_score = iid.getDoubleValue();
				index = iid.getInt1();
			}
		}
		
		return index;
	}
	
	
	//计算距离.  shield==0表示不屏蔽所有的x,y,z磁强，1表示屏蔽x,2表示屏蔽y,3表示屏蔽z; type控制选择哪种距离度量
	public double squreDist(TripleDouble tuple1, TripleDouble tuple2, int type, int shield)
	{
		
		if(tuple1==null||tuple2==null)
		{
			System.out.println("ERROR：输入的三元组为空！！！");
			return -1;
		}
		
		if(shield==1)//屏蔽x
		{
			tuple1.setDouble1(-1000);
			tuple2.setDouble1(-1000);
		}
		else if(shield==2)//屏蔽y磁强
		{
			tuple1.setDouble2(-1000);
			tuple2.setDouble2(-1000);
		}
		else if(shield==3)//屏蔽z磁强
		{
			tuple1.setDouble3(-1000);
			tuple2.setDouble3(-1000);
		}		
		
		
		
		
		if(type == 1) //欧氏距离
		{
			double re = Math.pow(tuple1.getDouble1()-tuple2.getDouble1(),2);
			re = re+Math.pow(tuple1.getDouble2()-tuple2.getDouble2(), 2);
			re = re+Math.pow(tuple1.getDouble3()-tuple2.getDouble3(), 2);
			return re;
		}
		else if(type==2) //绝对值距离
		{
			double re = Math.abs(tuple1.getDouble1()-tuple2.getDouble1());
			re = re+Math.abs(tuple1.getDouble2()-tuple2.getDouble2());
			re = re+Math.abs(tuple1.getDouble3()-tuple2.getDouble3());
			return re;
		}
		else if(type==3)//绝对值间的最小距离
		{
			double re = Double.MAX_VALUE;
			if((tuple1.getDouble1()==-1000)&&(tuple2.getDouble1()==-1000)){}
			else
			{
				double temp = Math.abs(tuple1.getDouble1()-tuple2.getDouble1());
				if(temp<re)
				{
					re = temp;
				}
			}
			
			if((tuple1.getDouble2()==-1000)&&(tuple2.getDouble2()==-1000)){}
			else
			{
				double temp = Math.abs(tuple1.getDouble2()-tuple2.getDouble2());
				if(temp<re)
				{
					re = temp;
				}
			}
			
			if((tuple1.getDouble3()==-1000)&&(tuple2.getDouble3()==-1000)){}
			else
			{
				double temp = Math.abs(tuple1.getDouble3()-tuple2.getDouble3());
				if(temp<re)
				{
					re = temp;
				}
			}			
			
			return re;
		}
		
		else if(type==4)//绝对值间的最大距离
		{
			double re = -1;
			if((tuple1.getDouble1()==-1000)&&(tuple2.getDouble1()==-1000)){}
			else
			{
				double temp = Math.abs(tuple1.getDouble1()-tuple2.getDouble1());
				if(temp>re)
				{
					re = temp;
				}
			}
			
			if((tuple1.getDouble2()==-1000)&&(tuple2.getDouble2()==-1000)){}
			else
			{
				double temp = Math.abs(tuple1.getDouble2()-tuple2.getDouble2());
				if(temp>re)
				{
					re = temp;
				}
			}
			
			if((tuple1.getDouble3()==-1000)&&(tuple2.getDouble3()==-1000)){}
			else
			{
				double temp = Math.abs(tuple1.getDouble3()-tuple2.getDouble3());
				if(temp>re)
				{
					re = temp;
				}
			}			
			
			return re;
		}
		else
		{
			return -1;
		}
		
	}
	
	//返回插入到有序列表中的为位置
	//int weizhi = insert(neighbors, distpow, K);
	
	public int insert(LinkedList<IntIntDouble> list, double value, int K){		
		
		int size = list.size();	
		
		if(size==0)
		{
			return 0;
		}		
		
		int left = 0;
		int right = size - 1;	
		int middle = 0;
		while(left <= right){
			middle = (left + right) / 2;			
			IntIntDouble temp = list.get(middle);			
			double currentMaxDist = temp.getDoubleValue();
			if(currentMaxDist < value){
				left = middle + 1;
			}
			else{
				right = middle - 1;
			}			
		}
		
		//System.out.println("middle:" + middle);
		IntIntDouble temp = list.get(middle);			
		double currentMaxDist = temp.getDoubleValue();
		if(currentMaxDist < value){
			middle = middle + 1;
		}
		
		return middle;
		
	}
	
}
