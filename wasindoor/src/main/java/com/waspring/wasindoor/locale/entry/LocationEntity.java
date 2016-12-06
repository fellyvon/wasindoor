package com.waspring.wasindoor.locale.entry;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class LocationEntity {
	
	private int buildingId; //建筑号
	private int floor; //楼层号 
	private int No; // 地点编号
	private double x; //x坐标
	private double y; //y坐标

	public LocationEntity()
	{}
	
	public LocationEntity(int building_id, int floor_id, int locNo, double locX, double locY)
	{
		buildingId = building_id;
		floor = floor_id;
		No = locNo;
		x = locX;
		y = locY;
	}
	
	//以loc1和loc2的距离为基准判断是不是近邻；函数返回的是近邻距离判断的阈值
	public double setBaseDist(String DirFileAddr, int building_id, int floor_id, int loc1, int loc2)
	{
		if(loc1==loc2)
		{
			return 0;
		}
		
		
		double[] xs = new double[2];//存储loc1 和 loc2 的 x 的坐标
		double[] ys = new double[2];//存储loc1 和 loc2 的 y 的坐标
		
		int size = 0;
		
		String completeFileAddr = DirFileAddr + File.separator + building_id + File.separator
		+ floor_id + File.separator+"map.txt";//这一句子的意思为简历文件夹的路径
		
		File mapfile = new File(completeFileAddr);//通过路径找到冰川见文件对象
		if(!mapfile.exists())//如果找的文件不存在，报错
		{
			System.out.println("ERROR: 地图文件不存在11！！！" +
					"请检查输入的完整文件路径是否正确.");
			return -1;
		}    		
		
		int count = 0;
		
		try{			
			BufferedReader reader = new BufferedReader(new FileReader(mapfile));  			           
	       
	        String[] strs = null;
	        String lineString = null;	       
	        
	        while(((lineString = reader.readLine())!=null)&&size<2){            	
	        	
	        	if(lineString.trim().length()==0)
	        	{
	        		continue;
	        	}
	        	
	        	strs = lineString.split("\\s+"); //一个或任意多个空格分隔
	        	
	        	if(strs.length!=3){
	        		System.out.println("ERROR: 地图文件" + completeFileAddr + 
	        				" 中: line " + (count+1) + " has a wrong format!!!");
            		return -1;
	        	}
	        		        	
	        	int location = Integer.parseInt(strs[0]);
	        	double x = Double.parseDouble(strs[1]);
	        	double y = Double.parseDouble(strs[2]);
	        	
	        	if(location == loc1 || location == loc2)
	        	{
	        		xs[size] = x;
	        		ys[size] = y;
	        		size++;
	        	}
	        		        		   	        
	        	count++;
	        }
	        	        
	        reader.close();	       
		}catch(IOException ex){
			ex.printStackTrace();
			return -1;
		}
		
		if(size!=2)
		{
			System.out.println("ERROR:Map文件中没有给定的地点!!!");
			return -1;
		}
		
		double threshold = (xs[0]-xs[1])*(xs[0]-xs[1])+(ys[0]-ys[1])*(ys[0]-ys[1]);		
		return threshold;		
	}
	
	//计算两个地点之间是不是近邻: 0 不是近邻，1是近邻，-1表示有错误； 判断 locA 和 locB 是不是近邻
	public int isNeighbor(String DirFileAddr, int building_id, int floor_id, int locA, int locB, double threshold)
	{
		if(locA==locB)
		{
			return 1;
		}
				
		double[] xs = new double[2];
		double[] ys = new double[2];
		
		int size = 0;
		
		String completeFileAddr = DirFileAddr + File.separator + building_id + File.separator
		+ floor_id + File.separator+"map.txt";
		
		File mapfile = new File(completeFileAddr);
		if(!mapfile.exists())
		{
			System.out.println("ERROR: 地图文件不存在12！！！" +
					"请检查输入的完整文件路径是否正确.");
			return -1;
		}    		
		
		int count = 0;
		
		try{			
			BufferedReader reader = new BufferedReader(new FileReader(mapfile));  			           
	       
	        String[] strs = null;
	        String lineString = null;	       
	        
	        while(((lineString = reader.readLine())!=null)&&size<2){            	
	        	
	        	if(lineString.trim().length()==0)
	        	{
	        		continue;
	        	}
	        	
	        	strs = lineString.split("\\s+"); //一个或任意多个空格分隔
	        	
	        	if(strs.length!=3){
	        		System.out.println("ERROR: 地图文件" + completeFileAddr + 
	        				" 中: line " + (count+1) + " has a wrong format!!!");
            		return -1;
	        	}
	        		        	
	        	int location = Integer.parseInt(strs[0]);
	        	double x = Double.parseDouble(strs[1]);
	        	double y = Double.parseDouble(strs[2]);
	        	
	        	if(location == locA || location == locB)
	        	{
	        		xs[size] = x;
	        		ys[size] = y;
	        		size++;
	        	}
	        		        		   	        
	        	count++;
	        }
	        	        
	        reader.close();	       
		}catch(IOException ex){
			ex.printStackTrace();
			return -1;
		}
		
		if(size!=2)
		{
			System.out.println("ERROR:Map文件中没有给定的地点!!!");
			return -1;
		}
		
		double dist = (xs[0]-xs[1])*(xs[0]-xs[1])+(ys[0]-ys[1])*(ys[0]-ys[1]);
		//System.out.println("Dist between " + locA + " and " + locB + " is " + dist);
		if(dist<=threshold)
		{
			return 1;
		}	
		else{
			return 0;
		}		
	}
	
	/*
	//得到K个近邻的位置信息, double[0]第1个点的x坐标,double[1]第1个点的y坐标，double[2]第二个点的x坐标,double[3]第二个点的y坐标...
	public double[] getNeighbor(String DirFileAddr, int building_id, int floor_id, LinkedList<IntIntDouble> neighbors)
	{
		if()
		{
			return 1;
		}
				
		double[] xs = new double[2];
		double[] ys = new double[2];
		
		int size = 0;
		
		String completeFileAddr = DirFileAddr + "\\" + building_id + "\\" 
		+ floor_id + "\\map.txt";
		
		File mapfile = new File(completeFileAddr);
		if(!mapfile.exists())
		{
			System.out.println("ERROR: 地图文件不存在13！！！" +
					"请检查输入的完整文件路径是否正确.");
			return -1;
		}    		
		
		int count = 0;
		
		try{			
			BufferedReader reader = new BufferedReader(new FileReader(mapfile));  			           
	       
	        String[] strs = null;
	        String lineString = null;	       
	        
	        while(((lineString = reader.readLine())!=null)&&size<2){            	
	        	
	        	if(lineString.trim().length()==0)
	        	{
	        		continue;
	        	}
	        	
	        	strs = lineString.split("\\s+"); //一个或任意多个空格分隔
	        	
	        	if(strs.length!=3){
	        		System.out.println("ERROR: 地图文件" + completeFileAddr + 
	        				" 中: line " + (count+1) + " has a wrong format!!!");
            		return -1;
	        	}
	        		        	
	        	int location = Integer.parseInt(strs[0]);
	        	double x = Double.parseDouble(strs[1]);
	        	double y = Double.parseDouble(strs[2]);
	        	
	        	if(location == locA || location == locB)
	        	{
	        		xs[size] = x;
	        		ys[size] = y;
	        		size++;
	        	}
	        		        		   	        
	        	count++;
	        }
	        	        
	        reader.close();	       
		}catch(IOException ex){
			ex.printStackTrace();
			return -1;
		}
		
		if(size!=2)
		{
			System.out.println("ERROR:Map文件中没有给定的地点!!!");
			return -1;
		}
		
		double dist = (xs[0]-xs[1])*(xs[0]-xs[1])+(ys[0]-ys[1])*(ys[0]-ys[1]);
		//System.out.println("Dist between " + locA + " and " + locB + " is " + dist);
		if(dist<=threshold)
		{
			return 1;
		}	
		else{
			return 0;
		}		
	}
	*/
		
	public int getNo() {
		return No;
	}
	public void setNo(int no) {
		No = no;
	}
	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}

	public int getBuildingId() {
		return buildingId;
	}

	public void setBuildingId(int buildingId) {
		this.buildingId = buildingId;
	}

	public int getFloor() {
		return floor;
	}

	public void setFloor(int floor) {
		this.floor = floor;
	}

	
	
}
