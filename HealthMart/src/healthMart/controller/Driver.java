package healthmart.controller;

import healthmart.model.*;

/**
 * Abstract class Driver - Base class untuk Admin dan Customer Driver
 * Mendemonstrasikan Abstraction di layer controller
 */
public abstract class Driver {
    
    protected Akun akun;
    protected ListObat listObat;
    
    /**
     * Constructor Driver
     */
    public Driver(Akun akun, ListObat listObat) {
        this.akun = akun;
        this.listObat = listObat;
    }
    
    /**
     * Getter Akun
     */
    public Akun getAkun() {
        return akun;
    }
    
    /**
     * Getter ListObat
     */
    public ListObat getListObat() {
        return listObat;
    }
    
    /**
     * Abstract method - Setiap driver harus implement menu utama
     */
    public abstract void showMainMenu();
    
    /**
     * Logout
     */
    public void logout() {
        System.out.println(akun.getNama() + " telah logout");
        this.akun = null;
    }
}
