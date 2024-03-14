package SENG355_Assignment12.src;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;
import java.util.regex.*;
import edu.stanford.nlp.pipeline.*;
import edu.stanford.nlp.ling.*;

public class TextProcessing {
	
    private static Map<String, String> lemmatizationMap;

    private static Set<String> stopWords = new HashSet<>(Arrays.asList(
    	    "i", "me", "my", "myself", "we", "our", "ours", "ourselves", "you", "your", "yours", "yourself", "yourselves",
    	    "he", "him", "his", "himself", "she", "her", "hers", "herself", "it", "its", "itself", "they", "them", "their",
    	    "theirs", "themselves", "what", "which", "who", "whom", "this", "that", "these", "those", "am", "is", "are", "was",
    	    "were", "be", "been", "being", "have", "has", "had", "having", "do", "does", "did", "doing", "a", "an", "the", "and",
    	    "but", "if", "or", "because", "as", "until", "while", "of", "at", "by", "for", "with", "about", "against", "between",
    	    "into", "through", "during", "before", "after", "above", "below", "to", "from", "up", "down", "in", "out", "on", "off",
    	    "over", "under", "again", "further", "then", "once", "here", "there", "when", "where", "why", "how", "all", "any",
    	    "both", "each", "few", "more", "most", "other", "some", "such", "no", "nor", "not", "only", "own", "same", "so",
    	    "than", "too", "very", "s", "t", "can", "will", "just", "don", "should", "now"
    	));
    
    static {
        // Verbs
    	lemmatizationMap = new HashMap<>();
        lemmatizationMap.put("running", "run");
        lemmatizationMap.put("ran", "run");
        lemmatizationMap.put("eaten", "eat");
        lemmatizationMap.put("ate", "eat");
        lemmatizationMap.put("sleeping", "sleep");
        lemmatizationMap.put("slept", "sleep");
        lemmatizationMap.put("driven", "drive");
        lemmatizationMap.put("drove", "drive");
        lemmatizationMap.put("writing", "write");
        lemmatizationMap.put("wrote", "write");
        lemmatizationMap.put("written", "write");
        lemmatizationMap.put("seeing", "see");
        lemmatizationMap.put("saw", "see");
        lemmatizationMap.put("seen", "see");
        lemmatizationMap.put("going", "go");
        lemmatizationMap.put("went", "go");
        lemmatizationMap.put("lying", "lie");
        lemmatizationMap.put("lay", "lie");
        lemmatizationMap.put("lying", "lay");
        lemmatizationMap.put("laid", "lay");

        // Nouns
        lemmatizationMap.put("mice", "mouse");
        lemmatizationMap.put("men", "man");
        lemmatizationMap.put("women", "woman");
        lemmatizationMap.put("children", "child");
        lemmatizationMap.put("teeth", "tooth");
        lemmatizationMap.put("feet", "foot");
        lemmatizationMap.put("people", "person");
        lemmatizationMap.put("leaves", "leaf");
        lemmatizationMap.put("lives", "life");
        lemmatizationMap.put("geese", "goose");
    }

    public static void main(String[] args) {
        String filePath = "C:\\Users\\snave\\eclipse-FerrisSpring2024\\SENG355_Assignment12\\src\\SENG355_Assignment12\\src\\longText"; // Path to the text file

        Properties props = new Properties();
        props.setProperty("annotators", "tokenize,ssplit,pos");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

        try {
            String content = new String(Files.readAllBytes(Paths.get(filePath)), StandardCharsets.UTF_8);
            List<String> sentences = segmentSentences(content);

            for (String sentence : sentences) {
                System.out.println("Sentence: " + sentence);
                List<String> tokens = tokenizeSentence(sentence);
                List<String> lemmatizedTokens = new ArrayList<>();
                List<String> removedTokens = new ArrayList<>();

                for (String token : tokens) {
                    String lemma = lemmatize(token.toLowerCase()); 
                    if (!stopWords.contains(lemma)) {
                        lemmatizedTokens.add(lemma);
                    } else {
                        removedTokens.add(token);
                    }
                }

                System.out.println("Tokens (lemmatized, non-stop words): " + lemmatizedTokens);
                System.out.println("Removed Tokens (stop words): " + removedTokens);
                System.out.println(); 
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }
    
    private static String lemmatize(String token) {
        return lemmatizationMap.getOrDefault(token, token); 
    }

    
    private static List<String> segmentSentences(String text) {
        List<String> sentences = new ArrayList<>();
        Pattern pattern = Pattern.compile("[^.!?]+"); 
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            sentences.add(matcher.group().trim());
        }
        return sentences;
    }

    
    private static List<String> tokenizeSentence(String sentence) {
        List<String> tokens = new ArrayList<>();
        Pattern pattern = Pattern.compile("\\b\\w+\\b"); 
        Matcher matcher = pattern.matcher(sentence);
        while (matcher.find()) {
            tokens.add(matcher.group());
        }
        return tokens;
    }
}
