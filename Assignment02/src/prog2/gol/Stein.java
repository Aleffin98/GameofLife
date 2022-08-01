package prog2.gol;
import java.util.Random;

/** Implementiert einzelne Spielsteine. 
 * Spielsteine kennen ihre Nachbarn, aber nicht ihre Position auf dem
 * Spielfeld. Ein Spielstein kann mit bis zu 5 Krankheiten infiziert
 * sein (Interface Krankheit).
 */
class Stein {
    // Context Fields
    final String name;
    private int n_neigh = 8;
    private Stein[] neighbour;

    // State Fields (change over time)
    private int alter = 1; // 0 == dead
    private Krankheit [] illness = new Krankheit[5];


    // one random generator for the whole class
    static Random r = new Random();


    protected Stein(int age, String name) {
        this.name = name;
        this.alter = Math.max(0, age);
        neighbour = new Stein[n_neigh];
        // choose number of illnesses
        for (int i = Stein.r.nextInt(3);
             i >= 0; i--) {
            illness[i] = Stein.randomKrankheit();
        }
    }

    protected Stein(int age) {
        this(age, "NN"); // first construct empty Stein
    }

    protected Stein() {
        this(1);
    }

    public Stein(Stein s) {
        this(s.alter, s.name);
        this.alter = s.alter;
        for (int i=0; i < neighbour.length; i++)
            neighbour[i] = s.neighbour[i];
        for (int i=0; i < illness.length; i++)
            illness[i] = s.illness[i];
    }

    public String getKrankheit() { // Methode, die alle aktuellen Krankheiten eines Steines zurückgibt
        String krankheit = "";

        for (int i = 0; i < 5; ++i) {
            if (illness[i] != null){
                char[] c = illness[i].toString().toCharArray();
                krankheit = krankheit+c[10];
            }
        }
        if (krankheit.equals(""))
            krankheit = "-";

        return krankheit;
    }

    protected void copyState(Stein s){
        this.alter = s.alter;
        this.illness = s.illness;
    }



    /** Erzeuge zufällig eine der bekannten Krankheiten
     *
     * TODO: sollte in eine abstrakte Klasse Krankheit verschoben
     * werden.
     */
    private static Krankheit randomKrankheit() {
        switch (Stein.r.nextInt(3)) {
            case 0:
                return new BitRot();
            case 1:
                return new InternetWorm();
            default:
                return new ForkBomb();
        }
    }

    /** Array mit allen Nachbarn.
     * @return eine Kopie des Arrays der Nachbarn, mit den echten
     * Referenzen.
     */
    public Stein[] neighbours() {
        Stein[] neighs = new Stein[neighbour.length];

        for (int i=0; i<neighbour.length && i<neighs.length; i++){
            neighs[i] = neighbour[i];
        }
        return neighs;
    }

    protected void addNeighbour(Stein neigh) {
        for (int i = 0; i < neighbour.length; i++) {
            if ( null == neighbour[i] ) {
                neighbour[i] = neigh;
                return;
            }
        }
        // TODO: throw exception: ListFull.
    }

    protected void copy_neighbours() {
        for (int i = 0; i < neighbour.length; i++) {
            if (neighbour[i] != null) {
                neighbour[i] = new Stein(neighbour[i]);
            }
        }
    }

    protected int countLivingNeighbours() {
        int num = 0;
        for (Stein s: this.neighbour)
            if (null != s && s.istAmLeben() )
                num++;
        return num;
    }

    protected boolean istAmLeben() {
        return this.alter > 0;
    }

    ////////// ACTIONS   ///////////////

    private void die(){
        this.alter = 0;
        this.healAll();
    }

    private void tryHealing() {
        double threshold = Stein.r.nextDouble();
        for (int i = 0; i < illness.length;i++){
            if (illness[i] != null &&
                    threshold < illness[i].chanceHeilung()) { // this differs from assignment
                illness[i] = null;
            }
        }
    }

    private void healAll() {
        for (int i = 0; i < illness.length; i++) {
            illness[i] = null;
        }
    }

    /** Stirbt der Stein diese Runde an einer seiner Krankheiten?
     *
     * TODO sollte eine Referenz der tödlichen Krankheiten zurückgeben.
     */
    private boolean hasTerminalIllness() {
        for (int i = 0; i < illness.length; i++){
            if (illness[i] != null &&
                    Stein.r.nextDouble() < illness[i].chanceZuSterben()) { // this differs from assignment
                //  System.err.println(this.name + " died from illness "+i+" (" + illness[i].getClass().getSimpleName()+")");
                return true;
            }
        }
        return false;
    }

    /* This is to be called by neighbours to check if this Stein infects
     * them this round. Just hands them the first
     * Illness that is (by chance) infecting him.
     */
    protected Krankheit infection() {
        if ( 0 <  this.alter) {
            for (int i = 0; i < illness.length; i++){
                if (illness[i] != null &&
                        Stein.r.nextDouble() < illness[i].chanceAnsteckung()) { // this differs from assignment
                    return illness[i];
                }
            }
        }
        return null; // that lucky guy got away
    }

    protected void addIllness(Krankheit infect){
        for (int i = 0; i < illness.length; i++){
            if (illness[i] == null) {
                illness[i] = infect;
                return;
            }
        }
        // No empty illness-slot found, select one at random
        int i = Stein.r.nextInt(5);
        illness[i] = infect;
    }

    protected void gameOfLiveCreationRules() {
        // GoL Rules create new life or terminate existing
        if (0 >= this.alter) { // dead
            if ( 3 == this.countLivingNeighbours()){
                //   System.err.println(this.name + " has been reborn");
                this.alter = 1;
            }
        }
    }


    protected void gameOfLiveDeathRules() {
        if ( 0 < this.alter) {
            int neigh = this.countLivingNeighbours();
            if ( 2 > neigh ) {
                // You die, but your illnesses with you
                //     System.err.println(this.name + " died from loneliness");
                this.die();
            } else if ( 3 < neigh ) {
                //    System.err.println(this.name + " died from overcrowding");
                this.die();
            }
        }
    }

    public void lebeWeiter(View_Controller2 v) {
        if ( 0 >= this.alter ) return; //dead is inactive
        else this.alter += 1; // Happy Birthday
        // am I Healing?
        this.tryHealing();
        // am I dying?
        if (this.hasTerminalIllness()) {
            this.die();
            v.incrdeadstones();
        } else {
            // become infected by a neighbour?
            for (int i = 0; i < neighbour.length; i++) {
                if(null != neighbour[i]) {
                    Krankheit infect = neighbour[i].infection();
                    if (infect != null) {
                        this.addIllness(infect);
                    }
                }
            }
        }
    }

    public void drucke() {
        System.out.print(this);
    }

    public String printNeighbours() {
        String list = "";
        for (int i=0; i < neighbour.length; i++){
            if (neighbour[i] != null) {
                list += i + neighbour[i].toString() + ", ";
            } else {
                list += "n, ";
            }
        }
        return list;
    }

    public String toString() {
        if (0 > this.alter)
            return "E";
        switch (this.alter) {
            case 0:
                return "x";
            case 1:
                return ".";
            case 2:
                return "o";
            case 3:
                return "O";
            default:
                return "*";
        }
    }
}
