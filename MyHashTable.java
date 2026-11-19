public class MyHashTable<K,V>{
    private DLList<V>[] table;
    private MyHashSet<K> keySet;

    // Internal DLList table for V and HashSet for K
    @SuppressWarnings ("unchecked")
    public MyHashTable(){
        table = (DLList<V>[]) new DLList<?>[10000];
        keySet = new MyHashSet<>();
    }

    public void put(K key, V value){
        int index = key.hashCode();
        if (!keySet.contains(key)) {
            table[index] = new DLList<>();
            keySet.add(key);
        }
        table[index].add(value);
    }

    public DLList<V> get(K key){
        // If HashSet contains it, return the value in the table based on key's hashcode
        if (keySet.contains(key)) {
            return table[key.hashCode()];
        }
        return null;
    }

    public MyHashSet<K> keySet(){
        return keySet;
    }
    
    public void remove(K key, V value){
        // Get DLList at the key location
        DLList<V> values = get(key);
        // If exists, remove value, and key if needed
        if (values != null) {
            values.remove(value);
            if (values.size() == 0) {
                remove(key);
            }
        }
    }

    public void remove(K key){
        // If HashSet removes successfully, remove from HashTable
        if (keySet.remove(key)) {
            table[key.hashCode()] = null;
        }
    }
}