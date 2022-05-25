public class Avion {
  private int id;
  private int intencion;

  // Intención = 0 : Quiere despegar el avión
  // Intención = 1 : Está volando el avión
  // Intención != 0 || 1 : Está estacionado
  // Última intención posible = 4

  public Avion(int id, int intencion) {
    this.id = id;
    this.intencion = intencion;
  }

  public int getIntencion() {
    return intencion;
  }

  public int getId() {
    return id;
  }

  public int nextIntencion() {
    intencion = ++intencion % 5;
    return intencion;
  }
}
