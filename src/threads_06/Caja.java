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

    public synchronized void ingresoThreadSafe(int saldo) {
        this.saldo += saldo;
        notify();
    }

    public void ingresoNoThreadSafe(int saldo) {
        this.saldo += saldo;
    }

    public synchronized void egresoThreadSafe(int saldo) {
        boolean onHold = false;
        while (this.saldo < saldo) {
            System.out.println("Saldo Insuficiente, egreso en espera");
            onHold = true;
            try {
                Thread.sleep(50);
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (onHold)
            System.out.println("Egreso en espera reanudado;");
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
