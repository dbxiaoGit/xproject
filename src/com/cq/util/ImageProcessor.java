/**
 * @ version 1.0
 * @ author dbxiao
 */

package com.cq.util;

public interface ImageProcessor {
	public int getGray(int rgb);
	public int getAC(int[][] gray,int x,int y,int width,int height);
	public int avgGray(int[][] points ,int width,int height);
}
