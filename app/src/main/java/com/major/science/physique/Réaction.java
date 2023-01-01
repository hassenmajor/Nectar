package com.major.science.physique;

import com.major.science.Algèbre;

import java.util.Arrays;

public class Réaction {

    private Molécule[] Réactifs;
    private Molécule[] Produits;

    public Réaction(String[] réactifs, String[] produits) {
        Réactifs = Molécule.valueOf(réactifs);
        Produits = Molécule.valueOf(produits);
    }
    public Réaction(Molécule[] réactifs, Molécule[] produits) {
        Réactifs = réactifs;
        Produits = produits;
    }

    public double[] getCoéfficients()
    {
        double[][] m = matriceRéaction(Réactifs, Produits);
        if (Algèbre.Déterminant(m)!=0)
        {
            double[] x = new double[Réactifs.length+Produits.length];
            m = Algèbre.Inverse(m);
            for (int i=0; i<x.length; i++)
                x[i] = m[i][0];
            return x;
        }
        return null;
    }
    private static double[][] matriceCarrée(double[][] systèmeLinéaire)
    {
        double[][] M = systèmeLinéaire;
        if (M.length==M[0].length)
        {
            return M;
        }
        else if (M.length>M[0].length)
        {
            for (int i=1; i<M.length; i++)
                for (int k=i+1; k<M.length; k++)
                    if (Arrays.equals(Algèbre.Produit(M[i][0]/M[k][0], M[k]),M[i]))
                    {
                        double[][] m = new double[M.length-1][M[0].length];
                        System.arraycopy(M, 0, m, 0, k);
                        System.arraycopy(M, k+1, m, k, m.length-k);
                        return matriceCarrée(m);
                    }
        }
        return M;
    }
    private static double[][] matriceRéaction(Molécule[] Réactifs, Molécule[] Produits)
    {
        Atome[] l = Chimie.mélange(Réactifs).getAtomes();
        if (!Arrays.equals(l, Chimie.mélange(Produits).getAtomes())) return null ;
        int c = Réactifs.length+Produits.length;
        double[][] x = new double[l.length+1][c];
        if (x.length==x[0].length)
        {
            x[0][0] = 1;
            for (int i=1; i<l.length+1; i++)
                for (int j=0; j<c; j++)
                    if (j<Réactifs.length) x[i][j] = Réactifs[j].getEffectif(l[i-1]);
                    else x[i][j] = - Produits[j-Réactifs.length].getEffectif(l[i-1]);
            return x;
        }
        else if (x.length>x[0].length)
        {
            x = matriceCarrée(x); if (x.length==x[0].length) return x;
            Molécule[] ProduitsPlus = new Molécule[Produits.length+1];
            System.arraycopy(Produits, 0, ProduitsPlus, 0, Produits.length);
            Atome[] L = new Atome[0];
            Molécule[] Espèce = new Molécule[c];
            System.arraycopy(Réactifs, 0, Espèce, 0, Réactifs.length);
            System.arraycopy(Produits, 0, Espèce, Réactifs.length, Produits.length);
            for (int i=0; i<c; i++)
                if (Espèce[i].getAtomes().length==1) L = Atome.Liste(L, Espèce[i].getAtomes()[0]);
            for (int i=0; i<l.length; i++)
                if (Atome.binarySearch(L, l[i])<0)
                {
                    ProduitsPlus[Produits.length] = Molécule.valueOf(l[i]);
                    x = matriceRéaction(Réactifs, ProduitsPlus);
                    return x;
                }
        }
        return null;
    }

    public Molécule[] getRéactifs() {
        return Réactifs;
    }
    public Molécule[] getProduits() {
        return Produits;
    }

}
