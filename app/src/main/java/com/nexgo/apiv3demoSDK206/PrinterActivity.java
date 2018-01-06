package com.nexgo.apiv3demoSDK206;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.nexgo.apiv3demo.R;
import com.nexgo.oaf.apiv3.DeviceEngine;
import com.nexgo.oaf.apiv3.device.printer.AlignEnum;
import com.nexgo.oaf.apiv3.device.printer.BarcodeFormatEnum;
import com.nexgo.oaf.apiv3.device.printer.DotMatrixFontEnum;
import com.nexgo.oaf.apiv3.device.printer.FontEntity;
import com.nexgo.oaf.apiv3.device.printer.GrayLevelEnum;
import com.nexgo.oaf.apiv3.device.printer.OnPrintListener;
import com.nexgo.oaf.apiv3.device.printer.Printer;

public class PrinterActivity extends AppCompatActivity {

    private DeviceEngine deviceEngine;
    private Printer printer;
    private final int FONT_SIZE_SMALL = 20;
    private final int FONT_SIZE_NORMAL = 24;
    private final int FONT_SIZE_BIG = 24;
    private FontEntity fontSmall = new FontEntity(DotMatrixFontEnum.CH_SONG_20X20, DotMatrixFontEnum.ASC_SONG_8X16);
    private FontEntity fontNormal = new FontEntity(DotMatrixFontEnum.CH_SONG_24X24, DotMatrixFontEnum.ASC_SONG_12X24);
    private FontEntity fontBold = new FontEntity(DotMatrixFontEnum.CH_SONG_24X24, DotMatrixFontEnum.ASC_SONG_BOLD_16X24);
    private FontEntity fontBig = new FontEntity(DotMatrixFontEnum.CH_SONG_24X24, DotMatrixFontEnum.ASC_SONG_12X24, true, true);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_printer);
        deviceEngine = ((NexgoApplication) getApplication()).deviceEngine;
        printer = deviceEngine.getPrinter();
        Log.i("PrinterActivity","This is test whether realse version v2.0.6");
    }

    public void onClick(View view) {
        Bitmap bitmap;
        switch (view.getId()) {
            case R.id.print_sale_order:
                printer.initPrinter();
                printer.setLetterSpacing(5);
                printer.setGray(GrayLevelEnum.LEVEL_2);
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.logo);
                printer.appendImage(bitmap, AlignEnum.CENTER);
                printer.appendPrnStr("商户名称:应用测试", FONT_SIZE_NORMAL, AlignEnum.LEFT, true);
                printer.appendPrnStr("1111111111111111111111111111111", FONT_SIZE_NORMAL, AlignEnum.LEFT, true);
                printer.appendPrnStr("0000000000000000000000000000000", FONT_SIZE_NORMAL, AlignEnum.LEFT, true);
                printer.appendPrnStr("OOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO", FONT_SIZE_NORMAL, AlignEnum.LEFT, true);
                printer.appendPrnStr("ooooooooooooooooooooooooooooooo", FONT_SIZE_NORMAL, AlignEnum.LEFT, true);
                printer.appendPrnStr("8888888888888888888888888888888", FONT_SIZE_NORMAL, AlignEnum.LEFT, true);


                printer.appendPrnStr("发卡行:工商银行", FONT_SIZE_NORMAL, AlignEnum.LEFT, true);
                printer.appendPrnStr("收单行:银联商务", FONT_SIZE_NORMAL, AlignEnum.LEFT, true);
                printer.appendPrnStr("有效期:26/06", FONT_SIZE_NORMAL, AlignEnum.LEFT, true);
                printer.appendPrnStr("卡号:", FONT_SIZE_NORMAL, AlignEnum.LEFT, true);
                printer.appendPrnStr("621226*********1197/C", FONT_SIZE_BIG, AlignEnum.LEFT, true);
                printer.appendPrnStr("交易类型:", FONT_SIZE_NORMAL, AlignEnum.LEFT, true);
                printer.appendPrnStr("消费撤销", FONT_SIZE_BIG, AlignEnum.LEFT, true);
                printer.appendPrnStr("批次号:000938", FONT_SIZE_NORMAL, AlignEnum.LEFT, true);
                printer.appendPrnStr("凭证号:000146", "授权码:012345", FONT_SIZE_NORMAL, true);
                printer.appendPrnStr("参考号:150303296481", FONT_SIZE_NORMAL, AlignEnum.LEFT, true);
                printer.appendPrnStr("交易日期:2016/09/21 15:03:03", FONT_SIZE_NORMAL, AlignEnum.LEFT, true);
                printer.appendPrnStr("金额:", FONT_SIZE_NORMAL, AlignEnum.LEFT, true);
                printer.appendPrnStr("    RMB:123456789.00", FONT_SIZE_BIG, AlignEnum.LEFT, true);
                printer.appendPrnStr("备注:", FONT_SIZE_NORMAL, AlignEnum.LEFT, true);
                printer.appendPrnStr("原凭证号:000145", FONT_SIZE_NORMAL, AlignEnum.LEFT, true);
                printer.appendPrnStr("附加信息(Host):", FONT_SIZE_NORMAL, AlignEnum.LEFT, true);
                printer.appendBarcode("1234567890", 50, 0, 2, BarcodeFormatEnum.CODE_128, AlignEnum.CENTER);
                printer.appendQRcode("测试二维码测试二维码测试二维码测试二维码测试二维码测试二维码测试二维码测试二维码测试二维码", 200, AlignEnum.CENTER);
                printer.appendPrnStr("---------------------------", FONT_SIZE_NORMAL, AlignEnum.LEFT, true);
                printer.appendPrnStr("持卡人签名:", FONT_SIZE_NORMAL, AlignEnum.LEFT, true);
                printer.appendPrnStr("\n", FONT_SIZE_NORMAL, AlignEnum.LEFT, true);
                printer.appendPrnStr("\n", FONT_SIZE_NORMAL, AlignEnum.LEFT, true);
                printer.appendPrnStr("\n", FONT_SIZE_NORMAL, AlignEnum.LEFT, true);
                printer.appendPrnStr("---------------------------", FONT_SIZE_NORMAL, AlignEnum.LEFT, true);
                printer.appendPrnStr("本人确认以上交易,同意将其计入本卡账户", FONT_SIZE_SMALL, AlignEnum.LEFT, true);
                printer.appendPrnStr("I ACKNOWLEDGE SATISFACTORY RECEIPT OF RELATIVE GOODS/SERVICES", FONT_SIZE_SMALL, AlignEnum.LEFT, true);
                printer.appendPrnStr("---------------------------", FONT_SIZE_NORMAL, AlignEnum.LEFT, true);
                printer.appendPrnStr("商户存根", FONT_SIZE_NORMAL, AlignEnum.RIGHT, true);
                printer.startPrint(true, new OnPrintListener() {
                    @Override
                    public void onPrintResult(final int retCode) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(PrinterActivity.this, retCode + "", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
                break;
            case R.id.print_sale_order2:
                printer.initPrinter();
                printer.setLetterSpacing(5);
                printer.setGray(GrayLevelEnum.LEVEL_2);
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.logo);
                printer.appendImage(bitmap, AlignEnum.CENTER);
                printer.appendPrnStr("商户名称:应用测试", fontNormal, AlignEnum.LEFT);
                printer.appendPrnStr("商户号:123456789012345", fontNormal, AlignEnum.LEFT);
                printer.appendPrnStr("终端号:12345678", "操作员号:01", fontNormal);
                printer.appendPrnStr("发卡行:工商银行", fontNormal, AlignEnum.LEFT);
                printer.appendPrnStr("收单行:银联商务", fontNormal, AlignEnum.LEFT);
                printer.appendPrnStr("有效期:26/06", fontNormal, AlignEnum.LEFT);
                printer.appendPrnStr("卡号:", fontNormal, AlignEnum.LEFT);
                printer.appendPrnStr("621226*********1197/C", fontBold, AlignEnum.LEFT);
                printer.appendPrnStr("交易类型:", fontNormal, AlignEnum.LEFT);
                printer.appendPrnStr("消费撤销", fontBig, AlignEnum.LEFT);
                printer.appendPrnStr("批次号:000938", fontNormal, AlignEnum.LEFT);
                printer.appendPrnStr("凭证号:000146", "授权码:012345", fontNormal);
                printer.appendPrnStr("参考号:150303296481", fontNormal, AlignEnum.LEFT);
                printer.appendPrnStr("交易日期:2016/09/21 15:03:03", fontNormal, AlignEnum.LEFT);
                printer.appendPrnStr("金额:", fontNormal, AlignEnum.LEFT);
                printer.appendPrnStr("    RMB:123456789.00", fontBold, AlignEnum.LEFT);
                printer.appendPrnStr("备注:", fontNormal, AlignEnum.LEFT);
                printer.appendPrnStr("原凭证号:000145", fontNormal, AlignEnum.LEFT);
                printer.appendPrnStr("附加信息(Host):", fontNormal, AlignEnum.LEFT);
                printer.appendBarcode("1234567890", 50, 0, 2, BarcodeFormatEnum.CODE_128, AlignEnum.CENTER);
                printer.appendQRcode("测试二维码测试二维码测试二维码测试二维码测试二维码测试二维码测试二维码测试二维码测试二维码", 200, AlignEnum.CENTER);
                printer.appendPrnStr("---------------------------", fontNormal, AlignEnum.LEFT);
                printer.appendPrnStr("持卡人签名:", fontNormal, AlignEnum.LEFT);
                printer.appendPrnStr("\n", fontNormal, AlignEnum.LEFT);
                printer.appendPrnStr("\n", fontNormal, AlignEnum.LEFT);
                printer.appendPrnStr("\n", fontNormal, AlignEnum.LEFT);
                printer.appendPrnStr("---------------------------", fontNormal, AlignEnum.LEFT);
                printer.appendPrnStr("本人确认以上交易,同意将其计入本卡账户", fontSmall, AlignEnum.LEFT);
                printer.appendPrnStr("I ACKNOWLEDGE SATISFACTORY RECEIPT OF RELATIVE GOODS/SERVICES", fontSmall, AlignEnum.LEFT);
                printer.appendPrnStr("---------------------------", fontNormal, AlignEnum.LEFT);
                printer.appendPrnStr("商户存根", fontNormal, AlignEnum.RIGHT);
                printer.startPrint(true, new OnPrintListener() {
                    @Override
                    public void onPrintResult(final int retCode) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(PrinterActivity.this, retCode + "", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
                break;
        }
    }
}
