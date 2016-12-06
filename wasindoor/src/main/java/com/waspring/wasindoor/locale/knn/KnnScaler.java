package com.waspring.wasindoor.locale.knn;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.waspring.wasindoor.locale.GeomagneticEntity;

public class KnnScaler {
	
	// mu_0为x磁强的平均值，mu_1为y磁强平均值,mu_2为z磁强平均值
	private double[] mu = new double[3]; 
	
	// sigma_0为x磁强的标准差，sigma_1为y磁强的标准差...
	private double[] sigma = new double[3]; 
		

	//将训练数据zscore标准化，并存入文件
	public boolean scaleTrainFile(String DirFileAddr, Integer buildingId, 
			Integer floor, Integer rowsInOneline)
	{
		
		//Step 1: 通过训练数据计算出mu和sigma，存入文件scaleModel
		
		String completeFileAddr = DirFileAddr + File.separator+ buildingId +File.separator
		+ floor + File.separator+"train.txt";
		
		int end_index = completeFileAddr.lastIndexOf(".");		
		String fileAddrTrim = completeFileAddr.substring(0,end_index);
		
		File inputfile = new File(completeFileAddr);
		if(!inputfile.exists())
		{
			System.out.println("要标准化的训练文件不存在！！！" +
					"请检查输入的完整文件路径（包含文件名）是否正确.");
			return false;
		}    		
		
		int count = 0;
		
		try{
			System.out.println("训练数据标准化，以行为单位读取文件内容，一次读一整行：");
			BufferedReader reader = new BufferedReader(new FileReader(inputfile));  
			FileWriter writer = new FileWriter(fileAddrTrim + "_scale_model");	//标准化模型写入这个文件夹                
	       
	        String[] strs = null;
	        String lineString = null;	       
	        
	        // 一次读入一行，直到读入null为文件结束
	        while((lineString = reader.readLine()) != null){            	
	        	
	        	if(lineString.trim().length()==0)
	        	{
	        		continue;
	        	}
	        	
	        	strs = lineString.split("\\s+"); //一个或任意多个空格分隔
	        	
	        	if(strs.length!=rowsInOneline){
	        		System.out.println("需标准化的文件" + completeFileAddr + 
	        				" 中: line " + (count+1) + " has a wrong format!!!");
            		return false;
	        	}
	        	
	        	// strs[1]:x磁强, strs[2]:y磁强, strs[3]:z磁强	        	
	        	double zAngle = Double.parseDouble(strs[0]);//z的角度值
	        	for(int i=1;i<4;i++)
	        	{
	        		double temp =  Double.parseDouble(strs[i]);	        		
	        		mu[i-1] = mu[i-1]+temp;
	        		sigma[i-1] = sigma[i-1]+temp*temp;
	        	}
	        		   	        
	        	count++;
	        }
	        
	        //计算均值和方差并存入文件scaleModel
	        for(int i=0;i<3;i++){
	        	mu[i] = mu[i]/count;
	        	sigma[i] = (sigma[i]-count*mu[i]*mu[i])/(count-1);
	        	sigma[i] = Math.sqrt(sigma[i]);
	        	writer.write(mu[i] + "\r\n");
	        	writer.write(sigma[i] + "\r\n");
	        }	                
	        
	        reader.close();
	        writer.flush();
	        writer.close();
		}catch(IOException ex){
			ex.printStackTrace();
			return false;
		}
		
		//Step 2:标准化训练数据文件
		
		try{
			FileWriter scaleWriter = new FileWriter(fileAddrTrim + "_scale.txt");	
			
		
			File trainfile = new File(completeFileAddr);		
			if(!trainfile.exists())
			{
				System.out.println("要标准化的训练文件不存在！！！" +
						"请检查输入的完整文件路径（包含文件名）是否正确.");
				return false;
			}    
			
			String lineString2 = null;		
			count = 0;
			String[] strs2 = null;
			
			BufferedReader fileReader = new BufferedReader(new FileReader(trainfile));
	        // 一次读入一行，直到读入null为文件结束
	        while((lineString2 = fileReader.readLine()) != null){            	
	        	
	        	if(lineString2.trim().length()==0)
	        	{
	        		continue;
	        	}
	        	
	        	strs2 = lineString2.split("\\s+"); //一个或任意多个空格分隔
	        	
	        	if(strs2.length!=rowsInOneline){
	        		System.out.println("需标准化的文件" + completeFileAddr + 
	        				" 中: line " + (count+1) + " has a wrong format!!!");
	        		return false;
	        	}
	        	
	        	// strs[0]:x磁强, strs[1]:y磁强, strs[2]:z磁强	        	
	        	double zAngle = Double.parseDouble(strs2[0]);//z的角度值
	        	scaleWriter.write(zAngle + "        ");
	        	
	        	for(int i=1;i<4;i++)
	        	{
	        		double temp =  Double.parseDouble(strs2[i]);	  
	        		temp = (temp-mu[i-1])/sigma[i-1];	 
	        		scaleWriter.write(temp + "        ");
	        	}
	        	
	        	scaleWriter.write(Integer.parseInt(strs2[4]) + "\r\n");
	        	count++;
	        }
	        
	        
	        fileReader.close();
	        scaleWriter.flush();
	        scaleWriter.close();
		}catch(IOException ex){
			ex.printStackTrace();
			return false;
		}
		
		System.out.println("已完成训练数据的标准化。训练数据共" + count + "行。");
		
		return true;
	}
	
	
	public GeomagneticEntity scaleEntity(String DirFileAddr, GeomagneticEntity entity)
	{
		//Step 1:  读模型文件得到mu和sigma
		
		String completeFileAddr = DirFileAddr + File.separator + entity.getBuildingId() + File.separator 
		+ entity.getFloor() +File.separator +"train_scale_model";
					
		File modelfile = new File(completeFileAddr);
		if(!modelfile.exists())
		{
			System.out.println("标准化模型文件不存在！！！" +
					"请检查输入的完整文件路径（包含文件名）是否正确.");
			return null;
		}    		
		
		int count = 0;
		
		try{
			//System.out.println("训练数据标准化，以行为单位读取文件内容，一次读一整行：");
			BufferedReader reader = new BufferedReader(new FileReader(modelfile));  
			//FileWriter writer = new FileWriter(fileAddrTrim + "_scale_model");	                
	       
	        String[] strs = null;
	        String lineString = null;	       
	        
	        while((lineString = reader.readLine()) != null){            	
	        	
	        	if(lineString.trim().length()==0)
	        	{
	        		continue;
	        	}
	        	
	        	strs = lineString.split("\\s+"); //一个或任意多个空格分隔
	        	
	        	if(strs.length!=1){
	        		System.out.println("标准化的模型文件" + completeFileAddr + 
	        				" 中: line " + (count+1) + " has a wrong format!!!");
            		return null;
	        	}
	        	
	        	if(count%2==0)//%是求余数，/是求整数
	        	{
	        		int index = (count+1+1)/2-1;
	        		mu[index] = Double.parseDouble(strs[0]);
	        	}
	        	else
	        	{
	        		int index = (count+1+1)/2-1;
	        		sigma[index] = Double.parseDouble(strs[0]);	        		 
	        	}
	        		        		   	        
	        	count++;
	        }
	        
	        
	        reader.close();	       
		}catch(IOException ex){
			ex.printStackTrace();
			return null;
		}
		
		
		// step2: 标准化该条记录 		
		double xMag = (entity.getxMagnetic()-mu[0])/sigma[0];
		double yMag = (entity.getyMagnetic()-mu[1])/sigma[1];
		double zMag = (entity.getzMagnetic()-mu[2])/sigma[2];
		
		entity.setxMagnetic(xMag);
		entity.setyMagnetic(yMag);
		entity.setzMagnetic(zMag);		
		
		return entity;
	}
	
	
	

	public double[] getMu() {
		return mu;
	}

	public void setMu(double[] mu) {
		this.mu = mu;
	}

	public double[] getSigma() {
		return sigma;
	}

	public void setSigma(double[] sigma) {
		this.sigma = sigma;
	}
	
	
	
}
