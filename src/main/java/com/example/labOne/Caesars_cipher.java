package com.example.labOne;

import java.util.HashMap;

public class Caesars_cipher
{
    private static int key = 7;
//    public static char[] cyrillicAlphabet = new char[]{'а', 'б', 'в', 'г', 'д', 'е', 'ё', 'ж', 'з', 'и', 'й', 'к', 'л', 'м', 'н', 'о', 'п', 'р', 'с', 'т', 'у', 'ф', 'х', 'ц', 'ч', 'ш', 'щ', 'ъ', 'ы', 'ь', 'э', 'ю', 'я'};
    public static String cyrillicAlphabetString = "абвгдеёжзийклмнопрстуфхцчшщъыьэюя";

//    public static int searchWord(char selectedChar)
//    {
//        for (int i = 0; i < cyrillicAlphabet.length; i++)
//        {
//            if (selectedChar == cyrillicAlphabet[i]) return i;
//        }
//        return -1;
//    }

    public static void setKey(int keyValue) { key = keyValue; }

    public static char checkBoundaries(char selectedChar)
    {
        int charCode = (int) selectedChar;
        if ((charCode>= 65) & (charCode <= 90)) return 'A';
        else if ((charCode >= 97) & (charCode <= 122))  return 'a';
        else if (((charCode >= 1040) & (charCode <= 1071)) || (charCode==1025))   return 'А';
        else if (((charCode >= 1072) & (charCode <= 1103)) || (charCode==1105)) return 'а';
        return 'N';
    }

    public static char offsetCharUnicode(Integer numberOfChars, Integer codeOfAChar, char character)
    {
        int localKey = Math.abs(key);
        int originalPos = character - codeOfAChar;
        int newPos = (key > 0) ? ((originalPos + localKey) % numberOfChars) :
                ((originalPos + (numberOfChars - (localKey % numberOfChars))) % numberOfChars);
        return ((char) (codeOfAChar + newPos));
    }

    public static char offsetCharArray(char character)
    {
        int localKey = Math.abs(key);
        char lowerChar = Character.toLowerCase(character);
        int originalPos = cyrillicAlphabetString.indexOf(lowerChar);
        int newPos = (key > 0 ) ? ((originalPos + localKey ) % cyrillicAlphabetString.length()) :
                ((originalPos + (cyrillicAlphabetString.length() - (localKey % cyrillicAlphabetString.length()))) % cyrillicAlphabetString.length());
        return cyrillicAlphabetString.charAt(newPos);
    }

    public static String doEncryption(String input)
    {
        HashMap<Character, Integer> mapS = new HashMap<>();
        mapS.put('A', 26);
        mapS.put('a', 26);
        mapS.put('А', 33);
        mapS.put('а', 33);

        StringBuilder buf = new StringBuilder();
        StringBuilder result = new StringBuilder();
        buf.append(input);
        for (char character : buf.toString().toCharArray())
        {
            switch (checkBoundaries(character))
            {
                case 'A':
                    result.append(offsetCharUnicode(mapS.get('A'),(int)'A',character));
                    break;
                case 'a':
                    result.append(offsetCharUnicode(mapS.get('a'),(int)'a',character));
                    break;
                case 'А':
                    result.append(Character.toUpperCase(offsetCharArray(character)));
                    break;
                case 'а':
                    result.append(Character.toLowerCase(offsetCharArray(character)));
                    break;
                case 'N':
                    result.append(character);
                    break;
                default:
                    break;
            }
            System.out.printf("code = %d, char - %s\n",(int)character, character);

        }
        return result.toString();
    }
}
