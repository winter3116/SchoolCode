package stage1;

import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;
import java.io.IOException;  

public class Main {
	
		public static void main(String[] args) throws IOException {
			Change ch1 = new Change(new File("Stage1_train_feature1-100.CSV"),new File("Change_stage1_train_feature1-100.CSV"));
			ch1.changedo();
			Change ch2 = new Change(new File("Stage1_train_feature.CSV"),new File("Change_stage1_train_feature.CSV"));
			ch2.changedo();
            Bp bp = new Bp(72, 18, 6); 
            File inputFile1 = new File("Change_stage1_train_feature.CSV");
            File inputFile2 = new File("Stage1_train_label.CSV");
            Scanner input1 = new Scanner(inputFile1);
            Scanner input2 = new Scanner(inputFile2);
            double fetrain[][] = new double[400][72] ;
            double labtrain[][] = new double[400][6];
            int i=0;
            while(input1.hasNextLine()){
            	String[] str = input1.nextLine().split(",");
            	for(int j=0; j<str.length; j++){
            		fetrain[i][j] = Double.valueOf(str[j]);
            	}
            	i++;
            }
            i=0;
            while(input2.hasNextLine()){
            	String[] str = input2.nextLine().split(",");
            	for(int j=0; j<str.length-1; j++){
            		labtrain[i][j] = Double.valueOf(str[j+1]);
            	}
            	i++;
            }
            input1.close();
            input2.close();
            for (i = 0; i != 200; i++) {  
               for(int j=0; j<fetrain.length; j++){  
                   
                    bp.train(fetrain[j],labtrain[j]);  
                }  
            }  
      
            System.out.println("训练完毕。");
            File inputFile3 = new File("Change_stage1_train_feature1-100.CSV");
            Scanner input3 = new Scanner(inputFile3);
            File outputFile1 = new File("Stage1_train_label1-100_pre.CSV");
            File outputFile2 = new File("Stage1_train_confidence1-100.CSV");
            PrintWriter output1 = new PrintWriter(outputFile1);
            PrintWriter output2 = new PrintWriter(outputFile2);
            double fetest[][] = new double[100][72];
             i=0;
            while(input3.hasNextLine()){
            	String[] str = input3.nextLine().split(",");
            	for(int j=0; j<str.length; j++){
            		fetest[i][j] = Double.valueOf(str[j]);
            	}
            	i++;
            }
            int labtest[][] = new int[100][6];
            double conftest[][] = new double[100][6];
            for(i=0; i<fetest.length; i++){
            	conftest[i] = bp.test(fetest[i]);
            }
            for(i=0; i<conftest.length; i++){
            	for(int j=0; j<conftest[i].length; j++){
            		if(j!=conftest[i].length-1){
            			if(conftest[i][j]<0.5){
                			labtest[i][j] = 0;
                			output2.print(conftest[i][j]+",");
                			output1.print(labtest[i][j]+",");
                		}
                		else{
                			labtest[i][j] = 1;
                			output2.print(conftest[i][j]+",");
                			output1.print(labtest[i][j]+",");
                		}
            		}
            	    else{
            				if(conftest[i][j]<0.5){
                    			labtest[i][j] = 0;
                    			output2.print(1-conftest[i][j]);
                    			output1.print(labtest[i][j]);
                    		}
                    		else{
                    			labtest[i][j] = 1;
                    			output2.print(conftest[i][j]);
                    			output1.print(labtest[i][j]);
                    		}
            			}	
            	}
            	output1.println();
            	output2.println();
            }
            output1.close();
            output2.close();
            input3.close();
            System.out.println("程序运行完毕！");
	}
}
