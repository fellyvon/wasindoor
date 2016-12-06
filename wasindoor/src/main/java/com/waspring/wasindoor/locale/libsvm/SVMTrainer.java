package com.waspring.wasindoor.locale.libsvm;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.aiyc.server.standalone.db.DaoUtil;
import com.waspring.wasindoor.locale.DivideIntoFoldsTrain;


public class SVMTrainer {
		
	//private String gmTrainFileAddr;		//ѵ���ļ�����Ŀ¼
	private Integer buildingId;   		//��������
	private Integer floor; 				//�ڼ��� 
	
	private int rowsInOneline;			//ѵ���ļ���ÿ���м������
	private int numOfLocation;			//��ǰ������ǰ¥���еĵص����
	private int numOfFolds;			//ѵ���ļ�360�ȷ��۵�����
	
	public SVMTrainer(Integer buildingId, Integer floor, int rowsInOneline, int numOfLocation, int numOfFolds)
	{
		this.buildingId = buildingId;
		this.floor = floor;
		this.rowsInOneline = rowsInOneline;
		this.numOfLocation = numOfLocation;
		this.numOfFolds = numOfFolds;
	}
	
	
	
	
	//����ѵ��model�ļ�
	//�����ļ�ÿ��Ϊ�� -160��z�Ƕȣ� 30.03(x��ǿ) 40.67(y��ǿ) -79.04(z��ǿ) 1(���ǩ)
	//���Զ����и�ʽת����1 1:30.03 2:40.67 3��-79.04������׼��������
	public boolean buildTrainModel(String gmTrainFileAddr){
		
		//ѵ�����
		
		//step 1: ת����ʽ
	
		String completeFileAddr = gmTrainFileAddr + File.separator + buildingId + File.separator + floor + File.separator+"train.txt";
		
		// 5��ÿ������, 28�ǲ���λ�ø���8������			
		DivideIntoFoldsTrain di = new DivideIntoFoldsTrain(completeFileAddr, rowsInOneline, numOfLocation, numOfFolds);
		
		if(di.readFile()!=1)
		{
			System.out.println("ѵ����ݸ�ʽת�����?�� ��");
			return false;
		}	
		
		
		
		
		int start_index = completeFileAddr.lastIndexOf(File.separator);
		int end_index = completeFileAddr.lastIndexOf(".");
		
		String fileName = completeFileAddr.substring(start_index+1, end_index);
		String fileAddrTrim = completeFileAddr.substring(0,end_index); //ȥ����.txt 
		String fileAddrWithoutFileName = completeFileAddr.substring(0,start_index);
	
		
		//step 2: ��ݱ�׼��
		
		//�ȵõ�һ�������׼����model
		String [] scaleArgs = {"-l","-1","-u","1","-s",fileAddrTrim+"_scale_model",fileAddrTrim + "_whole.txt"};			
		try {			
			SVMScaler s = new SVMScaler(fileAddrTrim + "_whole_scale.txt");			
			s.scaleIntoFile(scaleArgs);
		} catch (IOException e) {			
			e.printStackTrace();
			System.out.println("��ݱ�׼�����?�� ��");
			return false;
		}			
		
		// ÿ���۵���ݸ��model���б�׼��
		for(int i=0;i<numOfFolds;i++){
			int j = i+1;
			String [] scaleArgs2 = {"-r",fileAddrTrim+"_scale_model", fileAddrTrim+"_"+j+".txt"};			
			try {			
				SVMScaler s = new SVMScaler(fileAddrTrim+"_"+j+"_scale.txt");			
				s.scaleIntoFile(scaleArgs2);
			} catch (IOException e) {			
				e.printStackTrace();
				System.out.println("��ݱ�׼�����?�� ��");
				return false;
			}	
		}
		
		
		
		//step 3:ѵ��
		for(int i=0;i<numOfFolds;i++)
		{
			int j = i+1;
			//=======================================================================================================
			//======================================== svm �����޸�    =================================================
			//=======================================================================================================
			//String[] trainArgs = {"-s","1","-n","0.5","-p","0.1",fileAddrTrim+"_"+j+"_scale.txt", fileAddrWithoutFileName+"\\"+j+".model"};
			//======String[] trainArgs = {"-s","1","-n","0.5","-p","0.1",fileAddrTrim+"_"+j+"_scale.txt", fileAddrWithoutFileName+"\\"+j+".model"};
			String[] trainArgs = {"-s","1","-p","0.1",fileAddrTrim+"_"+j+"_scale.txt", fileAddrWithoutFileName+File.separator+j+".model"};
			//String[] trainArgs = {"-t","2",fileAddrTrim+"_"+j+"_scale.txt", fileAddrWithoutFileName+"\\"+j+".model"};
			try {
				String modelFile = svm_train.main(trainArgs);
			} catch (IOException e) {				
				e.printStackTrace();
				System.err.println("ѵ������" + j + "ʱ���?����");
			}
			//System.out.println("--------- modelFile:" + modelFile + " ---------");
		}
		
		System.out.println("ѵ����̳ɹ���ɣ�����");
		return true;
	}
	
	
	public Integer getBuildingId() {
		return buildingId;
	}

	public void setBuildingId(Integer buildingId) {
		this.buildingId = buildingId;
	}

	public Integer getFloor() {
		return floor;
	}

	public void setFloor(Integer floor) {
		this.floor = floor;
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
	public static  int getBuildFloorLocation() throws SQLException {
   int    locationNum=0;
			// //ȷ��ÿ���ɼ����ָ�Ƶ㶼����20
			String sql = "select  count(1) num  from   "
					+ " x_tmnlpos where     x_tmnlpos.floor_no=? and "
					+ "  x_tmnlpos. build_no=?   ";

			ResultSet rs = DaoUtil.queryData(sql, new Object[] { "15",
					"29" });

			if (rs.next()) {
				locationNum = rs.getInt("num");
			}

	 
		return locationNum;
	}
	public static void main(String args[]) throws Exception
	{
		/*
		//����:ѵ���ļ�·���������ļ�·���������ļ�ÿ������5����ѵ��������ص���������28��������4��
		SVMTrainer tt = new SVMTrainer("E:\\indoorGeo\\angletest\\labtest_angle_train.txt", 
				"E:\\indoorGeo\\angletest\\labtest_angle_test.txt", 5, 28, 4);
		tt.run();
		*/
		int loc=getBuildFloorLocation();
		
		SVMTrainer trainer = new SVMTrainer(15, 29, 5, 360, 1);
		trainer.buildTrainModel("G:\\������\\���ڶ�λ\\SRC\\icscnServer\\indoor\\");
		
	}
	
}
