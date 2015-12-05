package luizventurote.minhagrana;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

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
}
