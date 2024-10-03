import java.util.*;
// LC 269
public class AlienDictionary {

    /**
     * NOTE: THE WORDS are sorted, not each individual word is sorted.
     * <p>
     * special case:
     * {abc
     * ab}
     * <p>
     * here, we need to check if no differentiating char found and word1.length > word2.length ---> return ""
     * <p>
     * WE have to prepare a directed graph such that if u comes before v --> there is an edge from u to v
     * Apply Topo sort (BFS) to get the lexicographic ordering and verify no cycles are found.
     * <p>
     * TC: O(n * s) + O(v + e), here, v = 26
     * SC: O(v + e) to build the adjacency list + O(v) for topo sort which is constant
     *
     * @param words
     * @return
     */
    public String alienOrder(String[] words) {
        // not required
        /*
        eg: aba
        it will automatically be handled by in-degree = 0
        and will be part of Topo Sort
         */
//        if (words.length == 1) {
//            return words[0];
//        }
        // at max 26 unique chars possible
        List<List<Integer>> adjList = new ArrayList<>();
        for (int i = 0; i < 26; i++) {
            adjList.add(new ArrayList<>());
        }
        // by default mark in-degrees as -1 for chars not present in words
        // mark in-degree as 0 for other chars present in words
        int[] inDegrees = new int[26];
        Arrays.fill(inDegrees, -1);
        for (String w : words) {
            for (char c : w.toCharArray()) {
                inDegrees[c - 'a'] = 0;
            }
        }

        // prepare adj list for differentiating characters ONLY
        // by comparing 2 words
        for (int j = 0; j < words.length - 1; j++) {
            String word1 = words[j];
            String word2 = words[j + 1];
            int len = Math.min(word1.length(), word2.length());
            boolean diff = false;
            for (int i = 0; i < len; i++) {
                int u = word1.charAt(i) - 'a';
                int v = word2.charAt(i) - 'a';
                // differentiating character decides an edge from u -> v
                if (u != v) {
                    adjList.get(u).add(v);
                    // in-degree
                    inDegrees[v]++;
                    diff = true;
                    break;
                }
            }
            /*
            special case:
              {abc
               ab}
             */
            if (!diff && word1.length() > word2.length()) {
                return "";
            }
        }

        // for topo sort, we need to know the unique chars present
        int uniqueCharCount = 0;
        // add to queue
        Deque<Integer> queue = new ArrayDeque<>();
        for (int i = 0; i < 26; i++) {
            if (inDegrees[i] < 0) {
                // char not present in words
                continue;
            }
            if (inDegrees[i] == 0) {
                // mark as source for BFS
                queue.offer(i);
            }
            uniqueCharCount++;
        }

        StringBuilder topoSort = new StringBuilder();
        while (!queue.isEmpty()) {
            int curr = queue.poll();
            topoSort.append((char) (curr + 'a'));

            for (int neighbour : adjList.get(curr)) {
                inDegrees[neighbour]--;
                if (inDegrees[neighbour] == 0) {
                    queue.offer(neighbour);
                }
            }
        }

        if (topoSort.length() == uniqueCharCount) {
            return topoSort.toString();
        }
        return "";
    }
}
