public class PontoMapa {

    public static int MAP_PONIT_OCCUPIED = 1;
    public static int MAP_PONIT_EMPTY = 0;
    public static int MAP_PONIT_OBSTRUCTION = 2;

    private boolean occupied = false;
    private int type;

    public PontoMapa(int type){
        setType(type);
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {

        if (type == MAP_PONIT_OBSTRUCTION || type == MAP_PONIT_OCCUPIED){
            this.occupied = true;
        }else {
            this.occupied = false;
        }

        this.type = type;
    }

    public boolean isOccupied() {
        return occupied;
    }

}
