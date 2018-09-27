package com.AOE;

public class Main {

	public static void main(String[] args) {
		Image image = new Image();
		
		long start = System.currentTimeMillis();
		
		//蚁群算法
		ACO aco = new ACO(image);
		aco.doIt();
		
		//遗传算法
		GA ga = new GA(image);
		ga.doIt();
		
		long end = System.currentTimeMillis();
		System.out.println(end - start);

	}

}
