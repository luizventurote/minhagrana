package luizventurote.minhagrana;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Date;

import java.util.Calendar;

import luizventurote.minhagrana.controller.MainController;
import luizventurote.minhagrana.helper.Helper;

public class MovimentacaoFinanceiraActivity extends AppCompatActivity{


    private int dia, mes, ano;
    private Button dataGasto;
    private EditText valor;
    private EditText descricao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movimentacao_financeira);

        //Variaveis do calendário
        Calendar calendario = Calendar.getInstance();
        dia = calendario.get(Calendar.DAY_OF_MONTH);
        mes = calendario.get(Calendar.MONTH);
        ano = calendario.get(Calendar.YEAR);

        dataGasto = (Button) findViewById(R.id.data);

        //Montagem do spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.categoria_gasto, android.R.layout.simple_spinner_item);
        Spinner categoria = (Spinner) findViewById(R.id.categoria);
        categoria.setAdapter(adapter);

    }

    public void selecionarData(View v){
        showDialog(v.getId());
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if(R.id.data == id){
            return new DatePickerDialog(this, listener, ano, mes, dia);
        }
        return null;
    }
    private DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            ano = year;
            mes = monthOfYear;
            dia = dayOfMonth;
            dataGasto.setText(dia + "/" + (mes + 1) + "/" + ano);
        }
    };

    public void inserirMovimentacaoFinanceira(View v){

        Toast.makeText(this, "Gasto registrado", Toast.LENGTH_SHORT).show();

        Date data;
        Double valorD;
        String d;
        Long retornoId;

        d = (dia + "/" + (mes+1) + "/" + ano);

        data = Helper.formatStringToDate(d);
        this.descricao = (EditText) findViewById(R.id.descricao);
        this.valor = (EditText) findViewById(R.id.valor);
        valorD = Double.parseDouble(this.valor.getText().toString().trim());


        retornoId =  MainController.inserirMovimentacaoFinanceira(this, this.descricao.getText().toString(), valorD, data);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
      //  getMenuInflater().inflate(R.menu.menu_gasto, menu);
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
