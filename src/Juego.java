import java.sql.SQLOutput;
import java.util.Scanner;

class Persona {
    private int vida;
    private int ataque;
    private int defensa;
    private int accionesRestantes;

    public Persona(int vida, int ataque, int defensa, int accionesRestantes) {
        this.vida = vida;
        this.ataque = ataque;
        this.defensa = defensa;
        this.accionesRestantes = accionesRestantes;
    }

    public int getVida() {
        return vida;
    }

    public int getDefensa() {
        return defensa;
    }

    private void ataque(Persona oponente) {
        int dano = ataque - oponente.getDefensa();
        if (dano < 0) {
            dano = 0;
        }
        else{
            oponente.recibirAtaque(dano);
            System.out.println(this.getClass().getSimpleName() + " ataca a " +
                    oponente.getClass().getSimpleName() + " causando " + dano + " de daño.");
        }
    }

    private void curacion() {
        int cantidadCuracion = 15;
        vida += cantidadCuracion;
        System.out.println(this.getClass().getSimpleName() + " se cura " + cantidadCuracion + " puntos de vida.");
    }

    public void recibirAtaque(int cantidadAtaque) {
        int danoRecibido = cantidadAtaque;
        if (danoRecibido > 0) {
            vida -= danoRecibido;
        }
        else
        {
            System.out.println("Perdiste");
        }

    }

    public void turno(Persona oponente, Scanner scanner) {
        if (accionesRestantes > 0) {
            mostrarEstado(this, oponente);

            boolean accionValida = false;
            do {
                System.out.println("Acciones restantes: " + accionesRestantes);
                System.out.println("Elige la acción (ataque/curacion): ");
                String accion = scanner.nextLine().toLowerCase();

                if (accion.equals("ataque")) {
                    ataque(oponente);
                    accionValida = true;
                } else if (accion.equals("curacion")) {
                    curacion();
                    accionValida = true;
                } else {
                    System.out.println("Acción no válida. Realiza una acción válida (ataque/curacion).");
                }
            } while (!accionValida && accionesRestantes > 0);  // Verifica también que queden acciones restantes

            accionesRestantes--;
            System.out.println("\n");

            if (accionesRestantes == 0) {
                System.out.println("¡Se han agotado las acciones de " + this.getClass().getSimpleName() + "!");
            }
        }
    }

    private void mostrarEstado(Persona personaje, Persona oponente) {
        System.out.println("\nEstado actual:");
        System.out.println(personaje.getClass().getSimpleName() + " - Vida: " + personaje.getVida() +
                " | Defensa: " + personaje.getDefensa() + " | Acciones Restantes: " + accionesRestantes);
        System.out.println(oponente.getClass().getSimpleName() + " - Vida: " + oponente.getVida() +
                " | Defensa: " + oponente.getDefensa() + " | Acciones Restantes: " + oponente.getAccionesRestantes());
    }

    public int getAccionesRestantes() {
        return accionesRestantes;
    }
}

class Heroe extends Persona {
    public Heroe(int vida, int ataque, int defensa, int accionesRestantes) {
        super(vida, ataque, defensa, accionesRestantes);
    }

    @Override
    public int getAccionesRestantes() {
        return super.getAccionesRestantes();
    }
}

class Villano extends Persona {
    public Villano(int vida, int ataque, int defensa, int accionesRestantes) {
        super(vida, ataque, defensa, accionesRestantes);
    }

    @Override
    public int getAccionesRestantes() {
        return super.getAccionesRestantes();
    }
}

public class Juego {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Heroe heroe = new Heroe(100, 30, 10, 3);
        Villano villano = new Villano(100, 30, 10, 3);

        while (heroe.getAccionesRestantes() > 0 & villano.getAccionesRestantes() > 0 & heroe.getVida() > 0 & villano.getVida() > 0) {
            heroe.turno(villano, scanner);
            villano.turno(heroe, scanner);
        }

        mostrarResultado(heroe, villano);
    }

    private static void mostrarResultado(Heroe heroe, Villano villano) {
        System.out.println("\n¡La batalla ha terminado!");
        System.out.println("Estado final:");
        System.out.println("Heroe - Vida: " + heroe.getVida() + " | Acciones Restantes: " + heroe.getAccionesRestantes());
        System.out.println("Villano - Vida: " + villano.getVida() + " | Acciones Restantes: " + villano.getAccionesRestantes());

        if (heroe.getVida() > villano.getVida()) {
            System.out.println("¡El héroe gana con " + heroe.getVida() + " puntos de vida restantes!");
        } else if (heroe.getVida() < villano.getVida()) {
            System.out.println("¡El villano gana con " + villano.getVida() + " puntos de vida restantes!");
        } else {
            System.out.println("¡La batalla termina en empate!");
        }
    }
}
