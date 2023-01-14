package com.crypto.demo.api;


import com.crypto.demo.models.AffineBrutModel;
import com.crypto.demo.models.InputModel;
import com.crypto.demo.models.VigenereBrutModel;
import com.crypto.demo.models.VigenereModel;
import com.crypto.demo.service.CryptoService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class CryptoController {

    private CryptoService cryptoService;

    public CryptoController(CryptoService cryptoService){
        this.cryptoService=cryptoService;
    }
    @PostMapping("/hi")
    public String sayHi(){
        return "Hi";
    }

    @PostMapping("/affine/encrypt")
    public String CryptAffine(@RequestBody InputModel model){
        return cryptoService.AffineEncrypt(model);
    }

    @PostMapping("/affine/decrypt")
    public String DeCryptAffine(@RequestBody InputModel model){
        return cryptoService.AffineDecrypt(model);
    }
    @PostMapping("/affine/brut")
    public List<InputModel> AffineBruteCrypt(@RequestBody AffineBrutModel model){
        return cryptoService.AffineBruteDecrypt(model);
    }

    @PostMapping("/viginere/brut")
    public List<VigenereModel> ViginereBruteCrypt(@RequestBody VigenereBrutModel model){
        return cryptoService.bruteForceVigenere(model);
    }
    @PostMapping("/rsa/encrypt")
    public String EncryptRSA(@RequestBody InputModel model) throws Exception {
        return cryptoService.RSAEncrypt(model);
    }


    @PostMapping("/rsa/decrypt")
    public String DeCryptRSA(@RequestBody InputModel model) throws Exception {
        return cryptoService.RSADecrypt(model);
    }
    @PostMapping("/vigenere/encrypt")
    public String EncryptVigenere(@RequestBody VigenereModel model) throws Exception {
        return cryptoService.VigenereEncrypt(model);
    }
    @PostMapping("/vigenere/decrypt")
    public String DeCryptVigenere(@RequestBody VigenereModel model) throws Exception {
        return cryptoService.ViginereDecrypt(model);
    }
}
