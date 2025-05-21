package com.tecnologiaefinancas.service;

import com.tecnologiaefinancas.ports.StoragePort;
import com.tecnologiaefinancas.dto.QrCodeRequest;
import com.tecnologiaefinancas.dto.QrCodeResponse;
import org.springframework.stereotype.Service;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.BarcodeFormat;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import com.google.zxing.WriterException;
import java.util.UUID;


@Service
public class QrCodeService {

    private final StoragePort storage;

    public QrCodeService(StoragePort storage) {
        this.storage = storage;
    }

    public QrCodeResponse generateAndUploadQrCode(String text) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, 250, 250);

        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
        byte[] pngQrCodeData = pngOutputStream.toByteArray();

        String url = storage.uploadFile(pngQrCodeData, UUID.randomUUID().toString(), "image/png");

        return new QrCodeResponse(url);

    }
}
