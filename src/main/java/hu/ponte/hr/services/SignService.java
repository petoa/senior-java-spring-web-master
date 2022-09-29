package hu.ponte.hr.services;

import hu.ponte.hr.exception.DigitalSignFailedException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Service
public class SignService {
    public String sign(byte[] data) {
        String digitalSignature;
        try {
            PrivateKey privateKey = getPrivateKey();
            Signature signature = Signature.getInstance("SHA256withRSA");
            signature.initSign(privateKey);
            signature.update(data);
            digitalSignature = Base64.getEncoder().encodeToString(signature.sign());
        } catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException e) {
            throw new DigitalSignFailedException("Digital signing failed.");
        }
        return digitalSignature;
    }

    public Boolean verify(byte[] data, String signatureToVerify) {
        boolean isVerified;
        try {
            Signature signature = Signature.getInstance("SHA256withRSA");
            signature.initVerify(getPublicKey());
            signature.update(data);
            isVerified = signature.verify(Base64.getDecoder().decode(signatureToVerify));
        } catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException e) {
            throw new DigitalSignFailedException("Signature verification failed.");
        }
        return isVerified;
    }

    private PrivateKey getPrivateKey() {
        PrivateKey privateKey;
        try {
            byte[] key = Files.readAllBytes(Paths.get("src/main/resources/config/keys/key.private"));
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(key);
            privateKey = keyFactory.generatePrivate(keySpec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException | IOException e) {
            throw new DigitalSignFailedException("Private key creation failed.");
        }
        return privateKey;
    }

    private PublicKey getPublicKey() {
        PublicKey publicKey;
        try {
            byte[] key = Files.readAllBytes(Paths.get("src/main/resources/config/keys/key.pub"));
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(key);
            publicKey = keyFactory.generatePublic(keySpec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException | IOException e) {
            throw new DigitalSignFailedException("Public key creation failed.");
        }
        return publicKey;
    }
}
