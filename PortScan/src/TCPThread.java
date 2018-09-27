/*import java.net.*;
import java.io.*;

public class TCPThread implements Runnable{
	
	public static InetAddress hostAddress;
	
	//查询方式：0为ip；1为主机名
	public static int type;

	//扫描的ip地址
	public static String ipAdress;

	//扫描的主机名
	public static String hostName = "";
	
	//端口的类别
	String porttype = "0";
	
	
	 *构造函数
	 
	private int port;
	
	public TCPThread(int port){
		this.port =  port;    
	} 
	
	
	 *运行函数
	 
	public void run() {
		
		Socket theTCPsocket;

		//根据ip地址进行扫描
		if(type == 0){
				
			try{
				//在给定IP 地址确定主机地址
				hostAddress=InetAddress.getByName(ipAdress);
			}
			catch(UnknownHostException e){
			}
		}
		
		//按照主机名进行端口扫描
		if(type == 1){
			try{
				//在给定主机名地址确定主机地址
				hostAddress=InetAddress.getByName(hostName);
			}
			catch(UnknownHostException e){
			}
					
		}
				
			//不同的端口扫描
			

				try{
					theTCPsocket=new Socket(hostAddress,port);
					theTCPsocket.close();
					ThreadScan.result.append(" " + port + "open" + "\n");
					}
					catch (IOException e){
						ThreadScan.result.append(" "+port+"close"+"\n");
					}
		
	}
}
*/