package edu.isu.cs.cs3308.structures.impl;

import edu.isu.cs.cs3308.structures.Set;

import java.util.Iterator;
import java.util.NoSuchElementException;

/* used http://robertovormittag.net/how-to-implement-a-simple-hashset-in-java/
* as a guide to implement a hashset and gain a better understanding
* or how they work.
*/
public class HashSet<T> implements Set<T> {
    //list of collided objects
    private static class Entry<T>{
        T key;
        Entry next;
    }

    //HashSetIterator class
    class HashSetIterator implements Iterator {

        private int currentBucket;
        private int previousBucket;
        private Entry<T> currentEntry;
        private Entry previousEntry;

        public HashSetIterator() {
            currentEntry = null;
            previousEntry = null;
            currentBucket = -1;
            previousBucket = -1;
        }

        @Override
        public boolean hasNext() {

            // currentEntry node has next
            if (currentEntry != null && currentEntry.next != null) { return true; }

            // there are still nodes
            for (int index = currentBucket+1; index < buckets.length; index++) {
                if (buckets[index] != null) { return true; }
            }

            // nothing left
            return false;
        }

        @Override
        public T next() {

            previousEntry = currentEntry;
            previousBucket = currentBucket;

            // if either the current or next node are null
            if (currentEntry == null || currentEntry.next == null) {

                // go to next bucket
                currentBucket++;

                // keep going until you find a bucket with a node
                while (currentBucket < buckets.length &&
                        buckets[currentBucket] == null) {
                    // go to next bucket
                    currentBucket++;
                }

                // if bucket array index still in bounds
                // make it the current node
                if (currentBucket < buckets.length) {
                    currentEntry = buckets[currentBucket];
                }
                // otherwise there are no more elements
                else {
                    throw new NoSuchElementException();
                }
            }
            // go to the next element in bucket
            else {

                currentEntry = currentEntry.next;
            }

            // return the element in the current node
            return currentEntry.key;

        }

    }

    //array of Entry instances
    private Entry[] buckets;
    //Size of the set
    private int size;

    //constructor, creates buckets based on the passed in capacity
    public HashSet(int capacity){
        buckets = new Entry[capacity];
        size = 0;
    }

    //hashFunction
    private int hashFunction(int hashCode){
        int index = hashCode;
        if (index < 0){ index = -index;}
        return index % buckets.length;
    }

    //adds an element to the set if the element doesn't already exist
    public void add(T e){
        int index = hashFunction(e.hashCode());
        Entry current = buckets[index];

        while (current != null){
            if (current.key.equals(e)){

            }
            else {
                current = current.next;
            }
        }

        Entry entry = new Entry();
        entry.key = e;
        entry.next = buckets[index];
        buckets[index] = entry;
        size++;
    }

    //removes an element from the set if the element is
    //contained in the set
    public void remove(T e){
        int index = hashFunction(e.hashCode());
        Entry current = buckets[index];
        Entry previous = null;

        while (current != null){
            if (current.key.equals(e)){
                if (previous == null){
                    buckets[index] = current.next;
                } else {
                    previous.next = current.next;
                }
                size --;
            }

            previous = current;
            current = current.next;
        }
    }

    //checks if the set contains a given element
    public boolean contains(T e){
        int index = hashFunction(e.hashCode());
        Entry current = buckets[index];

        while (current != null){
            if (current.key.equals(e)){
                return true;
            } else {
                current = current.next;
            }

        }
        return false;
    }

    public Iterator<T> iterator(){
        return new HashSetIterator();
    }

    /**
     * Check if the set is empty
     *
     * @return true if there are no items in the set, false otherwise
     */
    public boolean isEmpty(){
        if (size == 0){
            return true;
        } else {
            return false;
        }
    }


    /**
     * Adds all items from Set s to this set, if those items are not already in this set.
     * This is equivalent to the set union operation.
     *
     * @param s Set containing the items to be added to this set.
     */
    public void addAll(Set<T> s){

        while (s.iterator().hasNext()){
            add(s.iterator().next());
        }
    }

    /**
     * Removes all items from this set, which are not items contained in the provided set.
     * This is equivalent to the set intersection operation.
     *
     * @param s The set defining which items are to be kept in this set.
     */
    public void retainAll(Set<T> s){
        while (iterator().hasNext()){
            if (!s.contains(iterator().next())){
                remove(iterator().next());
            }
        }
    }

    /**
     * Removes all items found in the provided set from this set.
     * This is equivalent to the set difference operation.
     *
     * @param s Set defining the items to be removed from this set.
     */
    public void removeAll(Set<T> s) {
        while (iterator().hasNext()){
            if (s.contains(iterator().next())){
                remove(iterator().next());
            }
        }
    }
}
