package com.major.science.physique;

public class Corps
{
    // les données
    protected double masse = 1;
    protected double charge = 0;

    // les constructeurs
    public Corps() { }
    public Corps(double masse)
    {
        this.masse = masse;
    }
    public Corps(double masse, double charge)
    {
        this.masse = masse;
        this.charge = charge;
    }

    public double getMasse()
    {
        return masse;
    }
    public double getCharge()
    {
        return charge;
    }

    // Particules élémentaires
    public static final Corps PROTON = new Corps(934e0 , +1) ;
    public static final Corps ELECTRON = new Corps(511e-3 , -1) ;
    public static final Corps NEUTRON = new Corps(939e0 , 0) ;
}
