package ex_final;

import java.io.*;
import java.util.*;

public class AddressBook {
    private ArrayList<Address> book;

    public AddressBook() {
        book = new ArrayList<Address>();
    }

    public void open(String filename) {
        try {
            book.clear();
            File file = new File(filename);
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] field = line.split(",");
                add(new Address(field[0], field[1], field[2], field[3]));
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void save(String filename) {
        try {
            File file = new File(filename);
            PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(file)));
            for (Address address : book) {
                writer.println(address);
            }
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void add(Address address) {
        book.add(address);
    }

    public void remove(Address address) {
        book.remove(address);
    }

    public void showAddresses() {
        for (Address address : book) {
            System.out.println(address);
        }
    }

    public Address findName(String name) {
        for (Address address : book) {
            if (name.equals(address.getName())) {
                return address;
            }
        }
        return null;
    }

    public Address findAddress(String add) {
        for (Address address : book) {
            if (add.equals(address.getAddress())) {
                return address;
            }
        }
        return null;
    }

    public Address findTel(String tel) {
        for (Address address : book) {
            if (tel.equals(address.getTel())) {
                return address;
            }
        }
        return null;
    }

    public Address findEmail(String email) {
        for (Address address : book) {
            if (email.equals(address.getEmail())) {
                return address;
            }
        }
        return null;
    }

    public ArrayList<String> getNames() {
        ArrayList<String> nameList = new ArrayList<String>();
        for (Address address : book) {
            nameList.add(address.getName());
        }
        return nameList;
    }

    public static void main(String[] args) {
        AddressBook book = new AddressBook();
        book.open("address.txt");
        book.showAddresses();
        Address taroAddress = new Address("????????????", "?????????????????????", "03-5280-XXXX", "taro@dendai.ac.jp");
        book.add(taroAddress);
        book.showAddresses();
        String tmp = "????????????";
        System.out.println(tmp + "????????????" + book.findName(tmp));
        tmp = "?????????????????????";
        System.out.println(tmp + "????????????" + book.findAddress(tmp));
        tmp = "03-5280-XXXX";
        System.out.println(tmp + "????????????" + book.findTel(tmp));
        tmp = "taro@dendai.ac.jp";
        System.out.println(tmp + "????????????" + book.findEmail(tmp));
        tmp = "????????????";
        System.out.println(tmp + "????????????" + book.findName(tmp));
        book.remove(taroAddress);
        book.showAddresses();
        book.save("address2.txt");
    }

}