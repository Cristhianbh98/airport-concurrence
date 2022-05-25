/* 
  Se gestiona un aeropuerto, cada avión tiene un hilo de ejecución, cada avión necesita enteramente de la pista de aterrizaje, ningun otro avión puede estar en la pista, cuando un avión aterriza pasa a una sección de aparcamiento esperando a pasar a una parte de la pista reservada para los aviones que van a despegar donde solamente pued haber maximo dos. https://i.ibb.co/Rb5m1MZ/ejemplo.png
 */

import java.util.Random;

public class App {
  public static void main(String[] args) throws InterruptedException {

    Random rand = new Random(System.nanoTime());
    Thread[] vec = new Thread[30];

    for (int i = 0; i < vec.length; i++) {
      int intencion = rand.nextInt(5);
      Avion avion = new Avion(i, intencion);

      Runnable run = new Aeropuerto(avion);
      vec[i] = new Thread(run);
      vec[i].start();
    }

    try {
      for (int i = 0; i < vec.length; i++) {
        vec[i].join();
      }
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

}
