package com.bradley.readinggenerator;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Reading {
	private float zeroReading, endReading;
	private int divisions;
	
	public Reading(float a, float b, int divisions) {
		this.zeroReading = a;
		this.endReading = b;
		this.divisions = divisions;
	}
	
	public float[] getReadings() {
		float[] r = new float[divisions + 1];
		
		r[0] = zeroReading;
		float diff = (endReading - zeroReading) / divisions;
		for (int i = 1; i <= divisions; ++i) {
			r[i] = r[i - 1] + diff + ThreadLocalRandom.current().nextInt(-4, 5);
		}
		
		return r;
	}
	
	public static float[] getAverage(List<Reading> list) {
		int len = list.get(0).getReadings().length;
		float[] avg = new float[len];
		Arrays.fill(avg, 0);
		
		float[][] data = new float[list.size()][len];
		for (int i = 0; i < list.size(); ++i) {
			data[i] = list.get(i).getReadings();
		}
		int lenY = data.length;
		for (int i = 0; i < len; ++i) {
			for (int j = 0; j < lenY; ++j) {
				avg[i] += data[j][i];
			}
			avg[i] /= lenY;
		}
		
		return avg;
	}
}
