package prog2.gol;
import java.lang.Math;
import java.util.Random;
import java.util.Scanner;

class Spielfeld {
    private int x_length;
    private int y_length;
    private Stein[][] stones;
    private boolean gamePaused;

    static Random r = new Random();
    private void initNeighbours() {
        // initiate the proper neighbours in all stones
        // assumes stones is initiated
        for (int i = 0; i < stones.length; i++) {
            for (int j = 0 ; j < stones[i].length; j++) {
                if ( null == stones[i][j] ) {
                } else {
                    for (int ii = Math.max(0, i-1);
                         ii < Math.min(stones.length, i+2);
                         ii++){
                        for (int jj = Math.max(0, j-1);
                             jj < Math.min(stones[ii].length, j+2) ;
                             jj++) {
                            if (stones[ii][jj] != stones[i][j] && stones[ii][jj] != null){
                                stones[i][j].addNeighbour(stones[ii][jj]);
                            }
                        }
                    }
                }
            }
        }
    }
    public Stein[][] getStones(){ return this.stones; }
    public int s_xlength(){ return this.x_length; }//Methode um die breite des Spielfeldes zurückzugeben
    public int s_ylength(){ return this.y_length; }//Methode um die länge des Spielfeldes zurückzugeben

    public Spielfeld() {
        this(1);
    }

    public Spielfeld(int stone_age) {
        this(8, 8, stone_age);
    }

    public Spielfeld(int xlen, int ylen, int stone_age) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Wie viele Spalten soll das Spielfeld beinhalten?");//Abfrage der Größe des Spielfelds
        this.y_length = sc.nextInt();
        System.out.println("Wie viele Zeilen soll das Spielfeld beinhalten?");
        this.x_length = sc.nextInt();
        sc.close();
        this.stones = new Stein[x_length][y_length];

        for (int x = 0; x < stones.length; x++)
            for (int y = 0; y < stones[x].length; y++){
                String name = "["+x+","+y+"]";
                // TODO find better random distribution of stones
                if (1 >= Spielfeld.r.nextInt(4)) {
                    stones[x][y] = new Stein(stone_age, name);
                } else {
                    stones[x][y] = new Stein(0, name);
                }
            }
        initNeighbours();
        // this.print_stone_neighbours();
        this.gamePaused = false;
    }


    public void naechsterSpielzug(View_Controller2 v) {
        Stein[][] newStones = new Stein[x_length][y_length];
        v.incrsteps();
        // calculate new states
        for (int x=0; x < stones.length; x++){
            for (int y=0; y < stones[x].length; y++){
                newStones[x][y] = new Stein(stones[x][y]); // temporary stone state, with all the old illnesses and neighbours
                newStones[x][y].gameOfLiveCreationRules();
                //newStones[x][y].gameOfLiveDeathRules(); // FIXME: illnesses have little effect
                newStones[x][y].lebeWeiter(v); // change stone state
            }
        }
        // transfer states
        for (int x=0; x < stones.length; x++){
            for (int y=0; y < stones[x].length; y++){
                stones[x][y].copyState(newStones[x][y]);
            }
        }

    }

    public String toString() {
        String result = "";
        for (Stein[] l: this.stones) {
            for (Stein s: l)
                if (null != s)
                    result += (" " == s.toString()?"_":s);
                else
                    //  result += "N";
                    result += " ";
            // result += "\n";
        }
        return result;
    }
    public boolean getGamePaused(){
        return this.gamePaused;
    }
    public void setGamePaused(boolean value){
        this.gamePaused = value;
    }
}
