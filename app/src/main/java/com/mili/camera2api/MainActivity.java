package com.mili.camera2api;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.mili.camera2api.databinding.ActivityMainBinding;

import static com.mili.camera2api.HelperClass.isDataAnUrl;
import static com.mili.camera2api.HelperClass.isDataAnEmailId;
import static com.mili.camera2api.HelperClass.isDataOfTypeJson;
import static com.mili.camera2api.HelperClass.isDataOfTypeNumber;

public class MainActivity extends BaseActivity implements UserAction {

    IntentIntegrator scanIntent;
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding  = DataBindingUtil.setContentView(this,R.layout.activity_main);
        getSupportActionBar().show();
        binding.setUserAction(this);
        checkCameraPermission();
    }

    @Override
    public void onClickPhoto() {
        Intent clickPhoto = new Intent(this, CameraActivity.class);
        startActivity(clickPhoto);
    }

    @Override
    public void onScanQRCode() {
        scanIntent = new IntentIntegrator(this);
        scanIntent.setCaptureActivity(ScanActivity.class);
        scanIntent.setBeepEnabled(true);
        scanIntent.setPrompt(getString(R.string.qr_code_scan_instruction));
        scanIntent.initiateScan();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Snackbar.make(binding.getRoot(), getResources().getString(R.string.code_not_scanned), Snackbar.LENGTH_LONG).show();
            } else {
                String dataInQrCode = result.getContents();
                binding.setScannedData(formatContentBasedOnData(dataInQrCode));
            }
        }

    }

    private String formatContentBasedOnData(String dataInQrCode) {
        StringBuilder builder = new StringBuilder();
        if (isDataOfTypeNumber(dataInQrCode)) {
            builder.append("Data in QR is a Number: \n").append(dataInQrCode);
            return builder.toString();
        } else if (isDataOfTypeJson(dataInQrCode)) {
            builder.append("Data in QR is a JSON Object: \n").append(dataInQrCode);
            return builder.toString();
        } else if (isDataAnEmailId(dataInQrCode)) {
            builder.append("Data in QR is an Email Id: \n").append(dataInQrCode);
            return builder.toString();
        } else if (isDataAnUrl(dataInQrCode)) {
            builder.append("Data in QR is an URL: \n").append(dataInQrCode);
            return builder.toString();
        } else {
            builder.append("Data in QR is a Normal Text: \n").append(dataInQrCode);
            return builder.toString();
        }
        // we can add more option if required
    }
}
