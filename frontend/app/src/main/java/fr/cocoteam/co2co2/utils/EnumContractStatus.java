package fr.cocoteam.co2co2.utils;

public enum EnumContractStatus {

    TODO("Establish agreement",0),
    DOING("Waiting for response",1),
    DONE("Done !",2);


    private String stringValue;
    private int intValue;


    EnumContractStatus(String toString, int value) {
        stringValue = toString;
        intValue = value;
    }

    @Override
    public String toString() {
        return stringValue;
    }
}
