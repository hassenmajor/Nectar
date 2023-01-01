package com.major.science.physique;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.major.science.Algèbre;
import com.major.science.Arithmétique;
import com.major.science.Mathématique;
import java.math.BigInteger;

public class Molécule extends Corps {

    // les données d'instances
    private String formuleBrute;
    private BigInteger produitFacteursPremiers;

    // les constructeurs
    public Molécule(Atome[] elements, int[] effectifs) throws IllegalArgumentException
    {
        if (elements.length==effectifs.length) {
            produitFacteursPremiers = BigInteger.ONE;
            for (int i = 0; i < effectifs.length; i++) {
                // Calculer le produit de facteurs premiers
                produitFacteursPremiers = produitFacteursPremiers
                        .multiply(new BigInteger(elements[i].nombrePremier+"").pow(effectifs[i]));
            }
        } else throw new IllegalArgumentException();
        if (produitFacteursPremiers.compareTo(BigInteger.valueOf(2))<0)
            throw new IllegalArgumentException();
        calculer();
    }
    public Molécule(String formuleBrute) throws IllegalArgumentException
    {
        String x = formuleBrute.replace(" ", "");
        if (!Chimie.estFormuleBrute(formuleBrute))
            throw new IllegalArgumentException();
        // Calculer le produit de facteurs premiers
        x = Chimie.versExposant(x);
        for (int i = 0; i < Chimie.ATOM_LIST.length; i++)
            if (Chimie.ATOM_LIST[i].length()==2)
                x = x.replace(Chimie.ATOM_LIST[i], "("+Mathématique.NOMBRE_PREMIER[i]+")");
        for (int i = 0; i < Chimie.ATOM_LIST.length; i++)
            if (Chimie.ATOM_LIST[i].length()==1)
                x = x.replace(Chimie.ATOM_LIST[i], "("+Mathématique.NOMBRE_PREMIER[i]+")");
        produitFacteursPremiers = Arithmétique.Valeur(x.toCharArray()).toBigInteger();
        if (!Double.isFinite(Arithmétique.Valeur(x)))
            throw new IllegalArgumentException();
        if (produitFacteursPremiers.compareTo(BigInteger.valueOf(2))<0)
            throw new IllegalArgumentException();
        calculer();
    }
    public Molécule(BigInteger produitFacteursPremiers) throws IllegalArgumentException
    {
        if (produitFacteursPremiers.compareTo(BigInteger.valueOf(2))<0)
            throw new IllegalArgumentException();
        this.produitFacteursPremiers = produitFacteursPremiers;
        calculer();
    }
    private void calculer()
    {
        Atome[] elements = this.getAtomes();
        int[] effectifs = this.getEffectifs();
        super.masse = 0;
        formuleBrute = "";
        for (int i = 0; i < elements.length; i++) {
            // Calculer la masse molaire
            super.masse = super.masse + effectifs[i] * elements[i].getMasse();
            // Déterminer la formule brute
            if (effectifs[i]==1) formuleBrute = formuleBrute + elements[i].getSymbole();
            else formuleBrute = formuleBrute + elements[i].getSymbole() + Mathématique.versIndice(effectifs[i]);
        }
    }

    // les getteurs
    public String getFormuleBrute() {
        return formuleBrute;
    }
    public BigInteger getProduitFacteursPremiers() {
        return produitFacteursPremiers;
    }

    public Atome[] getAtomes()
    {
        BigInteger x = produitFacteursPremiers;
        int[] y = new int[0];
        for (int i=0; i<Mathématique.NOMBRE_PREMIER.length; i++)
        {
            if (x.mod(BigInteger.valueOf(Mathématique.NOMBRE_PREMIER[i])).equals(new BigInteger(0+"")))
                y = Algèbre.Liste(y, i+1);
        }
        return Atome.Liste(y);
    }
    public int[] getEffectifs()
    {
        Atome[] x = getAtomes();
        int[] y = new int[x.length] ;
        for (int i = 0; i < x.length; i++)
            y[i] = getEffectif(x[i]);
        return y;
    }
    public int getEffectif(Atome element)
    {
        BigInteger x = produitFacteursPremiers;
        int y = 0;
        while (x.mod(BigInteger.valueOf(element.nombrePremier)).equals(BigInteger.ZERO))
        {
            x = x.divide(BigInteger.valueOf(element.nombrePremier));
            y++;
        }
        return y;
    }
    public int getEffectifTotal()
    {
        int y = 0;
        for (Atome element: getAtomes())
            y = y + getEffectif(element);
        return y;
    }

    // les méthodes statiques
    public static Molécule[] Liste(String[] formuleBrute)
    {
        String[] x = formuleBrute;
        Molécule[] y = new Molécule[x.length];
        for (int i = 0; i < x.length; i++)
            y[i] = new Molécule(x[i]);
        return y;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        return (obj instanceof Molécule) && ((Molécule)obj).getProduitFacteursPremiers()==this.produitFacteursPremiers;
    }
    @NonNull
    @Override
    public String toString() {
        return super.toString();
    }

    public static Molécule valueOf(Atome element, int effectif)
    {
        return new Molécule(new Atome[]{element}, new int[]{effectif});
    }
    public static Molécule valueOf(Atome element)
    {
        return new Molécule(new Atome[]{element}, new int[]{1});
    }
    public static Molécule[] valueOf(String[] formuleBrute)
    {
        String[] x = formuleBrute;
        Molécule[] y = new Molécule[x.length];
        for (int i = 0; i < x.length; i++)
            y[i] = new Molécule(x[i]);
        return y;
    }
}
