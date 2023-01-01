package com.major.science.physique;

public class Solide extends Corps
{
    // les données
    public double taille;
    public double age;

    // les constructeurs
    public Solide()
    {
        super() ;
        taille = 0 ;
        age = Double.POSITIVE_INFINITY ;
    }
    public Solide(double masse, double charge, double taille, double age)
    {
        super(masse, charge) ;
        this.taille = taille ;
        this.age = age ;
    }

    public double getDensité()
    {
        return masse / taille ;
    }

    // Astres principaux
    final Solide TERRE = new Solide(5.98e24, 0, 2 * 6.37e6, 0) ;
    final Solide LUNE = new Solide(7.36e22, 0, 2 * 1.74e6, 0) ;
    final Solide SOLEIL = new Solide(1.99e30, 0, 2 * 6.96e8, 0) ;
}
