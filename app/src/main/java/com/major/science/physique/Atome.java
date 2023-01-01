package com.major.science.physique;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.major.science.Mathématique;

public class Atome extends Corps
{

    // les données d'instances
    private int protons;
    private String symbole;
    protected int nombrePremier;

    // les constructeurs
    public Atome(int Z) throws IndexOutOfBoundsException
    {
        if (1<=Z && Z<=118) {
            this.protons = Z;
            this.symbole = Chimie.ATOM_LIST[protons-1];
            this.nombrePremier = Mathématique.NOMBRE_PREMIER[protons-1];
            super.masse = Chimie.MOLAR_LIST[protons-1];
        } else throw new IndexOutOfBoundsException();
    }
    public Atome(String symbole) throws IndexOutOfBoundsException
    {
        for (int Z=1; Z<=Chimie.ATOM_LIST.length; Z++)
            if (Chimie.ATOM_LIST[Z-1]==symbole) {
                this.protons = Z;
                this.symbole = Chimie.ATOM_LIST[protons-1];
                this.nombrePremier = Mathématique.NOMBRE_PREMIER[protons-1];
                super.masse = Chimie.MOLAR_LIST[protons-1];
            }
        throw new IndexOutOfBoundsException();
    }

    // les getteurs
    public String getSymbole() {
        return symbole;
    }
    public int getProtons()
    {
        return protons;
    }
    protected int getNombrePremier() {
        return nombrePremier;
    }

    // Fusionner deux listes
    public static Atome[] Liste(Atome[] Liste1, Atome[] Liste2)
    {
        Atome[] x = new Atome[Liste1.length+Liste2.length] ;
        System.arraycopy(Liste1, 0, x, 0, Liste1.length);
        System.arraycopy(Liste2, 0, x, Liste1.length, Liste2.length);
        return x;
    }
    public static Atome[] Liste(Atome[] Liste, Atome Nouveau)
    {
        return Liste(Liste, new Atome[] {Nouveau} );
    }
    public static Atome[] Liste(Atome Nouveau, Atome[] Liste)
    {
        return Liste(new Atome[] {Nouveau}, Liste);
    }
    public static Atome[] Liste(Atome Element)
    {
        return new Atome[] {Element} ;
    }
    public static Atome[] Liste(int... numerosAtomiques)
    {
        Atome[] x = new Atome[numerosAtomiques.length];
        for (int i=0; i<x.length; i++)
            x[i] = new Atome(numerosAtomiques[i]);
        return x ;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        return (obj instanceof Atome) && ((Atome)obj).getNombrePremier()==this.nombrePremier;
    }
    @NonNull
    @Override
    public String toString() {
        return super.toString();
    }
    public Molécule toMolécule()
    {
        return new Molécule(new Atome[]{this}, new int[]{1});
    }
    public static int binarySearch(Atome[] a, Atome key)
    {
        for (int i=0; i<a.length; i++)
            if (a[i].equals(key)) return i;
        return -1;
    }
}
