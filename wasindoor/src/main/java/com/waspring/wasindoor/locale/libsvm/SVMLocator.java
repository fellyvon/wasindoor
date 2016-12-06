package com.waspring.wasindoor.locale.libsvm;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.StringTokenizer;
import java.util.Vector;

import com.waspring.wasindoor.locale.GeomagneticEntity;

public class SVMLocator {
	private Double accuracy;

	public SVMLocator() {
		accuracy = -1.0;
	}

	private double atof(String s) {
		return Double.valueOf(s).doubleValue();
	}

	private int atoi(String s) {
		return Integer.parseInt(s);
	}

	// �����������ļ��Ķ�λ��������ļ�
	private void predict(BufferedReader input, DataOutputStream output,
			svm_model model, int predict_probability) throws IOException {
		int correct = 0;
		int total = 0;
		double error = 0;
		double sumv = 0, sumy = 0, sumvv = 0, sumyy = 0, sumvy = 0;

		int svm_type = svm.svm_get_svm_type(model);
		int nr_class = svm.svm_get_nr_class(model);
		double[] prob_estimates = null;

		if (predict_probability == 1) {
			if (svm_type == svm_parameter.EPSILON_SVR
					|| svm_type == svm_parameter.NU_SVR) {
				System.out
						.print("Prob. model for test data: target value = predicted value + z,\nz: Laplace distribution e^(-|z|/sigma)/(2sigma),sigma="
								+ svm.svm_get_svr_probability(model) + "\n");
			} else {
				int[] labels = new int[nr_class];
				svm.svm_get_labels(model, labels);
				prob_estimates = new double[nr_class];
				output.writeBytes("labels");
				for (int j = 0; j < nr_class; j++)
					output.writeBytes(" " + labels[j]);
				output.writeBytes("\n");
			}
		}
		while (true) {
			String line = input.readLine();
			if (line == null)
				break;

			StringTokenizer st = new StringTokenizer(line, " \t\n\r\f:");

			double target = atof(st.nextToken());
			int m = st.countTokens() / 2;
			svm_node[] x = new svm_node[m];
			for (int j = 0; j < m; j++) {
				x[j] = new svm_node();
				x[j].index = atoi(st.nextToken());
				x[j].value = atof(st.nextToken());
			}

			double v;
			if (predict_probability == 1
					&& (svm_type == svm_parameter.C_SVC || svm_type == svm_parameter.NU_SVC)) {
				v = svm.svm_predict_probability(model, x, prob_estimates);
				output.writeBytes(v + " ");
				for (int j = 0; j < nr_class; j++)
					output.writeBytes(prob_estimates[j] + " ");
				output.writeBytes("\n");
			} else {
				v = svm.svm_predict(model, x);
				output.writeBytes(v + "\n");
			}

			if (v == target)
				++correct;
			error += (v - target) * (v - target);
			sumv += v;
			sumy += target;
			sumvv += v * v;
			sumyy += target * target;
			sumvy += v * target;
			++total;
		}
		if (svm_type == svm_parameter.EPSILON_SVR
				|| svm_type == svm_parameter.NU_SVR) {
			System.out.print("Mean squared error = " + error / total
					+ " (regression)\n");
			System.out.print("Squared correlation coefficient = "
					+ ((total * sumvy - sumv * sumy) * (total * sumvy - sumv
							* sumy))
					/ ((total * sumvv - sumv * sumv) * (total * sumyy - sumy
							* sumy)) + " (regression)\n");
		} else {
			// System.out.print("Accuracy = "+(double)correct/total*100+
			// "% ("+correct+"/"+total+") (classification)\n");
			accuracy = (double) correct / total;
		}
	}

	// ���ڵ���GeomagneticEntity�Ķ�λ
	private Integer predict(GeomagneticEntity gmEntity, svm_model model,
			int predict_probability) {
		int correct = 0;
		int total = 0;
		double error = 0;
		double sumv = 0, sumy = 0, sumvv = 0, sumyy = 0, sumvy = 0;

		int svm_type = svm.svm_get_svm_type(model);
		int nr_class = svm.svm_get_nr_class(model);
		double[] prob_estimates = null;

		if (predict_probability == 1) {
			if (svm_type == svm_parameter.EPSILON_SVR
					|| svm_type == svm_parameter.NU_SVR) {
				System.out
						.print("Prob. model for test data: target value = predicted value + z,\nz: Laplace distribution e^(-|z|/sigma)/(2sigma),sigma="
								+ svm.svm_get_svr_probability(model) + "\n");
			} else {
				int[] labels = new int[nr_class];
				svm.svm_get_labels(model, labels);
				prob_estimates = new double[nr_class];
			}
		}

		Integer target = gmEntity.getTargetLable();
		double targetLocation = -100000.5; // ��һ�������ܵ����ֵ

		if (target != null) {
			targetLocation = target.intValue();
		}

		int m = 3; // ��ǰֻʹ����x,y,z�����ǿ
		svm_node[] x = new svm_node[m];

		x[0] = new svm_node();
		x[0].index = 1;
		x[0].value = gmEntity.getxMagnetic();

		x[1] = new svm_node();
		x[1].index = 2;
		x[1].value = gmEntity.getyMagnetic();

		x[2] = new svm_node();
		x[2].index = 3;
		x[2].value = gmEntity.getzMagnetic();

		double v;
		if (predict_probability == 1
				&& (svm_type == svm_parameter.C_SVC || svm_type == svm_parameter.NU_SVC)) {
			v = svm.svm_predict_probability(model, x, prob_estimates);
		} else {
			v = svm.svm_predict(model, x);
		}

		if (v == targetLocation)
			++correct;
		error += (v - targetLocation) * (v - targetLocation);
		sumv += v;
		sumy += targetLocation;
		sumvv += v * v;
		sumyy += targetLocation * targetLocation;
		sumvy += v * targetLocation;
		++total;

		if (svm_type == svm_parameter.EPSILON_SVR
				|| svm_type == svm_parameter.NU_SVR) {
			System.out.print("Mean squared error = " + error / total
					+ " (regression)\n");
			System.out.print("Squared correlation coefficient = "
					+ ((total * sumvy - sumv * sumy) * (total * sumvy - sumv
							* sumy))
					/ ((total * sumvv - sumv * sumv) * (total * sumyy - sumy
							* sumy)) + " (regression)\n");
		} else {
			// System.out.print("Accuracy = "+(double)correct/total*100+
			// "% ("+correct+"/"+total+") (classification)\n");
			accuracy = (double) correct / total;
		}

		Integer result = (int) v;

		return result;
	}

	private void exit_with_help() {
		System.err
				.print("usage: svm_predict [options] test_file model_file output_file\n"
						+ "options:\n"
						+ "-b probability_estimates: whether to predict probability estimates, 0 or 1 (default 0); one-class SVM not supported yet\n");
		System.exit(1);
	}

	// ��λ����ļ�
	public Double locateIntoFile(String argv[]) throws IOException {
		int i, predict_probability = 0;

		// parse options
		for (i = 0; i < argv.length; i++) {
			if (argv[i].charAt(0) != '-')
				break;
			++i;
			switch (argv[i - 1].charAt(1)) {
			case 'b':
				predict_probability = atoi(argv[i]);
				break;
			default:
				System.err.print("Unknown option: " + argv[i - 1] + "\n");
				exit_with_help();
			}
		}
		if (i >= argv.length - 2)
			exit_with_help();
		try {
			BufferedReader input = new BufferedReader(new FileReader(argv[i]));
			DataOutputStream output = new DataOutputStream(
					new BufferedOutputStream(new FileOutputStream(argv[i + 2])));
			svm_model model = svm.svm_load_model(argv[i + 1]);
			if (predict_probability == 1) {
				if (svm.svm_check_probability_model(model) == 0) {
					System.err
							.print("Model does not support probabiliy estimates\n");
					System.exit(1);
				}
			} else {
				if (svm.svm_check_probability_model(model) != 0) {
					System.out
							.print("Model supports probability estimates, but disabled in prediction.\n");
				}
			}
			predict(input, output, model, predict_probability);
			input.close();
			output.close();
		} catch (FileNotFoundException e) {
			exit_with_help();
		} catch (ArrayIndexOutOfBoundsException e) {
			exit_with_help();
		}
		return accuracy;
	}

	// �����ش���ݶ�λ������λ�ñ��.���Զ���׼�����
	public Integer locate(GeomagneticEntity gmEntity, String gmLocatorFileAddr,
			Integer numOfFolds) throws IOException {
		SVMScaler scaler = new SVMScaler();
		gmEntity = scaler.scale(gmEntity, gmLocatorFileAddr);

		int predict_probability = 0;

		double angle = gmEntity.getzAngle();

		double period = 360 / numOfFolds;
		int NoOfFold = (int) Math.ceil((angle + 180) / period); // NO of Fold

		String modelFileAddr = gmLocatorFileAddr + File.separator
				+ gmEntity.getBuildingId() + File.separator + gmEntity.getFloor() + File.separator
				+ NoOfFold + ".model";
		svm_model model = svm.svm_load_model(modelFileAddr);

		if (predict_probability == 1) {
			if (svm.svm_check_probability_model(model) == 0) {
				System.err
						.print("Model does not support probabiliy estimates\n");
				System.exit(1);
			}
		} else {
			if (svm.svm_check_probability_model(model) != 0) {
				System.out
						.print("Model supports probability estimates, but disabled in prediction.\n");
			}
		}
		Integer location = predict(gmEntity, model, predict_probability);

		return location;
	}

	// һ�ζ�λ������¼.���Զ���׼�����
	public Vector<Integer> locate(Vector<GeomagneticEntity> gmEntities,
			String gmLocatorFileAddr, int numOfFolds) throws  Exception {

		SVMScaler scaler = new SVMScaler();
		gmEntities = scaler.scale(gmEntities, gmLocatorFileAddr);

		int vecSize = gmEntities.size();
		Vector<Integer> results = new Vector<Integer>(vecSize);

		int predict_probability = 0;

		for (int i = 0; i < vecSize; i++) {
			GeomagneticEntity gmEntity = gmEntities.get(i);

			double angle = gmEntity.getzAngle();

			double period = 360 / numOfFolds;
			int NoOfFold = (int) Math.ceil((angle + 180) / period); // NO of
																	// Fold

			String modelFileAddr = gmLocatorFileAddr + File.separator
					+ gmEntity.getBuildingId() + File.separator + gmEntity.getFloor()
					+ File.separator + NoOfFold + ".model";
			svm_model model = svm.svm_load_model(modelFileAddr);

			if (predict_probability == 1) {
				if (svm.svm_check_probability_model(model) == 0) {
					System.err
							.print("Model does not support probabiliy estimates\n");
					System.exit(1);
				}
			} else {
				if (svm.svm_check_probability_model(model) != 0) {
					System.out
							.print("Model supports probability estimates, but disabled in prediction.\n");
				}
			}
			Integer location = predict(gmEntity, model, predict_probability);
			results.add(location);
		}

		return results;
	}

	// ����ʵ���ļ��Ķ�λ,ÿ��4��
	// ����Ϊ�ļ�������ļ�Ŀ¼�������ļ���
	public boolean locate_file(String gmLocatorFileAddr, Integer buildingId,
			Integer floor, Integer rowsInOneline, Integer numOfFolds,
			String filename) {
		String completeFileAddr = gmLocatorFileAddr + File.separator + buildingId + File.separator
				+ floor + File.separator + filename;

		int end_index = completeFileAddr.lastIndexOf(".");
		String fileAddrTrim = completeFileAddr.substring(0, end_index);

		File inputfile = new File(completeFileAddr);
		if (!inputfile.exists()) {
			System.out
					.println("File Do Not Exist! �ļ������ڣ�������������������ļ�·�������ļ����Ƿ���ȷ.");
			return false;
		}

		int line = 0;

		try {
			System.out.println("����Ϊ��λ��ȡ�ļ����ݣ�һ�ζ�һ���У�");
			BufferedReader reader = new BufferedReader(
					new FileReader(inputfile));
			FileWriter writer = new FileWriter(fileAddrTrim + "Result");

			String[] strs = null;
			String lineString = null;

			// һ�ζ���һ�У�ֱ������nullΪ�ļ�����
			while ((lineString = reader.readLine()) != null) {

				if (lineString.trim().length() == 0) {
					continue;
				}

				strs = lineString.split("\\s+"); // һ�����������ո�ָ�

				if (strs.length != rowsInOneline) {
					System.out.println("�趨λ���ļ�" + completeFileAddr
							+ " ��: line " + (line + 1)
							+ " has a wrong format!!!");
					return false;
				}

				double zAngle = Double.parseDouble(strs[0]);
				double xMag = Double.parseDouble(strs[1]);
				double yMag = Double.parseDouble(strs[2]);
				double zMag = Double.parseDouble(strs[3]);

				GeomagneticEntity entity = new GeomagneticEntity();
				entity.setBuildingId(buildingId);
				entity.setFloor(floor);
				entity.setzAngle(zAngle);
				entity.setxMagnetic(xMag);
				entity.setyMagnetic(yMag);
				entity.setzMagnetic(zMag);

				Integer location = locate(entity, gmLocatorFileAddr, numOfFolds);
				writer.write(location + "\r\n");

				line++;
			}

			reader.close();
			writer.flush();
			writer.close();

		} catch (IOException ex) {
			ex.printStackTrace();
			return false;
		}

		System.out.println("����ɶ�λ������λ" + line + "�С�");

		return true;
	}

}
