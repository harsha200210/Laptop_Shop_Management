package lk.ijse.Laptop_Shop_Management.util;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamResolution;
import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;

import java.awt.image.BufferedImage;
import java.util.EnumMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class QrScanner {

    public static String startScanning() {
        Webcam webcam = Webcam.getDefault();
        if (webcam != null) {
            webcam.setViewSize(WebcamResolution.VGA.getSize());
            webcam.open();

            FutureTask<String> futureTask = new FutureTask<>(new Callable<String>() {
                @Override
                public String call() {
                    while (true) {
                        BufferedImage image = webcam.getImage();
                        if (image != null) {
                            try {
                                String qrCodeData = readQRCode(image);
                                if (qrCodeData != null) {
                                    webcam.close();
                                    return qrCodeData;
                                }
                            } catch (NotFoundException e) {
                                // QR code not found in the current frame, continue scanning
                            }
                        }
                    }
                }
            });

            new Thread(futureTask).start();

            try {
                return futureTask.get();  // Blocks until the QR code is read
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
                return null;
            }
        } else {
            System.err.println("No webcam detected!");
            return null;
        }
    }

    private static String readQRCode(BufferedImage image) throws NotFoundException {
        Map<DecodeHintType, Object> hints = new EnumMap<>(DecodeHintType.class);
        hints.put(DecodeHintType.TRY_HARDER, Boolean.TRUE);

        BinaryBitmap binaryBitmap = new BinaryBitmap(
                new HybridBinarizer(new BufferedImageLuminanceSource(image)));
        MultiFormatReader reader = new MultiFormatReader();
        Result result = reader.decode(binaryBitmap, hints);

        return result.getText();
    }
}
