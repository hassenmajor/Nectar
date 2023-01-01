package com.major.science;

public class Analyse
{
    // Retourner le mot qui symbolise la variable
    private static char Variable ;
    public static char Variable() { return Variable ; }

    public static String Dérivée(String Fonction)
    {
        return "(" + Fonction.replace(Variable+"", "(" + String.valueOf(Variable + "+1E-10") + ")") + ")"
                + "-" +
                "(" + Fonction.replace(Variable+"", "(" + String.valueOf(Variable + "-1E-10") + ")") + ")/2E-10";
    }
    public static double Dérivée(String Fonction, double Point)
    {
        double x = Point, y;
        double d = Image(Fonction, x + 1E-10), g = Image(Fonction, x - 1E-10);
        if (!Double.isNaN(d) && !Double.isNaN(g)) y = ( d - g ) / (2E-10) ;
        else if (!Double.isNaN(d)) y = ( Image(Fonction, x + 2E-10) - d ) / (1E-10) ;
        else if (!Double.isNaN(g)) y = ( g - Image(Fonction, x - 2E-10) ) / (1E-10) ;
        else y = Double.NaN ;
        return y;
    }

    public static double Intégrale(String Fonction, double début, double fin)
    {
        double x, y = 0, z = (fin - début) / 100 ;
        double image1 = Image(Fonction, début), image2 ;
        if (début < fin)
            for (x = début + z ; x <= fin ; x = x + z)
            {
                image2 = Image(Fonction, x);
                y = y + (image1 + image2) * z / 2 ;
                image1 = image2 ;
            }
        else if (fin < début)
            y = - Intégrale(Fonction, fin, début) ;
        return y ;
    }

    // Calculer l'image d'un nombre par la fonction donnée
    public static double Image(String Fonction, double Antécedent)
    {
        String e = Fonction ;
        double y = Double.NaN ;
        y = Arithmétique.Valeur( e ) ; if (!Double.isNaN(y)) return y ;
        Variable = 'X' ;
        for (int n = 0; n < e.length(); n++)
        {
            char x = e.charAt(n) ;
            if (Mathématique.LETTRE.indexOf(x) != -1 || Mathématique.LETTRE_GREQUE.indexOf(x) != -1)
            {
                Variable = x ;
                e = Fonction.replace(Variable+"", "(" + String.valueOf(Antécedent) + ")") ;
                y = Arithmétique.Valeur( e ) ; return y ;
            }
        }
        return y ;
    }
}
