package com.major.science.physique;

import com.major.science.Arithmétique;
import com.major.science.Mathématique;
import java.math.BigInteger;

public class Chimie {

    // les données de la classe
    public final static String[] ATOM_LIST = {"H", "He", "Li", "Be", "B", "C", "N", "O", "F", "Ne",
            "Na", "Mg", "Al", "Si", "P", "S", "Cl", "Ar", "K", "Ca",
            "Sc", "Ti", "V", "Cr", "Mn", "Fe", "Co", "Ni", "Cu", "Zn",
            "Ga", "Ge", "As", "Se", "Br", "Kr", "Rb", "Sr", "Y", "Zr",
            "Nb", "Mo", "Tc", "Ru", "Rh", "Pd", "Ag", "Cd", "In", "Sn",
            "Sb", "Te", "I", "Xe", "Cs", "Ba", "La", "Ce", "Pr", "Nd",
            "Pm", "Sm", "Eu", "Gd", "Tb", "Dy", "Ho", "Er", "Tm", "Yb",
            "Lu", "Hf", "Ta", "W", "Re", "Os", "Ir", "Pt", "Au", "Hg",
            "Tl", "Pb", "Bi", "Po", "At", "Rn", "Fr", "Ra", "Ac", "Th",
            "Pa", "U", "Np", "Pu", "Am", "Cm", "Bk", "Cf", "Es", "Fm",
            "Md", "No", "Lr", "Rf", "Db", "Sg", "Bh", "Hs", "Mt", "Ds",
            "Rg", "Cn", "Nh", "Fl", "Mc", "Lv", "Ts", "Og"};
    public final static double[] MOLAR_LIST = {1.008, 4.003, 6.941, 9.012, 10.811, 12.011, 14.007, 15.999, 18.998, 20.180,
            22.990, 24.305, 26.982, 28.086, 30.974, 32.066, 35.453, 39.948, 39.098, 40.078,
            44.956, 47.880, 50.942, 51.996, 54.938, 55.847, 58.933, 58.690, 63.556, 65.390,
            69.723, 72.610, 74.922, 78.960, 79.904, 83.798, 85.467, 87.620, 88.906, 91.224,
            92.906, 95.940, 98.906, 101.070, 102.905, 106.420, 107.868, 112.411, 114.820, 118.710,
            121.750, 127.600, 126.904, 131.293, 132.905, 137.327, 138.906, 140.115, 140.908, 144.240,
            146.915, 150.360, 151.965, 157.250, 158.925, 162.500, 164.930, 167.260, 168.934, 173.040,
            174.967, 178.490, 180.948, 183.850, 186.207, 190.230, 192.217, 195.084, 196.967, 200.590,
            204.383, 207.200, 208.980, 208.982, 209.987, 222.018, 223.020, 226.025, 227.028, 232.038,
            231.036, 238.029, 237.048, 244.064, 243.061, 247.070, 247.070, 251.080, 252.083, 257.095,
            258.099, 259.101, 260.105, 261.108, 262.114, 263.118, 262.123, 265.000, 266.000, 269.000,
            272.000, 277.000, 286.000, 289.000, 289.000, 293.000, 294.000, 294.000};

    // les méthodes statiques
    public static Molécule mélange(Molécule... molécules)
    {
        BigInteger p = BigInteger.ONE;
        for (Molécule e: molécules)
            p = p.multiply(e.getProduitFacteursPremiers());
        return new Molécule(p);
    }
    public static double getMasseMolaire(String formuleBrute)
    {
        String x = formuleBrute.replace(" ", "");
        if (!estFormuleBrute(x)) return Double.NaN;
        for (int i = 0; i < ATOM_LIST.length; i++)
            if (ATOM_LIST[i].length()==2) x = x.replace(ATOM_LIST[i], "("+MOLAR_LIST[i]+")");
        for (int i = 0; i < ATOM_LIST.length; i++)
            if (ATOM_LIST[i].length()==1) x = x.replace(ATOM_LIST[i], "("+MOLAR_LIST[i]+")");
        for (int i = 0; i < 10; i++)
            x = x.replace(i+"(", i+"+(");
        x = x.replace(")(", ")+(");
        return Arithmétique.Valeur(x.toCharArray()).doubleValue();
    }
    public static double getMasseAtomique(String symbole)
    {
        String x = symbole;
        for (int i = 0; i < ATOM_LIST.length; i++)
            if (ATOM_LIST[i]==x) return MOLAR_LIST[i];
        return Double.NaN;
    }
    public static boolean estFormuleBrute(String Expression)
    {
        String x = Expression.replace(" ", "");
        if (Mathématique.LETTRE.indexOf(x.charAt(0))<0) return false;
        String signe = "()"+Mathématique.CHIFFRE+Mathématique.LETTRE+Mathématique.LETTRE.toLowerCase();
        int p=0;
        for (char e: x.toCharArray()) {
            if (signe.indexOf(e)<0) return false;
            if (Character.isDigit(e) && x.contains("("+e)) return false;
            if (e=='(') p++;
            else if (e==')') p--;
            if (p<0) return false;
        }
        return p==0;
    }
    public static boolean estCoéfficients(Réaction réaction, int[] coefficients)
    {
        BigInteger x1 = new BigInteger("1"), x2 = new BigInteger("1");
        for (int i=0; i<réaction.getRéactifs().length; i++)
        {
            x1 = x1.multiply(réaction.getRéactifs()[i].getProduitFacteursPremiers().pow(coefficients[i]));
        }
        for (int i=0; i<réaction.getProduits().length; i++)
        {
            x2 = x2.multiply(réaction.getProduits()[i].getProduitFacteursPremiers().pow(coefficients[réaction.getRéactifs().length+i]));
        }
        return (x1.compareTo(x2)==0);
    }

    public static String versIndice(String Expression)
    {
        String x = Expression;
        for (int i = 0; i < 10; i++)
            x = x.replace(Mathématique.CHIFFRE.charAt(i), Mathématique.CHIFFRE_INDICE.charAt(i));
        return x;
    }
    public static String versExposant(String Expression)
    {
        String x = Expression;
        for (int i = 0; i < 10; i++)
            x = x.replace(Mathématique.CHIFFRE.charAt(i), Mathématique.CHIFFRE_EXPOSANT.charAt(i));
        return x;
    }

}
