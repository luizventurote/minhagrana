package luizventurote.minhagrana;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import luizventurote.minhagrana.controller.MainController;
import luizventurote.minhagrana.helper.Helper;
import luizventurote.minhagrana.model.MovimentacaoFinanceira;

public class MainActivity extends AppCompatActivity {

    private Context context = this;
    private List<Map<String, Object>> gastos;
    private AccountHeader headerResult = null;
    private Drawer result = null;
    private boolean opened = false;
    static SQLiteDatabase database;
    private Toast mToast;
    private Menu menu;
    private Toolbar toolbar;


    /**
     * Mês selecionado pelo usuário para exibição de dados
     */
    private int mes_selecionado = -1;

    /**
     * Ano selecionado pelo usuário para exibição de dados
     */
    private int ano_selecionado = -1;

    /**
     * Floating action button
     */
    private FloatingActionsMenu menuMultipleActions;

    /**
     * ListView
     */
    private ListView mainListView;
    private ArrayAdapter<String> listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Seleciona o mês vigente
        getMesSelecionado();

        // Seleciona o ano vigente
        getAnoSelecionado();

        // Show Drawer
        this.showDrawer(savedInstanceState);

        // Show Float Action Button
        this.showFloatActionButton();

        // Show ListView
        this.showListView();

    }

    protected void onResume() {
        super.onResume();

        // Atualiza a list view
        this.showListView();
    }

    /**
     * Show drawer with toolbar
     *
     * @param savedInstanceState
     */
    private void showDrawer(Bundle savedInstanceState) {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Create the drawer
        result = new DrawerBuilder(this)
                .withRootView(R.id.drawer_container)
                .withToolbar(toolbar)
                .withActionBarDrawerToggleAnimated(true)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName(R.string.drawer_item_home).withIcon(FontAwesome.Icon.faw_money).withIdentifier(1),
                        new SecondaryDrawerItem().withName(R.string.drawer_item_report).withIcon(FontAwesome.Icon.faw_bar_chart).withIdentifier(3),
                        new SecondaryDrawerItem().withName(R.string.drawer_item_settings).withIcon(FontAwesome.Icon.faw_cog).withIdentifier(2)
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {

                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {

                        if (drawerItem != null) {

                            Intent intent = null;

                            if (drawerItem.getIdentifier() == 1) {

                            } else if (drawerItem.getIdentifier() == 2) {

                                // Abre activity de configurações
                                startActivity(new Intent(context, ConfigActivity.class));

                                finish();

                            }  else if (drawerItem.getIdentifier() == 3) {

                                // Abre activity de relatórios
                                Intent mIntent = new Intent(context, RelatorioActivity.class);
                                Bundle mBundle = new Bundle();
                                mBundle.putInt("year", ano_selecionado);
                                mIntent.putExtras(mBundle);
                                startActivity(mIntent);
                            }
                        }

                        return false;
                    }

                })
                .withSavedInstance(savedInstanceState)
                .build();
    }

    /**
     * Show Float Action Button
     */
    private void showFloatActionButton() {

        this.menuMultipleActions = (FloatingActionsMenu) findViewById(R.id.multiple_actions);

        findViewById(R.id.action_a).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(MainActivity.this, "Clicked pink Floating Action Button", Toast.LENGTH_SHORT).show();
                // ShowDialogAddValue();
                startActivity(new Intent(context, MovimentacaoFinanceiraActivity.class));
            }
        });

        findViewById(R.id.action_b).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                // Abre a activity de inserir crédito
                Intent mIntent = new Intent(context, MovimentacaoFinanceiraActivity.class);
                Bundle mBundle = new Bundle();
                mBundle.putBoolean("credito", true);
                mIntent.putExtras(mBundle);
                startActivity(mIntent);
            }
        });
    }

    /**
     * Exibe a ListView
     */
    private void showListView() {


        String[] de = {"descricao", "data", "valor"};
        int[] para = {R.id.descricao, R.id.data, R.id.valor};

        SimpleAdapter adapter = new SimpleAdapter(this, listarGastos(), R.layout.itens_da_lista_gasto, de, para);

        //Resgatando recurso ListView do XML
        ListView lv = (ListView) findViewById(R.id.listViewValues);

        lv.setAdapter(adapter);
        lv.setOnItemClickListener(chamaAtividades(this));

        //Exibir total gasto
        this.exibirTotalGasto();
    }

    public AdapterView.OnItemClickListener chamaAtividades(final Context context) {
        return (new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> av, View v, int position, long id) {

                Map<String, Object> map = gastos.get(position);

                // Abre a activity de edição
                Intent mIntent = new Intent(context, MovimentacaoFinanceiraActivity.class);
                Bundle mBundle = new Bundle();
                mBundle.putInt("id", Integer.parseInt(gastos.get(position).get("id").toString()));
                mIntent.putExtras(mBundle);
                startActivity(mIntent);

            }
        });
    }

    /**
     * Monta a lista de gastos para ser exibido no ListView
     *
     * @return List
     */
    private List<Map<String, Object>> listarGastos() {


        // Gastos
        this.gastos = new ArrayList<Map<String, Object>>();
        Map<String, Object> item = new HashMap<String, Object>();

        // Busca uma lista de movimentações financeiras
        List<MovimentacaoFinanceira> lista_gastos = MainController.buscarPorMes(this, ano_selecionado, mes_selecionado + 1);

        // Model de movimentação financeira
        MovimentacaoFinanceira mov = null;

        // Loop de gastos
        int j = 0;
        while (lista_gastos.size() > j) {

            // Load model
            mov = lista_gastos.get(j);

            item.put("id", mov.getId());
            item.put("descricao", mov.getDescricao());
            item.put("data", Integer.toString(Helper.getDay(mov.getData())));
            item.put("valor", Helper.formatCurrency(mov.getValor()));
            gastos.add(item);
            item = new HashMap<String, Object>();

            j++;


        }

        return gastos;
    }

    /**
     * Open Dialog to Add a value
     */
    private void ShowDialogAddValue() {

        new MaterialDialog.Builder(this)
                .title(R.string.dialog_input_text_add_title)
                .content(R.string.dialog_input_text_add)
                .theme(Theme.DARK)
                .inputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_TEXT)
                .input(R.string.quantia_hint, R.string.dialog_input_text_add_prefill, new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(MaterialDialog dialog, CharSequence input) {
                        // Do something
                    }
                }).show();

        new MaterialDialog.Builder(this)
                .title(R.string.dialog_input_text_add_title)
                .content(R.string.dialog_input_text_add)
                .theme(Theme.DARK)
                .inputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_TEXT)
                .input(R.string.novo_gasto_hint, R.string.dialog_input_text_add_prefill, new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(MaterialDialog dialog, CharSequence input) {
                        // Do something
                        listAdapter.add(input.toString());
                    }
                }).show();

    }

    /**
     * Infla o menu de ícones da toolbar
     *
     * @param menu
     * @return boolean
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        this.menu = menu;

        atualizarMes();

        return true;
    }

    /**
     * Abre um dialog para selecionar o mês selecionado
     *
     * @param item
     */
    public void showSelectMonthDialog(MenuItem item) {

        int month = getMesSelecionado();

        new MaterialDialog.Builder(this)
                .title(R.string.title_meses_dialog)
                .items(R.array.meses)
                .itemsCallbackSingleChoice(month, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {

                        showToast(which + ": " + text);

                        // Seta o mês selecionado
                        mes_selecionado = which;

                        // Atualiza o texto do item do menu
                        atualizarMes();

                        // Atualiza os dados do ListView
                        showListView();

                        /**
                         * If you use alwaysCallSingleChoiceCallback(), which is discussed below,
                         * returning false here won't allow the newly selected radio button to actually be selected.
                         **/
                        return true;
                    }
                })
                .positiveText(R.string.choose)
                .show();
    }

    /**
     * Exibe uma string no Toast
     *
     * @param message
     */
    private void showToast(String message) {
        if (mToast != null) {
            mToast.cancel();
            mToast = null;
        }
        mToast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        mToast.show();
    }

    /**
     * Busca o mês para exibição de dados
     *
     * @return int
     */
    private int getMesSelecionado() {

        if (this.mes_selecionado == -1) {

            // Seleciona o mês atual
            Date date = new Date();
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            int month = cal.get(Calendar.MONTH);

            this.mes_selecionado = month;
        }

        return this.mes_selecionado;
    }

    /**
     * Busca o ano para exibição de dados
     *
     * @return int
     */
    private int getAnoSelecionado() {

        if (this.ano_selecionado == -1) {

            // Seleciona o mês atual
            Date date = new Date();
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            int year = cal.get(Calendar.YEAR);

            this.ano_selecionado = year;
        }

        return this.ano_selecionado;
    }

    /**
     * Atualiza o texto do item do menu da toolbar com o mês selecionado
     */
    public void atualizarMes() {

        MenuItem bedMenuItem = menu.findItem(R.id.action_mes_atual);

        switch (this.mes_selecionado) {

            case 0:
                bedMenuItem.setTitle(R.string.mes_janeiro);
                break;

            case 1:
                bedMenuItem.setTitle(R.string.mes_fevereiro);
                break;

            case 2:
                bedMenuItem.setTitle(R.string.mes_marco);
                break;

            case 3:
                bedMenuItem.setTitle(R.string.mes_abril);
                break;

            case 4:
                bedMenuItem.setTitle(R.string.mes_maio);
                break;

            case 5:
                bedMenuItem.setTitle(R.string.mes_junho);
                break;

            case 6:
                bedMenuItem.setTitle(R.string.mes_julho);
                break;

            case 7:
                bedMenuItem.setTitle(R.string.mes_agosto);
                break;

            case 8:
                bedMenuItem.setTitle(R.string.mes_setembro);
                break;

            case 9:
                bedMenuItem.setTitle(R.string.mes_outubro);
                break;

            case 10:
                bedMenuItem.setTitle(R.string.mes_novembro);
                break;

            case 11:
                bedMenuItem.setTitle(R.string.mes_dezembro);
                break;
        }
    }

    /**
     * Abre um dialog para selecionar o ano
     *
     * @param item
     */
    public void showSelectYearDialog(MenuItem item) {

        int year = getAnoSelecionado();

        new MaterialDialog.Builder(this)
                .title(R.string.informe_o_ano)
                .inputType(InputType.TYPE_NUMBER_FLAG_SIGNED)
                .input(R.string.ex_with_point + " " + Integer.toString(year),
                        Integer.toString(year), new MaterialDialog.InputCallback() {

                            @Override
                            public void onInput(MaterialDialog dialog, CharSequence input) {

                                int selected_year = Integer.parseInt(input.toString());

                                try {

                                    if (selected_year > 1999 && selected_year < 2200) {

                                        // Seta o novo ano
                                        ano_selecionado = selected_year;

                                        // Altera ano na toolbar
                                        MenuItem bedMenuItem = menu.findItem(R.id.action_ano_atual);
                                        bedMenuItem.setTitle(input.toString());

                                        // Atualiza os dados do ListView
                                        showListView();
                                    }
                                } catch (NumberFormatException e) {
                                    showToast("O ano informado é inválido!");
                                }
                            }
                        }).show();

    }

    /**
     * Realiza a soma e exibe o taltal de creditos e gastos
     */
    public void exibirTotalGasto() {

        Map<String, Object> item = new HashMap<String, Object>();

        // MovimentacaoFinanceira mov = null;
        Double totalG = 0.0; //represento o gasto
        Double totalC = 0.0; //repressenta o crédito

        // Loop de gastos
        int j = 0;
        while (this.gastos.size() > j) {

            item = this.gastos.get(j);

            //soma o gasto
            if (Helper.formatCurrencyInverted(item.get("valor").toString()) < 0)
                totalG += Helper.formatCurrencyInverted(item.get("valor").toString());

            //realiza o credito
            totalC += Helper.formatCurrencyInverted(item.get("valor").toString());

            j++;
        }

        TextView totalGasto = (TextView) findViewById(R.id.totalGasto);
        totalGasto.setText("R$ " + String.valueOf(totalG));

        TextView totalCredito = (TextView) findViewById(R.id.totalCredito);
        totalCredito.setText("R$ " + String.valueOf(totalC));

        //changeColorAplication(totalC);
    }

    //Altera a cor da aplicação de acordo com o saldo
    public void changeColorAplication(Double credito) {

        this.toolbar = (Toolbar) findViewById(R.id.toolbar);

        if(credito > 0)
            this.toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        else if( credito == 0)
            this.toolbar.setBackgroundColor(Color.BLACK);
        else if(credito < 0)
            this.toolbar.setBackgroundColor(Color.RED);

    }

}
