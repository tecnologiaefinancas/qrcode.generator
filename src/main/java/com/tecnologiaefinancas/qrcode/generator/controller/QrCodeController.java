package com.tecnologiaefinancas.qrcode.generator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.ResponseEntity;
import com.tecnologiaefinancas.dto.QrCodeRequest;
import com.tecnologiaefinancas.dto.QrCodeResponse;
import com.tecnologiaefinancas.service.QrCodeService;
import com.google.zxing.WriterException;
import java.io.IOException;

@RestController
@RequestMapping("/qrcode")
public class QrCodeController {

    private final QrCodeService qrCodeService;

    @Autowired
    public QrCodeController(QrCodeService qrCodeService) {
        this.qrCodeService = qrCodeService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<QrCodeResponse> generate(@RequestBody QrCodeRequest request){
        try {
            QrCodeResponse response = this.qrCodeService.generateAndUploadQrCode(request.text());
            return ResponseEntity.ok(response);
        
        } catch (WriterException | IOException e) {
            return ResponseEntity.internalServerError().build();
        }
        
    }

}