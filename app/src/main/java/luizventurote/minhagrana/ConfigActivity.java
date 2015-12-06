package luizventurote.minhagrana;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import java.util.Calendar;
import java.util.Date;

public class ConfigActivity extends AppCompatActivity {

    /**
     * Context
     */
    private Context context = this;

    /**
     * Drawer
     */
    private Drawer drawer = null;

    /**
     * Toolbar
     */
    private Toolbar toolbar;

    /**
     * Menu toolbar
     */
    private Menu menu;

    /**
     * Ano selecionado pelo usuário para exibição de dados
     */
    private int ano_selecionado = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);

        // Show Drawer
        this.showDrawer(savedInstanceState);
    }

    /**
     * Show drawer with toolbar
     *
     * @param savedInstanceState
     */
    private void showDrawer(Bundle savedInstanceState) {

        toolbar = (Toolbar) findViewById(R.id.toolbar_config);
        setSupportActionBar(toolbar);

        // Create the drawer
        drawer  = new DrawerBuilder(this)
                .withRootView(R.id.drawer_container_config)
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

                                // Abre a main activity
                                startActivity(new Intent(context, MainActivity.class));

                                finish();

                            } else if (drawerItem.getIdentifier() == 2) {

                            }
                        }

                        return false;
                    }

                })
                .withSavedInstance(savedInstanceState)
                .build();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_config, menu);

        this.menu = menu;

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

    /**
     * Exporta dados para o Google Drive
     *
     * @param v
     */
    public void saveGoogleDrive(View v) {

        showSelectYearDialog();

    }

    /**
     * Abre um dialog para selecionar o ano
     */
    public void showSelectYearDialog() {

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

                                        // Abre activity
                                        Intent mIntent = new Intent(context, GoogleDrive.class);
                                        Bundle mBundle = new Bundle();
                                        mBundle.putInt("year", ano_selecionado);
                                        mIntent.putExtras(mBundle);
                                        startActivity(mIntent);
                                    }
                                } catch (NumberFormatException e) {
                                    showMessage("O ano informado é inválido!");
                                }
                            }
                        }).show();

    }

    /**
     * Busca o ano para exibição de dados
     *
     * @return int
     */
    private int getAnoSelecionado() {

        if(this.ano_selecionado == -1) {

            // Seleciona o mês atual
            Date date= new Date();
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            int year = cal.get(Calendar.YEAR);

            this.ano_selecionado = year;
        }

        return this.ano_selecionado;
    }

    /**
     * Shows a toast message.
     */
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}
