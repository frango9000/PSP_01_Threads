package threads_06;

import java.util.Objects;

public class Caja {

    private Integer saldo = 0;

    public Caja() {
    }

    public Caja(int saldo) {
        this.saldo = saldo;
    }


    public int getSaldo() {
        return saldo;
    }

    public void setSaldo(int saldo) {
        this.saldo = saldo;
    }

    // en cada ingreso de saldo se les notifica a los hilos que pueden volver a intentar realizar un egreso
    public synchronized void ingresoThreadSafe(int saldo) {
        System.out.println("Ingreso realizado : " + this.saldo + " + " + saldo + " = " + (this.saldo+saldo));
        this.saldo += saldo;
        notify();
    }

    public void ingresoNoThreadSafe(int saldo) {
        this.saldo += saldo;
    }

    // metodo para q el consumidor egrese saldo, primero compruba que el egreso se puede realizar sin
    // dejar el saldo negativo, si no hay saldo suficiente el hilo entra en wait() a la espera de algun
    // notify que llegara de cualquier ingreso nuevo, momento en el qque se volvera a realizar la
    // verificacion de saldo suficiente
    public synchronized void egresoThreadSafe(int saldo) {
        boolean onHold = false;
        while (this.saldo < saldo) {
            System.out.println("Saldo Insuficiente, egreso en espera " + this.saldo + " - " + saldo + " = " + (this.saldo-saldo));
            onHold = true;
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Egreso" + (onHold? " en espera ":" directo ") + "finalizado: "  + this.saldo + " - " + saldo + " = " +(this.saldo-saldo));
        this.saldo -= saldo;
    }
    public void egresoNoThreadSafe(int saldo) {
        this.saldo -= saldo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Caja caja = (Caja) o;
        return getSaldo() == caja.getSaldo();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSaldo());
    }


    @Override
    public String toString() {
        return "Caja{" +
               "saldo=" + saldo +
               '}';
    }
}
