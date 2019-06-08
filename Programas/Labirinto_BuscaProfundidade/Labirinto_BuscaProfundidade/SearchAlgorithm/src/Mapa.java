import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class Mapa {

    private PontoMapa[][] mapArr;
    int mapHeight;
    int mapWidth;

    public PontoMapa[][] getMapArr() {
        return mapArr;
    }

    public Map (String path){
        readMapFromFile(path);
    }

    public void setPoint(int i,int j,int type){
        mapArr[i][j].setType(type);
    }

    public PontoMapa getPoint(int i,int j){
        return mapArr[i][j];
    }


    public void printMap(){
        for (int i =0;i<mapHeight;i++){
            for (int j = 0;j<mapWidth;j++){
                System.out.print(mapArr[i][j].getType()+"\t");
            }
            System.out.println();
        }
    }

    public void readMapFromFile(String path){

        int mapWidth = 0;
        int mapHeight = 0;

        int[][] tempMap = new int[100][100];

        try {
            String encoding="GBK";
            File file=new File(path);
            if(file.isFile() && file.exists()){
                InputStreamReader read = new InputStreamReader(
                        new FileInputStream(file),encoding);
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                while((lineTxt = bufferedReader.readLine()) != null){
                    mapWidth = lineTxt.length();

                    for (int i=0;i<mapWidth;i++){
                        tempMap[mapHeight][i] = Integer.parseInt(String.valueOf(lineTxt.charAt(i)));
                    }

                    mapHeight++;
                }
                read.close();
            }else{
                System.out.println("Can't find specific file");
            }
        } catch (Exception e) {
            System.out.println("Read Error");
            e.printStackTrace();
        }

        this.mapHeight = mapHeight;
        this.mapWidth = mapWidth;


        this.mapArr = new PontoMapa[mapHeight][mapWidth];
        for (int i=0;i<mapHeight;i++){
            for (int j=0;j<mapWidth;j++){
                this.mapArr[i][j] = new PontoMapa(tempMap[i][j]);
            }
        }



    }

}
