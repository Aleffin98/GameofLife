package prog2.gol;


public class Model2 {

    int deadstones = 0; //Counter für gestorbene Steine
    int steps = 0;      //Counter für die Spielzüge
    Spielfeld spiel;    //Hier wird in jeder Runde das aktuelle Spielfeld gespeichert

    Model2(){}

    public void incrdeadstones(){ this.deadstones++; } // Methode, um gestorbene Steine zu erhöhen

    public int getDeadstones(){ return this.deadstones; }  // Getter für gestorbene Steine

    public void incrsteps(){ this.steps++; } // Spielzüge erhöhen

    public int getSteps(){ return this.steps; } // Getter für die Spielzüge

    public void setSpielfeld(Spielfeld spielfeld){ this.spiel = spielfeld; } // Spielfeld wird gespeichert

    public Spielfeld getSpielfeld(){ return this.spiel; } // Getter für das Spielfeld

    public int getX_length(){     // Getter für X-Koordinate der Spielfeldgröße
        int m_xlength = spiel.s_xlength();
        return m_xlength;
    }
    public int getY_length(){
        int m_ylength = spiel.s_ylength(); // Getter für X-Koordinate der Spielfeldgröße
        return m_ylength;
    }
}
