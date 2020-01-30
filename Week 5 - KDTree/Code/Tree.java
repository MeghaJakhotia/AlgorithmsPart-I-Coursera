private class Tree<Key extends Comparable<Key>, Value> {
    private node root;

    private class node {
        private Key key;
        private node left, right;
        private Value value;

        public node(Key key, Value value) {
            this.key = key;
            this.value = value;
        }
    }

    public void add(Key key, Value value) {

    }

    public boolean contains(Key key) {

    }

    public int size() {

    }

    public boolean isEmpty() {

    }


}
