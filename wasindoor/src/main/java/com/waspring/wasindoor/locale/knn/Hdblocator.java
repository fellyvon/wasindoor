package com.waspring.wasindoor.locale.knn;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

import com.waspring.wasindoor.locale.GeomagneticEntity;
import com.waspring.wasindoor.locale.entry.LocationEntity;

public class Hdblocator {

	public GeomagneticEntity entity;
	public String DirFileAddr;
	public Integer rowsInOneline;
	public Integer K;
	public int base1;
	public int base2;
	public LinkedList<Integer> Hbdata;
	public LinkedList<Integer> UpdatedHbdata;

	// 历史轨迹时， firstfive中距离现在定位点最远距离的那个标号
	public int hdbresult;
	public int result;

	// k 表示单个数据定位中设置的近邻数
	public int hdblocate(GeomagneticEntity entity, String DirFileAddr,
			Integer rowsInOneline, Integer K, int base1, int base2,
			LinkedList<Integer> Hbdata)

	{
		KnnLocator SingleLocator = new KnnLocator();
		result = SingleLocator.locate(entity, DirFileAddr, rowsInOneline, K,
				base1, base2);

		LocationEntity ent = new LocationEntity();
		double threshold = ent.setBaseDist(DirFileAddr, entity.getBuildingId(),
				entity.getFloor(), base1, base2);
		System.out.println("Threshold:" + threshold);

		String completeFileAddr = DirFileAddr + File.separator
				+ entity.getBuildingId() + File.separator + entity.getFloor()
				+ File.separator + "map.txt";

		int m = Hbdata.size();// 确定历史轨迹有几个。
		double[] Distance = new double[m];

		double[] xs = new double[m];
		double[] ys = new double[m];

		double resultX = 0;
		double resultY = 0;
		System.out.println("completeFileAddr3="+completeFileAddr);
		File mapfile = new File(completeFileAddr);
		if (!mapfile.exists()) {
			System.out.println("ERROR: 地图文件不存在15！！！" + "请检查输入的完整文件路径是否正确.");
			return -1;
		}

		int count = 0;

		try {
			BufferedReader reader = new BufferedReader(new FileReader(mapfile));

			String[] strs = null;
			String lineString = null;

			while (((lineString = reader.readLine()) != null)) {

				if (lineString.trim().length() == 0) {
					continue;
				}

				strs = lineString.split("\\s+"); // 一个或任意多个空格分隔

				if (strs.length != 3) {
					System.out.println("ERROR: 地图文件" + completeFileAddr
							+ " 中: line " + (count + 1)
							+ " has a wrong format!!!");
					return -1;
				}

				int location = Integer.parseInt(strs[0]);
				double x = Double.parseDouble(strs[1]);
				double y = Double.parseDouble(strs[2]);

				if (location == result) {
					resultX = x;
					resultY = y;
				}

				for (int i = 0; i < m; i++) {
					if (location == Hbdata.get(i))
						xs[i] = x;
					ys[i] = y;
				}
			}
			reader.close();
		} catch (IOException ex) {
			ex.printStackTrace();
			return -1;
		}

		for (int i = 0; i < m; i++) {
			Distance[i] = (xs[i] - resultX) * (xs[i] - resultX)
					+ (ys[i] - resultY) * (ys[i] - resultY);
		}

		boolean successflag = false;

		int j = 0;
		while (successflag == false && j < m) {
			if (Distance[j] <= threshold) {
				hdbresult = result;
				successflag = true;
			} else {
				j++;
			}
		}

		if (successflag == false) {
			hdbresult = Hbdata.get(m - 1);
		}// 如果所得的位置同历史数据位置不一致，返回结果为历史数据链表中的最后一个结果，即上一次的定位结果

		// step2: 更新hbdata
		double MaxDis = 0;
		int index = 0;

		for (int i = 0; i < m; i++) {
			if (Distance[i] > MaxDis) {
				MaxDis = Distance[i];
				index = i;
			}
		}

		Hbdata.remove(index);// 删除历史数据中距离现在位置最远的点
		Hbdata.addLast(hdbresult);// 将最近一次定位成功的结果保存在链表的最后一个位置上

		UpdatedHbdata = Hbdata;

		return hdbresult;
	}

	public LinkedList<Integer> GetUpdatedHbdata() {
		return UpdatedHbdata;
	}

	public int GetResult() {
		return result;
	}
}
