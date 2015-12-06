package luizventurote.minhagrana;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import java.util.Calendar;
import java.util.Date;

import luizventurote.minhagrana.controller.MainController;
import luizventurote.minhagrana.helper.Helper;
import luizventurote.minhagrana.model.MovimentacaoFinanceira;

public class MovimentacaoFinanceiraActivity extends AppCompatActivity {

    private int dia, mes, ano;
    private Button dataGasto;
    private EditText valor;
    private Toast mToast;

    /**
     * Context
     */
    private Context context = this;

    /**
     * Valor do campo de descrição
     */
    private EditText descricao;

    /**
     * Variável de controle de edição
     */
    private boolean fun_editar = false;

    /**
     * Objeto de movimentação financeira
     */
    private MovimentacaoFinanceira mov = null;

    /**
     * Variável que controla a inserção de crédito ou gasto
     */
    private boolean credito = false;

    /**
     * Id do objeto de edição
     */
    private Long obj_id;


    /**
     * Toolbar
     */
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movimentacao_financeira);

        // Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_mov_fin);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        startToolbar();

        // Verifica edição
        verificarEdicao(this);

        // Verifica inserção de crédito
        verificarInsercaoCredito();

        if( !fun_editar ) {

            //Variaveis do calendário
            Calendar calendario = Calendar.getInstance();
            dia = calendario.get(Calendar.DAY_OF_MONTH);
            mes = calendario.get(Calendar.MONTH);
            ano = calendario.get(Calendar.YEAR);

            // Data padrão
            EditText act_text_data = (EditText) findViewById(R.id.data_text);
            act_text_data.setText( dia + "/" + (mes + 1) + "/" + ano );

            // Valor padrão
            EditText act_text_valor = (EditText) findViewById(R.id.valor);
            act_text_valor.setText( "0" );
        }
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

            EditText act_text_valor = (EditText) findViewById(R.id.data_text);
            act_text_valor.setText( dia + "/" + (mes + 1) + "/" + ano );

        }
    };

    public void inserirMovimentacaoFinanceira(View v){

        Date data;
        Double valorD;
        String d;
        Long retornoId;

        d = (ano + "-" + (mes+1) + "-" + dia +" 00:00:00"); //yyyy-MM-dd HH:mm:ss

        data = Helper.formatStringToDate(d);
        this.descricao = (EditText) findViewById(R.id.descricao);
        this.valor = (EditText) findViewById(R.id.valor);

        if(this.credito) {
            valorD = Double.parseDouble(this.valor.getText().toString().trim());
        } else {
            valorD = (-1) * Double.parseDouble(this.valor.getText().toString().trim());
        }

        if(fun_editar) {

            // Seta um novo valor para a descrição
            mov.setDescricao(this.descricao.toString());

            // Atualiza o objeto no banco
            MainController.atualizarMovimentacaoFinanceira(this, mov);

        } else {

            retornoId =  MainController.inserirMovimentacaoFinanceira(this, this.descricao.getText().toString(), valorD, data);

        }

        Toast.makeText(this, "Gasto registrado", Toast.LENGTH_SHORT).show();

        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_novo_gasto, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_delete_mov) {
            return true;
        }

        if(id == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Verifica a função de edição para alterar a activity
     */
    public void verificarEdicao(Context context) {

        // Verifica se o ID foi passado no intent para ativar a edição
        if( getIntent().hasExtra("id") ) {

            Bundle extras = getIntent().getExtras();

            // Id do objeto
            this.obj_id = Long.parseLong( Integer.toString(extras.getInt("id")) );

            // Carrega objeto
            mov = MainController.buscarMovimentacaoFinanceira(context, this.obj_id);

            // Habilita a edição
            fun_editar = true;
        }

        // Ações para edição
        if(fun_editar) {

            //Toast.makeText(this, "Objeto carregado: " + mov.getDescricao(), Toast.LENGTH_SHORT).show();

            // Verifica se é crédito ou gasto
            if(mov.getValor() > 0) {
                credito =  true;
            }

            // Altera as interfaces
            if(credito) {

                // Altera título da toolbar
                toolbar.setTitle(R.string.editar_credito);
                setSupportActionBar(toolbar);

                // Seta o valor
                EditText act_text_valor = (EditText) findViewById(R.id.valor);
                act_text_valor.setText(mov.getValor().toString());

            } else {

                // Altera título da toolbar
                toolbar.setTitle(R.string.editar_gasto);
                setSupportActionBar(toolbar);

                // Seta o valor
                EditText act_text_valor = (EditText) findViewById(R.id.valor);
                Double valor = (-1) * mov.getValor();
                act_text_valor.setText(valor.toString());
            }

            // Seta a descrição
            EditText act_text_desc = (EditText) findViewById(R.id.descricao);
            act_text_desc.setText(mov.getDescricao());

            // Seta a data
            EditText act_text_data = (EditText) findViewById(R.id.data_text);
            act_text_data.setText( Helper.formatDateToStringWithSlash( mov.getData() ) );

            ano = Helper.getDay( mov.getData() );
            mes = Helper.getMonth( mov.getData() );
            dia = Helper.getYear( mov.getData() );

            // Altera o texto do botão
            Button act_text_btn = (Button) findViewById(R.id.inserirGasto);
            act_text_btn.setText(R.string.salvar);

            // Altera o onClick do botão
            act_text_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // Nova descrição
                    EditText act_text_desc = (EditText) findViewById(R.id.descricao);
                    mov.setDescricao(act_text_desc.getText().toString());

                    // Novo valor
                    EditText act_text_valor = (EditText) findViewById(R.id.valor);
                    Double valor = Double.parseDouble(act_text_valor.getText().toString());

                    if(!credito) {
                        valor = valor * (-1);
                    }

                    mov.setValor(valor);

                    // Nova data
                    EditText act_text_data_text = (EditText) findViewById(R.id.data_text);
                    mov.setData(Helper.formatStringToDateWithSlash(act_text_data_text.getText().toString()));

                    // Atualiza o objeto
                    MainController.atualizarMovimentacaoFinanceira(v.getContext(), mov);

                    finish();
                }
            });
        }
    }

    /**
     * Inicia a toolbar
     */
    private void startToolbar() {

        toolbar = (Toolbar) findViewById(R.id.toolbar_mov_fin);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void verificarInsercaoCredito() {


        if( getIntent().hasExtra("credito") ) {

            Bundle extras = getIntent().getExtras();

            this.credito = extras.getBoolean("credito");

            if(this.credito) {

                // Altera título da toolbar
                toolbar.setTitle(R.string.editar_credito);
                setSupportActionBar(toolbar);
            }
        }
    }

    /**
     * Deleta uma movimentação
     */
    public void deletarMovimentacao(MenuItem item) {

        new MaterialDialog.Builder(this)
                .content("Você tem certeza que deseja deletar esse registro?")
                .positiveText("Confirmar")
                .negativeText("Cancelar")
                .onAny(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                        if (which.name().equals("POSITIVE")) {

                            MainController.deletarMovimentacaoFinanceira(context, mov);

                            showToast("Registro deletado!");

                            finish();

                        }

                        //showToast(which.name() + "!");
                    }
                })
                .show();
    }

    private void showToast(String message) {
        if (mToast != null) {
            mToast.cancel();
            mToast = null;
        }
        mToast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        mToast.show();
    }
}
