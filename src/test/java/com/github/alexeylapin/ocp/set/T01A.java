package com.github.alexeylapin.ocp.set;

class T01A implements Comparable<T01A> {

    private int i;

    public T01A(int i) {
        this.i = i;
    }

    @Override
    public String toString() {
        return "T01A{" + "i=" + i + "}[" + System.identityHashCode(this) + "]";
    }

    @Override
    public int hashCode() {
        System.out.println(">> [" + this + "] hashCode()");
        return super.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        System.out.println(">> [" + this + "] equals(" + o.toString() + ")");
        return super.equals(o);
    }

    @Override
    public int compareTo(T01A o) {
        System.out.println(">> " + this + ".compareTo(" + o.toString() + ")");
        return Integer.compare(i, o.i);
    }
}
