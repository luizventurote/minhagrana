package luizventurote.minhagrana;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.drive.DriveApi.DriveContentsResult;
import com.google.android.gms.drive.DriveContents;
import com.google.android.gms.drive.DriveFolder.DriveFileResult;
import com.google.android.gms.drive.MetadataChangeSet;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.List;

import luizventurote.minhagrana.controller.MainController;
import luizventurote.minhagrana.helper.Helper;
import luizventurote.minhagrana.model.MovimentacaoFinanceira;

public class GoogleDrive extends GoogleDriveBaseActivity {

    private static final String TAG = "GoogleDrive";

    private Context context = this;

    @Override
    public void onConnected(Bundle connectionHint) {
        super.onConnected(connectionHint);
        // create new contents resource
        Drive.DriveApi.newDriveContents(getGoogleApiClient())
                .setResultCallback(driveContentsCallback);
    }

    final private ResultCallback<DriveContentsResult> driveContentsCallback = new
            ResultCallback<DriveContentsResult>() {
                @Override
                public void onResult(DriveContentsResult result) {
                    if (!result.getStatus().isSuccess()) {
                        showMessage("Error while trying to create new file contents");
                        return;
                    }
                    final DriveContents driveContents = result.getDriveContents();

                    // Perform I/O off the UI thread.
                    new Thread() {
                        @Override
                        public void run() {
                            // write content to DriveContents
                            OutputStream outputStream = driveContents.getOutputStream();
                            Writer writer = new OutputStreamWriter(outputStream);

                            int year = 2015;

                            try {

                                // Verifica o ano selecionado
                                if( getIntent().hasExtra("year") ) {

                                    Bundle extras = getIntent().getExtras();

                                    year = extras.getInt("year");
                                }

                                // CSV Header
                                writer.write("descricao,data,valor\n");

                                // Datas
                                String minDate = Integer.toString(year)+"-01-01";
                                String maxDate = Integer.toString(year)+"-12-31";

                                // Busca movimentações
                                List<MovimentacaoFinanceira> mov_list = MainController.buscarEntreDatas(context, minDate, maxDate);

                                // Model de movimentação financeira
                                MovimentacaoFinanceira mov = null;

                                // Loop de gastos
                                int j = 0; while (mov_list.size() > j) {

                                    // Load model
                                    mov = mov_list.get(j);

                                    String data = Helper.formatDateToStringWithSlash(mov.getData());
                                    String desc = mov.getDescricao();
                                    String valor = Helper.formatCurrency(mov.getValor()).replace(".","").replace(",",".");

                                    writer.write(desc + "," + data + "," + valor + "\n");

                                    j++;

                                }

                                writer.close();

                            } catch (IOException e) {
                                Log.e(TAG, e.getMessage());
                            }

                            MetadataChangeSet changeSet = new MetadataChangeSet.Builder()
                                    .setTitle("minha-grana-em-"+Integer.toString(year)+".csv")
                                    .setMimeType("text/csv")
                                    .setStarred(true).build();

                            // create a file on root folder
                            Drive.DriveApi.getRootFolder(getGoogleApiClient())
                                    .createFile(getGoogleApiClient(), changeSet, driveContents)
                                    .setResultCallback(fileCallback);
                        }
                    }.start();
                }
            };

    final private ResultCallback<DriveFileResult> fileCallback = new
            ResultCallback<DriveFileResult>() {
                @Override
                public void onResult(DriveFileResult result) {
                    if (!result.getStatus().isSuccess()) {
                        showMessage("Error while trying to create the file");
                        return;
                    }
                    showMessage("Created a file with content: " + result.getDriveFile().getDriveId());

                    finish();
                }
            };
}
