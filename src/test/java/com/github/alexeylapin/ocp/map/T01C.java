package com.github.alexeylapin.ocp.map;

class T01C {

    private int i;

    public T01C(int i) {
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
        T01C t01A = (T01C) o;
        return i == t01A.i;
    }

    @Override
    public int hashCode() {
        System.out.println(">> " + this + ".hashCode()");
        return 42;
    }

//    @Override
//    public int compareTo(T01C o) {
//        System.out.println(">> " + this + ".compareTo(" + o.toString() + ")");
//        return Integer.compare(i, o.i);
//    }
}
