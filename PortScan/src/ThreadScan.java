/*import java.net.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.io.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;


 *实现扫描的主体程序
 
public class ThreadScan{
	
	public static JFrame main=new JFrame("TCP端口扫描器");
	
		//显示扫描结果
		public static JTextArea result=new JTextArea("",8,40);
		//滚动条面板
	    public static JScrollPane resultPane = new JScrollPane(result,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	
	
		//输入主机名文本框
		public static JTextField hostName=new JTextField("localhost",15);
		
		//输入ip地址的输入框
		public static JTextField ipAddress=new JTextField("0.0.0.0",15);
	
		//输入最小端口的输入框
		public static JTextField minPort=new JTextField("0",4);
		//输入最大端口的输入框
		public static JTextField maxPort=new JTextField("1000",4);
		//输入最大线程数量的输入框
		public static JTextField maxThread=new JTextField("100",3);
	
		//错误提示框
		public static JDialog DLGError=new JDialog(main,"错误!");
		public static JLabel DLGINFO=new JLabel("");
		
		public static JLabel type=new JLabel("请选择：");
	
		//扫描类型
		public static JRadioButton radioIp = new JRadioButton("IP地址：");
		public static JRadioButton radioHost = new JRadioButton("主机名：",true);
		//单选框组
		public static ButtonGroup group = new ButtonGroup();

		public static JLabel P1=new JLabel("端口范围:");
		public static JLabel P2=new JLabel("~");
		public static JLabel P3=new JLabel("~");
		public static JLabel TNUM=new JLabel("线程数:");
		public static JLabel RST=new JLabel("扫描结果:   ");
		public static JLabel con=new JLabel("            ");

		//定义按钮
		public static JButton OK = new JButton("确定");
		public static JButton Submit = new JButton("开始扫描");
		public static JButton Cancel = new JButton("退出");
		public static JButton saveButton = new JButton("保存扫描结果");

		public static void main(String[] args){

		main.setSize(500,400);
		main.setLocation(300,300);
		main.setResizable(false);
		main.setLayout(new GridBagLayout());
		main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		DLGError.setSize(300,100);
		DLGError.setLocation(400,400);

		//采用表格包型布局
		Container mPanel = main.getContentPane();
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(10,0,0,10);

		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 10;
		c.fill = GridBagConstraints.BOTH;
		c.anchor = GridBagConstraints.CENTER;
		mPanel.add(type,c);

		group.add(radioIp);
		group.add(radioHost);

		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 1;
		c.fill = GridBagConstraints.BOTH;
		c.anchor = GridBagConstraints.CENTER;
		mPanel.add(radioIp,c);

		c.gridx = 1;
		c.gridy = 1;
		c.gridwidth = 1;
		c.fill = GridBagConstraints.BOTH;
		c.anchor = GridBagConstraints.CENTER;
		mPanel.add(ipAddress,c);

		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 1;
		c.fill = GridBagConstraints.BOTH;
		c.anchor = GridBagConstraints.CENTER;
		mPanel.add(radioHost,c);

		c.gridx = 1;
		c.gridy = 2;
		c.gridwidth = 3;
		c.fill = GridBagConstraints.BOTH;
		c.anchor = GridBagConstraints.CENTER;
		mPanel.add(hostName,c);

		c.gridx = 0;
		c.gridy = 3;
		c.gridwidth = 1;
		c.fill = GridBagConstraints.BOTH;
		c.anchor = GridBagConstraints.CENTER;
		mPanel.add(P1,c);

		c.gridx = 1;
		c.gridy = 3;
		c.gridwidth = 1;
		c.fill = GridBagConstraints.BOTH;
		c.anchor = GridBagConstraints.CENTER;
		mPanel.add(minPort,c);

		c.gridx = 2;
		c.gridy = 3;
		c.gridwidth = 1;
		c.fill = GridBagConstraints.BOTH;
		c.anchor = GridBagConstraints.CENTER;
		mPanel.add(P3,c);

		c.gridx = 3;
		c.gridy = 3;
		c.gridwidth = 1;
		c.fill = GridBagConstraints.BOTH;
		c.anchor = GridBagConstraints.CENTER;
		mPanel.add(maxPort,c);

		c.gridx = 0;
		c.gridy = 4;
		c.gridwidth = 1;
		c.fill = GridBagConstraints.BOTH;
		c.anchor = GridBagConstraints.CENTER;
		mPanel.add(TNUM,c);

		c.gridx = 1;
		c.gridy = 4;
		c.gridwidth = 3;
		c.fill = GridBagConstraints.BOTH;
		c.anchor = GridBagConstraints.CENTER;
		mPanel.add(maxThread,c);

		c.gridx = 0;
		c.gridy = 5;
		c.gridwidth = 3;
		c.fill = GridBagConstraints.VERTICAL;
		c.anchor = GridBagConstraints.CENTER;
		mPanel.add(Submit,c);
		
		

		c.gridx = 6;
		c.gridy = 5;
		c.gridwidth = 4;
		c.fill = GridBagConstraints.VERTICAL;
		c.anchor = GridBagConstraints.CENTER;
		mPanel.add(Cancel,c);

		c.gridx = 0;
		c.gridy = 6;
		c.gridwidth = 10;
		c.fill = GridBagConstraints.BOTH;
		c.anchor = GridBagConstraints.CENTER;
		mPanel.add(RST,c);

		//设置文本区域可以换行
		result.setLineWrap(true);
		//设置文本区域不可编辑
		result.setEditable(false);

		c.gridx = 0;
		c.gridy = 7;
		c.gridwidth = 10;
		c.gridheight = 4;
		c.fill = GridBagConstraints.VERTICAL;
		c.anchor = GridBagConstraints.CENTER;
		mPanel.add(resultPane,c);

		Container dPanel = DLGError.getContentPane();
		dPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		dPanel.add(DLGINFO);
		dPanel.add(OK);

		Submit.addActionListener(new SubmitAction());
		Cancel.addActionListener(new CancleAction());
		OK.addActionListener(new OKAction());	

		saveButton.addActionListener(new java.awt.event.ActionListener() { 
			public void actionPerformed(java.awt.event.ActionEvent e) {    
				JFileChooser fc=new JFileChooser();
				int returnVal=fc.showSaveDialog(null);
				
				//点击“保存”
				if(returnVal == 0){
					File saveFile=fc.getSelectedFile();
					try {
						FileWriter writeOut = new FileWriter(saveFile);
						writeOut.write(ThreadScan.result.getText());
						writeOut.close();
					}
					catch (IOException ex) {
						System.out.println("保存失败");
					}
				}
				//点击“取消”
				else
					return;
			}
		});

		main.setVisible(true);
	}
}


 *实现“取消”功能
 *退出程序
 
class CancleAction implements ActionListener{

	public void actionPerformed (ActionEvent e){
			System.exit(0);
		}
}


 *实现“确定”功能
 *完成扫描
 
class SubmitAction implements ActionListener{

	public void actionPerformed (ActionEvent a){

		int minPort;
		int maxPort;
		int maxThread;
		int type = 1;

		String ipAddress = "0.0.0.0";
		String hostName = "";
		InetAddress hostAddress = null;

		ThreadScan.result.setText("");
		//将"开始扫描"按钮设置成为不可用
		if(ThreadScan.Submit.isEnabled()){
			ThreadScan.Submit.setEnabled(false);
		}

		
		 *判断搜索的类型
		 *按照ip地址扫描：type = 0
		 *按照主机名称扫描：type = 1
		 
		if(ThreadScan.radioIp.isSelected()){
			
			type = 0;
			
			//判断ip地址是否正确
			int flag = 1;
			if(ipAddress.trim() == ""){
				flag = 0;
			}
			String[] ipstr = ipAddress.split(".");
			for(int i=0; i<ipstr.length; i++){
				int ip = Integer.parseInt(ipstr[i]);
				if(ip<0 || ip>255){
					flag = 0;
					break;
				}
			}
			if(flag == 0){
				ThreadScan.DLGINFO.setText("错误的ip!");
				ThreadScan.DLGError.setVisible(true);
				return;
			}

			
			
			 *判断ip地址的有效性
			 
			try{
				TCPThread.hostAddress=InetAddress.getByName(ipAddress);
			}
			catch(UnknownHostException e){
				ThreadScan.DLGINFO.setText(" 错误的IP或地址不可达!  ");
				ThreadScan.DLGError.setVisible(true);
				return;
			}
		}
		
		//根据主机名进行端口扫描
		if(ThreadScan.radioHost.isSelected()){
			
			type = 1;
			
			
			 *判断主机名称的有效性
			 
			try{
				TCPThread.hostAddress=InetAddress.getByName(ThreadScan.hostName.getText());
			}
			catch(UnknownHostException e){
				ThreadScan.DLGINFO.setText("错误的域名或地址不可达! ");
				ThreadScan.DLGError.setVisible(true);
				return;
			}
		}

		
		 *判断端口号的有效性
		 
		try{
			minPort=Integer.parseInt(ThreadScan.minPort.getText());
			maxPort=Integer.parseInt(ThreadScan.maxPort.getText());
			maxThread=Integer.parseInt(ThreadScan.maxThread.getText());
		}
		catch(NumberFormatException e){
			ThreadScan.DLGINFO.setText("错误的端口号或线程数!端口号和线程数必须为整数!");
			ThreadScan.DLGError.setVisible(true);
			return;
		}
		
		
		 *判断最小端口号的有效范围
		 *判断条件：大于0且小于65535，最大端口应大于最小端口
		 
		if(minPort<0 || minPort>65535 || minPort>maxPort){
			ThreadScan.DLGINFO.setText("最小端口必须是0-65535并且小于最大端口的整数!");
			ThreadScan.DLGError.setVisible(true);
			return;			
		}
		

		
		 *判断最大端口号的有效范围
		 *判断条件：大于0且小于65535，最大端口应大于最小端口
		 
		if(maxPort<0 || maxPort>65535 || maxPort<minPort){
			ThreadScan.DLGINFO.setText("最大端口必须是0-65535并且大于最小端口的整数!");
			ThreadScan.DLGError.setVisible(true);
			return;	
		}
		

		
		 *判断线程数量的有效范围
		 *判断条件：大于1且小于200
		 
		if(maxThread<1 || maxThread>200){
			ThreadScan.DLGINFO.setText("线程数为1-200的整数! ");
			ThreadScan.DLGError.setVisible(true);
			return;
		}

		ThreadScan.result.append("线程数 "+ThreadScan.maxThread.getText()+"\n");
		
		ExecutorService tPool = Executors.newFixedThreadPool(maxThread);
		//启动线程
		long stime = System.currentTimeMillis();
		int port;
		final CountDownLatch latch = new CountDownLatch(maxPort - minPort + 1);
		
		if(0 == type){
			try {
				hostAddress = InetAddress.getByName(ipAddress);
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			try {
				hostAddress = InetAddress.getByName(hostName);
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//局部内部类
				class MyThread implements Runnable{
					int port;
					InetAddress hostAddress;
					
					public MyThread(int port, InetAddress hostAddress) {
						this.port = port;
						this.hostAddress = hostAddress;
					}

					public void run() {
						Socket theTCPsocket;

								try{
									theTCPsocket=new Socket(hostAddress,port);
									theTCPsocket.close();
									ThreadScan.result.append(" " + port + "open" + "\n");
									}
									catch (IOException e){
										ThreadScan.result.append(" "+port+"close"+"\n");
									}
								latch.countDown();
						
					}
				}
				
		for(port=minPort; port<=maxPort; port++){
			
			tPool.execute(new MyThread(port, hostAddress));
			
		}
		
		//tPool.shutdown();
		//if(tPool.isTerminated()){
		try {
			latch.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		long etime = System.currentTimeMillis();
		System.out.println(etime - stime);
		
		//扫描完成后，显示扫描完成，并将“确定”按钮设置为可用
			ThreadScan.result.append("\n"+"扫描完成...");
			
			//将"确定"按钮设置成为可用
			if(!ThreadScan.Submit.isEnabled()){
				ThreadScan.Submit.setEnabled(true);
			}
		//}
	
	}
}


 *实现错误提示框中的“确定”按钮功能
 
class OKAction implements ActionListener{

	public void actionPerformed (ActionEvent e){
			ThreadScan.DLGError.dispose();
		}
}

*/