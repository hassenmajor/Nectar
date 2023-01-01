package com.major.science;

import java.math.BigDecimal;

public class Arithmétique
{
    private static double AnglePI = Math.PI ;
    public static void Unité_angle(double AnglePI) { Arithmétique.AnglePI = AnglePI ; }

    // Les lettres majuscules sont des variables
    // Les lettres minuscules sont des caractères réservées

    // Calculer la valeur d'une expression arithmétique

    public static BigDecimal Valeur(char[] Expression)
    {
        try
        {
            return CalculDecimal(new String (Expression)) ;
        }
        catch(Exception e)
        {
            return null ;
        }
    }
    private static BigDecimal CalculDecimal(String Expression)
    {
        String e = Expression ;
        e = Remplacement(e) ;
        e = Croisement(e) ;
        int x = e.indexOf("(") ;
        if (x != -1)
        {
            int z = -1 ;
            for (int y = x + 1 ; y < e.length() ; y++)
            {
                if (e.charAt(y) == '(') z = z - 1 ;
                if (e.charAt(y) == ')') z = z + 1 ;
                if (z == 0)
                {
                    return CalculDecimal(
                            e.substring(0, x) +
                                    "{" + CalculDecimal(e.substring(x + 1, y)) + "}"
                                    + e.substring(y + 1)
                    ) ;
                }
            }
        }
        else
        {
            e = PuissancePrime(e) ; e = DivisionPrime(e) ; e = MultiplicationPrime(e) ;
        }
        return AdditionSoustactionPrime(e) ;
    }

    public static double Valeur(String Expression)
    {
        try
        {
            return Calcul(Expression) ;
        }
        catch(Exception e)
        {
            return Double.NaN ;
        }
    }
    private static double Calcul(String Expression)
    {
        String e = Expression ;
        e = Remplacement(e) ;
        e = Croisement(e) ;
        int x = e.indexOf("(") ;
        if (x != -1)
        {
            int z = -1 ;
            for (int y = x + 1 ; y < e.length() ; y++)
            {
                if (e.charAt(y) == '(') z = z - 1 ;
                if (e.charAt(y) == ')') z = z + 1 ;
                if (z == 0)
                {
                    return Calcul(
                            e.substring(0, x) +
                                    "{" + Calcul(e.substring(x + 1, y)) + "}"
                                    + e.substring(y + 1)
                    ) ;
                }
            }
        }
        else
        {
            e = Factorielle(e) ; e = Primorielle(e) ; e = Absolue(e) ;

            e = Logarithme_décimal(e) ; e = Croisement(e) ;
            e = Logarithme_naturel(e) ; e = Croisement(e) ;

            e = Cosinush(e) ; e = Sinush(e) ; e = Tangenteh(e) ;

            e = Arccosinus(e) ; e = Arcsinus(e) ; e = Arctangente(e) ;
            e = Cosinus(e) ; e = Sinus(e) ; e = Tangente(e) ;

            e = Racine(e) ; e = Carré(e) ;
            e = Radical(e) ; e = Puissance(e) ;

            e = Reste(e) ; e = Division(e) ; e = Multiplication(e) ;
        }
        return AdditionSoustaction(e) ;
    }

    // Donner à une expression une forme mathématiquement correcte

    private static String Remplacement(String Expression)
    {
        String x = Expression.replace(" ", "") ;
        String[] gauche = {")", "]", "!", "#", "%", "e", "\u03C0" /* signe PI */};
        String[] droite = {"(", "[", "@", "cos", "sin", "tan", "a", "l", "e", "\u03C0" /* signe PI */};
        // Remplacer la réunion de deux signes droite et gauche
        for (int n = 0 ; n <= gauche.length - 1 ; n++)
            for (int m = 0 ; m <= droite.length - 1 ; m++)
                x = x.replace(gauche[n] + droite[m], gauche[n] + "*" + droite[m]) ;
        // Remplacer la reunion d'un signe et d'un chiffre
        for (int n = 0 ; n <= 9 ; n++)
        {
            for (int m = 0 ; m <= gauche.length - 1 ; m++)
            {
                x = x.replace(gauche[m] + Integer.toString(n),
                        gauche[m] + "*" + Integer.toString(n)) ;
                x = x.replace(gauche[m] + Mathématique.CHIFFRE_EXPOSANT.charAt(n),
                        gauche[m] + "^" + Mathématique.CHIFFRE_EXPOSANT.charAt(n)) ;
            }
            for (int m = 0 ; m <= droite.length - 1 ; m++)
            {
                x = x.replace(Integer.toString(n) + droite[m],
                        Integer.toString(n) + "*" + droite[m]) ;
                x = x.replace(Mathématique.CHIFFRE_EXPOSANT.charAt(n) + droite[m],
                        Mathématique.CHIFFRE_EXPOSANT.charAt(n) + "*" + droite[m]) ;
            }
            for (int m = 0 ; m <= 9 ; m++)
            {
                x = x.replace(Integer.toString(m) + Mathématique.CHIFFRE_EXPOSANT.charAt(n),
                        Integer.toString(m) + "^" + Mathématique.CHIFFRE_EXPOSANT.charAt(n)) ;
                x = x.replace(Mathématique.CHIFFRE_EXPOSANT.charAt(n) + Integer.toString(m),
                        Mathématique.CHIFFRE_EXPOSANT.charAt(n) + "*" + Integer.toString(m)) ;
            }
        }
        // Remplacer les exposants par des chiffres
        for (int n = 0 ; n <= 9 ; n++)
            x = x.replace(Mathématique.CHIFFRE_EXPOSANT.charAt(n)+"", n+"") ;
        // Remplacer les signes synonymes
        String[] avant = {",", "×", "÷",      "%", "exp",  "[", "]", "\u221A"} ;
        String[] après = {".", "*", "/", "/(100)",  "e^", "@(", ")", "(2)\u2206"} ;
        for (int n = 0 ; n <= avant.length - 1 ; n++)
        {
            x = x.replace(avant[n], après[n]) ;
        }
        // Remplacer les constantes PI et e
        x = x.replace("\u03C0" /* signe PI */, "("+Double.toString(Math.PI)+")") ;
        x = x.replace("e", "("+Double.toString(Math.E)+")") ;
        return x ;
    }
    private static String Croisement(String Expression)
    {
        String x = Expression ;
        while (x.indexOf("++") != -1
                || x.indexOf("+-") != -1
                || x.indexOf("-+") != -1
                || x.indexOf("--") != -1)
        {
            x = x.replace("++", "+") ;
            x = x.replace("+-", "-") ;
            x = x.replace("-+", "-") ;
            x = x.replace("--", "+") ;
        }
        return x ;
    }

    // Calcul des fonctions raccourcis (facto et primo)

    private static String Factorielle(String Expression)
    {
        String x = Croisement(Expression) ;
        char o = '!' ;
        int n = x.indexOf(o) ;
        if (n != -1)
        {
            String g = x.substring(0, n) ;
            String d = x.substring(n + 1) ;
            String ng = Nombre_gauche(g) ;
            return Factorielle(
                    g.substring(0, n - ng.length())
                            + "+"
                            + Mathématique.Factorielle( (int) Nombre(ng) )
                            + d
            ) ;
        }
        else return x ;
    }
    private static String Primorielle(String Expression)
    {
        String x = Croisement(Expression) ;
        char o = '#' ;
        int n = x.indexOf(o) ;
        if (n != -1)
        {
            String g = x.substring(0, n) ;
            String d = x.substring(n + 1) ;
            String ng = Nombre_gauche(g) ;
            return Primorielle(
                    g.substring(0, n - ng.length())
                            + "+"
                            + Mathématique.Primorielle( (int) Nombre(ng) )
                            + d
            ) ;
        }
        else return x ;
    }
    private static String Carré(String Expression)
    {
        String x = Croisement(Expression) ;
        char o = '²' ;
        int n = x.indexOf(o) ;
        if (n != -1)
        {
            String g = x.substring(0, n) ;
            String d = x.substring(n + 1) ;
            String ng = Nombre_gauche(g) ;
            return Carré(
                    g.substring(0, n - ng.length())
                            + "+"
                            + Math.pow(Nombre(ng), 2)
                            + d
            ) ;
        }
        else return x ;
    }
    private static String Absolue(String Expression)
    {
        String x = Croisement(Expression) ;
        char o = '@' ;
        int n = x.lastIndexOf(o) ;
        if (n != -1)
        {
            String g = x.substring(0, n) ;
            String d = x.substring(n + 1) ;
            String nd = Nombre_droite(d) ;
            return Absolue(
                    g
                            + "+"
                            + Math.abs(Nombre(nd))
                            + d.substring(nd.length())
            ) ;
        }
        else return x ;
    }
    private static String Racine(String Expression)
    {
        String x = Croisement(Expression) ;
        char o = '\u221A' ;
        int n = x.lastIndexOf(o) ;
        if (n != -1)
        {
            String g = x.substring(0, n) ;
            String d = x.substring(n + 1) ;
            String nd = Nombre_droite(d) ;
            return Racine(
                    g
                            + "+"
                            + Math.sqrt(Nombre(nd))
                            + d.substring(nd.length())
            ) ;
        }
        else return x ;
    }

    // Calcul des fonctions analytiques (ln et exp)

    private static String Logarithme_naturel(String Expression)
    {
        String x = Croisement(Expression) ;
        String o = "ln" ;
        int n = x.lastIndexOf(o) ;
        if (n != -1)
        {
            String g = x.substring(0, n) ;
            String d = x.substring(n + o.length()) ;
            String nd = Nombre_droite(d) ;
            return Logarithme_naturel(
                    g
                            + "+"
                            + Math.log(Nombre(nd))
                            + d.substring(nd.length())
            ) ;
        }
        else return x ;
    }
    private static String Logarithme_décimal(String Expression)
    {
        String x = Croisement(Expression) ;
        String o = "log" ;
        int n = x.lastIndexOf(o) ;
        if (n != -1)
        {
            String g = x.substring(0, n) ;
            String d = x.substring(n + o.length()) ;
            String nd = Nombre_droite(d) ;
            return Logarithme_décimal(
                    g
                            + "+"
                            + Math.log10(Nombre(nd))
                            + d.substring(nd.length())
            ) ;
        }
        else return x ;
    }

    // Calcul des fonctions trigonométriques et hyperboliques (cos, sin, tan, ...)

    private static String Cosinush(String Expression)
    {
        String x = Croisement(Expression) ;
        String o = "cosh" ;
        int n = x.lastIndexOf(o) ;
        if (n != -1)
        {
            String g = x.substring(0, n) ;
            String d = x.substring(n + o.length()) ;
            String nd = Nombre_droite(d) ;
            return Cosinush(
                    g
                            + "+"
                            + Math.cosh(Nombre(nd))
                            + d.substring(nd.length())
            ) ;
        }
        else return x ;
    }
    private static String Sinush(String Expression)
    {
        String x = Croisement(Expression) ;
        String o = "sinh" ;
        int n = x.lastIndexOf(o) ;
        if (n != -1)
        {
            String g = x.substring(0, n) ;
            String d = x.substring(n + o.length()) ;
            String nd = Nombre_droite(d) ;
            return Sinush(
                    g
                            + "+"
                            + Math.sinh(Nombre(nd))
                            + d.substring(nd.length())
            ) ;
        }
        else return x ;
    }
    private static String Tangenteh(String Expression)
    {
        String x = Croisement(Expression) ;
        String o = "tanh" ;
        int n = x.lastIndexOf(o) ;
        if (n != -1)
        {
            String g = x.substring(0, n) ;
            String d = x.substring(n + o.length()) ;
            String nd = Nombre_droite(d) ;
            return Tangenteh(
                    g
                            + "+"
                            + Math.tanh(Nombre(nd))
                            + d.substring(nd.length())
            ) ;
        }
        else return x ;
    }

    private static String Arccosinus(String Expression)
    {
        String x = Croisement(Expression) ;
        String o = "acos" ;
        int n = x.lastIndexOf(o) ;
        if (n != -1)
        {
            String g = x.substring(0, n) ;
            String d = x.substring(n + o.length()) ;
            String nd = Nombre_droite(d) ;
            return Arccosinus(
                    g
                            + "+"
                            + Math.acos(Nombre(nd)) * AnglePI / Math.PI
                            + d.substring(nd.length())
            ) ;
        }
        else return x ;
    }
    private static String Arcsinus(String Expression)
    {
        String x = Croisement(Expression) ;
        String o = "asin" ;
        int n = x.lastIndexOf(o) ;
        if (n != -1)
        {
            String g = x.substring(0, n) ;
            String d = x.substring(n + o.length()) ;
            String nd = Nombre_droite(d) ;
            return Arcsinus(
                    g
                            + "+"
                            + Math.asin(Nombre(nd)) * AnglePI / Math.PI
                            + d.substring(nd.length())
            ) ;
        }
        else return x ;
    }
    private static String Arctangente(String Expression)
    {
        String x = Croisement(Expression) ;
        String o = "atan" ;
        int n = x.lastIndexOf(o) ;
        if (n != -1)
        {
            String g = x.substring(0, n) ;
            String d = x.substring(n + o.length()) ;
            String nd = Nombre_droite(d) ;
            return Arctangente(
                    g
                            + "+"
                            + Math.atan(Nombre(nd)) * AnglePI / Math.PI
                            + d.substring(nd.length())
            ) ;
        }
        else return x ;
    }

    private static String Cosinus(String Expression)
    {
        String x = Croisement(Expression) ;
        String o = "cos" ;
        int n = x.lastIndexOf(o) ;
        if (n != -1)
        {
            String g = x.substring(0, n) ;
            String d = x.substring(n + o.length()) ;
            String nd = Nombre_droite(d) ;
            return Cosinus(
                    g
                            + "+"
                            + Math.cos(Nombre(nd) * Math.PI / AnglePI)
                            + d.substring(nd.length())
            ) ;
        }
        else return x ;
    }
    private static String Sinus(String Expression)
    {
        String x = Croisement(Expression) ;
        String o = "sin" ;
        int n = x.lastIndexOf(o) ;
        if (n != -1)
        {
            String g = x.substring(0, n) ;
            String d = x.substring(n + o.length()) ;
            String nd = Nombre_droite(d) ;
            return Sinus(
                    g
                            + "+"
                            + Math.sin(Nombre(nd) * Math.PI / AnglePI)
                            + d.substring(nd.length())
            ) ;
        }
        else return x ;
    }
    private static String Tangente(String Expression)
    {
        String x = Croisement(Expression) ;
        String o = "tan" ;
        int n = x.lastIndexOf(o) ;
        if (n != -1)
        {
            String g = x.substring(0, n) ;
            String d = x.substring(n + o.length()) ;
            String nd = Nombre_droite(d) ;
            return Tangente(
                    g
                            + "+"
                            + Math.tan(Nombre(nd) * Math.PI / AnglePI)
                            + d.substring(nd.length())
            ) ;
        }
        else return x ;
    }

    // Calcul des opérations binaires composées (produit, quotient, reste ...)

    private static String Radical(String Expression)
    {
        String x = Croisement(Expression) ;
        int n = x.indexOf('\u2206') ;
        if (n != -1)
        {
            String g = x.substring(0, n) ;
            String d = x.substring(n + 1) ;
            String ng = Nombre_gauche(g) ;
            String nd = Nombre_droite(d) ;
            return Radical(
                    g.substring(0, n - ng.length())
                            + "+"
                            + Math.pow(Nombre(nd), 1 / Nombre(ng))
                            + d.substring(nd.length())
            ) ;
        }
        else return x ;
    }
    private static String Puissance(String Expression)
    {
        String x = Croisement(Expression) ;
        int n = x.indexOf('^') ;
        if (n != -1)
        {
            String g = x.substring(0, n) ;
            String d = x.substring(n + 1) ;
            String ng = Nombre_gauche(g) ;
            String nd = Nombre_droite(d) ;
            return Puissance(
                    g.substring(0, n - ng.length())
                            + "+"
                            + Math.pow(Nombre(ng), Nombre(nd))
                            + d.substring(nd.length())
            ) ;
        }
        else return x ;
    }
    private static String PuissancePrime(String Expression)
    {
        String x = Croisement(Expression) ;
        int n = x.indexOf('^') ;
        if (n != -1)
        {
            String g = x.substring(0, n) ;
            String d = x.substring(n + 1) ;
            String ng = Nombre_gauche(g) ;
            String nd = Nombre_droite(d) ;
            return PuissancePrime(
                    g.substring(0, n - ng.length())
                            + "+"
                            + NombrePrime(ng).pow((int)Nombre(nd))
                            + d.substring(nd.length())
            ) ;
        }
        else return x ;
    }
    private static String Reste(String Expression)
    {
        String x = Croisement(Expression) ;
        int n = x.indexOf('\\') ;
        if (n != -1)
        {
            String g = x.substring(0, n) ;
            String d = x.substring(n + 1) ;
            String ng = Nombre_gauche(g) ;
            String nd = Nombre_droite(d) ;
            return Reste(
                    g.substring(0, n - ng.length())
                            + "+"
                            + Nombre(ng) % Nombre(nd)
                            + d.substring(nd.length())
            ) ;
        }
        else return x ;
    }
    private static String Division(String Expression)
    {
        String x = Croisement(Expression) ;
        int n = x.indexOf('/') ;
        if (n != -1)
        {
            String g = x.substring(0, n) ;
            String d = x.substring(n + 1) ;
            String ng = Nombre_gauche(g) ;
            String nd = Nombre_droite(d) ;
            return Division(
                    g.substring(0, n - ng.length())
                            + "+"
                            + Nombre(ng) / Nombre(nd)
                            + d.substring(nd.length())
            ) ;
        }
        else return x ;
    }
    private static String DivisionPrime(String Expression)
    {
        String x = Croisement(Expression) ;
        int n = x.indexOf('/') ;
        if (n != -1)
        {
            String g = x.substring(0, n) ;
            String d = x.substring(n + 1) ;
            String ng = Nombre_gauche(g) ;
            String nd = Nombre_droite(d) ;
            return DivisionPrime(
                    g.substring(0, n - ng.length())
                            + "+"
                            + NombrePrime(ng).divide(NombrePrime(nd))
                            + d.substring(nd.length())
            ) ;
        }
        else return x ;
    }
    private static String Multiplication(String Expression)
    {
        String x = Croisement(Expression) ;
        int n = x.indexOf('*') ;
        if (n != -1)
        {
            String g = x.substring(0, n) ;
            String d = x.substring(n + 1) ;
            String ng = Nombre_gauche(g) ;
            String nd = Nombre_droite(d) ;
            return Multiplication(
                    g.substring(0, n - ng.length())
                            + "+"
                            + Nombre(ng) * Nombre(nd)
                            + d.substring(nd.length())
            ) ;
        }
        else return x ;
    }
    private static String MultiplicationPrime(String Expression)
    {
        String x = Croisement(Expression) ;
        int n = x.indexOf('*') ;
        if (n != -1)
        {
            String g = x.substring(0, n) ;
            String d = x.substring(n + 1) ;
            String ng = Nombre_gauche(g) ;
            String nd = Nombre_droite(d) ;
            return MultiplicationPrime(
                    g.substring(0, n - ng.length())
                            + "+"
                            + NombrePrime(ng).multiply(NombrePrime(nd))
                            + d.substring(nd.length())
            ) ;
        }
        else return x ;
    }

    // Calcul des opérations binaires simples (somme et différence)

    private static double AdditionSoustaction(String Expression)
    {
        String x = Expression.replace("{", "").replace("}", "") ;
        x = Croisement(x) ;
        if (Mathématique.Logique.estNombre(x)) return Double.valueOf(x) ;
        String d = Nombre_droite(x) ;
        return Double.valueOf(d) + AdditionSoustaction(x.substring(d.length())) ;
    }
    private static BigDecimal AdditionSoustactionPrime(String Expression)
    {
        String x = Expression.replace("{", "").replace("}", "") ;
        x = Croisement(x) ;
        if (Mathématique.Logique.estNombre(x)) return new BigDecimal(x) ;
        String d = Nombre_droite(x) ;
        return new BigDecimal(d).add(AdditionSoustactionPrime(x.substring(d.length()))) ;
    }

    // Détecter un nombre à l'extrémité gauche ou droite d'une expression

    private static String Nombre_droite(String Expression)
    {
        String x = Expression ;
        String y ;
        boolean a , b ;
        for (int n = x.length() ; n > 0 ; n--)
        {
            y = x.substring(0, n) ;
            a = Mathématique.Logique.estNombre(y) ;
            b = y.charAt(0) == '{' && y.charAt(y.length()-1) == '}'
                    &&
                    Mathématique.Logique.estNombre( y.substring( 1, y.length()-1 ) ) ;
            if (a || b) return y ;
        }
        return "" ;
    }
    private static String Nombre_gauche(String Expression)
    {
        String x = Expression ;
        String y ;
        boolean a , b ;
        for (int n = 0 ; n < x.length() ; n++)
        {
            y = x.substring(n) ;
            a = Mathématique.Logique.estNombre(y)
                    && Double.valueOf(y) >= 0 ;
            b = y.charAt(y.length()-1) == '}' && y.charAt(0) == '{'
                    &&
                    Mathématique.Logique.estNombre( y.substring( 1, y.length()-1 ) ) ;
            if (a || b) return y ;
        }
        return "" ;
    }
    private static double Nombre(String Expression)
    {
        String x = Expression ;
        if (x.charAt(0) == '{') return Double.valueOf(x.substring( 1, x.length()-1 )) ;
        else return Double.valueOf(x) ;
    }
    private static BigDecimal NombrePrime(String Expression)
    {
        String x = Expression ;
        if (x.charAt(0) == '{') return new BigDecimal(x.substring( 1, x.length()-1 )) ;
        else return new BigDecimal(x) ;
    }
}
