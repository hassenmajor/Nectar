package com.major.science;

public class Mathématique
{
    // Quelques constantes utiles
    public final static String CHIFFRE = "0123456789" ;
    public final static String LETTRE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" ;
    public final static String SYMBOLE = "+-×÷" ;
    public final static String CHIFFRE_EXPOSANT = "\u2070\u00B9\u00B2\u00B3\u2074\u2075\u2076\u2077\u2078\u2079" ;
    public final static String CHIFFRE_INDICE = "\u2080\u2081\u2082\u2083\u2084\u2085\u2086\u2087\u2088\u2089" ;
    public final static String CHIFFRE_ROMAIN = "IVXLCDM" ;
    public final static String LETTRE_GREQUE = "\u0391\u0392\u0393\u0394\u0395\u0396\u0397\u0398\u0399\u039A"
            + "\u039B\u039C\u039D\u039E\u039F\u03A0\u03A1\u03A3\u03A4\u03A5"
            + "\u03A6\u03A7\u03A8\u03A9" ;
    public final static int[] NOMBRE_PREMIER = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71,
            73, 79, 83, 89, 97, 101, 103, 107, 109, 113, 127, 131, 137, 139, 149, 151, 157, 163, 167, 173,
            179, 181, 191, 193, 197, 199, 211, 223, 227, 229, 233, 239, 241, 251, 257, 263, 269, 271, 277, 281,
            283, 293, 307, 311, 313, 317, 331, 337, 347, 349, 353, 359, 367, 373, 379, 383, 389, 397, 401, 409,
            419, 421, 431, 433, 439, 443, 449, 457, 461, 463, 467, 479, 487, 491, 499, 503, 509, 521, 523, 541,
            547, 557, 563, 569, 571, 577, 587, 593, 599, 601, 607, 613, 617, 619, 631, 641, 643, 647, 653, 659,
            661, 673, 677, 683, 691, 701, 709, 719, 727, 733, 739, 743, 751, 757, 761, 769, 773, 787, 797, 809,
            811, 821, 823, 827, 829, 839, 853, 857, 859, 863, 877, 881, 883, 887, 907, 911, 919, 929, 937, 941,
            947, 953, 967, 971, 977, 983, 991, 997};

    // Calcul des bases arithmétiques
    public static int Valeur(char Lettre)
    {
        return (CHIFFRE + LETTRE).indexOf( Lettre );
    }
    public static char Valeur(int Chiffre)
    {
        return (CHIFFRE + LETTRE).charAt( Chiffre );
    }
    public static String versIndice(int Entier)
    {
        String x = Entier + "";
        for (int i = 0; i < 10; i++)
            x = x.replace(i+"", CHIFFRE_INDICE.charAt(i)+"");
        return x;
    }
    public static String versExposant(int Entier)
    {
        String x = Entier + "";
        for (int i = 0; i < 10; i++)
            x = x.replace(i+"", CHIFFRE_EXPOSANT.charAt(i)+"");
        return x;
    }
    public static String FacteursPremiers(int nombre)
    {
        int x = nombre;
        String y = "";
        for (int i = 0; i<NOMBRE_PREMIER.length && x>1; i++)
        {
            int j = 0;
            while (x%NOMBRE_PREMIER[i] == 0)
            {
                x = x/NOMBRE_PREMIER[i];
                j++;
            }
            if (j>0) y = y + NOMBRE_PREMIER[i] + versExposant(j) ;
        }
        if (!y.isEmpty()) return y;
        else return String.valueOf(nombre);
    }
    public static String Valeur(String Nombre_entier, int Base_initiale, int Base_finale)
    {
        String x ;
        try
        {
            x = Calcul(Nombre_entier, Base_initiale, Base_finale) ;
            return x ;
        }
        catch(Exception e)
        {
            return String.valueOf(Double.NaN) ;
        }
    }
    private static String Calcul(String Nombre_entier, int Base_initiale, int Base_finale)
    {
        String x = Nombre_entier.replace(" ", "").toUpperCase() ;
        if (2 <= Base_initiale && Base_initiale <= 36
                && 2 <= Base_finale && Base_finale <= 36)
        {
            for (int n = 0; n < x.length(); n++)
                if ( Valeur( x.charAt(n) ) >= Base_initiale || Valeur( x.charAt(n) ) == -1 )
                    return String.valueOf(Double.NaN) ;
            x = Conversion(Conversion(x, Base_initiale), Base_finale) ;
            return x ;
        }
        return String.valueOf(Double.NaN) ;
    }
    private static int Conversion(String Nombre_allogène, int Base_initiale)
    {
        String x = Nombre_allogène ;
        double y = 0 ;
        for (int n = 0 ; n < x.length() ; n++)
        {
            y = y + Valeur(x.charAt(n)) * Math.pow(Base_initiale , x.length()-1-n) ;
        }
        return (int) y ;
    }
    private static String Conversion(int Nombre_en_base_10, int Base_finale)
    {
        int x = Nombre_en_base_10 ;
        char i ; int r ;
        String y = "" ;
        while ( x >= Base_finale )
        {
            r = x % Base_finale ;
            i = Valeur(r) ;
            y = i + y ;
            x = (int) (x / Base_finale) ;
        }
        r = x % Base_finale ;
        i = Valeur(r) ;
        y = i + y ;
        return y ;
    }

    // Retourne une fraction irréductible équivalente à la fraction donnée
    public static int[] Fraction(int Numérateur, int Dénominateur)
    {
        int n = PGCD(Numérateur, Dénominateur) ;
        return new int[] {(Numérateur/n), (Dénominateur/n)} ;
    }
    public static int[] Fraction(int[] Elements)
    {
        int n = PGCD(Elements) ;
        int[] x = new int[Elements.length] ;
        for (int i=0; i<x.length; i++)
            x[i] = Elements[i]/n ;
        return x;
    }
    public static int[] Fraction(double[] Elements, float ordre)
    {
        double[] x = Elements ;
        for (int i=0; i<x.length; i++)
            if ( Math.round(x[i]) != x[i] )
            {
                int[] z = Fraction(x[i], ordre); if (z==null) return null;
                x = Algèbre.Produit(z[1], x); x[i] = z[0];
            }
        double n = PGCD(x) ;
        int[] y = new int[Elements.length] ;
        for (int i=0; i<y.length; i++)
            y[i] = (int)(x[i]/n) ;
        return y;
    }
    public static int[] Fraction(double Nombre_décimal, float ordre)
    {
        int[] f = null ;
        String x = String.valueOf(Nombre_décimal) ;
        try
        {
            int n = x.indexOf('.') ;
            int m = x.indexOf('E') ;
            if (n != -1 && m == -1) f = Fraction(Nombre_décimal, ordre, x.substring(n + 1).length()) ;
            if (Math.abs((double)f[0]/(double)f[1] - Nombre_décimal) <= ordre) return f;
            else return null;
        }
        catch (Exception e)
        {
            return null;
        }
    }
    private static int[] Fraction(double Nombre_décimal, float ordre, int Longueur_décimale)
    {
        String x = String.valueOf(Nombre_décimal) ;
        if ((int) Nombre_décimal == Nombre_décimal) return new int[] {(int) Nombre_décimal, 1} ;
        if (Longueur_décimale < 10)
        {
            double k = Math.pow(10, Longueur_décimale) ;
            return Fraction( (int)( Nombre_décimal * k ) , (int) k ) ;
        }
        for (int i = 1; i < 10; i++)
        {
            String y = String.valueOf(i) ;
            y = y + y + y + y ;
            if (x.indexOf(y) != -1)
            {
                int[] f = Fraction(Math.round(9 * Nombre_décimal), ordre) ;
                return Fraction(f[0], f[1] * 9) ;
            }
        }
        return null;
    }
    public static int PGCD(int[] nombres)
    {
        int x = nombres[0];
        for (int n: nombres)
            x = PGCD(x, n);
        return x;
    }
    public static int PGCD(double[] nombres)
    {
        int x = (int)nombres[0];
        for (double n: nombres)
            x = PGCD(x, (int)n);
        return x;
    }
    public static int PGCD(int nombre1, int nombre2)
    {
        if (nombre1==0) return Math.abs(nombre2) ;
        else if (nombre2==0) return Math.abs(nombre1) ;
        int x = Math.max(Math.abs(nombre1), Math.abs(nombre2)), y = Math.min(Math.abs(nombre1), Math.abs(nombre2)) ;
        int r = x%y ;
        if (r==0) return y ;
        else return PGCD(y, r) ;
    }
    public static int PPCM(int nombre1, int nombre2)
    {
        return nombre1 * nombre2 / PGCD(nombre1, nombre2) ;
    }

    // Retourne un nombre aléatoire compris dans un interval donné
    public static double Aléatoire(double Minimum, double Maximum)
    {
        return (Maximum - Minimum) * Math.random() + Minimum ;
    }
    // Retourne la factorielle d'un entier naturel
    public static double Factorielle(int Nombre)
    {
        double f = 1;
        for (int n = 1 ; n <= Nombre ; n++)
            f = f * n;
        return f ;
    }
    // Retourne la primorielle d'un entier naturel
    public static double Primorielle(int Nombre)
    {
        double p = 0;
        for (int n = 1 ; n <= Nombre ; n++)
            p = p + n;
        return p ;
    }

    // Retourne la forme arrondi d'un nombre réel
    public static double Arrondi(double nombre, int ordre)
    {
        String x = String.valueOf(nombre) ;
        int n = x.indexOf('.') ;
        int m = x.indexOf('E') ;
        if (ordre >= 0 && ordre < 10 && n != -1)
        {
            String entier = x.substring(0, n) ;
            if (m != -1)
            {
                String décimal = x.substring(n + 1, m) ;
                String exposant = x.substring(m + 1) ;
                if (décimal.length() > ordre) décimal = décimal.substring(0, ordre) ;
                x = entier + '.' + décimal + 'E' + exposant ;
            }
            else
            {
                String décimal = x.substring(n + 1) ;
                if (décimal.length() > ordre) décimal = décimal.substring(0, ordre) ;
                x = entier + '.' + décimal ;
            }
        }
        return Double.valueOf(x) ;
    }

    // Retourne la valeur numérique d'un chiffre romain ou d'un nombre romain
    private static int Valeur_romaine(char Caractère)
    {
        switch
                (CHIFFRE_ROMAIN.indexOf(Caractère))
        {
            default : return 0 ;
            case 0 : return 1 ;
            case 1 : return 5 ;
            case 2 : return 10 ;
            case 3 : return 50 ;
            case 4 : return 100 ;
            case 5 : return 500 ;
            case 6 : return 1000 ;
        }
    }
    public static int Valeur_romaine(String Nombre_romain)
    {
        if (Nombre_romain.length() == 1)
        { return Valeur_romaine(Nombre_romain.charAt(0)) ; }
        int y ;
        for (int x = CHIFFRE_ROMAIN.length() - 1 ; x >= 0 ; x--)
        {
            y = Nombre_romain.indexOf(CHIFFRE_ROMAIN.charAt(x)) ;
            if (y != -1)
                return Valeur_romaine(Nombre_romain.charAt(y))
                        + Valeur_romaine(Nombre_romain.substring(y + 1))
                        - Valeur_romaine(Nombre_romain.substring(0, y)) ;
        }
        return 0 ;
    }

    // Retourner le(s) solution(s) d'un polynôme du premier ou du second degrès
    public static double  Solution(double a, double b)
    {
        double x = - b / a ;
        return x ;
    }
    public static double[]  Solution(double a, double b, double c)
    {
        double d = b * b - 4 * a * c ;
        double[] x ;
        if (d == 0)
        {
            x = new double[1] ;
            x[0] = - b / (2 * a) ;
        }
        else
        {
            if (d > 0)
            {
                x = new double[2];
                x[0] = (- b + Math.sqrt(d))/ (2 * a) ;
                x[1] = (- b - Math.sqrt(d))/ (2 * a) ;
            }
            else x = new double[0] ;
        }
        return x ;
    }

    public static double MesurePrincipale(double Angle)
    {
        int signe = 1 ;
        if (Angle < 0) signe = -1 ;
        Angle = Math.abs(Angle) ;
        while (Angle > Math.PI)
        {
            Angle = Angle - 2 * Math.PI ;
        }
        return signe * Angle ;
    }

    public static class Logique
    {
        // Indique si un nombre entier est divisible par un autre
        public static boolean estDivisible(int Divident, int Diviseur)
        {
            return (Divident % Diviseur == 0) ;
        }

        // Indique si une expression constitue un nombre
        public static boolean estNombre(String Expression)
        {
            try
            {
                double x = Double.valueOf(Expression) ;
                return Double.isFinite(x) ;
            }
            catch(Exception e)
            {
                return false ;
            }
        }

        // Indique si un entier est premier ou si deux entiers sont premiers entre eux
        public static boolean estPremier(int Nombre_entier)
        {
            for (int n = 2 ; n <= Math.sqrt(Nombre_entier) ; n++)
            {
                if (estDivisible(Nombre_entier, n)) return false ;
            }
            return true ;
        }
        public static boolean estPremier(int Premier_nombre, int Second_nombre)
        {
            return (PGCD(Premier_nombre, Second_nombre) == 1) ;
        }
    }
}
