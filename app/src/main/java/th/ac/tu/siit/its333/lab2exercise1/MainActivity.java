package th.ac.tu.siit.its333.lab2exercise1;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    // expr = the current string to be calculated
    StringBuffer expr;
    int storeVal = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        expr = new StringBuffer();
        updateExprDisplay();
    }

    public void updateExprDisplay() {
        TextView tvExpr = (TextView)findViewById(R.id.tvExpr);
        tvExpr.setText(expr.toString());
    }

    public void updateAnsDisplay(int answer)
    {
        TextView tvAns = (TextView)findViewById(R.id.tvAns);
        tvAns.setText(Integer.toString(answer));
    }

    public void recalculate() {
        //Calculate the expression and display the output

        //Split expr into numbers and operators
        //e.g. 123+45/3 --> ["123", "+", "45", "/", "3"]
        //reference: http://stackoverflow.com/questions/2206378/how-to-split-a-string-but-also-keep-the-delimiters
        String e = expr.toString();
        String[] tokens = e.split("((?<=\\+)|(?=\\+))|((?<=\\-)|(?=\\-))|((?<=\\*)|(?=\\*))|((?<=/)|(?=/))");

        int result = 0, left, right;
        String op = "";

        if(tokens.length > 2) {
            for (int i = 0; i < tokens.length; ) {

                if (i == 0) {
                    left = Integer.parseInt(tokens[i]);
                    op = tokens[i+1];
                    right = Integer.parseInt(tokens[i + 2]);

                    System.out.println(left + op + right);

                    if (op.equals("+")) {
                        result = left + right;
                    } else if (op.equals("-")) {
                        result = left - right;
                    } else if (op.equals("*")) {
                        result = left * right;
                    } else if (op.equals("/")) {
                        result = left / right;
                    }

                    i = 3;
                } else {
                    if ((i == tokens.length-1) && (tokens.length%2==0))
                    {
                        break;
                    }
                    else
                    {
                        op = tokens[i];
                        right = Integer.parseInt(tokens[i + 1]);

                        if (op.equals("+")) {
                            result = result + right;
                        } else if (op.equals("-")) {
                            result = result - right;
                        } else if (op.equals("*")) {
                            result = result * right;
                        } else if (op.equals("/")) {
                            result = result / right;
                        }
                    }

                    i += 2;
                }

                updateAnsDisplay(result);
            }

            System.out.println("result: " + result);


        }
        else
        {
            updateAnsDisplay(0);
        }
    }

    public void digitClicked(View v) {
        //d = the label of the digit button
        String d = ((TextView)v).getText().toString();
        //append the clicked digit to expr
        expr.append(d);
        //update tvExpr
        updateExprDisplay();
        //calculate the result if possible and update tvAns
        recalculate();
    }

    public void operatorClicked(View v) {
        //IF the last character in expr is not an operator(+, -, *, /) and expr is not "",
        //THEN append the clicked operator and updateExprDisplay,
        //ELSE do nothing

        if(!(expr.toString().equals("")) && !(isOperator(expr.charAt(expr.length()-1))))
        {
            //op = the label of the operator button
            String op = ((TextView)v).getText().toString();
            //append the clicked operator to expr
            expr.append(op);
            //update tvExpr
            updateExprDisplay();
        }
    }

    public boolean isOperator(char c)
    {
        if(c == '+' | c == '-' | c == '*' | c == '/')
        {
            return true;
        }

        return false;
    }

    public void equalClicked(View v)
    {
        TextView tvAns = (TextView)findViewById(R.id.tvAns);

        int answer = Integer.parseInt(tvAns.getText().toString());

        expr = new StringBuffer();
        expr.append(answer);
        updateExprDisplay();

        tvAns.setText("");
    }

    public void ACClicked(View v) {
        //Clear expr and updateExprDisplay
        expr = new StringBuffer();
        updateExprDisplay();
        updateAnsDisplay(0);
        //Display a toast that the value is cleared
        Toast t = Toast.makeText(this.getApplicationContext(),
                "All cleared", Toast.LENGTH_SHORT);
        t.show();
    }

    public void BSClicked(View v) {
        //Remove the last character from expr, and updateExprDisplay
        if (expr.length() > 0) {
            expr.deleteCharAt(expr.length()-1);
            updateExprDisplay();
            recalculate();
        }
    }

    public void MClicked(View v){
        int id = v.getId();
        TextView tvAns = (TextView)findViewById(R.id.tvAns);

        switch (id) {
            case R.id.madd:
                storeVal = storeVal + Integer.parseInt(tvAns.getText().toString());
                break;
            case R.id.msub:
                storeVal = storeVal - Integer.parseInt(tvAns.getText().toString());
                break;
            case R.id.mr:
                expr.append(storeVal);
                updateExprDisplay();
                break;
            case R.id.mc:
                storeVal = 0;
                break;
        }

        Toast t = Toast.makeText(this.getApplicationContext(),
                "Memory Value = "+storeVal, Toast.LENGTH_SHORT);
        t.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
