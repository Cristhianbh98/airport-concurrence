import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Aeropuerto implements Runnable {
  private Avion avion;
  private static int contDespegar = 0;

  private static ReentrantLock lock = new ReentrantLock();
  private static Condition despegar = lock.newCondition();
  private static Condition aparcamiento = lock.newCondition();

  public Aeropuerto(Avion avion) {
    this.avion = avion;
  }

  public void run() {
    int intencion = avion.getIntencion();

    while (true) {
      lock.lock();

      switch (intencion) {
        case 0:
          intencion = avion.nextIntencion(); // Intención 1
          System.out.println("El avion " + (avion.getId() + 1) + " está volando");
        
          contDespegar--;                

          despegar.signal();
          aparcamiento.signalAll();
          break;
        case 1:
          intencion = avion.nextIntencion(); // Intencion 2
          System.out.println("El avion " + (avion.getId() + 1) + " va a aterrizar");

          await(aparcamiento);
          break;
        case 4:
          intencion = avion.nextIntencion(); // Intencion 0

          while (contDespegar >= 2) {
            await(aparcamiento);
            System.out.println("El avion " + (avion.getId() + 1) + " ha intentado despegar pero está llena la cola");
          }

          System.out.println("El avion " + (avion.getId() + 1) + " va a pasar a la cola de despegue");
          contDespegar++;
          await(despegar);
          break;
        default:
          intencion = avion.nextIntencion(); // Intencion 3 o 4
          System.out.println("El avion " + (avion.getId() + 1) + " está en el parking");
          break;
      }

      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }

      lock.unlock();
    }
  }

  private void await(Condition condition) {
    try {
      condition.await();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}