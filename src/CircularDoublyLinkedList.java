/**
 * Your implementation of a CircularDoublyLinkedList
 *
 * @author Julia Neuman
 * @version 1.0
 */
public class CircularDoublyLinkedList<T> implements LinkedListInterface<T> {

    // Do not add new instance variables.
    private LinkedListNode<T> head;
    private int size = 0;

    /**
     * Creates an empty circular doubly-linked list.
     */
    public CircularDoublyLinkedList() {

    }

    /**
     * Creates a circular doubly-linked list with
     * {@code data} added to the list in order.
     * @param data The data to be added to the LinkedList.
     * @throws java.lang.IllegalArgumentException if {@code data} is null or any
     * item in {@code data} is null.
     */
    public CircularDoublyLinkedList(T[] data) {

        if (data == null) {
            throw new java.lang.IllegalArgumentException("Cannot insert "
                    + "null data");
        }
        for (int i = 0; i < data.length; i++) {

            addToBack(data[i]);
        }
    }

    @Override
    public void addAtIndex(int index, T data) {
        if (index < 0 || index > size) {
            throw new java.lang.IndexOutOfBoundsException("Index must be "
                    + "greater than zero and less than the size of the "
                    + "Linked List.");
        }
        if (data == null) {
            throw new java.lang.IllegalArgumentException("Cannot insert "
                + "null data.");
        }

        if (index == 0) {
            addToFront(data);
        } else {
            LinkedListNode<T> current = head.getNext();
            int currIndex = 1;
            while (currIndex != index) {
                current = current.getNext();
                currIndex++;
            }

            LinkedListNode<T> newNode = new LinkedListNode<T>(data,
                    current.getPrevious(), current);
            current.getPrevious().setNext(newNode);
            current.setPrevious(newNode);
            size++;



        }
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new java.lang.IndexOutOfBoundsException("Index must be "
                    + "greater than zero and less than the "
                    + "size of the Linked List.");
        }
        if (index == 0) {
            return this.head.getData();
        }
        if (index == (size - 1)) {
            return this.head.getPrevious().getData();
        }
        LinkedListNode<T> current = this.head;
        int currIndex = 0;
        while (currIndex != index) {
            current = current.getNext();
            currIndex++;
        }
        return current.getData();

    }

    @Override
    public T removeAtIndex(int index) {
        if (index < 0 || index >= size) {
            throw new java.lang.IndexOutOfBoundsException("Index must be "
                    + "greater than zero and less than the "
                    + "size of the Linked List.");
        }
        if (index == 0) {
            return removeFromFront();
        }
        if (index == (size - 1)) {
            return removeFromBack();
        }
        LinkedListNode<T> current = head.getNext();
        int currIndex = 1;
        while (currIndex != index) {
            current = current.getNext();
            currIndex++;
        }
        T data = current.getData();
        current.getPrevious().setNext(current.getNext());
        current.getNext().setPrevious(current.getPrevious());
        size--;
        return data;
    }

    @Override
    public void addToFront(T data) {
        if (data == null) {
            throw new java.lang.IllegalArgumentException("Cannot insert "
            + "null data.");
        }
        if (isEmpty()) {
            LinkedListNode<T> newNode = new LinkedListNode<T>(data);
            newNode.setNext(newNode);
            newNode.setPrevious(newNode);
            this.head = newNode;
        } else {

            LinkedListNode<T> previousNode = this.head.getPrevious();
            if (previousNode == null) {
                previousNode = this.head;
            }
            LinkedListNode<T> newNode = new LinkedListNode<T>(data,
                    previousNode, this.head);

            previousNode.setNext(newNode);
            this.head.setPrevious(newNode);
            this.head = newNode;
        }
        size++;
    }

    @Override
    public void addToBack(T data) {
        if (data == null) {
            throw new java.lang.IllegalArgumentException("Cannot insert "
            + "null data.");
        }
        if (isEmpty()) {
            addToFront(data);
        } else {
            LinkedListNode<T> previousNode = this.head.getPrevious();
            LinkedListNode<T> newNode = new LinkedListNode<T>(data,
                    previousNode, this.head);
            this.head.setPrevious(newNode);
            previousNode.setNext(newNode);
            size++;
        }

    }

    @Override
    public T removeFromFront() {
        if (isEmpty()) {
            return null;
        }
        if (size == 1) {
            T data = this.head.getData();
            this.head = null;
            size--;
            return data;

        }
        LinkedListNode<T> previousNode = this.head.getPrevious();
        LinkedListNode<T> nextNode = this.head.getNext();

        T data = head.getData();
        previousNode.setNext(nextNode);
        nextNode.setPrevious(previousNode);
        this.head = nextNode;
        size--;
        return data;
    }

    @Override
    public T removeFromBack() {
        if (isEmpty()) {
            return null;
        }
        LinkedListNode<T> lastNode = this.head.getPrevious();
        LinkedListNode<T> prevNode = lastNode.getPrevious();

        T data = lastNode.getData();
        prevNode.setNext(this.head);
        this.head.setPrevious(prevNode);
        size--;
        return data;

    }

    @Override
    public int removeFirstOccurrence(T data) {
        if (data == null) {
            throw new java.lang.IllegalArgumentException("Data cannot "
                    + "be null.");
        }
        LinkedListNode<T> current = this.head;
        int index = 0;
        while (index < size) {
            if (current.getData().equals(data)) {
                if (index == 0) {
                    removeFromFront();
                } else if (index == (size - 1)) {
                    removeFromBack();
                } else {
                    current.getPrevious().setNext(current.getNext());
                    current.getNext().setPrevious(current.getPrevious());
                    size--;
                }
                return index;
            }
            current = current.getNext();
            index++;
        }
        throw new java.util.NoSuchElementException("The element is "
                + "not in the linked list");
    }

    @Override
    public boolean removeAllOccurrences(T data) {
        if (data == null) {
            throw new java.lang.IllegalArgumentException("Data cannot "
                    + "be null.");
        }
        LinkedListNode<T> current = this.head;
        int index = 0;
        boolean removed = false;
        while (index < size()) {
            if (current.getData().equals(data)) {
                if (index == 0) {
                    removeFromFront();
                    index = index - 1;
                } else if (index == (size() - 1)) {
                    removeFromBack();
                    return true;
                } else {

                    current.getPrevious().setNext(current.getNext());
                    current.getNext().setPrevious(current.getPrevious());
                    index--;
                    size--;
                }
                removed = true;

            }
            current = current.getNext();
            index++;

        }
        return removed;
    }

    @Override
    public Object[] toArray() {
        Object[] nodeArray = new Object[size];
        LinkedListNode<T> current = head;
        int index = 0;
        while (index < size) {
            nodeArray[index] = current.getData();
            current = current.getNext();
            index++;
        }
        return nodeArray;

    }


    @Override
    public boolean isEmpty() {
        return (size() == 0);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        head = null;
        size = 0;

    }

    /* DO NOT MODIFY THIS METHOD */
    @Override
    public LinkedListNode<T> getHead() {
        return head;
    }
}
