public class Node {
    // Instance variables
    private boolean isWord;
    private Node[] next;
    // Constructor
    public Node(boolean isWord, Node[] next){
        this.isWord = isWord;
        this.next = next;
    }
    public boolean isWord(){
        return isWord;
    }
    public void setWord(){
        isWord = true;
    }
    public Node[] getNext(){
        return next;
    }
}

