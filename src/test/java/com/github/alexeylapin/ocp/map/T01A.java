package com.github.alexeylapin.ocp.map;

import java.util.Objects;

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
    public boolean equals(Object o) {
        System.out.println(">> " + this + ".equals(" + o.toString() + ")");
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        T01A t01A = (T01A) o;
        return i == t01A.i;
    }

    @Override
    public int hashCode() {
        System.out.println(">> " + this + ".hashCode()");
        return Objects.hash(i);
    }

    @Override
    public int compareTo(T01A o) {
        System.out.println(">> " + this + ".compareTo(" + o.toString() + ")");
        return Integer.compare(i, o.i);
    }
}
