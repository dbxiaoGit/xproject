/**
 * @ version 1.0
 * @ author dbxiao
 */

package com.cq.util;

import java.awt.Color;

public abstract class ComplexExcuter extends SimpleExcuter implements ImageProcessor  {
	public ComplexExcuter(int h, int m, int s) {
		super(h, m, s);
		// TODO Auto-generated constructor stub
	}
	@Override
	public int getGray(int rgb) {
		Color c = new Color(rgb);
		int r = c.getRed();
		int g = c.getGreen();
		int b = c.getBlue();
		return (r+g+b)/3;
	}
	@Override    
	public int getAC(int[][] gray,int x,int y,int width,int height) {
		int sum;
		sum = gray[x][y]
		        +(x == 0 || y == 0 ? 255 : gray[x-1][y-1])
		        +(x == 0 ? 255 : gray[x-1][y])
		        +(x == 0 || y == height - 1  ? 255 : gray[x-1][y+1])
		        +(y == 0 ? 255 : gray[x][y-1])
		        +(y == height -1 ? 255 : gray[x][y+1])
		        +(x == width -1 || y == 0 ? 255 : gray[x+1][y-1])
		        +(x == width-1 ? 255 : gray[x+1][y])
		        +(x == width-1 || y == height - 1 ? 255 : gray[x+1][y+1]);
		return sum/9;
	}
	@Override
	public int avgGray(int[][] points ,int width,int height) {
    	int sum = 0 ;
    	for (int i = 0 ;i < width ; i++) {
    		for (int j = 0 ; j < height ; j++) {
    			sum += points[i][j];
    		}
    	}
    	return sum/width/height;
    }
	
}
