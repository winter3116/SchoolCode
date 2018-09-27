public class ACO {
	public final double ALPHA=1.0;//信息启发式因子，信息素的重要程度  
	public final double BETA=2.0;//期望启发式因子, 城市间距离的重要程度  
	public final double ROU=0.5;//信息素残留系数  
	   
	public int ANT_COUNT=5;//蚂蚁数量  
	public int IT_COUNT=100;//迭代次数  
	   
	public final double INF=1.0;//初始信息量 
	public final int L = 0;     //初始路径长度
	public final double MAX = 10.0;
	
	int bestLength;
	int[] bestPath;
	int curPoint; //当前节点
	      
	//两两节点间的信息量  
	double[][] Trial; 
	

	Image image;//图
	int n;//图的节点数
	
	public ACO(Image image){
		this.image = image;
		this.n = image.getN();
		bestLength = L;
		bestPath = new int[n];
		bestPath[0] = 0;//都从0节点开始
		curPoint = 0;
		
		//初始化信息素   
        Trial=new double[n][n];  
  
        for (int i = 0; i <n; i++)  
        {  
            for(int j=0; j<n; j++){
            	Trial[i][j] = INF;
            }
        } 
	}
	
	
	//开始做
	public void doIt(){
		
		//迭代次数
		for(int i=0; i<IT_COUNT; i++){
			int best = 0;
			int[] lengths = new int[ANT_COUNT];//长度
			int[][] paths = new int[ANT_COUNT][n];//路径
			for(int j=0; j<ANT_COUNT; j++){
				lengths[j] = 0;
				paths[j][0] = 0;
			}
			//蚂蚁数量
			for(int j=0; j<ANT_COUNT; j++){
				int nextP;//下一节点
				int m = 1;//当前节点数
				curPoint = 0;
				while(true){
					nextP = selectNext(curPoint);
					
					paths[j][m] = nextP;
					
					m++;
					
					lengths[j] += image.getEdge(curPoint, nextP);
					
					if(nextP == n-1){
						break;
					}
					curPoint = nextP;
					
					
				}
				
				if(lengths[j] > bestLength){
					best = j;
				}
			}
			
			bestLength = lengths[best];
			for(int j=0; j<n; j++){
				bestPath[j] = paths[best][j];
			}
			
			//信息素更新
			int[][] pathInf = new int[n][n];
			for(int j=0; j<n; j++){
				for(int k=0; k<n; k++){
					pathInf[j][k] = 0;
				}
			}
			for(int j=0; j<ANT_COUNT; j++){
				//路径中前后两个节点
				int ap = 0;
				int bp = 1;
				
				while(true){
					if(paths[j][bp] == n-1){
						break;
					}
				
					pathInf[paths[j][ap]][paths[j][bp]] += lengths[j];
					
					ap++;
					bp++;
							
				}
			}
			
			for(int j=0; j<n; j++){
				for(int k=0; k<n; k++){
					if(0 == pathInf[j][k]){
						break;
					}
					
					Trial[j][k] = (1-ROU)*Trial[j][k] + pathInf[j][k]/MAX;
				}
			}
			
		}
		
		//输出结果
		String result = "a";
		for(int i=1; i<n; i++){
			if(0 != bestPath[i]){
				result += ",";
				result += (char)(bestPath[i]+'a');
			}
			
		}
		System.out.println("关键路径："+result+";路径长度为："+bestLength);
	}
	
	//选择下一节点，如果返回为0
	int selectNext(int curPoint){
		int nextN =0;
		double[] p1 = new double[n];
		double[] p2 = new double[n];
		int[] nextPoints = new int[n];
		for(int i=curPoint+1; i<n; i++){
			int edge = image.getEdge(curPoint, i);
			if(-1 != edge){
				nextN++;
				nextPoints[nextN-1] = i;
				p1[nextN-1] = Math.pow(Trial[curPoint][i], ALPHA) + Math.pow((edge/MAX), BETA);
			}
		}
		if(0 == nextN){
			return 0;
		}
		double sumP = 0.0;
		double temp = 0.0;
		for(int i=0; i<nextN; i++){
			sumP += p1[i];
		}
		for(int i=0; i<nextN; i++){
			temp += p1[i];
			p2[i] = temp/sumP;
		}
		double q = Math.random();
		for(int i=0; i<nextN; i++){
			if(q <= p2[i]){
				return nextPoints[i];
			}
		}
		return 0;
	}
	
}
