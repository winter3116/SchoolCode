package stage1;
import java.io.File;
//import java.io.PrintWriter;
import java.util.Scanner;
import java.io.IOException;

public class Evaluate {

	public static void main(String[] args)throws IOException {
		// TODO Auto-generated method stub
		double hanLoss;
		double rankLoss;
		final int n=100;
		final int q=6;
		double hansum = 0;
		double ranksum = 0;
		File inputFile1 = new File("Stage1_train_label1-100.CSV");
		File inputFile2 = new File("Stage1_train_label1-100_pre.CSV");
		File inputFile3 = new File("Stage1_train_confidence1-100.CSV");
		Scanner input1 = new Scanner(inputFile1);
		Scanner input2 = new Scanner(inputFile2);
		Scanner input3 = new Scanner(inputFile3);
		int reallab[][] = new int[100][6];
		int prelab[][] = new int[100][6];
		double confid[][] = new double[100][6];
		int i = 0, j;
		while(input1.hasNextLine()){
			String[] str = input1.nextLine().split(",");
			for(j=0; j<str.length-1; j++){
				reallab[i][j] = Integer.valueOf(str[j+1]);
			}
			i++;
		}
		i = 0;
		while(input2.hasNextLine()){
			String[] str = input2.nextLine().split(",");
			for(j=0; j<str.length; j++){
				prelab[i][j] = Integer.valueOf(str[j]);
			}
			i++;
		}
		i = 0;
		while(input3.hasNextLine()){
			String[] str = input3.nextLine().split(",");
			for(j=0; j<str.length; j++){
				confid[i][j] = Double.valueOf(str[j]);
			}
			i++;
		}
		input1.close();
		input2.close();
		input3.close();
		for(i=0; i<n; i++){
			for(j=0; j<q; j++){
				if(reallab[i][j]!=prelab[i][j]){
					hansum += 1;
				}
			}
		}
		hanLoss = hansum/(n*q);
		for(i=0; i<n; i++){
			int num0 = 0;
			int num1 = 0;
			int num = 0;
			int[] str0 = new int[q];
			int[] str1 = new int[q];
			int a=0, b=0;
			for(j=0; j<q; j++){
				if(reallab[i][j]==1){
					num1++;
					str1[a] = j;
					a++;
				}
				else{
					num0++;
					str0[b] = j;
					b++;
				}
			}
			for(int k=0; k<num1; k++){
				for(int l=0; l<num0; l++){
					if(confid[i][str1[k]]<confid[i][str0[l]]){
						num++;
					}
				}
			}
			ranksum +=num/(double)(num0+num1);
		}
		rankLoss = ranksum/n;
		System.out.println(hanLoss);
		System.out.println(rankLoss);
	}

}
