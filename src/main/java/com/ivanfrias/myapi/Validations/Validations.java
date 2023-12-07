package com.ivanfrias.myapi.Validations;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Validations {
    public static int verifyName(String name){
        if (name == null || name.trim().isEmpty()) return 1; // Vacio
        if (!name.matches("^[a-zA-Z\\s\\-]+$")) return 2; // Caracteres no validos
        if(name.length()<2) return 3;
        if(name.length()>50) return 4;
        return 0;
    }

    public static int verifyEmail(String email){
        if (email == null || email.trim().isEmpty()) return 1; // Vacio
        if (!email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) return 2;
        if(email.length()>50) return 3;
        return 0;
    }

    public static int generarCodigo() {
        // Semilla para el generador de números aleatorios
        Random rand = new Random();

        // Límite superior e inferior para el rango de dígitos
        int limiteInferior = (int) Math.pow(10, 6 - 1);
        int limiteSuperior = (int) Math.pow(10, 6) - 1;

        // Genera un número aleatorio dentro del rango
        return rand.nextInt(limiteSuperior - limiteInferior + 1) + limiteInferior;
    }

    public static String encrypt(String txt){
        String txtEncrypted = "";
        try {
            // Crear una instancia de MessageDigest para SHA-256
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            // Obtener el hash de la cadena original
            byte[] hash = digest.digest(txt.getBytes());

            // Convertir el hash a una representación hexadecimal
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            txtEncrypted = hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            System.err.println("Algoritmo no encontrado: " + e.getMessage());
        }
        return txtEncrypted;
    }
}