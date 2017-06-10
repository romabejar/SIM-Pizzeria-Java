package upc.edu;

import java.util.Random;

/**
 * Created by romabejar on 30/03/17.
 */
public class Motorista {
    public int ocupatFins; // (segons)

    public Motorista() {
        ocupatFins = -1;
    }

    public boolean estaLliure(){
        return ocupatFins == -1;
    }

    public boolean haAcabat(int segonActual){
        boolean resultat = false;
        if (ocupatFins == segonActual) {
            ocupatFins = -1;
            resultat = true;
        }
        return resultat;
    }

    public void agafarComanda(int segonActual){
        Random random = new Random();
        ocupatFins = segonActual + 5*60+random.nextInt()%(60*10); // REPARTIDOR: [10,5] minuts
    }
}
