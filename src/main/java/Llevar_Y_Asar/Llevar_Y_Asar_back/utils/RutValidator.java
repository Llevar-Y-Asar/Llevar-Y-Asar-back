package Llevar_Y_Asar.Llevar_Y_Asar_back.utils;

/**
 * Validador de RUT Chileno
 */
public class RutValidator {
    
    /**
     * Valida un RUT chileno en formato 12.345.678-K o 12345678K
     * @param rut RUT a validar
     * @return true si es válido, false si no
     */
    public static boolean esValido(String rut) {
        if (rut == null || rut.isEmpty()) {
            return false;
        }
        
        // Eliminar puntos y espacios
        rut = rut.replaceAll("[.-]", "").toUpperCase();
        
        // Validar formato: números + dígito verificador
        if (!rut.matches("^\\d{7,8}[0-9K]$")) {
            return false;
        }
        
        String numero = rut.substring(0, rut.length() - 1);
        String digito = rut.substring(rut.length() - 1);
        
        // Calcular dígito verificador
        String digitoCalculado = calcularDigitoVerificador(numero);
        
        return digito.equals(digitoCalculado);
    }
    
    /**
     * Calcula el dígito verificador de un RUT
     * @param numero Número del RUT sin dígito verificador
     * @return Dígito verificador (0-9 o K)
     */
    public static String calcularDigitoVerificador(String numero) {
        int suma = 0;
        int multiplicador = 2;
        
        // Recorrer el número de derecha a izquierda
        for (int i = numero.length() - 1; i >= 0; i--) {
            int digito = Character.getNumericValue(numero.charAt(i));
            suma += digito * multiplicador;
            multiplicador++;
            
            if (multiplicador > 7) {
                multiplicador = 2;
            }
        }
        
        int residuo = 11 - (suma % 11);
        
        if (residuo == 11) {
            return "0";
        } else if (residuo == 10) {
            return "K";
        } else {
            return String.valueOf(residuo);
        }
    }
    
    /**
     * Formatea un RUT al formato XX.XXX.XXX-X
     * @param rut RUT sin formato
     * @return RUT formateado
     */
    public static String formatear(String rut) {
        if (rut == null || rut.isEmpty()) {
            return "";
        }
        
        rut = rut.replaceAll("[.-]", "");
        
        if (rut.length() < 8) {
            return rut;
        }
        
        String numero = rut.substring(0, rut.length() - 1);
        String digito = rut.substring(rut.length() - 1);
        
        // Agregar puntos: XX.XXX.XXX
        StringBuilder sb = new StringBuilder();
        int contador = 0;
        for (int i = numero.length() - 1; i >= 0; i--) {
            if (contador == 3 || contador == 6) {
                sb.insert(0, ".");
            }
            sb.insert(0, numero.charAt(i));
            contador++;
        }
        
        sb.append("-").append(digito);
        return sb.toString();
    }
}
