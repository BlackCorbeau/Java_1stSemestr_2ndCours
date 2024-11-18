package org.example;

class BCpu {
    public static ICpu build() {
        return new CPU();
    }
}
