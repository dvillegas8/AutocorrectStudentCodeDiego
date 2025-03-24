public class Trie {
    // Instance variables
    private Node root;
    public Trie(Node root){
        this.root = root;
    }
    // Add String "s" into the Trie
    public void insert(String s){
        Node currentNode = root;
        // For each letter in s
        for(int i = 0; i < s.length(); i++){
            // Check if the next node is null
            if(currentNode.getNext()[s.charAt(i)] == null) {
                // Create new node for "s" letter
                currentNode.getNext()[s.charAt(i)] = new Node(false, new Node[253]);
            }
            currentNode = currentNode.getNext()[s.charAt(i)];
        }
        // CurrentNode = last letter in s, so set isWord to true
        currentNode.setWord();
    }
    // Return false if "s" is not in Trie/dictionary, return true otherwise
    public boolean lookup(String s){
        Node currentNode = root;
        // For each letter in s
        for(int i = 0; i < s.length(); i++){
            // Check if the node is null, return false if true
            if(currentNode.getNext()[s.charAt(i)] == null) {
                return false;
            }
            currentNode = currentNode.getNext()[s.charAt(i)];
        }
        // Check if it is even a word in the dictionary
        if(!currentNode.isWord()){
            return false;
        }
        return true;
    }
}

