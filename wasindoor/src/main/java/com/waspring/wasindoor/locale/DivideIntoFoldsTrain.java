package com.waspring.wasindoor.locale;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class DivideIntoFoldsTrain {
	private String inputFileAddress;
	private int rowsInOneline;
	private int numOfLocation;
	private int numOfFolds;
	
	public DivideIntoFoldsTrain(String str1, int i1, int i2, int i3)//构造函数
	{
		inputFileAddress = str1;
		rowsInOneline = i1;
		numOfLocation = i2;
		numOfFolds = i3;
	}
	
	/*
	public static void main(String[] args){
		
		// 5是每行列数, 28是采样位置个数，8是折数			
		DivideIntoFoldsTrain di = new DivideIntoFoldsTrain("E:\\indoorGeo\\angletest\\labtest_angle_test.txt", 5, 28, 8);
		
		di.readFile();		
	}
	*/
	
	
    /**
     * 以行为单位读取文件，常用于读面向行的格式化文件
     */
	// 成功返回1，否则返回0
    public int readFile(){   
    	
    	int start_index = inputFileAddress.lastIndexOf("\\");
		int end_index = inputFileAddress.lastIndexOf(".");
		
		String fileName = inputFileAddress.substring(start_index+1, end_index);
		String fileAddrTrim = inputFileAddress.substring(0,end_index);//没有了。txt
    	   	    	
    	//目标文件===============================================
    	String targetFileName = fileAddrTrim;
    	
		File resultfile = new File(targetFileName);
		if(resultfile.exists())
		{
			System.out.println("File already exists! Will rewrite new contents.");
			// resultfile.delete();
		}    		  		
    	   
		
		String[] targetFileNames = new String[numOfFolds+1];
		
		for(int i=0;i<numOfFolds;i++){
			targetFileNames[i] = targetFileName + "_" + (i+1) + ".txt";
		}
		targetFileNames[numOfFolds] = targetFileName + "_whole.txt";
		
		// file reader
        File readfile = new File(inputFileAddress);
        BufferedReader reader = null;                
        
        
        
        int minNum = Integer.MAX_VALUE; //每个地点同角度的点最少个数
        
        // 第一遍读:行数和每个角度的点个数
        try {
            System.out.println("以行为单位读取文件内容，一次读一整行：");
            reader = new BufferedReader(new FileReader(readfile));  
            
            String lineString = null;            
            int line = 1; //行数                    
            
         //   int[][] num = new int[numOfLocation][numOfFolds];
            
          //  for(int i=0;i<numOfLocation;i++)
          //  {
            //	for(int j=0;j<numOfFolds;j++)
            //	{
            	//	num[i][j] = 0;
           // 	}
         //   }
            
            
            // 一次读入一行，直到读入null为文件结束
            while ((lineString = reader.readLine()) != null) {            	
            	
            	if(lineString.trim().length()==0)
            	{
            		continue;
            	}
            	
            	String[] strs = lineString.split("\\s+"); //一个或任意多个空格分隔
            	
            	if(strs.length!=rowsInOneline)
            	{
            		System.out.println("ERROR: line " + line + " has a wrong format!!!");
            		return 0;
            	}
            	    
            	double angle = Double.parseDouble(strs[0]);  
            	double period = 360/numOfFolds;
            	
            	int no = (int)Math.ceil((angle+180)/period); // # of Fold
            //	int classNo = Integer.parseInt(strs[4]); //# of Class
            	
            	if(no==0){
            		no = 1;
            	}
            	
            //	num[classNo-1][no-1] = num[classNo-1][no-1]+1;
            	
                line++;
            }
            
            // close reader and writer
            reader.close(); 
            
            // 确定最少的同地点同角度的个数
        // for(int i=0;i<numOfLocation;i++)
           // {
            //	for(int j=0;j<numOfFolds;j++)
            //	{
            	//	if(num[i][j]<minNum){
            		//	minNum = num[i][j];
            		//}
            	//}
            //}
            
			
			System.out.println("第一遍读完成!!! There are " + (line-1) + 
					" lines. 同地点同角度的数据最少个数:" + minNum);
			
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
        
        
        
        int[][] writenNum = new int[numOfLocation][numOfFolds];
        for(int i=0;i<numOfLocation;i++)
        {
        	for(int j=0;j<numOfFolds;j++)
        	{
        		writenNum[i][j] = 0;
        	}
        }
        
        // 第二遍读，边读边转换格式后输出到文件
        try {
            System.out.println("以行为单位读取文件内容，一次读一整行：");
            reader = new BufferedReader(new FileReader(readfile));
            
            
            FileWriter[] fileWriter = new FileWriter[numOfFolds+1];
            
            for(int i=0; i<numOfFolds+1; i++){
            	fileWriter[i] = new FileWriter(targetFileNames[i]);  //如果是已有文件，内容会自动重写                              
            }
            
            
            String lineString = null;            
            int line = 1; //行数
            
            
            // 一次读入一行，直到读入null为文件结束
            while ((lineString = reader.readLine()) != null) {            	
            	
            	if(lineString.trim().length()==0)// 所有起始和结尾的空格都删除了
            	{
            		continue;
            	}
            	
            	String[] strs = lineString.split("\\s+"); //一个或任意多个空格分隔
            	
            	if(strs.length!=rowsInOneline)
            	{
            		System.out.println("ERROR: line " + line + " has a wrong format!!!");
            		return 0;
            	}
            	    
            	double angle = Double.parseDouble(strs[0]);  
            	double period = 360/numOfFolds;
            	
            	int no = (int)Math.ceil((angle+180)/period); // # of Fold
            //	int classNo = Integer.parseInt(strs[4]); //# of Class
            	
            	if(no==0){
            		no = 1;
            	}
            	
            //	if(writenNum[classNo-1][no-1]<minNum)//这个地缝可以改变每个折中指纹点的个数
            	//{
            		
	            	String hang = strs[4] + " 1:" + strs[1] + " 2:" + strs[2] + " 3:" + strs[3] + "\r\n";	            	   
	            	fileWriter[no-1].write(hang);	
	            	//writenNum[classNo-1][no-1] = writenNum[classNo-1][no-1]+1;
	            	fileWriter[numOfFolds].write(hang);
            //	}
            	
                line++;
            }
            
            // close reader and writer
            reader.close();
            
            for(int i=0;i<numOfFolds+1;i++){
	            fileWriter[i].flush();
				fileWriter[i].close();	
			}
			
			System.out.println("All are done!!! There are " + (line-1) + " lines.");
			
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                	e1.printStackTrace();
                	return 0;
                }
            }
        }//end of finally   
        
        return 1;
    }

	public String getInputFileAddress() {
		return inputFileAddress;
	}

	public void setInputFileAddress(String inputFileAddress) {
		this.inputFileAddress = inputFileAddress;
	}

	public int getRowsInOneline() {
		return rowsInOneline;
	}

	public void setRowsInOneline(int rowsInOneline) {
		this.rowsInOneline = rowsInOneline;
	}

	public int getNumOfLocation() {
		return numOfLocation;
	}

	public void setNumOfLocation(int numOfLocation) {
		this.numOfLocation = numOfLocation;
	}

	public int getNumOfFolds() {
		return numOfFolds;
	}

	public void setNumOfFolds(int numOfFolds) {
		this.numOfFolds = numOfFolds;
	} 
    
    
}//end of class
