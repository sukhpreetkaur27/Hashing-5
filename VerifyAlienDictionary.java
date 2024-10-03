import java.util.HashMap;
import java.util.Map;

// LC 953
public class VerifyAlienDictionary {

    /**
     * compare to words till a differentiating character is found.
     * search for these differentiating characters in the dictionary order.
     * to optimise hash the dictionary so that search = O(1)
     * <p>
     * TC: O(n * s); s= average string length, n = # of words
     * SC: O(1), assuming 26 chars
     * <p>
     * special case:
     * abc
     * ab
     * <p>
     * here, min. length to compare is of the next word(ab).
     * therefore, we'll never get a differentiating char --> giving a false positive
     * <p>
     * so in such a case check if word1.length > word2.length
     *
     * @param words
     * @param order
     * @return
     */
    public boolean isAlienSorted(String[] words, String order) {
        // hash the dictionary
        Map<Character, Integer> dictionary = new HashMap<>();
        for (int i = 0; i < order.length(); i++) {
            dictionary.put(order.charAt(i), i);
        }

        for (int i = 0; i < words.length - 1; i++) {
            String word1 = words[i];
            String word2 = words[i + 1];
            int ptr = 0;
            boolean diff = false;
            // pick the min. length for comparison
            int len = Math.min(word1.length(), word2.length());
            while (ptr < len) {
                char ch1 = word1.charAt(ptr);
                char ch2 = word2.charAt(ptr);
                if (ch1 != ch2) {
                    diff = true;
                    if (dictionary.get(ch1) > dictionary.get(ch2)) {
                        // not sorted
                        return false;
                    }
                    break;
                }
                ptr++;
            }
            // if no diff is found, word1.length <= word2.length
            if (!diff && word1.length() > word2.length()) {
                // not sorted
                return false;
            }
        }
        // sorted
        return true;
    }
}
