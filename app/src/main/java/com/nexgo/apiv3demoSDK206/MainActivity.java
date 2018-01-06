package com.nexgo.apiv3demoSDK206;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.nexgo.apiv3demo.R;
import com.nexgo.oaf.apiv3.DeviceEngine;
import com.nexgo.oaf.apiv3.DeviceInfo;
import com.nexgo.oaf.apiv3.SdkResult;
import com.nexgo.oaf.apiv3.card.cpu.CPUCardHandler;
import com.nexgo.oaf.apiv3.card.memory.MemoryCardHandler;
import com.nexgo.oaf.apiv3.card.memory.ReadEntity;
import com.nexgo.oaf.apiv3.card.mifare.BlockEntity;
import com.nexgo.oaf.apiv3.card.mifare.M1CardHandler;
import com.nexgo.oaf.apiv3.card.mifare.M1CardOperTypeEnum;
import com.nexgo.oaf.apiv3.device.beeper.Beeper;
import com.nexgo.oaf.apiv3.device.led.LEDDriver;
import com.nexgo.oaf.apiv3.device.led.LightModeEnum;
import com.nexgo.oaf.apiv3.device.reader.CardInfoEntity;
import com.nexgo.oaf.apiv3.device.reader.CardReader;
import com.nexgo.oaf.apiv3.device.reader.CardSlotTypeEnum;
import com.nexgo.oaf.apiv3.device.reader.CardTypeEnum;
import com.nexgo.oaf.apiv3.device.reader.OnCardInfoListener;
import com.nexgo.oaf.apiv3.device.scanner.OnScannerListener;
import com.nexgo.oaf.apiv3.device.scanner.Scanner;
import com.nexgo.oaf.apiv3.device.scanner.ScannerCfgEntity;
import com.nexgo.oaf.apiv3.device.serialport.SerialCfgEntity;
import com.nexgo.oaf.apiv3.device.serialport.SerialPortDriver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private Logger log;
    private DeviceEngine deviceEngine;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        log = LoggerFactory.getLogger(this.getClass().getName());
        deviceEngine = ((NexgoApplication) getApplication()).deviceEngine;
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.beeper:
                beeperTest();
                break;
            case R.id.led:
                ledTest();
                break;
            case R.id.printer:
                startActivity(new Intent(this, PrinterActivity.class));
                break;
            case R.id.Serialport:
                serialportTest();
                break;
            case R.id.Cpucard:
                cpucardTest();
                break;
            case R.id.cardReader:
                cardReaderTest();
                break;
            case R.id.M1Card:
                M1CardTest();
                break;
            case R.id.pinpad:
                startActivity(new Intent(this, PinpadActivity.class));
                break;
            case R.id.emv:
                startActivity(new Intent(this, EmvActivity.class));
                break;
            case R.id.scanner:
                scannerTest();
                break;
            case R.id.dev_info:
                devInfoTest();
                break;

            case R.id.memorycard:
                MemoryCardTest();
                break;

            default:
                break;
        }
    }

    private void cardReaderTest() {
        final CardReader cardReader = deviceEngine.getCardReader();
        HashSet<CardSlotTypeEnum> slotTypes = new HashSet<>();
        slotTypes.add(CardSlotTypeEnum.SWIPE);
        slotTypes.add(CardSlotTypeEnum.ICC1);
        slotTypes.add(CardSlotTypeEnum.RF);
        cardReader.searchCard(slotTypes, 60, new OnCardInfoListener() {
            @Override
            public void onCardInfo(int retCode, CardInfoEntity cardInfo) {
                final StringBuilder sb = new StringBuilder();
                sb.append("返回值" + retCode + "\n");
                if (cardInfo != null) {
                    sb.append("卡存在的卡槽类型" + cardInfo.getCardExistslot() + "\n");
                    sb.append("一磁道" + cardInfo.getTk1() + "\n");
                    sb.append("二磁道" + cardInfo.getTk2() + "\n");
                    sb.append("三磁道" + cardInfo.getTk3() + "\n");
                    sb.append("卡号" + cardInfo.getCardNo() + "\n");
                    sb.append("是否为IC卡" + cardInfo.isICC() + "\n");
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, sb.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onSwipeIncorrect() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, "请重新刷卡", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onMultipleCards() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, "请使用单一卡片", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        Toast.makeText(MainActivity.this, getString(R.string.prompt_card), Toast.LENGTH_SHORT).show();
    }

    private void beeperTest() {
        final Beeper beeper = deviceEngine.getBeeper();
        View dv = getLayoutInflater().inflate(R.layout.dialog_items_layout, null);
        ListView lv = (ListView) dv.findViewById(R.id.listView);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        Observable.just(null)
                                .observeOn(Schedulers.io())
                                .subscribe(new Action1<Object>() {
                                    @Override
                                    public void call(Object o) {
                                        beeper.beep(100);
                                    }
                                }, new Action1<Throwable>() {
                                    @Override
                                    public void call(Throwable throwable) {

                                    }
                                });
                        break;
                    case 1:
                        Observable.just(null)
                                .observeOn(Schedulers.io())
                                .subscribe(new Action1<Object>() {
                                    @Override
                                    public void call(Object o) {
                                        beeper.beep(500);
                                    }
                                }, new Action1<Throwable>() {
                                    @Override
                                    public void call(Throwable throwable) {

                                    }
                                });
                        break;
                    case 2:
                        Observable.interval(70, TimeUnit.MILLISECONDS)
                                .observeOn(Schedulers.io())
                                .takeUntil(new Func1<Long, Boolean>() {
                                    @Override
                                    public Boolean call(Long aLong) {
                                        return aLong == 1;
                                    }
                                })
                                .subscribe(new Action1<Object>() {
                                    @Override
                                    public void call(Object o) {
                                        beeper.beep(50);
                                    }
                                }, new Action1<Throwable>() {
                                    @Override
                                    public void call(Throwable throwable) {

                                    }
                                });
                        break;
                    case 3:
                        Observable.interval(600, TimeUnit.MILLISECONDS)
                                .observeOn(Schedulers.io())
                                .takeUntil(new Func1<Long, Boolean>() {
                                    @Override
                                    public Boolean call(Long aLong) {
                                        return aLong == 3;
                                    }
                                })
                                .subscribe(new Action1<Object>() {
                                    @Override
                                    public void call(Object o) {
                                        beeper.beep(100);
                                    }
                                }, new Action1<Throwable>() {
                                    @Override
                                    public void call(Throwable throwable) {

                                    }
                                });
                        break;
                    case 4:
                        Observable.interval(400, TimeUnit.MILLISECONDS)
                                .observeOn(Schedulers.io())
                                .takeUntil(new Func1<Long, Boolean>() {
                                    @Override
                                    public Boolean call(Long aLong) {
                                        return aLong == 2;
                                    }
                                })
                                .subscribe(new Action1<Object>() {
                                    @Override
                                    public void call(Object o) {
                                        beeper.beep(200);
                                    }
                                }, new Action1<Throwable>() {
                                    @Override
                                    public void call(Throwable throwable) {

                                    }
                                });
                        break;
                }
            }
        });
        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.beeper_items));
        lv.setAdapter(stringArrayAdapter);
        new AlertDialog.Builder(this).setView(dv).create().show();
    }

    private void ledTest() {
        final LEDDriver ledDriver = deviceEngine.getLEDDriver();
        View dv = getLayoutInflater().inflate(R.layout.dialog_items_layout, null);
        ListView lv = (ListView) dv.findViewById(R.id.listView);
        final HashMap<Integer, LightModeEnum> hashMap = new HashMap<>();
        hashMap.put(0, LightModeEnum.BLUE);
        hashMap.put(1, LightModeEnum.YELLOW);
        hashMap.put(2, LightModeEnum.RED);
        hashMap.put(3, LightModeEnum.GREEN);
        final boolean[] state = new boolean[hashMap.size()];
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                state[position] = !state[position];
                ledDriver.setLed(hashMap.get(position), state[position]);
            }
        });
        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.led_items));
        lv.setAdapter(stringArrayAdapter);
        new AlertDialog.Builder(this).setView(dv).setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                for (int i = 0; i < hashMap.size(); i++) {
                    ledDriver.setLed(hashMap.get(i), false);
                }
            }
        }).create().show();
    }

    private void serialportTest() {
        final SerialPortDriver port = deviceEngine.getSerialPortDriver(0);
        SerialCfgEntity serialCfgEntity = new SerialCfgEntity();
        serialCfgEntity.setBaudRate(9600);
        serialCfgEntity.setDataBits(8);
        serialCfgEntity.setParity('n');
        serialCfgEntity.setStopBits(1);
        int ret = port.connect(serialCfgEntity);
        if (ret == SdkResult.Success) {
            Toast.makeText(MainActivity.this, "serialport connect success", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(MainActivity.this, "serialport connect fail", Toast.LENGTH_SHORT).show();
        }
        port.disconnect();
//        Toast.makeText(MainActivity.this, "serialport cannot apply to N5,only G870S can", Toast.LENGTH_SHORT).show();
    }

    private void cpucardTest() {
        final CPUCardHandler cpuCardHandler = deviceEngine.getCPUCardHandler(CardSlotTypeEnum.ICC1);
        byte[] atr = new byte[512];
        Arrays.fill(atr, (byte) 0);
        cpuCardHandler.powerOn(atr);
        if (atr[0] > 0) {
            Toast.makeText(MainActivity.this, "cpucard powerOn have return value", Toast.LENGTH_SHORT).show();
        }

        Toast.makeText(MainActivity.this, "cpucard test", Toast.LENGTH_SHORT).show();
    }

    private void M1CardTest() {
        final M1CardHandler m1Card = deviceEngine.getM1CardHandler();
//        byte[] data = new byte[]{0x01,0x02};
        BlockEntity blockEntity = new BlockEntity();
        blockEntity.setOperType(M1CardOperTypeEnum.INCREMENT);
//        blockEntity.setBlkData(data);
//        blockEntity.setBlkNo(1);
        blockEntity.setDesBlkNo(2);
        int ret = m1Card.readBlock(blockEntity);
        if (ret == SdkResult.Success) {
            Toast.makeText(MainActivity.this, "M1Card readBlock SUCCESS", Toast.LENGTH_SHORT).show();
        }
        Toast.makeText(MainActivity.this, "M1Card test", Toast.LENGTH_SHORT).show();
    }

    private void scannerTest() {
        final Scanner scanner = deviceEngine.getScanner(this);
        ScannerCfgEntity cfgEntity = new ScannerCfgEntity();
        cfgEntity.setAutoFocus(false);
        cfgEntity.setUsedFrontCcd(false);
        scanner.initScanner(cfgEntity, new OnScannerListener() {
            @Override
            public void onInitResult(int retCode) {
                if (retCode == SdkResult.Success) {
                    int result = scanner.startScan(60, new OnScannerListener() {
                        @Override
                        public void onInitResult(int retCode) {

                        }

                        @Override
                        public void onScannerResult(int retCode, final String data) {
                            switch (retCode) {
                                case SdkResult.Success:
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(MainActivity.this, getString(R.string.scanner_content) + " " + data, Toast.LENGTH_LONG).show();
                                        }
                                    });
                                    break;
                                case SdkResult.TimeOut:
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(MainActivity.this, getString(R.string.scanner_timeout), Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                    break;
                                case SdkResult.Scanner_Customer_Exit:
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(MainActivity.this, getString(R.string.scanner_customer_exit), Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                    break;
                                default:
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(MainActivity.this, getString(R.string.scanner_fail), Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                    break;
                            }
                        }
                    });
                    if (result != SdkResult.Success) {
                        Toast.makeText(MainActivity.this, getString(R.string.scanner_start_error), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, getString(R.string.scanner_init_error), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onScannerResult(int retCode, String data) {

            }
        });
    }

    private void devInfoTest() {
        DeviceInfo deviceInfo = deviceEngine.getDeviceInfo();
        StringBuilder sb = new StringBuilder();
        sb.append("SN:" + deviceInfo.getSn() + "\n");
        sb.append("KSN:" + deviceInfo.getKsn() + "\n");
        sb.append("OSVer:" + deviceInfo.getOsVer() + "\n");
        sb.append("FirmWareVer:" + deviceInfo.getFirmWareVer() + "\n");
        sb.append("KernelVer:" + deviceInfo.getKernelVer() + "\n");
        sb.append("SDKVer:" + deviceInfo.getSdkVer() + "\n");
        sb.append("MODEL:" + deviceInfo.getModel());
        Toast.makeText(MainActivity.this, sb.toString(), Toast.LENGTH_SHORT).show();
    }
    private void MemoryCardTest() {
        final MemoryCardHandler memoryCardHandler = deviceEngine.getMemoryCardHandler(CardSlotTypeEnum.PSAM1);
        int ret = memoryCardHandler.reset(CardTypeEnum.AT88SC153);
        if (ret == SdkResult.Success) {
            ReadEntity readEntity = new ReadEntity();
            readEntity.setCardType(CardTypeEnum.AT88SC153);
            readEntity.setZone(1);
            readEntity.setAddress(0);
            readEntity.setReadLen(16);
            byte[] data = memoryCardHandler.read(readEntity);
            if(data == null){
                Toast.makeText(MainActivity.this, "MemoryCard read fail", Toast.LENGTH_SHORT).show();
            }
            Toast.makeText(MainActivity.this, "M1Card readBlock SUCCESS", Toast.LENGTH_SHORT).show();
        }
        Toast.makeText(MainActivity.this, "MemoryCard test", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}
