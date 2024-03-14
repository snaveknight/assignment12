package SENG355_Assignment12.src;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;
import java.util.regex.*;
import edu.stanford.nlp.pipeline.*;
import edu.stanford.nlp.ling.*;

public class TextProcessing {
	
    private Map<String, String> lemmatizationMap;
    private Set<String> stopWords;
    private String filePath;

    public TextProcessing(String filePath) {
        this.filePath = filePath;
        initializeStopWords();
        initializeLemmatizationMap();
    }

    private void initializeStopWords() {
    	 stopWords = new HashSet<>(Arrays.asList(
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
    }
    
    private void initializeLemmatizationMap() {
        lemmatizationMap = new HashMap<>();
       
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

    public void processText() {
        try {
            String content = new String(Files.readAllBytes(Paths.get(filePath)), StandardCharsets.UTF_8);
            List<String> sentences = segmentSentences(content);

            for (String sentence : sentences) {
                System.out.println("Sentence: " + sentence);
                List<String> tokens = tokenizeSentence(sentence);
                List<String> lemmatizedTokens = lemmatizeTokens(tokens);
                List<String> filteredTokens = removeStopWords(lemmatizedTokens);

                System.out.println("Tokens (lemmatized, non-stop words): " + filteredTokens);
                System.out.println();
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    private List<String> lemmatizeTokens(List<String> tokens) {
        List<String> lemmatizedTokens = new ArrayList<>();
        for (String token : tokens) {
            String lemma = lemmatize(token.toLowerCase());
            lemmatizedTokens.add(lemma);
        }
        return lemmatizedTokens;
    }

    private List<String> removeStopWords(List<String> tokens) {
        List<String> filteredTokens = new ArrayList<>();
        for (String token : tokens) {
            if (!stopWords.contains(token)) {
                filteredTokens.add(token);
            }
        }
        return filteredTokens;
    }

    private String lemmatize(String token) {
        return lemmatizationMap.getOrDefault(token, token);
    }

    private List<String> segmentSentences(String text) {
        List<String> sentences = new ArrayList<>();
        Pattern pattern = Pattern.compile("[^.!?]+");
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            sentences.add(matcher.group().trim());
        }
        return sentences;
    }

    private List<String> tokenizeSentence(String sentence) {
        List<String> tokens = new ArrayList<>();
        Pattern pattern = Pattern.compile("\\b\\w+\\b");
        Matcher matcher = pattern.matcher(sentence);
        while (matcher.find()) {
            tokens.add(matcher.group());
        }
        return tokens;
    }

    public static void main(String[] args) {
        TextProcessing processor = new TextProcessing("your_file_path_here");
        processor.processText();
    }
}
