package com.major.science;

public class Algèbre
{
    // Retourner le mot qui symbolise la variable
    private static char Inconnue ;
    public static char Inconnue() { return Inconnue ; }

    public static double[] Valeur(String[] Système_linéaire, char[] Inconnues)
    {
        double[][] v = new double[Système_linéaire.length][1];
        double[][] m = new double[Système_linéaire.length][Inconnues.length];
        if (Système_linéaire.length==Inconnues.length)
        {
            String x;
            for (int i=0; i<Système_linéaire.length; i++)
            {
                x = Système_linéaire[i];
                for (int j=0; j<Inconnues.length; j++)
                    x = x.replace(Inconnues[j]+"", "(0)");
                v[i][0] = -Arithmétique.Valeur(x);

                for (int j=0; j<Inconnues.length; j++)
                {
                    x = Système_linéaire[i];
                    for (int k=0; k<Inconnues.length; k++)
                        if (k==j) x = x.replace(Inconnues[k]+"", "(1)");
                        else x = x.replace(Inconnues[k]+"", "(0)");
                    m[i][j] = Arithmétique.Valeur(x)+v[i][0];
                }
            }
            if (Algèbre.Déterminant(m)!=0)
            {
                double[][] r = Algèbre.Produit(Algèbre.Inverse(m), v);
                double[] f = new double[r.length];
                for (int i=0; i<r.length; i++)
                    f[i] = r[i][0];
                return f;
            }
        }
        return null;
    }
    // Calculer la solution d'une équation numérique
    public static double[] Valeur(String Expression, double Minimum, double Maximum, float ordre, boolean Tout)
    {
        double x = Valeur(Expression, Minimum, Maximum, ordre) ;
        double[] t = new double[0];
        if (Minimum == Maximum)
            t = Valeur(Expression, -50, 50, ordre, Tout);
        else if (Minimum < Maximum && Double.isFinite(x))
        {
            t = Liste(x) ;
            if (Tout)
            {
                t = Liste(t, Valeur(Expression, x+10*ordre, Maximum, ordre, true)) ;
                t = Liste(Valeur(Expression, Minimum, x-10*ordre, ordre, true), t) ;
            }
        }
        return t ;
    }
    public static double Valeur(String Expression, double Minimum, double Maximum)
    {
        return Valeur(Expression, Minimum, Maximum, (float) 1E-10) ;
    }
    public static double Valeur(String Expression, double Minimum, double Maximum, float ordre)
    {
        double x;
        try
        {
            x = Calcul(Expression, Minimum, Maximum, ordre) ;
            return x ;
        }
        catch(Exception e)
        {
            return Double.NaN ;
        }
    }

    // Calculer le symbole de l'inconnue recherchée
    private static double Calcul(String Expression, double Minimum, double Maximum, float ordre)
    {
        String e = Expression ;
        double y ;
        Inconnue = 'X' ;
        for (int n = 0; n < e.length(); n++)
        {
            char x = e.charAt(n) ;
            if (Mathématique.LETTRE.indexOf(x) != -1 || Mathématique.LETTRE_GREQUE.indexOf(x) != -1)
            {
                Inconnue = x;
                if (Maximum == Minimum)
                {
                    y = Encadrement(e, 0, 10, ordre) ; if (Double.isFinite(y)) return y ;
                    y = Encadrement(e, 0, -10, ordre) ; if (Double.isFinite(y)) return y ;

                    y = Encadrement(e, 10, 110, ordre) ; if (Double.isFinite(y)) return y ;
                    y = Encadrement(e, -10, -110, ordre) ; if (Double.isFinite(y)) return y ;

                    y = Encadrement(e, 110, 1110, ordre) ; if (Double.isFinite(y)) return y ;
                    y = Encadrement(e, -110, -1110, ordre) ; if (Double.isFinite(y)) return y ;
                }
                else
                    return Encadrement(e, Minimum, Maximum, ordre);
            }
        }
        return Double.NaN ;
    }

    // Calculer l'image d'un nombre par la fonction définie par l'expression donnée
    private static double Image(String Expression, double Antécedent)
    {
        return Arithmétique.Valeur( Expression.replace(Character.toString(Inconnue), "(" + String.valueOf(Antécedent) + ")") ) ;
    }

    // Rechercher la solution dans l'interval donné
    private static double Encadrement(String Expression, double Minimum, double Maximum, float ordre)
    {
        String e = Expression ;
        double m, M, x, z ;
        m = Image(e, Minimum) ; M = Image(e, Maximum) ;
        if (m * M <= 0) return Résolution(e, Minimum, Maximum, ordre) ;
        if (Double.isFinite(m))
        {
            if (Minimum < Maximum)
            {
                for (z = (Maximum - Minimum) / 10 ; z > (Maximum - Minimum) / 110 ; z = z / 10)
                    for (x = Minimum + z ; x < Maximum ; x = x + z)
                        if (Image(e, x) * m <= 0) return Résolution(e, Minimum, x, ordre) ;
            }
            else if (Maximum < Minimum)
            {
                for (z = (Minimum - Maximum) / 10 ; z > (Minimum - Maximum) / 110 ; z = z / 10)
                    for (x = Minimum - z ; x > Maximum ; x = x - z)
                        if (Image(e, x) * m <= 0) return Résolution(e, Minimum, x, ordre) ;
            }
        } else if (Double.isFinite(M)) return Encadrement(Expression, Maximum, Minimum, ordre);
        return Double.NaN ;
    }

    // Rechercher la solution d'une équation à expression continue
    private static double Résolution(String Expression, double Minimum, double Maximum, float ordre)
    {
        String e = Expression ;
        double x, y, m, M ;
        m = Image(e, Minimum) ; if (Math.abs(m) <= ordre) return Minimum ;
        M = Image(e, Maximum) ; if (Math.abs(M) <= ordre) return Maximum ;
        x = (Minimum + Maximum) / 2 ; y = Image(e, x) ;
        if ( m * M <= 0 && Math.abs(y) < 1E10)
        {
            if (Math.abs(Image(e, Math.round(x))) <= ordre) return Math.round(x) ;
            int n, virgule = String.valueOf(x).indexOf('.') ;
            if (virgule != -1)
            {
                n = String.valueOf(x).lastIndexOf("000") ;
                if (n != -1 && Math.abs( Image(e, Mathématique.Arrondi(x, n-virgule)) ) <= ordre)
                    return Mathématique.Arrondi(x, n-virgule) ;
                n = String.valueOf(x).lastIndexOf("999") ;
                if (n != -1 && Math.abs( Image( e, x/Math.abs(x) * (
                        Mathématique.Arrondi(Math.abs(x) + Math.pow(10, virgule-n-2), n-virgule)
                ) ) ) <= ordre )
                    return x/Math.abs(x)*(Mathématique.Arrondi(Math.abs(x)+Math.pow(10, virgule-n-2), n-virgule)) ;
            }
            if (Math.abs(y) <= ordre) return x ;
            if (y * m <= 0) return Résolution(e, Minimum, x, ordre) ;
            else return Résolution(e, x, Maximum, ordre) ;
        }
        else return Double.NaN ;
    }

    // Calculer la somme ou le produit d'une suite de nombres
    public static double Somme(double... Nombres)
    {
        double s = 0;
        for (double i: Nombres)
            s = s + i ;
        return s;
    }
    public static double Produit(double... Nombres)
    {
        double p = 1;
        for (double i: Nombres)
            p = p * i ;
        return p;
    }

    public static String[] Tableau(String Texte, String Séparateur)
    {
        int x = Texte.indexOf(Séparateur);
        if (x==-1) return new String[] {Texte};
        else return Liste(Texte.substring(0, x), Tableau(Texte.substring(x+Séparateur.length()), Séparateur));
    }
    public static String[] Liste(String[] Liste1, String[] Liste2)
    {
        String[] x = new String[Liste1.length+Liste2.length] ;
        System.arraycopy(Liste1, 0, x, 0, Liste1.length);
        System.arraycopy(Liste2, 0, x, Liste1.length, Liste2.length);
        return x;
    }
    public static String[] Liste(String[] Liste, String Nouveau)
    {
        return Liste(Liste, new String[] {Nouveau} );
    }
    public static String[] Liste(String Nouveau, String[] Liste)
    {
        return Liste(new String[] {Nouveau}, Liste);
    }
    public static String[] Liste(String Element)
    {
        return new String[] {Element} ;
    }

    // Fusionner deux listes
    public static double[] Liste(double[] Liste1, double[] Liste2)
    {
        double[] x = new double[Liste1.length+Liste2.length] ;
        System.arraycopy(Liste1, 0, x, 0, Liste1.length);
        System.arraycopy(Liste2, 0, x, Liste1.length, Liste2.length);
        return x;
    }
    public static double[] Liste(double[] Liste, double Nouveau)
    {
        return Liste(Liste, new double[] {Nouveau} );
    }
    public static double[] Liste(double Nouveau, double[] Liste)
    {
        return Liste(new double[] {Nouveau}, Liste);
    }
    public static double[] Liste(double Element)
    {
        return new double[] {Element} ;
    }

    public static int[] Liste(int[] Liste1, int[] Liste2)
    {
        int[] x = new int[Liste1.length+Liste2.length] ;
        System.arraycopy(Liste1, 0, x, 0, Liste1.length);
        System.arraycopy(Liste2, 0, x, Liste1.length, Liste2.length);
        return x;
    }
    public static int[] Liste(int[] Liste, int Nouveau)
    {
        return Liste(Liste, new int[] {Nouveau} );
    }
    public static int[] Liste(int Nouveau, int[] Liste)
    {
        return Liste(new int[] {Nouveau}, Liste);
    }
    public static int[] Liste(int Element)
    {
        return new int[] {Element} ;
    }

    // Inverser une liste
    public static double[] Inverse(double[] Liste)
    {
        double[] x = new double[Liste.length] ;
        for (int i = 0; i < x.length; i++)
            x[i] = Liste[x.length-1-i] ;
        return x;
    }

    // Calculer la somme de deux matrices
    public static double[][] Somme(double[][] Matrice1, double[][] Matrice2)
    {
        double M[][] = new double[Matrice1.length][Matrice1[0].length] ;
        if (Matrice1.length == Matrice2.length
                && Matrice1[0].length == Matrice2[0].length)
        {
            for (int i = 0; i < M.length; i++)
                for (int j = 0; j < M[0].length; j++)
                    M[i][j] = Matrice1[i][j] + Matrice2[i][j];
            return M ;
        }
        else return null;
    }

    // Calculer le produit de deux matrices
    public static double[][] Produit(double[][] Matrice1, double[][] Matrice2)
    {
        double M[][] = new double[Matrice1.length][Matrice2[0].length] ;
        double x ;
        if (Matrice1[0].length == Matrice2.length)
        {
            for (int i = 0; i < M.length; i++)
                for (int j = 0; j < M[0].length; j++)
                {
                    x = 0;
                    for (int k=0; k < Matrice1[0].length; k++) x = x + Matrice1[i][k] * Matrice2[k][j] ;
                    M[i][j] = x ;
                }
            return M ;
        } else return null;
    }

    // Calculer le produit d'une matrice et d'un scalaire
    public static double[] Produit(double Nombre, double[] Matrice)
    {
        double M[] = new double[Matrice.length] ;
        for (int i = 0; i < M.length; i++)
        {
            M[i] = Nombre * Matrice[i] ;
        }
        return M ;
    }
    public static double[][] Produit(double Nombre, double[][] Matrice)
    {
        double M[][] = new double[Matrice.length][0] ;
        for (int i = 0; i < M.length; i++)
        {
            M[i] = new double[Matrice[i].length] ;
            for (int j = 0; j < M[i].length; j++)
                M[i][j] = Nombre * Matrice[i][j] ;
        }
        return M ;
    }

    // Calculer le déterminant d'une matrice carrée
    public static double Déterminant(double[][] Matrice_carrée)
    {
        double x = 0, M[][] = Matrice_carrée ;
        if (M.length == 1)
            if (M.length != M[0].length) return Double.NaN;
            else return M[0][0] ;
        else if (M.length == 2)
            if (M.length != M[0].length || M.length != M[1].length) return Double.NaN;
            else return M[0][0]*M[1][1] - M[1][0]*M[0][1];
        else for (int k = 0; k < M.length; k++)
            {
                if (M.length != M[k].length) return Double.NaN;
                double m[][] = new double[M.length-1][M[0].length-1] ;
                //
                for (int i = 0; i < m.length; i++)
                    for (int j = 0; j < m[0].length; j++)
                        if (i<k) m[i][j] = M[i][j+1] ;
                        else m[i][j] = M[i+1][j+1] ;
                //
                if (k%2==0) x = x + M[k][0]*Déterminant(m) ;
                else x = x - M[k][0]*Déterminant(m) ;
            }
        return x ;
    }
    public static double[][] Inverse(double[][] Matrice_carrée)
    {
        double[][] M = Matrice_carrée;
        double d = Déterminant(M);
        if (d != 0) return Produit(1/d, Transposée(Comatrice(M)));
        else return null;
    }
    public static double[][] Identité(int dimension)
    {
        if (dimension == 0)
            return new double[][] {{}};
        else if (dimension == 1)
            return new double[][] {{1}};
        else if (dimension > 1)
        {
            double[][] M = new double[dimension][dimension];
            for (int i = 0; i < M.length; i++)
                for (int j = 0; j < M[0].length; j++)
                    if (i==j) M[i][j] = 1;
                    else M[i][j] = 0;
            return M;
        }
        return null;
    }
    public static double[][] Comatrice(double[][] Matrice_carrée)
    {
        double[][] M = new double[Matrice_carrée.length][Matrice_carrée[0].length] ;
        if (M.length > 1)
        {
            for (int i = 0; i < M.length; i++)
            {
                if (Matrice_carrée.length != Matrice_carrée[i].length) return null;
                for (int j = 0; j < M[0].length; j++)
                    M[i][j] = Cofacteur(Matrice_carrée, i, j);
            }
            return M ;
        }
        else return Identité(1);
    }
    public static double Cofacteur(double[][] Matrice_carrée, int ligne, int colonne)
    {
        return Math.pow(-1, ligne+colonne) * Mineur(Matrice_carrée, ligne, colonne);
    }
    public static double Mineur(double[][] Matrice_carrée, int ligne, int colonne)
    {
        double[][] M = new double[Matrice_carrée.length-1][Matrice_carrée[0].length-1] ;
        for (int i = 0; i < M.length; i++)
            for (int j = 0; j < M[0].length; j++)
                if (i<ligne && j<colonne) M[i][j] = Matrice_carrée[i][j] ;
                else if (i<ligne && j>=colonne) M[i][j] = Matrice_carrée[i][j+1] ;
                else if (i>=ligne && j<colonne) M[i][j] = Matrice_carrée[i+1][j] ;
                else M[i][j] = Matrice_carrée[i+1][j+1] ;
        return Déterminant(M);
    }

    // Calculer la transposée d'une matrice carrée
    public static double[][] Transposée(double[][] Matrice)
    {
        double[][] M = new double[Matrice.length][0] ;
        if (M.length > 0)
        {
            for (int i = 0; i < M.length; i++)
            {
                M[i] = new double[Matrice[i].length] ;
                for (int j = 0; j < M[i].length; j++)
                    M[i][j] = Matrice[j][i];
            }
            return M ;
        }
        else return null;
    }
}
