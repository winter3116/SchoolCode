package stage1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
//import java.util.HashSet;
import java.io.PrintWriter;

public class Change {
	public File inputFile;
	public File outputFile;

	public Change(File inputFile, File outputFile){
		this.inputFile = inputFile;
		this.outputFile = outputFile;
	}
	public void changedo()throws FileNotFoundException {
		int i = 0;
		PrintWriter output = new PrintWriter(outputFile);
		Scanner input = new Scanner(inputFile);
		System.out.println("转换开始");
		while(input.hasNextLine()){
			String[] str = input.nextLine().split(",");
			for(i=1; i<73; i++){
				float f = Float.valueOf(str[i]);
				switch(i){
				case 4: f=(-f)/100.0f;break;
				case 5:
				case 20: f=f/12.0f;break;
				case 6: f=(f+3)/7.0f;break;
				case 7: f=f/4.0f;break;
				case 8: f=(f+1)/3.5f;break;
				case 9: f=(f+0.5f)/3.0f;break;
				case 10:
				case 12:
				case 15: f=(f+0.5f)/1.6f;break;
				case 11: f=(f+1)/3.0f;break;
				case 13: f=(f+0.8f)/2.0f;break;
				case 14: f=(f+0.6f)/2.0f;break;
				case 16: f=(f+0.2f)/1.6f;break;
				case 21:
				case 38: f=f/2.5f;break;
				case 22: f=f/2.0f;break;
				case 23:
				case 65:
				case 67:
				case 70:
				case 71: f=f/1.8f;break;
				case 24:
				case 25:
				case 39: f=f/1.4f;break;
				case 36: f=f/14.0f;break;
				case 37: f=f/3.0f;break;
				case 52: f=f/6.0f;break;
				case 66: f=f/120.0f;break;
				case 68: f=f/250.0f;break;
				case 69: f = f==2?0:1;break;
				case 72: f=f/3.5f;break;
				default:
					break;
				}
				if(i!=72){
					output.print(f+",");
				}
				else{
					output.println(f);
				}
			}
		}
		System.out.println("转换完成");
		input.close();
		output.close();

	}
}
