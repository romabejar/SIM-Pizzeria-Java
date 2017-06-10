package upc.edu;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

/**
 * Created by romabejar on 30/03/17.
 */
public class Pizzeria {

    private int trucadesEnEspera;
    private int personesEnLaCua;
    private int personesEnLaCuaQueJaHanDemanat;
    private int trucadesPerdudes;
    private int personesPerdudes;
    private int pizzesRepartidesCua;
    private int pizzesRepartidesTelf;
    private Queue<Character> comandes;
    private Queue<Character> amassades;
    private Queue<Character> condimentades;
    private Queue<Character> fornejades;
    private LinkedList<Condimentador> condimentadors;
    private LinkedList<Amassador> amassadors;
    private LinkedList<Motorista> motoristas;
    private Recepcionista recepcionista;
    private Telefonista telefonista;
    private Fornejador fornejador;
    private int seguentTrucada;
    private int seguentPersona;
    private Random random;


    public Pizzeria() {
        random = new Random();
    }

    public void simulacio(int durada, int numCondimentadors, int numAmassadors, int numMotoristes){
        char aux;
        trucadesEnEspera = 0;
        personesEnLaCua = 0;
        personesEnLaCuaQueJaHanDemanat = 0;
        trucadesPerdudes = 0;
        personesPerdudes = 0;
        pizzesRepartidesCua = 0;
        pizzesRepartidesTelf = 0;
        comandes = new LinkedList<>();
        amassades = new LinkedList<>();
        condimentades = new LinkedList<>();
        fornejades = new LinkedList<>();
        condimentadors = new LinkedList<>();
        amassadors = new LinkedList<>();
        motoristas = new LinkedList<>();
        for(int i = 0; i < numCondimentadors; ++i) condimentadors.add(new Condimentador());
        for(int i = 0; i < numAmassadors; ++i) amassadors.add(new Amassador());
        for(int i = 0; i < numMotoristes; ++i) motoristas.add(new Motorista());
        recepcionista = new Recepcionista();
        telefonista = new Telefonista();
        fornejador = new Fornejador();
        seguentPersona = (int) random.nextInt()%120+60; // PERSONES: Entren cada 2 minuts +-1
        seguentTrucada = (int) random.nextInt()%60+30; // TRUCADES: llei Uniforme [1 +- 0.5] minut.

        System.out.println("seguentPersona: " + seguentPersona + "\n" +
        "seguentTrucada: " + seguentTrucada + "\n");
        for(int segon  = 0; segon < durada; ++segon) {
            if(seguentTrucada == segon){
                seguentTrucada = segon + (int) random.nextInt()%60+30; // TRUCADES: llei Uniforme [1 +- 0.5] minut.
                System.out.println("seguentTrucada: " + seguentTrucada + "\n");
                if (trucadesEnEspera < 2) trucadesEnEspera++;
                else trucadesPerdudes++;
            }
            if(seguentPersona == segon){
                seguentPersona = segon + (int) random.nextInt()%120+60; // PERSONES: Entren cada 2 minuts +-1
                System.out.println("seguentPersona: " + seguentPersona + "\n");
                if (personesEnLaCua < 10) personesEnLaCua++;
                else personesPerdudes++;
            }

            // Recepcionista
            if(recepcionista.estaLliure()){
                if(personesEnLaCua > personesEnLaCuaQueJaHanDemanat){
                    recepcionista.agafarComanda('C',segon);
                    ++personesEnLaCuaQueJaHanDemanat;
                }
            }else if(recepcionista.haAcabat(segon)){
                comandes.add('C');
            }

            // Telefonista
            if(telefonista.estaLliure()){
                if(trucadesEnEspera > 0){
                    telefonista.agafarComanda('T',segon);
                    --trucadesEnEspera;
                }else{
                    if(personesEnLaCua > personesEnLaCuaQueJaHanDemanat){
                        recepcionista.agafarComanda('C',segon);
                        ++personesEnLaCuaQueJaHanDemanat;
                    }
                }
            }else if(telefonista.haAcabat(segon)){
                comandes.add(telefonista.getComanda());
            }

            // Amassadors
            for (Amassador amassador : amassadors) {
                if(amassador.estaLliure()){
                    if(comandes.size() > 0) {
                        amassador.agafarComanda(comandes.poll(), segon);
                    }
                }else if(amassador.haAcabat(segon)){
                    amassades.add(amassador.getComanda());
                }
            }

            // Condimentadors
            for (Condimentador condimentador : condimentadors){
                if(condimentador.estaLliure()){
                    if(amassades.size() > 0){
                        condimentador.agafarComanda(amassades.poll(),segon);
                    }
                }else if(condimentador.haAcabat(segon)){
                    condimentades.add(condimentador.getComanda());
                }
            }

            // Fornejador
            for (Character condimentada : condimentades) {
                fornejador.agafarComanda(condimentada,segon);
                condimentades.remove(condimentada);
            }
            fornejades.addAll(fornejador.comandaAcabada(segon));

            // Repartir Locals
            for (Character fornejada : fornejades) {
                if (fornejada.charValue() == 'C'){
                    fornejades.remove(fornejada);
                    personesEnLaCua--;
                    personesEnLaCuaQueJaHanDemanat--;
                    pizzesRepartidesCua++;
                }
            }

            //Motoristes
            for(Motorista motorista : motoristas){
                if(motorista.estaLliure()){
                    if(fornejades.size()>0){
                        fornejades.poll();
                        motorista.agafarComanda(segon);
                    }
                }else{
                    if(motorista.haAcabat(segon)){
                        pizzesRepartidesTelf++;
                    }
                }
            }




        }
        // Resultats

        System.out.println(
            "trucadesPerdudes: " + trucadesPerdudes + "\n" +
            "personesPerdudes: " + personesPerdudes + "\n" +
            "pizzesRepartidesCua: " + pizzesRepartidesCua + "\n" +
            "pizzesRepartidesTelf: " + pizzesRepartidesTelf + "\n" +
            "numCondimentadors: " + numCondimentadors + "\n" +
            "numAmassadors: " + numAmassadors + "\n" +
            "numMotoristes: " + numMotoristes + "\n"
        );

    }

}
