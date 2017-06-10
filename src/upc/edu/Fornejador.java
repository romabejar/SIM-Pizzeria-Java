package upc.edu;


import com.sun.tools.javac.util.Pair;

import java.util.LinkedList;
import java.util.Random;

/**
 * Created by romabejar on 30/03/17.
 */
public class Fornejador {
    private LinkedList<Pair<Character,Integer>> comandes;
    private Random random;

    public Fornejador() {
        random = new Random();
        comandes = new LinkedList<>();
    }

    public void agafarComanda(Character comanda, int segonActual){
        comandes.add(new Pair<>(comanda,60+random.nextInt()%120));
    }

    public LinkedList<Character> comandaAcabada(int segons){
        LinkedList<Character> result = new LinkedList<>();
        for (Pair<Character, Integer> comanda : comandes) {
            if(comanda.snd == segons){
                result.add(comanda.fst);
                comandes.remove(comanda);
            }
        }
        return result;
    }

}
