package luizventurote.minhagrana;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
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

    /**
     * Mês selecionado pelo usuário para exibição de dados
     */
    private int mes_selecionado = -1;

    /**
     * Ano selecionado pelo usuário para exibição de dados
     */
    private int ano_selecionado = 2015;

    /**
     * Floating action button
     */
    private FloatingActionsMenu menuMultipleActions;

    /**
     * ListView
     */
    private ListView mainListView ;
    private ArrayAdapter<String> listAdapter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.mes_selecionado = getMesSelecionado();

        // Show Drawer
        this.showDrawer(savedInstanceState);

        // Show Float Action Button
        this.showFloatActionButton();

        // Show ListView
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
                        new SecondaryDrawerItem().withName(R.string.drawer_item_settings).withIcon(FontAwesome.Icon.faw_cog).withIdentifier(2)
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {

                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {

                        if (drawerItem != null) {

                            Intent intent = null;

                            if (drawerItem.getIdentifier() == 1) {
                                Log.d("LOG", "Click item 1");
                            } else if (drawerItem.getIdentifier() == 2) {
                                Log.d("LOG", "Click item 2");
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
                Toast.makeText(MainActivity.this, "Clicked pink Floating Action Button", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Show List View
     */
    private void showListView() {

        // Find the ListView resource.
        mainListView = (ListView) findViewById( R.id.listViewValues );

        // Create and populate a List of planet names.
        String[] de = {"descricao", "valor"};
        int[] para = {R.id.descricao, R.id.valor};
        SimpleAdapter adapter = new SimpleAdapter(this, listarGastos(), R.layout.itens_da_lista_gasto, de, para);

        ListView lv = (ListView) findViewById(R.id.listViewValues);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(chamaAtividades(this));

    }

    public AdapterView.OnItemClickListener chamaAtividades(final Context context){
        return(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> av, View v, int position, long id) {
                Map<String, Object> map = gastos.get(position);
               //AQUI VAI O (CÓDIGO A SER DEFINIDO)
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

        // Busca uma lista com todos os gastos
        List<MovimentacaoFinanceira> lista_gastos = MainController.buscarMovimentacaoFinanceira(this);

        // Model de movimentação financeira
        MovimentacaoFinanceira mov = null;

        // Loop de gastos
        int j = 0; while (lista_gastos.size() > j) {

            // Load model
            mov = lista_gastos.get(j);

            item.put("descricao", mov.getDescricao());
            item.put("valor", mov.getValor());
            gastos.add(item);

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

                        atualizarMes();

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

        if(this.mes_selecionado == -1) {

            // Seleciona o mês atual
            Date date= new Date();
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            int month = cal.get(Calendar.MONTH);

            this.mes_selecionado = month;
        }

        return this.mes_selecionado;
    }

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
}
