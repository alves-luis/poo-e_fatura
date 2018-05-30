
/**
 * Enumeration class Districts - all the districts in Portugal
 *
 * @author Zé, Miguel e Luís
 * @version 1.0
 */
public enum Districts {
    AVEIRO, BEJA, BRAGA, BRAGANCA, CASTELO_BRANCO, COIMBRA,
    EVORA, FARO, GUARDA, LEIRIA, LISBOA, PORTALEGRE,
    PORTO, SANTAREM, SETUBAL, VIANA_DO_CASTELO, VILA_REAL, VISEU;

    public static boolean isInland(int district) {
      switch(Districts.values()[district]) {
        case BRAGANCA: return true;
        case CASTELO_BRANCO: return true;
        case EVORA: return true;
        case GUARDA: return true;
        case PORTALEGRE: return true;
        case SANTAREM: return true;
        case VILA_REAL: return true;
        case VISEU: return true;
        default: return false;
      }
    }
}
