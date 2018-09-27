public class Image {
	//6节点图
	int image[][] = new int[6][6];
	
	public Image(){
		//-1表示不可达
		for(int i=0; i<6; i++){
			for(int j=0; j<6; j++){
				image[i][j] = -1;
			}
		}
		image[0][1] = 2;
		image[0][2] = 4;
		image[1][3] = 9;
		image[1][4] = 8;
		image[2][4] = 6;
		image[3][5] = 7;
		image[4][5] = 2;
	}
	
	//得到边的距离
	public int getEdge(int x, int y){
		return image[x][y];
	}
	
	//得到节点数
	public int getN(){
		return image.length;
	}
}
