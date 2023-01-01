package com.major.nectar;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.*;

import io.github.kexanie.library.MathView;
import com.major.science.Algèbre;
import com.major.science.Mathématique;
import com.major.science.physique.Chimie;
import com.major.science.physique.Molécule;
import com.major.science.physique.Réaction;

public class MainActivity extends Activity {

    TextView atomText;
    ImageView imageView;
    EditText editText;
    Spinner spinner;
    MathView textView;
    public static String[] atom_list, element_list, molar_list, all_list ;
    String Erreur;
    AlertDialog.Builder helpBox;
    int atomTextColor;
    public Handler handler = new Handler();

    @Override
    protected void onPause() {
        super.onPause();
        atomText.setTextColor(atomTextColor);
        imageView.setImageResource(R.drawable.ic_vertical_black);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        helpBox = new AlertDialog.Builder(this)
                .setTitle(R.string.dialog_title)
                .setMessage(R.string.message)
                .setPositiveButton(R.string.positive,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dlg, int sumthin)
                            {
                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("mailto:hassenmajor@gmail.com")));
                            } } )
                .setNeutralButton(R.string.neutral,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dlg, int sumthin)
                            {
                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.google))));
                                // startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://pages.flycricket.io/nectar/privacy.html")));
                            } } )
                .setNegativeButton(R.string.negative,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dlg, int sumthin)
                            {

                            } } );

        textView = (MathView)findViewById(R.id.textView);
        textView.config(
                "MathJax.Hub.Config({\n"+
                        "  CommonHTML: { linebreaks: { automatic: true } },\n"+
                        "  \"HTML-CSS\": { linebreaks: { automatic: true } },\n"+
                        "         SVG: { linebreaks: { automatic: true } }\n"+
                        "});");
        editText = (EditText)findViewById(R.id.editText);
        spinner = (Spinner)findViewById(R.id.spinner);
        atomText = (TextView)findViewById(R.id.atomText);
        imageView = (ImageView)findViewById(R.id.imageView);
        atom_list = getResources().getStringArray(R.array.atom_list) ;
        element_list = getResources().getStringArray(R.array.element_list) ;
        molar_list = getResources().getStringArray(R.array.molar_list) ;

        all_list = new String[atom_list.length+1] ;
        all_list[0] = getString(R.string.list_title);
        for (int i=0; i<atom_list.length; i++)
        {
            all_list[1+i] = element_list[i] + " (" + molar_list[i]+ ") Z=" + (i+1);
        }
        ArrayAdapter<String> aa=new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item,
                all_list);
        aa.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
        spinner.setAdapter(aa);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {
                if (i>0)
                {
                    atomText.setText(Mathématique.versIndice(i) + atom_list[i-1]);
                }
                else atomText.setText("");
            }
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        atomTextColor = atomText.getCurrentTextColor();
        atomText.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction()==MotionEvent.ACTION_DOWN)
                    atomText.setTextColor(atomText.getCurrentHintTextColor());
                else if (event.getAction()==MotionEvent.ACTION_UP)
                {
                    int i = spinner.getSelectedItemPosition();
                    if (i>0)
                    {
                        int s = editText.getSelectionStart();
                        int e = editText.getSelectionEnd();
                        int M = Math.max(s, e);
                        int m = Math.min(s, e);
                        String x = editText.getEditableText().replace(m, M, atom_list[i-1], 0, atom_list[i-1].length()).toString();
                        editText.setText(x);
                        editText.setSelection(m+atom_list[i-1].length());
                    }
                    atomText.setTextColor(atomTextColor);
                }
                return true;
            }
        });

        imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction()==MotionEvent.ACTION_DOWN)
                    imageView.setImageResource(R.drawable.ic_vertical_white);
                else if (event.getAction()==MotionEvent.ACTION_UP)
                {
                    imageView.setImageResource(R.drawable.ic_vertical_black);
                    try
                    {
                        YES();
                    }
                    catch (Exception e)
                    {
                        NO(e);
                    }
                }
                return true;
            }
        });

        editText.setOnKeyListener(new View.OnKeyListener() { public boolean onKey(View arg0, int arg1, KeyEvent arg2) {
            if (arg2.getAction()==KeyEvent.ACTION_UP && arg1==KeyEvent.KEYCODE_ENTER)
            {
                try
                {
                    YES();
                }
                catch (Exception e)
                {
                    NO(e);
                }
                return true;
            }
            return false;
        } } ) ;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.action_help)
        {
            helpBox.show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private String give(double x)
    {
        String y ;
        int[] z = Mathématique.Fraction(x, (float) Math.pow(10, -10)) ;
        if (z!=null && z[1]!=1)
            y = "\\color{blue}{\\mathbf{\\dfrac{"+z[0]+"}{"+z[1]+"}}}" ;
        else
            if ((int) x == x)
                y = "\\color{blue}{\\mathbf{ " + (int)x + " }}" ;
            else
                y = "\\color{blue}{\\mathbf{ " + x + " }}" ;
        y = y + "~";
        return y ;
    }
    private void YES()
    {
        Erreur = ""; // notification d'erreur
        String x = editText.getText().toString();
        if (x.indexOf("=")>0)
        {
            OK();
        }
        else
        {
            //<Test>
            //Molécule molecule = new Molécule(x);
            //Toast.makeText(this, molecule.getFormuleBrute()+" "+molecule.getMasse()+" "+molecule.getAtomes().length+" "+molecule.getEffectifTotal(), Toast.LENGTH_LONG).show();
            //<Test>
            double y = Mathématique.Arrondi(Chimie.getMasseMolaire(x), 2);
            x = mathViewMolécule(x.toCharArray());
            if (Double.isFinite(y) && x!=null)
            {
                x = "$$\\Large{ M( " + x + " ) = \\color{blue}{\\mathbf{" + y + "}}~ g \\cdot mol^{-1} }$$";
                //textView.setVisibility(View.INVISIBLE);
                textView.setText(x);
                /*handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        textView.setVisibility(View.VISIBLE);
                    }
                }, 200);*/
            }
            else NO(null);
        }
    }
    private void NO(Exception e)
    {
        textView.setText("$$\\Large{}\\color{red}{\\textbf{?}}$$");
        Toast.makeText(MainActivity.this, R.string.error_text, Toast.LENGTH_SHORT).show();
        //if (e!=null) Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
    }
    private void OK()
    {
        String x = editText.getText().toString(); // "C2H6+O2 = CO2+H2O"
        x = x.replace('\u2192', '=');
        x = x.replace('\u27F6', '=');
        String x1, x2;
        x1 = Algèbre.Tableau(x, "=")[0];
        x2 = Algèbre.Tableau(x, "=")[1];
        String[] z1 = Algèbre.Tableau(x1, "+");
        String[] z2 = Algèbre.Tableau(x2, "+");
        Molécule[] y1 = Molécule.valueOf(z1);
        Molécule[] y2 = Molécule.valueOf(z2);
        for (int i=0; i<y1.length; i++)
        for (int k=i+1; k<y1.length; k++)
            if (y1[i].equals(y1[k]))
            {
                Molécule[] yprim = new Molécule[y1.length-1];
                System.arraycopy(y1, 0, yprim, 0, k);
                System.arraycopy(y1, k+1, yprim, k, yprim.length-k);
                y1 = yprim;
                String[] zprim = new String[z1.length-1];
                System.arraycopy(z1, 0, zprim, 0, k);
                System.arraycopy(z1, k+1, zprim, k, zprim.length-k);
                z1 = zprim;
            }
        for (int i=0; i<y2.length; i++)
        for (int k=i+1; k<y2.length; k++)
            if (y2[i].equals(y2[k]))
            {
                Molécule[] yprim = new Molécule[y2.length-1];
                System.arraycopy(y2, 0, yprim, 0, k);
                System.arraycopy(y2, k+1, yprim, k, yprim.length-k);
                y2 = yprim;
                String[] zprim = new String[z2.length-1];
                System.arraycopy(z2, 0, zprim, 0, k);
                System.arraycopy(z2, k+1, zprim, k, zprim.length-k);
                z2 = zprim;
            }
        double[] Y = new Réaction(y1, y2).getCoéfficients();
        int[] y = Mathématique.Fraction(Y, (float)1E-10);
        if (Chimie.estCoéfficients(new Réaction(y1, y2), y))
        {
            String z = "";
            for (int i=0; i<y1.length; i++)
                if (i==0) z = z + " " + give(y[0]) + " " + mathViewMolécule(z1[0].toCharArray()) ;
                else z = z + " + " + give(y[i]) + " " + mathViewMolécule(z1[i].toCharArray()) ;
            for (int i=0; i<y2.length; i++)
                if (i==0) z = z + " \\to " + give(y[y1.length]) + " " + mathViewMolécule(z2[0].toCharArray()) ;
                else z = z + " + " + give(y[y1.length+i]) + " " + mathViewMolécule(z2[i].toCharArray()) ;
            z = z.replaceAll(" 1 ", " ") + "\n\n";
            if (Y[0]!=y[0])
            {
                z = z + " \\\\ \\\\ ";
                for (int i=0; i<y1.length; i++)
                    if (i==0) z = z + " " + give(Y[0]) + " " + mathViewMolécule(z1[0].toCharArray()) ;
                    else z = z + " + " + give(Y[i]) + " " + mathViewMolécule(z1[i].toCharArray()) ;
                for (int i=0; i<y2.length; i++)
                    if (i==0) z = z + " \\to " + give(Y[y1.length]) + " " + mathViewMolécule(z2[0].toCharArray()) ;
                    else z = z + " + " + give(Y[y1.length+i]) + " " + mathViewMolécule(z2[i].toCharArray()) ;
            }
            z = "$$\\Large{} \\begin{matrix} " + z + " \\end{matrix}$$";
            textView.setText(z);
        }
        else NO(null);
    }

    public static String mathViewMolécule(char[] Molécule)
    {
        String x = new String(Molécule).replace(" ", "");
        if (!Chimie.estFormuleBrute(x)) return null;
        if (Mathématique.CHIFFRE.indexOf(x.charAt(x.length()-1))!=-1)
            x = x + "}";
        for (char n: Mathématique.CHIFFRE.toCharArray())
        {
            for (char m: (Mathématique.LETTRE+Mathématique.LETTRE.toLowerCase()).toCharArray())
            {
                x = x.replace(m+""+n, m+"_{"+n);
                x = x.replace(n+""+m, n+"}"+m);
            }
            x = x.replace(")"+n, ")_{"+n);
            x = x.replace(n+")", n+"})");
            x = x.replace(n+"(", n+"}(");
        }
        int z = 0;
        for (char n: x.toCharArray())
            if (n=='{') z++;
            else if (n=='}') z--;
        if (z!=0) return null;
        return x;
    }

}
