package prog2.gol;
class InternetWorm implements Krankheit {
   @Override
   public double chanceAnsteckung() {
    return 0.3;
   }
   @Override
   public double chanceHeilung() {
     return 0.6;
   }
   @Override
   public double chanceZuSterben() {
     return 0.2;
   }

};
