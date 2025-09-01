package co.com.crediyarequest.api.handler;

public final class ErrorConstants {

    private ErrorConstants() {
        // Constructor privado para evitar instanciación
    }

    public static final class Codes {
        public static final String VALIDATION = "VAL002";
        public static final String BUSINESS = "BUS001";
        public static final String GENERIC = "GEN001";

        private Codes() {
            // Constructor privado para evitar instanciación
        }
    }

    public static final class Messages {
        public static final String GENERIC_ERROR = "An unexpected error has occurred: ";

        private Messages() {
            // Constructor privado para evitar instanciación
        }
    }
}
