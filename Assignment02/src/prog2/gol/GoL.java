package prog2.gol;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;

// Hauptklasse.
// In dieser Klasse wollen Sie anfangen zu programmieren.
// Aufwand pro Woche: 12 Stunden pro Studierender
class GoL {
    private static int Wartezeit;
    public static void main(String [] args) throws InterruptedException {
        Scanner s = new Scanner(System.in); // Scanner wird erstellt und die Wartezeit zwischen Zügen wird abgefragt
        System.out.println("Wie viele Millisekunden soll vor jedem Zug gewartet werden?");
        Wartezeit = s.nextInt();

        Spielfeld spielfeld = new Spielfeld();

        s.close();

        View_Controller2 v = new View_Controller2();
        JFrame j = new JFrame();
        JPanel p = new JPanel();
        p.setLayout(new GridLayout(2, 4, 15, 15)); //Die Jbuttons und der JSlider in 2 Reihen einstellen

        // Erstelle JButton "Play", um das Spiel während einer Pause fortzusetzen.
        JButton b1 = new JButton("Play");
        b1.setBounds(500,150,100,40);
        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                spielfeld.setGamePaused(false);
            }
        });
        p.add(b1); //in den Panel hinzugefügt
        // Erstelle JButton "Pause", welche das Spiel Pausiert.
        JButton b2 = new JButton("Pause");
        b2.setBounds(500,250,100,40);
        b2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                spielfeld.setGamePaused(true);
            }
        });
        p.add(b2); //in den Panel hinzugefügt
        //Erstelle JButton "Step", welche Spiel pausiert.
        JButton b3 = new JButton("Step");
        b3.setBounds(500,350,100,100);
        b3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                spielfeld.naechsterSpielzug(v);
                v.safeSF(spielfeld);
                j.repaint();
            }
        });
        p.add(b3); //in den Panel hinzugefügt
        JSlider s1 = new JSlider(JSlider.HORIZONTAL,100 ,2000,200 );
        s1.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent e) {
                GoL.setWartezeit(200000 / s1.getValue());
            }
        });
        s1.setPreferredSize(new Dimension(300,30));
        s1.setPaintTicks(true);
        s1.setMinorTickSpacing(100);

        s1.setPaintTrack(true);
        s1.setMajorTickSpacing(250);
        p.add(s1);
        j.add(p,BorderLayout.SOUTH); // fügt das panel dem Jframe hinzu sodass es immer UNTEN ist.
        j.setSize(700,700);
        j.setTitle("Game of Life - Made by 2 dumme 1 Gedanke");
        j.setLocationRelativeTo(null);
        j.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        v.safeSF(spielfeld);
        j.add(v);
        j.getContentPane().setBackground(new Color(128,128,128));
        j.setVisible(true);


        while(true) { // Die Schleifen wurden aufgestellt, um die JButtons mit true/false im ActionListener einstellen zu können.
            while (!spielfeld.getGamePaused()) {
                Thread.sleep(Wartezeit);
                spielfeld.naechsterSpielzug(v);
                v.safeSF(spielfeld);
                j.repaint();
            }
        }
    }
    public static void setWartezeit(int value){
        Wartezeit = value;
    }
}



