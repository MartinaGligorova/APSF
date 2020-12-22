package niziilistilab2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Array<E> {

    private E data[]; // declared to be an Object since it would be too
    // complicated with generics
    private int size;

    public Array(int size) {
        data = (E[]) new Object[size];
        this.size = size;
    }

    public void set(int position, E o) {
        if (position >= 0 && position < size)
            data[position] = o;
        else
            System.out.println("Ne moze da se vmetne element na dadenata pozicija");
    }

    public E get(int position) {
        if (position >= 0 && position < size)
            return data[position];
        else
            System.out.println("Ne e validna dadenata pozicija");
        return null;
    }

    public int getLength() {
        return size;
    }

    public int find(E o) {
        for (int i = 0; i < size; i++) {
            if (o.equals(data[i]))
                return i;
        }
        return -1;
    }

    public void insert(int position, E o) {
        // before all we check if position is within range
        if (position >= 0 && position <= size) {
            // first resize the storage array
            E[] newData = (E[]) new Object[size + 1];
            // copy the data prior to the insertion
            for (int i = 0; i < position; i++)
                newData[i] = data[i];
            // insert the new element
            newData[position] = o;
            // move the data after the insertion
            for (int i = position; i < size; i++)
                newData[i + 1] = data[i];
            // replace the storage with the new array
            data = newData;
            size = size + 1;
        }
    }

    public void delete(int position) {
        // before all we check if position is within range
        if (position >= 0 && position < size) {
            // first resize the storage array
            E[] newData = (E[]) new Object[size - 1];
            // copy the data prior to the delition
            for (int i = 0; i < position; i++)
                newData[i] = data[i];
            // move the data after the deletion
            for (int i = position + 1; i < size; i++)
                newData[i - 1] = data[i];
            // replace the storage with the new array
            data = newData;
            size = size - 1;
        }
    }

    public void resize(int newSize) {
        // first resize the storage array
        E[] newData = (E[]) new Object[newSize];
        // copy the data
        int copySize = size;
        if (newSize < size)
            copySize = newSize;
        for (int i = 0; i < copySize; i++)
            newData[i] = data[i];
        // replace the storage with the new array
        data = newData;
        size = newSize;
    }


    public static int brojDoProsek(Array<Integer> niza) {

        //Vashiot kod tuka...

        int suma = 0;
        for (int i = 0; i < niza.getLength(); i++) {
            suma = suma + niza.get(i);
        }
        float prosek = (float) suma / niza.getLength();
        float minRastojanie = Math.abs(niza.get(0) - prosek);
        int elementNajblisku = niza.get(0);
        for (int i = 1; i < niza.getLength(); i++) {
            if ((Math.abs(niza.get(i) - prosek)) < minRastojanie) {
                minRastojanie = Math.abs(niza.get(i) - prosek);
                elementNajblisku = niza.get(i);
            } else if ((Math.abs(niza.get(i) - prosek)) == minRastojanie) {
                minRastojanie = Math.abs(niza.get(i) - prosek);
                if (niza.get(i) < elementNajblisku)
                    elementNajblisku = niza.get(i);
            }
        }
        return elementNajblisku;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
        String s = stdin.readLine();
        int N = Integer.parseInt(s);

        //Vashiot kod tuka...
        Array<Integer> niza = new Array<>(N);
        for (int i = 0; i < N; i++) {
            String sp = stdin.readLine();
            niza.set(i, Integer.parseInt(sp));
        }
        System.out.println(brojDoProsek(niza));
    }
}
