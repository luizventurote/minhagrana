package luizventurote.minhagrana;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    /**
     * Save header or result
     */
    //variaveis pessoais
    private Context context = this;
    private List<Map<String, Object>> gastos;


    private AccountHeader headerResult = null;
    private Drawer result = null;
    private boolean opened = false;

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

    private List<Map<String, Object>> listarGastos() {
        this.gastos = new ArrayList<Map<String, Object>>();

        Map<String, Object> item = new HashMap<String, Object>();
        item.put("descricao", "Churrasco");
        item.put("valor", "50,00");
        gastos.add(item);

        item = new HashMap<String, Object>();
        item.put("descricao", "Onibus");
        item.put("valor", "20,00");

        gastos.add(item);

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
}
