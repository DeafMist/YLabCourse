package lesson3.task1;

public class TransliteratorImpl implements Transliterator {
    @Override
    public String transliterate(String source) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < source.length(); i++) {
            sb.append(cyrToLat(source.charAt(i)));
        }
        return sb.toString();
    }

    private String cyrToLat(char letter) {
        switch (letter) {
            case 'А': return "A";
            case 'Б': return "B";
            case 'В': return "V";
            case 'Г': return "G";
            case 'Д': return "D";
            case 'Е':
            case 'Ё':
            case 'Э':
                return "E";
            case 'Ж': return "ZH";
            case 'З': return "Z";
            case 'И': return "I";
            case 'Й': return "I";
            case 'К': return "K";
            case 'Л': return "L";
            case 'М': return "M";
            case 'Н': return "N";
            case 'О': return "O";
            case 'П': return "P";
            case 'Р': return "R";
            case 'С': return "S";
            case 'Т': return "T";
            case 'У': return "U";
            case 'Ф': return "F";
            case 'Х': return "KH";
            case 'Ц': return "TS";
            case 'Ч': return "CH";
            case 'Ш': return "SH";
            case 'Щ': return "SHCH";
            case 'Ъ': return "IE";
            case 'Ы': return "Y";
            case 'Ь': return "";
            case 'Ю': return "IU";
            case 'Я': return "IA";
            default: return String.valueOf(letter);
        }
    }
}
