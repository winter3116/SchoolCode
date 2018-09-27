import java.util.Random;

public class GA {
	int size = 1000;//种群规模
	int n;     //二进制编码长度，同节点数
	
	double rate_crossover = 0.5;//交配率
	double rate_mutation = 0.1;//变异率
	int it_num = 1000;//迭代次数
	
	Image image;
	
	/*****初始化定义的种群和个体*****/
    Chromosome[] population_current = new Chromosome[size];      //当前种群  
    Chromosome[] population_next_generation = new Chromosome[size]; //产生的下一代的种群                        
    Chromosome best_individual;                                       //记录适应度的最大值
    /****************************************/
	
	public GA(Image image){
		this.image = image;
		this.n = image.getN();
		for(int i=0; i<size; i++){
			population_current[i] = new Chromosome(image);
			population_next_generation[i] = new Chromosome(image);
			best_individual = new Chromosome(image);
		}
		
		
	}
	
	
	
    //开始
    public void doIt(){
    	
    	//开始迭代
        for(int i=0; i<it_num; i++){
        	
        	
        	fresh_property();
        	
        	
        	//挑选优秀个体组成新的种群
            seletc_prw();                 
            //对选择后的种群进行交叉操作
            crossover();              
            //对交叉后的种群进行变异操作
            mutation();                      
            //更新种群内个体的属性值
            fresh_property();
            //将population_next_generation的值赋给population_current，并清除population_next_generation的值
            for (int j = 0; j <size; j++)
            {
                population_current[j] = population_next_generation[j];
                
            }
        }
        
        //输出结果
        String result = "a";
        for(int i=1; i<n; i++){
        	if(best_individual.bit[i] == 1){
        		result += ",";
        		result += (char)(i+'a');
        	}
        }
        System.out.println("关键路径："+result+";路径长度为："+best_individual.fitness);
        
        
    }
	
	
    //函数：更新种群内个体的属性值
    //说明：当种群中个体的二进制串确定后，就可以计算每个个体fitness、value、rate_fit 、cumu_fit
    void fresh_property()
    {
        int j = 0;
        double sum = 0;

        for (j = 0; j < size; j++)
        {
            sum = sum + population_current[j].getfit();

        }


        //计算每条染色体的适应值百分比及累计适应度值的百分比，在轮盘赌选择法时用它选择染色体
        if(0.0 != sum){
        	population_current[0].rate_fit = population_current[0].getfit() / sum;
            population_current[0].cumu_fit = population_current[0].rate_fit;
            for (j = 1; j <size; j++)
            {
                population_current[j].rate_fit = population_current[j].getfit() / sum;
                population_current[j].cumu_fit = population_current[j].rate_fit + population_current[j-1].cumu_fit;
            }
        }
        
    

    }

    //函数：基于轮盘赌选择方法，对种群中的染色体进行选择  
    void seletc_prw()
    {

        int i = 0;
        int j = 0;
        double rate_rand = 0.0;
        //best_individual = population_current[0];
        for (i = 0; i <size; i++)
        {
            rate_rand = Math.random();
            if (rate_rand <= population_current[0].cumu_fit)
                population_next_generation[i] = population_current[0];      
            else
            {
                for (j = 0; j <size-1; j++)
                {
                    if (population_current[j].cumu_fit < rate_rand && rate_rand <= population_current[j + 1].cumu_fit)
                    {
                        population_next_generation[i] = population_current[j + 1];
                        break;
                    }
                }
            }

            //如果当前个体比目前的最优秀个体还要优秀，则将当前个体设为最优个体
            
            if(population_current[i].getfit() > best_individual.getfit()){
            
                best_individual = population_current[i];
            }
            	
        }

    }


    // 函数：交配操作
    void crossover(){   
        int j = 0;
        double rate_rand = 0.0;
        int bit_temp = 0;
        int num1_rand = 0, num2_rand = 0, position_rand = 0;
        Random random = new Random();
        
        //应当交叉变异多少次呢？先设定为种群数量
        for (j = 0; j<size; j++)
        {
            rate_rand = Math.random();
            //如果大于交叉概率就进行交叉操作
            if(rate_rand <= rate_crossover)
            {
                //随机产生两个染色体
                num1_rand = random.nextInt(size);
                num2_rand = random.nextInt(size);
                //随机产生两个染色体的交叉位置
                position_rand = random.nextInt(n-2)+1;
                //采用单点交叉，交叉点之后的位数交换
                
                    bit_temp = population_next_generation[num1_rand].bit[position_rand];
                    population_next_generation[num1_rand].bit[position_rand] = population_next_generation[num2_rand].bit[position_rand];     
                    population_next_generation[num2_rand].bit[position_rand] = bit_temp;     
               

            }
        }

    }

    // 函数：变异操作
    void mutation(){
        int position_rand = 0;
        int i = 0;
        double rate_rand = 0.0;
        //产生随机数种子
        Random random = new Random();
        //变异次数设定为种群数量
        for (i = 0; i<size; i++)
        {
            rate_rand = random.nextDouble();
            //如果大于交叉概率就进行变异操作
            if(rate_rand <= rate_mutation)
            {
                //随机产生突变位置
                position_rand = random.nextInt(n-2)+1;
                //突变
                if (population_next_generation[i].bit[position_rand] == 0)
                    population_next_generation[i].bit[position_rand] = 1;
                else
                    population_next_generation[i].bit[position_rand] = 0;

            }

        }
    }
	
	
	

}


//单个染色体
class Chromosome{
	int n;
	Image image;
	int[] bit;
	
	int fitness; //适应值
	double rate_fit;//相对的fit值，即所占的百分比
	double cumu_fit;//积累概率
	
	public Chromosome(Image image){
		this.image = image;
		this.n = image.getN();
		this.bit = new int[n];
		this.fitness = 0;
		this.rate_fit = 0.0;
		this.cumu_fit = 0.0;
		for(int i=0; i<n; i++){
			bit[i] = (Math.random()>0.5) ? 1 : 0;
		}
	}
	
	public boolean isRight(){
		if(bit[0]==0 || bit[n-1]==0){
			return false;
		}
		for(int i=0; i<n-1; i++){
			if(bit[i] == 1){
				for(int j=i+1; j<n; j++){
					if(bit[j] == 1){
						if(-1 == image.getEdge(i, j)){
							return false;
						}else{
							break;
						}
						
					}
				}
			}
			
		}
		return true;
	}
	public int getfit(){
		int length = 0;
		
		
		if(!isRight()){
			fitness = 0;
		}else{
			for(int i=0; i<n-1; i++){
				if(bit[i] == 1){
					for(int j=i+1; j<n; j++){
						if(bit[j] == 1){
							if(-1 != image.getEdge(i, j)){
								length += image.getEdge(i, j);
							}else{
								break;
							}
						}
					}
				}
				
			}
			fitness = length;
		}
		return fitness;
	}
}
