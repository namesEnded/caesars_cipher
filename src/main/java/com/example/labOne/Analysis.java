package com.example.labOne;

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Analysis {
    private static String refText = "";
    private static String ciphertext = "";
    private static Map<Character,Integer> sortedRefTextMap;
    private static Map<Character,Integer> sortedCipherTextMap;

    public static boolean checkBoundaries(char selectedChar)
    {
        int charCode = (int) selectedChar;
        return ((charCode>= 65) & (charCode <= 90)) || ((charCode >= 97) & (charCode <= 122)) ||
                (((charCode >= 1040) & (charCode <= 1071)) || (charCode==1025)) ||
                (((charCode >= 1072) & (charCode <= 1103)) || (charCode==1105));
    }

    public static Map<Character, Integer> getSortedRefTextMap() {
        return sortedRefTextMap;
    }

    public static Map<Character, Integer> getSortedCipherTextMap() {
        return sortedCipherTextMap;
    }

    public static void setRefText(String refText) {
        Analysis.refText = refText;
    }

    public static void setCiphertext(String ciphertext) {
        Analysis.ciphertext = ciphertext;
    }

    public static  Map<Character, Integer>  sortMap (Map<Character,Integer> sortedMap) {
        Map<Character, Integer> sortedFrequenciesRefText = sortedMap.entrySet().stream()
                .sorted(Comparator.comparingInt(e -> -e.getValue()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (a, b) -> { throw new AssertionError(); },
                        LinkedHashMap::new
                ));
        return sortedFrequenciesRefText;
    }

    public static void start() {
//        System.out.println(refText + "/" + refText + "\n");
//        System.out.println(ciphertext + "/"  + ciphertext + "\n");
        setCiphertext(ciphertext.toUpperCase());
        setRefText(refText.toUpperCase());
        Map<Character,Integer> frequenciesRefText = new HashMap<>();
        Map<Character,Integer> frequenciesCipherText = new HashMap<>();
        for (char ch : refText.toCharArray())
            if (checkBoundaries(ch)) frequenciesRefText.put(ch, frequenciesRefText.getOrDefault(ch, 0) + 1);
        sortedRefTextMap = sortMap(frequenciesRefText);
        for (char ch : ciphertext.toCharArray())
            if (checkBoundaries(ch))  frequenciesCipherText.put(ch, frequenciesCipherText.getOrDefault(ch, 0) + 1);
        sortedCipherTextMap =sortMap(frequenciesCipherText);
//        System.out.println("sortedCipherTextMap\n");
//        for (var entry : sortedCipherTextMap.entrySet()) {
//            System.out.println(entry.getKey() + "/" + entry.getValue() + "\n");
//        }
//        System.out.println("sortedRefTextMap\n");
//        for (var entry : sortedRefTextMap.entrySet()) {
//            System.out.println(entry.getKey() + "/" + entry.getValue() + "\n");
//        }
    }

}
