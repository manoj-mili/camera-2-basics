package com.mili.camera2api;

import com.journeyapps.barcodescanner.CaptureActivity;


/*
* only reason to create this activity is to make sure the qr code is in portrait mode
* and CaptureActivity is part of com.journeyapps.barcodescanner lib
* As we cannot access CaptureActivity and set it to portrait we can use this approach
* */
public class ScanActivity extends CaptureActivity {

}
