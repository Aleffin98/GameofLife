package prog2.gol;
import javax.swing.*;
import java.awt.*;

public class View_Controller2 extends JComponent {

    Model2 mod = new Model2(); // Model wird erzeugt

    View_Controller2(){}

    public void paintComponent(Graphics g) {
        Stein [][] steine = mod.getSpielfeld().getStones();
        int xcoor = 0, ycoor = 0;
        Font font = new Font("Arial",Font.BOLD,11);
        g.setFont(font);
        g.setColor(Color.BLACK);
        g.drawString("Legende: ",60*mod.getX_length(),120); // Hier wird begonnen die Legende zu zeichnen
        g.drawString("Alter: 0 ",60*mod.getX_length(),150);
        g.drawString("Alter: 1 ",60*mod.getX_length(),200);
        g.drawString("Alter: 2 ",60*mod.getX_length(),250);
        g.drawString("Alter: 3 ",60*mod.getX_length(),300);
        g.drawString("Alter: >3",60*mod.getX_length(),350);
        g.drawString("BitRot:",60*mod.getX_length(),400);
        g.drawString("ForkBomb:",60*mod.getX_length(),450);
        g.drawString("InternetWorm:",60*mod.getX_length(),500);
        Font font2 = new Font("Arial",Font.BOLD,24);
        g.setFont(font2);
        g.drawString("B",76*mod.getX_length(),405);
        g.drawString("F",76*mod.getX_length(),455);
        g.drawString("I",76*mod.getX_length(),505);

        g.setColor(Color.BLACK);
        g.fillRect(75*mod.getX_length(),130,35,35);
        g.setColor(Color.BLUE);
        g.fillRect(75*mod.getX_length(),180,35,35);
        g.setColor(Color.GREEN);
        g.fillRect(75*mod.getX_length(),230, 35, 35);
        g.setColor(Color.YELLOW);
        g.fillRect(75*mod.getX_length(),280, 35, 35);
        g.setColor(Color.RED);
        g.fillRect(75*mod.getX_length(),330, 35, 35);//Hier endet die Zeichnung der Legende
        for (int i = 0; i < mod.getX_length(); ++i) {
            for (int j=0; j < mod.getY_length(); ++j){
                g.setColor(Color.BLACK);
                g.drawRect(xcoor, ycoor, 50, 50);//}
                //Abfrage des Alters der einzelnen Steine um die Farben der Steine zu setzen
                switch (steine[i][j].toString()) {
                    case ".":
                        g.setColor(Color.BLUE);
                        break;
                    case "o":
                        g.setColor(Color.GREEN);
                        break;
                    case "O":
                        g.setColor(Color.YELLOW);
                        break;
                    case "*":
                        g.setColor(Color.RED);
                        break;
                }
                g.setFont(font);
                g.drawString(steine[i][j].getKrankheit(),xcoor+10,ycoor+20);//Darstellung der Krankheiten mit denen sich die einzelnen Steine infiziert haben
                xcoor += 55;
            }
            xcoor=0;
            ycoor += 55;
        }
        g.setColor(Color.BLACK);
        g.drawString("Anzahl der Züge: "+mod.getSteps(),60*mod.getX_length(),30);//Darstellung des Counters für die vergangenen Züge
        g.drawString("Anzahl verstorbene Steine: "+mod.getDeadstones(),60*mod.getX_length(),90);//Darstellung des Counters für die verstorbenen Steine

    }

    public void incrdeadstones () {
        mod.incrdeadstones();
    } //Methode zum erhöhen der verstorbene Steine

    public void incrsteps () {
        mod.incrsteps();
    } //Methode zum erhöhen der durchgeführten Züge

    public void safeSF (Spielfeld a){ //An diese Methode soll jede Runde das Spielfeld übergeben werden und
        mod.setSpielfeld(a);
    }
}

