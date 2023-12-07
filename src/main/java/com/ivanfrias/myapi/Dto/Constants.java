package com.ivanfrias.myapi.Dto;

import org.springframework.stereotype.Component;

@Component
public class Constants {

   public final String ACCESS_TOKEN_SECRET = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9";
   public final Long ACCESS_TOKEN_VALIDITY_SECONDS = 2_592_000L;
   public final String EMAIL_SENDER = "futapi2023@gmail.com";
   public final String EMAIL_SENDER_APLICATION_PASS = "snmkbvrmnavogeby";
   public final String EMAIL_SENDER_PASS = "mnJn27nch81,a1a";
   public final String EMAIL_HOST = "smtp.office365.com";
   public final int EMAIL_PORT = 587;

   // ********************* STATUS CODES *********************

   //NAME
   public static final String LONG_NAME = "El nombre es demasiado largo";
   public static final String SHORT_NAME = "El nombre es demasiado corto";
   public static final String EMPTY_NAME = "El nombre no puede estar vacío";
   public static final String SPECIAL_CHARACTER_NAME = "El nombre tiene caracteres no permitidos. Solo son válidos caracteres alfabéticos, espacios y guión.";
   // LASTNAME
   public static final String LONG_LASTNAME = "El apellido es demasiado largo";
   public static final String SHORT_LASTNAME = "El apellido es demasiado corto";
   public static final String EMPTY_LASTNAME = "El apellido no puede estar vacío";
   public static final String SPECIAL_CHARACTER_LASTNAME = "El apellido tiene caracteres no permitidos. Solo son válidos caracteres alfabéticos, espacios y guión.";
   // TEAM
   public static final String LONG_TEAM_NAME = "El nombre es demasiado largo";
   public static final String SHORT_TEAM_NAME = "El nombre es demasiado corto";
   public static final String EMPTY_TEAM_NAME = "El nombre no puede estar vacío";
   public static final String SPECIAL_CHARACTER_TEAM_NAME = "El nombre tiene caracteres no permitidos. Solo son válidos caracteres alfabéticos, espacios y guión.";
   public static final String NOT_UNIQUE_TEAMNAME = "El nombre del equipo ya está registrado";
   // EMAIL
   public static final String LONG_EMAIL = "El email es demasiado largo";
   public static final String EMPTY_EMAIL = "El nombre no puede estar vacío";
   public static final String SPECIAL_CHARACTER_EMAIL = "El email tine que tener el siguiente formato: 'oneuser@user.com'";
   public static final String EMAIL_ERROR = "No se ha podido enviar el correo electronico";
   public static final String EMAIL_NOT_UNIQUE = "El correo electrónico ya está registrado";
   // CREATE PLAYER
   public static final String EMPTY_MARKET_VALUE = "El market value no puede estar vacio";
   public static final String TO_SHORT_MARKET_VALUE = "El valor de mercado es demasiado bajo, debe ser mayor o igual a 100.000";
   public static final String TO_LONG_MARKET_VALUE = "El valor de mercado es demasiado alto, debe ser menor o igual a 50.000.000";
   public static final String NOT_FOUND_POSITION = "La posicion no es correcta, (goalkeeper, defender, midfielder, striker)";
   public static final String INCORRECT_NUMBER = "El dorsal debe estar entre 1-99";
   public static final String INCORRECT_COUNTRY = "El pais no es correcto, debes escribirlo en inglés.";
}

