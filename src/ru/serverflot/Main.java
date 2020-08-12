package ru.serverflot;

public class Main {
    private static APIServer API;

    public static void main(String[] args) {
        API = new APIServer("localhost", 8001);
    }
}
