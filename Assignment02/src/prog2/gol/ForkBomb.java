package prog2.gol;

/** while (true) fork;
 */
class ForkBomb implements Krankheit {
   @Override
   public double chanceAnsteckung() {
    return 0.1;
   }
   @Override
   public double chanceHeilung() {
     return 0.2;
   }
   @Override
   public double chanceZuSterben() {
     return 0.4;
   }
};
